package rover.shared.practical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CoordinateMap {
    public ArrayList<RoverOffset> coordinates;
    private static final double RADIUS_SPACING_FACTOR = Math.sqrt(3)/2;
    private ArrayList<RoverOffset> nodesToExclude;

    public CoordinateMap(double mapWidth, double mapHeight, double mapScanRadius){
        generateCompleteSet(mapWidth, mapHeight, mapScanRadius);
        nodesToExclude = new ArrayList<>();
        sortNodes();
    }

    private void generateCompleteSet(double mapWidth, double mapHeight, double mapScanRadius) {
        coordinates = new ArrayList<>();
        mapScanRadius = mapScanRadius*2;
        mapScanRadius = mapScanRadius*RADIUS_SPACING_FACTOR;
        double mapScanDiameter = (mapScanRadius*2)-4;
        int xSections = (int)Math.ceil(mapWidth/mapScanDiameter);
        int ySections = (int)Math.ceil(mapHeight/mapScanDiameter);
        RoverOffset offset = new RoverOffset(mapWidth/2,mapHeight/2);
        for(int y = 0; y < ySections; y++){
            for(int x = 0; x < xSections; x++) {
                RoverOffset newOffset = new RoverOffset(
                        x*mapScanDiameter+((y%2)*mapScanDiameter/2),
                        y*mapScanDiameter,
                        mapWidth,
                        mapHeight);
                newOffset.addOffset(offset);
                coordinates.add(newOffset);
            }
        }
    }

    public CoordinateMap(double mapWidth, double mapHeight, double mapScanRadius, int id, int numberOfOtherAgents) {
        generateCompleteSet(mapWidth,mapHeight,mapScanRadius);
        nodesToExclude = new ArrayList<>();
        sortNodes();
        takeSlice(id,numberOfOtherAgents);
    }

    private void takeSlice(int id, int numberOfOtherAgents) {
        int remainder = coordinates.size()%numberOfOtherAgents;
        int myLimit;
        int perRover = ((coordinates.size()-remainder)/numberOfOtherAgents);
        if(id==numberOfOtherAgents-1){
            myLimit = ((id+1)*perRover) + remainder;
        } else {
            myLimit = ((id+1)*perRover);
        }
        ArrayList<RoverOffset> newCoords = new ArrayList<>();
        for(int i=id*perRover;i<myLimit;i++){
            newCoords.add(coordinates.get(i));
        }
        coordinates = newCoords;
    }

    void sortNodes() {
        Collections.sort(coordinates, new Comparator<RoverOffset>() {
            @Override
            public int compare(RoverOffset o1, RoverOffset o2) {
                return (o1.comparePolarAngle(o2));
            }
        });
    }

    public RoverOffset popOffsetToNextClosestNode(RoverOffset roverLocation){
        RoverOffset closest;
        RoverOffset toRemove;
        ArrayList<RoverOffset> nonExcludedCoordinates = coordinates;

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

//        MessagingService.sendNewMessage(MessageParser.generateSearchingMessage(toRemove));
        coordinates.remove(toRemove);
        return closest;
    }

    public RoverOffset popOffsetToNextClosestNodeToBase(RoverOffset roverLocation){
        RoverOffset nextClosest = coordinates.get(0).getDifference(roverLocation);
        coordinates.remove(0);
        return nextClosest;
    }

    public int remaining() {
        return coordinates.size();
    }

    public void addNodesToExclude(ArrayList<RoverOffset> nodesToExclude) {
        if(nodesToExclude==null) return;
        this.nodesToExclude.addAll(nodesToExclude);
    }

    public ArrayList<RoverOffset> getNonExcludedNodes(){
        ArrayList<RoverOffset> nodes = new ArrayList<>();
        for (RoverOffset node : coordinates) {
            boolean exclude = false;
            for (RoverOffset nodeToExclude : nodesToExclude) {
                if(nodeToExclude.isSameAs(node))
                    exclude = true;
            }
            if(!exclude)
                nodes.add(node);
        }
        return nodes;
    }
}
