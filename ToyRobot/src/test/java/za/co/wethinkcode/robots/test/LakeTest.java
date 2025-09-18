//package za.co.wethinkcode.robots.test;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import robotworld.world.IWorld;
//import robotworld.robot.Position;
//
//public class LakeTest {
//    private Lake lake;
//    private final int bottomLeftX = 10;
//    private final int bottomLeftY = 15;
//
//    @BeforeEach
//    public void setUp() {
//        lake = new Lake(bottomLeftX, bottomLeftY);
//    }
//
//    @Test
//    public void testGetBottomLeftX() {
//        assertEquals(bottomLeftX, lake.getBottomLeftX());
//    }
//
//    @Test
//    public void testGetBottomLeftY() {
//        assertEquals(bottomLeftY, lake.getBottomLeftY());
//    }
//
//    @Test
//    public void testGetSize() {
//        assertEquals(6, lake.getSize());
//    }
//
//    @Test
//    public void testGetType() {
//        assertEquals(IWorld.ObstacleType.LAKE, lake.getType());
//    }
//
//    @Test
//    public void testBlocksPositionInsideLake() {
//        Position position = new Position(12, 17);
//        assertTrue(lake.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPositionOutsideLake() {
//        Position position = new Position(17, 22);
//        assertFalse(lake.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPositionOnBoundary() {
//        Position position = new Position(15, 20);
//        assertTrue(lake.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPath() {
//        Position start = new Position(10, 15);
//        Position end = new Position(15, 20);
//        assertTrue(lake.blocksPath(start, end));
//    }
//
//    @Test
//    public void testDoesNotBlockPath() {
//        Position start = new Position(17, 23);
//        Position end = new Position(18, 24);
//        assertFalse(lake.blocksPath(start, end));
//    }
//
//    @Test
//    public void testBlocksObstacleOverlapping() {
//        Position position = new Position(11, 16);
//        assertTrue(lake.blocksObstacle(position));
//    }
//
//    @Test
//    public void testDoesNotBlockObstacleNotOverlapping() {
//        Position position = new Position(17, 23);
//        assertFalse(lake.blocksObstacle(position));
//    }
//}
//
