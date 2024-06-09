public class Positions{

    int[][] positions;

    public Positions(int[][] positions){
        this.positions = positions;
    }


    public int getValueAt(int r, int c) {
        return positions[r][c];
    }


    public int getWidth() {
        return positions[0].length;
    }


    public int getLength() {
        return positions.length;
    }


    public int[][] getBooleanArray() {
        return positions;
    }
}
