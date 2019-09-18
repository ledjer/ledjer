package galaktica.trading.kryptonite;

import java.util.List;

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
    public void witnessTx(TxData txData, List<TxSignature> txSignatures) {
        String signature = LedjerCrypto.sign(txData);
        networkComms.sendSignature(this, new TxWitnessSignature(txData.txReference, signature), txData.participants);
    }
}
