//package za.co.wethinkcode.robots.server.world;
//
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import za.co.wethinkcode.robots.server.robot.*;
//import za.co.wethinkcode.robots.server.world.obstacle.*;
//import za.co.wethinkcode.robots.maze.CustomMaze;
//
//import java.util.*;
//
//class AbstractWorldTest {
//
//    // Minimal concrete subclass to instantiate AbstractWorld
//    static class TestIWorld extends AbstractWorld {
//        public TestIWorld(CustomMaze maze, int visibility, int repairTime, int reloadTime, int maxShieldStrength) {
//            super(maze, visibility, repairTime, reloadTime, maxShieldStrength);
//        }
//    }
//
//    private AbstractIWorld world;
//    private CustomMaze maze;
//    private Position topLeft = new Position(0, 0);
//    private Position bottomRight = new Position(10, 10);
//
//    @BeforeEach
//    void setup() {
//        // Create a stub CustomMaze that returns fixed positions and obstacles list
//        maze = new CustomMaze() {
//            @Override
//            public List<Obstacle> getObstacles() {
//                // We'll override this in tests where needed
//                return new ArrayList<>();
//            }
//            @Override
//            public Position getTOP_LEFT() {
//                return topLeft;
//            }
//            @Override
//            public Position getBOTTOM_RIGHT() {
//                return bottomRight;
//            }
//        };
//
//        // Override fields on the maze to satisfy AbstractWorld's fields (they are public)
//        maze.TOP_LEFT = topLeft;
//        maze.BOTTOM_RIGHT = bottomRight;
//
//        world = new TestWorld(maze, 5, 10, 5, 7);
//    }
//
//    @Test
//    void testIsWithinBoundary() {
//        Position inside = new Position(5, 5);
//        Position outside = new Position(15, 15);
//        assertTrue(world.isWithinBoundary(inside));
//        assertFalse(world.isWithinBoundary(outside));
//    }
//
//    @Test
//    void testCalculateNewPosition() {
//        Robot robot = new Robot("Robo", new Position(5, 5), Direction.NORTH, 1, 1, 1);
//        Position posNorth = world.calculateNewPosition(3, robot);
//        assertEquals(new Position(5, 8), posNorth);
//
//        robot.setCurrentDirection(Direction.EAST);
//        Position posEast = world.calculateNewPosition(2, robot);
//        assertEquals(new Position(7, 5), posEast);
//
//        robot.setCurrentDirection(Direction.SOUTH);
//        Position posSouth = world.calculateNewPosition(1, robot);
//        assertEquals(new Position(5, 4), posSouth);
//
//        robot.setCurrentDirection(Direction.WEST);
//        Position posWest = world.calculateNewPosition(4, robot);
//        assertEquals(new Position(1, 5), posWest);
//    }
//
//    @Test
//    void testIsPositionBlockedByRobot() {
//        Robot r1 = new Robot("R1", new Position(1, 1), Direction.NORTH, 1, 1, 1);
//        Robot r2 = new Robot("R2", new Position(2, 2), Direction.EAST, 1, 1, 1);
//
//        world.addRobot(r1);
//        assertTrue(world.isPositionBlockedByRobot(new Position(1, 1)));
//        assertFalse(world.isPositionBlockedByRobot(new Position(3, 3)));
//
//        world.addRobot(r2);
//        assertTrue(world.isPositionBlockedByRobot(new Position(2, 2)));
//    }
//
//    @Test
//    void testIsPathBlockedByRobot() {
//        Robot r1 = new Robot("R1", new Position(3, 3), Direction.NORTH, 1, 1, 1);
//        Robot r2 = new Robot("R2", new Position(5, 5), Direction.EAST, 1, 1, 1);
//
//        world.addRobot(r1);
//        world.addRobot(r2);
//
//        Position start = new Position(1, 1);
//        Position end = new Position(6, 6);
//
//        // Path crosses robots at (3,3) and (5,5)
//        assertTrue(world.isPathBlockedByRobot(start, end));
//
//        // Path not blocked
//        Position start2 = new Position(0, 0);
//        Position end2 = new Position(2, 2);
//        assertFalse(world.isPathBlockedByRobot(start2, end2));
//    }
//
//}
