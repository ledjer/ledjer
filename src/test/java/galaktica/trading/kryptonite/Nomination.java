package galaktica.trading.kryptonite;

public class Nomination {
    public final String id;
    public final Trader nominator;
    public final Trader nominee;
    public final Freighter freighter;

    public Nomination(String id, Trader nominator, Trader nominee, Freighter freighter) {
        this.id = id;
        this.nominator = nominator;
        this.nominee = nominee;
        this.freighter = freighter;
    }

    public static Nomination nomination(String id, Trader nominator, Trader nominee, Freighter freighter) {
        return new Nomination(id, nominator, nominee, freighter);
    }
}
