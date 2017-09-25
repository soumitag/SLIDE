import fastcluster as fc
import numpy as np
import scipy as sp
import sys

infilename = str(sys.argv[1])
linkage_startegy = str(sys.argv[2])
distance_metric = str(sys.argv[3])
num_rows = int(str(sys.argv[4]))
num_cols = int(str(sys.argv[5]))
process_id = str(sys.argv[6])

print ("Loading Data...")
data = np.fromfile(infilename, dtype=float, count=-1, sep='\t')
data2 = np.reshape(data, (num_rows, num_cols))
print (data2.shape)
print ("Done.")

print ("Creating Distance Matrix...")
#D = sp.spatial.distance.pdist (data2, 'euclidean')
D = sp.spatial.distance.pdist (data2, distance_metric)
print ("Done.")

print ("Clustering...")
#Z = fc.linkage(D, method='average', metric='euclidean', preserve_input=True)
Z = fc.linkage(D, method=linkage_startegy, metric=distance_metric, preserve_input=False)
print ("Done.")

print ("Saving To File...")
np.savetxt('ClusteringOutput_' + process_id + '.txt', Z)
print ("Done.")

print ("Process Completed")
