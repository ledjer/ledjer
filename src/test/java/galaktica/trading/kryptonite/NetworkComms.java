package galaktica.trading.kryptonite;

import java.util.Map;

public class NetworkComms {


    public TxResponse sendContractTo(GalactikaNode destinationNode, Contract contract) {
        destinationNode.registerContract(contract);
        return new TxResponse("Completed", contract.getId());
    }

    public TxResponse sendStateTo(GalactikaNode node, ContractState state) {
        node.findContract(state.getContractId()).appendState(state);
        return new TxResponse("Completed", state.getContractId());
    }

    public Map<LegalEntity, TxSignature> collectSignaures(TxData txData) {
        return null;
    }

    public void sendSignedTx(SignedTx signedTx) {

    }

    public Map<LegalEntity, TxSignature> requestSignatures(TxData txData) {
        return null;
    }
}
