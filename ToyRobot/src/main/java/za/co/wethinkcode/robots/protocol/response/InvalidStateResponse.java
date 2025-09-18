package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.RobotStatus;

public class InvalidStateResponse {
    public static String invalidStateResponse(RobotStatus status){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "ERROR");

        ObjectNode data = Protocol.createObjectNode();
        data.put("message", status.toString());
        responseMessage.set("data", data);

        return responseMessage.toString();
    }
}
