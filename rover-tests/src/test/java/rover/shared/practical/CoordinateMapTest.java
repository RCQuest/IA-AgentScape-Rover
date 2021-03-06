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
    public void addNodesToExclude_dummyNode() throws Exception {
        exclusion = new ArrayList<RoverOffset>(){{
            add(dummyNode);
        }};
        uut.addNodesToExclude(exclusion);
        RoverOffset offset = uut.popOffsetToNextClosestNode(realNode);

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


        assertTrue(offsets.size()==8);
    }

    @Test
    public void segmentation_3() throws Exception{
        int total = uut.getNonExcludedNodes().size();
        assertTrue(total==9);
        uut = new CoordinateMap(worldWidth,worldHeight,4,0,3);
        int s1 = uut.getNonExcludedNodes().size();
//        assertEquals(3,s1);
        uut = new CoordinateMap(worldWidth,worldHeight,4,1,3);
        int s2 = uut.getNonExcludedNodes().size();
//        assertEquals(3,s2);
        uut = new CoordinateMap(worldWidth,worldHeight,4,2,3);
        int s3 = uut.getNonExcludedNodes().size();
//        assertEquals(3,s3);
        assertTrue(total==s3+s2+s1);
    }

    @Test
    public void segmentation_1() throws Exception{
        int total = uut.getNonExcludedNodes().size();
        assertTrue(total==9);
        uut = new CoordinateMap(worldWidth,worldHeight,4,0,1);
        int s1 = uut.getNonExcludedNodes().size();
        assertTrue(total==s1);
    }

    @Test
    public void segmentation_8() throws Exception{
        uut = new CoordinateMap(200,200,5);
        int total = uut.getNonExcludedNodes().size();
        int s = 0;
        int agents = 8;
        for (int i = 0; i < agents; i++) {
            uut = new CoordinateMap(200,200,5,i,agents);

            s += uut.getNonExcludedNodes().size();
        }
        assertEquals(total,s);
    }

    @Test
    public void segmentation_2_100() throws Exception{
        uut = new CoordinateMap(100,100,5);
        int total = uut.getNonExcludedNodes().size();
        int s = 0;
        int agents = 2;
        for (int i = 0; i < agents; i++) {
            uut = new CoordinateMap(100,100,5,i,agents);
            System.out.println("Number of nodes: "+uut.getNonExcludedNodes().size());
            s += uut.getNonExcludedNodes().size();
        }
        assertEquals(total,s);
    }

    @Test
    public void segmentation_2_80() throws Exception{
        uut = new CoordinateMap(80,80,5);
        int total = uut.getNonExcludedNodes().size();
        int s = 0;
        int agents = 2;
        for (int i = 0; i < agents; i++) {
            uut = new CoordinateMap(80,80,5,i,agents);
            System.out.println("Number of nodes: "+uut.getNonExcludedNodes().size());
            s += uut.getNonExcludedNodes().size();
        }
        assertEquals(total,s);
    }

    @Test
    public void segmentation_3_500() throws Exception{
        uut = new CoordinateMap(500,500,5);
        int total = uut.getNonExcludedNodes().size();
        int s = 0;
        int agents = 3;
        for (int i = 0; i < agents; i++) {
            uut = new CoordinateMap(500,500,5,i,agents);
            System.out.println("Number of nodes: "+uut.getNonExcludedNodes().size());
            s += uut.getNonExcludedNodes().size();
        }
        assertEquals(total,s);
    }

    @Test
    public void popNode() throws Exception{
        assertEquals(9,uut.getNonExcludedNodes().size());
        uut.popOffsetToNextClosestNode(new RoverOffset(0,0,1,1));
        assertEquals(8,uut.getNonExcludedNodes().size());
    }

    @Test
    public void sortNodes() throws Exception{
        uut.sortNodes();

    }

}