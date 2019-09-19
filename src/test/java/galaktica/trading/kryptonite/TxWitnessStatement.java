package galaktica.trading.kryptonite;

public class TxWitnessStatement {
    public final String witnessNode;
    public final TxSignature txSignature;
    public final  String witnessEvidence;

    public TxWitnessStatement(WitnessNode witnessNode, TxSignature txSignature, String witnessEvidence) {
        this.witnessNode = witnessNode.getName();
        this.txSignature = txSignature;
        this.witnessEvidence = witnessEvidence;
    }
}
