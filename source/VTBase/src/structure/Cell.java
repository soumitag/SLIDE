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
public class Cell implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public double dataval;
    public double visualization_normval;
    public double clustering_normval;
    public double[] rgb;
    public int bin;
    
    public Cell(double dataval){
        //datavals = new ArrayList <> ();
        this.dataval = dataval;
        this.visualization_normval = -1;
        this.clustering_normval = -1;
        rgb = new double[3];
        bin = -1;
    }
    
    public void setCellColor(double[] rgb) {
        this.rgb = rgb;
    }
    
    public void setNormalizedVal(double normval, String type) {
        if (type.equalsIgnoreCase("heatmap")) {
            this.visualization_normval = normval;
        } else if (type.equalsIgnoreCase("clustering")) {
            this.clustering_normval = normval;
        }
    }
    
    /*
    public void addVal(int i, int j, double dataval) {
        datavals.get(i).add(j, dataval);
    }
    */
    
}
