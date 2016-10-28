package rover.messaging;

import rover.shared.practical.RoverOffset;
import rover.shared.practical.WorldPercept;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class CollectedMessage extends AMessage {

    private RoverOffset collected;
    @Override
    protected void extractMessageParameters(String[] originalMessageTokens) {
        double x = Double.parseDouble(originalMessageTokens[1]);
        double y = Double.parseDouble(originalMessageTokens[2]);
        collected = new RoverOffset(x,y);
    }

    @Override
    public void modifyPercept(WorldPercept percept) {
        percept.addItemsCollected(collected);
    }
}
