package rover.reasoning.simple;

import rover.reasoning.plans.DespositAtBasePlan;
import rover.reasoning.plans.DoNothingPlan;
import rover.reasoning.plans.RetrieveResourcePlan;
import rover.reasoning.plans.SearchPlan;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.*;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;
import rover.shared.reasoning.beliefs.ResourceLocations;
import rover.shared.reasoning.beliefs.SearchNodes;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class SimplePlanFactory extends APlanFactory {

    @Override
    public APlan createPlan(ArrayList<ABelief> b, ArrayList<AIntention> i) {

        if(OntologyUtils.c(b, OntologyConcept.there_are_found_unobtained_resources)
                &&(OntologyUtils.c(b, OntologyConcept.carrying_something)
                ||OntologyUtils.c(b, OntologyConcept.not_carrying_anything))
                &&OntologyUtils.c(i, OntologyConcept.obtain_resources))
            return new RetrieveResourcePlan(
                    (ResourceLocations)OntologyUtils.getBelief(b,OntologyConcept.there_are_found_unobtained_resources));

        if(OntologyUtils.c(b, OntologyConcept.at_capacity)
                &&OntologyUtils.c(i, OntologyConcept.deposit_resources_at_base))
            return new DespositAtBasePlan(
                    (RoverCapacity)OntologyUtils.getBelief(b,OntologyConcept.at_capacity));

        if(OntologyUtils.c(b,OntologyConcept.there_are_unscanned_nodes)
                &&OntologyUtils.c(i, OntologyConcept.search))
            return new SearchPlan(
                    (SearchNodes)OntologyUtils.getBelief(b,OntologyConcept.there_are_unscanned_nodes));

        if(OntologyUtils.c(b,OntologyConcept.all_nodes_are_scanned)
            &&OntologyUtils.c(b, OntologyConcept.carrying_something)
                &&OntologyUtils.c(i, OntologyConcept.deposit_resources_at_base))
            return new DespositAtBasePlan(
                    (RoverCapacity)OntologyUtils.getBelief(b,OntologyConcept.carrying_something));


        return new DoNothingPlan();

    }


}
