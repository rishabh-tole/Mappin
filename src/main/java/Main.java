import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Stick to odd numbers for now for pin x and pin y

        Config cfg = new Config(11,17,3, "mother", "jframe", "NewMap.png");

        MapOrchestrator mo = new MapOrchestrator(cfg);

        Pose target = new Pose(213,301,0);

        mo.initialize(target);

        while (true){
            mo.update();
        }
    }
}