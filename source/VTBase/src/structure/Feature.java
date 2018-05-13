/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.Serializable;
import java.util.ArrayList;
import utils.Utils;

/**
 *
 * @author soumitag
 */
public class Feature implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public int identifier_type;     // display name type
    public String identifier;       // display name
    public String entrez;
    public ArrayList<String> identifier_aliases;
    public ArrayList<String> entrez_ids;
    public boolean hasBadEntrez;
    
    public Feature(){}
    
    /*
    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }
    */
    
    public void setIdentifiers (String identifier, ArrayList<String> identifier_aliases){
        this.identifier = identifier;
        if (identifier_aliases != null && identifier_aliases.size() > 0) {
            // identifier_aliases must contain identifier
            this.identifier_aliases = identifier_aliases;
        } else {
            this.identifier_aliases = new ArrayList <> ();
            this.identifier_aliases.add(identifier);
        }
    }
    
    public void setEntrezs (String entrez, boolean isBadEntrez, ArrayList<String> entrez_ids){
        this.entrez = entrez;
        this.hasBadEntrez = isBadEntrez;
        if (!isBadEntrez) {
            if (entrez_ids != null && entrez_ids.size() > 0) {
                // entrez_ids must contain entrez
                this.entrez_ids = entrez_ids;
            } else {
                this.entrez_ids = new ArrayList <> ();
                this.entrez_ids.add(entrez);
            }
        }
    }
    
    /*
    public void setAliases(ArrayList<String> identifier_aliases){
        this.identifier_aliases = identifier_aliases;
    }
    */

    public String getFormattedFeatureName(AnalysisContainer analysis) {
        
        String entrez_i = this.entrez;
        if (this.hasBadEntrez) {
            entrez_i = "-";
        }
        String genes;
        if(analysis.database.identifier_name.equals("entrez_2021158607524066")) {
            genes = entrez_i.toUpperCase();
        } else {
            if (analysis.database.metadata.hasStandardMetaData()) {
                genes = (this.identifier_aliases.get(0) + " (" + entrez_i + ")").toUpperCase();
            } else {
                genes = this.identifier_aliases.get(0).toUpperCase();
            }
        }

        if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION ||
            analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {
                genes = Utils.checkAndRemoveHtml(genes);
        }
        
        return genes;
    }
}
