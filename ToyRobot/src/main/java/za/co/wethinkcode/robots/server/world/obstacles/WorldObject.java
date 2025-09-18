package za.co.wethinkcode.robots.server.world.obstacles;

import za.co.wethinkcode.robots.server.world.IWorld;

public class WorldObject {
    private final IWorld.Direction direction;
    private final int distanceFromRobot;
    private final   IWorld.ObstacleType type;

    public WorldObject(IWorld.Direction direction, int distance,IWorld.ObstacleType type) {
        this.direction = direction;
        this.distanceFromRobot = distance;
        this.type = type;
    }


    public  IWorld.Direction getDirection() {
        return direction;
    }


    public  IWorld.ObstacleType getType() {
        return type;
    }

    public int getDistanceFromRobot() {
        return distanceFromRobot;
    }
}


