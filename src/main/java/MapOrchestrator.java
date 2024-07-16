/**
 * Orchestrates the mapping process.
 */
public class MapOrchestrator {
    Config cfg;
    IInput input;

    IOutput[] outputs;

    Map map;
    PositionCalculator positionCalculator;
    private final int RESIZE_WIDTH = 5;
    private final int PRINT_SCALE = 2;

    /**
     * Constructs a MapOrchestrator with the specified configuration.
     *
     * @param cfg The configuration for the orchestrator.
     */
    public MapOrchestrator(Config cfg) {
        this.cfg = cfg;
        // Choose Inputs
        if (cfg.getInputType().equals("simulated")) {
            input = new SimulatedLocationInput();
        }
        if (cfg.getInputType().equals("mother")) {
            input = new MotherInput();
        }

        if (cfg.getInputType().equals("esp")) {
            input = new ESPIMULocationInput();
        }

        // Choose Outputs
        if (cfg.getOutputType().equals("ascii")) {
            outputs = new IOutput[]{new SimulatedASCIIOutput()};
        }

        if (cfg.getOutputType().equals("jframe")) {
            outputs = new IOutput[]{new JFrameOutput(cfg)};
        }

        positionCalculator = new PositionCalculator(cfg);


        //TEST
        outputs = new IOutput[]{
                new JFrameOutput(cfg),
                new DeviceOutput(),
                new Compressed4x4JframeOutput()
        };
        //TEST
    }

    /**
     * Initializes the mapping process with the target pose.
     *
     * @param targetPose The target pose.
     */
    public void initialize(Pose targetPose) {
        // Get initial positions
        Pose initialPose = input.init();

        // Construct Map
        map = MapConverter.convertImageToBooleanArray(cfg.getMapPath());
        map = MapConverter.padArray(map.getBooleanArray(), Math.max(cfg.getPinx(), cfg.getPiny()) + 1);
        //MapPrinter.printMap(map, PRINT_SCALE);

        //Path Plan
        map = PathPlanner.planPath(map, initialPose, targetPose, RESIZE_WIDTH);
        //MapPrinter.printMap(map, PRINT_SCALE);

        //Calculate initial output positions
        Positions initialOutputPositions = positionCalculator.initialize(initialPose, map);

        //Set initial output positions

        for (IOutput output:outputs) {
            output.initialize(initialOutputPositions);
        }
    }

    /**
     * Updates the mapping process.
     */
    public void update() {

        //Update pose from the newest information given by input
        Pose currentPose = input.update();

        //Calculate new positions
        Positions outputPositions = positionCalculator.update(currentPose, map);

        //Update the output the positions
        for (IOutput output:outputs) {
            output.update(outputPositions);
        }

        System.out.println("============================= New Positions =================================");
    }
}
