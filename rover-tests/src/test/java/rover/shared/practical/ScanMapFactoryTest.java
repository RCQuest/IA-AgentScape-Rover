package rover.shared.practical;

import org.junit.Before;
import org.junit.Test;
import rover.messaging.AMessage;
import rover.messaging.HelloMessage;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by rachelcabot on 04/11/2016.
 */
public class ScanMapFactoryTest {

    ScanMapFactory uut;
    ArrayList<AMessage> messages;

    @Before
    public void setUp(){
        uut = new ScanMapFactory();
    }

    @Test
    public void getScanMapIndex() throws Exception {
        messages = new ArrayList<AMessage>(){{
            add(new HelloMessage());
            add(new HelloMessage());
            add(new HelloMessage());
        }};
        for (int i = 0; i < messages.size(); i++) {
            ((HelloMessage) messages.get(i)).setId(i*2);
        }
        assertEquals(0,uut.getScanMapIndex(0,messages));
        assertEquals(1,uut.getScanMapIndex(2,messages));
        assertEquals(2,uut.getScanMapIndex(4,messages));

        messages = new ArrayList<AMessage>(){{
            add(new HelloMessage());
        }};
        for (int i = 0; i < messages.size(); i++) {
            ((HelloMessage) messages.get(i)).setId(5);
        }
        assertEquals(0,uut.getScanMapIndex(5,messages));
    }

}