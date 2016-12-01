import numpy
import math
import matplotlib.pyplot as plt
import sys

def norm(x_values, y_values):
    C = min(y_values)*.99
    transformed_y = [math.log(y - C) for y in y_values]
    return numpy.polyfit(x_values, transformed_y, 1)

# better regression method from stack overflow post
def betterNorm(x_values, y_values):
    # make the S sequence
    # equation is y = Ae^(Bx) + C
    S = [0]
    for k in range(1,len(x_values)):
        S.append(S[k-1]+ .5*(y_values[k]+y_values[k-1])*(x_values[k]-x_values[k-1]))


    matrixA = numpy.matrix([ [sum([math.pow(x - x_values[0],2) for x in x_values]), 
    sum([(x - x_values[0])*s for x,s in zip(x_values,S)])],
     [sum([(x - x_values[0])*s for x,s in zip(x_values,S)]), 
     sum([math.pow(s,2) for s in S])]])

    matrixB = numpy.matrix([[sum([(y - y_values[0])*(x - x_values[0]) for y,x in zip(y_values,x_values)])],
    [sum([(y - y_values[0])*s for y,s in zip(y_values,S)])]])

    matrixC = matrixA.I*matrixB
    B_value = matrixC.item(1)

    matrixD = numpy.matrix([[len(x_values),
    sum([math.exp(B_value*x) for x in x_values])],
    [sum([math.exp(B_value*x) for x in x_values]),
    sum([math.exp(2*B_value*x) for x in x_values])]])

    matrixE = numpy.matrix([[sum([y for y in y_values])],
    [sum([y*math.exp(B_value*x) for y,x in zip(y_values,x_values)])]])

    matrixF = matrixD.I*matrixE

    C_value = matrixF.item(0)
    A_value = matrixF.item(1)

    return A_value, B_value, C_value
    

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

    averageWidths = average(shellWidths)

    y_values = averageWidths

    x_values = range(len(averageWidths))

    [A,B,C] = betterNorm(x_values,y_values)

    reads = len(shellWidths)
    
    output = "Sample output file\n"

    for i in range(reads):
        line = "sample" + str(i+1) + ","
        shellLen = len(shellWidths[i])
        divideBy = [function(x,A,B,C) for x in range(shellLen)]
        normedWidths = [x/y for x, y in zip(shellWidths[i], divideBy)]
        line += ','.join(map(str,normedWidths))
        line += "\n"
        output += line

    print output[:-1]
    


    domain = numpy.arange(0,max(x_values),.1)
    output = [function(x,A,B,C) for x in domain]

    plt.plot(x_values,y_values, 'ro', domain, output)
    #plt.show()
    plt.savefig('normedFig.png')

    


main()

