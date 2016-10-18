package rover.shared.reasoning;

import rover.PollResult;

import java.util.Collection;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class APercept {
    public APercept(PollResult pr) {

    }

    public abstract Collection<? extends ABelief> toBeliefs();
}
