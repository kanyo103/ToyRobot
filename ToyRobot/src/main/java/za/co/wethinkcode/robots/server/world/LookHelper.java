package za.co.wethinkcode.robots.server.world;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import za.co.wethinkcode.robots.protocol.Protocol;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.world.obstacles.Obstacle;
import za.co.wethinkcode.robots.server.world.obstacles.WorldObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Helper class for managing the look functionality for robots in the world.
 * Implements methods to inspect the surroundings of a robot, detect obstacles, and generate a
 * JSON array node of the objects in view.
 */
public class LookHelper {

    private static ArrayList<WorldObject> objects = new ArrayList<>();
    private static ArrayNode objectsArray = Protocol.createArrayNode();

    /**
     * Inspects the surroundings of the given robot within the world and updates
     * the list of detected objects.
     *
     * @param robot The robot whose surroundings are being inspected.
     * @param world The world in which the robot is located.
     */
    public static void lookAround(Robot robot, AbstractWorld world) {
        objects.clear();
        Position robotPosition = robot.getPosition();
        int robotX = robotPosition.getX();
        int robotY = robotPosition.getY();

        int visibility = world.getVisibility();

        IWorld.ObstacleType type = IWorld.ObstacleType.EDGE;
        int distance;
        int size;
        IWorld.Direction direction;

        // Looks for edges of the world within visibility range
        if (getDistance(world.getTOP_LEFT().getY(), robotY) <= visibility) {
            distance = getDistance(world.getTOP_LEFT().getY(), robotY);
            objects.add(new WorldObject(IWorld.Direction.NORTH, distance, type));
        }

        if (getDistance(robotY, world.getBOTTOM_RIGHT().getY()) <= visibility) {
            distance = getDistance(robotY, world.getBOTTOM_RIGHT().getY());
            objects.add(new WorldObject(IWorld.Direction.SOUTH, distance, type));
        }

        if (getDistance(world.getTOP_LEFT().getX(), robotX) <= visibility) {
            distance = getDistance(robotX, world.getTOP_LEFT().getX());
            objects.add(new WorldObject(IWorld.Direction.WEST, distance, type));
        }

        if (getDistance(robotX, world.getBOTTOM_RIGHT().getX()) <= visibility) {
            distance = getDistance(world.getBOTTOM_RIGHT().getX(), robotX);
            objects.add(new WorldObject(IWorld.Direction.EAST, distance, type));
        }

        // Looks for obstacles and robots within visibility range
        for (Map.Entry<Position, Object> obs : world.getWorldObstacles().entrySet()) {
            if (obs.getValue() == robot) {
                continue;
            }

            int x = obs.getKey().getX();
            int y = obs.getKey().getY();

            if (obs.getValue() instanceof Obstacle) {
                type = ((Obstacle) obs.getValue()).getType();
                size = ((Obstacle) obs.getValue()).getSize();
            } else {
                type = IWorld.ObstacleType.ROBOT;
                size = 1;
            }

            if (x <= robotX && robotX < (x + size) && robotY < y && getDistance(y, robotY) <= visibility) {
                direction = IWorld.Direction.NORTH;
                distance = getDistance(y, robotY);
                objects.add(new WorldObject(direction, distance, type));

            } else if (x <= robotX && robotX < (x + size) && robotY > y && getDistance(robotY, y + size-1) <= visibility) {
                direction = IWorld.Direction.SOUTH;
                distance = getDistance(robotY, y + size) + 1;
                objects.add(new WorldObject(direction, distance, type));

            } else if (y <= robotY && robotY < (y + size) && robotX < x && getDistance(x, robotX) <= visibility) {
                direction = IWorld.Direction.EAST;
                distance = getDistance(x, robotX);
                objects.add(new WorldObject(direction, distance, type));

            } else if (y <= robotY && robotY < (y + size) && robotX > x && getDistance(robotX, x + size-1) <= visibility) {
                direction = IWorld.Direction.WEST;
                distance = getDistance(robotX, x + size) + 1;
                objects.add(new WorldObject(direction, distance, type));
            }
        }
        removeUnnecessaryObjects(objects);
    }


    /**
     * Removes objects from the list that are seen behind mountains in the same direction.
     *
     * @param list The list of objects to be filtered.
     */
    public static void removeUnnecessaryObjects(ArrayList<WorldObject> list) {
        IWorld.ObstacleType type;
        IWorld.Direction direction;
        int distance;
        ArrayList<WorldObject> newObjects = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            type = list.get(i).getType();
            direction = list.get(i).getDirection();
            distance = list.get(i).getDistanceFromRobot();

            //Removes all objects from list in a certain direction that are behind a mountain
            if (type == IWorld.ObstacleType.MOUNTAIN) {
                for (WorldObject object : list) {
                    if (object.getDirection() == direction && object.getDistanceFromRobot() > distance) {
                        newObjects.remove(object);
                    }
                }
            }
        }
        objects = newObjects;
    }

    /**
     * Generates a JSON array node representing the objects seen around the robot.
     *
     * @param robot The robot whose surroundings are being inspected.
     * @param world The world in which the robot is located.
     * @return A JSON array node representing the detected objects.
     */
    public static ArrayNode getArrayNode(Robot robot, AbstractWorld world) {
        lookAround(robot, world);
        objectsArray = Protocol.createArrayNode();
        for (WorldObject object : objects) {
            objectsArray.add(createObject(object.getDirection(), object.getType(), object.getDistanceFromRobot()));
        }
        return objectsArray;
    }

    /**
     * Calculates the distance between two points.
     *
     * @param a The first point.
     * @param b The second point.
     * @return The distance between the two points.
     */
    public static int getDistance(int a, int b) {
        int result = a - b;
        if (result < 0) return result * (-1);
        return result;
    }

    /**
     * Creates a JSON object node representing the objects seen around
     * the robot.
     *
     * @param direction The direction of the object relative to the robot.
     * @param type The type of the object.
     * @param distance The distance to object from the robot.
     * @return A JSON object node representing the objects seen around the robot.
     */
    public static ObjectNode createObject(IWorld.Direction direction, IWorld.ObstacleType type, int distance){
        ObjectNode object = Protocol.createObjectNode();
        object.put("direction", direction.name());
        object.put("type", type.name());
        object.put("distance", distance);
        return object;
    }

    /**
     * Method specifically added for testing purposes to retrieve the list of detected objects.
     * This method returns a copy of the list of detected objects that were identified during
     * the last execution of the lookAround method.
     *
     * @return A list of WorldObject instances representing the objects detected in the robot's surroundings.
     */
    static ArrayList<WorldObject> getDetectedObjectsForTest() {
        return new ArrayList<>(objects);
    }
}