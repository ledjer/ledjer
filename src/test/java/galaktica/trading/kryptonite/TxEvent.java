package galaktica.trading.kryptonite;


public class TxEvent  {
    public final String status;
    public final TxReference txReference;
    public final Object payload;

    public TxEvent(String status, TxReference txReference) {
        this(status, txReference, null);
    }
    public TxEvent(String status, TxReference txReference, Object payload) {
        this.status = status;
        this.txReference = txReference;
        this.payload = payload;
    }
}
