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
        if(nodes.remaining()>0){
            return OntologyConcept.there_are_unscanned_nodes;
        }
        return OntologyConcept.all_nodes_are_scanned;
    }

    public CoordinateMap getSearchNodes() {
        return nodes;
    }

    @Override
    public void coalesceWith(APercept p) {
    }
}
