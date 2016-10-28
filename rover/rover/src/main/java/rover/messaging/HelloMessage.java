package rover.messaging;

import rover.shared.practical.WorldPercept;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class HelloMessage extends AMessage {
    @Override
    protected void extractMessageParameters(String[] originalMessageTokens) {}

    @Override
    public void modifyPercept(WorldPercept percept) {
        System.out.println("I see you!");
    }
}
