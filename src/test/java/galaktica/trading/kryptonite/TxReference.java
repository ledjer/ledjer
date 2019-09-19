package galaktica.trading.kryptonite;

public class TxReference {
    private final String hash;

    public TxReference(String uuid) {
        this.hash = uuid;
    }

    public String toString() {
        return hash;
    }
}
