package rover.messaging;

import rover.shared.practical.RoverOffset;

/**
 * Created by rachelcabot on 28/10/2016.
 */
public abstract class ASingleParameterMessage extends AMessage{
    protected RoverOffset item;
    @Override
    protected void extractMessageParameters(String[] originalMessageTokens) {
        double x = Double.parseDouble(originalMessageTokens[1]);
        double y = Double.parseDouble(originalMessageTokens[2]);
        item = new RoverOffset(x,y);
    }
}
