package galaktica.trading.kryptonite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static java.util.UUID.randomUUID;

public class KryptoniteNominations {

    private static final Logger log = LoggerFactory.getLogger(KryptoniteNominations.class);


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

    public TxReference propose(Trader receiver, Freighter freighter) {
        log.debug("[{}] Proposing Nomination", node.name);
        TxReference txReference = new TxReference(randomUUID().toString());
        TxData txData = new TxData(
                txReference,
                0,
                NominationContract.class,
                Arrays.<LedjerNode>asList(node, receiver.node),
                "init",
                Arrays.<Object>asList(freighter.id));

        node.submitTx(txData);
        return txReference;
    }

    private String newContractAddress() {
        return randomUUID().toString();
    }


    public NominationContract mostRecentNomination() {
        return node.mostRecentContract();
    }
}
