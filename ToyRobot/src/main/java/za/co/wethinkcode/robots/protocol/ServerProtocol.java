package za.co.wethinkcode.robots.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import za.co.wethinkcode.robots.protocol.response.*;
import za.co.wethinkcode.robots.robot.Make;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.RobotStatus;
import za.co.wethinkcode.robots.server.SimpleServer;
import za.co.wethinkcode.robots.utils.Utilities;
import za.co.wethinkcode.robots.server.world.IWorld;
import za.co.wethinkcode.robots.server.world.TextIWorld;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * The ServerProtocol class is responsible for processing client requests
 * and generating appropriate responses. It handles commands related to
 * launching, moving, and managing the state of robots in the world.
 */
public class ServerProtocol {

    /**
     * Processes the incoming request from the client and generates a response.
     *
     * @param messageFromClient The message received from the client.
     * @param server The server handling the request.
     * @return The response to be sent to the client.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    public static String processRequest(String messageFromClient, SimpleServer server) throws IOException {
        Request parser = new Request(messageFromClient);
        String command = parser.getCommand();
        return switch (command){
            case "launch" -> launch(parser, server);
            case "forward", "back" -> forwardBack(parser, server);
            case "turn" -> turn(parser, server);
            case "state" -> state(parser, server);
            case "look" -> look(parser, server);
            case "repair" -> repair(parser, server);
            case "reload" -> reload(parser, server);
            case "fire" -> fire(parser, server);
            case "help" -> help(parser, server);
            default -> InvalidResponse.unsupportedCommandResponse();
        };
    }

    /**
     * Launches a new robot into the world.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */
    private static String launch(Request parser, SimpleServer server) throws IOException {
        String robot_name = parser.getName();
        TextIWorld world = server.getWorld();
        String[] arguments = parser.getArguments();
        String kind = arguments[0];

        if (robot_name.isEmpty()){
            return InvalidResponse.unsupportedArgumentsResponse();
        }
        if (world.validName(robot_name)){
            if (world.enoughSpace()){
                Position emptyPosition = world.getEmptySpot();
                Make make = new Make(kind); // Parameters will be set based on the kind inside the Make constructor
                Robot newRobot = new Robot(robot_name, emptyPosition, make);
                newRobot.setMaxShields(Math.min(world.getMaxShieldStrength(), newRobot.getShields()));
                world.addRobot(newRobot);
                server.setRobot(newRobot);
                return LaunchResponse.launchSuccessfulResponse(newRobot, world.getVisibility(), world.getReloadTime(), world.getRepairTime());
            }
            return LaunchResponse.launchNoSpaceResponse();
        }
        return LaunchResponse.launchNotUniqueNameResponse();
    }

    /**
     * Moves the robot forward or backward in the world.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */
    private static String forwardBack(Request parser, SimpleServer server) {
        Robot robot = server.getRobot();
        TextIWorld world = server.getWorld();
        IWorld.UpdateResponse response;
        if (parser.getArguments().length == 0 || !Utilities.isInteger(parser.getArguments()[0])){
            return InvalidResponse.unsupportedArgumentsResponse();
        }
        if (robot.getStatus() != RobotStatus.NORMAL || robot.getStatus() == RobotStatus.DEAD){
            return ForwardBackResponse.forwardBackResponse(robot.getStatus().toString(), robot);
        }
        response = robot.move(parser.getCommand(), parser.getArguments(), world);
        if (response == IWorld.UpdateResponse.SUCCESS) {
            return ForwardBackResponse.forwardBackResponse("Done", robot);
        } else {
            assert response != null;
            return ForwardBackResponse.forwardBackResponse(response.toString(), robot);
        }
    }
    /**
     * Turns the robot left or right in the world.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */
    private static String turn(Request parser, SimpleServer server) {
        Robot robot = server.getRobot();
        TextIWorld world = server.getWorld();
        if (parser.getArguments().length == 0 || !Utilities.isRightOrLeft(parser.getArguments()[0])){
            return InvalidResponse.unsupportedArgumentsResponse();
        }
        if (robot.getStatus() != RobotStatus.NORMAL || robot.getStatus() == RobotStatus.DEAD){
            return TurnResponse.turnResponse(robot.getStatus().toString(), robot);
        }
        robot.turn(parser.getArguments(), world);
        return TurnResponse.turnResponse("Done", robot);
    }

    /**
     * Handles the "state" command from the client.
     *
     * @param parser The request parser containing parsed data.
     * @return The response to the client.
     */
    static String state(Request parser, SimpleServer server) {
        if(hasArgs(parser)){
            return InvalidResponse.unsupportedArgumentsResponse();
        }
        return StateResponse.createStateResponse(server.getRobot());
    }

    /**
     * Makes the robot look around its environment.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */
    private static String look(Request parser, SimpleServer server) {
        if(hasArgs(parser)){
            return InvalidResponse.unsupportedArgumentsResponse();
        }
        Robot robot = server.getRobot();
        TextIWorld world = server.getWorld();
        if (robot != null){
            return LookResponse.lookSuccessfulResponse(robot, world);
        }
        return LookResponse.robotDoesNotExistResponse();
    }

    /**
     * Repairs the robot.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */
    private static String repair(Request parser, SimpleServer server) {
        Robot robot = server.getRobot();
        if (robot.getStatus() == RobotStatus.NORMAL ){
            CompletableFuture.supplyAsync(() -> {
                try {
                    server.getRobot().setStatus(RobotStatus.REPAIR);
                    TimeUnit.SECONDS.sleep(server.getWorld().getRepairTime());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "Repairing";
            }).thenApplyAsync((pre) -> {
                server.getRobot().repair();
                return "Done repairing";
            });
            return RepairResponse.repairResponse(robot, "Done");
        }
        return RepairResponse.repairResponse(robot, robot.getStatus().toString());
    }

    /**
     * Reloads the robot.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */
    private static String reload(Request parser, SimpleServer server) {
        Robot robot = server.getRobot();
        if (robot.getStatus() == RobotStatus.NORMAL ) {
            CompletableFuture.supplyAsync(() -> {
                try {
                    server.getRobot().setStatus(RobotStatus.RELOAD);
                    TimeUnit.SECONDS.sleep(server.getWorld().getReloadTime());
                    return "Reloading";
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).thenApplyAsync((pre) -> {
                server.getRobot().reload();
                return "Done reloading";
            });
            return ReloadResponse.reloadResponse(robot, "Done");
        }
        return ReloadResponse.reloadResponse(robot, robot.getStatus().toString());
    }

    /**
     * Fires the robot's weapon.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    private static String fire(Request parser, SimpleServer server) throws JsonProcessingException {
        Robot robot = server.getRobot();
        IWorld world = server.getWorld();
        if (robot == null) return InvalidResponse.unsupportedCommandResponse();
        if (robot.getStatus() == RobotStatus.NORMAL) {
            if (robot.getShots() <= 0) return FireResponse.fireNoShotsResponse(robot.getShots());
            Robot.FireResult result = robot.fire(world);
            if (result.hit)
                return FireResponse.fireHitResponse(result.targetRobot.getName(), result.distance, result.shotsLeft, createStateNode(result.targetRobot));
            else return FireResponse.fireMissedResponse(result.shotsLeft);
        }else return InvalidStateResponse.invalidStateResponse(robot.getStatus());
    }

    /**
     * Return help commands for the robot.
     *
     * @param parser The request parser containing parsed data.
     * @param server The server handling the request.
     * @return The response to the client.
     */

    private static String help(Request parser, SimpleServer server)  {
        return HelpResponse.helpResponse(server.getRobot());
    }

    /**
     * Creates the state of the specified robot.
     *
     * @param robot The robot whose state is to be returned.
     * @return A JSON ObjectNode containing the robot's shield and shot information.
     */
    private static ObjectNode createStateNode(Robot robot) {
        ObjectNode state = Protocol.createObjectNode();
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        return state;
    }

    /**
     * Checks if the parser has any arguments.
     *
     * @param parser The request parser.
     * @return True if there are arguments, false otherwise.
     */
    private static Boolean hasArgs(Request parser ){
        return parser.getArguments().length > 0;
    }

    public static String createConfigJSON() throws IOException {
        String[] worldParams = {"width", "height", "numberOfObstacles", "visibility", "repairTime", "reloadTime", "maxShieldStrength"};

        ObjectNode worldObj = Protocol.createObjectNode();
        System.out.println("Enter the world parameters");
        System.out.println("==========================");
        for (String param : worldParams){
            worldObj.put(param, Utilities.getIntInput(param));
        }

        ObjectNode configJSON = Protocol.createObjectNode();
        configJSON.set("world", worldObj);

        return Protocol.nodeAsString(configJSON);
    }
}
