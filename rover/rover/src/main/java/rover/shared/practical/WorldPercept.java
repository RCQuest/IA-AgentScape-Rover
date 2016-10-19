package rover.shared.practical;

import rover.ScanItem;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.beliefs.ResourceLocations;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.beliefs.SearchNodes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class WorldPercept extends APercept {

    private ScanItem[] itemsICanSee;
    private int roverCapacity;
    private int roverLoad;
    private CoordinateMap searchNodesRemaining;

    @Override
    public Collection<? extends ABelief> toBeliefs() {
        ArrayList<ABelief> derivedBeliefs = new ArrayList<>();

        if(itemsICanSee.length>0)
            derivedBeliefs.add(new ResourceLocations(itemsICanSee));

        derivedBeliefs.add(new RoverCapacity(roverCapacity,roverLoad));

        if(searchNodesRemaining.remaining()>0)
            derivedBeliefs.add(new SearchNodes(searchNodesRemaining));

        return derivedBeliefs;
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

    public void setRoverCapacity(int roverCapacity) {
        this.roverCapacity = roverCapacity;
    }
}
