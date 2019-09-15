package galaktica.trading.kryptonite;

public interface Contract<T> {
    String getId();

    void appendState(T state);
}
