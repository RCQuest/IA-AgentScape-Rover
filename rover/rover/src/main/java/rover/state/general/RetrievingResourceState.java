package rover.state.general;

import rover.APracticalRover;
import rover.ScanItem;
import rover.state.ARoverState;

public class RetrievingResourceState extends ARoverState {
    private APracticalRover rover;

    public RetrievingResourceState(APracticalRover rover){
        this.rover = rover;
    }
    @Override
    public ARoverState justMoved() throws Exception {
        return tryCollect();
    }

    @Override
    public ARoverState justScanned(ScanItem[] items) throws Exception {
        boolean focusedResourceIsGone=true;
        for (ScanItem item : items) {
            if(Math.abs(item.getxOffset())<0.00001
            && Math.abs(item.getyOffset())<0.00001) {
                focusedResourceIsGone = false;
            }
        }
        if(focusedResourceIsGone)
            rover.removeCurrentResourceLocation();
        if(rover.hasResourceBacklog()){
            rover.focusNextResource();
            rover.moveToFocusedResource();
            return null;
        }
        rover.searchMovement();
        return new SearchingState(rover);
    }

    @Override
    public ARoverState justPickedUp() throws Exception {
        return tryCollect();
    }

    @Override
    public ARoverState justDeposited() {
        return null;
    }

    private ARoverState tryCollect() throws Exception {
        if(rover.loadIsFull()){
            rover.moveBackToBase();
            return new ReturnToBaseState(rover);
        }
        try {
            rover.collect();
        } catch (Exception e) {

            rover.scan(1);
            return null;
        }
        return null;
    }
}
