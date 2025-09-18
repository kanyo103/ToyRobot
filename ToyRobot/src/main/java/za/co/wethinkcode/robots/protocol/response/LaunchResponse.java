package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Robot;

/**
 * Generate response messages for the launch command.
 */
public class LaunchResponse {

    /**
     * Generates a response message for a successful launch.
     *
     * @param robot     The robot that has been launched.
     * @param visibility The visibility range of the robot.
     * @param reload     The reload time of the robot.
     * @param repair     The repair time of the robot.
     * @return A JSON-formatted response message for a successful launch.
     */
    public static String launchSuccessfulResponse(Robot robot, Integer visibility, Integer reload, Integer repair){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "OK");

        ObjectNode data = Protocol.createObjectNode();
        ArrayNode positionArray = Protocol.createArrayNode();
        positionArray.add(robot.getPosition().getX());
        positionArray.add(robot.getPosition().getY());
        data.set("position", positionArray);
        data.put("visibility", visibility);
        data.put("reload", reload);
        data.put("repair", repair);
        data.put("shield", robot.getShields());
        responseMessage.set("data", data);

        ObjectNode state = Protocol.createObjectNode();
        state.set("position", positionArray);
        state.put("direction", "NORTH");
        state.put("shields", robot.getShields());
        state.put("shots", robot.getShots());
        state.put("status", robot.getStatus().toString());
        responseMessage.set("state", state);

        return responseMessage.toString();
    }

    /**
     * Generates a response message when there is no space in the world for a new robot launch.
     *
     * @return A JSON-formatted response message indicating no space for launch.
     */
    public static String launchNoSpaceResponse(){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "ERROR");
        ObjectNode data = Protocol.createObjectNode();
        data.put("message", "No more space in this world");
        responseMessage.set("data", data);
        return responseMessage.toString();
    }

    /**
     * Generates a response message when the name for a new robot launch is not unique in the world.
     *
     * @return A JSON-formatted response message indicating non-unique name.
     */
    public static String launchNotUniqueNameResponse(){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "ERROR");
        ObjectNode data = Protocol.createObjectNode();
        data.put("message", "Too many of you in this world");
        responseMessage.set("data", data);
        return responseMessage.toString();
    }
}


