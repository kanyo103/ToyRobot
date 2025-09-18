package za.co.wethinkcode.robots.server.world;

import za.co.wethinkcode.robots.maze.CustomMaze;
import za.co.wethinkcode.robots.robot.Position;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.server.world.obstacles.Obstacle;
import za.co.wethinkcode.robots.server.world.obstacles.Obstacle.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a textual representation of the world.
 */
public abstract class TextIWorld extends AbstractWorld {
    private int width;
    private int height;


    public TextIWorld(CustomMaze maze, int visibility, int repairTime, int reloadTime, int maxShieldStrength){
        super(maze, visibility, repairTime, reloadTime, maxShieldStrength);
        this.width = maze.BOTTOM_RIGHT.getX() - maze.TOP_LEFT.getX() + 1;
        this.height = maze.TOP_LEFT.getY() - maze.BOTTOM_RIGHT.getY() + 1;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public synchronized IWorld.UpdateResponse updatePosition(int nrSteps, Robot robot) {
        Position newPosition = calculateNewPosition(nrSteps, robot);
        if (!isWithinBoundary(newPosition)) {
            return UpdateResponse.FAILED_OUTSIDE_WORLD;
        }
        if (isPositionBlockedByObstacle(newPosition)){
            return UpdateResponse.FAILED_OBSTRUCTED_OBSTACLE;
        }
        if (isPathBlockedByObstacle(robot.getPosition(), newPosition)){
            return UpdateResponse.FAILED_OBSTRUCTED_OBSTACLE;
        }
        if (isPositionBlockedByRobot(newPosition)){
            return IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT;
        }
        if (isPathBlockedByRobot(robot.getPosition(), newPosition)){
            return IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT;
        }
        if (isPitInPath(newPosition, robot)){
            return UpdateResponse.FAILED_PITFALL;
        }
        robot.setPosition(newPosition);
        return IWorld.UpdateResponse.SUCCESS;
    }

    @Override
    public synchronized void updateDirection(boolean turnRight, Robot robot) {
        int numDirections = IWorld.Direction.values().length;
        int currentIndex = robot.getCurrentDirection().ordinal();
        int newIndex;

        if (turnRight) {
            newIndex = (currentIndex + 1) % numDirections;
        } else {
            newIndex = (currentIndex - 1 + numDirections) % numDirections;
        }
        robot.setDirection(IWorld.Direction.values()[newIndex]);
    }


    @Override
    public void showObstacles() {
        List<Obstacle> obsList = getObstacles();
        if (!obsList.isEmpty()){
            System.out.println("There are some obstacles:");
            for (Obstacle obs : obsList){
                switch (obs.getType()){
                    case PIT -> {
                        System.out.print(" A ");
                        System.out.print(COLOR_PIT_TEXT + "▦ Pit " + RESET);
                    }
                    case MOUNTAIN -> {
                        System.out.print(" A ");
                        System.out.print(COLOR_MOUNTAIN_TEXT + "▣ Mountain " + RESET);
                    }
                    case LAKE -> {
                        System.out.print(" A ");
                        System.out.print(COLOR_LAKE_TEXT + "◙ Lake " + RESET);
                    }
                }
                System.out.println("at position " + obs.getBottomLeftX() + "," + obs.getBottomLeftY() +
                        "(to " + (obs.getBottomLeftX() + obs.getSize()) + "," +
                        (obs.getBottomLeftY() + obs.getSize()) + ")");
            }
        }
    }

    @Override
    public boolean enoughSpace() {
        // iterate over all position in the world
        for (int x = maze.TOP_LEFT.getX(); x <= maze.BOTTOM_RIGHT.getX(); x++) {
            for (int y = maze.BOTTOM_RIGHT.getY(); y <= maze.TOP_LEFT.getY(); y++) {
                Position position = new Position(x, y);
                if (!isPositionBlockedByObstacle(position) && !isPositionBlockedByRobot(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validName(String name){
        for (Robot robot : robotList){
            if (robot.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Position getEmptySpot() {
        while (true) {
            int randomX = ThreadLocalRandom.current().nextInt(maze.TOP_LEFT.getX(), maze.BOTTOM_RIGHT.getX() + 1);
            int randomY = ThreadLocalRandom.current().nextInt(maze.BOTTOM_RIGHT.getY(), maze.TOP_LEFT.getY() + 1);
            Position randomPosition = new Position(randomX, randomY);

            if (!isPositionBlockedByObstacle(randomPosition) && !isPositionBlockedByRobot(randomPosition) && !isPositionBlockedByPit(randomPosition)) {
                return randomPosition;
            }
        }
    }

    @Override
    public Robot findRobotInRange(Position startPosition, int range,Direction direction) {
        for (Robot robot : getRobots()) {
            Position robotPosition = robot.getPosition();
            int distance = startPosition.distanceTo(robotPosition);

            if (distance <= range && isInLineOfFire(startPosition, robotPosition, direction)) {
                return robot;
            }
        }
        return null;
    }

    // Private helper method
    private boolean isInLineOfFire(Position shooter, Position target, IWorld.Direction direction) {
        switch (direction) {
            case NORTH:
                return shooter.getX() == target.getX() && shooter.getY() < target.getY() && !isPathBlockedByObstacle(shooter, target);
            case EAST:
                return shooter.getY() == target.getY() && shooter.getX() < target.getX() && !isPathBlockedByObstacle(shooter, target);
            case SOUTH:
                return shooter.getX() == target.getX() && shooter.getY() > target.getY() && !isPathBlockedByObstacle(shooter, target);
            case WEST:
                return shooter.getY() == target.getY() && shooter.getX() > target.getX() && !isPathBlockedByObstacle(shooter, target);
            default:
                return false;
        }
    }

}

