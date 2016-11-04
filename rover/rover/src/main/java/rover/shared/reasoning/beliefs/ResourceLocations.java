package rover.shared.reasoning.beliefs;

import rover.ScanItem;
import rover.messaging.MessageParser;
import rover.messaging.MessagingService;
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
        itemsIHaveJustSeenFromMyPosition=filterResources(itemsIHaveJustSeenFromMyPosition);
        offsetsFromBase = new ArrayList<>();
        situateItems(itemsIHaveJustSeenFromMyPosition,offsetsFromBase,myPosition,worldWidth,worldHeight);
        this.myPosition = myPosition;
    }

    public ArrayList<ScanItem> filterResources(ArrayList<ScanItem> items){
        ArrayList<ScanItem> filteredItems = new ArrayList<>();
        for (ScanItem item:items) {
            if(item.getItemType()==ScanItem.RESOURCE)
                filteredItems.add(item);
        }
        return filteredItems;
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

    public ArrayList<RoverOffset> situateItems(ArrayList<ScanItem> unsituatedItems, ArrayList<RoverOffset> situatedItems, RoverOffset offsetFromBase, double worldWidth, double worldHeight){
        ArrayList<RoverOffset> addedItems = new ArrayList<>();
        for (ScanItem item : unsituatedItems) {
            RoverOffset situatedItem = situateItem(item, offsetFromBase, worldWidth, worldHeight);
            addedItems.add(situatedItem);
            addIfDoesNotAlreadyExist(situatedItem,situatedItems);
        }
        unsituatedItems.clear();
        return addedItems;
    }

    private void addIfDoesNotAlreadyExist(RoverOffset item, ArrayList<RoverOffset> itemList){
        boolean isSameAsExisting = false;
        for (RoverOffset existingSituatedItem:itemList) {
            if(existingSituatedItem.isSameAs(item))
                isSameAsExisting = true;
        }
        if(!isSameAsExisting)
            itemList.add(item);
    }

    public RoverOffset situateItem(ScanItem item, RoverOffset offsetFromBase, double worldWidth, double worldHeight){
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
            Collections.addAll(itemsIHaveJustSeenFromMyPosition,p.getScanItems());
            itemsIHaveJustSeenFromMyPosition=filterResources(itemsIHaveJustSeenFromMyPosition);
            ArrayList<RoverOffset> situatedItems = situateItems(itemsIHaveJustSeenFromMyPosition,offsetsFromBase,p.getMyPosition(),p.getWorldWidth(),p.getWorldHeight());
            if(situatedItems.size()>0) MessagingService.sendNewMessage(MessageParser.generateFoundMessage(situatedItems));
        }

        ArrayList<RoverOffset> resourcesFoundByOtherRovers = p.getResourcesJustFoundByOtherRovers();

        if(resourcesFoundByOtherRovers!=null){

            addSituatedItems(resourcesFoundByOtherRovers);
        }

        removeFromLocations(p.getItemsCollected());
    }

    private void addSituatedItems(ArrayList<RoverOffset> newResources) {
        for (RoverOffset newResource : newResources) {
            addIfDoesNotAlreadyExist(newResource,offsetsFromBase);
        }
    }

    public void removeFromLocations(ArrayList<RoverOffset> itemsToRemove){
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
