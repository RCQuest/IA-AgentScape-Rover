package rover.messaging;

import rover.shared.practical.RoverOffset;
import rover.shared.practical.WorldPercept;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class CollectedMessage extends ASingleParameterMessage {

    @Override
    public void modifyPercept(WorldPercept percept) {
        percept.addItemsCollected(item);
    }
}
