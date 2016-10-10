package rover;

public class GeneralRover extends Rover {

    private static final int BASE_SPEED = 4;

    private enum RoverState{
        SEARCHING,
        RETRIEVING,
        RETURNING
    }

    private RoverState state;
    private final int SCAN_RADIUS = 4;
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
            state = RoverState.SEARCHING;
            searchMovement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void searchMovement() throws Exception{
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

        switch(pr.getResultType()) {
            case PollResult.MOVE:
                //move finished
                getLog().info("Move complete.");

                //now scan
                try {
                    switch (state) {
                        case SEARCHING:
                            getLog().info("Scanning...");
                            scan(SCAN_RADIUS);
                            break;
                        case RETRIEVING:
                            state=RoverState.RETURNING;
                            collect();
                            break;
                        case RETURNING:
                            state=RoverState.SEARCHING;
                            deposit();
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case PollResult.SCAN:
                getLog().info("Scan complete");

                for(ScanItem item : pr.getScanItems()) {
                    if(item.getItemType() == ScanItem.RESOURCE) {
                        getLog().info("Resource found at: " + item.getxOffset() + ", " + item.getyOffset());
                        state=RoverState.RETRIEVING;
                    }
                }

                try {
                    getLog().info("Moving...");
                    switch (state) {
                        case SEARCHING:
                            searchMovement();
                            break;
                        case RETRIEVING:
                            ScanItem item = pr.getScanItems()[0];
                            move(new RoverMovement(item.getxOffset(),item.getyOffset(),BASE_SPEED));
                            break;
                        case RETURNING:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PollResult.COLLECT:
                getLog().info("Collect complete.");
                try {
                    state=RoverState.RETURNING;
                    move(new RoverMovement(offsetFromBase,BASE_SPEED));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case PollResult.DEPOSIT:
                getLog().info("Deposit complete.");
                try {
                    state=RoverState.SEARCHING;
                    searchMovement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

}
