package za.co.wethinkcode.robots.config;

import za.co.wethinkcode.robots.protocol.ServerProtocol;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for creating and managing configuration files for the robot world.
 */
public class Config {

//    public static final String DEFAULT_CONFIG_FILE = "config.json";

    /**
     * Creates a configuration file with the specified content and filename.
     *
     * @param configContent The content to write to the configuration file.

     * @throws IOException If an I/O error occurs during writing.
     */
//    public static void createConfigFile(String configContent, String filename) throws IOException {
//        try (FileWriter writer = new FileWriter(filename, false)) {
//            writer.write(configContent);
//        }
//    }
        public static void createConfigFile(String configContent) throws IOException {
        FileWriter myWriter = new FileWriter("config.json", false);
        myWriter.write(configContent);
        myWriter.close();
    }

    /**
     * Creates a configuration file with the specified content,
     * using the default filename ("config.json").
     *
     * @param configContent The content to write to the configuration file.
     * @throws IOException If an I/O error occurs during writing.
     */
//    public static void createConfigFile(String configContent) throws IOException {
//        createConfigFile(configContent, DEFAULT_CONFIG_FILE);
//    }

    /**
     * Creates a default configuration file with predefined settings,
     * writing to the default filename.
     *
     * @throws IOException If an I/O error occurs during writing.
     */
//    public static void createDefaultConfigFile() throws IOException {
//        createDefaultConfigFile(DEFAULT_CONFIG_FILE);
//    }

    /**
     * Creates a default configuration file with predefined settings,
     * writing to the given filename.
     *

     * @throws IOException If an I/O error occurs during writing.
     */
    public static void createDefaultConfigFile() throws IOException {
        Config.createConfigFile( "{\n" +
                "  \"world\": {\n" +
                "    \"width\": 200,\n" +
                "    \"height\": 200,\n" +
                "    \"numberOfObstacles\": 20,\n" +
                "    \"visibility\": 10,\n" +
                "    \"repairTime\": 10,\n" +
                "    \"reloadTime\": 5,\n" +
                "    \"maxShieldStrength\": 7\n" +
                "  },\n" +
                "  \"make\": [\n" +
                "    {\n" +
                "      \"name\": \"sniper\",\n" +
                "      \"shieldStrength\": 1,\n" +
                "      \"numShots\": 2,\n" +
                "      \"shotDistance\": 9\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"tank\",\n" +
                "      \"shieldStrength\": 7,\n" +
                "      \"numShots\": 2,\n" +
                "      \"shotDistance\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"explorer\",\n" +
                "      \"shieldStrength\": 4,\n" +
                "      \"numShots\": 3,\n" +
                "      \"shotDistance\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}");

    }

    /**
     * Creates a configuration file from user input,
     * writing to the default filename.
     *
     * @throws IOException If an I/O error occurs during writing.
     */
    public static void createConfigFileFromInput() throws IOException {
        String configJSON = ServerProtocol.createConfigJSON();
        createConfigFile(configJSON);
    }
}
