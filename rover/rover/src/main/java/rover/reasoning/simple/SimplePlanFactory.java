package rover.reasoning.simple;

import rover.reasoning.plans.RetrieveResourcePlan;
import rover.shared.reasoning.*;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimplePlanFactory extends APlanFactory {

    @Override
    public APlan createPlan(ArrayList<ABelief> b, ArrayList<AIntention> i) {
        if(OntologyUtils.containsOntologicalConcept(b, OntologyConcept.there_are_found_unobtained_resources)&&
                OntologyUtils.containsOntologicalConcept(i,OntologyConcept.obtain_resources))
            return new RetrieveResourcePlan(OntologyUtils.getBelief(b,OntologyConcept.there_are_found_unobtained_resources));
        return null;
    }


}
