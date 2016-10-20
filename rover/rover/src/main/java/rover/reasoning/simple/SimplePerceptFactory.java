package rover.reasoning.simple;

import rover.PollResult;
import rover.ScanItem;
import rover.shared.practical.CoordinateMap;
import rover.shared.practical.IPerceiver;
import rover.shared.practical.WorldPercept;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.APerceptFactory;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimplePerceptFactory extends APerceptFactory {
    @Override
    public APercept create(PollResult pr, IPerceiver rover) {
        ScanItem[] scanItems = pr.getScanItems();
        int capacity = rover.getCapacity();
        int load = rover.getCurrentLoad();
        CoordinateMap searchNodes = rover.getSearchNodes();

        WorldPercept percept = new WorldPercept();
        percept.setItemsICanSee(scanItems);
        percept.setRoverCapacity(capacity);
        percept.setRoverLoad(load);
        percept.setSearchNodesRemaining(searchNodes);
        percept.setWorldHeight(rover.getWorldHeight());
        percept.setWorldWidth(rover.getWorldWidth());
        percept.setMyPosition(rover.getPosition());
        return percept;
    }
}
