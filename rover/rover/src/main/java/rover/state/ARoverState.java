package rover.state;

/**
 * Created by rachelcabot on 13/10/2016.
 */
public abstract class ARoverState {
    private ARoverState nextState;

    public abstract ARoverState executeMove();
    public abstract ARoverState executeScan();
    public abstract ARoverState executePickup();
    public abstract ARoverState executeDeposit();
}
