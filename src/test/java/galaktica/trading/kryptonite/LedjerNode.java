package galaktica.trading.kryptonite;

import java.util.List;

public interface  LedjerNode  {
    void submitTx(TxData txData);

    void requestSignature(TxData txData);

    void receiveSignature(TxSignature signature);

    String getName();

}
