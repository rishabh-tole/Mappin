import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ESPIMULocationInput implements IInput {

    private Socket socket;
    private BufferedReader in;
    private long lastTime = 0;
    private IMUProcessor imuProcessor = new IMUProcessor();
    private Pose currentPose = new Pose(0, 0, 0);

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
        lastTime = System.currentTimeMillis();
        return currentPose; // Initial pose (x, y, heading)
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
            }

            if (message != null) {
                // Process received gyro data and compute pose
                System.out.println("Received gyro data: " + message);

                double[][] array = dataToArray(message);
                double[] gyro = array[0];
                double[] accelerometer = array[1];

                // Calculate time difference since last update
                long currentTime = System.currentTimeMillis();
                float dt = (currentTime - lastTime) / 1000.0f; // Convert ms to seconds
                lastTime = currentTime;

                // Update IMUProcessor with new data
                imuProcessor.update(gyro, accelerometer, dt);

                // Get the updated position and heading
                float[] position = imuProcessor.getPosition();
                float heading = imuProcessor.getHeading();

                // Return the updated pose
                currentPose = new Pose((int) position[0], (int) position[1], (int) heading);
                return currentPose;
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

        // Return a default pose if there's no update
        return currentPose;
    }

    private double[][] dataToArray(String str){
        str = str.substring(1, str.length() - 1);

        // Split the string into rows
        String[] rows = str.split("\\], \\[");

        // Create a 2D array
        double[][] array = new double[rows.length][];

        // Process each row
        for (int i = 0; i < rows.length; i++) {
            // Remove any remaining brackets
            rows[i] = rows[i].replace("[", "").replace("]", "");

            // Split the row into individual numbers
            String[] values = rows[i].split(", ");

            // Convert the string values to doubles and store in the array
            array[i] = new double[values.length];
            for (int j = 0; j < values.length; j++) {
                array[i][j] = Double.parseDouble(values[j]);
            }
        }

        return array;
    }
}