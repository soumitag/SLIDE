/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.enrichment;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.math3.distribution.HypergeometricDistribution;
import structure.Feature;

/**
 *
 * @author soumitag
 */
public class EnrichmentAnalysis_Old {
    
    public static final int TYPE_PATHWAY = 0;
    public static final int TYPE_ONTOLOGY = 1;
    
    // persistent data
    public int background_list_size_N;
    public int n_Feature_Lists;
    public int n_Functional_Groups;
    public ArrayList <Integer> feature_list_sizes_n;
    public ArrayList <String> feature_list_names;
    public ArrayList <String> functional_group_names;
    public Hashtable <String, String[]> functional_group_names_map;     // map from 0022967_bp -> [0022967, bp, immune system process]
    public boolean[] functional_grouplist_mask;         // used to filter functional groups based on parameters K, k, and significance level
    
    // holds values for all functional groups x all feature lists. 
    // used for filtering when filter values are specified.
    // Persistent Data
    public float[][] p_values;
    public Hashtable <String, Integer> functional_group_bigK_map;   // maps func grp name - > big K; 0022967_bp -> 10
    public Hashtable <String, int[]> functional_group_smallk_map;   // maps func grp name - > array of small ks; 0022967_bp -> [4,5,7]
    
    // parameters (persistent data)
    public int MIN_BIG_K = 5;
    public int MIN_SMALL_k = 4;
    public double SIGNIFICANCE_LEVEL = 0.05;
    public int enrichmentType;
    public String ontology = null;              // Only used for GO
    
    // non-persistent data (should be cleared when done)
    private ArrayList <Feature> features;
    private ArrayList <String> entrez_ids;
    private HashMap <String, ArrayList <Integer>> selected_filter_list_maps;
    
    // all connections should be closed when done by calling closeDBconn()
    private MongoClient mongoClient;
    private DB db;
    private String species;
    private DBCollection goMap2;
    private DBCollection geneMap2;
    private DBCollection aliasEntrezMap;
    private DBCollection pathwayMap;
    public boolean isConnected;

    private HypergeomParameterContainer testParams;
    
    public EnrichmentAnalysis_Old(ArrayList <Feature> features, 
            HashMap <String, ArrayList <Integer>> selected_filter_list_maps, 
            String species, String enrichmentType, 
            int min_big_K, int min_small_k, double significance_level, 
            String ontology) {
        
        this.testParams = new HypergeomParameterContainer(features.size());
        
        this.MIN_BIG_K = min_big_K;
        this.MIN_SMALL_k = min_small_k;
        this.SIGNIFICANCE_LEVEL = significance_level;
        
        this.species = species;
        if (enrichmentType.equals("go")) {
            this.enrichmentType = EnrichmentAnalysis.TYPE_ONTOLOGY;
            if (ontology != null && !ontology.equals("")) {
                this.ontology = ontology;
            }
        } else if (enrichmentType.equals("pathway")) {
            this.enrichmentType = EnrichmentAnalysis.TYPE_PATHWAY;
        }
        
        this.features = features;
        
        Set <String> set = new HashSet <String> ();
        for (int i = 0; i < features.size(); i++) {
            set.add(features.get(i).entrezId);
        }
        this.background_list_size_N = set.size();
        this.entrez_ids = new ArrayList <String> ();
        this.entrez_ids.addAll(set);
        
        this.selected_filter_list_maps = selected_filter_list_maps;
        
        connectDB();
        
        feature_list_names = new ArrayList <String> ();
        feature_list_sizes_n = new ArrayList <Integer> ();
        Iterator it = selected_filter_list_maps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            feature_list_names.add((String)pair.getKey());
            feature_list_sizes_n.add(((ArrayList <Integer>)pair.getValue()).size());
        }
        n_Feature_Lists = feature_list_names.size();
        
    }
    
    public void run () {
        
        // create a master list of selected row numbers from selected_filter_list_maps
        ArrayList <Integer> master_list_of_selected_rownums = new ArrayList <Integer> ();
        Set <Integer> set = new HashSet <Integer> (selected_filter_list_maps.get(feature_list_names.get(0)));
        for (int i=1; i<feature_list_names.size(); i++) {
            set.addAll(selected_filter_list_maps.get(feature_list_names.get(i)));
        }
        master_list_of_selected_rownums.addAll(set);
        
        // create an association matrix for selected row to feature list association
        boolean[][] selected_row_feature_list_association_matrix = new boolean[master_list_of_selected_rownums.size()][n_Feature_Lists];
        for (int i = 0; i < master_list_of_selected_rownums.size(); i++) {
            for (int j=0; j<n_Feature_Lists; j++) {
                if (selected_filter_list_maps.get(feature_list_names.get(j)).contains(master_list_of_selected_rownums.get(i))) {
                    selected_row_feature_list_association_matrix[i][j] = true;
                }
            }
        }
        
        functional_group_bigK_map = new Hashtable <String, Integer> ();
        functional_group_smallk_map = new Hashtable <String, int[]> ();
        functional_group_names_map = new Hashtable <String, String[]> ();
        
        compute_K();
        compute_k(master_list_of_selected_rownums, selected_row_feature_list_association_matrix);
        
        functional_group_names = new ArrayList <String> ();
        Iterator it = functional_group_smallk_map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            functional_group_names.add((String)pair.getKey());
        }
        n_Functional_Groups = functional_group_names.size();
        
        //p_values = new float[n_Functional_Groups][n_Feature_Lists];
        //temp = new double[n_Functional_Groups][n_Feature_Lists];
        computeHyperGeom();
        
        filterFunctionalGroupList(this.MIN_BIG_K, this.MIN_SMALL_k, this.SIGNIFICANCE_LEVEL);
        
        closeDBconn();
    }
    
    public void compute_K () {
        if (this.enrichmentType == EnrichmentAnalysis.TYPE_ONTOLOGY) {
            compute_K_GO ();
        } else if (this.enrichmentType == EnrichmentAnalysis.TYPE_PATHWAY) {
            compute_K_PATHWAY ();
        }
    }
    
    public void compute_K_GO () {
        for (int i = 0; i < entrez_ids.size(); i++) {
            BasicDBObject query = new BasicDBObject("_id", entrez_ids.get(i));
            DBCursor gene_cursor = geneMap2.find(query);
            while (gene_cursor.hasNext()) {
                DBObject match = (DBObject) gene_cursor.next();
                List <BasicDBObject> go_s = (List<BasicDBObject>) match.get("goids");
                for (int x = 0; x < go_s.size(); x++) {
                    String goID = (String) go_s.get(x).get("go");
                    String goTerm = (String) go_s.get(x).get("term");
                    String goOnto = (String) go_s.get(x).get("ontology");
                    if (this.ontology == null || goOnto.equalsIgnoreCase(this.ontology)) {
                        String goKey = goID + "_" + goOnto;
                        if (functional_group_bigK_map.containsKey(goKey)) {
                            Integer K = functional_group_bigK_map.get(goKey);
                            K++;
                            functional_group_bigK_map.put(goKey, K);
                        } else {
                            functional_group_bigK_map.put(goKey, (Integer)1);
                            String[] qualified_functional_names = new String[2];
                            qualified_functional_names[0] = goID.toUpperCase();
                            qualified_functional_names[1] = (goTerm + ", " + goOnto).toUpperCase();
                            functional_group_names_map.put(goKey, qualified_functional_names);
                        }
                    }
                }
            }
        }
    }
    
    public void compute_K_PATHWAY () {
        for (int i = 0; i < entrez_ids.size(); i++) {
            BasicDBObject query = new BasicDBObject("_id", entrez_ids.get(i));
            DBCursor gene_cursor = geneMap2.find(query);
            while (gene_cursor.hasNext()) {
                DBObject match = (DBObject) gene_cursor.next();
                List <BasicDBObject> path_s = (List<BasicDBObject>) match.get("pathways");
                for (int x = 0; x < path_s.size(); x++) {
                    String pathID = (String) path_s.get(x).get("external_id");
                    String pathName = (String) path_s.get(x).get("pathway");

                    if (functional_group_bigK_map.containsKey(pathID)) {
                        Integer K = functional_group_bigK_map.get(pathID);
                        K++;
                        functional_group_bigK_map.put(pathID, K);
                    } else {
                        functional_group_bigK_map.put(pathID, (Integer) 1);
                        String[] qualified_functional_names = new String[2];
                        qualified_functional_names[0] = pathID.toUpperCase();
                        qualified_functional_names[1] = pathName.toUpperCase();
                        functional_group_names_map.put(pathID, qualified_functional_names);
                    }

                }
            }
        }
    }
    
    public void compute_k(ArrayList <Integer> master_list_of_selected_rownums, boolean[][] selected_row_feature_list_association_matrix) {
        if (this.enrichmentType == EnrichmentAnalysis.TYPE_ONTOLOGY) {
            compute_k_go (master_list_of_selected_rownums, selected_row_feature_list_association_matrix);
        } else if (this.enrichmentType == EnrichmentAnalysis.TYPE_PATHWAY) {
            compute_k_pathway (master_list_of_selected_rownums, selected_row_feature_list_association_matrix);
        }
    }
    
    public void compute_k_go(ArrayList <Integer> master_list_of_selected_rownums, boolean[][] selected_row_feature_list_association_matrix) {
        
        // for each entrez id in the master list
        for (int i = 0; i < master_list_of_selected_rownums.size(); i++) {
            // find the entry in db
            BasicDBObject query = new BasicDBObject("_id", features.get(master_list_of_selected_rownums.get(i)).entrezId);
            DBCursor gene_cursor = geneMap2.find(query);
            while (gene_cursor.hasNext()) {     // this should run only once
                DBObject match = (DBObject) gene_cursor.next();
                List<BasicDBObject> go_s = (List<BasicDBObject>) match.get("goids");
                // for each go term that this entrez id is associated with
                for (int x = 0; x < go_s.size(); x++) {
                    String goOnto = (String) go_s.get(x).get("ontology");
                    if (this.ontology == null || goOnto.equalsIgnoreCase(this.ontology)) {
                        String goKey = (String) go_s.get(x).get("go") + "_" + (String) go_s.get(x).get("ontology");
                        
                        if (functional_group_smallk_map.containsKey(goKey)) {
                            int[] k = functional_group_smallk_map.get(goKey);
                            // increment the values of the feature lists that this entrez id is associated with
                            for (int j=0; j<n_Feature_Lists; j++) {
                                if (selected_row_feature_list_association_matrix[i][j]) {
                                    k[j]++;
                                }
                            }
                        } else {
                            // set the values of the feature lists that this entrez id is associated with to 1
                            int[] k = new int[n_Feature_Lists];
                            for (int j=0; j<n_Feature_Lists; j++) {
                                if (selected_row_feature_list_association_matrix[i][j]) {
                                    k[j] = 1;
                                }
                            }
                            functional_group_smallk_map.put(goKey, k);
                        }
                    }
                }
            }
        }
    }
    
    public void compute_k_pathway(ArrayList <Integer> master_list_of_selected_rownums, boolean[][] selected_row_feature_list_association_matrix) {
        
        // for each entrez id in the master list
        for (int i = 0; i < master_list_of_selected_rownums.size(); i++) {
            // find the entry in db
            BasicDBObject query = new BasicDBObject("_id", features.get(master_list_of_selected_rownums.get(i)).entrezId);
            DBCursor gene_cursor = geneMap2.find(query);
            while (gene_cursor.hasNext()) {     // this should run only once
                DBObject match = (DBObject) gene_cursor.next();
                List<BasicDBObject> path_s = (List<BasicDBObject>) match.get("pathways");
                // for each go term that this entrez id is associated with
                for (int x = 0; x < path_s.size(); x++) {

                    String pathKey = (String) path_s.get(x).get("external_id");

                    if (functional_group_smallk_map.containsKey(pathKey)) {
                        int[] k = functional_group_smallk_map.get(pathKey);
                        // increment the values of the feature lists that this entrez id is associated with
                        for (int j = 0; j < n_Feature_Lists; j++) {
                            if (selected_row_feature_list_association_matrix[i][j]) {
                                k[j]++;
                            }
                        }
                    } else {
                        // set the values of the feature lists that this entrez id is associated with to 1
                        int[] k = new int[n_Feature_Lists];
                        for (int j = 0; j < n_Feature_Lists; j++) {
                            if (selected_row_feature_list_association_matrix[i][j]) {
                                k[j] = 1;
                            }
                        }
                        functional_group_smallk_map.put(pathKey, k);
                    }

                }
            }
        }
    }
    
    public void computeHyperGeom() {

        p_values = new float[n_Functional_Groups][n_Feature_Lists];
        
        for (int i = 0; i < n_Functional_Groups; i++) {
            
            int K = functional_group_bigK_map.get(functional_group_names.get(i));
            int[] k = functional_group_smallk_map.get(functional_group_names.get(i));
            
            for (int j = 0; j < n_Feature_Lists; j++) {

                HypergeometricDistribution hypergeom = new HypergeometricDistribution(
                        background_list_size_N, K, feature_list_sizes_n.get(j));
                
                double p_value = 1 - hypergeom.cumulativeProbability(k[j]);
                
                if (p_value <= Float.MIN_NORMAL) {
                    p_value = Float.MIN_NORMAL;
                }
                
                if (Double.isNaN(p_value) || Double.isInfinite(p_value)) {
                    p_value = 0.99;
                }

                System.out.println("P: " + Math.log10(p_value));
                p_values[i][j] = (float) -Math.log10(p_value);

            }
        }
    }
    
    public void computeHyperGeom_w_Filtering() {
        
        ArrayList <String> selected_functional_group_names = new ArrayList <String> ();
        ArrayList <float[]> selected_p_values = new ArrayList <float[]> ();
        
        //String str = "";
        boolean keep_row_indicator = false;
        for (int i=0; i<n_Functional_Groups; i++) {
            int K = functional_group_bigK_map.get(functional_group_names.get(i));
            if (K > MIN_BIG_K) {
                int[] k = functional_group_smallk_map.get(functional_group_names.get(i));
                keep_row_indicator = false;
                float[] p_values_i = new float[n_Feature_Lists];
                for (int j=0; j<n_Feature_Lists; j++) {
                    if (k[j] > MIN_SMALL_k) {
                        HypergeometricDistribution hypergeom = 
                                new HypergeometricDistribution(background_list_size_N, K, feature_list_sizes_n.get(j));
                        double p_value = 1 - hypergeom.cumulativeProbability(k[j]);
                        if (p_value <= Float.MIN_NORMAL) {
                            p_value = Float.MIN_NORMAL;
                        }
                        if (Double.isNaN(p_value) || Double.isInfinite(p_value)) {
                            p_value = 0.99;
                        }
                        if (p_value <= SIGNIFICANCE_LEVEL) {
                            keep_row_indicator = true;
                        }
                        p_values_i[j] = (float)-Math.log10(p_value);

                    }
                }
                if (keep_row_indicator) {
                    selected_p_values.add(p_values_i);
                    selected_functional_group_names.add(functional_group_names.get(i));
                    /*
                    for (int j=0; j<n_Feature_Lists; j++) {
                        str = str + ", " + p_values_i[j];
                    }
                    str = str + ", " + functional_group_names.get(i);
                    str = str + "\n";
                    */
                }
            }
        }
        
        p_values = new float[selected_p_values.size()][n_Feature_Lists];
        for (int i=0; i<selected_p_values.size(); i++) {
            p_values[i] = selected_p_values.get(i);
        }
        functional_group_names = selected_functional_group_names;
        
        //utils.Utils.saveText(str, "D:\\ad\\sg\\16_June_2017\\p_values.txt");
    }
    
    public void filterFunctionalGroupList(int min_big_K, int min_small_k, double significance_level) {
        
        this.MIN_BIG_K = min_big_K;
        this.MIN_SMALL_k = min_small_k;
        this.SIGNIFICANCE_LEVEL = significance_level;
        
        // generate mask
        this.functional_grouplist_mask = new boolean[n_Functional_Groups];
        
        for (int i=0; i<n_Functional_Groups; i++) {
            int K = functional_group_bigK_map.get(functional_group_names.get(i));
            if (K > MIN_BIG_K) {
                int[] k = functional_group_smallk_map.get(functional_group_names.get(i));
                for (int j=0; j<n_Feature_Lists; j++) {
                    if (k[j] > MIN_SMALL_k) {
                        if (p_values[i][j] >= -Math.log10(SIGNIFICANCE_LEVEL)) {
                            functional_grouplist_mask[i] = true;
                        }
                    }
                }
            }
        }

    }
    
    /*
    public EnrichmentAnalysis(int bgListNum, String[] deList, String species, String listType, String enrichmentType){
        this.species = species;
        this.bgListNum = bgListNum;
        this.enrichmentType = enrichmentType;
        go_kKMap = new Hashtable <String, int[]> ();
        connectDB();
        if (listType.equals("genesymbol")) {
            
            this.deList = convertSymtoEntrez(deList);
            
        } else if (listType.equals("entrez")) {
            this.deList = deList;
        }
    }
    */
    
    public final String[] convertSymtoEntrez(String[] deList){
        String[] entrezList = new String[deList.length];
        for(int i = 0; i < deList.length; i++){
            BasicDBObject query = new BasicDBObject ("_id", deList[i].trim().toLowerCase());
            DBCursor alias_cursor = aliasEntrezMap.find(query);
            while(alias_cursor.hasNext()){
                DBObject match = (DBObject) alias_cursor.next();
                List<BasicDBObject> entrezs = (List<BasicDBObject>) match.get("entrez_ids");
                entrezList[i] = (String)entrezs.get(0).get("entrez");
            }
            
        }
        
        return entrezList;
    }
    
    public void connectDB(){
        try {
            mongoClient = new MongoClient (new ServerAddress("localhost" , 27017), Arrays.asList());
            
            if(species.equals("human")){            // human
            // Connect to databases
                db = mongoClient.getDB("geneVocab_HomoSapiens"); 
                goMap2 = db.getCollection("HS_goMap2");
                geneMap2 = db.getCollection("HS_geneMap2");
                aliasEntrezMap = db.getCollection("HS_aliasEntrezMap");
                pathwayMap = db.getCollection("HS_pathwayMap");
                
                isConnected = true;
                
            } else if (species.equals("mouse")){    // mouse 
                db = mongoClient.getDB("geneVocab_MusMusculus");
                goMap2 = db.getCollection("MM_goMap2");
                geneMap2 = db.getCollection("MM_geneMap2");
                aliasEntrezMap = db.getCollection("MM_aliasEntrezMap");
                pathwayMap = db.getCollection("MM_pathwayMap");
                
                isConnected = true;
            }
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
        }
        
    }
    
    /*
    public void compute_K1(){
        if (enrichmentType.equals("go")) {

            DBCursor cursor = goMap2.find();
            while (cursor.hasNext()) {
                DBObject match = (DBObject) cursor.next();
                List<BasicDBObject> genes = (List<BasicDBObject>) match.get("genes");
                int[] kK = new int[2];
                kK[1] = genes.size();
                this.go_kKMap.put((String) match.get("_id"), kK);
            }
        } else if (enrichmentType.equals("pathway")){
            
            DBCursor cursor = pathwayMap.find();
            while (cursor.hasNext()) {
                DBObject match = (DBObject) cursor.next();
                List<BasicDBObject> genes = (List<BasicDBObject>) match.get("genes");
                int[] kK = new int[2];
                kK[1] = genes.size();
                this.go_kKMap.put((String) match.get("_id"), kK);
            }
        }
    }
    
    public void compute_k(){
        if (enrichmentType.equals("go")) {

            for (int i = 0; i < deList.length; i++) {
                BasicDBObject query = new BasicDBObject("_id", deList[i]);
                DBCursor gene_cursor = geneMap2.find(query);
                while (gene_cursor.hasNext()) {
                    DBObject match = (DBObject) gene_cursor.next();
                    List<BasicDBObject> go_s = (List<BasicDBObject>) match.get("goids");
                    for (int x = 0; x < go_s.size(); x++) {
                        String goKey = (String) go_s.get(x).get("go") + "_" + (String) go_s.get(x).get("ontology");
                        int[] kK = go_kKMap.get(goKey);
                        kK[0]++;
                    }
                }
            }
            
        } else if (enrichmentType.equals("pathway")){
            
            for (int i = 0; i < deList.length; i++) {
                BasicDBObject query = new BasicDBObject("_id", deList[i]);
                DBCursor gene_cursor = geneMap2.find(query);
                while (gene_cursor.hasNext()) {
                    DBObject match = (DBObject) gene_cursor.next();
                    List<BasicDBObject> paths = (List<BasicDBObject>) match.get("pathways");
                    for (int x = 0; x < paths.size(); x++) {
                        //String pathwayKey = (String) go_s.get(x).get("go") + "_" + (String) go_s.get(x).get("ontology");
                        String pathwayKey = (String) paths.get(x).get("external_id");
                        int[] kK = go_kKMap.get(pathwayKey);
                        kK[0]++;
                    }

                }
            }
            
        }
    }
    
    public void computeHyperGeom(){
        Hashtable <String, Double> goEnriched = new Hashtable <String, Double>();
        Set<String> keys = go_kKMap.keySet();
        for(String key: keys){
            int[] kK = go_kKMap.get(key);
            if(kK[0] > 0){
                HypergeometricDistribution hypergeom = new HypergeometricDistribution(bgListNum, kK[1], deList.length);
                double p_val = hypergeom.cumulativeProbability(kK[0]);
                goEnriched.put(key, p_val);
            }
        }
    }
    */
    
    
    public void closeDBconn(){
        try {
            
            mongoClient.close();
            isConnected = false;
            
        } catch(Exception e){
            
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
            
        }
    }
    
    /*
    public void saveEnrichmentOutput () {
        String str = "";

        for (int i=0; i<n_Functional_Groups; i++) {
            if (functional_grouplist_mask[i]) {
                for (int j = 0; j < n_Feature_Lists; j++) {
                    str = str + ", " + p_values[i][j];
                }
                str = str + ", " + functional_group_names.get(i);
                str = str + "\n";
            }
        }

        utils.Utils.saveText(str, "D:\\ad\\sg\\16_June_2017\\p_values.txt");
    }
    */
}
