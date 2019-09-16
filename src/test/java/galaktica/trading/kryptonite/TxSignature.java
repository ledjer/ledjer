package galaktica.trading.kryptonite;

public class TxSignature {
    public final TxReference txReference;
    public final String signature;

    public TxSignature(TxReference txReference, String signature) {
        this.txReference = txReference;
        this.signature = signature;
    }
}
