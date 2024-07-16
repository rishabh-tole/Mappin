import javax.swing.*;
import java.awt.*;

public class Compressed4x4JframeOutput implements IOutput {

    private JFrame frame;
    private JPanel gridPanel;

    public Compressed4x4JframeOutput() {
        initializeFrame();
    }

    @Override
    public void initialize(Positions positions) {
        // Initialization code (if needed)
    }

    @Override
    public void update(Positions positions) {
        int[][] originalPath = positions.getPositions();
        int[][] scaledPath = scaleDownPath(originalPath);
        displayPositions(scaledPath);
    }

    private void initializeFrame() {
        frame = new JFrame("Path Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        frame.add(gridPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private int[][] scaleDownPath(int[][] path) {
        int originalRows = path.length;
        int originalCols = path[0].length;
        int scaledRows = 4;
        int scaledCols = 4;

        int[][] scaledPath = new int[scaledRows][scaledCols];

        // Calculate the size of each block in the original array
        int rowBlockSize = originalRows / scaledRows;
        int colBlockSize = originalCols / scaledCols;

        // Iterate over each block and determine the value for each cell in the scaled path
        for (int i = 0; i < scaledRows; i++) {
            for (int j = 0; j < scaledCols; j++) {
                int sum = 0;
                int count = 0;
                for (int k = i * rowBlockSize; k < (i + 1) * rowBlockSize && k < originalRows; k++) {
                    for (int l = j * colBlockSize; l < (j + 1) * colBlockSize && l < originalCols; l++) {
                        sum += path[k][l];
                        count++;
                    }
                }
                scaledPath[i][j] = sum >= (count / 2.0) ? 1 : 0;
            }
        }

        return scaledPath;
    }

    private void displayPositions(int[][] positions) {
        gridPanel.removeAll();

        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                JPanel cell = new JPanel();
                cell.setBackground(positions[i][j] == 1 ? Color.BLACK : Color.WHITE);
                gridPanel.add(cell);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}
