/**
 * Orchestrates the mapping process.
 */
public class MapOrchestrator {
    Config cfg;
    IInput input;
    IOutput output;

    Map map;
    PositionCalculator positionCalculator;
    private final int RESIZE_WIDTH = 5;
    private final int PRINT_SCALE = 2;

    /**
     * Constructs a MapOrchestrator with the specified configuration.
     * @param cfg The configuration for the orchestrator.
     */
    public MapOrchestrator(Config cfg){
        this.cfg = cfg;
        // Choose Inputs
        if (cfg.getInputType().equals("simulated")){
            input = new SimulatedLocationInput();
        }
        if (cfg.getInputType().equals("mother")){
            input = new MotherInput();
        }

        if (cfg.getInputType().equals("esp")){
            input = new ESPIMULocationInput();
        }

        // Choose Outputs
        if (cfg.getOutputType().equals("ascii")){
            output = new SimulatedASCIIOutput();
        }

        if (cfg.getOutputType().equals("jframe")){
            output = new JFrameOutput(cfg);
        }

        positionCalculator = new PositionCalculator(cfg);
    }

    /**
     * Initializes the mapping process with the target pose.
     * @param targetPose The target pose.
     */
    public void initialize(Pose targetPose){
        // Get initial positions
        Pose initialPose = input.init();

        // Construct Map
        map = MapConverter.convertImageToBooleanArray(cfg.getMapPath());
        map = MapConverter.padArray(map.getBooleanArray(), Math.max(cfg.getPinx(), cfg.getPiny())+1);
        MapPrinter.printMap(map, PRINT_SCALE);

        //Path Plan
        map = PathPlanner.planPath(map, initialPose, targetPose, RESIZE_WIDTH);
        MapPrinter.printMap(map, PRINT_SCALE);

        //Calculate initial output positions
        Positions initialOutputPositions = positionCalculator.initialize(initialPose, map);

        //Set initial output positions
        output.initialize(initialOutputPositions);
    }

    /**
     * Updates the mapping process.
     */
    public void update(){

        //Update pose from the newest information given by input
        Pose currentPose = input.update();

        //Calculate new positions
        Positions outputPositions = positionCalculator.update(currentPose, map);

        //Update the output the positions
        output.update(outputPositions);
        System.out.println("============================= New Positions =================================");
    }
}
