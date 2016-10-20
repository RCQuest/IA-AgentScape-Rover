package rover.shared.practical;

import rover.ScanItem;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.beliefs.ResourceLocations;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.beliefs.SearchNodes;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class WorldPercept extends APercept {

    private ScanItem[] itemsICanSee;
    private RoverOffset myPosition;
    private int roverCapacity;
    private int roverLoad;
    private CoordinateMap searchNodesRemaining;
    private int worldHeight;
    private int worldWidth;

    @Override
    public Collection<? extends ABelief> getAnyNewBeliefs(ArrayList<ABelief> b) {
        //TODO:edit me to not just return all beliefs
        ArrayList<ABelief> derivedBeliefs = new ArrayList<>();

        if(itemsICanSee.length>0
            && !OntologyUtils.c(b, OntologyConcept.there_are_found_unobtained_resources))
            derivedBeliefs.add(new ResourceLocations(itemsICanSee,myPosition, worldHeight, worldWidth));

        if(!(OntologyUtils.c(b, OntologyConcept.at_capacity)||OntologyUtils.c(b, OntologyConcept.not_at_capacity)))
            derivedBeliefs.add(new RoverCapacity(roverCapacity,roverLoad));

        if(searchNodesRemaining.remaining()>0&&!OntologyUtils.c(b, OntologyConcept.there_are_unscanned_nodes))
            derivedBeliefs.add(new SearchNodes(searchNodesRemaining));

        return derivedBeliefs;
    }

    @Override
    public ScanItem[] getScanItems() {
        return new ScanItem[0];
    }

    @Override
    public RoverOffset getMyPosition() {
        return myPosition;
    }

    @Override
    public int getWorldWidth() {
        return worldWidth;
    }

    @Override
    public int getWorldHeight() {
        return worldHeight;
    }

    @Override
    public int getCurrentLoad() {
        return roverLoad;
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

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }

    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public void setMyPosition(RoverOffset myPosition) {
        this.myPosition = myPosition;
    }
}
