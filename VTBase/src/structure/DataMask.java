/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Soumita
 */
public class DataMask {
    
    int NUM_ROWS;
    int NUM_COLS;
    
    public boolean[] row_mask;
    public boolean[] col_mask;
    
    public DataMask (Data data) {
        this.NUM_ROWS = data.datacells.height;
        this.NUM_COLS = data.datacells.width;
        row_mask = new boolean [NUM_ROWS];
        col_mask = new boolean [NUM_COLS];
        Arrays.fill(row_mask, true);
        Arrays.fill(col_mask, true);
    }
    
    public void createRowMask(ArrayList <Integer> rows) {
        row_mask = new boolean [NUM_ROWS];
        for (int i=0; i<rows.size(); i++) {
            row_mask[i] = true;
        }
    }
    
    public void createColMask(ArrayList <Integer> cols) {
        col_mask = new boolean [NUM_COLS];
        for (int i=0; i<cols.size(); i++) {
            col_mask[i] = true;
        }
    }
    
    public void createMask(ArrayList <Integer> rows, ArrayList <Integer> cols) {
        createRowMask(rows);
        createColMask(cols);
    }
    
    public void createRowMask(boolean[] mask) {
        assert NUM_ROWS == mask.length;
        row_mask = mask;
    }
    
    public void createColMask(boolean[] mask) {
        assert NUM_COLS == mask.length;
        col_mask = mask;
    }
    
    public void createMask(boolean[] row_mask, boolean[] col_mask) {
        assert NUM_ROWS == row_mask.length;
        this.row_mask = row_mask;
        assert NUM_COLS == col_mask.length;
        this.col_mask = col_mask;
    }
    
}
