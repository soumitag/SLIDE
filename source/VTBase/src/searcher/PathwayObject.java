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
public class PathwayObject {
    
    public String pathway;
    public String _id;
    public String source;
    public ArrayList <String> entrez_ids;
    public ArrayList <String> genesymbols;
    
    public PathwayObject(String pathway, String _id, String source) {
        this.pathway = pathway;
        this._id = _id;
        this.source = source;
    }
    
    public PathwayObject(DBObject pathwayMap_Doc) {
        this._id = (String)pathwayMap_Doc.get("_id");
        this.pathway = (String)pathwayMap_Doc.get("pathway");
        this.source = (String)pathwayMap_Doc.get("source");
        List <BasicDBObject> gene_dbobjs = 
                (List <BasicDBObject>)pathwayMap_Doc.get("genes");
        entrez_ids = new ArrayList <> ();
        genesymbols = new ArrayList <> ();
        for (int i=0; i<gene_dbobjs.size(); i++) {
            BasicDBObject gene_dbobj = gene_dbobjs.get(i);
            //entrez_ids.add((String)gene_dbobj.getString("entrez_id"));
            entrez_ids.add((String)gene_dbobj.getString("entrez"));
            genesymbols.add((String)gene_dbobj.getString("genesymbol"));
        }
    }
    
    public PathwayObject(){
        entrez_ids = new ArrayList <> ();
        genesymbols = new ArrayList <> ();
        this._id = null;
        this.pathway = null;
        this.source = null;        
    }
    
    public void setPathid (String id) {
        _id = id;
    }
    
    public void setPathname (String pathname){
        pathway = pathname;
    }
    
    public void setSource (String src){
        source = src;
    }
    
    public void setEntrezIDs (Object genes){
        List <BasicDBObject> gene_list = (List <BasicDBObject>) genes;
        for (int i = 0; i < gene_list.size(); i++){
            entrez_ids.add((String)gene_list.get(i).get("entrez"));
            genesymbols.add((String)gene_list.get(i).get("genesymbol"));
        }
    }
}
