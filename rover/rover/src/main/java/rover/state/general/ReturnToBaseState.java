package rover.state.general;

import rover.AGeneralRover;
import rover.ScanItem;
import rover.state.ARoverState;

/**
 * Created by rachelcabot on 14/10/2016.
 */
public class ReturnToBaseState extends ARoverState {

    private AGeneralRover rover;

    public ReturnToBaseState(AGeneralRover rover){
        this.rover = rover;
    }
    @Override
    public ARoverState justMoved() throws Exception {
        return tryDeposit();
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
    public ARoverState justDeposited() throws Exception {
        return tryDeposit();
    }

    private ARoverState tryDeposit() throws Exception{
        if(!rover.loadIsEmpty()) {
            rover.deposit();
            return null;
        }
        if(rover.isFocusedOnResource()){
            rover.moveToFocusedResource();
            return new RetrievingResourceState(rover);
        }
        if(rover.hasResourceBacklog()){
            rover.focusNextResource();
            rover.moveToFocusedResource();
            return new RetrievingResourceState(rover);
        }
        rover.searchMovement();
        return new SearchingState(rover);
    }

}
