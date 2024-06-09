import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Provides a graphical representation of a map.
 */
public class MapPrinter extends JFrame {

    private boolean[][] map;
    private int scale;

    /**
     * Constructs a MapPrinter with the specified map and scale.
     * @param map The boolean array representing the map.
     * @param scale The scale factor for printing.
     */
    public MapPrinter(boolean[][] map, int scale) {
        this.map = map;
        this.scale = scale;
        setTitle("Map Visualization");
        setSize(map[0].length * scale + 50, map.length * scale + 50); // Set window size based on map size and scale
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / scale;
                int y = e.getY() / scale;
                if (x >= 0 && x < map[0].length && y >= 0 && y < map.length) {
                    JOptionPane.showMessageDialog(null, "Coordinates: (" + x + ", " + y + ")");
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x]) {
                    g.setColor(Color.RED); // Red box for true
                } else {
                    g.setColor(Color.BLUE); // Blue box for false
                }
                g.fillRect(x * scale, y * scale, scale, scale);
                g.setColor(Color.BLACK);
                g.drawRect(x * scale, y * scale, scale, scale);
            }
        }
    }

    /**
     * Prints the map using the specified scale.
     * @param map The map to print.
     * @param scale The scale factor for printing.
     */
    public static void printMap(Map map, int scale) {
        boolean[][] mapData = map.getBooleanArray();
        new MapPrinter(mapData, scale);
    }
}
