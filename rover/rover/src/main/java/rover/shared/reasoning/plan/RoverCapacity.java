package rover.shared.reasoning.plan;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class RoverCapacity extends ABelief {
    private int maxCapacity;
    private int currentCapacity;

    public RoverCapacity(int maxCapacity, int currentCapacity){

        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    @Override
    public boolean agreesWith(APercept p) {
        return false;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        if(maxCapacity<= currentCapacity)
            return OntologyConcept.at_capacity;
        else
            return OntologyConcept.not_at_capacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }
}
