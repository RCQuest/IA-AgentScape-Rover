package rover.shared.reasoning;

import rover.PollResult;
import rover.shared.practical.IPerceiver;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public abstract class APerceptFactory {
    public abstract APercept create(PollResult pr,IPerceiver rover);
}
