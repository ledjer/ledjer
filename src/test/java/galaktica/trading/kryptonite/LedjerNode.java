package galaktica.trading.kryptonite;

public interface  LedjerNode  {
    void submitTx(TxData txData);

    void requestSignature(LedjerNode coordinator, TxData txData);

    void receiveSignature(TxSignature signature);

    String getName();
}
