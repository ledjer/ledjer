package galaktica.trading.kryptonite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NetworkComms {

    private static final Logger log = LoggerFactory.getLogger(NetworkComms.class);

    public NetworkComms() {

    }

    public TxResponse sendContractTo(GalactikaNode destinationNode, Contract contract) {
        destinationNode.registerContract(contract);
        return new TxResponse("Completed", contract.getAddress());
    }

    public TxResponse sendStateTo(GalactikaNode node, ContractState state) {
        node.findContract(state.getContractId()).appendState(state);
        return new TxResponse("Completed", state.getContractId());
    }


    public void sendSignature(final AddressableNode source, final TxSignature signature, List<LedjerNode> participants) {
        for (final LedjerNode destination : participants) {
            if (!source.getName().equals(destination.getName())) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(50);

                            log.debug("[{}] Sending Signature [{}] to [{}]", source.getName(), signature.signature, destination.getName());

                            destination.receiveSignature(signature);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                t.start();
            }
        }
    }

    public void sendWitnessStatement(final WitnessNode witnessNode, final TxWitnessStatement txWitnessStatement, List<LedjerNode> participants) {
        for (final LedjerNode destination : participants) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(50);

                        log.debug("[{}] Sending Witness Statment [Signature: {}, Evidence: {}] to [{}]",
                                witnessNode.getName(),
                                txWitnessStatement.txSignature.signature,
                                txWitnessStatement.witnessEvidence,
                                destination.getName());

                        destination.receiveWitnessStatement(txWitnessStatement);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            t.start();
        }
    }


    public void sendTxToParticipants(final LedjerNode coordinator, final TxReference txReference,
                                     final TxData txData, final TxSignature coordinatorSignature) {
        for (final LedjerNode destination : txData.participants) {
            if (!coordinator.getName().equals(destination.getName())) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(50);

                            log.debug("[{}] Sending Tx [{}] to [{}]", coordinator.getName(), txReference, destination.getName());

                            destination.receiveTxData(txReference, txData, coordinatorSignature);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                t.start();
            }
        }
    }

    public void sendTxToWitness(final LedjerNode coordinator, final Tx tx) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);

                    log.debug("[{}] Sending Tx [{}] to witness [{}]", coordinator.getName(), tx.txReference, tx.txData.witness.getName());

                    tx.txData.witness.witnessTx(tx.txReference, tx.txData, tx.txSignatures);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }


}
