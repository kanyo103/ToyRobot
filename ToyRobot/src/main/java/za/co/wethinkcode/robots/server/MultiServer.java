package za.co.wethinkcode.robots.server;

/**
 * will add more imports as needed
 */

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.flow.Recorder;
import za.co.wethinkcode.robots.HelpCommand;
import za.co.wethinkcode.robots.config.Config;
import za.co.wethinkcode.robots.maze.CustomMaze;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.world.TextIWorld;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static za.co.wethinkcode.robots.server.world.MultiServers.getRobots;

/**
 * Sets up the game world with obstacles, manages client sessions, and interprets server-side commands.
 */
public class MultiServer {
    private static TextIWorld world;  // shared world
    private static final ArrayList<Socket> clientSockets = new ArrayList<>();  // list of all clients connected

    /**
     * Entry point of the server application.
     * Establishes server setup, processes incoming client connections,
     * and interprets server instructions like 'quit', 'dump', 'help', and 'robots'.
     *
     * @param args Command-line arguments that may determine configuration options.
     * @throws IOException When connection issues occur.
     */

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("config"))
            Config.createConfigFileFromInput();

        File file = new File("config.json");
        if (!file.exists()) {
            try {
                Config.createDefaultConfigFile();
            } catch (IOException e) {
                System.out.println("Could not create config file.");
            }
        }

        JsonNode config = Protocol.interpretJSONtoObjectNode(file).path("world");

        int width = config.path("width").asInt();
        int height = config.path("height").asInt();
        int numberOfObstacles = config.path("numberOfObstacles").asInt();
        int visibility = config.path("visibility").asInt();
        int repairTime = config.path("repairTime").asInt();
        int reloadTime = config.path("reloadTime").asInt();
        int maxShieldStrength = config.path("maxShieldStrength").asInt();

        world = new TextIWorld(
                new CustomMaze(numberOfObstacles,
                        new Position(-width / 2, height / 2),
                        new Position(width / 2, -height / 2)),
                visibility,
                repairTime,
                reloadTime,
                maxShieldStrength) {
            @Override
            public void removeRobot(Robot robot) {

            }
        };
        System.out.println("World created with size: " + width + "x" + height + " with " +
                numberOfObstacles + " obstacle(s)." +
                "\nVisibility: " + visibility + " kliks" +
                "\nRepair time: " + repairTime + "s\nReload time: " + reloadTime + "s" +
                "\nMax shield strength: " + maxShieldStrength + "\n");

        ServerSocket s = new ServerSocket(za.co.wethinkcode.robots.server.SimpleServer.PORT);
        System.out.println("SimpleServer details");
        System.out.println("IP address: " + InetAddress.getLocalHost().getHostAddress() + "\tPort: " + za.co.wethinkcode.robots.server.SimpleServer.PORT);

        System.out.println("SimpleServer running & waiting for client connections.");
        serverCommands(s);  //server commands thread

// Loop to accept and handle incoming client connections
        while (!s.isClosed()) {
            try {
                Socket socket = s.accept();
                clientSockets.add(socket);
                System.out.println("Connection: " + socket);

                Runnable r = new SimpleServer(socket, world);
                Thread task = new Thread(r);
                task.start();
            } catch (IOException ex) {
                System.out.println("Shutting down server...");
            }
        }
        System.out.println("SimpleServer shutdown");
    }
    static {new Recorder().logRun();}

    /**
     * Interprets server-specific console commands.
     * Available commands include:
     * - help: Shows available server commands.
     * - quit: Terminates the server and all connections.
     * - dump: Outputs all current obstacles and robot details.
     * - robots: Prints data on each robot in the environment.
     *
     * @param socket The server's main listening socket.
     */
    private static void serverCommands(ServerSocket socket) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message;
                    System.out.println("Enter a command: ");
                    while ((message = in.readLine()) != null) {
                        if (message.equalsIgnoreCase("help")) {
                            HelpCommand.serverCommands();
                        } else if (message.equalsIgnoreCase("quit")) {
                            socket.close();
                            for (Socket s : clientSockets) {
                                s.close();
                            }
                            return;
                        } else if (message.equalsIgnoreCase("dump")) {
                            dumpCommand();
                        } else if (message.equalsIgnoreCase("robots")) {
                            getRobots(true);
                        } else System.out.println("Unsupported command\n");
                        System.out.println("Enter a command: ");
                    }
                } catch (IOException ex) {
                    System.out.println("Shutting down single client server");
                }
            }
        });
        task.start();
    }

    /**
     * Executes the 'dump' functionality which prints out all obstacles and robot entities.
     */
    private static void dumpCommand() {
        //show obstacles in world
        world.showObstacles();
        //show robots in world
        getRobots(false);
        display2DWorld();
    }

    /**
     * Renders a grid-based visual of the world with symbols indicating robots and obstacle types.
     * The layout is centered around the origin and marked using characters.
     */
    private static void display2DWorld() {
        // Create a grid representation of the world
        int width = world.getWidth();
        int height = world.getHeight();
        String[][] grid = new String[height][width];

        // Initialize grid with empty spaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ".";
            }
        }
    }
}