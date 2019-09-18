package galaktica.trading.kryptonite;

import java.util.List;

public interface  LedjerNode  {
    void submitTx(TxData txData);



    void receiveSignature(TxSignature signature);

    String getName();

    void receiveTxData(TxData txData);
}
