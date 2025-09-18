package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Robot;

public class RepairResponse {
    public static String repairResponse(Robot robot, String message){
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
        state.put("status", robot.getStatus().toString());
        responseMessage.set("state", state);
        return responseMessage.toString();
    }
}
