/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import searcher.GeneObject;
import searcher.Searcher;
import vtbase.DataParsingException;
import vtbase.SlideException;

/**
 *
 * @author Soumita
 */
public class MetaData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public int nFeatures;
    
    private final HashMap <Integer, String> identifier_name_map;
    private final HashMap <String, Integer> identifier_index_map;
    private final HashMap <String, String[]> metadata_in;
    //private final HashMap <String, Boolean> standard_metadata_availibility_map;
    private boolean hasMappedData;
    
    public MetaData(int nFeatures) {
        
        this.nFeatures = nFeatures;
        
        this.metadata_in = new HashMap <> ();
        
        this.identifier_name_map = new HashMap <> ();
        identifier_name_map.put(0, "entrez_2021158607524066");
        identifier_name_map.put(1, "genesymbol_2021158607524066");
        identifier_name_map.put(2, "refseq_2021158607524066");
        identifier_name_map.put(3, "ensembl_gene_id_2021158607524066");
        identifier_name_map.put(4, "ensembl_transcript_id_2021158607524066");
        identifier_name_map.put(5, "ensembl_protein_id_2021158607524066");
        identifier_name_map.put(6, "uniprot_id_2021158607524066");
        
        this.identifier_index_map = new HashMap <> ();
        identifier_index_map.put("entrez_2021158607524066", 0);
        identifier_index_map.put("genesymbol_2021158607524066", 1);
        identifier_index_map.put("refseq_2021158607524066", 2);
        identifier_index_map.put("ensembl_gene_id_2021158607524066", 3);
        identifier_index_map.put("ensembl_transcript_id_2021158607524066", 4);
        identifier_index_map.put("ensembl_protein_id_2021158607524066", 5);
        identifier_index_map.put("uniprot_id_2021158607524066", 6);
        
        //standard_metadata_availibility_map = new HashMap <> ();
        hasMappedData = false;
    }
    
    public int getIdentifierType(String identifier_name) {
        return identifier_index_map.get(identifier_name);
    }
    
    public String getIdentifierName(int identifier_type) {
        return identifier_name_map.get(identifier_type);
    }
    
    /*
    public void addMetaCol_1 (int metacol_type) {
        String[] ids = new String[this.nFeatures];
        metadata_in.put(identifier_name_map.get(metacol_type), ids);
    }
    */
    
    public void setHasMappedMetadata (boolean hasMetaData) {
        this.hasMappedData = hasMetaData;
    }
    
    public void addMetaCol (String metacol_name) {
        String[] ids = new String[this.nFeatures];
        metadata_in.put(metacol_name, ids);
    }
    
    public ArrayList <String> getMetaColNames_1() {
        return new ArrayList<>(metadata_in.keySet());
    }
    
    public ArrayList <String> getNonStandardMetaColNames() {
        ArrayList <String> nonstandard_metacolnames = new ArrayList<>();
        ArrayList <String> metacolnames = new ArrayList<>(metadata_in.keySet());
        for (int i=0; i<metacolnames.size(); i++) {
            if (!identifier_index_map.containsKey(metacolnames.get(i))) {
                nonstandard_metacolnames.add(metacolnames.get(i));
            }
        }
        return nonstandard_metacolnames;
    }
    
    public void addFeature(int feature_index, String metacol_name, String value) throws SlideException {
        if (metadata_in.containsKey(metacol_name)) {
            metadata_in.get(metacol_name)[feature_index] = value.trim().toUpperCase();
        } else {
            throw new SlideException(
                    "Exception in MetaData.addFeature(). No metadata column found for key '" + metacol_name + "'.", 
                    -111);
        }
    }
    
    public boolean hasStandardMetaData() {
        return hasMappedData;
    }
    
    public void mapFeatureEntrezs( String species, 
                                   ArrayList <Feature> features) {
        
        Searcher searcher = null;
        if (this.hasMappedData) {
            searcher = new Searcher(species);
        }
        
        boolean hasEntrezData = metadata_in.containsKey("entrez_2021158607524066");
        
        String parsed_entrez;
        int bad_entrez_counter = -1;
        boolean hasIdentifiers;
        boolean usesBadEntrez;
        
        for (int i=0; i<this.nFeatures; i++) {
            
            usesBadEntrez = false;
            //ArrayList <String> db_entrezs = new ArrayList <> ();
            HashSet <String> db_entrezs = new HashSet <String> ();
            
            String entrez_in = "";
            if (hasEntrezData) {
                entrez_in = metadata_in.get("entrez_2021158607524066")[i];
            }
            
            if (!entrez_in.equals("")) {
                parsed_entrez = entrez_in; 
            } else {
               
                hasIdentifiers = false;
                HashMap <Integer, String> available_identifier_type_value_map = null;
                if (this.hasMappedData) {
                    available_identifier_type_value_map = new HashMap <> ();
                    for (int k=0; k<6; k++) {
                        String identifier_in_name = identifier_name_map.get(k+1);
                        if (metadata_in.containsKey(identifier_in_name)) {
                            String identifier_in_value = metadata_in.get(identifier_in_name)[i];
                            if (!identifier_in_value.equals("")) {
                                hasIdentifiers = true;
                                available_identifier_type_value_map.put(k+1, identifier_in_value);
                            }
                        }
                    }
                }
                
                if(hasIdentifiers) {
                    
                    for (Map.Entry<Integer, String> entry : available_identifier_type_value_map.entrySet()) {
                        db_entrezs.addAll(searcher.getEntrezFromDB(entry.getValue(), entry.getKey()));
                    }
                    
                    if (db_entrezs.size() > 0) {
                        parsed_entrez = getConsensusValue(db_entrezs);
                    } else {
                        parsed_entrez = "" + bad_entrez_counter;
                        usesBadEntrez = true;
                        bad_entrez_counter--;
                    }
                    
                } else {
                    parsed_entrez = "" + bad_entrez_counter;
                    usesBadEntrez = true;
                    bad_entrez_counter--;
                }
            }
            
            Feature f = new Feature();
            f.setEntrezs(parsed_entrez, usesBadEntrez, db_entrezs);
            features.add(f);
        }
        
    }
    
    public HashMap <String, ArrayList <String>> mapFeatureIdentifiers (String species, 
                                                                       String identifier_name,
                                                                       ArrayList <Feature> features) {
        
        HashMap <String, ArrayList <String>> entrezIdentifierMap = new HashMap <> ();
        
        Searcher searcher = null;
        if (this.hasMappedData) {
            searcher = new Searcher(species);
        }
        
        boolean hasIdentifierData = metadata_in.containsKey(identifier_name);
        
        String parsed_identifier;
        int bad_identifier_counter = 0;
        ArrayList <String> db_identifiers = null;
        
        for (int i=0; i<this.nFeatures; i++) {
            
            db_identifiers = new ArrayList <> ();
            
            String identifier_in = "";
            if (hasIdentifierData) {
                identifier_in = metadata_in.get(identifier_name)[i];
            }
            
            if (!identifier_in.equals("")) {
                
                parsed_identifier = identifier_in;
                
            } else {
                
                if (this.hasMappedData) {
                    db_identifiers = searcher.getIdentifiersFromDB(features.get(i).entrez, identifier_index_map.get(identifier_name));
                }
                
                if (db_identifiers.size() > 0) {
                    parsed_identifier = db_identifiers.get(0);
                } else {
                    parsed_identifier = "Unknown_" + bad_identifier_counter;
                    bad_identifier_counter++;
                }
            }
            
            if (!(db_identifiers.contains(parsed_identifier))) {
                db_identifiers.add(0, parsed_identifier);
            }
            
            features.get(i).identifier = parsed_identifier;
            features.get(i).identifier_aliases = db_identifiers;
            
            entrezIdentifierMap.put(features.get(i).entrez, db_identifiers);
        }
        
        return entrezIdentifierMap;
    }
    
    public MetaData cloneMetaData(ArrayList <Integer> filtered_row_indices) throws SlideException {
        
        MetaData clone;
        
        clone = new MetaData(filtered_row_indices.size());
        
        int count;
        String metacol_name;
        ArrayList <String> metacolnames = new ArrayList<>(metadata_in.keySet());
        for (int i=0; i<metacolnames.size(); i++) {
            metacol_name = metacolnames.get(i);
            clone.addMetaCol(metacol_name);
            String[] metadata = metadata_in.get(metacol_name);
            count = 0;
            for (int j=0; j<filtered_row_indices.size(); j++) {
                clone.addFeature(count++, metacol_name, metadata[filtered_row_indices.get(j)]);
            }
        }
        clone.hasMappedData = this.hasMappedData;
        
        return clone;
        
    }
    
    /*
    Searches mapped metadata
    */
    public ArrayList <CompactSearchResultContainer> searchMetadata(ArrayList <Feature> features, String metacol_name, String query) {
        
        ArrayList <CompactSearchResultContainer> current_search_results = new ArrayList <> ();
        
        String qualified_metacol_name;
        switch (metacol_name) {
            case "entrez":
                qualified_metacol_name = "entrez_2021158607524066";
                break;
            case "genesymbol":
                qualified_metacol_name = "genesymbol_2021158607524066";
                break;
            case "refseq":
                qualified_metacol_name = "refseq_2021158607524066";
                break;
            case "ensembl_gene_id":
                qualified_metacol_name = "ensembl_gene_id_2021158607524066";
                break;
            case "ensembl_transcript_id":
                qualified_metacol_name = "ensembl_transcript_id_2021158607524066";
                break;
            case "ensembl_protein_id":
                qualified_metacol_name = "ensembl_protein_id_2021158607524066";
                break;
            case "uniprot_id":
                qualified_metacol_name = "uniprot_id_2021158607524066";
                break;
            default:
                return current_search_results;
        }
        
        query = query.trim().toUpperCase();
        
        String[] metacol_values = this.metadata_in.get(qualified_metacol_name);
        if (metacol_values != null) {
            for (int i=0; i<metacol_values.length; i++) {
                Feature f = features.get(i);
                if (f.hasBadEntrez) {
                    if (metacol_values[i] != null && metacol_values[i].contains(query)) {
                        CompactSearchResultContainer csrc = new CompactSearchResultContainer();
                        csrc.createGeneSearchResult(f.entrez, metacol_values[i]);
                        current_search_results.add(csrc);
                    }
                }
            }
        }
        return current_search_results;
    }
    
    public ArrayList <CompactSearchResultContainer> searchNonStandardMetadata(ArrayList <Feature> features, String metacol_name, String query) {
        
        ArrayList <CompactSearchResultContainer> current_search_results = new ArrayList <> ();
        
        String qualified_metacol_name = metacol_name.substring(1);
        query = query.trim().toUpperCase();
        String[] metacol_values = this.metadata_in.get(qualified_metacol_name);
        if (metacol_values != null) {
            for (int i=0; i<metacol_values.length; i++) {
                Feature f = features.get(i);
                if (f.hasBadEntrez) {
                    if (metacol_values[i] != null && metacol_values[i].contains(query)) {
                        CompactSearchResultContainer csrc = new CompactSearchResultContainer();
                        csrc.createGeneSearchResult(f.entrez, metacol_values[i]);
                        current_search_results.add(csrc);
                    }
                }
            }
        }
        return current_search_results;
    }
    
    private String getConsensusValue (HashSet <String> values) {
        //return values.get(0);
        Iterator iter = values.iterator();
        return (String) iter.next();
    }
    
}
