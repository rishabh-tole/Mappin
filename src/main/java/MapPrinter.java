import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPrinter extends JFrame {

    private boolean[][] map;
    private int scale;

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

    public static void printMap(Map map, int scale) {
        boolean[][] mapData = map.getBooleanArray();
        new MapPrinter(mapData, scale);
    }
}