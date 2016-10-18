package rover.shared.reasoning.ontology;

import rover.shared.reasoning.ABelief;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class OntologyUtils {
    public static boolean containsOntologicalConcept(ArrayList<? extends IOntologicalConcept> oc, OntologyConcept identifier){
        for (IOntologicalConcept concept : oc) {
            if(identifier==concept.getOntologicalOrdinal())
                return true;
        }
        return false;
    }

    public static ABelief getBelief(ArrayList<ABelief> b, OntologyConcept concept){
        for (ABelief belief : b) {
            if(concept==belief.getOntologicalOrdinal())
                return belief;
        }
        return null;
    }
}
