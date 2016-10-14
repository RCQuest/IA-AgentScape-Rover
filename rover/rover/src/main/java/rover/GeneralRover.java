package rover;

import rover.state.ARoverState;
import rover.state.SearchingState;
import rover.state.RetrievingResourceState;
import rover.state.ReturnToBaseState;

public class GeneralRover extends Rover {

    private static final int BASE_SPEED = 4;
    private ARoverState state;
    private static final int SCAN_RADIUS = 4;
    private RoverOffset offsetFromBase;
    private CoordinateMap scanMap;

    public GeneralRover() {
        super();

        //use your username for team name
        setTeam("rc566");

        try {
            //set attributes for this rover
            //speed, scan range, max load
            //has to add up to <= 9
            setAttributes(BASE_SPEED, SCAN_RADIUS, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    void begin() {
        //called when the world is started
        getLog().info("BEGIN!");

        try {
            //move somewhere initially
            scanMap = new CoordinateMap(getWorldWidth(),getWorldHeight(),SCAN_RADIUS);
            offsetFromBase = new RoverOffset(0,0,getWorldWidth(),getWorldHeight());
            state = new SearchingState(this);
            searchMovement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public double getScanRadius() {
        return SCAN_RADIUS;
    }

    public int getSpeed() {
        return BASE_SPEED;
    }

    public void moveToItem(ScanItem item) throws Exception {
        this.move(new RoverMovement(item.getxOffset(),item.getyOffset(),getSpeed()));
    }

    public void searchMovement() throws Exception{
        RoverOffset searchOffset = scanMap.popNextClosestNode(offsetFromBase);
        if(searchOffset!=null){
            move(new RoverMovement(searchOffset,BASE_SPEED));
        } else {
            move(new RoverMovement(1,1,1));
        }
    }

    private void move(RoverMovement movement) throws Exception {
        offsetFromBase.addOffset(movement);
        move(movement.xOffset,movement.yOffset,movement.speed);
    }

    public void moveBackToBase() throws Exception {
        move(new RoverMovement(offsetFromBase,BASE_SPEED));
    }

    @Override
    void end() {
        // called when the world is stopped
        // the agent is killed after this
        getLog().info("END!");
    }

    @Override
    void poll(PollResult pr) {
        // This is called when one of the actions has completed

        getLog().info("Remaining Power: " + getEnergy());

        if(pr.getResultStatus() == PollResult.FAILED) {
            getLog().info("Ran out of power...");
            return;
        }

        ARoverState nextState=null;
        try {
            switch(pr.getResultType()) {
                case PollResult.MOVE:
                    getLog().info("Move complete.");
                    nextState = state.justMoved();
                    break;
                case PollResult.SCAN:
                    getLog().info("Scan complete");
                    nextState = state.justScanned(pr.getScanItems());
                    break;

                case PollResult.COLLECT:
                    getLog().info("Collect complete.");
                    nextState = state.justPickedUp();
                    break;

                case PollResult.DEPOSIT:
                    getLog().info("Deposit complete.");
                    nextState = state.justDeposited();
                    break;
            }
            if(nextState!=null)
                state = nextState;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
