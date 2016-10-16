package rover.state;

import rover.GeneralRover;
import rover.ScanItem;

public class RetrievingResourceState extends ARoverState {
    private GeneralRover rover;

    public RetrievingResourceState(GeneralRover rover){
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
        rover.moveBackToBase();
        return new ReturnToBaseState(rover);
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
            rover.scan(1);
            return null;
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
