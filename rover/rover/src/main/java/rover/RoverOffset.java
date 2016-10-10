package rover;

public class RoverOffset {
    private double xOffset;
    private double yOffset;
    private double mapSizeX;
    private double mapSizeY;

    public RoverOffset(double xOffset, double yOffset, double mapSizeX, double mapSizeY) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
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
    }

    public RoverOffset getDifference(RoverOffset roverOffset) {
        return new RoverOffset(
                (roverOffset.getxOffset()-xOffset)% mapSizeX,
                (roverOffset.getyOffset()-yOffset)% mapSizeY,
                mapSizeX,
                mapSizeY);
    }

    public double magnitude() {
        return Math.hypot(xOffset,yOffset);
    }
}
