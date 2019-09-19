package galaktica.trading.kryptonite;

public interface  LedjerNode extends AddressableNode {
    void submitTx(TxData txData);



    void receiveSignature(TxSignature signature);

    void receiveTxData(TxData txData, TxSignature coordinatorSignature);

    void receiveWitnessStatement(TxWitnessStatement txWitnessStatement);
}
