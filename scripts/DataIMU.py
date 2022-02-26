# importing the required module
import matplotlib.pyplot as plt
import json
import sys

input_arg = sys.argv[::-1][0]
if(len(sys.argv) == 2):
    f = open(input_arg)
else:
    raise Exception("Can only input one thing")
data = json.load(f)

time = []
raw_chassis = []
linear_chassis = []
encoder_chassis = []
raw_acceleration = []
linear_acceleration = []

for i in data:
    time.append(float(i))
    raw_chassis.append(float(data[i][0]))
    linear_chassis.append(float(data[i][1]) )
    encoder_chassis.append(float(data[i][2]))
    raw_acceleration.append(float(data[i][3]))
    linear_acceleration.append(float(data[i][4]))

      
f.close() 
# plotting the points
plt.figure()
plt.plot(time, raw_chassis, label="raw Chassis IMU")
plt.plot(time, linear_chassis, label="linear Chassis IMU")
plt.plot(time, encoder_chassis, label="encoder Chassis")

 
# naming the x axis
plt.xlabel('time')
# naming the y axis
plt.ylabel('meters per second')
 
# giving a title to my graph
plt.title('Velocities vs Time')

plt.legend()

plt.figure()
plt.plot(time, raw_acceleration, label="raw acceleration")
plt.plot(time, linear_acceleration, label="linear acceleration")
 
# naming the x axis
plt.xlabel('time')
# naming the y axis
plt.ylabel('meters per second squared')
 
# giving a title to my graph
plt.title('Acceleration vs Time')

plt.legend()

# function to show the plot
plt.show()
