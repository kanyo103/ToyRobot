package za.co.wethinkcode.robots.protocol.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;

public class InvalidResponse {

    public static String unsupportedCommandResponse(){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "ERROR");

        ObjectNode data = Protocol.createObjectNode();
        data.put("message", "Unsupported command");
        responseMessage.set("data", data);

        return responseMessage.toString();
    }

    public static String unsupportedArgumentsResponse(){
        ObjectNode responseMessage = Protocol.createObjectNode();
        responseMessage.put("result", "ERROR");

        ObjectNode data = Protocol.createObjectNode();
        data.put("message", "Could not parse arguments");
        responseMessage.set("data", data);

        return responseMessage.toString();
    }

}
