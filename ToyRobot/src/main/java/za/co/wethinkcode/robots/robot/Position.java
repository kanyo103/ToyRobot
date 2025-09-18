package za.co.wethinkcode.robots.robot;

/**
 * The Position class represents a coordinate in a 2D grid.
 * It encapsulates the x and y coordinates and provides methods to
 * access these coordinates and perform comparisons.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a Position object with specified x and y coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     * Checks if this position is within a specified rectangular boundary.
     *
     * @param topLeft The top-left corner of the boundary.
     * @param bottomRight The bottom-right corner of the boundary.
     * @return True if this position is within the boundary, false otherwise.
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

//    @Override
//    public int hashCode() {
//        int result = x;
//        result = 31 * result + y;
//        return result;
//    }

    /**
     * Returns a string representation of this position.
     *
     * @return A string in the format "(x, y)".
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Method to calculate distance to another position
    public int distanceTo(Position other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}


