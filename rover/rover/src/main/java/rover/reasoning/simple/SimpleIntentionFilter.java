package rover.reasoning.simple;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.Desire;
import rover.shared.reasoning.intention.ObtainIntention;
import rover.shared.reasoning.intention.RetrieveIntention;
import rover.shared.reasoning.intention.SearchIntention;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimpleIntentionFilter extends rover.shared.reasoning.AIntentionFilter {
    @Override
    public ArrayList<AIntention> filter(ArrayList<ABelief> b, ArrayList<Desire> d, ArrayList<AIntention> i) {
        if(i==null){
            i = new ArrayList<>();
            i.add(new SearchIntention());
            i.add(new ObtainIntention());
            i.add(new RetrieveIntention());
        }
        if(!OntologyUtils.c(i, OntologyConcept.search)){
            if(OntologyUtils.c(b, OntologyConcept.there_are_found_unobtained_resources))
                i.add(new SearchIntention());
        }
        if(!OntologyUtils.c(i, OntologyConcept.obtain_resources)){
            if(OntologyUtils.c(b, OntologyConcept.not_at_capacity))
                i.add(new ObtainIntention());
        }
        if(!OntologyUtils.c(i, OntologyConcept.deposit_resources_at_base)){
            if(OntologyUtils.c(b, OntologyConcept.at_capacity))
                i.add(new RetrieveIntention());
        }
        return i;
    }
}
