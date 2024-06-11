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
        out.println(json);
    }

    @Override
    public void initialize(Positions positions) {
        // Initialization code (if needed)
    }

    private String positionsToJson(Positions positions) {
        // Convert Positions array to JSON format (implement this method according to your JSON format)
        // Example: "{\"positions\": [[true, false, true], [false, true, false]]}"
        // This is a simple example, you may need to adjust it based on your actual Positions format
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
}
