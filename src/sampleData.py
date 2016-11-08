from __future__ import division
import math
from scipy.integrate import quad
import numpy
import matplotlib.pyplot as plt

def function(x):
    result = 1/math.sqrt(2*math.pi)*math.exp(-x**2/2)
    return result

def doubleGaussian(x):
    result = 1/math.sqrt(2*math.pi)*math.exp(-x**2/2) + 1/math.sqrt(2*math.pi)*math.exp(-(x-6)**2/2)

    return result



def discreteProbs(function, binSize, start, end):

    bins = numpy.arange(start, end, binSize)

    probs = []

    for i in range(int(math.ceil((end - start)/binSize)) - 1):
        ans,err = quad(function, bins[i], bins[i+1])
        probs.append(ans)

    return [prob/sum(probs) for prob in probs]


def generateSamples(numSamples, function, binSize, start, end):
    probs = discreteProbs(function,binSize,start,end)
    
    samples = numpy.random.choice(numpy.arange(start,end-binSize,binSize), numSamples, p = probs)

    plt.hist(samples, numpy.arange(start,end-binSize,binSize))
    plt.show()
    return samples

generateSamples(1000, doubleGaussian, .1,-3,9)







