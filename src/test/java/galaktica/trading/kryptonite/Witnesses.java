package galaktica.trading.kryptonite;
import static galaktica.trading.kryptonite.GalactikaNetwork.NETWORK_COMMS;

public class Witnesses {
    public static final WitnessNode DEFAULT_WITNESS = new EthereumWitnessNode("EthereumWitness", NETWORK_COMMS);
}
