package rover;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 10/10/2016.
 */
public class CoordinateMap {
    public ArrayList<RoverOffset> coordinates;

    public CoordinateMap(double mapWidth, double mapHeight, double mapScanRadius){
        coordinates = new ArrayList<>();
        int xSections = (int)Math.ceil(mapWidth/mapScanRadius);
        int ySections = (int)Math.ceil(mapHeight/mapScanRadius);
        for(int y = 0; y < ySections; y++){
            for(int x = 0; x < xSections; x++) {
                coordinates.add(new RoverOffset(
                        x*2*mapScanRadius+((y%2)*mapScanRadius),
                        y*mapScanRadius,
                        mapWidth,
                        mapHeight));
            }
        }
    }

    public RoverOffset popNextClosestNode(RoverOffset roverOffset){
        RoverOffset closest;
        RoverOffset toRemove;

        if(coordinates.isEmpty()){
            return null;
        } else {
            toRemove = coordinates.get(0);
            closest = (coordinates.get(0).getDifference(roverOffset));
        }
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
