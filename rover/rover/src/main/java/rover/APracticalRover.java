package rover;

import rover.messaging.AMessage;
import rover.messaging.MessageParser;
import rover.messaging.MessagingService;
import rover.shared.practical.*;
import rover.state.ARoverState;
import rover.state.general.SearchingState;

import java.util.ArrayList;

public abstract class APracticalRover extends Rover implements IPerceiver {

    private final int BASE_SPEED;
    private final int SCAN_RADIUS;
    private final int CARRY_SIZE;

    private ARoverState state;
    private RoverOffset offsetFromBase;
    private CoordinateMap scanMap;
    private ArrayList<RoverOffset> resourceMap;
    private RoverOffset resourceLocationFocus;
    private int numberOfOtherAgents;
    private int id;

    public APracticalRover(int speed, int radius, int capacity) {
        super();
        //use your username for team name
        setTeam("rc566");
        BASE_SPEED=speed;
        SCAN_RADIUS=radius;
        CARRY_SIZE=capacity;

        try {
            //set attributes for this rover
            //speed, scan range, max load
            //has to add up to <= 9
            setAttributes(BASE_SPEED, SCAN_RADIUS, CARRY_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    void begin() {
        //called when the world is started
        getLog().info("BEGIN!");
        setUpPracticalAttributes();

        try {
            //move somewhere initially

            searchMovement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public RoverOffset noteResourceLocation(ScanItem item){
        RoverOffset offset = new RoverOffset(
                item.getxOffset(),
                item.getyOffset(),
                getWorldWidth(),
                getWorldHeight());
        offset.addOffset(offsetFromBase);
        resourceMap.add(offset);
        return offset;
    }

    public void removeCurrentResourceLocation(){
        if(resourceLocationFocus!=null)
            resourceMap.remove(resourceLocationFocus);
        resourceLocationFocus = null;
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

    public boolean searchMovement() {
        try {
            RoverOffset searchOffset = scanMap.popOffsetToNextClosestNode(offsetFromBase);
            if (searchOffset != null) {
                move(new RoverMovement(searchOffset.getxOffset(), searchOffset.getyOffset(), BASE_SPEED));
                System.out.println("magnitude to next closest: "+searchOffset.magnitude());
            } else {
                move(new RoverMovement(1, 1, 1));
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void move(RoverMovement movement) throws Exception {
        move(movement.xOffset,movement.yOffset,movement.speed);
        offsetFromBase.addOffset(movement);
    }

    public boolean loadIsFull(){
        return getCurrentLoad()>=CARRY_SIZE;
    }

    public boolean loadIsEmpty(){
        return getCurrentLoad()==0;
    }

    public void moveBackToBase() throws Exception {
        System.out.println("Moving back to base.");
        move(new RoverMovement(-offsetFromBase.getxOffset(),-offsetFromBase.getyOffset(),BASE_SPEED));
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
                    System.out.println("Move complete.");
                    nextState = state.justMoved();
                    break;
                case PollResult.SCAN:
                    getLog().info("Scan complete");
                    System.out.println("Scan complete.");
                    nextState = state.justScanned(pr.getScanItems());
                    break;

                case PollResult.COLLECT:
                    getLog().info("Collect complete.");
                    System.out.println("Collect complete.");
                    nextState = state.justPickedUp();
                    break;

                case PollResult.DEPOSIT:
                    getLog().info("Deposit complete.");
                    System.out.println("Deposit complete.");
                    nextState = state.justDeposited();
                    break;
            }
            if(nextState!=null)
                state = nextState;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setResourceFocus(RoverOffset resourceFocus) {
        this.resourceLocationFocus = resourceFocus;
    }

    public boolean isFocusedOnResource() {
        return resourceLocationFocus!=null;
    }

    public void moveToFocusedResource() throws Exception {
        System.out.println("Moving to focused resource.");
        move(new RoverMovement(
                resourceLocationFocus.getxOffset()-offsetFromBase.getxOffset(),
                resourceLocationFocus.getyOffset()-offsetFromBase.getyOffset(),
                getSpeed()));
    }

    public boolean hasResourceBacklog() {
        return !resourceMap.isEmpty();
    }

    public void focusNextResource() {
        resourceLocationFocus=resourceMap.get(0);
    }

    public boolean moveToClosestResource(ArrayList<RoverOffset> resourceLocations) {
        RoverOffset r = resourceLocations.get(0);
        return moveTo(r);
    }

    public boolean moveTo(RoverOffset node) {
        try {
            move(new RoverMovement(
                    node.getxOffset()-offsetFromBase.getxOffset(),
                    node.getyOffset()-offsetFromBase.getyOffset(),
                    getSpeed()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean previousActionWasSuccessful(){ return true; }

    @Override
    public ArrayList<AMessage> getNewMessages() {
        retrieveMessages();
        ArrayList<String> newMessages = new ArrayList<>(this.messages);
        this.messages.clear();
        return MessageParser.parse(newMessages);
    }

    @Override
    public int getCapacity(){
        return CARRY_SIZE;
    }

    public RoverOffset getOffsetFromBase() {
        return offsetFromBase;
    }

    public CoordinateMap getSearchNodes(){
        return scanMap;
    }

    public RoverOffset getPosition(){
        return offsetFromBase;
    }

    protected void setUpPracticalAttributes() {
        MessagingService.sendNewMessage("hello");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // assuming all hellos...
        numberOfOtherAgents=getNewMessages().size();
        id=convertStringId();
        RoverWorld.mapHeight = getWorldHeight();
        RoverWorld.mapWidth = getWorldWidth();
        scanMap = new CoordinateMap(getWorldWidth(),getWorldHeight(),SCAN_RADIUS,id,numberOfOtherAgents);
        offsetFromBase = new RoverOffset(0,0,getWorldWidth(),getWorldHeight());
        state = new SearchingState(this);
        resourceMap = new ArrayList<>();
    }

    private int convertStringId() {
        String id = getID();
        System.out.println(id);
//        id = id.split("rc566-")[0];
        return Integer.parseInt(id);
    }


}
