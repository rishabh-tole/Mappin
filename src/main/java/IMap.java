/**
 * Represents a map interface.
 */
public interface IMap {
    /**
     * Gets the value at the specified row and column.
     * @param r The row index.
     * @param c The column index.
     * @return The value at the specified position.
     */
    boolean getValueAt(int r, int c);

    /**
     * Gets the width of the map.
     * @return The width of the map.
     */
    int getWidth();

    /**
     * Gets the length of the map.
     * @return The length of the map.
     */
    int getLength();

    /**
     * Prints the map with colors according to the specified scale.
     * @param scale The scale factor for printing.
     */
    void printColor(int scale);

    /**
     * Prints the map.
     * @param scale The scale factor for printing.
     */
    void print(int scale);

    /**
     * Gets the boolean array representation of the map.
     * @return The boolean array representation of the map.
     */
    boolean[][] getBooleanArray();
}
