package rover;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 10/10/2016.
 */
public class CoordinateMap {
    public ArrayList<RoverOffset> coordinates;
    private static final double RADIUS_SPACING_FACTOR = Math.sqrt(3)/2;

    public CoordinateMap(double mapWidth, double mapHeight, double mapScanRadius){
        coordinates = new ArrayList<>();
        mapScanRadius = mapScanRadius*RADIUS_SPACING_FACTOR;
        int xSections = (int)Math.ceil(mapWidth/mapScanRadius);
        int ySections = (int)Math.ceil(mapHeight/mapScanRadius);
        for(int y = 0; y < ySections; y++){
            for(int x = 0; x < xSections; x++) {
                coordinates.add(new RoverOffset(
                        x*mapScanRadius+((y%2)*mapScanRadius/2),
                        y*mapScanRadius,
                        mapWidth,
                        mapHeight));
            }
        }
    }

    public RoverOffset popNextClosestNode(RoverOffset roverOffset){
        RoverOffset closest;
        RoverOffset toRemove;

        if(coordinates.isEmpty())
            return null;

        toRemove = coordinates.get(0);
        closest = (coordinates.get(0).getDifference(roverOffset));
        for (RoverOffset coordinate : coordinates) {
            RoverOffset diff = coordinate.getDifference(roverOffset);
            if(diff.magnitude()<closest.magnitude()){
                toRemove = coordinate;
                closest = diff;
            }
        }
        coordinates.remove(toRemove);
        return closest;
    }
}
