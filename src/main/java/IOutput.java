/**
 * Represents an output interface for a device.
 */
public interface IOutput {

    /**
     * Updates the output with the specified positions.
     * @param positions The positions to update with.
     */
    void update(Positions positions);

    /**
     * Initializes the output with the specified positions.
     * @param positions The positions to initialize with.
     */
    void initialize(Positions positions);
}
