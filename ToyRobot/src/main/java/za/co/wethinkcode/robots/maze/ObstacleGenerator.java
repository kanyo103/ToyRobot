package za.co.wethinkcode.robots.maze;

import za.co.wethinkcode.robots.server.world.obstacles.Obstacle;
import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.server.world.obstacles.Pit;
import za.co.wethinkcode.robots.server.world.obstacles.Mountain;
import za.co.wethinkcode.robots.server.world.obstacles.Lake;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstacleGenerator {
    public final Random random = new Random();
    private final Position TOP_LEFT;
    private final Position BOTTOM_RIGHT;
    private final int numberOfObstacles;

    public ObstacleGenerator(int numberOfObstacles, Position TOP_LEFT, Position BOTTOM_RIGHT){
        this.TOP_LEFT = TOP_LEFT;
        this.BOTTOM_RIGHT = BOTTOM_RIGHT;
        this.numberOfObstacles = numberOfObstacles;
    }

    public List<Obstacle> generateObstacles() {

        int numPits = Math.round(numberOfObstacles* 0.2f);
        int numLakes = Math.round(numberOfObstacles * 0.3f);
        int numMountains = numberOfObstacles - numPits - numLakes;
        List<Obstacle> obstacles = new ArrayList<>();

        addLakes(obstacles, numLakes);
        addMountains(obstacles, numMountains);
        addPits(obstacles, numPits);

        return obstacles;
    }

    private void addPits(List<Obstacle> obstacles, int count) {
        for (int i = 0; i < count; i++) {
            while (true) {
                int x = randomX(2);
                int y = randomY(2);

                if (!isOverlapping(obstacles, x, y, 2)) {
                    obstacles.add(new Pit(x, y));
                    break;
                }
            }
        }
    }

    private void addLakes(List<Obstacle> obstacles, int count) {
        for (int i = 0; i < count; i++) {
            while (true) {
                int x = randomX(6);
                int y = randomY(6);
                if (!isOverlapping(obstacles,x,y,6)){
                    obstacles.add(new Lake(x,y));
                    break;
                }
            }
        }
    }

    private void addMountains(List<Obstacle> obstacles, int count) {
        for (int i = 0; i < count; i++) {
            while (true) {
                int x = randomX(3);
                int y = randomY(3);
                if (!isOverlapping(obstacles,x,y,3)){
                    obstacles.add(new Mountain(x,y));
                    break;
                }
            }
        }
    }

    private int randomX(int size) {
        int minX = this.TOP_LEFT.getX();
        int maxX = this.BOTTOM_RIGHT.getX() - size;
        return random.nextInt((maxX - minX + 1)) + minX;
    }

    private int randomY(int size) {
        int minY = this.BOTTOM_RIGHT.getY();
        int maxY = this.TOP_LEFT.getY() - size;
        return random.nextInt((maxY - minY + 1)) + minY;
    }

    private boolean isOverlapping(List<Obstacle> obstacles, int x, int y, int size) {
        for (Obstacle obstacle : obstacles) {
            int obstacleX = obstacle.getBottomLeftX();
            int obstacleY = obstacle.getBottomLeftY();
            int obstacleSize = obstacle.getSize();

            // Check if the new pit overlaps with the existing obstacle
            if (x + size > obstacleX && obstacleX + obstacleSize > x &&
                    y + size > obstacleY && obstacleY + obstacleSize > y) {
                return true;
            }
        }
        return false;
    }
}
