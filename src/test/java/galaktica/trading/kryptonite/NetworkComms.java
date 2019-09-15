package galaktica.trading.kryptonite;

public class NetworkComms {


    public TxResponse sendContractTo(GalactikaNode destinationNode, Contract contract) {
        destinationNode.registerContract(contract);
        return new TxResponse("Completed", contract.getId());
    }

    public TxResponse sendStateTo(GalactikaNode node, ContractState state) {
        node.findContract(state.getContractId()).appendState(state);
        return new TxResponse("Completed", state.getContractId());
    }
}
