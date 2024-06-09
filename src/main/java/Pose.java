public class Pose {
    private int x;
    private int y;
    private int orientation;

    public Pose(int x, int y, int o){
        this.x=x;
        this.y=y;
        this.orientation = o;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getOrientation() {
        return orientation;
    }
}
