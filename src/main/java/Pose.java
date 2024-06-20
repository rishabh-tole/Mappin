/**
 * Represents a pose (position and orientation) in a coordinate system.
 */
public class Pose {
    private int x;
    private int y;
    private int orientation;

    /**
     * Constructs a new Pose object with the specified coordinates and orientation.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param o The orientation.
     */
    public Pose(int x, int y, int o) {
        this.x = x;
        this.y = y;
        this.orientation = o;
    }

    /**
     * Gets the x-coordinate of the pose.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the pose.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the orientation of the pose.
     *
     * @return The orientation.
     */
    public int getOrientation() {
        return orientation;
    }

    public String toString() {
        return "Pose{" +
                "x=" + x +
                ", y=" + y +
                ", heading=" + orientation +
                '}';
    }
}
