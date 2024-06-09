public class Config {
    private int pinx;
    private int piny;
    private int scaleFactor;
    private String inputType;
    private String mapPath;



    private String outputType;
    public Config(int pinx, int piny, int scaleFactor, String inputType, String outputType, String mapPath){
        this.pinx = pinx;
        this.piny = piny;
        this.scaleFactor = scaleFactor;
        this.inputType = inputType;
        this.outputType = outputType;
        this.mapPath = mapPath;
    }
    public int getPinx() {
        return pinx;
    }

    public int getPiny() {
        return piny;
    }

    public int getScaleFactor() {
        return scaleFactor;
    }

    public String getInputType(){
        return inputType;
    }

    public String getOutputType() {
        return outputType;
    }

    public String getMapPath() {
        return mapPath;
    }
}
