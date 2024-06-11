import network
import uasyncio as asyncio
import usocket as socket
import urandom
import servo_positions_buffer

#Initialize Buffer

position_buffer = servo_positions_buffer.ServoPositionsBuffer()

#Only start taks on even numbers of connections since we will always connect 1 input and 1 output
global counter
counter = 0


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
            random_values = [urandom.random() for _ in range(3)]
            await writer.awrite(f"{random_values}\n")
            await asyncio.sleep(0.1)
    except OSError as e:
        print("Error in send_random_values:", e)
    finally:
        await writer.aclose()

async def set_servo_positions(position_buffer):
    while True:
        print(position_buffer.get_newest_position())
        await asyncio.sleep(3)



async def handle_client(reader, writer):
    try:
        # Start the task to send random values in the background
        global counter
        if (counter%2==0):
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
                await asyncio.sleep(0.5)

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
        await asyncio.sleep(1)  # Keep the event loop running

# Run the event loop
asyncio.run(main())

