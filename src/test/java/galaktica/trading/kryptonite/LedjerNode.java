package galaktica.trading.kryptonite;

public interface  LedjerNode extends AddressableNode {
    TxReference submitTx(TxData txData);



    void receiveSignature(TxSignature signature);

    void receiveTxData(TxReference txReference, TxData txData, TxSignature coordinatorSignature);

    void receiveWitnessStatement(TxWitnessStatement txWitnessStatement);
}
