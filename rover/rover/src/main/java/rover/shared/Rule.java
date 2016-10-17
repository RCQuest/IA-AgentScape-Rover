package rover.shared;

import rover.PollResult;
import rover.shared.Percept;
import rover.shared.RoverAction;
import rover.state.ARoverState;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public class Rule {
    private ARoverState internalState;
    private PollResult previousAction;
    private Percept observation;
    private RoverAction action;

}
