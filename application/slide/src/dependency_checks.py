import sys
import numpy as np

def print_to_file(value):
	text_file = open(sys.argv[1], "w")
	text_file.write(str(value))
	text_file.close()

def test():
	package = 'fastcluster'
	try:
		__import__(package)
	except ImportError:
		print_to_file("-1")
		return -1
		
		
	package = 'numpy'
	try:
		__import__(package)
	except ImportError:
		print_to_file("-2")
		return -2
		
	package = 'scipy'
	try:
		__import__(package)
	except ImportError:
		print_to_file("-3")
		return -3
		
	package = 'scipy.cluster.hierarchy'
	try:
		__import__(package)
	except ImportError:
		print_to_file("-4")
		return -4
		
		
	print_to_file("1")
	return 1
		
test()