package rover.shared;

import rover.PollResult;
import rover.shared.reasoning.APercept;
import rover.state.ARoverState;

/**
 * Created by rachelcabot on 17/10/2016.
 */
public class Rule {
    private ARoverState internalState;
    private PollResult previousAction;
    private APercept observation;
    private ARoverAction action;

}
