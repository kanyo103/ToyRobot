package za.co.wethinkcode.robots.server.world;

import java.util.List;
import java.util.Map;
import za.co.wethinkcode.robots.server.world.obstacles.*;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.Position;


public interface IWorld {

    enum Direction {
        NORTH, EAST, SOUTH, WEST
    }

    enum ObstacleType {
        MOUNTAIN, LAKE, PIT, EDGE, ROBOT
    }


    enum UpdateResponse {
        SUCCESS,
        FAILED_OUTSIDE_WORLD,
        FAILED_OBSTRUCTED_OBSTACLE,
        FAILED_OBSTRUCTED_ROBOT,
        FAILED_PITFALL,
    }



    UpdateResponse updatePosition(int nrSteps, Robot robot);


    void updateDirection(boolean turnRight, Robot robot);


    boolean isWithinBoundary(Position position);


    boolean isPositionBlockedByObstacle(Position newPosition);

    boolean isPositionBlockedByRobot(Position newPosition);


    boolean isPathBlockedByObstacle(Position a, Position b);


    boolean isPathBlockedByRobot(Position a, Position b);


    List<Obstacle> getObstacles();

    void showObstacles();


    List<Robot> getRobots();


    boolean enoughSpace();


    boolean validName(String robotName);


    void addRobot(Robot robot);


    Position getEmptySpot();

    Position getTOP_LEFT();

    Position getBOTTOM_RIGHT();

    void updateWorldObstacles();

    Map<Position, Object> getWorldObstacles();


    Robot findRobotInRange(Position position, int range,Direction direction);



    void removeRobot(Robot robot);


}
