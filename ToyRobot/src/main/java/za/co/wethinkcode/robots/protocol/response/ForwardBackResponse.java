package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Robot;

/**
 * Generate a response message for forward and back commands.
 */
public class ForwardBackResponse {

    /**
     * Generates a response message for forward and back commands.
     *
     * @param message The message to include in the response.
     * @param robot   The robot for which the response is generated.
     * @return A JSON-formatted response message.
     */
    public static String forwardBackResponse(String message, Robot robot){
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
        return responseMessage.toString();
    }
}

