import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DeviceOutput implements IOutput {

    private Socket socket;
    private PrintWriter out;

    public DeviceOutput() {
        connectToWebSocket();
    }

    private void connectToWebSocket() {
        try {
            // Connect to the WebSocket server running on the ESP32
            socket = new Socket("192.168.1.171", 8765); // Replace with actual ESP32 IP address
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("WebSocket connection opened");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Positions positions) {
        // Convert Positions array to JSON format and send it to the WebSocket server
        String json = positionsToJson(positions);
        System.out.println("getting run");
        out.println(json);
    }

    @Override
    public void initialize(Positions positions) {
        // Initialization code (if needed)
    }

    private String positionsToJson(Positions positions) {


        positions = new Positions(scaleDownPath(positions.getPositions()));
        // Convert Positions array to JSON format (implement this method according to your JSON format)
        // Example: "{\"positions\": [[true, false, true], [false, true, false]]}"
        // This is a simple example, you may need to adjust it based on your actual Positions format

//        positions = new Positions( new int[][]{
//                {0,1,1,0},
//                {0,1,1,0},
//                {0,1,1,0},
//                {0,1,1,0}
//        });

        ///TEST
        StringBuilder json = new StringBuilder();
        json.append("{\"positions\": [");
        for (int i = 0; i < positions.getLength(); i++) {
            json.append("[");
            for (int j = 0; j < positions.getWidth(); j++) {
                json.append(positions.getValueAt(i, j));
                if (j < positions.getWidth() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            if (i < positions.getLength() - 1) {
                json.append(",");
            }
        }
        json.append("]}");
        return json.toString();
    }
    private int[][] scaleDownPath(int[][] path) {
        int originalRows = path.length;
        int originalCols = path[0].length;
        int scaledRows = 4;
        int scaledCols = 4;

        int[][] scaledPath = new int[scaledRows][scaledCols];

        // Calculate the size of each block in the original array
        int rowBlockSize = originalRows / scaledRows;
        int colBlockSize = originalCols / scaledCols;

        // Iterate over each block and determine the value for each cell in the scaled path
        for (int i = 0; i < scaledRows; i++) {
            for (int j = 0; j < scaledCols; j++) {
                int sum = 0;
                int count = 0;
                for (int k = i * rowBlockSize; k < (i + 1) * rowBlockSize && k < originalRows; k++) {
                    for (int l = j * colBlockSize; l < (j + 1) * colBlockSize && l < originalCols; l++) {
                        sum += path[k][l];
                        count++;
                    }
                }
                scaledPath[i][j] = sum >= (count / 2.0) ? 1 : 0;
            }
        }

        return scaledPath;
    }
}
