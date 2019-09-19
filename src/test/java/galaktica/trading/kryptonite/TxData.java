package galaktica.trading.kryptonite;

import java.util.List;

public class TxData {

    public final Class<NominationContract> contractClass;
    public final List<LedjerNode> participants;
    public final String method;
    public final List<Object> inputParameters;
    public final String contractAddress;
    public final WitnessNode witness;

    public TxData(String contractAddress,
                  Class<NominationContract> contractClass, List<LedjerNode> participants,
                  String method, List<Object> inputParameters, WitnessNode witness) {
        this.contractClass = contractClass;
        this.participants = participants;
        this.method = method;
        this.inputParameters = inputParameters;
        this.contractAddress = contractAddress;
        this.witness = witness;
    }
}
