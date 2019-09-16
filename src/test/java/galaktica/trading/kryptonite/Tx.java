package galaktica.trading.kryptonite;

import java.util.List;

public class Tx {

    public final TxReference txReference;
    public final TxData txData;

    public final String status;

    public Tx(TxReference txReference, TxData txData, String status) {

        this.txReference = txReference;
        this.txData = txData;
        this.status = status;
    }

    public static Tx readEvents(TxReference txReference, List<TxEvent> txEventStore) {
        String status = null;
        TxData txData = null;
        for (TxEvent txEvent : txEventStore) { // @todo filter list by txReference
            if (txEvent.txReference.equals(txReference)) {
                status = txEvent.status;
                if (txEvent.payload != null && txEvent.getClass().isAssignableFrom(TxData.class)) {
                    txData = (TxData) txEvent.payload;
                }
            }
        }
        return new Tx(txReference, txData, status);

    }



    public boolean isComplete() {
        return false;
    }
}
