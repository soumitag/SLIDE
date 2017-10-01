/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Arrays;
import java.util.List;


import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import java.util.ArrayList;



import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
/**
 *
 * @author soumitag
 */
public class MongoDBConnect {
    
    private MongoClient mongoClient;
    private DB db;
    //private DBCollection geneMap;
    private DBCollection aliasEntrezMap;
    private DBCollection entrezAliasMap;
    private String species;
    private String collectionType;
    
    public boolean isConnected;
    
    public MongoDBConnect(String species, String collectionType){
        this.species = species;
        this.collectionType = collectionType;
        connectToMongoDB();
    }
    
    public final void connectToMongoDB () {
        
        try {
            
            mongoClient = new MongoClient (new ServerAddress("localhost" , 27017), Arrays.asList());
            
            if(species.equals("human")){            // human
            // Connect to databases
                db = mongoClient.getDB("geneVocab_HomoSapiens"); 
                if(collectionType.equals("geneMap")){
                    aliasEntrezMap = db.getCollection("HS_aliasEntrezMap");
                    entrezAliasMap = db.getCollection("HS_entrezAliasMap");
                }
                isConnected = true;
                
            } else if (species.equals("mouse")){    // mouse 
                db = mongoClient.getDB("geneVocab_MusMusculus");
                if(collectionType.equals("geneMap")){
                    aliasEntrezMap = db.getCollection("MM_aliasEntrezMap");
                    entrezAliasMap = db.getCollection("MM_entrezAliasMap");
                }
                isConnected = true;
            }
            
        } catch(Exception e){
            
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            isConnected = false;
            
        }
    }
    
    public ArrayList<String> getGeneSymbol(String entrez_id){
        
        //String gSymbol = null;
        ArrayList <String> geneSymbols;
        BasicDBObject query = new BasicDBObject("_id", entrez_id);
        geneSymbols = new ArrayList<String>();
        try (DBCursor cursor = entrezAliasMap.find(query)) {
            while (cursor.hasNext()) {
                DBObject match = (DBObject)cursor.next();
                //gSymbol = match.get("genesymbol").toString().toLowerCase(); 
                List <BasicDBObject> aliases = (List <BasicDBObject>) match.get("aliases");  
                
                for (int i=0; i<aliases.size(); i++) {
                    //System.out.println(aliases.get(i).get("alias"));
                    geneSymbols.add((String)aliases.get(i).get("alias"));
                }
                
            }
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return geneSymbols;
        
    }
    
    
    public String getEntrezID(String genesymbol){
        
        String eid = null;
        ArrayList <String> entrez_IDs = new ArrayList<String>();
        BasicDBObject query = new BasicDBObject ("_id", genesymbol);
        try (DBCursor cursor = aliasEntrezMap.find(query)){
            while(cursor.hasNext()){
                DBObject match = (DBObject)cursor.next();
                //eid = match.get("_id").toString().toLowerCase();
                List <BasicDBObject> eids = (List <BasicDBObject>) match.get("entrez_ids");  
                
                for (int i=0; i<eids.size(); i++) {
                    //System.out.println(eids.get(i).get("entrez"));
                    entrez_IDs.add((String)eids.get(i).get("entrez"));
                }
                
                eid = entrez_IDs.get(0);
            }  
            
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        //eid = entrez_IDs.get(0);
        return eid;
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
    
}
