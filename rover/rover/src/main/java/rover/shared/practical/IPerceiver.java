package rover.shared.practical;

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
}
