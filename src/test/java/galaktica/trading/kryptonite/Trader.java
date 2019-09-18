package galaktica.trading.kryptonite;

public class Trader {
    public final String name;
    public GalactikaNode node;

    public Trader(String name, NetworkComms networkComms, WitnessNode witness) {
        this.name = name;
        this.node = new GalactikaNode(this.name, networkComms, witness);
    }
}
