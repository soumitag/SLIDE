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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import structure.Feature;

/**
 *
 * @author soumitag
 */
public class EnrichmentAnalysis implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int TYPE_PATHWAY = 0;
    public static final int TYPE_ONTOLOGY = 1;
    
    // persistent data
    public HashMap <String, String[]> funcgrp_details_map;     // map from 0022967_bp -> [0022967, bp, immune system process]
    
    // parameters (persistent data)
    public int MIN_BIG_K = 5;
    public int MIN_SMALL_k = 4;
    public double SIGNIFICANCE_LEVEL = 0.05;
    public int enrichmentType;
    public ArrayList <String> ontologies = null;              // Only used for GO
    
    // non-persistent data (should be cleared when done)
    private ArrayList <String> entrez_master;
    private HashMap <String, ArrayList <String>> entrez_featlist_maps;
    
    // all connections should be closed when done by calling closeDBconn()
    private MongoClient mongoClient;
    private DB db;
    private String species;
    private DBCollection goMap2;
    private DBCollection geneMap2;
    private DBCollection aliasEntrezMap;
    private DBCollection pathwayMap;
    public boolean isConnected;

    public HypergeomParameterContainer testParams;
    
    public EnrichmentAnalysis (ArrayList <Feature> features, 
            HashMap <String, ArrayList <Integer>> featurelist_eid_maps, 
            String species, String enrichmentType, 
            int min_big_K, int min_small_k, double significance_level, 
            String[] ontologies) {
        
        this.testParams = new HypergeomParameterContainer(features.size());
        
        this.MIN_BIG_K = min_big_K;
        this.MIN_SMALL_k = min_small_k;
        this.SIGNIFICANCE_LEVEL = significance_level;
        
        this.species = species;
        this.ontologies = new ArrayList <String> ();
        if (enrichmentType.equals("go")) {
            this.enrichmentType = EnrichmentAnalysis.TYPE_ONTOLOGY;
            if (ontologies != null) {
                String temp = Arrays.toString(ontologies);
                this.ontologies.addAll(Arrays.asList(temp.toUpperCase().split(",")));
            }
        } else if (enrichmentType.equals("pathway")) {
            this.enrichmentType = EnrichmentAnalysis.TYPE_PATHWAY;
        }
        
        connectDB();
        
        funcgrp_details_map = new HashMap <String, String[]> ();
        
        // maintain a master list of entrez ids
        this.entrez_master = new ArrayList <String> ();
        
        // add feature lists to testParams
        // create a entrez id -> feature list name mapping
        // populate master list of entrez ids
        entrez_featlist_maps = new HashMap <String, ArrayList <String>> ();
        
        featurelist_eid_maps.entrySet().forEach((pair) -> {
            
            String featlist_name = (String)pair.getKey();
            ArrayList <Integer> rownums = (ArrayList <Integer>)pair.getValue();
            
            // add feature lists to testParams
            testParams.addFeatureList(featlist_name, rownums.size());
            
            for (int i = 0; i < rownums.size(); i++) {
                
                String eid = features.get(rownums.get(i)).entrezId;
                System.out.println(i + ": " + eid);
                
                // add entrez-id -> feature-list-name mappings (e.g. 11386 -> [A8,B7]
                if (entrez_featlist_maps.containsKey(eid)) {
                    ArrayList <String> featlist_names = entrez_featlist_maps.get(eid);
                    if (!featlist_names.contains(featlist_name)) {
                        featlist_names.add(featlist_name);
                        entrez_featlist_maps.put(eid, featlist_names);
                    }
                } else {
                    ArrayList <String> featlist_names = new ArrayList <String> ();
                    featlist_names.add(featlist_name);
                    entrez_featlist_maps.put(eid, featlist_names);
                }
                
                // populate master list of entrez ids
                if (!this.entrez_master.contains(eid)) {
                    this.entrez_master.add(eid);
                }
            }
        });
        
    }
    
    public void run () {
        
        compute_K();
        compute_k();
        
        testParams.computeHyperGeom();
        testParams.filterFunctionalGroupList(MIN_BIG_K, MIN_SMALL_k, SIGNIFICANCE_LEVEL);
        
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
        
        // for each entrez id in master list
        for (int i = 0; i < entrez_master.size(); i++) {
            
            // look up db
            BasicDBObject query = new BasicDBObject("_id", entrez_master.get(i));
            DBCursor gene_cursor = geneMap2.find(query);
            
            // for the record in db
            while (gene_cursor.hasNext()) {
                
                DBObject match = (DBObject) gene_cursor.next();
                List <BasicDBObject> go_s = (List<BasicDBObject>) match.get("goids");
                
                for (int x = 0; x < go_s.size(); x++) {
                    
                    String goID = (String) go_s.get(x).get("go");
                    String goTerm = (String) go_s.get(x).get("term");
                    String goOnto = (String) go_s.get(x).get("ontology");
                    
                    if (this.ontologies.contains(goOnto.toUpperCase())) {
                        String goKey = goID + "_" + goOnto;
                        testParams.incrementFunctionalGroupSize(goKey);
                        if (!funcgrp_details_map.containsKey(goKey)) {
                            funcgrp_details_map.put(goKey, new String[]{goID.toUpperCase(), goTerm.toUpperCase(), goOnto.toUpperCase()});
                        }
                    }
                    
                }
            }
        }
    }
    
    public void compute_K_PATHWAY () {
        
         // for each entrez id in master list
        for (int i = 0; i < entrez_master.size(); i++) {
            
            // look up db
            BasicDBObject query = new BasicDBObject("_id", entrez_master.get(i));
            DBCursor gene_cursor = geneMap2.find(query);
            
            while (gene_cursor.hasNext()) {
                
                DBObject match = (DBObject) gene_cursor.next();
                List <BasicDBObject> path_s = (List<BasicDBObject>) match.get("pathways");
                
                for (int x = 0; x < path_s.size(); x++) {
                    
                    String pathID = (String) path_s.get(x).get("external_id");
                    String pathName = (String) path_s.get(x).get("pathway");

                    testParams.incrementFunctionalGroupSize(pathID);
                    funcgrp_details_map.put(pathID, new String[]{pathID.toUpperCase(), pathName.toUpperCase()});

                }
            }
        }
    }
    
    public void compute_k() {
        if (this.enrichmentType == EnrichmentAnalysis.TYPE_ONTOLOGY) {
            compute_k_go ();
        } else if (this.enrichmentType == EnrichmentAnalysis.TYPE_PATHWAY) {
            compute_k_pathway ();
        }
    }
    
    public void compute_k_go() {
        
        // for each entrez id in the master list
        for (int i = 0; i < entrez_master.size(); i++) {
            
            // find the entry in db
            BasicDBObject query = new BasicDBObject("_id", entrez_master.get(i));
            DBCursor gene_cursor = geneMap2.find(query);
            
            // get the feature lists that contain this entrez id, for use later
            ArrayList <String> assoc_featlist_names = entrez_featlist_maps.get(entrez_master.get(i));
            
            while (gene_cursor.hasNext()) {     // this should run only once
                
                DBObject match = (DBObject) gene_cursor.next();
                List<BasicDBObject> go_s = (List<BasicDBObject>) match.get("goids");
                
                // for each go term that this entrez id is associated with
                for (int x = 0; x < go_s.size(); x++) {
                    
                    String goOnto = (String) go_s.get(x).get("ontology");
                    if (this.ontologies.contains(goOnto.toUpperCase())) {
                        String goKey = (String) go_s.get(x).get("go") + "_" + (String) go_s.get(x).get("ontology");
                        
                        for (int j=0; j<assoc_featlist_names.size(); j++) {
                            testParams.incrFuncGrpFeatListAssoc(goKey, assoc_featlist_names.get(j));
                        }
                    }
                    
                }   // end go term loop
                
            }   // end db match if loop
            
        }   // end entrez master loop
        
    }
    
    public void compute_k_pathway() {
        
        // for each entrez id in the master list
        for (int i = 0; i < entrez_master.size(); i++) {
            
            // find the entry in db
            BasicDBObject query = new BasicDBObject("_id", entrez_master.get(i));
            DBCursor gene_cursor = geneMap2.find(query);
            
            // get the feature lists that contain this entrez id, for use later
            ArrayList <String> assoc_featlist_names = entrez_featlist_maps.get(entrez_master.get(i));
            
            while (gene_cursor.hasNext()) {     // this should run only once
                
                DBObject match = (DBObject) gene_cursor.next();
                List<BasicDBObject> path_s = (List<BasicDBObject>) match.get("pathways");
                
                // for each path id that this entrez id is associated with
                for (int x = 0; x < path_s.size(); x++) {

                    String pathKey = (String) path_s.get(x).get("external_id");

                    for (int j=0; j<assoc_featlist_names.size(); j++) {
                        testParams.incrFuncGrpFeatListAssoc(pathKey, assoc_featlist_names.get(j));
                    }
                    
                }
            }
        }
    }
    
    
    
    // Connect to databases
    public void connectDB(){
        try {
            mongoClient = new MongoClient (new ServerAddress("localhost" , 27017), Arrays.asList());
            
            if(species.equals("human")){            // human
            
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
    
    public void closeDBconn(){
        try {
            
            mongoClient.close();
            isConnected = false;
            
        } catch(Exception e){
            
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
            
        }
    }

}
