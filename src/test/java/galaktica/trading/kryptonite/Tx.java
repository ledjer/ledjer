package galaktica.trading.kryptonite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Tx {

    private static final Logger log = LoggerFactory.getLogger(Tx.class);
    public final TxReference txReference;
    public final TxData txData;
    public final List<TxSignature> txSignatures;

    public final String status;

    public Tx(TxReference txReference, TxData txData, List<TxSignature> txSignatures, String status) {

        this.txReference = txReference;
        this.txData = txData;
        this.txSignatures = txSignatures;
        this.status = status;
    }

    public static Tx readEvents(TxReference txReference, List<TxEvent> txEventStore) {
        String status = null;
        TxData txData = null;
        List<TxSignature> txSignatures = new ArrayList<TxSignature>();

        for (TxEvent txEvent : txEventStore) { // @todo filter list by txReference
            log.trace("Event: " + txEvent.type);

            if (txEvent.txReference.equals(txReference)) {
                status = txEvent.type;
                if (txEvent.payload != null) {
                    if (txEvent.payload.getClass().isAssignableFrom(TxData.class)) {
                        txData = (TxData) txEvent.payload;
                    } else if (txEvent.payload.getClass().isAssignableFrom(TxSignature.class)) {
                        txSignatures.add((TxSignature) txEvent.payload);
                    }
                }
            }
        }

        if (txData == null) {
            throw new RuntimeException("No tx data found in event store for tx [" + txReference + "]");
        }

        if (txSignatures.size() == txData.participants.size()) {
            status = "Completed";
        }
        return new Tx(txReference, txData, txSignatures, status);

    }


    public boolean isComplete() {
        return status == "Completed";
    }

    public TxExternal toExternalForm() {
        return new TxExternal(txReference.toString(),
                this.txData.contractClass.getSimpleName(),
                this.txData.method,
                this.txData.inputParameters,
                extractSignatures(this.txSignatures),
                this.status);
    }

    private List<String> extractSignatures(List<TxSignature> txSignatures) {
        List<String> stringSignatures = new ArrayList<String>();
        for (TxSignature txSignature : txSignatures) {
            stringSignatures.add(txSignature.signature);
        }
        return stringSignatures;
    }
}
