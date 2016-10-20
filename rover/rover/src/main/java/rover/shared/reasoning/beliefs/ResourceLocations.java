package rover.shared.reasoning.beliefs;

import rover.ScanItem;
import rover.shared.practical.RoverOffset;
import rover.shared.reasoning.ABelief;
import rover.shared.reasoning.APercept;
import rover.shared.reasoning.ontology.OntologyConcept;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rachelcabot on 18/10/2016.
 */
public class ResourceLocations extends ABelief {
    private ArrayList<RoverOffset> offsetsFromBase;
    private ArrayList<ScanItem> itemsIHaveJustSeenFromMyPosition;

    public ResourceLocations(ScanItem[] itemsICanSee, RoverOffset myPosition, double worldHeight, double worldWidth) {
        itemsIHaveJustSeenFromMyPosition = new ArrayList<ScanItem>();
        Collections.addAll(itemsIHaveJustSeenFromMyPosition, itemsICanSee);
        situateItems(myPosition,worldWidth,worldHeight);
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        return OntologyConcept.there_are_found_unobtained_resources;
    }

    public ArrayList<RoverOffset> getLocations() {
        return offsetsFromBase;
    }

    public void situateItems(RoverOffset offsetFromBase, double worldWidth, double worldHeight){
        for (ScanItem item : itemsIHaveJustSeenFromMyPosition) {
            offsetsFromBase.add(situateItem(item, offsetFromBase, worldWidth, worldHeight));
        }
    }

    private RoverOffset situateItem(ScanItem item, RoverOffset offsetFromBase, double worldWidth, double worldHeight){
        RoverOffset offset = new RoverOffset(
                item.getxOffset(),
                item.getyOffset(),
                worldWidth,
                worldHeight);
        offset.addOffset(offsetFromBase);
        return offset;
    }

    public void addOffsetsFromBase(ArrayList<RoverOffset> offsetsFromBase) {
        this.offsetsFromBase.addAll(offsetsFromBase);
    }

    @Override
    public boolean isNullifiedBy(APercept p) {
        return false;
    }

    @Override
    public void coalesceWith(APercept p) {
        if(p.getScanItems().length>0){
            Collections.addAll(itemsIHaveJustSeenFromMyPosition,p.getScanItems());
            situateItems(p.getMyPosition(),p.getWorldWidth(),p.getWorldHeight());
        }
    }
}
