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
        this.mapSizeX = RoverWorld.mapWidth;
        this.mapSizeY = RoverWorld.mapHeight;
        this.xOffset = x%mapSizeX;
        this.yOffset = y%mapSizeY;
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

    public String toMessageString(String delimiter) {
        return xOffset+delimiter+yOffset;
    }

    public double getPolarAngle() {
        double angle = Math.atan(yOffset/xOffset);
        if(1/angle<0){
            return Math.PI-angle;
        }
        return angle;
    }

    public boolean isZero(){
        return isSameAs(new RoverOffset(0,0,1,1));
    }

    public int comparePolarAngle(RoverOffset o2) {
        double a = getPolarAngle();
        double b = o2.getPolarAngle();
        return (int)(Math.round(a*1000) - Math.round(b*1000));
    }
}
