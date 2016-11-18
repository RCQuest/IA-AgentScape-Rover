package rover;

import rover.messaging.AMessage;
import rover.messaging.HelloMessage;
import rover.messaging.MessageParser;
import rover.messaging.MessagingService;
import rover.shared.practical.*;
import rover.state.ARoverState;
import rover.state.general.SearchingState;

import java.util.ArrayList;

public abstract class APracticalRover extends Rover implements IPerceiver {

    private final int CARRY_TYPE;
    private final int BASE_SPEED;
    private final int SCAN_RADIUS;
    private final int CARRY_SIZE;

    private ARoverState state;
    private RoverOffset offsetFromBase;
    private CoordinateMap scanMap;
    private ArrayList<RoverOffset> resourceMap;
    private RoverOffset resourceLocationFocus;
    private int totalNumberOfScanningAgents;
    private int id;

    public APracticalRover(int speed, int radius, int capacity, int carryType) {
        super();
        //use your username for team name
        setTeam("rc566");
        BASE_SPEED=speed;
        SCAN_RADIUS=radius;
        CARRY_SIZE=capacity;
        CARRY_TYPE=carryType;

        try {
            //set attributes for this rover
            //speed, scan range, max load
            //has to add up to <= 9
            setAttributes(BASE_SPEED, SCAN_RADIUS, CARRY_SIZE,CARRY_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    void begin() {
        //called when the world is started
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
        move(new RoverMovement(-offsetFromBase.getxOffset(),-offsetFromBase.getyOffset(),BASE_SPEED));
    }

    @Override
    void end() {
        // called when the world is stopped
        // the agent is killed after this
        getLog().info("END!");
        System.out.println("END!");
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

    public void setResourceFocus(RoverOffset resourceFocus) {
        this.resourceLocationFocus = resourceFocus;
    }

    public boolean isFocusedOnResource() {
        return resourceLocationFocus!=null;
    }

    public void moveToFocusedResource() throws Exception {
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
        System.out.println(newMessages);
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
        boolean isScanner=true;
        if(SCAN_RADIUS<1)
            isScanner=false;

        id=convertStringId();

        // only scanning rovers say hello
        if(isScanner)
            MessagingService.sendNewMessage("hello "+id);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RoverWorld.mapHeight = getWorldHeight();
        RoverWorld.mapWidth = getWorldWidth();

        ScenarioOptimisations.setScenario(getScenario());

        ArrayList<AMessage> helloMessages = getNewMessages();
        HelloMessage ownHello = new HelloMessage();
        ownHello.setId(id);
        helloMessages.add(ownHello);
        totalNumberOfScanningAgents = helloMessages.size();


        if(isScanner) {
            ScanMapFactory smf = new ScanMapFactory();
            scanMap = smf.create(getWorldWidth(), getWorldHeight(), SCAN_RADIUS, id, totalNumberOfScanningAgents, helloMessages);
        }
        offsetFromBase = new RoverOffset(0,0,getWorldWidth(),getWorldHeight());
        state = new SearchingState(this);
        resourceMap = new ArrayList<>();

    }

    private int convertStringId() {
        String id = getID();
        id = id.split("-")[1];

        return Integer.parseInt(id);
    }


    public boolean moveNowhere() {
        try {
            move(new RoverMovement(0,0,getSpeed()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getMovementSpeed(){
        return BASE_SPEED;
    }

    @Override
    public double getEnergyRemaining(){
        return getEnergy();
    }

    @Override
    public int getTypeOfResourceCarrier(){
        return CARRY_TYPE;
    }

    @Override
    public int getNumberOfScanningRovers() {
        return totalNumberOfScanningAgents;
    }


}
