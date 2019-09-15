package galaktica.trading.kryptonite;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class KryptoniteNominations {



    private final GalactikaNode node;

    public KryptoniteNominations(GalactikaNode node) {
        this.node = node;
    }

    public NominationContract at(String nominationId) {
        return node.findContract(nominationId);
    }

    public TxResponse accept(String nomination_001_taurus) {

        return null;
    }

    public TxResponse propose(Trader nominator, Trader receiver, Freighter freighter) {
        NominationContract contract = new NominationContract(newContractAddress(), node.networkComms);
        node.registerContract(contract);
        return contract.propose(nominator, receiver, freighter, "Proposed");
    }

    private String newContractAddress() {
        return randomUUID().toString();
    }


    public NominationContract mostRecentNomination() {
        return node.mostRecentContract();
    }
}
