package galaktica.trading.kryptonite;

import java.util.List;

public class TxExternal {
    public final String txReference;
    public final TxDataExternal txData;
    public final List<String> signatures;
    public final TxWitnessStatementExternal witnessStatement;
    public final String status;


    public TxExternal(String txReference,
                      List<String> participants,
                      String contractClass,
                      String method,
                      List<Object> inputParameters,
                      List<String> signatures,
                      TxWitnessStatementExternal txWitnessStatement,
                      String status) {
        this.txReference = txReference;
        this.txData = new TxDataExternal(participants, contractClass, method, inputParameters);
        this.signatures = signatures;
        this.status = status;
        this.witnessStatement = txWitnessStatement;
    }

    public static class TxDataExternal {
        public final List<String> participants;
        public final String contractClass;
        public final String method;
        public final List<Object> inputParameters;

        public TxDataExternal(List<String> participants, String contractClass, String method, List<Object> inputParameters) {
            this.participants = participants;
            this.contractClass = contractClass;
            this.method = method;
            this.inputParameters = inputParameters;
        }
    }

    public static class TxWitnessStatementExternal {
        public final String witnessNode;
        public final String signature;
        public final String evidence;

        public TxWitnessStatementExternal(TxWitnessStatement txWitnessStatement) {
            this.witnessNode = txWitnessStatement.witnessNode;
            this.signature = txWitnessStatement.txSignature.signature;
            this.evidence = txWitnessStatement.witnessEvidence;
        }
    }
}
