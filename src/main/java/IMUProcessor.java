public class IMUProcessor {
    private float posX = 0, posY = 0;
    private float velocityX = 0, velocityY = 0;
    private float heading = 0; // In degrees

    public void update(double[] gyro, double[] accel, float dt) {
        // Convert gyro readings from radians to degrees
        float gyroZ = (float) Math.toDegrees(gyro[2]);

        // Update heading (clockwise direction)
        heading -= gyroZ * dt;

        // Ensure heading stays within 0-360 degrees
        if (heading < 0) {
            heading += 360;
        } else if (heading >= 360) {
            heading -= 360;
        }

        // Integrate acceleration to get velocity (assumes accel is in m/s^2 and dt is in seconds)
//        velocityX += accel[0] * dt;
//        velocityY += accel[1] * dt;

        // Integrate velocity to get position (convert from meters to centimeters)
//        posX += velocityX * dt * 100;
//        posY += velocityY * dt * 100;
    }

    public float[] getPosition() {
        return new float[]{posX, posY};
    }

    public float getHeading() {
        return heading;
    }
}
