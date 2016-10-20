package rover.shared.reasoning.beliefs;

import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class RoverCapacity extends ABelief {
    private int maxCapacity;
    private int currentLoad;

    public RoverCapacity(int maxCapacity, int currentCapacity){

        this.maxCapacity = maxCapacity;
        this.currentLoad = currentCapacity;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        if(maxCapacity<= currentLoad)
            return OntologyConcept.at_capacity;
        else
            return OntologyConcept.not_at_capacity;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    @Override
    public boolean isNullifiedBy(APercept p) {
        return false;
    }

    @Override
    public void coalesceWith(APercept p) {
        currentLoad = p.getCurrentLoad();
    }
}
