//package za.co.wethinkcode.robots.test;
//
//import static org.mockito.Mockito.*;
//
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robots.robot.Position;
//import za.co.wethinkcode.robots.robot.Robot;
//import za.co.wethinkcode.robots.server.world.AbstractWorld;
//import za.co.wethinkcode.robots.server.world.LookHelper;
//import za.co.wethinkcode.robots.server.world.IWorld;
//import za.co.wethinkcode.robots.server.world.obstacles.Mountain;
//import za.co.wethinkcode.robots.server.world.obstacles.Obstacle;
//import za.co.wethinkcode.robots.server.world.obstacles.WorldObject;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LookHelperTest {
//    private LookHelper lookHelper;
//    private Robot robot;
//    private AbstractWorld world;
//    private Map<Position, Object> worldObstacles;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        robot = mock(Robot.class);
//        world = mock(AbstractWorld.class);
//        when(world.getTOP_LEFT()).thenReturn(new Position(-5,5));
//        when(world.getBOTTOM_RIGHT()).thenReturn(new Position(5,-5));
//        when(world.getVisibility()).thenReturn(7);
//        when(robot.getPosition()).thenReturn(new Position(0,0));
//        worldObstacles = new HashMap<>();
//    }
//    @Test
//    public void testLookAround_EmptyWorld() {
//        when(world.getObstacles()).thenReturn(new ArrayList<>());
//        LookHelper.lookAround(robot, world);
//        ArrayList<WorldObject> objects = LookHelper.getDetectedObjectsForTest();
//        assertEquals(4, objects.size(), "Should detect 4 edges.");
//    }
//
//    @Test
//    public void testLookAround_WithObstacles() {
//        Position obstaclePosition = new Position(7, 5);
//        Obstacle obstacle = new Mountain(obstaclePosition.getX(), obstaclePosition.getY());
//        when(world.getObstacles()).thenReturn(Arrays.asList(obstacle));
//        when(world.getWorldObstacles()).thenReturn(worldObstacles);
//
//
//        LookHelper.lookAround(robot, world);
//        ArrayList<WorldObject> objects = LookHelper.getDetectedObjectsForTest();
//        assertEquals(4, objects.size(), "Should detect 3 edges and 1 obstacle.");
//    }
//
//
//
//    @Test
//    public void testGetDistance() {
//        assertEquals(5, LookHelper.getDistance(10, 5));
//        assertEquals(5, LookHelper.getDistance(5, 10));
//    }
//
//    @Test
//    public void testCreateObject() {
//        ObjectNode objectNode = LookHelper.createObject(IWorld.Direction.NORTH, IWorld.ObstacleType.LAKE, 5);
//        assertEquals("NORTH", objectNode.get("direction").asText());
//        assertEquals("LAKE", objectNode.get("type").asText());
//        assertEquals(5, objectNode.get("distance").asInt());
//    }
//}
