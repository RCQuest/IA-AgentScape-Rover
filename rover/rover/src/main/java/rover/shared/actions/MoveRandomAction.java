package rover.shared.actions;

import rover.APracticalRover;
import rover.shared.practical.RoverOffset;
import rover.shared.practical.RoverWorld;

/**
 * Created by rachelcabot on 18/11/2016.
 */
public class MoveRandomAction extends rover.shared.practical.ARoverAction {
    @Override
    public boolean execute(APracticalRover rover) {
        return rover.moveTo(new RoverOffset(Math.random()*RoverWorld.mapWidth,Math.random()*RoverWorld.mapHeight));
    }
}
