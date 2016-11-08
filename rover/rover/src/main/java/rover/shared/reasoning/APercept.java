package rover.shared.reasoning;

import rover.ScanItem;
import rover.shared.practical.Resource;
import rover.shared.practical.RoverOffset;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public abstract class APercept {

    public abstract ScanItem[] getScanItems();

    public abstract RoverOffset getMyPosition();

    public abstract double getWorldWidth();

    public abstract double getWorldHeight();

    public abstract int getCurrentLoad();

    public abstract ArrayList<Resource> getItemsWhollyCollected();

    public abstract ArrayList<ABelief> initialiseBeliefs();

    public abstract ArrayList<Resource> getResourcesJustFoundByOtherRovers();

    public abstract ArrayList<RoverOffset> getNodesBeingSearchedByOtherRovers();

    public abstract ArrayList<RoverOffset> getItemsTouched();

    public abstract double getEnergyRemaining();
}
