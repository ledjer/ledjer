package galaktica.trading.kryptonite;

public interface Contract<T> {
    String getAddress();

    void appendState(T state);
}
