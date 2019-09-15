package galaktica.trading.kryptonite;

public class NominationState implements ContractState {
    public final String id;
    public final Trader nominator;
    public final Trader receiver;
    public final Freighter freighter;
    public final String status;

    public NominationState(String id, Trader nominator, Trader nominee, Freighter freighter, String status) {
        this.id = id;
        this.nominator = nominator;
        this.receiver = nominee;
        this.freighter = freighter;
        this.status = status;
    }

    public static NominationState nomination(String id, Trader nominator, Trader nominee, Freighter freighter) {
        return new NominationState(id, nominator, nominee, freighter,"Created");
    }


    public String getContractId() {
        return id;
    }
}
