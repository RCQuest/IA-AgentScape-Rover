package rover;

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

    public void optimise(){
        if(Math.abs(xOffset)>mapSizeX/2)
            xOffset = -xOffset;
        if(Math.abs(yOffset)>mapSizeY/2)
            yOffset = -yOffset;
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
                (roverOffset.getxOffset()-xOffset)% mapSizeX,
                (roverOffset.getyOffset()-yOffset)% mapSizeY,
                mapSizeX,
                mapSizeY);
    }

    public double magnitude() {
        return Math.hypot(xOffset,yOffset);
    }
}
