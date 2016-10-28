package rover.shared.practical;

import rover.messaging.MessageParser;
import rover.messaging.MessagingService;

import java.util.ArrayList;

public class CoordinateMap {
    public ArrayList<RoverOffset> coordinates;
    private static final double RADIUS_SPACING_FACTOR = Math.sqrt(3);
    private ArrayList<RoverOffset> nodesToExclude;

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
        nodesToExclude = new ArrayList<>();
    }

    public RoverOffset popOffsetToNextClosestNode(RoverOffset roverLocation){
        RoverOffset closest;
        RoverOffset toRemove;
        ArrayList<RoverOffset> nonExcludedCoordinates = getNonExcludedNodes();

        if(nonExcludedCoordinates.isEmpty())
            return null;

        toRemove = nonExcludedCoordinates.get(0);
        closest = (nonExcludedCoordinates.get(0).getDifference(roverLocation));
        for (RoverOffset coordinate : nonExcludedCoordinates) {
            RoverOffset diff = coordinate.getDifference(roverLocation);
            if(diff.magnitude()<closest.magnitude()){
                toRemove = coordinate;
                closest = diff;
            }
        }

        MessagingService.sendNewMessage(MessageParser.generateSearchingMessage(toRemove));
        nonExcludedCoordinates.remove(toRemove);
        return closest;
    }

    public int remaining() {
        return coordinates.size();
    }

    public void addNodesToExclude(ArrayList<RoverOffset> nodesToExclude) {
        this.nodesToExclude.addAll(nodesToExclude);
    }

    private ArrayList<RoverOffset> getNonExcludedNodes(){
        ArrayList<RoverOffset> nodes = new ArrayList<>(coordinates);
        for (RoverOffset node : nodes) {
            for (RoverOffset nodeToExclude : nodesToExclude) {
                if(nodeToExclude.isSameAs(node))
                    nodes.remove(nodeToExclude);
            }
        }
        return nodes;
    }
}
