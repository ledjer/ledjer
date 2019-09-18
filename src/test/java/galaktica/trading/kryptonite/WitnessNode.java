package galaktica.trading.kryptonite;

import java.util.List;

public interface WitnessNode extends AddressableNode {
    void witnessTx(TxData txData, List<TxSignature> txSignatures);
}
