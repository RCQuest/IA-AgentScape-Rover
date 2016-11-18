package rover.shared.actions;

import rover.APracticalRover;
import rover.shared.practical.ARoverAction;

/**
 * Created by rachelcabot on 18/11/2016.
 */
public class PickupAgnosticAction extends ARoverAction {
    @Override
    public boolean execute(APracticalRover rover) {
        try {
            rover.collect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
