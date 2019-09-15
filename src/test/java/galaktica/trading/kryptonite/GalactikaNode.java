package galaktica.trading.kryptonite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalactikaNode {


    public final KryptoniteTrading kryptonite;
    public final String name;
    public final NetworkComms networkComms;
    private final Map<String, Contract> contractIndex = new HashMap<String, Contract>();
    private final List<Contract> contracts = new ArrayList<Contract>();



    public GalactikaNode(String name, NetworkComms networkComms) {
        this.name = name;
        this.networkComms = networkComms;
        kryptonite = new KryptoniteTrading(new KryptoniteNominations(this));
    }

    public void registerContract(Contract contract) {
        contractIndex.put(contract.getId(), contract);
        contracts.add(contract);
    }

    public <T extends Contract> T findContract(String id) {
        return (T)contractIndex.get(id);
    }


    public <T extends Contract> T mostRecentContract() {
        if (contractIndex.size() == 0) {
            throw new IllegalStateException("There are no contracts!");
        }
        return (T)contracts.get(contractIndex.size()-1);
    }
}
