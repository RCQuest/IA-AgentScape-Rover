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
    private final RoverOffset myPosition;
    private ArrayList<RoverOffset> offsetsFromBase;
    private ArrayList<ScanItem> itemsIHaveJustSeenFromMyPosition;

    public ResourceLocations(ScanItem[] itemsICanSee, RoverOffset myPosition, double worldHeight, double worldWidth) {
        itemsIHaveJustSeenFromMyPosition = new ArrayList<>();
        Collections.addAll(itemsIHaveJustSeenFromMyPosition, itemsICanSee);
        offsetsFromBase = new ArrayList<>();
        situateItems(itemsIHaveJustSeenFromMyPosition,offsetsFromBase,myPosition,worldWidth,worldHeight);
        this.myPosition = myPosition;
    }

    @Override
    public OntologyConcept getOntologicalOrdinal() {
        if(offsetsFromBase.size()>0)
            return OntologyConcept.there_are_found_unobtained_resources;
        else
            return OntologyConcept.there_are_no_found_unobtained_resources;
    }

    public ArrayList<RoverOffset> getLocations() {
        return offsetsFromBase;
    }

    private void situateItems(ArrayList<ScanItem> unsituatedItems, ArrayList<RoverOffset> situatedItems, RoverOffset offsetFromBase, double worldWidth, double worldHeight){
        for (ScanItem item : unsituatedItems) {
            situatedItems.add(situateItem(item, offsetFromBase, worldWidth, worldHeight));
        }
        unsituatedItems.clear();
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

    @Override
    public void coalesceWith(APercept p) {
        if(p.getScanItems().length>0){
            System.out.println("there are new resources!!");
            Collections.addAll(itemsIHaveJustSeenFromMyPosition,p.getScanItems());
            System.out.println("just seen these items: "+itemsIHaveJustSeenFromMyPosition);
            situateItems(itemsIHaveJustSeenFromMyPosition,offsetsFromBase,p.getMyPosition(),p.getWorldWidth(),p.getWorldHeight());
        }
        removeFromLocations(p.getItemsCollected());
        System.out.println("offsets from base: "+offsetsFromBase);
    }

    private void removeFromLocations(ArrayList<RoverOffset> itemsToRemove){
        ArrayList<RoverOffset> toPreserve = new ArrayList<>();
        for (RoverOffset existingItem:offsetsFromBase) {
            boolean remove = false;
            for (RoverOffset itemToRemove:itemsToRemove) {
                if(existingItem.isSameAs(itemToRemove))
                    remove = true;
            }
            if(!remove) toPreserve.add(existingItem);
        }
        offsetsFromBase = toPreserve;
    }

    public boolean onTopOfResourceLocation() {
        for (RoverOffset r:offsetsFromBase) {
            if(myPosition.isSameAs(r))
                return true;
        }
        return false;
    }
}
