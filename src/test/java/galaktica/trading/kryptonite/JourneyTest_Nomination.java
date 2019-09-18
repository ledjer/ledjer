package galaktica.trading.kryptonite;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.qos.logback.classic.Level.DEBUG;
import static galaktica.trading.kryptonite.Freighters.CARGO_CULT;
import static galaktica.trading.kryptonite.Traders.HYDRA;
import static galaktica.trading.kryptonite.Traders.TAURUS;
import static ledjer.platform.ConsoleLogging.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JourneyTest_Nomination {

    private static final Logger log = LoggerFactory.getLogger(JourneyTest_Nomination.class);

    private GalactikaNode hydraNode = HYDRA.node;
    private GalactikaNode taurusNode = TAURUS.node;
    private KryptoniteNominations hydraNominations = hydraNode.kryptonite.nominations;
    private KryptoniteNominations taurusNominations = taurusNode.kryptonite.nominations;

    @BeforeClass
    public static void initialiseLogging() {
        initialiseConsoleLogging(DEBUG, SIMPLE_CONSOLE_FORMAT);
    }

    @Test
    public void can_complete_a_simple_transaction() {

        log.info("Hydra Nominates a freighter [cargo-cult] to Taurus");
        TxReference tx1_reference = hydraNominations.propose(TAURUS, CARGO_CULT);
        assertThat(hydraNode.getTx(tx1_reference).status, is("TransactionSent"));

        Tx tx_hydra = waitForTx(hydraNode, tx1_reference, 1000);
        assertThat(tx_hydra.status, is("Completed"));
        assertThat(tx_hydra.txData.txReference, is(tx1_reference));

        Tx tx_taurus = waitForTx(taurusNode, tx1_reference, 1000);
        assertThat(tx_taurus.status, is("Completed"));
        assertThat(tx_taurus.txData, is(notNullValue()));
    }

    @Test
    public void can_nominate_a_freighter_for_kryptonite() throws JsonProcessingException {

        log.info("Hydra Nominates a freighter [cargo-cult] to Taurus");
        TxReference tx1_reference = hydraNominations.propose(TAURUS, CARGO_CULT);
        waitForTx(hydraNode, tx1_reference, 1000);

        log.info("Taurus accepts the nomination");
        Tx tx_taurus = waitForTx(taurusNode, tx1_reference, 1000);
        TxReference tx2_reference = taurusNominations.accept(tx_taurus.txData.contractAddress);

        NominationContract taurusNominarion = waitForNomination(tx2_reference,
                taurusNode,
                taurusNominations);
        assertThat(taurusNominarion.getStatus(), is("Accepted"));

        NominationContract hydraNomination = waitForNomination(tx2_reference,
                hydraNode,
                hydraNominations);
        assertThat(hydraNomination.getStatus(), is("Accepted"));

    }

    private NominationContract waitForNomination(TxReference tx2_reference, GalactikaNode hydraNode, KryptoniteNominations hydraNominations) {
        Tx tx_hydra_accept = waitForTx(hydraNode, tx2_reference, 1000);
        return hydraNominations.at(tx_hydra_accept.txData.contractAddress);
    }

    private void logTx(LedjerNode ledjerNode, Tx tx) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            log.info("[{}] Transaction Json:\n{}", ledjerNode.getName(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tx.toExternalForm()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // Be beter to have a callback
    private Tx waitForTx(GalactikaNode node, TxReference txReference, long timeout) {
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
            logTx(hydraNode, tx);
            return tx;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

    }

}
