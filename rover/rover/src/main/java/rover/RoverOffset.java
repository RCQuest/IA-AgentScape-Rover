package rover;

/**
 * Created by rachelcabot on 09/10/2016.
 */
public class RoverOffset {
    private int xOffset;
    private int yOffset;

    public RoverOffset(int xOffset, int yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void addOffset(RoverMovement movement){
        xOffset+=movement.xOffset;
        yOffset+=movement.yOffset;
    }
}
