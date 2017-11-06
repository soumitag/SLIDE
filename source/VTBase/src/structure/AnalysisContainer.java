/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import algorithms.clustering.BinaryTree;
import algorithms.enrichment.EnrichmentAnalysis;
import graphics.Heatmap;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.Serializable;
import searcher.Searcher;
import utils.Logger;
import utils.SessionManager;
import vtbase.DataParsingException;

/**
 *
 * @author Soumita
 */
public class AnalysisContainer implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int GENE_LEVEL_VISUALIZATION = 0;
    public static final int PATHWAY_LEVEL_VISUALIZATION = 1;
    public static final int ONTOLOGY_LEVEL_VISUALIZATION = 2;
    
    public int visualizationType;
    public boolean isMasterAnalysis;
    public boolean isTreeAvailable;
    public String analysis_name;
    public String base_path;
    
    public Data database;
    public Heatmap heatmap;
    public BinaryTree linkage_tree;
    
    public EnrichmentAnalysis ea;
            
    public HashMap <String, ArrayList <Integer>> entrezPosMap;
    
    public ArrayList <String> search_strings;
    public ArrayList <ArrayList <CompactSearchResultContainer>> search_results;
    
    public HashMap <String, ArrayList <Integer>> filterListMap;
    
    public HashMap <String, String> clustering_params;
    public HashMap <String, String> visualization_params;
    public HashMap <String, Double> enrichment_params;
    
    public StateVariables state_variables;
    
    public Searcher searcher;
    public Logger logger;
    
    public AnalysisContainer () { 
        isTreeAvailable = false;
        visualizationType = AnalysisContainer.GENE_LEVEL_VISUALIZATION;
        state_variables = new StateVariables();
    }
    
    public void setAnalysisName (String analysis_name) {
        this.analysis_name = analysis_name;
    }
    
    public void setBasePath (String base_path) {
        this.base_path = base_path;
    }
    
    public void setDatabase (Data database) {
        this.database = database;
        state_variables.init(database.features.size());
    }
    
    public void setClusteringParams (HashMap <String, String> clustering_params) {
        this.clustering_params = clustering_params;
    }
    
    public void setVisualizationParams (HashMap <String, String> visualization_params) {
        this.visualization_params = visualization_params;
    }
    
    public void setEnrichmentParams (HashMap <String, Double> enrichment_params) {
        this.enrichment_params = enrichment_params;
    }
    
    /*
    public void setStateVariables (HashMap <String, Double> state_variables) {
        this.state_variables = state_variables;
    }
    */
    
    public void setSearchResults (ArrayList <ArrayList <CompactSearchResultContainer>> search_results) {
        this.search_results = search_results;
    }
    
    public void setSearchStrings (ArrayList <String> search_strings) {
        this.search_strings = search_strings;
    }
    
    public void setHeatmap (Heatmap heatmap) {
        this.heatmap = heatmap;
    }
    
    public void setLinkageTree (BinaryTree linkage_tree, boolean isTreeAvailable) {
        this.linkage_tree = linkage_tree;
        this.isTreeAvailable = isTreeAvailable;
    }
    
    public void setEntrezPosMap (HashMap <String, ArrayList <Integer>> entrezPosMap) {
        this.entrezPosMap = entrezPosMap;
    }
    
    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
    }
    
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    public void setFilterListMap (HashMap <String, ArrayList <Integer>> filterListMap) {
        this.filterListMap = filterListMap;
    }
    
    public void addFilterListMap (String list_name, ArrayList <Integer> list) {
        if (filterListMap == null) {
            filterListMap = new HashMap <String, ArrayList <Integer>> ();
        }
        this.filterListMap.put(list_name, list);
    }
    
    public boolean equalsClusteringParam (HashMap <String, String> thatParam) {
        return (this.clustering_params.get("do_clustering").equals(thatParam.get("do_clustering")) &&
                this.clustering_params.get("linkage").equals(thatParam.get("linkage")) &&
                this.clustering_params.get("distance_func").equals(thatParam.get("distance_func")));
    }
    
    public AnalysisContainer createSubAnalysis(String sub_analysis_name, 
            ArrayList <Integer> filtered_entrez_ids, String installPath, String session_id) 
    throws DataParsingException {
        
        AnalysisContainer sub_analysis = new AnalysisContainer();
        sub_analysis.setAnalysisName(sub_analysis_name);
        
        sub_analysis.setBasePath(SessionManager.getBasePath(installPath, session_id, sub_analysis.analysis_name));
        SessionManager.createAnalysisDirs(sub_analysis);
        
        sub_analysis.setSearcher(new Searcher(this.database.species));
        try {
            sub_analysis.setLogger(new Logger(sub_analysis.base_path, "server.log"));
        } catch (Exception e) {
            System.out.println("Could not create logger for sub_analysis.base_path. Going in without logging enabled.");
        }
        
        sub_analysis.isMasterAnalysis = false;
        sub_analysis.setDatabase(database.cloneDB(filtered_entrez_ids));
        
        // initialize following variables as in init.jsp
        HashMap <String, String> clustering_params = new HashMap<String, String>();
        clustering_params.put("linkage", "");
        clustering_params.put("distance_func", "");
        clustering_params.put("do_clustering", "false");
        clustering_params.put("use_cached", "false");
        sub_analysis.setClusteringParams(clustering_params);

        HashMap <String, String> visualization_params = new HashMap<String, String>();
        visualization_params.put("leaf_ordering_strategy", "0");   // largest cluster first
        visualization_params.put("heatmap_color_scheme", "blue_red");
        visualization_params.put("nBins", "11");
        visualization_params.put("bin_range_type", "data_bins");
        visualization_params.put("bin_range_start", "-1");
        visualization_params.put("bin_range_end", "-1");
        sub_analysis.setVisualizationParams(visualization_params);
        
        /*
        state_variables = new HashMap <String, Double> ();
        state_variables.put("detailed_view_start", 0.0);
        state_variables.put("detailed_view_end", 37.0);
        sub_analysis.setStateVariables(state_variables);
        */
        
        ArrayList<ArrayList<CompactSearchResultContainer>> search_results
                                    = new ArrayList<ArrayList<CompactSearchResultContainer>>();

        ArrayList<String> search_strings = new ArrayList<String>();

        sub_analysis.setSearchResults(search_results);
        sub_analysis.setSearchStrings(search_strings);

        HashMap <String, ArrayList <Integer>> filterListMap = new HashMap <String, ArrayList <Integer>>();
        //ArrayList <Integer> blanks = new ArrayList <Integer>();
        //filterListMap.put("Detailed View", blanks);
        sub_analysis.setFilterListMap(filterListMap);
        
        return sub_analysis;
    }
    
    public AnalysisContainer createSubAnalysis(String enrichment_analysis_name, 
            EnrichmentAnalysis ea, String installPath, String session_id) {
        
        AnalysisContainer sub_analysis = new AnalysisContainer();
        sub_analysis.setAnalysisName(enrichment_analysis_name);
        
        sub_analysis.setBasePath(SessionManager.getBasePath(installPath, session_id, sub_analysis.analysis_name));
        SessionManager.createAnalysisDirs(sub_analysis);
        
        sub_analysis.setSearcher(new Searcher(this.database.species));
        try {
            sub_analysis.setLogger(new Logger(sub_analysis.base_path, "server.log"));
        } catch (Exception e) {
            System.out.println("Could not create logger for " + sub_analysis.base_path + ". Going in without logging enabled.");
        }
        
        sub_analysis.ea = ea;
        
        sub_analysis.isMasterAnalysis = false;
        if (ea.enrichmentType == EnrichmentAnalysis.TYPE_ONTOLOGY) {
            sub_analysis.visualizationType = AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION;
        } else if (ea.enrichmentType == EnrichmentAnalysis.TYPE_PATHWAY) {
            sub_analysis.visualizationType = AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION;
        }
        sub_analysis.visualizationType = AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION;
        sub_analysis.setDatabase(database.cloneDBForEnrichment(ea));
        
        // initialize following variables as in init.jsp
        HashMap <String, String> clustering_params = new HashMap<String, String>();
        clustering_params.put("linkage", "");
        clustering_params.put("distance_func", "");
        clustering_params.put("do_clustering", "false");
        clustering_params.put("use_cached", "false");
        sub_analysis.setClusteringParams(clustering_params);

        HashMap <String, String> visualization_params = new HashMap<String, String>();
        visualization_params.put("leaf_ordering_strategy", "0");   // largest cluster first
        visualization_params.put("heatmap_color_scheme", "blue_red");
        visualization_params.put("nBins", "21");
        //visualization_params.put("bin_range_type", "data_bins");
        //visualization_params.put("bin_range_start", "-1");
        //visualization_params.put("bin_range_end", "1");
        visualization_params.put("bin_range_type", "symmetric_bins");
        visualization_params.put("bin_range_start", (-sub_analysis.database.DATA_MIN_MAX[1])+"");
        visualization_params.put("bin_range_end", sub_analysis.database.DATA_MIN_MAX[1]+"");
        sub_analysis.setVisualizationParams(visualization_params);
        
        HashMap <String, Double> enrichment_params = new HashMap <String, Double> ();
        enrichment_params.put("significance_level", 0.05);
        enrichment_params.put("small_k", 4.0);
        enrichment_params.put("big_K", 5.0);
        sub_analysis.setEnrichmentParams(enrichment_params);
        
        ArrayList<ArrayList<CompactSearchResultContainer>> search_results
                                    = new ArrayList<ArrayList<CompactSearchResultContainer>>();

        ArrayList<String> search_strings = new ArrayList<String>();

        sub_analysis.setSearchResults(search_results);
        sub_analysis.setSearchStrings(search_strings);

        HashMap <String, ArrayList <Integer>> filterListMap = new HashMap <String, ArrayList <Integer>>();
        //ArrayList <Integer> blanks = new ArrayList <Integer>();
        //filterListMap.put("Detailed View", blanks);
        sub_analysis.setFilterListMap(filterListMap);
        
        return sub_analysis;
        
    }
    
    // used only for testing, delete for production
    public void saveFeatures_1() {
            
        try (ObjectOutputStream oos
                = new ObjectOutputStream(new FileOutputStream("D:\\ad\\sg\\16_June_2017\\features.obj"))) {

            oos.writeObject(database.features);
            System.out.println("Features saved.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
