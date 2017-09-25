/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

/**
 *
 * @author Soumita
 */

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
import java.util.HashMap;
import java.util.StringTokenizer;

    
public class Searcher_1 {    
    
    private MongoClient mongoClient;
    private DB db;
    //private DBCollection geneMap;
    private DBCollection aliasEntrezMap;
    private DBCollection entrezAliasMap;
    private DBCollection geneMap2;
    private DBCollection goMap2;
    private DBCollection pathwayMap;
    private String species;
    public boolean isConnected;
    
    public Searcher_1(String species){
        this.species = species;
        connectToMongoDB();
    }
    
    public final void connectToMongoDB () {        
        try {
            
            mongoClient = new MongoClient (new ServerAddress("localhost" , 27017), Arrays.asList());
            
            if(species.equals("human")){            // human
            // Connect to databases
                db = mongoClient.getDB("geneVocab_HomoSapiens"); 
                
                aliasEntrezMap = db.getCollection("HS_aliasEntrezMap");
                entrezAliasMap = db.getCollection("HS_entrezAliasMap");
                geneMap2 = db.getCollection("HS_geneMap2");
                goMap2 = db.getCollection("HS_goMap2");
                pathwayMap = db.getCollection("HS_pathwayMap");
                                
                isConnected = true;
                
            } else if (species.equals("mouse")){    // mouse 
                db = mongoClient.getDB("geneVocab_MusMusculus");
                
                aliasEntrezMap = db.getCollection("MM_aliasEntrezMap");
                entrezAliasMap = db.getCollection("MM_entrezAliasMap");
                geneMap2 = db.getCollection("MM_geneMap2");
                goMap2 = db.getCollection("MM_goMap2");
                pathwayMap = db.getCollection("MM_pathwayMap");
                
                isConnected = true;
            }
            
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
    
    public ArrayList <ArrayList<String>> processRequest (String queryString, String queryType, String searchType) {
        StringTokenizer st = new StringTokenizer(queryString, ",");
        ArrayList <ArrayList<String>> fullList = new ArrayList <ArrayList<String>> ();
        int count = 0;
        while(st.hasMoreTokens()) {
            ArrayList <ArrayList<String>> part_lists = processSingleRequest(st.nextToken(), queryType, searchType);
            
            for(int i = 0; i < part_lists.size(); i++){
                if (count == 0){
                    fullList.add(part_lists.get(i));
                } else {
                    fullList.get(i).addAll(part_lists.get(i));
                }
            }
            count++;
        }
        return fullList;
    }
    
    public ArrayList <ArrayList<String>> processSingleRequest(String queryString, String queryType, String searchType){
        switch(queryType){
            case "entrez": 
                return processGeneQuery(queryString, searchType, "entrez");
            case "genesymbol":
                return processGeneQuery(queryString, searchType, "genesymbol");
            case "pathname":
                return processPathQuery(queryString, searchType, "pathname");
            case "pathid":
                return processPathQuery(queryString, searchType, "pathid");
            case "goid":
                return processGOQuery(queryString, searchType, "goid");
            case "goterm":
                return processGOQuery(queryString, searchType, "goterm");
            default:
                return null;
        }
    }
    
    public ArrayList <ArrayList<String>> processGeneQuery (
            String queryString,
            String searchType,
            String queryType
    ) {
        
        ArrayList <String> entrez_ids = new ArrayList <> ();
        ArrayList <String> gene_symbols = new ArrayList <> ();
        ArrayList <ArrayList<String>> entrezList = new ArrayList <ArrayList<String>>();
        DBCursor cursor = null;
        
        try {

            BasicDBObject query = null;            
            
            if (searchType.equals("exact")) {
                query = new BasicDBObject("_id", queryString.trim().toLowerCase());
            } else if (searchType.equals("contains")) {
                query = new BasicDBObject("_id", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            }

            if (queryType.equals("entrez")) {
                cursor = entrezAliasMap.find(query);
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    entrez_ids.add((String)match.get("_id"));
                    List <BasicDBObject> aliases = (List <BasicDBObject>) match.get("aliases");
                    String aliases_str = "";
                    for (int a = 0; a < aliases.size()- 1; a++) {
                        aliases_str += (String) aliases.get(a).get("alias") + " , ";
                    }
                    aliases_str += (String) aliases.get(aliases.size()- 1).get("alias");
                    gene_symbols.add(aliases_str); 
                }
            } else if (queryType.equals("genesymbol")) {
                cursor = aliasEntrezMap.find(query);
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    List <BasicDBObject> entrezs = (List <BasicDBObject>) match.get("entrez_ids");  
                    
                    for(int i = 0; i < entrezs.size(); i++ ){
                        gene_symbols.add((String)match.get("_id")); 
                        entrez_ids.add((String)match.get("entrez")); 
                    }
                }
            }
                             
            
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        entrezList.add(entrez_ids);
        entrezList.add(gene_symbols);
        return entrezList;
        
    }
    
    public ArrayList <ArrayList<String>> processPathQuery (
            String queryString,
            String searchType,
            String queryType
    ) {
        ArrayList <String> paths = new ArrayList <String> ();
        ArrayList <String> pathids = new ArrayList <String> ();
        ArrayList <String> entrez_ids = new ArrayList <String> ();
        ArrayList <ArrayList<String>> entrezPathList = new ArrayList <ArrayList<String>>();
        DBCursor cursor = null;
        
        try {

            BasicDBObject query = null;            
            
            if (searchType.equals("exact") && queryType.equals("pathid")) {
                query = new BasicDBObject("_id", queryString.trim().toLowerCase());
            } else if (searchType.equals("contains") && queryType.equals("pathid")) {
                query = new BasicDBObject("_id", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            } else if (searchType.equals("exact") && queryType.equals("pathname")){
                query = new BasicDBObject("pathway", queryString.trim().toLowerCase());
            } else if (searchType.equals("contains") && queryType.equals("pathname")) {
                query = new BasicDBObject("pathway", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            }

            if (queryType.equals("pathname")) {
                cursor = pathwayMap.find(query);
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    paths.add((String)match.get("pathway")); 
                    pathids.add((String)match.get("_id"));
                    
                    List <BasicDBObject> genes = (List <BasicDBObject>) match.get("genes");
                    for (int i = 0; i < genes.size(); i++){
                        entrez_ids.add((String)genes.get(i).get("entrez"));
                    } 
                }
            } else if (queryType.equals("pathid")) {
                cursor = pathwayMap.find(query);
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    paths.add((String)match.get("pathway")); 
                    pathids.add((String)match.get("_id"));
                    
                    //paths.add((String)match.get("_id")); 
                    
                    List <BasicDBObject> genes = (List <BasicDBObject>) match.get("genes");
                    for (int i = 0; i < genes.size(); i++){
                        entrez_ids.add((String)genes.get(i).get("entrez"));
                    } 
                }
            }
                             
            
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        entrezPathList.add(entrez_ids);
        entrezPathList.add(paths);
        entrezPathList.add(pathids);
        
        return entrezPathList;
    }
    
    public ArrayList <ArrayList<String>> processGOQuery (
            String queryString,
            String searchType,
            String queryType
    ) {
        ArrayList <String> go_s = new ArrayList <String> ();
        ArrayList <String> go_ids = new ArrayList <String> ();
        ArrayList <String> entrez_ids = new ArrayList <String> ();
        ArrayList <ArrayList<String>> entrezGOList = new ArrayList <ArrayList<String>>();
        DBCursor cursor = null;
        
        try {

            BasicDBObject query = null;            
            
            if (searchType.equals("exact") && queryType.equals("goid")) {
                query = new BasicDBObject("go", queryString.trim().toLowerCase());
            } else if (searchType.equals("contains") && queryType.equals("goid")) {
                query = new BasicDBObject("go", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            } else if (searchType.equals("exact") && queryType.equals("goterm")){
                query = new BasicDBObject("term", queryString.trim().toLowerCase());
            } else if (searchType.equals("contains") && queryType.equals("goterm")) {
                query = new BasicDBObject("term", java.util.regex.Pattern.compile(queryString.trim().toLowerCase()));
            }

            if (queryType.equals("goterm")) {
                cursor = goMap2.find(query);
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    go_s.add((String)match.get("term")); 
                    go_ids.add((String)match.get("_id")); 
                    
                    List <BasicDBObject> genes = (List <BasicDBObject>) match.get("genes");
                    for (int i = 0; i < genes.size(); i++){
                        entrez_ids.add((String)genes.get(i).get("entrez"));
                    } 
                }
            } else if (queryType.equals("goid")) {
                cursor = goMap2.find(query);
                while (cursor.hasNext()) {
                    DBObject match = (DBObject)cursor.next();
                    go_s.add((String)match.get("go")); 
                    
                    List <BasicDBObject> genes = (List <BasicDBObject>) match.get("genes");
                    for (int i = 0; i < genes.size(); i++){
                        entrez_ids.add((String)genes.get(i).get("entrez"));
                    } 
                }
            }
                             
            
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        entrezGOList.add(entrez_ids);
        entrezGOList.add(go_s);
        entrezGOList.add(go_ids);
        
        return entrezGOList;
    }
    
    public ArrayList <GoObject> processGOid (String id) {
        ArrayList <GoObject> go_s = new ArrayList<GoObject> ();
        DBCursor cursor = null;
        
        try{
            BasicDBObject query = new BasicDBObject("_id", id.trim().toLowerCase());
            cursor = goMap2.find(query);
            
            while(cursor.hasNext()){
                DBObject match = (DBObject)cursor.next();
                GoObject go = new GoObject(match);
                go_s.add(go);
            }
            
        }catch(Exception e) {
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return go_s;
    }
    
    public ArrayList <PathwayObject> processPathid (String id){
        
        ArrayList <PathwayObject> paths = new ArrayList<PathwayObject> ();
        DBCursor cursor = null;
                
        try{
            BasicDBObject query = new BasicDBObject("_id", id.trim().toLowerCase());
            cursor = pathwayMap.find(query);
            
            while(cursor.hasNext()){
                DBObject match = (DBObject)cursor.next();
                PathwayObject path = new PathwayObject(match);
                paths.add(path);
            }
            
            
        }catch(Exception e) {
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        return paths;
    }
    
    public ArrayList <GeneObject> processEntrezidGeneSymbol(String entrezid){
               
        ArrayList <GeneObject> genes = new ArrayList<GeneObject> ();
        DBCursor cursor = null;
        DBCursor cursor1 = null;
        
        
        try {
            BasicDBObject query = new BasicDBObject("_id", entrezid.trim().toLowerCase());
            cursor = geneMap2.find(query);
            
            while(cursor.hasNext()){
                DBObject match = (DBObject)cursor.next();
                GeneObject gene = new GeneObject(match);
                
                BasicDBObject q1 = new BasicDBObject("_id", gene.entrez_id.trim().toLowerCase());
                cursor1 = entrezAliasMap.find(q1);
                if(cursor1.hasNext()){
                    DBObject match1 = (DBObject)cursor1.next();
                    gene.setAliases(match1); 
                }
                genes.add(gene);
            }
            
            
        } catch(Exception e) {
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return genes;
    }
    
    public ArrayList <String> processEntrezidGOInfo(String entrezid){
        
        ArrayList <String> goinfo = new ArrayList<String> ();
        DBCursor cursor = null;
        
        try{
            BasicDBObject query = null;  
            query = new BasicDBObject("_id", entrezid.trim().toLowerCase());
            cursor = geneMap2.find(query);
            while(cursor.hasNext()){
                DBObject match = (DBObject)cursor.next();
                List <BasicDBObject> godata = (List <BasicDBObject>) match.get("goids");
                
                for(int i = 0; i < godata.size(); i++){
                    goinfo.add((String)godata.get(i).get("go"));
                    goinfo.add((String)godata.get(i).get("ontology"));
                    goinfo.add((String)godata.get(i).get("evidence"));
                    goinfo.add((String)godata.get(i).get("term"));                    
                }
            }      
            
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return goinfo;
    }
    
    public ArrayList <String> processEntrezidPathInfo(String entrezid){
        
        ArrayList <String> pathinfo = new ArrayList<String> ();
        DBCursor cursor = null;
        
        try{
            BasicDBObject query = null;  
            query = new BasicDBObject("_id", entrezid.trim().toLowerCase());
            cursor = geneMap2.find(query);
            while(cursor.hasNext()){
                DBObject match = (DBObject)cursor.next();
                List <BasicDBObject> godata = (List <BasicDBObject>) match.get("pathways");
                
                for(int i = 0; i < godata.size(); i++){
                    pathinfo.add((String)godata.get(i).get("pathway"));
                    pathinfo.add((String)godata.get(i).get("external_id"));
                    pathinfo.add((String)godata.get(i).get("source"));
                                     
                }
            }      
            
        }catch(Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return pathinfo;
    }
}
