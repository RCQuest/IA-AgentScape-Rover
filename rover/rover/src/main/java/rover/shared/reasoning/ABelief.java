package rover.shared.reasoning;

import rover.shared.reasoning.ontology.IOntologicalConcept;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class ABelief implements IOntologicalConcept {

    public abstract void coalesceWith(APercept p);
}
