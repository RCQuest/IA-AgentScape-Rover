package rover.reasoning.simple;

import rover.PollResult;
import rover.ScanItem;
import rover.messaging.AMessage;
import rover.shared.practical.CoordinateMap;
import rover.shared.practical.IPerceiver;
import rover.shared.practical.WorldPercept;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.APerceptFactory;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimplePerceptFactory extends APerceptFactory {
    @Override
    public APercept create(PollResult pr, IPerceiver rover) {
        if(pr==null){
            pr = new PollResult(PollResult.COMPLETE,PollResult.WORLD_STARTED);
            pr.setScanItems(new ScanItem[]{});
        }
        ScanItem[] scanItems = pr.getScanItems();
        if(scanItems==null){
            scanItems= new ScanItem[]{};
        }
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
        percept.setPreviousActionWasSuccessful(rover.previousActionWasSuccessful());
        percept.setPreviousAction(pr.getResultType());
        percept.setMovementSpeed(rover.getMovementSpeed());
        percept.setEnergyRemaining(rover.getEnergyRemaining());
        percept.setTypeOfResourceConcerned(rover.getTypeOfResourceCarrier());
        percept.setNumberOfScanningRovers(rover.getNumberOfScanningRovers());

        ArrayList<AMessage> newMessages = rover.getNewMessages();
        for (AMessage message:newMessages) {
            message.modifyPercept(percept);
        }

        return percept;
    }
}
