package za.co.wethinkcode.robots.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.robot.Make;

import java.io.IOException;
import java.util.Arrays;

/**
 * The ClientProtocol class provides methods for creating JSON-formatted request messages.
 */
public class ClientProtocol {

    /**
     * Creates a JSON-formatted request message for launching a robot.
     *
     * @param robotName        The name of the robot to be launched.
     * @param kind             The type of robot to be launched.
     * @param maxShieldStrength The maximum shield strength of the robot.
     * @param maxShots         The maximum number of shots the robot can fire.
     * @return A JSON-formatted request message for launching a robot.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    public static String createJSONLaunchRequest(String robotName, String kind, int maxShieldStrength, int maxShots) throws JsonProcessingException {
        ObjectNode requestMessage = Protocol.createObjectNode();

        requestMessage.put("robot", robotName);
        requestMessage.put("command", "launch");

        ArrayNode argumentsArray = Protocol.createArrayNode();
        argumentsArray.add(kind);
        argumentsArray.add(maxShieldStrength);
        argumentsArray.add(maxShots);
        requestMessage.set("arguments", argumentsArray);

        return Protocol.nodeAsString(requestMessage);
    }
    /**
     * Creates a JSON-formatted request message for moving a robot forward or backward.
     *
     * @param robotName The name of the robot to move.
     * @param command   The command indicating the direction of movement ("forward" or "back").
     * @param nrSteps   The number of steps to move.
     * @return A JSON-formatted request message for moving a robot.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    public static String createJSONForwardBackRequest(String robotName, String command, String nrSteps) throws JsonProcessingException {
        ObjectNode requestMessage = Protocol.createObjectNode();
        requestMessage.put("robot", robotName);
        requestMessage.put("command", command);

        ArrayNode argumentsArray = Protocol.createArrayNode();
        if (!nrSteps.isEmpty()){
            argumentsArray.add(nrSteps);
        }
        requestMessage.set("arguments", argumentsArray);
        return Protocol.nodeAsString(requestMessage);
    }

    /**
     * Creates a JSON-formatted request message for turning a robot.
     *
     * @param robotName The name of the robot to turn.
     * @param command   The command indicating the direction of turn ("left" or "right").
     * @param direction The direction to turn.
     * @return A JSON-formatted request message for turning a robot.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    public static String createJSONTurnRequest(String robotName, String command, String direction) throws JsonProcessingException {
        ObjectNode requestMessage = Protocol.createObjectNode();
        requestMessage.put("robot", robotName);
        requestMessage.put("command", command);

        ArrayNode argumentsArray = Protocol.createArrayNode();
        argumentsArray.add(direction);
        requestMessage.set("arguments", argumentsArray);

        return Protocol.nodeAsString(requestMessage);
    }

    /*
    Simple requests are requests where no arguments are provided.
    Only the robot name and command are included.
    */
    public static String createJSONSimpleRequest(String robotName, String command) throws JsonProcessingException {
        ObjectNode requestMessage = Protocol.createObjectNode();
        requestMessage.put("robot", robotName);
        requestMessage.put("command", command);

        ArrayNode argumentsArray = Protocol.createArrayNode();
        requestMessage.set("arguments", argumentsArray);

        return Protocol.nodeAsString(requestMessage);
    }

/**
 * Creates a JSON-formatted request message for simple commands with arguments.
 *
 * @param robotName The name of the robot.
 * @param command   The command to execute.
 * @param args      The arguments for the command.
 * @return A JSON-formatted request message for simple commands with arguments.
 * @throws JsonProcessingException If there is an error processing JSON data.
 */
public static String createJSONSimpleRequest(String robotName, String command, String[] args) throws JsonProcessingException {
    ObjectNode requestMessage = Protocol.createObjectNode();
    requestMessage.put("robot", robotName);
    requestMessage.put("command", command);

    ArrayNode argumentsArray = Protocol.createArrayNode();
    argumentsArray.add(Arrays.toString(args));
    requestMessage.set("arguments", argumentsArray);

    return Protocol.nodeAsString(requestMessage);
}

    /**
     * Builds a JSON-formatted request message based on the given instruction.
     *
     * @param instruction The instruction containing the command and optional arguments.
     * @param robotName   The name of the robot.
     * @return A JSON-formatted request message.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    public static String buildRequestMessage(String[] instruction, String robotName) throws IOException {
        String clientRequest;
        switch (instruction[0]) {
            case ("launch") -> {
                if (instruction.length != 3) {
                    clientRequest = createJSONLaunchRequest("", "", 0, 0);
                    break;
                }
                String kind = instruction[1];
                Make make = new Make(kind);
                robotName = instruction[2];
                clientRequest = createJSONLaunchRequest(robotName, kind, make.getShieldStrength(), make.getNumShots());
            }
            case ("forward"), ("back") -> {
                if (instruction.length != 2){
                    clientRequest = createJSONForwardBackRequest(robotName, instruction[0], "");
                    break;
                }
                clientRequest = createJSONForwardBackRequest(robotName, instruction[0], instruction[1]);
            }
            case ("turn") -> {
                if (instruction.length != 2){
                    clientRequest = createJSONTurnRequest(robotName, instruction[0], "");
                    break;
                }
                clientRequest = createJSONTurnRequest(robotName, instruction[0], instruction[1]);
            }
            case ("look"), ("state"), ("fire"), ("repair"), ("reload"), ("help") -> {
                if (instruction.length > 1){
                    clientRequest = createJSONSimpleRequest(robotName, instruction[0], Arrays.copyOfRange(instruction, 1, instruction.length));
                    break;
                }
                clientRequest = createJSONSimpleRequest(robotName, instruction[0]);
            }
            default -> clientRequest = createJSONSimpleRequest(robotName, "");
        }
        return clientRequest;
    }

}
