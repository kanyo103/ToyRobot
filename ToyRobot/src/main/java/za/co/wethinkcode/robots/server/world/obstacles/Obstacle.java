package za.co.wethinkcode.robots.server.world.obstacles;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.server.world.IWorld;

public interface Obstacle {

    int getBottomLeftX();

    int getBottomLeftY();

    int getSize();

    boolean blocksPosition(Position position);

    boolean blocksPath(Position a, Position b);

    boolean blocksObstacle(Position position);

    IWorld.ObstacleType getType();
}
