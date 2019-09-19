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
                      long nonce,
                      String contractAddress,
                      String contractClass,
                      String method,
                      List<Object> inputParameters,
                      List<String> signatures,
                      TxWitnessStatementExternal txWitnessStatement,
                      String status) {
        this.txReference = txReference;
        this.txData = new TxDataExternal(participants,  contractAddress, nonce, contractClass, method, inputParameters);
        this.signatures = signatures;
        this.status = status;
        this.witnessStatement = txWitnessStatement;
    }

    public static class TxDataExternal {

        public final String contract;
        public final String contractAddress;
        public final List<String> participants;
        public final String action;
        public final List<Object> inputParameters;
        public final long nonce;

        public TxDataExternal(List<String> participants, String contractAddress,
                              long nonce, String contract, String action, List<Object> inputParameters) {
            this.participants = participants;
            this.contractAddress = contractAddress;
            this.nonce = nonce;
            this.contract = contract;
            this.action = action;
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
