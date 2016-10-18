package rover.shared.reasoning.plan;

import rover.shared.RoverOffset;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class ResourceLocations extends ABelief {
    private ArrayList<RoverOffset> offsetsFromBase;

    @Override
    public boolean agreesWith(APercept p) {
        return false;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return OntologyConcept.there_are_found_unobtained_resources;
    }

    public ArrayList<RoverOffset> getLocations() {
        return offsetsFromBase;
    }
}
