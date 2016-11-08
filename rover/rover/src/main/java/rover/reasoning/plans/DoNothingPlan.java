package rover.reasoning.plans;

import rover.shared.actions.MoveBackToBaseAction;
import rover.shared.actions.MoveNowhereAction;
import rover.shared.actions.MoveTowardsAction;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 21/10/2016.
 */
public class DoNothingPlan extends APlan {

    public DoNothingPlan(){
        super();

        actions.add(new MoveNowhereAction());
    }

    @Override
    public boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return true;
    }
}
