package galaktica.trading.kryptonite;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        Tx tx_hydra = hydraNominations.waitForTx(tx1_reference, 1000);
        assertThat(tx_hydra.status, is("Completed"));
        assertThat(tx_hydra.txData.txReference, is(tx1_reference));

        Tx tx_taurus = taurusNominations.waitForTx(tx1_reference, 1000);
        assertThat(tx_taurus.status, is("Completed"));
        assertThat(tx_taurus.txData, is(notNullValue()));
    }

    @Test
    public void can_nominate_a_freighter_for_kryptonite() throws JsonProcessingException {

        log.info("Hydra Nominates a freighter [cargo-cult] to Taurus");
        TxReference tx1_reference = hydraNominations.propose(TAURUS, CARGO_CULT);
        hydraNominations.waitForTx(tx1_reference, 1000);

        log.info("Taurus accepts the nomination");
        Tx tx_taurus = taurusNominations.waitForTx(tx1_reference, 1000);
        TxReference tx2_reference = taurusNominations.accept(tx_taurus.txData.contractAddress);

        NominationContract taurusNomination = taurusNominations.waitForNomination(tx2_reference);
        assertThat(taurusNomination.getStatus(), is("Accepted"));

        NominationContract hydraNomination = hydraNominations.waitForNomination(tx2_reference);
        assertThat(hydraNomination.getStatus(), is("Accepted"));

    }

}
