package rover.messaging;

import rover.shared.practical.IPerceiver;

/**
 * Created by rachelcabot on 24/10/2016.
 */
public abstract class AMessage {
    public void addToNextPercept(IPerceiver perceiver) {
        perceiver.addNewMessage(this);
    }
}
