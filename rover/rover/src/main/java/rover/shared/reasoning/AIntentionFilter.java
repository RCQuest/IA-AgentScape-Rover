package rover.shared.reasoning;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public abstract class AIntentionFilter {
    public abstract ArrayList<AIntention> filter(ArrayList<ABelief> b, ArrayList<Desire> d, ArrayList<AIntention> i);
}
