package rover.shared.practical;

import rover.PollResult;
import rover.ScanItem;
import rover.messaging.MessageParser;
import rover.messaging.MessagingService;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.beliefs.ResourceLocations;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.beliefs.RoverEnergy;
import rover.shared.reasoning.beliefs.SearchNodes;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class WorldPercept extends APercept {

    private ScanItem[] itemsICanSee;
    private RoverOffset myPosition;
    private int roverCapacity;
    private int roverLoad;
    private CoordinateMap searchNodesRemaining;
    private int previousAction;
    private double worldHeight;
    private double worldWidth;
    private boolean previousActionWasSuccessful;
    private ArrayList<Resource> resourcesJustFoundByOtherRovers;
    private ArrayList<Resource> resourcesJustCollectedByOtherRovers;
    private ArrayList<RoverOffset> nodesBeingSearchedByOtherRovers;
    private double energyRemaining;
    private int movementSpeed;
    private int typeOfResourceConcerned;

    @Override
    public ScanItem[] getScanItems() {
        return itemsICanSee;
    }

    @Override
    public RoverOffset getMyPosition() {
        return myPosition;
    }

    @Override
    public double getWorldWidth() {
        return worldWidth;
    }

    @Override
    public double getWorldHeight() {
        return worldHeight;
    }

    @Override
    public int getCurrentLoad() {
        return roverLoad;
    }

    @Override
    public ArrayList<Resource> getItemsWhollyCollected() {
        ArrayList<Resource> items = new ArrayList<>();
        if(PollResult.COLLECT==previousAction) {
            if (!previousActionWasSuccessful && roverCapacity > roverLoad) {
                Resource positionResource = new Resource(myPosition,typeOfResourceConcerned);
                items.add(positionResource);
                MessagingService.sendNewMessage(MessageParser.generateCollectedMessage(positionResource));
            }
        }
        if(resourcesJustCollectedByOtherRovers!=null){
            items.addAll(resourcesJustCollectedByOtherRovers);
        }
        return items;
    }

    @Override
    public ArrayList<RoverOffset> getItemsTouched(){
        ArrayList<RoverOffset> items = new ArrayList<>();
        if(PollResult.COLLECT==previousAction) {
            if (previousActionWasSuccessful) {
                items.add(myPosition);
//                MessagingService.sendNewMessage(MessageParser.generateTouchedMessage(myPosition));
            }
        }
//        if(resourcesJustTouchedByOtherRovers!=null){
//            items.addAll(resourcesJustTouchedByOtherRovers);
//        }
        return items;
    }

    @Override
    public double getEnergyRemaining() {
        return energyRemaining;
    }

    @Override
    public ArrayList<ABelief> initialiseBeliefs() {
        ArrayList<ABelief> derivedBeliefs = new ArrayList<>();

        derivedBeliefs.add(new ResourceLocations(itemsICanSee,myPosition, worldHeight, worldWidth, typeOfResourceConcerned));

        derivedBeliefs.add(new RoverCapacity(roverCapacity,roverLoad));

        derivedBeliefs.add(new SearchNodes(searchNodesRemaining));

        derivedBeliefs.add(new RoverEnergy(energyRemaining,movementSpeed,myPosition));

        return derivedBeliefs;
    }

    public void setRoverCapacity(int roverCapacity) {
        this.roverCapacity = roverCapacity;
    }

    public void setEnergyRemaining(double energyRemaining){
        this.energyRemaining = energyRemaining;
    }

    public void setMovementSpeed(int movementSpeed){
        this.movementSpeed = movementSpeed;
    }

    public void setItemsICanSee(ScanItem[] itemsICanSee) {
        this.itemsICanSee = itemsICanSee;
    }

    public void setSearchNodesRemaining(CoordinateMap searchNodesRemaining) {
        this.searchNodesRemaining = searchNodesRemaining;
    }

    public void setRoverLoad(int roverLoad) {
        this.roverLoad = roverLoad;
    }

    public void setWorldHeight(double worldHeight) {
        this.worldHeight = worldHeight;
    }

    public void setWorldWidth(double worldWidth) {
        this.worldWidth = worldWidth;
    }

    public void setMyPosition(RoverOffset myPosition) {
        this.myPosition = myPosition;
    }

    public void setPreviousActionWasSuccessful(boolean previousActionWasSuccessful) {
        this.previousActionWasSuccessful = previousActionWasSuccessful;
    }

    public ArrayList<Resource> getResourcesJustFoundByOtherRovers() {
        return resourcesJustFoundByOtherRovers;
    }

    @Override
    public ArrayList<RoverOffset> getNodesBeingSearchedByOtherRovers() {
        return nodesBeingSearchedByOtherRovers;
    }

    public void addResourcesJustFoundByOtherRovers(ArrayList<Resource> resourcesJustFoundByOtherRovers) {
        if(this.resourcesJustFoundByOtherRovers==null)
            this.resourcesJustFoundByOtherRovers = new ArrayList<>();
        this.resourcesJustFoundByOtherRovers.addAll(resourcesJustFoundByOtherRovers);
    }

    public void addItemsCollected(Resource collected) {
        if(this.resourcesJustCollectedByOtherRovers==null)
            this.resourcesJustCollectedByOtherRovers = new ArrayList<>();
        this.resourcesJustCollectedByOtherRovers.add(collected);
    }

    public void addNodeBeingSearchedByOtherRover(RoverOffset item) {
        if(this.nodesBeingSearchedByOtherRovers==null)
            this.nodesBeingSearchedByOtherRovers = new ArrayList<>();
        this.nodesBeingSearchedByOtherRovers.add(item);
    }

    public void setPreviousAction(int previousAction) {
        this.previousAction = previousAction;
    }

    public void setTypeOfResourceConcerned(int typeOfResourceConcerned) {
        this.typeOfResourceConcerned = typeOfResourceConcerned;
    }
}
