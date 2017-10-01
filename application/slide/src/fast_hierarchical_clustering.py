import fastcluster as fc
import numpy as np
import scipy as sp
import sys
import os
import scipy.cluster.hierarchy as hac

data_folderpath = str(sys.argv[1])
infilename = str(sys.argv[2])
linkage_startegy = str(sys.argv[3])
distance_metric = str(sys.argv[4])
process_id = str(sys.argv[5])

print ("Loading Data...")
data = np.loadtxt(os.path.join(data_folderpath, infilename), dtype=float, delimiter='\t')
print ("Done.")

print ("Creating Distance Matrix...")
#D = sp.spatial.distance.pdist (data, 'euclidean')
D = sp.spatial.distance.pdist (data, distance_metric)
print ("Done.")

print ("Clustering...")
#Z = fc.linkage(D, method='average', metric='euclidean', preserve_input=True)
Z = fc.linkage(D, method=linkage_startegy, metric=distance_metric, preserve_input=False)
print ("Done.")

# print ("Preparing Dendogram...")
# R = hac.dendrogram(Z, 100, truncate_mode='lastp', orientation='left', no_plot=True, count_sort=True, distance_sort=False, show_leaf_counts=False)
# Y = R['icoord']
# X = R['dcoord']
# C = [(x[0], y[0], x[1], y[1], x[2], y[2], x[3], y[3]) for x,y in zip(X,Y)]
# RL = hac.dendrogram(Z, orientation='left', no_plot=True, count_sort=True, distance_sort=False, show_leaf_counts=False)
# print ("Done.")

print ("Saving To File...")
# np.savetxt('Leaf_Ordering_' + process_id + '.txt', RL['leaves'], fmt='%d')
# np.savetxt('Dendogram_' + process_id + '.txt', C, fmt='%.2f')
np.savetxt(os.path.join(data_folderpath, 'ClusteringOutput_' + process_id + '.txt'), Z, fmt='%.3f')
print ("Done.")

print ("Process Completed")
