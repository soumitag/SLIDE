/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package params;

import java.io.Serializable;

/**
 *
 * @author soumita
 */

public class ClusteringParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
    public String linkage;
    public String distance_func;
    public boolean do_clustering;
    
    public ClusteringParams() {
        linkage = "";
        distance_func = "";
        do_clustering = false;
    }
 
    public void setLinkage(String linkage) {
        this.linkage = linkage;
    }
    
    public void setDistanceFunc(String distance_func) {
        this.distance_func = distance_func;
    }
    
    public void setDoClustering(boolean do_clustering) {
        this.do_clustering = do_clustering;
    }
    
    public int getLinkageIndex() {
        if (this.linkage.equalsIgnoreCase("average")) {
            return 0;
        } else if (this.linkage.equalsIgnoreCase("complete")) {
            return 1;
        } else if (this.linkage.equalsIgnoreCase("median")) {
            return 2;
        } else if (this.linkage.equalsIgnoreCase("centroid")) {
            return 3;
        } else if (this.linkage.equalsIgnoreCase("ward")) {
            return 4;
        } else if (this.linkage.equalsIgnoreCase("weighted")) {
            return 5;
        } else {
            return 0;
        }
    }
    
    public int getDistanceFuncIndex() {
        if (this.distance_func.equalsIgnoreCase("euclidean")) {
            return 0;
        } else if (this.distance_func.equalsIgnoreCase("cityblock")) {
            return 1;
        } else if (this.distance_func.equalsIgnoreCase("cosine")) {
            return 2;
        } else if (this.distance_func.equalsIgnoreCase("correlation")) {
            return 3;
        } else if (this.distance_func.equalsIgnoreCase("chebyshev")) {
            return 4;
        } else {
            return 0;
        }
    }
    
    /*
    public boolean equals(ClusteringParams that) {
        
        return (this.linkage.equals(that.linkage) &&
                this.distance_func.equals(that.distance_func) &&
                this.do_clustering == that.do_clustering
               );
    
    }
    */
    
    public String getHashString() {
        return linkage + "_" + distance_func;
    }
}
