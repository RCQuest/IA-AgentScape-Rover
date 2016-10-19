package rover.shared.reasoning.beliefs;

import rover.ScanItem;
import rover.shared.practical.RoverOffset;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class ResourceLocations extends ABelief {
    private ArrayList<RoverOffset> offsetsFromBase;
    private ScanItem[] itemsIHaveJustSeenFromMyPosition;

    public ResourceLocations(ScanItem[] itemsICanSee) {
        itemsIHaveJustSeenFromMyPosition = itemsICanSee;
    }

    @Override
    public boolean agreesWith(APercept p) {
        return false;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return OntologyConcept.there_are_found_unobtained_resources;
    }

    public ArrayList<RoverOffset> getLocations() {
        return offsetsFromBase;
    }

    public void situateItems(RoverOffset offsetFromBase, double worldHeight, double worldWidth){
        for (ScanItem item : itemsIHaveJustSeenFromMyPosition) {
            offsetsFromBase.add(situateItem(item, offsetFromBase, worldHeight, worldWidth));
        }
    }

    private RoverOffset situateItem(ScanItem item, RoverOffset offsetFromBase, double worldHeight, double worldWidth){
        RoverOffset offset = new RoverOffset(
                item.getxOffset(),
                item.getyOffset(),
                worldWidth,
                worldHeight);
        offset.addOffset(offsetFromBase);
        return offset;
    }

    public void coalesce(ResourceLocations otherResourceLocations){
        otherResourceLocations.addOffsetsFromBase(offsetsFromBase);
        this.addOffsetsFromBase(otherResourceLocations.getLocations());
    }

    public void addOffsetsFromBase(ArrayList<RoverOffset> offsetsFromBase) {
        this.offsetsFromBase.addAll(offsetsFromBase);
    }
}
