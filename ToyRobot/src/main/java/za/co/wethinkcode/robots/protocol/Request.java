package za.co.wethinkcode.robots.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * The Request class parses JSON-formatted request messages and extracts relevant information.
 */
public class Request {
    private final String[] arguments;
    private final String command;
    private final String name;

    /**
     * Constructs a Request object to parse the given JSON-formatted request message.
     *
     * @param messageFromClient The JSON-formatted request message from the client.
     * @throws JsonProcessingException If there is an error processing JSON data.
     */
    public Request(String messageFromClient) throws JsonProcessingException {
        JsonNode requestNode = Protocol.interpretJSONtoObjectNode(messageFromClient);
        JsonNode argumentsNode = requestNode.get("arguments");
        List<String> argsList = new ArrayList<>();
        if (argumentsNode != null && argumentsNode.isArray()) {
            // Iterate over the array elements and add them to argsList
            for (JsonNode arg : argumentsNode) {
                argsList.add(arg.asText());
            }
        }
        // Convert the list to an array and assign it to the arguments field
        this.arguments = argsList.toArray(new String[0]);
        // Extract and assign the command field from JSON
        this.command = requestNode.get("command").asText();
        // Extract and assign the name field from JSON
        this.name = requestNode.get("robot").asText();
    }

    public String[] getArguments() {
        return arguments;
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }
}