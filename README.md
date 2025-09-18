# Toy Robot World

This project is made up of two main components: the Server and the Client. The Server is responsible for managing the virtual world, including its robots, obstacles, and other elements. The Client is used to launch a robot into that world and control its actions by sending commands to the Server and handling its responses.

## How to run the project

#### Clone the Repository

```bash
git clone git@github.com:kanyo103/ToyRobot.git
```
### Compile Code

```bash
mvn compile
````

### Create a Package

```bash
mvn package
````

### To run Tests

```bash
mvn test
```

### To skip tests Tests

```bash
mvn clean package -DskipTests
```

### TO START THE SERVER

```bash
mvn exec:java@run-server
```
### TO START THE CLIENT

```bash
mvn exec:java@run-client
```
### 


Features:

    🧠 Multiple client types: SimpleClient: new Robot
    🛡️ Robot commands: move, turn, fire, repair, look, state, etc.
    🗺️ Virtual world with obstacles
    ⚔️ Multiplayer support via server-client architecture
    🧪 Unit testing enabled with JUnit
    📦 Built using Maven

🗂️ Project Structure

toyrobot/
├── README.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │       └── za/
│   │           └── co/
│   │               └── wethinkcode/
│   │                   └── robots/
│   │                       ├── client/       # Client apps
│   │                       ├── protocol/     # JSON communication models and command handlers
│   │                       ├── config/       # configuration
│   │                       ├── maze/         #  maze
│   │                       ├── robot/        #robtot logic
│   │                       ├── server/       # Server-side logic and world
│   │                       └── utils/        # utilities
│   │   
│   └── test/
│       └── java/   #unit tests

🚀 Getting Started

This project is a Java project built with maven and can run it through the terminal or an IDE like IntelliJ.

✅ Prerequisites

    Java 17+
    Maven 3.x

🧪 Build, Test & Run

You may use IntelliJ to run your code and tests, but alternatively you can use the Maven build tool:


    First ensure you are in the root directory of the project after cloning
    To compile your code, run: mvn compile
    To create the required package, run: mvn package
    To run the tests: mvn test
    To run your server: java -cp target/robot-world-0.0.2-jar-with-dependencies.jar za.co.wethinkcode.robots.server.Server
    To run your client: java -cp target/robot-world-0.0.2-jar-with-dependencies.jar za.co.wethinkcode.robots.client.Play {IP_number} {Port_number} 
