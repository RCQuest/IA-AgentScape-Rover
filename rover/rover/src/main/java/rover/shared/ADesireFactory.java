package rover.shared;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public abstract class ADesireFactory {
    public abstract ArrayList<Desire> generateDesires(ArrayList<ABelief> b, ArrayList<AIntention> i);
}
