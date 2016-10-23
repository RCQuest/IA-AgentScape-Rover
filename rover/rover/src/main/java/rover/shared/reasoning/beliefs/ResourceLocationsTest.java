package rover.shared.reasoning.beliefs;

import junit.framework.TestCase;
import org.junit.Assert;
import rover.PollResult;
import rover.ScanItem;
import rover.reasoning.simple.SimplePerceptFactory;
import rover.shared.practical.CoordinateMap;
import rover.shared.practical.RoverOffset;
import rover.shared.practical.WorldPercept;
import rover.shared.reasoning.ontology.OntologyConcept;
import rover.shared.test.TestWorld;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by rachelcabot on 23/10/2016.
 */
public class ResourceLocationsTest {


    private ResourceLocations uut;
    @org.junit.Before
    public void setUp() throws Exception {
        uut = new ResourceLocations(
                new ScanItem[]{},
                new RoverOffset(0,0, TestWorld.width,TestWorld.height),
                TestWorld.height,
                TestWorld.width
        );
    }

    @org.junit.Test
    public void getOntologicalOrdinal() throws Exception {
        assertEquals(uut.getOntologicalOrdinal(), OntologyConcept.there_are_no_found_unobtained_resources);
    }

    @org.junit.Test
    public void getLocations() throws Exception {
        assertEquals(uut.getLocations().size(), 0);
    }

    @org.junit.Test
    public void coalesceWithNoNewResource() throws Exception {
        executePerceptScenario(new ScanItem[]{},new RoverOffset(0,0,TestWorld.width,TestWorld.height),true);
        assertEquals(uut.getLocations().size(),0);
        assertEquals(uut.getOntologicalOrdinal(), OntologyConcept.there_are_no_found_unobtained_resources);
    }

    @org.junit.Test
    public void coalesceWithNewResource() throws Exception {
        executePerceptScenario(new ScanItem[]{new ScanItem(ScanItem.BASE,0,0)},new RoverOffset(0,0,TestWorld.width,TestWorld.height),true);
        assertEquals(0, uut.getLocations().size());
        assertEquals(uut.getOntologicalOrdinal(), OntologyConcept.there_are_no_found_unobtained_resources);
        executePerceptScenario(new ScanItem[]{new ScanItem(ScanItem.BASE,0,0),new ScanItem(ScanItem.RESOURCE,2,2)},new RoverOffset(0,0,TestWorld.width,TestWorld.height),true);
        assertEquals(1, uut.getLocations().size());
        assertEquals(OntologyConcept.there_are_found_unobtained_resources, uut.getOntologicalOrdinal());
        assertEquals(2, uut.getLocations().get(0).getxOffset(),0.1);
        executePerceptScenario(new ScanItem[]{new ScanItem(ScanItem.BASE,-2,-2),new ScanItem(ScanItem.RESOURCE,0,0)},new RoverOffset(2,2,TestWorld.width,TestWorld.height),true);
        assertEquals(1, uut.getLocations().size());
        assertEquals(OntologyConcept.there_are_found_unobtained_resources, uut.getOntologicalOrdinal());
        executePerceptScenario(new ScanItem[]{new ScanItem(ScanItem.RESOURCE,-4,-4),new ScanItem(ScanItem.RESOURCE,0,0)},new RoverOffset(15,15,TestWorld.width,TestWorld.height),true);
        assertEquals(3, uut.getLocations().size());
        assertEquals(OntologyConcept.there_are_found_unobtained_resources, uut.getOntologicalOrdinal());
    }

    @org.junit.Test
    public void coalesceToRemoveResources() throws Exception {
        executePerceptScenario(new ScanItem[]{
                new ScanItem(ScanItem.BASE,0,0),
                new ScanItem(ScanItem.RESOURCE,1,1),
                new ScanItem(ScanItem.RESOURCE,2,2),
                new ScanItem(ScanItem.RESOURCE,3,3),
                new ScanItem(ScanItem.RESOURCE,4,4),
        }, new RoverOffset(0,0,TestWorld.width,TestWorld.height),true);
        assertEquals(4, uut.getLocations().size());
        assertEquals(uut.getOntologicalOrdinal(), OntologyConcept.there_are_found_unobtained_resources);
        executePerceptScenario(new ScanItem[]{}, new RoverOffset(1,1,TestWorld.width,TestWorld.height),false);
        assertEquals(3, uut.getLocations().size());
        assertEquals(uut.getOntologicalOrdinal(), OntologyConcept.there_are_found_unobtained_resources);
        executePerceptScenario(new ScanItem[]{}, new RoverOffset(2,2,TestWorld.width,TestWorld.height),false);
        executePerceptScenario(new ScanItem[]{}, new RoverOffset(3,3,TestWorld.width,TestWorld.height),false);
        executePerceptScenario(new ScanItem[]{}, new RoverOffset(4,4,TestWorld.width,TestWorld.height),false);
        assertEquals(0, uut.getLocations().size());
        assertEquals(uut.getOntologicalOrdinal(), OntologyConcept.there_are_no_found_unobtained_resources);
    }

    @org.junit.Test
    public void onTopOfResourceLocation() throws Exception {
        assertEquals(uut.onTopOfResourceLocation(),false);
        executePerceptScenario(new ScanItem[]{new ScanItem(ScanItem.RESOURCE,0,0)},new RoverOffset(0,0,TestWorld.width,TestWorld.height),true);
        assertEquals(uut.onTopOfResourceLocation(),true);
    }

    private void executePerceptScenario(ScanItem[] items, RoverOffset position, boolean previousActionSuccess){
        WorldPercept p = new WorldPercept();
        p.setItemsICanSee(items);
        p.setRoverCapacity(4);
        p.setRoverLoad(0);
        p.setSearchNodesRemaining(new CoordinateMap(TestWorld.width,TestWorld.height,4));
        p.setWorldHeight(TestWorld.height);
        p.setWorldWidth(TestWorld.width);
        p.setMyPosition(position);
        p.setPreviousActionWasSuccessful(previousActionSuccess);
        p.setPreviousAction(PollResult.COLLECT);
        uut.coalesceWith(p);
    }

}