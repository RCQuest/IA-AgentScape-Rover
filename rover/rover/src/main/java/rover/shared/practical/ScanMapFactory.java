package rover.shared.practical;

import rover.messaging.AMessage;
import rover.messaging.HelloMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rachelcabot on 04/11/2016.
 */
public class ScanMapFactory {
    public CoordinateMap create(int worldWidth, int worldHeight, int scanRadius, int roverId, int totalNumberOfScanningAgents, ArrayList<AMessage> helloMessages) {
        int scanId = getScanMapIndex(roverId,helloMessages);
        return new CoordinateMap(worldWidth,worldHeight,scanRadius,scanId,totalNumberOfScanningAgents);
    }

    public int getScanMapIndex(int id, ArrayList<AMessage> helloMessages) {
        Collections.sort(helloMessages, new Comparator<AMessage>() {
            @Override
            public int compare(AMessage o1, AMessage o2) {
                return ((HelloMessage)o1).getId()-((HelloMessage)o2).getId();
            }
        });
        int scanId = 0;
        for (AMessage helloMessage : helloMessages) {
            if(((HelloMessage)helloMessage).getId()==id)
                return scanId;
            scanId++;
        }
        return 0;
    }
}
