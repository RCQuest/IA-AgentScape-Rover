package rover.shared.actions;

import rover.APracticalRover;
import rover.shared.practical.ARoverAction;
import rover.shared.practical.RoverOffset;
import rover.shared.reasoning.plan.ResourceLocations;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class MoveTowardsAction extends ARoverAction {

    private ResourceLocations resourceLocationBelief;
    private RoverOffset node;

    public MoveTowardsAction(ResourceLocations resourceLocationBelief) {
        this.resourceLocationBelief = resourceLocationBelief;
    }

    public MoveTowardsAction(RoverOffset node) {
        this.node = node;
    }

    @Override
    public boolean execute(APracticalRover rover) {
        if(node!=null)
            return rover.moveTo(node);
        if(resourceLocationBelief!=null)
            return rover.moveToClosestResource(resourceLocationBelief.getLocations());
        return false;
    }
}
