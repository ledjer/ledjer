package galaktica.trading.kryptonite;

public interface  LedjerNode  {
    void submitTx(TxData txData);



    void receiveSignature(TxSignature signature);

    String getName();

    void receiveTxData(TxData txData, TxSignature coordinatorSignature);
}
