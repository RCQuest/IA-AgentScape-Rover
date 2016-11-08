package rover.messaging;

import rover.shared.practical.Resource;
import rover.shared.practical.RoverOffset;
import rover.shared.practical.WorldPercept;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class FoundMessage extends AMessage {

    private ArrayList<Resource> resources;

    public FoundMessage(){
        resources = new ArrayList<>();
    }

    @Override
    protected void extractMessageParameters(String[] originalMessageTokens) {
        for (int i = 1; i < originalMessageTokens.length; i+=2) {
            double x = Double.parseDouble(originalMessageTokens[i]);
            double y = Double.parseDouble(originalMessageTokens[i+1]);
            resources.add(new Resource(new RoverOffset(x,y)));
        }
    }

    @Override
    public void modifyPercept(WorldPercept percept) {
        percept.addResourcesJustFoundByOtherRovers(resources);
    }
}
