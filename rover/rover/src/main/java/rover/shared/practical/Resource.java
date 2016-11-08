package rover.shared.practical;

/**
 * Created by rachelcabot on 08/11/2016.
 */
public class Resource {
    private RoverOffset offset;

    public Resource(RoverOffset offset) {
        this.offset = offset;
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
}
