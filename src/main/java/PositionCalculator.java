public class PositionCalculator {
    private int xsize;
    private int ysize;
    private int scale;


    public PositionCalculator(Config cfg){
        this.xsize= cfg.getPinx();
        this.ysize= cfg.getPiny();
        this.scale = cfg.getScaleFactor();
    }

    public Positions initialize(Pose initialPose, Map map){
        return update(initialPose, map);
    }

    public Positions update(Pose pose, Map map) {
        int centerX = pose.getX();
        int centerY = pose.getY();
        double heading = Math.toRadians(pose.getOrientation());

        int[][] pinMap = new int[ysize][xsize];

        // Get the map array
        boolean[][] mapArray = map.getBooleanArray();

        // Calculate the offsets and apply rotation
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                // Calculate the position relative to the center pin
                int offsetX = (x - xsize / 2) * scale;
                int offsetY = (y - ysize / 2) * scale;

                // Apply the rotation matrix
                int rotatedX = (int) (offsetX * Math.cos(heading) - offsetY * Math.sin(heading));
                int rotatedY = (int) (offsetX * Math.sin(heading) + offsetY * Math.cos(heading));

                // Calculate the final map coordinates
                int finalX = centerX + rotatedX;
                int finalY = centerY + rotatedY;

                // Check if the final coordinates are within the map bounds
                if (finalX >= 0 && finalX < mapArray[0].length && finalY >= 0 && finalY < mapArray.length) {
                    pinMap[y][x] = mapArray[finalY][finalX] ? 1: 0;
                } else {
                    pinMap[y][x] = -1; // or some default value if out of bounds
                }
            }
        }
        return new Positions(pinMap);
    }
}
