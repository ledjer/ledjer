package galaktica.trading.kryptonite;

import java.util.*;

public class GalactikaNode implements LedjerNode {


    public final KryptoniteTrading kryptonite;
    public final String name;
    public final NetworkComms networkComms;
    private final Map<String, Contract> contractIndex = new HashMap<String, Contract>();
    private final List<Contract> contracts = new ArrayList<Contract>();
    private HashMap<TxReference, TxData> txs = new HashMap<TxReference, TxData>();
    private final List<TxEvent> txEventStore = new ArrayList<TxEvent>();


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
        store_registeredTx(txData);

        networkComms.requestSignatures(txData);

        store_signaturesRequestedTx(txData.txReference);

    }

    private void store_registeredTx(TxData txData) {

        this.txEventStore.add(new TxEvent("RegisteredTx", txData.txReference, txData));

    }

    private void store_signaturesRequestedTx(TxReference txReference) {
        this.txEventStore.add(new TxEvent("RequestedSignatures", txReference));
    }




    private void processTx(TxData txData) {

    }

    public Tx getTx(TxReference txReference) {
        return Tx.readEvents(txReference, txEventStore);
    }
}
