//package za.co.wethinkcode.robots.test;
//
//import org.junit.jupiter.api.*;
//import za.co.wethinkcode.robots.server.MultiServer;
//import za.co.wethinkcode.robots.robot.Robot;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class MultiSimpleServerTest {
//
//    private MultiServer server;
//    private Thread serverThread;
//
//    @BeforeEach
//    void setUp() {
//        server = new MultiServer();
//        serverThread = new Thread(() -> {
//            try {
//                server.start();
//            } catch (IOException e) {
//                // SimpleServer might stop during test, that's fine
//            }
//        });
//        serverThread.start();
//
//        try {
//            Thread.sleep(300); // Let server start
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        server.stop();
//        try {
//            serverThread.join(1000); // wait for thread to stop
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void testRobotIsRegisteredAfterConnection() throws IOException {
//        Socket clientSocket = new Socket("localhost", MultiServer.PORT);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
//
//        String prompt = reader.readLine();
//        assertTrue(prompt.contains("Enter your robot's name"));
//
//        String testRobotName = "TestBot123";
//        writer.println(testRobotName);
//
//        try {
//            Thread.sleep(200); // Let server register robot
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ConcurrentHashMap<String, Robot> robots = MultiServer.getRobots();
//        assertTrue(robots.containsKey(testRobotName));
//        assertEquals(testRobotName, robots.get(testRobotName).getName());
//
//        clientSocket.close();
//    }
//}
//
