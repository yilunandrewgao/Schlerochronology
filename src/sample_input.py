import math
import sys
import random

params = sys.argv
e = math.e

random.seed()


if len(params) != 8:
	print 'Invalid Parameters'
	print 'Syntax is sample_input.py <num reads> <a> <b> <c> <min length> <max length> <deviation range>'
	sys.exit(1)
	# TODO: checking if inputs are valid, since this is internal use not all that important
else:
	reads = int(params[1])
	a = float(params[2])
	b = float(params[3]) * -1
	c = float(params[4])
	min = int(params[5])
	max = int(params[6])
	dev = float(params[7])

samples = [['Sample input file']]  # TODO: better header line

for i in range(1, reads+1):
	segments = random.randint(min, max)
	for j in range(1, segments+1):
		val = (a * (e ** (b * j)) + c) * (random.uniform((1-dev), (1+dev)))
		samples.append([])
		samples[i].insert(j, val)

for k in range(0, reads+1):
	if k!= 0:
		line = 'sample' + str(k) + ','
	else:
		line = ''

	line += ','.join(map(str, samples[k]))
	print line

sys.exit(0)
