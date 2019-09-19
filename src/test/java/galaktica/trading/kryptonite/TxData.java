package galaktica.trading.kryptonite;

import java.util.List;

public class TxData {

    public final Class<? extends Contract> contractClass;
    public final String contractAddress;
    public final String method;
    public final List<LedjerNode> participants;

    public final List<Object> inputParameters;

    public final WitnessNode witness;
    public final long nonce;

    public TxData(long nonce,
                  String contractAddress,
                  Class<? extends Contract> contractClass, List<LedjerNode> participants,
                  String method, List<Object> inputParameters, WitnessNode witness) {
        this.nonce = nonce;
        this.contractClass = contractClass;
        this.participants = participants;
        this.method = method;
        this.inputParameters = inputParameters;
        this.contractAddress = contractAddress;
        this.witness = witness;
    }
}
