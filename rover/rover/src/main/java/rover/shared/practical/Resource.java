package rover.shared.practical;

/**
 * Created by rachelcabot on 08/11/2016.
 */
public class Resource {
    private RoverOffset offset;
    private int left;
    private int resourceType;

    public Resource(RoverOffset offset,int resourceType) {
        this.offset = offset;
        this.left = ScenarioOptimisations.getResourceVolume();
        this.resourceType= resourceType;
    }

    public RoverOffset getOffset() {
        return offset;
    }

    public boolean isSameAs(Resource itemToRemove) {
        return itemToRemove.getOffset().isSameAs(offset);
    }

    public String toMessageString(String delimiter) {
        return offset.toMessageString(delimiter)+delimiter+resourceType;
    }

    public void decrementLeft() {
        left--;
    }

    public boolean isDepleted(){
        return left<1;
    }

    public int getResourceType() {
        return resourceType;
    }
}
