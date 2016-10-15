package rover.state;

import rover.GeneralRover;
import rover.Rover;
import rover.RoverMovement;
import rover.ScanItem;

/**
 * Created by rachelcabot on 14/10/2016.
 */
public class RetrievingResourceState extends ARoverState {
    private GeneralRover rover;

    public RetrievingResourceState(GeneralRover rover){

        this.rover = rover;
    }
    @Override
    public ARoverState justMoved() throws Exception {
        rover.collect();
        return null;
    }

    @Override
    public ARoverState justScanned(ScanItem[] items) {
        return null;
    }

    @Override
    public ARoverState justPickedUp() throws Exception {
        System.out.println("Returning...");
        rover.moveBackToBase();
        return new ReturnToBaseState(rover);
    }

    @Override
    public ARoverState justDeposited() {
        return null;
    }
}
