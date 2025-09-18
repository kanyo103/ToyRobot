package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Robot;

/**
 * Generate response messages for the turn command.
 */
public class TurnResponse {

    /**
     * Generates a response message for the turn command.
     *
     * @param message The message indicating the result of the turn command.
     * @param robot   The robot whose state is being updated.
     * @return A JSON-formatted response message for the turn command.
     */
    public static String turnResponse(String message, Robot robot){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "OK");

        ObjectNode data = Protocol.createObjectNode();
        data.put("message", message);
        responseMessage.set("data", data);

        ArrayNode positionArray = Protocol.createArrayNode();
        positionArray.add(robot.getPosition().getX());
        positionArray.add(robot.getPosition().getY());

        ObjectNode state = Protocol.createObjectNode();
        state.set("position", positionArray);
        state.put("direction", robot.getCurrentDirection().toString());
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", String.valueOf(robot.getStatus()));
        responseMessage.set("state", state);

        return  responseMessage.toString();
    }
}

