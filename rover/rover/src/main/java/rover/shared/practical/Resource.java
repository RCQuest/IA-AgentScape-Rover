package rover.shared.practical;

/**
 * Created by rachelcabot on 08/11/2016.
 */
public class Resource {
    private RoverOffset offset;
    private int left;

    public Resource(RoverOffset offset) {
        this.offset = offset;
        this.left = ScenarioOptimisations.getResourceVolume();
    }

    public RoverOffset getOffset() {
        return offset;
    }

    public boolean isSameAs(Resource itemToRemove) {
        return itemToRemove.getOffset().isSameAs(offset);
    }

    public String toMessageString(String delimiter) {
        return offset.toMessageString(delimiter);
    }

    public void decrementLeft() {
        left--;
    }

    public boolean isDepleted(){
        return left<1;
    }
}
