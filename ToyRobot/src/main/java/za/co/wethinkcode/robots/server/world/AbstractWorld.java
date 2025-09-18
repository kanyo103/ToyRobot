package za.co.wethinkcode.robots.server.world;

import za.co.wethinkcode.robots.server.world.obstacles.*;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.maze.CustomMaze;

import java.util.*;


public abstract class AbstractWorld implements IWorld{
    public static final String COLOR_MOUNTAIN_TEXT = "\u001B[38;5;70m ⛰\uFE0F";
    public static final String COLOR_LAKE_TEXT = "\u001B[38;5;27m\uD83C\uDF0A";
    public static final String COLOR_PIT_TEXT = "\u001B[38;5;137m \uD83D\uDD73\uFE0F";
    public static final String COLOR_ROBOT = "\u001B[38;5;125m \uD83E\uDD16";
    public static final String RESET = "\u001B[0m";

    protected final CustomMaze maze;
    protected final List<Robot> robotList = new ArrayList<>();
    protected Map<Position, Object> worldObstacles;
    private final int visibility;
    private final int repairTime;
    private final int reloadTime;
    private final int maxShieldStrength;


    public AbstractWorld(CustomMaze maze, int visibility, int repairTime, int reloadTime, int maxShieldStrength){
        this.maze = maze;
        this.visibility = visibility;
        this.repairTime = repairTime;
        this.reloadTime = reloadTime;
        this.maxShieldStrength = maxShieldStrength;
    }

    @Override
    public boolean isWithinBoundary(Position position) {
        return position.isIn(maze.TOP_LEFT, maze.BOTTOM_RIGHT);
    }


    public boolean isPositionBlockedByObstacle(Position newPosition) {
        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.blocksPosition(newPosition)) {
                return true;
            }
        }
        return false;
    }


    public boolean isPitInPath(Position newPosition, Robot robot){
        Position robotPosition = robot.getPosition();
        int startX = robotPosition.getX();
        int startY = robotPosition.getY();
        int endX = newPosition.getX();
        int endY = newPosition.getY();

        int minX = Math.min(startX, endX);
        int maxX = Math.max(startX, endX);
        int minY = Math.min(startY, endY);
        int maxY = Math.max(startY, endY);

        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.getType() == ObstacleType.PIT){
                int size = obstacle.getSize();
                int x = obstacle.getBottomLeftX();
                int y = obstacle.getBottomLeftY();
                for (int i = minX; i <= maxX; i++) {
                    for (int j = minY; j <= maxY; j++) {
                        if (i >= x && i < x + size && j >= y && j < y + size) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isPositionBlockedByRobot(Position newPosition){
        for (Robot robot: this.robotList){
            if (robot.getPosition().equals(newPosition)) return true;
        }
        return false;
    }


    public boolean isPositionBlockedByPit(Position position){
        int x = position.getX();
        int y = position.getY();
        for (Obstacle obstacle: getObstacles()){
            if (x >= obstacle.getBottomLeftX() && x < obstacle.getBottomLeftX() + obstacle.getSize() &&
                    y >= obstacle.getBottomLeftY() && y < obstacle.getBottomLeftY() + obstacle.getSize()){
                return true;
            }
        }
        return false;
    }


    public boolean isPathBlockedByObstacle(Position a, Position b){
        // Check if any obstacle blocks the path
        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPathBlockedByRobot(Position a, Position b){
        for (int x = Math.min(a.getX(), b.getX()); x <= Math.max(a.getX(), b.getX()); x++) {
            for (int y = Math.min(a.getY(), b.getY()); y <= Math.max(a.getY(), b.getY()); y++) {
                Position position = new Position(x, y);
                if (isPositionBlockedByRobot(position) && !a.equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }


    public Position calculateNewPosition(int nrSteps, Robot robot){
        int newX = robot.getPosition().getX();
        int newY = robot.getPosition().getY();
        switch (robot.getCurrentDirection()){
            case NORTH:
                newY += nrSteps;
                break;
            case EAST:
                newX += nrSteps;
                break;
            case WEST:
                newX -= nrSteps;
                break;
            case SOUTH:
                newY -= nrSteps;
                break;
        }
        return new Position(newX, newY);
    }

    public List<Obstacle> getObstacles() {
        return this.maze.getObstacles();
    }

    public List<Robot> getRobots() {
        return robotList;
    }

    public void addRobot(Robot robot) {
        this.robotList.add(robot);
        updateWorldObstacles();
    }

    public Position getTOP_LEFT(){
        return this.maze.TOP_LEFT;
    }

    public Position getBOTTOM_RIGHT() {
        return this.maze.BOTTOM_RIGHT;
    }

    @Override
    public void updateWorldObstacles() {
        Map<Position, Object> worldObstacles = new HashMap<>();
        for (Robot robot : getRobots()){
            worldObstacles.put(robot.getPosition(), robot);
        }
        for (Obstacle obstacle : getObstacles()) {
            int x = obstacle.getBottomLeftX();
            int y = obstacle.getBottomLeftY();
            worldObstacles.put(new Position(x,y), obstacle);
        }

        this.worldObstacles = worldObstacles;
    }

    @Override
    public Map<Position, Object> getWorldObstacles() {
        updateWorldObstacles();
        return worldObstacles;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getRepairTime() {
        return repairTime;
    }

    public int getMaxShieldStrength() {
        return maxShieldStrength;
    }

}
