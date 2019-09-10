package galaktica.trading.kryptonite;

import org.junit.Test;

import static galaktica.trading.kryptonite.Freighters.CARGO_CULT;
import static galaktica.trading.kryptonite.Nomination.nomination;
import static galaktica.trading.kryptonite.Traders.HYDRA;
import static galaktica.trading.kryptonite.Traders.TAURUS;

public class JourneyTest_Nomination {

    private GalactikaNode hydraNode;
    private GalactikaNode taurusNode;

    @Test
    public void can_nominate_a_freighter_for_kryptonite() {
        Nomination nomination_001_hydra = nomination("NOM_HYDRA_TAURUS_001", HYDRA, TAURUS, CARGO_CULT);

        hydraNode.submitNomination(nomination_001_hydra);

        Nomination nomination_001_taurus =  taurusNode.findNomination("NOM_HYDRA_TAURUS_001");

        taurusNode.acceptNomination(nomination_001_taurus);


    }

}
