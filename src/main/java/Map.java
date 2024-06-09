public class Map implements IMap{
    final String REDBOX = "\u001B[41m  \u001B[0m"; // Red box
    final String BLUEBOX = "\u001B[44m  \u001B[0m"; // Blue box
    final String YELLOWBOX = "\u001B[43m  \u001B[0m"; // Yellow Box
    private boolean[][] map;

    public Map(boolean[][] map){
        this.map = map;
    }
    @Override
    public boolean getValueAt(int r, int c) {
        return map[r][c];
    }

    @Override
    public int getWidth() {
        return map[0].length;
    }

    @Override
    public int getLength() {
        return map.length;
    }

    @Override
    public void printColor(int scale) {
        for (int i = 0; i < map.length; i+=scale) {
            for (int j = 0; j < map[0].length; j+=scale) {
                System.out.print(map[i][j]? REDBOX:BLUEBOX);
            }
            System.out.println();
        }
    }

    @Override
    public void print(int scale) {
        for (int i = 0; i < map.length; i+=scale) {
            for (int j = 0; j < map[0].length; j+=scale) {
                System.out.print(map[i][j]? "1":"0");
            }
            System.out.println();
        }
    }

    @Override
    public boolean[][] getBooleanArray() {
        return map;
    }
}