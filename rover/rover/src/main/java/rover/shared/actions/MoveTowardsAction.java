package rover.shared.actions;

import rover.APracticalRover;
import rover.shared.practical.ARoverAction;
import rover.shared.practical.CoordinateMap;
import rover.shared.practical.RoverOffset;
import rover.shared.reasoning.beliefs.ResourceLocations;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class MoveTowardsAction extends ARoverAction {

    private CoordinateMap searchNodes;
    private ResourceLocations resourceLocationBelief;
    private RoverOffset node;

    public MoveTowardsAction(ResourceLocations resourceLocationBelief) {
        this.resourceLocationBelief = resourceLocationBelief;
    }

    public MoveTowardsAction(RoverOffset node) {
        this.node = node;
    }

    public MoveTowardsAction(CoordinateMap searchNodes) {
        this.searchNodes = searchNodes;
    }

    @Override
    public boolean execute(APracticalRover rover) {
        if(searchNodes!=null)
            return rover.moveTo(
                searchNodes.popCoordinateOfNextClosestNode(
                    rover.getOffsetFromBase()));

        if(node!=null)
            return rover.moveTo(node);

        if(resourceLocationBelief!=null)
            return rover.moveToClosestResource(resourceLocationBelief.getLocations());
        return false;
    }
}
