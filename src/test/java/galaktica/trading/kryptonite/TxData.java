package galaktica.trading.kryptonite;

import java.util.List;

public class TxData {
    public final TxReference txReference;

    public final Class<NominationContract> contractClass;
    public final List<LedjerNode> participants;
    public final String method;
    public final List<Object> inputParameters;
    public final String contractAddress;

    public TxData(TxReference txReference, String contractAddress, Class<NominationContract> contractClass, List<LedjerNode> participants, String method, List<Object> inputParameters) {
        this.txReference = txReference;
        this.contractClass = contractClass;
        this.participants = participants;
        this.method = method;
        this.inputParameters = inputParameters;
        this.contractAddress = contractAddress;
    }
}
