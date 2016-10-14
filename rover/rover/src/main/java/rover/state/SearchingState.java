package rover.state;

import rover.GeneralRover;
import rover.ScanItem;

/**
 * Created by rachelcabot on 13/10/2016.
 */
public class SearchingState extends ARoverState {
    private GeneralRover rover;

    public SearchingState(GeneralRover rover){
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
                rover.moveToItem(item);
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
    public ARoverState justDeposited() {
        return null;
    }
}
