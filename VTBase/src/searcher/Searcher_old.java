/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.mongodb.ServerAddress;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 * @author SOUMITA GHOSH
 */
public class Searcher_old {
    
    private MongoClient mongoClient;
    private DB db;
    private DBCollection geneMap;
    private DBCollection goMap;
    private DBCollection pathwayMap;
    
    public boolean isConnected;
    
    public final int GO_ID_SEARCH = 0;
    public final int ENTREZ_ID_SEARCH = 1;
    public final int GENE_SYMBOL_SEARCH = 2;

    
    public Searcher_old () {
        connectToMongoDB();
    }
    public final void connectToMongoDB () {
        
        try {
            
            mongoClient = new MongoClient (new ServerAddress("localhost" , 27017), Arrays.asList());
            
            // Connect to databases
            db = mongoClient.getDB("geneVocab_HomoSapiens");
            geneMap = db.getCollection("HS_geneMap2");
            goMap = db.getCollection("HS_goMap2");
            pathwayMap = db.getCollection("HS_pathwayMap");
            
            isConnected = true;
            
        } catch(Exception e){
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
            
        }
    }
    
    public void closeMongoDBConnection () {
        
        try {
            
            mongoClient.close();
            isConnected = false;
            
        } catch(Exception e){
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
            
        }
        
    }
    
    public ArrayList <String> processGeneQuery (String entrezid) {
        return null;
    }
    
    public ArrayList <ArrayList<GeneObject>> processingSingleGeneQueryStr (
            String queryString,
            String queryType) {
        String queryStr = queryString.trim().toLowerCase();
        StringTokenizer st = new StringTokenizer(queryStr, ",");
        
        ArrayList <ArrayList<GeneObject>> fullgeneSet = new ArrayList <> (); 
                
        while (st.hasMoreElements()){
            ArrayList <GeneObject> geneset = processSingleGeneQuery(st.nextToken().trim().toLowerCase(), queryType);
            fullgeneSet.add(geneset);
            
        }
        
        return fullgeneSet;
    }
    
    public ArrayList <GeneObject> processSingleGeneQuery (
            String queryString, 
            String queryType
    ) {
        
        // Takes as input a single genesymbol or entrez id as query
        // The input genesymbol or entrez id can be the exact term
        // in which case a single match is expected
        // The input genesymbol or entrez id can also contain wildcards like *
        // in which case multiple matches may be found
        
        // Returns a list of GeneGoMap objects
        // A GeneGoMap object is a pair of GeneInfo and GoObject objects
        
        String querystr = queryString.trim().toLowerCase();        
        ArrayList <GeneObject> geneSet = new ArrayList <> ();      
        
        try {

            BasicDBObject query = null;           
            
                if (queryType.equals("genesymbol")) {
                    query = new BasicDBObject("genesymbol", querystr.trim().toLowerCase());
                } else if (queryType.equals("entrez")) {
                    query = new BasicDBObject("_id", querystr.trim().toLowerCase());
                }
            
            
                DBCursor cursor = geneMap.find(query);
                try {

                    while (cursor.hasNext()) {
                        DBObject match = (DBObject)cursor.next();
                        System.out.println(match);
                        GeneObject gene = new GeneObject (match);
                        geneSet.add(gene);
                    }
                } finally {
                    cursor.close();
                }
                   
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return geneSet;
        
    }
    
    public ArrayList <GoObject> processGoIDQuery (String goid) {
        return processSingleGoIDQuery(goid.substring(2));
    }
    
    public ArrayList <GoObject> processSingleGoIDQuery (String goid) {
        
        // Assumes Go IDs are unique and only a single match will be found
        // for the input Go ID
        // Input Go ID is without the "GO" prefix
        // handle wild card
        // handle evidence and ontology
        
        ArrayList <GoObject> goObjs = new ArrayList <> ();
        
        try {
            
            BasicDBObject query = new BasicDBObject("go", goid);
            DBCursor cursor = goMap.find(query);
            try {
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    GoObject goObj = new GoObject(match);
                    goObjs.add(goObj);
                }
            } finally {
                cursor.close();
            }
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return goObjs;
    }
    
    public ArrayList <PathwayObject> processSinglePathwayQuery (
            String queryString,
            String queryType
    ) {
        
        // Takes as input a single genesymbol or entrez id as query
        // The input genesymbol or entrez id can be the exact term
        // in which case a single match is expected
        // The input genesymbol or entrez id can also contain wildcards like *
        // in which case multiple matches may be found
        
        // Returns a list of GeneGoMap objects
        // A GeneGoMap object is a pair of GeneInfo and GoObject objects
        
        ArrayList <PathwayObject> paths = new ArrayList <> ();
        
        try {

            BasicDBObject query = null;
            
            if (queryType.equals("pathname")) {
                query = new BasicDBObject("pathway", queryString);
            } else if (queryType.equals("pathid")) {
                query = new BasicDBObject("_id", queryString);
            }
            
            DBCursor cursor = pathwayMap.find(query);
            try {
                
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    PathwayObject path = new PathwayObject (match);
                    paths.add(path);
                }
            } finally {
                cursor.close();
            }
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return paths;
        
    }
    
    public ArrayList <PathwayObject> processWildCardPathwayQuery (
            String queryString,
            String queryType
    ) {
        
        // Takes as input a single genesymbol or entrez id as query
        // The input genesymbol or entrez id can be the exact term
        // in which case a single match is expected
        // The input genesymbol or entrez id can also contain wildcards like *
        // in which case multiple matches may be found
        
        // Returns a list of GeneGoMap objects
        // A GeneGoMap object is a pair of GeneInfo and GoObject objects
        
        ArrayList <PathwayObject> paths = new ArrayList <> ();
        
        try {

            BasicDBObject query = null;
            
            if (queryType.equals("pathname")) {
                query = new BasicDBObject("pathway", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            } else if (queryType.equals("pathid")) {
                query = new BasicDBObject("_id", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            }
            
            
            //String pattern = "*" + query + "*";
            
            //query.put("pathway", java.util.regex.Pattern.compile(pattern));
            
            DBCursor cursor = pathwayMap.find(query);
            
            try {
                
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    System.out.println(match);
                    PathwayObject path = new PathwayObject (match);
                    paths.add(path);
                }
            } finally {
                cursor.close();
            }
            
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return paths;
        
    }
    
    
    
    
    public GeneObject mapEntrezSymbol (String queryStr, boolean isEntrezSearch) {
        
        // Assumes the input term is an exact Entrez ID or Genesymbol
        // isEntrezSearch is TRUE if the input queryStr is an Entrez ID
        // isEntrezSearch is FALSE if the input queryStr is a Genesymbol
        // Returns a GeneInfo object
        // handle wild cards
        
        
        String result = null;
        GeneObject gene = null;
        try {
            BasicDBObject query;
            String entrez_id = null;
            String genesymbol = null;
            if (isEntrezSearch) {
                query = new BasicDBObject("_id", queryStr);
                entrez_id = queryStr;
            } else {
                query = new BasicDBObject("genesymbol", queryStr);
                genesymbol = queryStr;
            }
            DBCursor cursor = geneMap.find(query);
            if (cursor.hasNext()) {
                DBObject match = (DBObject)cursor.next();
                if (isEntrezSearch) {
                    genesymbol = match.get("genesymbol").toString();
                } else {
                    entrez_id = match.get("_id").toString();
                }
            }
            gene = new GeneObject (entrez_id, genesymbol);
        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return gene;
    }
    
    /*public GoObject mapGOIDTerms (String GOID) {
        return new GoObject (GOID,"BondGene","The coolness gene");
    }*/
    
}
