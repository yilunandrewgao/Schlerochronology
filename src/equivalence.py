from __future__ import division
import math
from scipy.integrate import quad
import numpy

def integrand(x):
    result = 1/math.sqrt(2*math.pi)*math.exp(-x**2/2)
    return result



def findFraction(function, lowerValue, upperValue):

    ans, err = quad(function, lowerValue, upperValue)
    totalArea, err = quad(function, -numpy.inf, numpy.inf)
    result = ans/totalArea

    return result


def giveMaxValue(function, lowerValue, closenessParam = .05):

    start = lowerValue
    stop1 = lowerValue
    stop2 = 2*lowerValue + 1

    if (findFraction(function, -numpy.inf, lowerValue) > (1 - closenessParam)):
        return numpy.inf


    while (abs(findFraction(function, start, stop2) - closenessParam) > .001):

        print (stop1, stop2)
        stop2copy = stop2
        if (findFraction(function, start, stop2) < closenessParam):
            stop2 = start + 2*(stop2 - start)
            stop1 = stop2copy
        else:
            stop2 = (stop1+stop2)/2

                



    return stop2


print giveMaxValue(integrand, 2, .05)
