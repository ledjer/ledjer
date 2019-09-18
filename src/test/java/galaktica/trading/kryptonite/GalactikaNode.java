package galaktica.trading.kryptonite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GalactikaNode implements LedjerNode {

    private static final Logger log = LoggerFactory.getLogger(GalactikaNode.class);

    public final KryptoniteTrading kryptonite;
    public final String name;
    public final NetworkComms networkComms;
    private final Map<String, Contract> contractIndex = new HashMap<String, Contract>();
    private final List<Contract> contracts = new ArrayList<Contract>();
    private HashMap<TxReference, TxData> txs = new HashMap<TxReference, TxData>();
    private final List<TxEvent> txEventStore = new CopyOnWriteArrayList<TxEvent>();


    public GalactikaNode(String name, NetworkComms networkComms) {
        this.name = name;
        this.networkComms = networkComms;
        kryptonite = new KryptoniteTrading(new KryptoniteNominations(this));
    }

    public void registerContract(Contract contract) {
        contractIndex.put(contract.getId(), contract);
        contracts.add(contract);
    }

    public <T extends Contract> T findContract(String id) {
        return (T)contractIndex.get(id);
    }


    public <T extends Contract> T mostRecentContract() {
        if (contractIndex.size() == 0) {
            throw new IllegalStateException("There are no contracts!");
        }
        return (T)contracts.get(contractIndex.size()-1);
    }

    public void submitTx(TxData txData) {
        log.debug("[{}] Transaction [{}] Submitted", name, txData.txReference);

        TxSignature txSignature = signTxData(txData);

        store_registeredTx(txData, txSignature);

        log.debug("[{}] Sending signed txData [{}] to participants (Signature [{}])", name, txData.txReference, txSignature.signature);

        networkComms.sendTxToParticipants(this, txData, txSignature);

        store_txSent(txData.txReference);


    }

    @Override
    public void receiveTxData(TxData txData, TxSignature coordinatorSignature) {
        store_registeredTx(txData, coordinatorSignature);


        TxSignature txSignature = signTxData(txData);
        networkComms.sendSignature(this, txSignature, txData.participants);
    }

    public void receiveSignature(TxSignature signature) {
        log.debug("[{}] Received signature [{}]", name, signature.signature);

        store_signature(signature);
    }


    public String getName() {
        return name;
    }




    private TxSignature signTxData(TxData txData) {
        return new TxSignature(txData.txReference, UUID.randomUUID().toString().substring(0, 6));
    }

    private void store_registeredTx(TxData txData, TxSignature coordinatorSignature) {
        this.txEventStore.addAll(Arrays.<TxEvent>asList(
                new TxEvent("RegisteredTx", txData.txReference, txData),
                new TxEvent("SignatureAdded", coordinatorSignature.txReference, coordinatorSignature)));
    }

    private void store_signature(TxSignature signature) {
        this.txEventStore.add(new TxEvent("SignatureAdded", signature.txReference, signature));
    }


    private void store_txSent(TxReference txReference) {
        this.txEventStore.add(new TxEvent("TransactionSent", txReference));
    }

    public Tx getTx(TxReference txReference) {
        log.trace("[{}] Load Tx [{}]", name, txReference);
        return Tx.readEvents(txReference, txEventStore);
    }

    public long nextNonce() {
        long nonce = 0;
        for (TxEvent txEvent : txEventStore) {
            if (txEvent.type == "RegisteredTx") {
                nonce ++;
            }
        }
        return nonce;
    }
}
