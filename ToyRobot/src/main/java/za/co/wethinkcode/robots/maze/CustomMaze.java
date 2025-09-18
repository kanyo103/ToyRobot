package za.co.wethinkcode.robots.maze;

import za.co.wethinkcode.robots.robot.Position;

public class CustomMaze extends AbstractMaze{

    public CustomMaze(int numberOfObstacles, Position TOP_LEFT, Position BOTTOM_RIGHT){
        this.TOP_LEFT = TOP_LEFT;
        this.BOTTOM_RIGHT = BOTTOM_RIGHT;
        ObstacleGenerator generator = new ObstacleGenerator(numberOfObstacles, this.TOP_LEFT, this.BOTTOM_RIGHT);
        setObstacles(generator.generateObstacles());
    }

}