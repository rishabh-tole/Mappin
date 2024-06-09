public interface IMap {
    boolean getValueAt(int r,int c);
    int getWidth();
    int getLength();
    void printColor(int scale);

    void print(int scale);
    boolean[][] getBooleanArray();
}
