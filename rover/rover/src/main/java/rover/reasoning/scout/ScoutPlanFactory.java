package rover.reasoning.scout;

import rover.reasoning.plans.DespositAtBasePlan;
import rover.reasoning.plans.DoNothingPlan;
import rover.reasoning.plans.RetrieveResourcePlan;
import rover.reasoning.plans.SearchPlan;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.AIntention;
import rover.shared.reasoning.APlan;
import rover.shared.reasoning.APlanFactory;
import rover.shared.reasoning.beliefs.ResourceLocations;
import rover.shared.reasoning.beliefs.RoverCapacity;
import rover.shared.reasoning.beliefs.SearchNodes;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.reasoning.ontology.OntologyUtils;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 05/11/2016.
 */
public class ScoutPlanFactory extends APlanFactory {
    @Override
    public APlan createPlan(ArrayList<ABelief> b, ArrayList<AIntention> i) {

        if(OntologyUtils.c(b,OntologyConcept.there_are_unscanned_nodes))
            return new SearchPlan(
                    (SearchNodes)OntologyUtils.getBelief(b,OntologyConcept.there_are_unscanned_nodes));


        return new DoNothingPlan();

    }
}
