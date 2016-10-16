package rover;

/**
 * Created by rachelcabot on 09/10/2016.
 */
public class RoverMovement {
    public final double xOffset;
    public final double yOffset;
    public final double speed;

    public RoverMovement(double xOffset, double yOffset, double speed) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.speed = speed;
    }

    public RoverMovement relativeTo(RoverOffset offset) {
        return new RoverMovement(
                xOffset-offset.getxOffset(),
                yOffset-offset.getyOffset(),
                speed);
    }
}
