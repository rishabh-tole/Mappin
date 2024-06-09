import java.util.Scanner;

/**
 * Simulates location input from user commands.
 */
public class MotherInput implements IInput {

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
        try {
            if (x < 350) {
                x += 10;
            }

            if (x>=350 && y<325){
                y+=10;
            }
            Thread.sleep(700);
        }


        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Pose(x, y, orientation);
    }
}
