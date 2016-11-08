package rover.messaging;

import org.junit.Before;
import org.junit.Test;
import rover.Rover;
import rover.shared.practical.Resource;
import rover.shared.practical.RoverOffset;
import rover.shared.practical.WorldPercept;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class MessageParserTest {

    private final ArrayList<Resource> noArgs = new ArrayList<Resource>();
    private final ArrayList<Resource> oneArg = new ArrayList<Resource>(){{
        add(new Resource(new RoverOffset(0,0,0,0)));
    }};
    private final ArrayList<Resource> multiArg = new ArrayList<Resource>(){{
        add(new Resource(new RoverOffset(0,0,0,0)));
        add(new Resource(new RoverOffset(1,0,0,0)));
        add(new Resource(new RoverOffset(0,1,0,0)));
    }};
    private final ArrayList<ArrayList<Resource>> argLists = new ArrayList<ArrayList<Resource>>(){{
        add(noArgs);
        add(oneArg);
        add(multiArg);
    }};

    @Test
    public void found() throws Exception {
        for (ArrayList<Resource> argList : argLists) {
            int size = argList.size();
            ArrayList<String> messages = new ArrayList<String>();
            messages.add(MessageParser.generateFoundMessage(argList));
            WorldPercept p = new WorldPercept();
            MessageParser.parse(messages).get(0).modifyPercept(p);
            assertEquals(size, p.getResourcesJustFoundByOtherRovers().size());
        }
    }

}