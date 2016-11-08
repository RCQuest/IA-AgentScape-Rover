package rover.shared.reasoning.beliefs;

import rover.ScanItem;
import rover.messaging.MessageParser;
import rover.messaging.MessagingService;
import rover.shared.practical.Resource;
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
    private ArrayList<Resource> offsetsFromBase;
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
        ArrayList<RoverOffset> offsets = new ArrayList<>();
        for (Resource resource : offsetsFromBase) {
            offsets.add(resource.getOffset());
        }
        return offsets;
    }

    public ArrayList<Resource> situateItems(ArrayList<ScanItem> unsituatedItems, ArrayList<Resource> situatedItems, RoverOffset offsetFromBase, double worldWidth, double worldHeight){
        ArrayList<Resource> addedItems = new ArrayList<>();
        for (ScanItem item : unsituatedItems) {
            Resource situatedItem = situateItem(item, offsetFromBase, worldWidth, worldHeight);
            addedItems.add(situatedItem);
            addIfDoesNotAlreadyExist(situatedItem,situatedItems);
        }
        unsituatedItems.clear();
        return addedItems;
    }

    private void addIfDoesNotAlreadyExist(Resource item, ArrayList<Resource> itemList){
        boolean isSameAsExisting = false;
        for (Resource existingSituatedItem:itemList) {
            if(existingSituatedItem.isSameAs(item))
                isSameAsExisting = true;
        }
        if(!isSameAsExisting)
            itemList.add(item);
    }

    public Resource situateItem(ScanItem item, RoverOffset offsetFromBase, double worldWidth, double worldHeight){
        RoverOffset offset = new RoverOffset(
                item.getxOffset(),
                item.getyOffset(),
                worldWidth,
                worldHeight);
        offset.addOffset(offsetFromBase);
        return new Resource(offset);
    }

    @Override
    public void coalesceWith(APercept p) {
        if(p.getScanItems().length>0){
            Collections.addAll(itemsIHaveJustSeenFromMyPosition,p.getScanItems());
            itemsIHaveJustSeenFromMyPosition=filterResources(itemsIHaveJustSeenFromMyPosition);
            ArrayList<Resource> situatedItems = situateItems(itemsIHaveJustSeenFromMyPosition,offsetsFromBase,p.getMyPosition(),p.getWorldWidth(),p.getWorldHeight());
            if(situatedItems.size()>0) MessagingService.sendNewMessage(MessageParser.generateFoundMessage(situatedItems));
        }

        ArrayList<Resource> resourcesFoundByOtherRovers = p.getResourcesJustFoundByOtherRovers();

        if(resourcesFoundByOtherRovers!=null){

            addSituatedItems(resourcesFoundByOtherRovers);
        }

        removeFromLocations(p.getItemsWhollyCollected());
        decrementResourceLevels(p.getItemsTouched());
    }

    private void decrementResourceLevels(ArrayList<RoverOffset> itemsTouched) {
        ArrayList<Resource> toRemove = new ArrayList<>();
        for (RoverOffset roverOffset : itemsTouched) {
            for (Resource resource : offsetsFromBase) {
                if(roverOffset.isSameAs(resource.getOffset())){
                    resource.decrementLeft();
                    if(resource.isDepleted())
                        toRemove.add(resource);
                }
            }
        }
        removeFromLocations(toRemove);
    }

    private void addSituatedItems(ArrayList<Resource> newResources) {
        for (Resource newResource : newResources) {
            addIfDoesNotAlreadyExist(newResource,offsetsFromBase);
        }
    }

    public void removeFromLocations(ArrayList<Resource> itemsToRemove){
        ArrayList<Resource> toPreserve = new ArrayList<>();
        for (Resource existingItem:offsetsFromBase) {
            boolean remove = false;
            for (Resource itemToRemove:itemsToRemove) {
                if(existingItem.isSameAs(itemToRemove))
                    remove = true;
            }
            if(!remove) toPreserve.add(existingItem);
        }
        offsetsFromBase = toPreserve;
    }

    public boolean onTopOfResourceLocation() {
        for (Resource r:offsetsFromBase) {
            if(myPosition.isSameAs(r.getOffset()))
                return true;
        }
        return false;
    }
}
