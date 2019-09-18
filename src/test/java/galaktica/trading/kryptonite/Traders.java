package galaktica.trading.kryptonite;

import static galaktica.trading.kryptonite.GalactikaNetwork.NETWORK_COMMS;
import static galaktica.trading.kryptonite.Witnesses.DEFAULT_WITNESS;

public class Traders {
    public static final Trader HYDRA = new Trader("Hydra", NETWORK_COMMS, DEFAULT_WITNESS);
    public static final Trader TAURUS = new Trader("Taurus", NETWORK_COMMS, DEFAULT_WITNESS);
}
