package galaktica.trading.kryptonite;


public class TxEvent  {
    public final String type;
    public final TxReference txReference;
    public final Object payload;

    public TxEvent(String status, TxReference txReference) {
        this(status, txReference, null);
    }
    public TxEvent(String type, TxReference txReference, Object payload) {
        this.type = type;
        this.txReference = txReference;
        this.payload = payload;
    }
}
