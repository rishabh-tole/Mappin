public class IMUProcessor {
    private float posX = 0, posY = 0;
    private float velocityX = 0, velocityY = 0;
    private float heading = 0; // In degrees

    public void update(double[] gyro, double[] accel, float dt) {
        // Convert gyro readings from radians to degrees
        float gyroZ = (float) Math.toDegrees(gyro[2]);

        // Update heading
        heading += gyroZ * dt;

        // Integrate acceleration to get velocity (assumes accel is in m/s^2 and dt is in seconds)
        // TODO: bro ts does not work :(
        //velocityX += accel[0] * dt;
        //velocityY += accel[1] * dt;

        // Integrate velocity to get position (convert from meters to centimeters)
        //posX += velocityX * dt * 100;
        //posY += velocityY * dt * 100;
    }

    public float[] getPosition() {
        return new float[]{posX, posY};
    }

    public float getHeading() {
        return heading;
    }
}