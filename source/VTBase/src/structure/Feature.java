/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author soumitag
 */
public class Feature implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public String genesymbol;
    public String entrezId;
    public String probeId;
    public ArrayList<String> aliases;
    
    public Feature(){}
    
    public void setGeneSymbol(String genesymbol){
        this.genesymbol = genesymbol;
    }
    
    public void setEntrezID (String entrezId){
        this.entrezId = entrezId;
    }
    
    public void setProbeID (String probeId){
        this.probeId = probeId;
    }
    
    public void setAliases(ArrayList<String> aliases){
        this.aliases = aliases;
    }
}
