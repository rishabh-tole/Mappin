/**
 * Represents an input interface for a device.
 */
public interface IInput {

    /**
     * Initializes the input.
     * @return The initial pose.
     */
    Pose init();

    /**
     * Updates the input.
     * @return The updated pose.
     */
    Pose update();
}
