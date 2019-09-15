package galaktica.trading.kryptonite;

import org.junit.Test;

import static galaktica.trading.kryptonite.Freighters.CARGO_CULT;
import static galaktica.trading.kryptonite.Traders.HYDRA;
import static galaktica.trading.kryptonite.Traders.TAURUS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JourneyTest_Nomination {

    private GalactikaNode hydraNode = HYDRA.node;
    private GalactikaNode taurusNode = TAURUS.node;
    private KryptoniteNominations hydraNominations = hydraNode.kryptonite.nominations;
    private KryptoniteNominations taurusNominations = taurusNode.kryptonite.nominations;

    @Test
    public void can_nominate_a_freighter_for_kryptonite() {
        TxResponse txResponse_1 = hydraNominations.propose(HYDRA, TAURUS, CARGO_CULT);
        assertThat(txResponse_1.status, is("Completed"));

        TxResponse txResponse_2 = taurusNominations.mostRecentNomination().accept();
        assertThat(txResponse_2.status, is("Completed"));

        NominationState nomination = hydraNominations.at(txResponse_1.contractAddress).currentState();
        assertThat(nomination.status, is("Accepted"));
    }

}
