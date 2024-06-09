import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MapConverter {

    public static Map convertImageToBooleanArray(String imagePath) {
        try {
            // Load the image
            BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();

            // Create the 2D boolean array
            boolean[][] map = new boolean[height][width];

            // Iterate over each pixel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get the color of the current pixel
                    int rgb = image.getRGB(x, y);

                    // Convert the RGB value to grayscale
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;
                    double grayscale = (red * 0.299 + green * 0.587 + blue * 0.114) / 255.0;

                    // Determine if the pixel is black or white
                    map[y][x] = grayscale <= 0.5;
                }
            }

            return new Map(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Map padArray(boolean[][] original, int padding) {
        int originalHeight = original.length;
        int originalWidth = original[0].length;
        int newHeight = originalHeight + 2 * padding;
        int newWidth = originalWidth + 2 * padding;

        boolean[][] paddedArray = new boolean[newHeight][newWidth];

        // Fill the paddedArray with false by default (no need to explicitly do this as default boolean value is false)

        // Copy the original array to the new padded array
        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                paddedArray[y + padding][x + padding] = original[y][x];
            }
        }

        return new Map(paddedArray);
    }
}
