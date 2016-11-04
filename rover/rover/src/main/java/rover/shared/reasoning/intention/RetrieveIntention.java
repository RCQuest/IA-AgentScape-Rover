package rover.shared.reasoning.intention;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 20/10/2016.
 */
public class RetrieveIntention extends rover.shared.reasoning.AIntention {
//    @Override
//    public boolean fulfilled(ArrayList<ABelief> b) {
//        return OntologyUtils.c(b,OntologyConcept.not_carrying_anything);
//    }
//
//    @Override
//    public boolean possible(ArrayList<ABelief> b) {
//        return true;
//    }
//
//    @Override
//    public boolean shouldBeReconsidered(ArrayList<ABelief> b, ArrayList<AIntention> i) {
//        return false;
//    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return OntologyConcept.deposit_resources_at_base;
    }
}
