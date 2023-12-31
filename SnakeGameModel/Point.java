package SnakeGameModel;

import java.util.Objects;

/**
 * Class Point that represents a point in a two-dimensional coordinate system.
 * The point is defined by its x and y coordinates on the game grid.
 * The x and y coordinates correspond to rows and columns.
 */
public class Point {
    private int x; //keep track of the x position of snake and food
    private int y; //keep track of y position of snake and food

    /**
     * Creates a new Point with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the point.
     *
     * @return The x-coordinate.
     */
    public int getXCoord(){return x;}

    /**
     * Gets the y-coordinate of the point.
     *
     * @return The y-coordinate.
     */
    public int getYCoord() {return y;}

    /**
     * Checks if this point is equal to another object.
     * Two points are considered equal if they have the same x and y coordinates.
     *
     * @param obj The object to compare with this point.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    /**
     * Generates a hash code for this point based on its x and y coordinates.
     *
     * @return The hash code for this point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
