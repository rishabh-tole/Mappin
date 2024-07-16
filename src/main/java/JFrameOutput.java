import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a JFrame-based output for path visualization.
 */
public class JFrameOutput extends JFrame implements IOutput {

    private static final int TILE_SIZE = 20; // Size of each tile in pixels
    private static final int UPDATE_INTERVAL = 300; // Update interval in milliseconds (0.1 seconds)
    private int[][] positions;
    private Timer timer;

    /**
     * Constructs a new JFrameOutput with the specified configuration.
     * @param cfg The configuration for the JFrameOutput.
     */
    public JFrameOutput(Config cfg) {
        setTitle("Path Visualization");
        setSize(cfg.getPinx() * TILE_SIZE, cfg.getPiny() * TILE_SIZE); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Create and start a timer that updates the display every 0.1 seconds
        timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    /**
     * Updates the JFrameOutput with new positions.
     * @param positions The new positions to update with.
     */
    @Override
    public void update(Positions positions) {
        this.positions = positions.getPositions();


    }

    /**
     * Initializes the JFrameOutput with positions.
     * @param positions The positions to initialize with.
     */
    @Override
    public void initialize(Positions positions) {
        update(positions);
    }

    /**
     * Paints the JFrameOutput.
     * @param g The graphics object.
     */
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
