package rover.state;

import rover.ScanItem;

/**
 * Created by rachelcabot on 13/10/2016.
 */
public abstract class ARoverState {
    private ARoverState nextState;

    public abstract ARoverState justMoved() throws Exception;
    public abstract ARoverState justScanned(ScanItem[] items) throws Exception;
    public abstract ARoverState justPickedUp() throws Exception;
    public abstract ARoverState justDeposited() throws Exception;
}
