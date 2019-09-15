package galaktica.trading.kryptonite;

public class NetworkComms {


    public TxResponse sendContractTo(GalactikaNode node, Contract contract) {
        node.registerContract(contract);
        return new TxResponse("Completed");
    }

    public TxResponse sendStateTo(GalactikaNode node, ContractState state) {
        node.findContract(state.getContractId()).appendState(state);
        return new TxResponse("Completed");
    }
}
