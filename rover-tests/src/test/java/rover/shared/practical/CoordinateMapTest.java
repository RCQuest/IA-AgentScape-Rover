package rover.shared.practical;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by rachelcabot on 02/11/2016.
 */
public class CoordinateMapTest {

    private CoordinateMap uut;
    private final float worldWidth = 16;
    private final float worldHeight = 16;
    private RoverOffset dummyNode;
    private RoverOffset realNode;
    private RoverOffset anotherRealNode;
    private ArrayList<RoverOffset> exclusion;

    @Before
    public void setUp() throws Exception {
        uut = new CoordinateMap(worldWidth,worldHeight,4);
        dummyNode=new RoverOffset(1,1,worldWidth,worldHeight);
        realNode=new RoverOffset(0,0,worldWidth,worldHeight);
        anotherRealNode=new RoverOffset(-2.1435935394489825,0.0,worldWidth,worldHeight);
    }

    @Test
    public void addNodesToExclude_realNode() throws Exception {
        exclusion = new ArrayList<RoverOffset>(){{
            add(realNode);
        }};
        uut.addNodesToExclude(exclusion);
        RoverOffset offset = uut.popOffsetToNextClosestNode(realNode);
        assertTrue(offset.isSameAs(anotherRealNode));
    }

    @Test
    public void addNodesToExclude_dummyNode() throws Exception {
        exclusion = new ArrayList<RoverOffset>(){{
            add(dummyNode);
        }};
        uut.addNodesToExclude(exclusion);
        RoverOffset offset = uut.popOffsetToNextClosestNode(realNode);

        assertTrue(offset.isSameAs(realNode));
    }

    @Test
    public void addNodesToExclude_manyNode() throws Exception {
        exclusion = new ArrayList<RoverOffset>(){{
            add(dummyNode);
            add(realNode);
        }};
        uut.addNodesToExclude(exclusion);
        RoverOffset offset = uut.popOffsetToNextClosestNode(realNode);
        System.out.println(offset);

        assertTrue(offset.isSameAs(anotherRealNode));
    }

    @Test
    public void addNodesToExclude_noNodes() throws Exception {
        exclusion = new ArrayList<RoverOffset>();
        uut.addNodesToExclude(exclusion);
        RoverOffset offset = uut.popOffsetToNextClosestNode(realNode);
        System.out.println(offset);

        assertTrue(offset.isSameAs(realNode));
    }

    @Test
    public void getNonExcludedNodes() throws Exception{
        exclusion = new ArrayList<RoverOffset>(){{
            add(dummyNode);
            add(realNode);
        }};
        uut.addNodesToExclude(exclusion);
        ArrayList<RoverOffset> offsets = uut.getNonExcludedNodes();
        System.out.println(offsets);

        assertTrue(offsets.size()==8);
    }

}