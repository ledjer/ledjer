package galaktica.trading.kryptonite;

import java.util.List;

/**
 * Not sure if this should be a node as in an external thing or just an api adaptor to ethereum
 */
public interface WitnessNode extends AddressableNode {
    void witnessTx(TxReference txReference, TxData txData, List<TxSignature> txSignatures);
}
