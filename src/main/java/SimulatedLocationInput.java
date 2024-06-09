import java.util.Scanner;

/**
 * Simulates location input from user commands.
 */
public class SimulatedLocationInput implements IInput {

    private int x;
    private int y;
    private int orientation;

    private Scanner scanner;
    private final int STEP = 15;

    /**
     * Initializes the simulated location input.
     * @return The initial pose.
     */
    @Override
    public Pose init() {
        scanner = new Scanner(System.in);
        this.x = 103;
        this.y = 232;
        this.orientation = 0;
        return new Pose(x, y, orientation);
    }

    /**
     * Updates the simulated location input based on user commands.
     * @return The updated pose.
     */
    @Override
    public Pose update() {

        String text = scanner.nextLine();

        if (text.substring(0, 1).equals("w")) {
            move(0, STEP);
        } else if (text.substring(0, 1).equals("a")) {
            move(-90, STEP);
        } else if (text.substring(0, 1).equals("s")) {
            move(180, STEP);
        } else if (text.substring(0, 1).equals("d")) {
            move(90, STEP);
        } else if (text.substring(0, 1).equals("q")) {
            orientation = Math.floorMod(orientation - 15, 360);
        } else if (text.substring(0, 1).equals("e")) {
            orientation = Math.floorMod(orientation + 15, 360);
        }
        else if (text.substring(0, 4).equals("turn")) {
            orientation = Math.floorMod(orientation + Integer.parseInt(text.substring(5)), 360);
        } else if (text.substring(0, 4).equals("head")) {
            orientation = Math.floorMod(Integer.parseInt(text.substring(5)), 360);
        } else if (text.substring(0, 4).equals("move")) {
            text = text.substring(5);
            int i = text.indexOf(" ");

            int dir = Integer.parseInt(text.substring(0, i));
            int mag = Integer.parseInt(text.substring(i + 1));
            move(dir, mag);

        } else if (text.substring(0, 2).equals("go")) {

            text = text.substring(3);
            int i = text.indexOf(" ");
            int x = Integer.parseInt(text.substring(0, i));
            int y = Integer.parseInt(text.substring(i + 1));
            this.x = x;
            this.y = y;

        }
        else {
            System.out.println("Something isnt right in your input");
        }

        System.out.println("X: " + x + " Y: " + y + " H: " + orientation);
        return new Pose(x, y, orientation);
    }

    /**
     * Moves the simulated location.
     * @param dir The direction.
     * @param mag The magnitude.
     */
    private void move(int dir, int mag){
        double realDir = Math.toRadians(Math.floorMod(orientation + dir, 360));
        this.x += Math.sin(realDir) * mag;
        this.y -= Math.cos(realDir) * mag;
    }
}
