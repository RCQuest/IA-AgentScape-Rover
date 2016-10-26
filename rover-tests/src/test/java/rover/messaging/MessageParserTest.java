package rover.messaging;

import org.junit.Before;
import org.junit.Test;
import rover.Rover;
import rover.shared.practical.RoverOffset;
import rover.shared.practical.WorldPercept;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by rachelcabot on 26/10/2016.
 */
public class MessageParserTest {

    private final ArrayList<RoverOffset> noArgs = new ArrayList<RoverOffset>();
    private final ArrayList<RoverOffset> oneArg = new ArrayList<RoverOffset>(){{
        add(new RoverOffset(0,0,0,0));
    }};
    private final ArrayList<RoverOffset> multiArg = new ArrayList<RoverOffset>(){{
        add(new RoverOffset(0,0,0,0));
        add(new RoverOffset(1,0,0,0));
        add(new RoverOffset(0,1,0,0));
    }};
    private final ArrayList<ArrayList<RoverOffset>> argLists = new ArrayList<ArrayList<RoverOffset>>(){{
        add(noArgs);
        add(oneArg);
        add(multiArg);
    }};

    @Test
    public void found() throws Exception {
        for (ArrayList<RoverOffset> argList : argLists) {
            int size = argList.size();
            ArrayList<String> messages = new ArrayList<String>();
            messages.add(MessageParser.generateFoundMessage(argList));
            WorldPercept p = new WorldPercept();
            MessageParser.parse(messages).get(0).modifyPercept(p);
            assertEquals(size, p.getResourcesJustFoundByOtherRovers().size());
        }
    }

}