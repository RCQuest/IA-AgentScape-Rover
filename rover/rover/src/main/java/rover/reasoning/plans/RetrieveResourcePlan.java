package rover.reasoning.plans;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class RetrieveResourcePlan extends APlan {
    public RetrieveResourcePlan(ABelief belief) {
        super();
    }

    @Override
    public boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return false;
    }
}
