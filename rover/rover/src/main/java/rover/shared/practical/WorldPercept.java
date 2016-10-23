package rover.shared.practical;

import rover.PollResult;
import rover.ScanItem;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.beliefs.ResourceLocations;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.beliefs.SearchNodes;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class WorldPercept extends APercept {

    private ScanItem[] itemsICanSee;
    private int previousAction;
    private RoverOffset myPosition;
    private int roverCapacity;
    private int roverLoad;
    private CoordinateMap searchNodesRemaining;
    private double worldHeight;
    private double worldWidth;
    private boolean previousActionWasSuccessful;

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
    public ArrayList<RoverOffset> getItemsCollected() {
        ArrayList<RoverOffset> items = new ArrayList<>();
        if(previousAction== PollResult.COLLECT&&!previousActionWasSuccessful&&roverCapacity>roverLoad){
            items.add(myPosition);
        }
        return items;
    }

    @Override
    public ArrayList<ABelief> initialiseBeliefs() {
        ArrayList<ABelief> derivedBeliefs = new ArrayList<>();

        derivedBeliefs.add(new ResourceLocations(itemsICanSee,myPosition, worldHeight, worldWidth));

        derivedBeliefs.add(new RoverCapacity(roverCapacity,roverLoad));

        derivedBeliefs.add(new SearchNodes(searchNodesRemaining));

        return derivedBeliefs;
    }

    public void setRoverCapacity(int roverCapacity) {
        this.roverCapacity = roverCapacity;
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

    public void setPreviousAction(int previousAction) {
        this.previousAction = previousAction;
    }
}
