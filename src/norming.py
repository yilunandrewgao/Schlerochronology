import numpy
import math
import matplotlib.pyplot as plt
import sys

def norm(x_values, y_values):
    C = min(y_values)*.99
    print C
    transformed_y = [math.log(y - C) for y in y_values]
    return numpy.polyfit(x_values, transformed_y, 1)


def function(x,A,B,C):

    result = A*math.exp(B*x) + C

    return result

def longestShell(shells):
    length = 0
    for shell in shells:
        if len(shell) > length:
            length = len(shell)

    return length

def average(shells):
    averageWidths = []

    for i in range(longestShell(shells)):
        bands = 0
        total = 0
        for j in range(len(shells)):
            if i < len(shells[j]):
                bands += 1
                total += shells[j][i]
        average = total/bands
        averageWidths.append(average)

    return averageWidths

def main():

    header= sys.stdin.readline()

    lines = sys.stdin.readlines()

    shellWidths = [map(float,line.split(",")[1:]) for line in lines]

    print shellWidths

    averageWidths = average(shellWidths)

    y_values = averageWidths
    print y_values
    x_values = range(len(averageWidths))

    [A,B] = norm(x_values,y_values)
    C = min(y_values) *.99

    domain = numpy.arange(0,max(x_values),.1)
    output = [function(x,math.exp(A),B,C) for x in domain]

    plt.plot(x_values,y_values, 'ro', domain, output)
    plt.show()

    print math.exp(A),B,C


main()

