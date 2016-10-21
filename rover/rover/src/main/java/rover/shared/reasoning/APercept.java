package rover.shared.reasoning;

import rover.PollResult;
import rover.ScanItem;
import rover.shared.practical.RoverOffset;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class APercept {

    public abstract ScanItem[] getScanItems();

    public abstract RoverOffset getMyPosition();

    public abstract int getWorldWidth();

    public abstract int getWorldHeight();

    public abstract int getCurrentLoad();

    public abstract ArrayList<RoverOffset> getItemsCollected();

    public abstract ArrayList<ABelief> initialiseBeliefs();
}
