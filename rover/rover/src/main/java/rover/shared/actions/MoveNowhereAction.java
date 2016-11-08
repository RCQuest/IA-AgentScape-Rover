package rover.shared.actions;

import rover.APracticalRover;

/**
 * Created by rachelcabot on 08/11/2016.
 */
public class MoveNowhereAction extends rover.shared.practical.ARoverAction {
    @Override
    public boolean execute(APracticalRover rover) {
        return rover.moveNowhere();
    }
}
