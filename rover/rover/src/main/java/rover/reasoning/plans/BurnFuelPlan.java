package rover.reasoning.plans;

import rover.shared.actions.MoveRandomAction;
import rover.shared.actions.PickupAgnosticAction;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/11/2016.
 */
public class BurnFuelPlan extends APlan {

    public BurnFuelPlan() {
        super();
        actions.add(new MoveRandomAction());
        actions.add(new PickupAgnosticAction());
    }

    @Override
    public boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return true;
    }
}
