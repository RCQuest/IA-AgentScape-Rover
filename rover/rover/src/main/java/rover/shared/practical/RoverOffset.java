package rover.shared.practical;

public class RoverOffset {
    private double xOffset;
    private double yOffset;
    private double mapSizeX;
    private double mapSizeY;

    public RoverOffset(double xOffset, double yOffset, double mapSizeX, double mapSizeY) {

        this.xOffset = xOffset%mapSizeX;
        this.yOffset = yOffset%mapSizeY;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        optimise();
    }

    public RoverOffset(double x, double y) {
        mapSizeX = RoverWorld.mapWidth;
        mapSizeY = RoverWorld.mapHeight;
        this.xOffset = xOffset%mapSizeX;
        this.yOffset = yOffset%mapSizeY;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
        optimise();
    }

    public void optimise(){
        if(Math.abs(xOffset)>mapSizeX/2)
            xOffset = reverseCoordinate(xOffset,mapSizeX);
        if(Math.abs(yOffset)>mapSizeY/2)
            yOffset = reverseCoordinate(yOffset,mapSizeY);
    }

    private double reverseCoordinate(double c, double wrap){
        return (c > 0) ? c-wrap : c+wrap;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void addOffset(RoverMovement movement){
        xOffset=(movement.xOffset+xOffset) % mapSizeX;
        yOffset=(movement.yOffset+yOffset) % mapSizeY;
        optimise();
    }

    public RoverOffset getDifference(RoverOffset roverOffset) {
        return new RoverOffset(
                (xOffset-roverOffset.getxOffset())% mapSizeX,
                (yOffset-roverOffset.getyOffset())% mapSizeY,
                mapSizeX,
                mapSizeY);
    }

    public double magnitude() {
        return Math.hypot(xOffset,yOffset);
    }

    public void addOffset(RoverOffset offset) {
        xOffset=(offset.xOffset+xOffset) % mapSizeX;
        yOffset=(offset.yOffset+yOffset) % mapSizeY;
        optimise();
    }

    public String toString(){
        return "("+xOffset+","+yOffset+")";
    }

    public boolean isSameAs(RoverOffset offset) {
        return doubleEqual(offset.getxOffset(),xOffset) && doubleEqual(offset.getyOffset(),yOffset);
    }

    private boolean doubleEqual(double a,double b){
        return Math.abs(a-b) < 0.00001;
    }
}
