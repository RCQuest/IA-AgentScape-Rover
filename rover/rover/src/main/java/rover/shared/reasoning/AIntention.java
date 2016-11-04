package rover.shared.reasoning;

import rover.shared.reasoning.ontology.IOntologicalConcept;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class AIntention implements IOntologicalConcept {
    public boolean fulfilled(ArrayList<ABelief> b){
        return false;
    }

    public boolean possible(ArrayList<ABelief> b){
        return true;
    }

    public boolean shouldBeReconsidered(ArrayList<ABelief> b, ArrayList<AIntention> i){
        return false;
    }
}
