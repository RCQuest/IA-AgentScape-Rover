package rover.reasoning.plans;

import rover.shared.actions.MoveTowardsAction;
import rover.shared.actions.ScanAction;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;
import rover.shared.reasoning.beliefs.SearchNodes;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class SearchPlan extends APlan {
    public SearchPlan(SearchNodes belief) {
        super();
        actions.add(new MoveTowardsAction(belief.getSearchNodes()));
        actions.add(new ScanAction(/*callback to remove the node form the scanlist*/));
    }

    @Override
    public boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return true;
    }
}
