import javax.swing.*;
import java.awt.*;

public class JFrameOutput extends JFrame implements IOutput {

    private static final int TILE_SIZE = 20; // Size of each tile in pixels
    private int[][] positions;

    public JFrameOutput(Config cfg) {
        setTitle("Path Visualization");
        setSize(cfg.getPinx()*20, cfg.getPiny()*20); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Positions positions) {
        this.positions = positions.getPositions();
        repaint(); // Repaint the window to update the display
    }

    @Override
    public void initialize(Positions positions) {
        update(positions);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (positions == null) return;

        for (int y = 0; y < positions.length; y++) {
            for (int x = 0; x < positions[0].length; x++) {
                int value = positions[y][x];
                if (value == 1) {
                    g.setColor(Color.RED); // Red box for path
                } else if (value == 0) {
                    g.setColor(Color.BLUE); // Blue box for non-path
                } else if (value == -1) {
                    g.setColor(Color.YELLOW); // Yellow box for some other condition
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}
