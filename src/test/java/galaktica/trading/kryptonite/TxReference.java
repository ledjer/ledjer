package galaktica.trading.kryptonite;

public class TxReference {
    private final String uuid;

    public TxReference(String uuid) {
        this.uuid = uuid;
    }

    public boolean isComplete() {
        return false;
    }
}
