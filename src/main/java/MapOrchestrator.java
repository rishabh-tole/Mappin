public class MapOrchestrator {
    Config cfg;
    IInput input;
    IOutput output;

    Map map;
    PositionCalculator positionCalculator;
    private final int RESIZE_WIDTH = 5;
    private final int PRINT_SCALE = 3;

    public MapOrchestrator(Config cfg){
        this.cfg = cfg;
        if (cfg.getInputType().equals("simulated")){
            input = new SimulatedLocationInput();
        }
        if (cfg.getOutputType().equals("ascii")){
            output = new SimulatedASCIIOutput();
        }

        positionCalculator = new PositionCalculator(cfg);
    }

    public void initialize(Pose targetPose){
        // Get initial positions
        Pose initialPose = input.init();

        // Construct Map
        map = MapConverter.convertImageToBooleanArray(cfg.getMapPath());
        map = MapConverter.padArray(map.getBooleanArray(), Math.max(cfg.getPinx(), cfg.getPiny())+1);
        map.printColor(PRINT_SCALE);

        System.out.println("============================ Original Map ==========================================");
        System.out.println("\n \n \n \n");

        //Path Plan
        map = PathPlanner.planPath(map, initialPose, targetPose, RESIZE_WIDTH);

        map.printColor(PRINT_SCALE);

        System.out.println("============================== Path Planned Map =================================");
        System.out.println("\n \n \n \n");

        //Calculate initial output positions
        Positions initialOutputPositions = positionCalculator.initialize(initialPose, map);

        //Set initial output positions
        output.initialize(initialOutputPositions);
    }

    public void update(){

        //Update pose from the newest information given  by input
        Pose currentPose = input.update();


        //Calculate new positions
        Positions outputPositions = positionCalculator.update(currentPose, map);

        //Update the output the positions
        output.update(outputPositions);
        System.out.println("============================= New Positions =================================");
    }
}
