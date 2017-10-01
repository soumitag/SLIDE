/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

/**
 *
 * @author Soumita
 */
public class CompactSearchResultContainer {
    
    public static final byte TYPE_GENE = 1;
    public static final byte TYPE_PATH = 2;
    public static final byte TYPE_GO = 3;
    
    public byte type;
    public String entrez_id;
    public String group_id;
    public String group_name;
    
    public CompactSearchResultContainer () { }
    
    public void createGeneSearchResult (String entrez_id, String genesymbol) {
        this.type = 1;
        this.entrez_id = entrez_id;
        this.group_name = genesymbol;
    }
    
    public void createPathwaySearchResult (String entrez_id, String path_id, String pathname) {
        this.type = 2;
        this.entrez_id = entrez_id;
        this.group_id = path_id;
        this.group_name = pathname;
    }
    
    public void createGOSearchResult (String entrez_id, String go_id, String go_term) {
        this.type = 3;
        this.entrez_id = entrez_id;
        this.group_id = go_id;
        this.group_name = go_term;
    }
    
    public String getEntrezID() {
        return this.entrez_id;
    }
    
    public String getGeneSymbol() {
        if (type == 1)
            return this.group_name;
        else
            return null;
    }
    
    public String getPathID() {
        if (type == 2)
            return this.group_id;
        else
            return null;
    }
    
    public String getGOID() {
        if (type == 3)
            return this.group_id;
        else
            return null;
    }
    
    public String getPathName() {
        if (type == 2)
            return this.group_name;
        else
            return null;
    }
    
    public String getGOTerm() {
        if (type == 3)
            return this.group_name;
        else
            return null;
    }
}
