package galaktica.trading.kryptonite;

public class TxResponse {
    public final String status;
    public final String contractAddress;

    public TxResponse(String status, String contractId) {
        this.status = status;
        this.contractAddress = contractId;
    }
}
