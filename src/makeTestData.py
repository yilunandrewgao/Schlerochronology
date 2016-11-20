import math
import sys
import random

def getShells(timeline):

    boolList = []

    shells=[]

    for i in range(len(timeline)+1):
        if i < len(timeline) + 1 - (min - 1):
            boolList.append(True)
        else:
            boolList.append(False)

    while 1:
        openIndices = [boolList[i] for i in len(boolList) and boolList[i] = True]


def main():

    params = sys.argv

    random.seed()

    if len(params) != 4:
        print "Invalid params"
        sys.exit(1)
    else:
        reads = params[0]
        min = params[1]
        max = params[2]
        numBins = params[3]


    totalLen = 2*reads + max -1

    timeline = []

    for i in range(totalLen):
        timeline.append(random.uniform(1,numBins+1))

    