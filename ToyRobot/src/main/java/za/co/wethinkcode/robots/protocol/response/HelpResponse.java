package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Robot;

/**
 * Generate response messages for the help command.
 */
public class HelpResponse {

    /**
     * Generates a response message for the help command.
     *
     * @param robot   The robot whose state is being updated.
     * @return A JSON-formatted response message for the turn command.
     */
    public static String helpResponse(Robot robot){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "Ok");

        ObjectNode data = Protocol.createObjectNode();
        data.put("message", "Help");
        responseMessage.set("data", data);

        return responseMessage.toString();
    }
}


