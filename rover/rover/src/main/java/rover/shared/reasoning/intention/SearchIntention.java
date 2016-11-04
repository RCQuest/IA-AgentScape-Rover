package rover.shared.reasoning.intention;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 20/10/2016.
 */
public class SearchIntention extends rover.shared.reasoning.AIntention {
//    @Override
//    public boolean fulfilled(ArrayList<ABelief> b) {
//        return !OntologyUtils.c(b,OntologyConcept.there_are_unscanned_nodes);
//    }
//
//    @Override
//    public boolean possible(ArrayList<ABelief> b) {
//        return OntologyUtils.c(b,OntologyConcept.there_are_unscanned_nodes);
//    }
//
//    @Override
//    public boolean shouldBeReconsidered(ArrayList<ABelief> b, ArrayList<AIntention> i) {
//        return !OntologyUtils.c(b,OntologyConcept.there_are_unscanned_nodes);
//    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return OntologyConcept.search;
    }
}
