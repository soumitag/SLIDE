/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package params;

import java.io.Serializable;

/**
 *
 * @author soumita
 */
public class EnrichmentParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public float significance_level;
    public int small_k;
    public int big_K;
    
    public EnrichmentParams() {
        significance_level = 0.05f;
        small_k = 0;
        big_K = 0;
    }
 
    public void setSignificanceLevel(float significance_level) {
        this.significance_level = significance_level;
    }
    
    public void setSmallk(int small_k) {
        this.small_k = small_k;
    }
    
    public void setBigK(int big_K) {
        this.big_K = big_K;
    }
    
    public String getHashString() {
        return significance_level + "_" + small_k + "_" + big_K;
    }
}
