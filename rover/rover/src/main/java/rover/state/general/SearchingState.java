package rover.state.general;

import rover.AGeneralRover;
import rover.shared.RoverOffset;
import rover.ScanItem;
import rover.state.ARoverState;

/**
 * Created by rachelcabot on 13/10/2016.
 */
public class SearchingState extends ARoverState {
    private AGeneralRover rover;

    public SearchingState(AGeneralRover rover){
        this.rover = rover;
    }
    @Override
    public ARoverState justMoved() throws Exception {
        rover.scan(rover.getScanRadius());
        return null;
    }

    @Override
    public ARoverState justScanned(ScanItem[] items) throws Exception {
        for(ScanItem item : items) {
            if(item.getItemType() == ScanItem.RESOURCE) {
                RoverOffset offset = rover.noteResourceLocation(item);
                rover.moveToItem(item);
                rover.setResourceFocus(offset);
                return new RetrievingResourceState(rover);
            }
        }
        rover.searchMovement();
        return null;
    }

    @Override
    public ARoverState justPickedUp() {
        return null;
    }

    @Override
    public ARoverState justDeposited() throws Exception {
        rover.searchMovement();
        return null;
    }
}
