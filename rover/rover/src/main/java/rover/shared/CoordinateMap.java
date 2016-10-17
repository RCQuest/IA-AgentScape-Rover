package rover.shared;

import java.util.ArrayList;

public class CoordinateMap {
    public ArrayList<RoverOffset> coordinates;
    private static final double RADIUS_SPACING_FACTOR = Math.sqrt(3);

    public CoordinateMap(double mapWidth, double mapHeight, double mapScanRadius){
        coordinates = new ArrayList<>();
        mapScanRadius = mapScanRadius*RADIUS_SPACING_FACTOR;
        int xSections = (int)Math.ceil(mapWidth/mapScanRadius);
        int ySections = (int)Math.ceil(mapHeight/mapScanRadius);
        for(int y = 0; y < ySections; y++){
            for(int x = 0; x < xSections; x++) {
                RoverOffset newOffset = new RoverOffset(
                        x*mapScanRadius+((y%2)*mapScanRadius/2),
                        y*mapScanRadius,
                        mapWidth,
                        mapHeight);
                coordinates.add(newOffset);
            }
        }
    }

    public RoverOffset popOffsetToNextClosestNode(RoverOffset roverLocation){
        RoverOffset closest;
        RoverOffset toRemove;

        if(coordinates.isEmpty())
            return null;

        toRemove = coordinates.get(0);
        closest = (coordinates.get(0).getDifference(roverLocation));
        for (RoverOffset coordinate : coordinates) {
            RoverOffset diff = coordinate.getDifference(roverLocation);
            if(diff.magnitude()<closest.magnitude()){
                toRemove = coordinate;
                closest = diff;
            }
        }
        coordinates.remove(toRemove);
        return closest;
    }
}
