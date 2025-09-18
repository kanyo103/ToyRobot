package za.co.wethinkcode.robots.maze;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.server.world.obstacles.Obstacle;

import java.util.List;

public abstract class AbstractMaze implements Maze {
    public Position TOP_LEFT = new Position(-200,100);
    public Position BOTTOM_RIGHT = new Position(100,-200);
    private List<Obstacle> obstacles;

    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public void setObstacles(List<Obstacle> obstacleList){
        this.obstacles = obstacleList;
    }
}
