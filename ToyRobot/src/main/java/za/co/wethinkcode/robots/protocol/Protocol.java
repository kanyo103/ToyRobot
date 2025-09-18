package za.co.wethinkcode.robots.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

/**
 * The Protocol class provides utility methods for creating and handling JSON nodes using Jackson library.
 */
public class Protocol {
    static ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates and returns a new ObjectNode.
     * @return a new ObjectNode.
     */
    public static ObjectNode createObjectNode(){
        return mapper.createObjectNode();
    }

    /**
     * Creates and returns a new ArrayNode.
     * @return a new ArrayNode.
     */
    public static ArrayNode createArrayNode(){
        return mapper.createArrayNode();
    }

    /**
     * Converts an object to its JSON string representation.
     * @param object the object to convert.
     * @return the JSON string representation of the object.
     * @throws JsonProcessingException if an error occurs during JSON processing.
     */
    public static String nodeAsString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
    /**
     * Parses a JSON string and returns an ObjectNode.
     * @param response the JSON string to parse.
     * @return the parsed ObjectNode.
     * @throws JsonProcessingException if an error occurs during JSON processing.
     */
    public static ObjectNode interpretJSONtoObjectNode(String response) throws JsonProcessingException {
        return (ObjectNode) mapper.readTree(response);
    }

    /**
     * Parses a JSON file and returns an ObjectNode.
     * @param file the JSON file to parse.
     * @return the parsed ObjectNode.
     * @throws IOException if an error occurs during file I/O.
     */
    public static ObjectNode interpretJSONtoObjectNode(File file) throws IOException {
        return (ObjectNode) mapper.readTree(file);
    }
}
