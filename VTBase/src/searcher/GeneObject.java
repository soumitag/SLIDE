/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searcher;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SOUMITA GHOSH
 */
public class GeneObject {
    
    public String entrez_id;
    public String genesymbol;
    public ArrayList <GoObject> goTerms;
    public ArrayList <PathwayObject> pathways;
    public ArrayList <String> aliases;
    protected boolean isFilled;
    
    public GeneObject (String entrez_id, String genesymbol) {
        this.entrez_id = entrez_id;
        this.genesymbol = genesymbol;
        goTerms = new ArrayList <> ();
        pathways = new ArrayList <> ();
        aliases = new ArrayList <> ();
        isFilled = false;
    }
    
    public GeneObject (DBObject geneMap2_Doc) {
        
        this.entrez_id = (String)geneMap2_Doc.get("_id");
        this.genesymbol = (String)geneMap2_Doc.get("genesymbol");
        
        List <BasicDBObject> go_dbobjs = 
                (List <BasicDBObject>)geneMap2_Doc.get("goids");
        
        goTerms = new ArrayList <> ();
        for (int i=0; i<go_dbobjs.size(); i++) {
            BasicDBObject go_dbobj = go_dbobjs.get(i);
            GoObject go = new GoObject (go_dbobj.getString("go"),  // should be term
                                        go_dbobj.getString("ontology"),
                                        go_dbobj.getString("evidence"),
                                        go_dbobj.getString("term"));
            goTerms.add(go);
        }
        
        List <BasicDBObject> path_dbobjs = 
                (List <BasicDBObject>)geneMap2_Doc.get("pathways");
        
        pathways = new ArrayList <> ();
        for (int i=0; i<path_dbobjs.size(); i++) {
            BasicDBObject path_dbobj = path_dbobjs.get(i);
            PathwayObject path = new PathwayObject (
                                        path_dbobj.getString("pathway"),
                                        path_dbobj.getString("external_id"),
                                        path_dbobj.getString("source")
                                 );
            pathways.add(path);
        }
        isFilled = true;
        
        
    }
    
    public void setAliases (DBObject aliases_match) {
        aliases = new ArrayList <> ();
        List <BasicDBObject> aliases_list = (List <BasicDBObject>) aliases_match.get("aliases"); 
        for (int i=0; i<aliases_list.size(); i++) {
            //System.out.println(aliases.get(i).get("alias"));
            aliases.add((String)aliases_list.get(i).get("alias"));
        }
    }
    
    public GeneObject(){
        this.entrez_id = null;
        this.genesymbol = null;   
        goTerms = new ArrayList <> ();
        pathways = new ArrayList <> ();
        aliases = new ArrayList <> ();
        isFilled = false;
    }
    
    public void setEntrezID(String eid){
        entrez_id = eid;
    }
    
    public void setGeneSymbol (String gs){
        genesymbol = gs;
    }
}
