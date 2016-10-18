package rover.shared;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class AIntention {
    public abstract boolean fulfilled(ArrayList<ABelief> b);

    public abstract boolean possible(ArrayList<ABelief> b);

    public abstract boolean shouldBeReconsidered(ArrayList<ABelief> b, ArrayList<AIntention> i);
}
