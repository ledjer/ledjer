package galaktica.trading.kryptonite;

import java.util.ArrayList;
import java.util.List;

public class NominationContract  implements Contract<NominationState> {

    private String status = "proposed";
    private List<NominationState> states = new ArrayList<NominationState>();
    public final String address;
    public List<LedjerNode> participants;

    public NominationContract(String address, List<LedjerNode> participants) {
        this.address = address;

        this.participants = participants;
    }


    public NominationState currentState() {
        return states.get(states.size()-1);
    }

    public String getAddress() {
        return address;
    }

    public void appendState(NominationState state) {
        states.add(state);
    }

    public void accept() {
        status = "Accepted";
    }

    public String getStatus() {
        return status;
    }
}
