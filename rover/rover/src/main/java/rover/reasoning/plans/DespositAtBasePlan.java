package rover.reasoning.plans;

import rover.shared.actions.DepositAction;
import rover.shared.actions.MoveBackToBaseAction;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class DespositAtBasePlan extends APlan {

    public DespositAtBasePlan(RoverCapacity capacity){
        actions.add(new MoveBackToBaseAction());
        System.out.println("formulating deposit plan...");
        for (int i = 0; i < capacity.getCurrentLoad(); i++) {
            System.out.println("adding deposit action...");
            actions.add(new DepositAction());
        }
    }
    @Override
    public boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b) {
        return true;
    }
}
