package rover.reasoning.plans;

import rover.shared.actions.MoveTowardsAction;
import rover.shared.actions.PickupAction;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;
import rover.shared.reasoning.plan.ResourceLocations;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class RetrieveResourcePlan extends APlan {

    public RetrieveResourcePlan(ResourceLocations resourceLocationBelief) {
        super();
        actions.add(new MoveTowardsAction(resourceLocationBelief));
        actions.add(new PickupAction());
    }

    @Override
    public boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return true;
    }
}
