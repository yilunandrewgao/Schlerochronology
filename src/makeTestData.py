import math
import sys
import random
import numpy

def generateTimeline(length, stddev):

    Timeline = []

    for i in range(length):
        Timeline.append(numpy.random.normal(loc=1,scale=stddev))

    return Timeline
    

def getShells(timeline, numShells, minLen, maxLen):

    shells=[]

    for i in range(numShells):
        shellLen = random.randint(minLen, maxLen)
        start = random.randint(0,len(timeline))
        end = start + shellLen
        shells.append(timeline[start:end])
    
    return shells


def main():

    params = sys.argv

    random.seed()

    if len(params) != 6:
        print "Invalid params"
        sys.exit(1)
    else:
        reads = int(params[1])
        minLen = int(params[2])
        maxLen = int(params[3])
        timelineLen = int(params[4])
        stddev = float(params[5])


    timeline = generateTimeline(timelineLen, stddev)
    shells = getShells(timeline, reads, minLen, maxLen)


    output = "Sample output file\n"

    for i in range(len(shells)):
        line = "sample" + str(i+1) + ","
        shellLen = len(shells[i])
        read = [shells[i][j] for j in range(shellLen)]
        line += ','.join(map(str,read))
        line += "\n"
        output += line

    print output[:-1]

main()
    