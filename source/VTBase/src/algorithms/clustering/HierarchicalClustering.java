/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.clustering;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import structure.Data;
import structure.DataMask;
import utils.Utils;


/**
 *
 * @author Soumita
 */
public class HierarchicalClustering {
    
    public static final String SINGLE_LINKAGE      = "single";
    public static final String COMPLETE_LINKAGE    = "complete";
    public static final String AVERAGE_LINKAGE     = "average";
    public static final String MEDIAN_LINKAGE      = "median";
    public static final String CENTROID_LINKAGE    = "centroid";
    public static final String WARD_LINKAGE        = "ward";
    public static final String WEIGHTED_LINKAGE    = "weighted";
    
    public static final String EUCLIDEAN_DISTANCE      = "euclidean";
    public static final String CITYBLOCK_DISTANCE      = "cityblock";
    public static final String COSINE_DISTANCE         = "cosine";
    public static final String CORRELATION_DISTANCE    = "correlation";
    public static final String CHEBYSHEV_DISTANCE      = "chebyshev";
    
    public String PYTHON_HOME;
    public String PYTHON_MODULE_PATH;
    public String DATA_FILES_PATH;
    public String DISTANCE_FUNCTION;
    public String LINKAGE;
    public int LEAF_ORDERING;
    
    // public double[][] linkage_tree;
    // public int[] leaf_ordering;
    // public double[][] dendogram_data;
    
    Data data;
    DataMask mask;
    
    
    public HierarchicalClustering (Data data,
                                   String linkage, 
                                   String distance_function, 
                                   int leaf_ordering,
                                   String data_files_path,
                                   String python_module_path,
                                   String python_home) {
        this.data = data;
        this.mask = new DataMask(this.data);
        this.LINKAGE = linkage;
        this.DISTANCE_FUNCTION = distance_function;
        this.LEAF_ORDERING = leaf_ordering;
        this.PYTHON_MODULE_PATH = python_module_path;
        this.PYTHON_HOME = python_home;
        this.DATA_FILES_PATH = data_files_path;
    }
    
    public HierarchicalClustering () { }
    
    public void setDataMask(DataMask mask) {
        this.mask = mask;
    }
    
    public BinaryTree doClustering(boolean use_cached) {
        
        if (use_cached) {
            
            String linkage_tree_fname = DATA_FILES_PATH + File.separator + "ClusteringOutput_0.txt";
            return new BinaryTree(
                Utils.loadDoubleDelimData(linkage_tree_fname, " ", false),
                this.LEAF_ORDERING
            );
            
        } else {
            return doClustering();
        }
    }

    public BinaryTree doClustering() {
        
        int id = 0;
        data.saveDataMatrix(DATA_FILES_PATH + File.separator + "InData.txt", "\t", "normed", mask);
        String linkage_tree_fname = DATA_FILES_PATH + File.separator + "ClusteringOutput_" + id + ".txt";
        
        try {

            File link_file = new File(linkage_tree_fname);
            
            Files.deleteIfExists(link_file.toPath());

            ProcessBuilder pb = new ProcessBuilder(
                    PYTHON_HOME + File.separator + "python",
                    PYTHON_MODULE_PATH + File.separator + "fast_hierarchical_clustering.py",
                    DATA_FILES_PATH,
                    "InData.txt",
                    LINKAGE,
                    DISTANCE_FUNCTION,
                    id + ""
            );
            System.out.println(pb.toString());
            
            pb.directory(new File(PYTHON_MODULE_PATH));
            File log = new File(PYTHON_MODULE_PATH + File.separator + "log.txt");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
            Process p = pb.start();
            assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
            assert pb.redirectOutput().file() == log;
            assert p.getInputStream().read() == -1;

            //p.destroyForcibly();
        } catch (Exception e) {
            System.out.println(e);
        }

        File link_file = new File(linkage_tree_fname);
        
        boolean isClusteringSuccessful = true;
        int waiting = 0;
        while (!link_file.exists()) {
            waiting++;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(e);
            }
            if (waiting > 6000) {
                isClusteringSuccessful = false;
                break;
            }
        }
        System.out.println("Waited for: " + waiting);

        if (isClusteringSuccessful) {
            // wait another 2 secs for filewrite to finish
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(e);
            }
        }
        
        //this.linkage_tree = Utils.loadDoubleDelimData(linkage_tree_fname, " ", false);
        //this.dendogram_data = Utils.loadDoubleDelimData(dendogram_data_fname, " ", false);
        /*
        return new BinaryTree(
                    data.datacells.length, 
                    Utils.loadDoubleDelimData(linkage_tree_fname, " ", false),
                    Utils.loadDoubleDelimData(dendogram_data_fname, " ", false),
                    Utils.loadDoubleDelimData(leaf_ordering_fname, " ", false)
        );
        */
        if (isClusteringSuccessful) {
            return new BinaryTree(
                Utils.loadDoubleDelimData(linkage_tree_fname, " ", false),
                this.LEAF_ORDERING
            );
        } else {
            return null;
        }

    }
    
    public double[][] extractTopKNodes(double[][] linkage_tree, int start_node, int K) {
        ArrayList <Integer> sub_tree = new ArrayList <Integer> ();
        sub_tree.add((int)linkage_tree[start_node][0]);
        sub_tree.add((int)linkage_tree[start_node][1]);
        getChild(sub_tree, linkage_tree, (int)linkage_tree[start_node][0], 0, K);
        getChild(sub_tree, linkage_tree, (int)linkage_tree[start_node][1], 0, K);
        return null;
    }
    
    public void getChild(ArrayList <Integer> sub_tree, double[][] linkage_tree, int curr_node, int count, int K) {
        if (count < K) {
            sub_tree.add((int)linkage_tree[curr_node][0]);
            sub_tree.add((int)linkage_tree[curr_node][1]);
            getChild(sub_tree, linkage_tree, (int)linkage_tree[curr_node][0], ++count, K);
            getChild(sub_tree, linkage_tree, (int)linkage_tree[curr_node][1], ++count, K);
        }
    }
    
}