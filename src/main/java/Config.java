/**
 * Represents configuration settings for a mapping application.
 */
public class Config {
    private int pinx;
    private int piny;
    private int scaleFactor;
    private String inputType;
    private String mapPath;
    private String outputType;

    /**
     * Constructs a new Config object with the specified parameters.
     * @param pinx The X-coordinate of the pin.
     * @param piny The Y-coordinate of the pin.
     * @param scaleFactor The scale factor of the map.
     * @param inputType The type of input data.
     * @param outputType The type of output data.
     * @param mapPath The path to the map file.
     */
    public Config(int pinx, int piny, int scaleFactor, String inputType, String outputType, String mapPath){
        this.pinx = pinx;
        this.piny = piny;
        this.scaleFactor = scaleFactor;
        this.inputType = inputType;
        this.outputType = outputType;
        this.mapPath = mapPath;
    }

    /**
     * Gets the X-coordinate of the pin.
     * @return The X-coordinate.
     */
    public int getPinx() {
        return pinx;
    }

    /**
     * Gets the Y-coordinate of the pin.
     * @return The Y-coordinate.
     */
    public int getPiny() {
        return piny;
    }

    /**
     * Gets the scale factor of the map.
     * @return The scale factor.
     */
    public int getScaleFactor() {
        return scaleFactor;
    }

    /**
     * Gets the type of input data.
     * @return The input data type.
     */
    public String getInputType(){
        return inputType;
    }

    /**
     * Gets the type of output data.
     * @return The output data type.
     */
    public String getOutputType() {
        return outputType;
    }

    /**
     * Gets the path to the map file.
     * @return The map file path.
     */
    public String getMapPath() {
        return mapPath;
    }
}
