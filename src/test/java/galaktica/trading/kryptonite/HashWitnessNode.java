package galaktica.trading.kryptonite;

import java.util.List;
import java.util.UUID;

public class HashWitnessNode implements WitnessNode {

    private final String name;
    private final NetworkComms networkComms;

    public HashWitnessNode(String name, NetworkComms networkComms) {
        this.name = name;
        this.networkComms = networkComms;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void witnessTx(TxReference txReference, TxData txData, List<TxSignature> txSignatures) {
        TxSignature txSignature = new TxSignature(txReference, LedjerCrypto.sign(txData));
        String witnessEvidence = LedjerCrypto.sha256HashOf(UUID.randomUUID().toString());
        networkComms.sendWitnessStatement(this, new TxWitnessStatement(this, txSignature, witnessEvidence), txData.participants);
    }
}
