package za.co.wethinkcode.robots.server.world.obstacles;
import za.co.wethinkcode.robots.server.world.IWorld;
public class Pit extends AbstractObstacle{

    public Pit(int x, int y) {
        super(x, y, 2, IWorld.ObstacleType.PIT);
    }
}