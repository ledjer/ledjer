package galaktica.trading.kryptonite;

import java.util.List;

public class TxData {
    public final TxReference txReference;
    public final long nonce;
    public final Class<NominationContract> contractClass;
    public final List<LedjerNode> participants;
    public final String method;
    public final List<Object> inputParameters;
    public final String contractAddress;

    public TxData(TxReference txReference, long nonce, Class<NominationContract> contractClass, List<LedjerNode> participants, String method, List<Object> inputParameters) {
        this.txReference = txReference;
        this.nonce = nonce;
        this.contractClass = contractClass;
        this.participants = participants;
        this.method = method;
        this.inputParameters = inputParameters;
        this.contractAddress = LedjerCrypto.sha256HashOf(nonce, contractClass.getName());
    }
}
