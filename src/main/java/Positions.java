/**
 * Represents positions on a map.
 */
public class Positions{

    int[][] positions;

    /**
     * Constructs a Positions object with the specified positions.
     * @param positions The positions.
     */
    public Positions(int[][] positions){
        this.positions = positions;
    }

    /**
     * Gets the value at the specified position.
     * @param r The row index.
     * @param c The column index.
     * @return The value at the specified position.
     */
    public int getValueAt(int r, int c) {
        return positions[r][c];
    }

    /**
     * Gets the width of the positions array.
     * @return The width.
     */
    public int getWidth() {
        return positions[0].length;
    }

    /**
     * Gets the length of the positions array.
     * @return The length.
     */
    public int getLength() {
        return positions.length;
    }

    /**
     * Gets the positions array.
     * @return The positions array.
     */
    public int[][] getPositions() {
        return positions;
    }
}
