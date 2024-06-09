/**
 * Simulates ASCII output based on positions.
 */
public class SimulatedASCIIOutput implements IOutput {
    final String REDBOX = "\u001B[41m  \u001B[0m"; // Red box
    final String BLUEBOX = "\u001B[44m  \u001B[0m"; // Blue box
    final String YELLOWBOX = "\u001B[43m  \u001B[0m"; // Yellow Box

    /**
     * Updates the output based on the positions.
     * @param positions The positions.
     */
    @Override
    public void update(Positions positions) {
        for (int i = 0; i < positions.getLength(); i++) {
            for (int j = 0; j < positions.getWidth(); j++) {
                int a = positions.getValueAt(i, j);

                if (a == 1) {
                    System.out.print(REDBOX);
                } else if (a == 0) {
                    System.out.print(BLUEBOX);
                } else if (a == -1) {
                    System.out.print(YELLOWBOX);
                }
            }
            System.out.println();
        }
    }

    /**
     * Initializes the output based on the positions.
     * @param positions The positions.
     */
    @Override
    public void initialize(Positions positions) {
        update(positions);
    }
}
