package galaktica.trading.kryptonite;

import org.junit.Test;

import static galaktica.trading.kryptonite.Freighters.CARGO_CULT;
import static galaktica.trading.kryptonite.Traders.HYDRA;
import static galaktica.trading.kryptonite.Traders.TAURUS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JourneyTest_Nomination {

    private GalactikaNode hydraNode = HYDRA.node;
    private GalactikaNode taurusNode = TAURUS.node;
    private KryptoniteNominations hydraNominations = hydraNode.kryptonite.nominations;
    private KryptoniteNominations taurusNominations = taurusNode.kryptonite.nominations;

    @Test
    public void can_nominate_a_freighter_for_kryptonite() {

        TxReference tx1_reference = hydraNominations.propose(TAURUS, CARGO_CULT);

        assertThat(hydraNode.getTx(tx1_reference).status, is("RequestedSignatures"));

//        Tx tx = waitForTxToComplete(hydraNode, tx1_reference, 1000);
//
//        assertThat(tx.status, is("Completed"));
//        assertThat(tx.txData, is(notNullValue()));
//
//        System.out.println(tx);

//        TxResponse txResponse_2 = taurusNominations.mostRecentNomination().accept();
//        assertThat(txResponse_2.status, is("Completed"));
//
//        NominationState nomination = hydraNominations.at(tx1_reference.contractAddress).currentState();
//        assertThat(nomination.status, is("Accepted"));
    }

    // Be beter to have a callback
    private Tx waitForTxToComplete(GalactikaNode node, TxReference txReference, long timeout) {
        try {

            long startTime = System.currentTimeMillis();
            Tx tx = node.getTx(txReference);
            while (!tx.isComplete()){
                Thread.sleep(100);
                if (System.currentTimeMillis() - startTime >= timeout) {
                    throw new RuntimeException("Timed out after " + timeout + " ms waiting for tx to complete.");
                }
                tx = node.getTx(txReference);

            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

}
