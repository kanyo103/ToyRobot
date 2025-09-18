package za.co.wethinkcode.robots.server.world.obstacles;

import za.co.wethinkcode.robots.server.world.IWorld;

public class Mountain extends AbstractObstacle{


    public Mountain(int x, int y){
        super(x,y,3, IWorld.ObstacleType.MOUNTAIN);
    }
}
