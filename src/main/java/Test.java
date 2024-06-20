public class Test {
    public static void main(String[] args) throws InterruptedException {

        ESPIMULocationInput espimuLocationInput = new ESPIMULocationInput();

        espimuLocationInput.init();

        DeviceOutput deviceOutput = new DeviceOutput();

        deviceOutput.initialize(new Positions(new int[10][10]));

        while (true) {
            deviceOutput.update(new Positions(new int[10][10]));
            Pose p = espimuLocationInput.update();

            System.out.println(p);

            Thread.sleep(100);
        }
    }
}
