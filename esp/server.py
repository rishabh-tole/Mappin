import network
import uasyncio as asyncio
import usocket as socket
import urandom
import servo_positions_buffer
import imu_dumper
from machine import Pin
import time
from machine import Pin, I2C
from servo import Servos

#Initialize Stuff

position_buffer = servo_positions_buffer.ServoPositionsBuffer()
sensor = imu_dumper.MotionDetector()
p = Pin(34, Pin.IN)

i2c = I2C(scl=Pin(22), sda=Pin(21))
servos = Servos(i2c)

#Only start taks on even numbers of connections since we will always connect 1 input and 1 output
global counter
counter = 0

#initialize positions to zero
for x in range(16):
    servos.position(x, 0)


# Connect to Wi-Fi
def connect_to_wifi(ssid, password):
    wlan = network.WLAN(network.STA_IF)
    if not wlan.isconnected():
        print('Connecting to Wi-Fi...')
        wlan.active(True)
        wlan.connect(ssid, password)
        while not wlan.isconnected():
            pass
    print('Wi-Fi connected:', wlan.ifconfig())

async def send_random_values(writer):
    try:
        while True:
            # Generate and send random values to the client
            #random_values = [sensor.get_calibrated_gyro_values(), sensor.get_calibrated_accel_values()]
            random_values = [sensor.get_calibrated_gyro_values(), [p.value(), -1,-1]]
            await writer.awrite(f"{random_values}\n")
            await asyncio.sleep(0.1)
    except OSError as e:
        print("Error in send_random_values:", e)
    finally:
        await writer.aclose()


async def set_servo_positions(position_buffer):
    while True:
        pos = position_buffer.get_newest_position()

        oned = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        if pos is not None:
            oned = parse_positions(pos)

        print(oned)

        # Servo mappings: [servo_index] = [original_index]
        servo_mappings = [3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12]

        for i in range(16):
            # Get the actual servo index from the mapping
            servo_index = servo_mappings[i]

            if oned[i] == 0:
                servos.position(servo_index, 0)
            elif oned[i] == 1:
                servos.position(servo_index, 180)

            time.sleep_ms(100)

        await asyncio.sleep(0.1)


def parse_positions(json_string):
    # Remove unwanted characters and split the string
    json_string = json_string.replace('{"positions": ', '').replace('}', '')
    json_string = json_string.replace('[', '').replace(']', '')

    # Split by commas and convert to integers
    one_d_array = [int(num) for num in json_string.split(',') if num]
    return one_d_array

async def handle_client(reader, writer):
    try:
        # Start the task to send random values in the background
        global counter
        if (counter%1==0):
            asyncio.create_task(send_random_values(writer))
            asyncio.create_task(set_servo_positions(position_buffer))
        counter+=1
        while True:
            # Receive data from the client
            data = await reader.readline()
            if data:
                pos_string = str(data.decode().strip())
                #print(pos_string)
                position_buffer.add_position(pos_string)
            await asyncio.sleep(0.1)
    except OSError as e:
        if e.args[0] == 104:
            print("Client connection reset.")
        else:
            print("Error in handle_client:", e)
    finally:
        await writer.aclose()
        await reader.aclose()

async def main():
    # Connect to Wi-Fi
    connect_to_wifi('Cheezits_2.4G', 'DoorBell20')

    # Set up WebSocket server
    server = await asyncio.start_server(handle_client, '0.0.0.0', 8765)
    print('WebSocket server running on ws://0.0.0.0:8765')

    i=0
    while True:
        print("Server up for " + str(i) + " seconds")
        i+=1
        await asyncio.sleep(10)  # Keep the event loop running

# Run the event loop
asyncio.run(main())

