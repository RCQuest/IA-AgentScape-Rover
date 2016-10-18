package rover.shared.actions;

import rover.AGeneralRover;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class PickupAction extends rover.shared.ARoverAction {
    @Override
    public boolean execute(AGeneralRover rover) {
        try {
            rover.collect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
