package rover.shared.actions;

import rover.AGeneralRover;
import rover.shared.reasoning.plan.ResourceLocations;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class MoveTowardsAction extends rover.shared.ARoverAction {

    private ResourceLocations resourceLocationBelief;

    public MoveTowardsAction(ResourceLocations resourceLocationBelief) {
        this.resourceLocationBelief = resourceLocationBelief;
    }

    @Override
    public boolean execute(AGeneralRover rover) {
        return rover.moveToClosestResource(resourceLocationBelief.getLocations());
    }
}
