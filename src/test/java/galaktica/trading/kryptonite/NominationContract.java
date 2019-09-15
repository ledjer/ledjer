package galaktica.trading.kryptonite;

import java.util.ArrayList;
import java.util.List;

public class NominationContract  implements Contract<NominationState> {

    private List<NominationState> states = new ArrayList<NominationState>();
    public final String id;
    private NetworkComms networkComms;

    public NominationContract(String id, NetworkComms networkComms) {
        this.id = id;
        this.networkComms = networkComms;
    }


    public TxResponse accept() {
        NominationState s = currentState();
        NominationState s1 = new NominationState(id, s.nominator, s.receiver, s.freighter, "Accepted");
        states.add(s1);
        return networkComms.sendStateTo(s.receiver.node, s1);
    }

    public TxResponse propose(Trader nominator, Trader receiver, Freighter freighter, String state) {
        states.add(new NominationState(id, nominator, receiver, freighter, state));
        return networkComms.sendContractTo(receiver.node, this);
    }

    public NominationState currentState() {
        return states.get(states.size()-1);
    }

    public String getId() {
        return id;
    }

    public void appendState(NominationState state) {
        states.add(state);
    }
}
