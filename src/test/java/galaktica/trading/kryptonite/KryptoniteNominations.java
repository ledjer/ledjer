package galaktica.trading.kryptonite;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;

public class KryptoniteNominations {

    private static final Logger log = LoggerFactory.getLogger(KryptoniteNominations.class);


    private final GalactikaNode node;
    private final WitnessNode witness;

    public KryptoniteNominations(GalactikaNode node, WitnessNode witness) {
        this.node = node;
        this.witness = witness;
    }

    public NominationContract waitForNomination(TxReference txReference) {
        Tx tx = waitForTx(txReference, 1000);
        return at(tx.txData.contractAddress);
    }

    private  void logTx(Tx tx) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            log.info("[{}] Transaction Json:\n{}", node.getName(),
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(tx.toExternalForm()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Be beter to have a callback
    public Tx waitForTx(TxReference txReference, long timeout) {
        try {

            long startTime = System.currentTimeMillis();
            Tx tx = node.getTx(txReference);
            while (!tx.isComplete()) {
                Thread.sleep(100);
                if (System.currentTimeMillis() - startTime >= timeout) {
                    throw new RuntimeException("Timed out after " + timeout + " ms waiting for tx to complete.");
                }
                tx =  node.getTx(txReference);
            }
            logTx(tx);
            return tx;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }

    public NominationContract at(String contractAddress) {
        return node.findContract(contractAddress);
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
                Arrays.<Object>asList(freighter.id),
                witness);

        node.submitTx(txData);
        return txReference;
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
                emptyList(),
                witness);

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
