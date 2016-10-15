package rover.state;

import rover.GeneralRover;
import rover.ScanItem;

/**
 * Created by rachelcabot on 14/10/2016.
 */
public class ReturnToBaseState extends ARoverState {

    private GeneralRover rover;

    public ReturnToBaseState(GeneralRover rover){
        this.rover = rover;
    }
    @Override
    public ARoverState justMoved() throws Exception {
        rover.deposit();
        return new SearchingState(rover);
    }

    @Override
    public ARoverState justScanned(ScanItem[] items) throws Exception {
        return null;
    }

    @Override
    public ARoverState justPickedUp() {
        return null;
    }

    @Override
    public ARoverState justDeposited() {
        return new SearchingState(rover);
    }
}
