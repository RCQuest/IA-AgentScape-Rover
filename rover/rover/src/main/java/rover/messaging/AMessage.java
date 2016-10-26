package rover.messaging;

import rover.shared.practical.IPerceiver;
import rover.shared.practical.WorldPercept;

/**
 * Created by rachelcabot on 24/10/2016.
 */
public abstract class AMessage {

    public void setMessageTokens(String[] messageTokens) {
        extractMessageParameters(messageTokens);
    }

    protected abstract void extractMessageParameters(String[] originalMessageTokens);

    public abstract void modifyPercept(WorldPercept percept);
}
