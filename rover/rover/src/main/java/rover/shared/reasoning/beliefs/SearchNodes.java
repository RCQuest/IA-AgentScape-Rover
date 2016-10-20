package rover.shared.reasoning.beliefs;

import rover.shared.practical.CoordinateMap;
import rover.shared.practical.RoverOffset;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class SearchNodes extends ABelief {
    private CoordinateMap nodes;

    public SearchNodes(CoordinateMap nodes){
        this.nodes = nodes;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return OntologyConcept.there_are_found_unobtained_resources;
    }

    public CoordinateMap getSearchNodes() {
        return nodes;
    }

    @Override
    public boolean isNullifiedBy(APercept p) {
        return nodes.remaining()<=0;
    }

    @Override
    public void coalesceWith(APercept p) {
        return;
    }
}
