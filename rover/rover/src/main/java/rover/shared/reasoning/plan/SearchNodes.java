package rover.shared.reasoning.plan;

import rover.shared.practical.RoverOffset;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class SearchNodes extends ABelief {
    private ArrayList<RoverOffset> nodes;

    public SearchNodes(ArrayList<RoverOffset> nodes){
        this.nodes = nodes;
    }

    @Override
    public boolean agreesWith(APercept p) {
        return false;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return null;
    }

    public ArrayList<RoverOffset> getNodes() {
        return nodes;
    }

    public RoverOffset getClosestNode() {
        return null;
    }
}
