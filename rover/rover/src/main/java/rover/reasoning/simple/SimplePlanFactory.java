package rover.reasoning.simple;

import rover.reasoning.plans.DespositAtBasePlan;
import rover.reasoning.plans.RetrieveResourcePlan;
import rover.reasoning.plans.SearchPlan;
import rover.shared.reasoning.plan.RoverCapacity;
import rover.shared.reasoning.*;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;
import rover.shared.reasoning.plan.ResourceLocations;
import rover.shared.reasoning.plan.SearchNodes;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimplePlanFactory extends APlanFactory {

    @Override
    public APlan createPlan(ArrayList<ABelief> b, ArrayList<AIntention> i) {
        if(OntologyUtils.c(b, OntologyConcept.there_are_found_unobtained_resources)&&
            OntologyUtils.c(i,OntologyConcept.obtain_resources)&&
            !OntologyUtils.c(b, OntologyConcept.at_capacity))
            return new RetrieveResourcePlan(
                    (ResourceLocations)OntologyUtils.getBelief(b,OntologyConcept.there_are_found_unobtained_resources));

        if(OntologyUtils.c(b, OntologyConcept.at_capacity)&&
            OntologyUtils.c(i,OntologyConcept.deposit_resources_at_base))
            return new DespositAtBasePlan(
                    (RoverCapacity)OntologyUtils.getBelief(b,OntologyConcept.at_capacity));

        if(!OntologyUtils.c(b,OntologyConcept.there_are_found_unobtained_resources)&&
            OntologyUtils.c(b,OntologyConcept.there_are_unscanned_nodes)&&
            OntologyUtils.c(i,OntologyConcept.search))
            return new SearchPlan(
                    (SearchNodes)OntologyUtils.getBelief(b,OntologyConcept.there_are_unscanned_nodes));

        return null;

    }


}