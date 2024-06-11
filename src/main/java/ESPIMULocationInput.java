import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ESPIMULocationInput implements IInput {

    private Socket socket;
    private BufferedReader in;

    public ESPIMULocationInput() {
        connectToWebSocket();
    }

    private void connectToWebSocket() {
        try {
            // Connect to the WebSocket server running on the ESP32
            socket = new Socket("192.168.1.171", 8765); // Replace with actual ESP32 IP address
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("WebSocket connection opened");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pose init() {
        // Initialization code (if needed)
        return new Pose(0, 0, 0); // Initial pose (x, y, heading)
    }

    @Override
    public Pose update() {
        try {
            // Check if the socket is connected and try to reconnect if necessary
            if (socket == null || socket.isClosed()) {
                System.out.println("Reconnecting to WebSocket...");
                connectToWebSocket();
            }

            // Receive gyro data from the WebSocket server
            String message = null;
            if (in.ready()) {
                message = in.readLine();
            } else {
                System.out.println("Message not ready");
            }

            if (message != null) {
                // Process received gyro data and compute pose
                // For now, print the received data
                System.out.println("Received gyro data: " + message);
                // You can add code here to process the message and update the Pose
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (socket != null) {
                    socket.close();
                }
                connectToWebSocket();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        // Dummy update logic since gyro data is received via WebSocket
        return new Pose(0, 0, 0); // Update this logic as needed
    }
}
