package rover.shared;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class APlan {
    private Queue<ARoverAction> actions;

    public ARoverAction popStep() {
        return actions.poll();
    }

    public boolean empty() {
        return actions.isEmpty();
    }

    public abstract boolean isSound(ArrayList<AIntention> i, ArrayList<ABelief> b);
}
