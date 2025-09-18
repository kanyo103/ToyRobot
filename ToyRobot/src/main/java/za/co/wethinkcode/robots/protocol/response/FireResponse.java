package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;

public class FireResponse {

    public static String fireHitResponse(String name, int distance, int shots, ObjectNode state) throws JsonProcessingException {
        ObjectNode responseNode = Protocol.createObjectNode();
        responseNode.put("result", "OK");

        ObjectNode dataNode = responseNode.putObject("data");
        dataNode.put("message", "Hit");
        dataNode.put("distance", distance);
        dataNode.put("robot", name);
        dataNode.set("state", state);

        ObjectNode stateNode = responseNode.putObject("state");
        stateNode.put("shots", shots);

        return responseNode.toString();
    }

    public static String fireMissedResponse(int shots) {
        ObjectNode responseNode = Protocol.createObjectNode();
        responseNode.put("result", "OK");

        ObjectNode dataNode = responseNode.putObject("data");
        dataNode.put("message", "Miss");

        ObjectNode stateNode = responseNode.putObject("state");
        stateNode.put("shots", shots);

        return responseNode.toString();
    }

    public static String fireNoShotsResponse(int shots) {
        ObjectNode responseNode = Protocol.createObjectNode();
        responseNode.put("result", "OK");

        ObjectNode dataNode = responseNode.putObject("data");
        dataNode.put("message", "No more shots.");

        ObjectNode stateNode = responseNode.putObject("state");
        stateNode.put("shots", shots);

        return responseNode.toString();
    }
}
