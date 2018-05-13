/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.clustering;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import structure.AnalysisContainer;
import structure.Data;
import structure.DataMask;
import utils.FileHandler;
import utils.Utils;
import vtbase.SlideException;


/**
 *
 * @author Soumita
 */
public class HierarchicalClusterer implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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
    //public String DISTANCE_FUNCTION;
    //public String LINKAGE;
    //public int LEAF_ORDERING;
    
    // public double[][] linkage_tree;
    // public int[] leaf_ordering;
    // public double[][] dendogram_data;
    
    //AnalysisContainer analysis;
    //Data data;
    //DataMask mask;
    HashMap <String, String> cache;
    
    public HierarchicalClusterer (String data_files_path,
                                  String python_module_path,
                                  String python_home) {
        
        this.PYTHON_MODULE_PATH = python_module_path;
        this.PYTHON_HOME = python_home;
        this.DATA_FILES_PATH = data_files_path;
        
        this.cache = new HashMap <String, String> ();
    }
    
    public HierarchicalClusterer () { }
    
    /*
    public void setDataMask(DataMask mask) {
        this.mask = mask;
    }
    */
    
    public BinaryTree doClustering(AnalysisContainer analysis,
                                   String linkage, 
                                   String distance_function, 
                                   int leaf_ordering) throws SlideException {
        
        String key = getCacheKey(analysis);
        if (this.cache.containsKey(key)) {
            String filetag = cache.get(key);
            String linkage_tree_fname = DATA_FILES_PATH + File.separator + "ClusteringOutput_" + filetag + ".txt";
            double[][] linkage_tree = null;
            try {
                linkage_tree = FileHandler.loadDoubleDelimData(linkage_tree_fname, " ", false);
            } catch (Exception e) {
                throw new SlideException("Cannot read linkage file " + linkage_tree_fname, 55);
            }
            return new BinaryTree(linkage_tree, leaf_ordering);
        } else {
            try {
                return doClustering(analysis, linkage, distance_function, leaf_ordering, key);
            } catch (Exception e) {
                throw new SlideException("Exception in HierarchicalClustering.java", 50);
            }
        }
    }
    
    private String getCacheKey (AnalysisContainer analysis) { 
        if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
            String cluster_params_hash_string = analysis.clustering_params.getHashString();
            String data_transform_params_hash_string = analysis.data_transformation_params.getHashString();
            String key = cluster_params_hash_string + "_" + data_transform_params_hash_string;
            return key;
        } else {
            String cluster_params_hash_string = analysis.clustering_params.getHashString();
            String enrich_params_hash_string = analysis.enrichment_params.getHashString();
            String key = cluster_params_hash_string + "_" + enrich_params_hash_string;
            return key;
        }
    }

    public BinaryTree doClustering (
            AnalysisContainer analysis, String linkage, String distance_function, int leaf_ordering, String key)
    throws SlideException {
        
        String id = System.currentTimeMillis() + "";
        Utils.saveDataMatrix(DATA_FILES_PATH + File.separator + "InData.txt", 
                "\t", 
                analysis.database.datacells,
                new DataMask(analysis.database)
        );
        String linkage_tree_fname = DATA_FILES_PATH + File.separator + "ClusteringOutput_" + id + ".txt";
        String error_fname = DATA_FILES_PATH + File.separator + "ClusteringError_" + id + ".txt";
        
        try {

            File link_file = new File(linkage_tree_fname);
            
            Files.deleteIfExists(link_file.toPath());

            ProcessBuilder pb = new ProcessBuilder(
                    PYTHON_HOME + File.separator + "python",
                    PYTHON_MODULE_PATH + File.separator + "fast_hierarchical_clustering.py",
                    DATA_FILES_PATH,
                    "InData.txt",
                    linkage,
                    distance_function,
                    id
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
            throw new SlideException("Failed to start clustering.", 53);
        }

        File link_file = new File(linkage_tree_fname);
        File error_file = new File(error_fname);
        
        boolean isClusteringSuccessful = true;
        int waiting = 0;
        while (!link_file.exists() && !error_file.exists()) {
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
        
        if (error_file.exists()) {
            isClusteringSuccessful = false;
        }

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
            double[][] linkage_tree = null;
            /*
            try {
                Files.deleteIfExists(link_file.toPath());
            } catch (Exception e) {
                System.out.println(e);
            }
            */
            try {
                linkage_tree = FileHandler.loadDoubleDelimData(linkage_tree_fname, " ", false);
            } catch (Exception e) {
                throw new SlideException("Cannot read linkage file " + linkage_tree_fname, 55);
            }
            cache.put(key, id);
            return new BinaryTree(linkage_tree, leaf_ordering);
        } else {
            throw new SlideException("Failed to perform clustering.", 54);
        }

    }
    
    public void clearCache() {
        this.cache.clear();
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