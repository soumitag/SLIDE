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
 * @author Soumita
 */
public class DataCells implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public float[][] dataval;
    public float[][] visualization_normval;
    public float[][] clustering_normval;
    public byte bin[][];
    
    public int height;
    public int width;
    
    public DataCells () {}
    
    public DataCells (int nRows, int nCols) {
        dataval = new float[nRows][nCols];
        visualization_normval = new float[nRows][nCols];
        clustering_normval = new float[nRows][nCols];
        bin = new byte[nRows][nCols];
        
        this.height = nRows;
        this.width = nCols;
    }
    
    public void setNormalizedVal(float normval, String type, int i, int j) {
        if (type.equalsIgnoreCase("heatmap")) {
            this.visualization_normval[i][j] = normval;
        } else if (type.equalsIgnoreCase("clustering")) {
            this.clustering_normval[i][j] = normval;
        }
    }
    
    public float[] getRow(int rownum) {
        return dataval[rownum];
    }
    
    public float[] getCol(int colnum) {
        
        float[] cellcoldata = new float[dataval[colnum].length];
        for(int i = 0; i < dataval.length; i++) {
            cellcoldata[i] = dataval[i][colnum];             
        }
        return cellcoldata;
    }
    
    public void setBinNo (int binno, int i, int j) {
        // binno = 255
        // bin[i][j] = 255 + (-128) = 127
        bin[i][j] = (byte)(binno + Byte.MIN_VALUE);
    }
    
    public int getBinNo (int i, int j) {
        // bin[i][j] = 127
        // binno = 127 - (-128) = 255
        
        // bin[i][j] = -128
        // binno = -128 - (-128) = 0
        return (int)(bin[i][j] - Byte.MIN_VALUE);
    }
    
    public DataCells getFilteredDataCells(ArrayList <Integer> filtered_row_indices) {
        
        DataCells cloneDC = new DataCells(filtered_row_indices.size(), this.width);
        int count = 0;
        for (int i=0; i<filtered_row_indices.size(); i++) {
            cloneDC.dataval[count] = this.dataval[filtered_row_indices.get(i)];
            cloneDC.visualization_normval[count] = this.visualization_normval[filtered_row_indices.get(i)];
            cloneDC.clustering_normval[count] = this.clustering_normval[filtered_row_indices.get(i)];
            cloneDC.bin[count] = this.bin[filtered_row_indices.get(i)];
            count++;
        }
        
        return cloneDC;
    }
}
