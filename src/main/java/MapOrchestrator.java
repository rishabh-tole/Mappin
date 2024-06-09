public class MapOrchestrator {
    Config cfg;
    IInput input;
    IOutput output;

    Map map;
    PositionCalculator positionCalculator;

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
        map.printColor(3);

        System.out.println("============================ Original Map ==========================================");
        System.out.println("\n \n \n \n");

        //Path Plan
        Map pathPlannedMap = PathPlanner.planPath(map, initialPose, targetPose, 5);

        pathPlannedMap.printColor(3);

        System.out.println("============================== Path Planned Map =================================");
        System.out.println("\n \n \n \n");

        //Calculate initial output positions
        Positions initialOutputPositions = positionCalculator.initialize(initialPose, pathPlannedMap);

        //Set initial output positions
        output.initialize(initialOutputPositions);


    }

    public void update(Pose targetPose){

        //Update pose from the newest information given  by input
        Pose currentPose = input.update();

        //Path plan
        Map pathPlannedMap = PathPlanner.planPath(map, targetPose, currentPose, 5);

        //Calculate new positions
        Positions outputPositions = positionCalculator.update(currentPose, pathPlannedMap);

        //Update the output the positions
        output.update(outputPositions);
        System.out.println("============================= New Positions =================================");
    }
}
