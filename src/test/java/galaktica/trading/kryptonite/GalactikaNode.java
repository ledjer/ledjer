package galaktica.trading.kryptonite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static galaktica.trading.kryptonite.LedjerCrypto.sha256HashOf;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;

public class GalactikaNode implements LedjerNode {

    private static final Logger log = LoggerFactory.getLogger(GalactikaNode.class);

    public final KryptoniteTrading kryptonite;
    public final String name;
    public final NetworkComms networkComms;
    private final Map<String, Contract> contractIndex = new HashMap<String, Contract>();
    private final List<Contract> contracts = new ArrayList<Contract>();
    private HashMap<TxReference, TxData> txs = new HashMap<TxReference, TxData>();
    private final List<TxEvent> txEventStore = new CopyOnWriteArrayList<TxEvent>();


    public GalactikaNode(String name, NetworkComms networkComms, WitnessNode witness) {
        this.name = name;
        this.networkComms = networkComms;
        kryptonite = new KryptoniteTrading(new KryptoniteNominations(this, witness));
    }

    public void registerContract(Contract contract) {
        contractIndex.put(contract.getAddress(), contract);
        contracts.add(contract);
    }

    public <T extends Contract> T findContract(String address) {
        if (!contractIndex.containsKey(address)) {
            throw new RuntimeException(format("[%s] Could not find a contract with address [%s]", name, address));
        }
        return (T) contractIndex.get(address);
    }


    public <T extends Contract> T mostRecentContract() {
        if (contractIndex.size() == 0) {
            throw new IllegalStateException("There are no contracts!");
        }
        return (T) contracts.get(contractIndex.size() - 1);
    }

    public TxReference submitTx(TxData txData) {
        TxReference txReference = new TxReference(sha256HashOf(txData));
        log.debug("[{}] Transaction [{}] Submitted", name, txReference);

        TxSignature txSignature = signTxData(txReference, txData);

        store_registeredTx(txReference, txData, txSignature);


        log.debug("[{}] Sending signed txData [{}] to participants (Signature [{}])", name, txReference, txSignature.signature);

        networkComms.sendTxToParticipants(this, txReference, txData, txSignature);

        store_txSent(txReference);

        return txReference;

    }

    private void process_tx(TxData txData) {
        if (NominationContract.class.getName().equals(txData.contractClass.getName())) {
            if ("init".equals(txData.method)) {
                NominationContract nominationContract = new NominationContract(txData.contractAddress, txData.participants);
                contractIndex.put(txData.contractAddress, nominationContract);
                log.debug("[{}] Creating contract [{}] at [{}]", name, nominationContract.getClass().getName(), txData.contractAddress);
            } else if ("accept".equals(txData.method)) {
                NominationContract nominationContract = kryptonite.nominations.at(txData.contractAddress);
                nominationContract.accept();
                log.debug("[{}] accepting contract [{}] at [{}]", name, nominationContract.getClass().getName(), txData.contractAddress);
            }
        }
    }

    @Override
    public void receiveTxData(TxReference txReference, TxData txData, TxSignature coordinatorSignature) {
        store_registeredTx(txReference, txData, coordinatorSignature);

        TxSignature txSignature = signTxData(txReference, txData);
        store_signature(txSignature);
        networkComms.sendSignature(this, txSignature, txData.participants);
    }


    public void receiveSignature(TxSignature signature) {
        log.debug("[{}] Received signature [{}]", name, signature.signature);

        store_signature(signature);

        if (TxWitnessStatement.class.isAssignableFrom(signature.getClass())) {
            return;
        }

        Tx tx = getTx(signature.txReference);

        if (tx.isCoordinator() && tx.readyToWitness()) {
            witnessTx(tx);
        }
    }

    @Override
    public void receiveWitnessStatement(TxWitnessStatement txWitnessStatement) {
        log.debug("[{}] Received witness statement from [{}]", name, txWitnessStatement.witnessNode);
        store_witnessStatement(txWitnessStatement);

    }



    private void witnessTx(Tx tx) {
        networkComms.sendTxToWitness(this, tx);
        store_txSentToWitness(tx.txReference);
    }


    public String getName() {
        return name;
    }


    private TxSignature signTxData(TxReference txReference, TxData txData) {
        return new TxSignature(txReference, LedjerCrypto.sign(txData));
    }

    /**
     * This needs to be an atomic transaction to the event store so that we cant loose the signature or tx data
     * otherwise we'd need some way to recover.
     */
    private void store_registeredTx(TxReference txReference, TxData txData, TxSignature coordinatorSignature) {
        this.txEventStore.addAll(Arrays.<TxEvent>asList(
                new TxEvent("RegisteredTx", txReference, txData),
                new TxEvent("SignatureAdded", coordinatorSignature.txReference, coordinatorSignature)));
        process_tx(txData);
    }

    private void store_signature(TxSignature txSignature) {
        this.txEventStore.add(new TxEvent("SignatureAdded", txSignature.txReference, txSignature));
    }

    private void store_witnessStatement(TxWitnessStatement txWitnessStatement) {
        this.txEventStore.add(new TxEvent("WitnessStatementAdded", txWitnessStatement.txSignature.txReference, txWitnessStatement));
    }



    private void store_txSent(TxReference txReference) {
        this.txEventStore.add(new TxEvent("TransactionSentToParticipants", txReference));
    }

    private void store_txSentToWitness(TxReference txReference) {
        this.txEventStore.add(new TxEvent("TransactionSentToWitness", txReference));
    }

    public Tx getTx(TxReference txReference) {
        log.trace("[{}] Load Tx [{}]", name, txReference);
        return Tx.readEvents(txReference, txEventStore);
    }

    public long nextNonce() {
        long nonce = 0;
        for (TxEvent txEvent : txEventStore) {
            if (txEvent.type == "RegisteredTx") {
                nonce++;
            }
        }
        return nonce;
    }
}
