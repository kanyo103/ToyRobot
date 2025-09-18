//package za.co.wethinkcode.robots.test;
//
//
//import static org.junit.Assert.assertEquals;
//import za.co.wethinkcode.robots.server.world.World;
//import org.junit.Before;
//import org.junit.Test;
//import za.co.wethinkcode.robots.robot.Robot;
//
//public class RobotTest {
//
//    private Robot robot;
//
//    // This method will run before each test, initializing the Robot instance.
//    @Before
//    public void setUp() {
//        World world = null;
//        robot = new Robot("Robo1", world);
//    }
//
//    // Test the constructor and getName() method.
//    @Test
//    public void testGetName() {
//        assertEquals("Robo1", robot.getName());
//    }
//
//    // Test the getStatus() method. The robot's initial position should be (0, 0) and direction "NORTH".
//    @Test
//    public void testGetStatus() {
//        String expectedStatus = "Robo1 at (0,0) facing NORTH";
//        assertEquals(expectedStatus, robot.getStatus());
//    }
//}
