package za.co.wethinkcode.robots.server.world.obstacles;

import za.co.wethinkcode.robots.server.world.IWorld;

public class  Lake extends AbstractObstacle{

    public Lake(int x, int y){
        super(x,y,6, IWorld.ObstacleType.LAKE);
    }
}