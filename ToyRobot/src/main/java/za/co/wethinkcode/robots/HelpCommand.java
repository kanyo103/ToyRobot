package za.co.wethinkcode.robots;

public class HelpCommand {

    public static void help(){
        // HELP COMMAND
        System.out.println(
                "\t\u001B[36m" +
                        "\t=======================================================================\n" +
                        "\t||                        🤖  ROBOT WORLD  🤖                         ||\n" +
                        "\t||                               CPT 11                       ||\n" +
                        "\t=======================================================================\n" +
                        "\t||  Welcome, Commander!                                              ||\n" +
                        "\t||  Take control of your robot and dominate the battlefield!        ||\n" +
                        "\t||                                                                   ||\n" +
                        "\t||  ROBOT MAKES:                                                     ||\n" +
                        "\t||    - sniper    : Long range, limited ammo                         ||\n" +
                        "\t||    - tank      : High defense, mid range                          ||\n" +
                        "\t||    - explorer  : Balanced and reliable                            ||\n" +
                        "\t||                                                                   ||\n" +
                        "\t||  LAUNCH COMMAND:                                                  ||\n" +
                        "\t||     Launch <robot_make> <robot_name>                              ||\n" +
                        "\t||             e.g - [ Launch sniper karabo]                         ||\n" +
                        "\t=======================================================================\u001B[0m\n"
        );
    }

    public static void handleClientCommands() {
        System.out.println(
                "\u001B[36m\n CLIENT COMMANDS: \n" +
                        "\t===============================================================\n" +
                        "\t* State                - Show current status of your robot     \n" +
                        "\t* Turn <left/right>    - Rotate robot                         \n" +
                        "\t* Forward <steps>      - Move robot forward                    \n" +
                        "\t* Back <steps>         - Move robot backward                   \n" +
                        "\t* Look                 - Scan the surrounding area             \n" +
                        "\t* Fire                 - Attack in your current direction      \n" +
                        "\t* Reload               - Reload weapon                         \n" +
                        "\t* Repair               - Repair robot’s shield                 \n" +
                        "\t* Help                 - Show this command menu                \n" +
                        "\t===============================================================\u001B[0m"
        );
    }

    public static void serverCommands() {
        System.out.println(
                "\u001B[36m\n SERVER COMMANDS: \n" +
                        "\t===============================================================\n" +
                        "\t* Quit      - Shutdown the server and disconnect all robots    \n" +
                        "\t* Dump      - Display full state of the world                  \n" +
                        "\t* Robots    - List all robots in the world                     \n" +
                        "\t* Help      - Show this server command menu                    \n" +
                        "\t===============================================================\u001B[0m"
        );
    }
}
