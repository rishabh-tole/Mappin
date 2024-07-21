from mpu9250 import MPU9250
from machine import Pin, I2C
import time
from time import sleep

from machine import I2C, Pin
from mpu9250 import MPU9250


class MotionDetector:



    def __init__(self, settling_time=4):
        self.i2c = I2C(scl=Pin(22), sda=Pin(21))
        self.imu = MPU9250(self.i2c)
        self.t0 = time.time()
        self.GYRO_OFFSETS_CONST = [0.03087347, -0.01512753, 0.008757836]
        self.ACCEL_OFFSETS_CONST = [-0.2086289, -0.007525306, 9.399065]
        # Wait for MPU to Settle
        print('Settling MPU for %d seconds' % settling_time)
        time.sleep(settling_time)
        print('MPU is Done Settling')


    def get_gyro(self):
        return self.imu.gyro

    def get_accel(self):
        return self.imu.acceleration


    def calibration(self, calibration_time=10):
        """
            Description: This is a function to get the offset values
                for gyro calibration for mpu6050.

            Parameters:

            calibration_time[int]: Time in seconds you want to calibrate
                mpu6050. The longer the time the more accurate the
                calibration

            Outputs: Array with offsets pertaining to three axes of
                rotation [offset_gx, offset_gy, offset_gz]. Add these
                offsets to your sensor readins later on for more
                accurate readings!
        """
        print('--' * 25)
        print('Beginning Gyro Calibration - Do not move the MPU6050')

        # placeholder for the average of tuples in mpu_gyro_array
        gyro_offsets = [0, 0, 0]
        accel_offsets = [0, 0, 0]
        # placeholder for number of calculations we get from the mpu
        num_of_points = 0

        # We get the current time and add the calibration time
        end_loop_time = time.time() + calibration_time
        # We end the loop once the calibration time has passed
        while end_loop_time > time.time():
            num_of_points += 1
            (gx, gy, gz) = self.get_gyro()
            (ax, ay, az) = self.get_accel()
            gyro_offsets[0] += gx
            gyro_offsets[1] += gy
            gyro_offsets[2] += gz

            accel_offsets[0] += ax
            accel_offsets[1] += ay
            accel_offsets[2] += az
            # This is just to show you its still calibrating :)
            if num_of_points % 100 == 0:
                print('Still Calibrating Gyro... %d points so far' % num_of_points)

        print('Calibration for Gyro is Complete! %d points total' % num_of_points)
        gyro_offsets = [i/num_of_points for i in gyro_offsets]
        accel_offsets = [i/num_of_points for i in accel_offsets]# we divide by the length to get the mean
        print(gyro_offsets)
        print(accel_offsets)
        return gyro_offsets, accel_offsets


    def get_calibrated_gyro_values(self):
        gx= self.imu.gyro[0] - self.GYRO_OFFSETS_CONST[0]
        gy= self.imu.gyro[1] - self.GYRO_OFFSETS_CONST[1]
        gz= self.imu.gyro[2] - self.GYRO_OFFSETS_CONST[2]
        return [gx, gy, gz]

    def get_calibrated_accel_values(self):
        ax = self.imu.acceleration[0] - self.ACCEL_OFFSETS_CONST[0]
        ay = self.imu.acceleration[1] - self.ACCEL_OFFSETS_CONST[1]
        az = self.imu.acceleration[2] - self.ACCEL_OFFSETS_CONST[2]
        return [ax, ay, az]
