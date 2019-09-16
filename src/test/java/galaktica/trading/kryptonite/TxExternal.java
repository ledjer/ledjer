package galaktica.trading.kryptonite;

import java.util.List;

public class TxExternal {

    public final TxDataExternal txData;
    public final List<String> signatures;
    public final String status;

    public TxExternal(String txReference, String contractClass,
                      String method, List<Object> inputParameters,
                      List<String> signatures, String status) {
        this.txData = new TxDataExternal(txReference, contractClass, method, inputParameters);
        this.signatures = signatures;
        this.status = status;
    }

    public static class TxDataExternal {
        public final String txReference;
        public final String contractClass;
        public final String method;
        public final List<Object> inputParameters;

        public TxDataExternal(String txReference, String contractClass, String method, List<Object> inputParameters) {
            this.txReference = txReference;
            this.contractClass = contractClass;
            this.method = method;
            this.inputParameters = inputParameters;
        }
    }
}
