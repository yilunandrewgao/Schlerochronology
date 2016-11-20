import numpy
import math
import matplotlib.pyplot as plt
import sys


def equalBinning(allWidths, numBins):
    allWidths.sort()
    elemsInBin = len(allWidths) / numBins

    bins = [allWidths[elemsInBin*i] for i in range(numBins)]
    bins.append(allWidths[-1])

    return bins

def BinSequence(widths, bins):
    return numpy.digitize(widths, bins)

    

def main():

    header= sys.stdin.readline()

    lines = sys.stdin.readlines()

    shellWidths = [map(float,line.split(",")[1:]) for line in lines]

    allWidths = [bandWidth for shell in shellWidths for bandWidth in shell]

    reads = len(shellWidths)
    
    output = "Sample output file\n"

    for i in range(reads):
        line = "sample" + str(i+1) + ","
        line += ','.join(map(str,BinSequence(shellWidths[i],equalBinning(allWidths,16))))
        line += "\n"
        output += line

    print output[:-1]

    plt.hist(allWidths, equalBinning(allWidths,16))
    plt.show()

main()