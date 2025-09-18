package za.co.wethinkcode.robots.server;

import za.co.wethinkcode.robots.protocol.ServerProtocol;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.world.TextIWorld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/**
 * The SimpleServer class represents a basic server implementation that handles communication with a single client.
 */
public class SimpleServer implements Runnable {

    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private final TextIWorld world;
    private Robot robot;  // Robot associated with this client

    public SimpleServer(Socket socket, TextIWorld world) throws IOException {
        this.clientMachine = socket.getInetAddress().getHostName();
        this.world = world;
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    public TextIWorld getWorld(){return world;}

    public void run() {
        try {
            String messageFromClient;
            while((messageFromClient = in.readLine()) != null) {
                System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                String response = ServerProtocol.processRequest(messageFromClient, this);
                out.println(response);
            }
        } catch(IOException ex) {
            System.out.println("Shutting down robot: " + robot.getName());
        } finally {
            closeQuietly();
        }
    }


    private void closeQuietly() {
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }
}