package galaktica.trading.kryptonite;

import java.util.HashMap;
import java.util.Map;

public class KryptoniteNominations {



    private final GalactikaNode node;

    public KryptoniteNominations(GalactikaNode node) {
        this.node = node;
    }

    public NominationContract find(String nominationId) {
        return node.findContract(nominationId);
    }

    public TxResponse accept(String nomination_001_taurus) {

        return null;
    }

    public TxResponse propose(String id, Trader nominator, Trader receiver, Freighter freighter) {
        NominationContract contract = new NominationContract(id, node.networkComms);
        node.registerContract(contract);
        return contract.propose(nominator, receiver, freighter, "Proposed");
    }


}
