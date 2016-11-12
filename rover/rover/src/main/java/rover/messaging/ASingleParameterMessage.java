package rover.messaging;

import rover.shared.practical.RoverOffset;

/**
 * Created by rachelcabot on 28/10/2016.
 */
public abstract class ASingleParameterMessage extends AMessage{
    protected RoverOffset item;
    protected int resourceType;

    @Override
    protected void extractMessageParameters(String[] originalMessageTokens) {
        double x = Double.parseDouble(originalMessageTokens[1]);
        double y = Double.parseDouble(originalMessageTokens[2]);
        resourceType = Integer.parseInt(originalMessageTokens[3]);
        item = new RoverOffset(x,y);
    }
}
