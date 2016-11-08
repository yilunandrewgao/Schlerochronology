import numpy
import math
import matplotlib.pyplot as plt

def norm(x_values, y_values):
    C = y_values[-1]*.9
    transformed_y = [math.log(y - C) for y in y_values]
    return numpy.polyfit(x_values, y_values, 1)

def function(x):
    A = 1
    B = -1
    C = 0
    result = A*math.exp(B*x) + C

    return result

x_values = range(10)
y_values = [function(x) for x in x_values]

[A,B] = norm(x_values,y_values)
C = y_values[-1]*.9

domain = numpy.arange(0,10,.1)
output = [function(x) for x in domain]

plt.plot(x_values,y_values, 'ro', domain, output)
plt.show()
