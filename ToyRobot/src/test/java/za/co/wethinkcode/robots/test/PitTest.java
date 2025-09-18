//package za.co.wethinkcode.robots.test;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import robotworld.world.IWorld;
//import robotworld.robot.Position;
//
//public class PitTest {
//
//    private Pit pit;
//    private final int bottomLeftX = 3;
//    private final int bottomLeftY = 5;
//
//    @BeforeEach
//    public void setUp() {
//        pit = new Pit(bottomLeftX, bottomLeftY);
//    }
//
//    @Test
//    public void testGetBottomLeftX() {
//        assertEquals(bottomLeftX, pit.getBottomLeftX());
//    }
//
//    @Test
//    public void testGetBottomLeftY() {
//        assertEquals(bottomLeftY, pit.getBottomLeftY());
//    }
//
//    @Test
//    public void testGetSize() {
//        assertEquals(2, pit.getSize());
//    }
//
//    @Test
//    public void testGetType() {
//        assertEquals(IWorld.ObstacleType.PIT, pit.getType());
//    }
//
//    @Test
//    public void testBlocksPositionInsidePit() {
//        Position position = new Position(3, 5);
//        assertFalse(pit.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPositionOutsidePit() {
//        Position position = new Position(5, 7);
//        assertFalse(pit.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPositionOnBoundary() {
//        Position position = new Position(4, 6);
//        assertFalse(pit.blocksPosition(position));
//    }
//
//    @Test
//    public void testBlocksPath() {
//        Position start = new Position(3, 5);
//        Position end = new Position(4, 6);
//        assertFalse(pit.blocksPath(start, end));
//    }
//
//    @Test
//    public void testDoesNotBlockPath() {
//        Position start = new Position(6, 8);
//        Position end = new Position(7, 9);
//        assertFalse(pit.blocksPath(start, end));
//    }
//
//    @Test
//    public void testBlocksObstacleOverlapping() {
//        Position position = new Position(3, 5);
//        assertFalse(pit.blocksObstacle(position));
//    }
//
//    @Test
//    public void testDoesNotBlockObstacleNotOverlapping() {
//        Position position = new Position(6, 8);
//        assertFalse(pit.blocksObstacle(position));
//    }
//}
