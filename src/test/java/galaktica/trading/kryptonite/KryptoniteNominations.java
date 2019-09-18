package galaktica.trading.kryptonite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;

public class KryptoniteNominations {

    private static final Logger log = LoggerFactory.getLogger(KryptoniteNominations.class);


    private final GalactikaNode node;

    public KryptoniteNominations(GalactikaNode node) {
        this.node = node;
    }

    public NominationContract at(String contractAddress) {
        return node.findContract(contractAddress);
    }

    public TxReference accept(String contractAddress) {
        NominationContract contract = at(contractAddress);
        log.debug("[{}] Accepting Nomination [{}]", node.name, contractAddress);
        TxReference txReference = new TxReference(randomUUID().toString());
        TxData txData = new TxData(
                txReference,
                contractAddress,
                NominationContract.class,
                contract.participants, // need to validate this
                "accept",
                emptyList());

        node.submitTx(txData);
        return txReference;
    }

    public TxReference propose(Trader receiver, Freighter freighter) {
        log.debug("[{}] Proposing Nomination", node.name);
        TxReference txReference = new TxReference(randomUUID().toString());
        TxData txData = new TxData(
                txReference,
                LedjerCrypto.sha256HashOf(node.nextNonce(), NominationContract.class.getName()),
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
