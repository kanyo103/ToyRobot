//package za.co.wethinkcode.robots.test;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import robotworld.world.IWorld;
//import robotworld.robot.Position;
//
//public class MountainTest {
//
//    private Mountain mountain;
//    private final int bottomLeftX = 5;
//    private final int bottomLeftY = 10;
//
//    @BeforeEach
//    public void setUp() {
//        mountain = new Mountain(bottomLeftX, bottomLeftY);
//    }
//
//    @Test
//    public void testGetBottomLeftX() {
//        assertEquals(bottomLeftX, mountain.getBottomLeftX());
//    }
//
//    @Test
//    public void testGetBottomLeftY() {
//        assertEquals(bottomLeftY, mountain.getBottomLeftY());
//    }
//
//    @Test
//    public void testGetSize() {
//        assertEquals(3, mountain.getSize());
//    }
//
//    @Test
//    public void testGetType() {
//        assertEquals(IWorld.ObstacleType.MOUNTAIN, mountain.getType());
//    }
//
//    @Test
//    public void testBlocksPositionInsideMountain() {
//        Position position = new Position(6, 11);
//        assertTrue(mountain.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPositionOutsideMountain() {
//        Position position = new Position(9, 14);
//        assertFalse(mountain.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPositionOnBoundary() {
//        Position position = new Position(7, 12);
//        assertTrue(mountain.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPath() {
//        Position start = new Position(5, 10);
//        Position end = new Position(7, 12);
//        assertTrue(mountain.blocksPath(start, end));
//    }
//
//    @Test
//    public void testDoesNotBlockPath() {
//        Position start = new Position(9, 14);
//        Position end = new Position(10, 15);
//        assertFalse(mountain.blocksPath(start, end));
//    }
//
//    @Test
//    public void testBlocksObstacleOverlapping() {
//        Position position = new Position(6, 11);
//        assertTrue(mountain.blocksObstacle(position));
//    }
//
//    @Test
//    public void testDoesNotBlockObstacleNotOverlapping() {
//        Position position = new Position(9, 14);
//        assertFalse(mountain.blocksObstacle(position));
//    }
//}
