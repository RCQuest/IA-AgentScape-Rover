package rover.shared.reasoning.beliefs;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

/**
 * Created by rachelcabot on 18/11/2016.
 */
public class PossibilityOfSuccess extends ABelief {
    private int scannersLeftAlive;

    public PossibilityOfSuccess(){
        scannersLeftAlive=0;
    }

    @Override
    public void coalesceWith(APercept p) {
        scannersLeftAlive = p.getNumberOfScanningRovers();
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        if(scannersLeftAlive>0){
            return OntologyConcept.scenario_is_possible_to_complete;
        } else {
            return OntologyConcept.scenario_is_impossible_to_complete;
        }
    }
}
