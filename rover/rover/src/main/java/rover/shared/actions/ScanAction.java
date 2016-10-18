package rover.shared.actions;

import rover.APracticalRover;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public class ScanAction extends rover.shared.practical.ARoverAction {
    @Override
    public boolean execute(APracticalRover rover) {
        try {
            rover.scan(rover.getScanRadius());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
