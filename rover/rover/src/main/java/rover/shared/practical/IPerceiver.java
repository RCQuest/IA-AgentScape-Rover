package rover.shared.practical;

import rover.messaging.AMessage;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 19/10/2016.
 */
public interface IPerceiver {
    boolean loadIsFull();

    CoordinateMap getSearchNodes();

    int getCurrentLoad();

    int getCapacity();

    int getWorldHeight();

    int getWorldWidth();

    RoverOffset getPosition();

    boolean previousActionWasSuccessful();

    ArrayList<AMessage> getNewMessages();

    int getMovementSpeed();

    double getEnergyRemaining();

    int getTypeOfResourceCarrier();

    int getNumberOfScanningRovers();
}
