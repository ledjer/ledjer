package galaktica.trading.kryptonite;

import java.util.List;

public class TxData {
    public final TxReference txReference;
    public final Class<NominationContract> contractClass;
    public final List<LedjerNode> participants;
    public final String propose;
    public final List<Object> inputParameters;

    public TxData(TxReference txReference, Class<NominationContract> contractClass, List<LedjerNode> participants, String propose, List<Object> inputParameters) {
        this.txReference = txReference;
        this.contractClass = contractClass;
        this.participants = participants;
        this.propose = propose;
        this.inputParameters = inputParameters;
    }
}
