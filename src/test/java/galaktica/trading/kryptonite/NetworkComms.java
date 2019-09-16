package galaktica.trading.kryptonite;

public class NetworkComms {


    public NetworkComms() {

    }

    public TxResponse sendContractTo(GalactikaNode destinationNode, Contract contract) {
        destinationNode.registerContract(contract);
        return new TxResponse("Completed", contract.getId());
    }

    public TxResponse sendStateTo(GalactikaNode node, ContractState state) {
        node.findContract(state.getContractId()).appendState(state);
        return new TxResponse("Completed", state.getContractId());
    }


    public void sendSignature(final LedjerNode source, final LedjerNode destination, final TxSignature signature) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                    System.out.println(Thread.currentThread().getId() + " - " + source.getName() + " Sending Signature [" + signature.signature + "] To " + destination.getName());
                    destination.receiveSignature(signature);
                } catch( InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

    public void requestSignatures(LedjerNode coordinator, TxData txData) {
        for (LedjerNode node : txData.participants) {
            requestSignature(node, coordinator, txData);
        }
    }

    private void requestSignature(final LedjerNode destination, final LedjerNode coordinator, final TxData txData) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                    System.out.println(Thread.currentThread().getId() + " - " + coordinator.getName() + " Requesting signature from " + destination.getName());
                    destination.requestSignature(coordinator, txData);
                } catch( InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }


}