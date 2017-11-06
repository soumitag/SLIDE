/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.transforms;

import java.util.ArrayList;
import net.sf.javaml.core.kdtree.KDTree;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import vtbase.DataParsingException;

/**
 *
 * @author Soumita
 */
public class Imputer {
        
    public static final int IMPUTE_NONE = 0;
    public static final int IMPUTE_ROW_MEAN = 1;
    public static final int IMPUTE_COLUMN_MEAN = 2;
    public static final int IMPUTE_ROW_MEDIAN = 3;
    public static final int IMPUTE_COLUMN_MEDIAN = 4;
    
    public boolean[] row_mask;
    public boolean[] col_mask;
    public boolean[][] cell_mask;
    public int nSamples;
    public int nFeatures;
    
    public Imputer(int nFeatures, int nSamples) {
        this.nSamples = nSamples;
        this.nFeatures = nFeatures;
        row_mask = new boolean[this.nFeatures];
        col_mask = new boolean[this.nSamples];
        cell_mask = new boolean[this.nFeatures][this.nSamples];
    }
    
    public void markCell (int row, int col) {
        row_mask[row] = true;
        col_mask[col] = true;
        cell_mask[row][col] = true;
    }
    
    public float[][] impute(float[][] raw_data, int impute_type) throws DataParsingException {
        
        checkBadRows();
        checkBadCols();
        
        switch (impute_type) {
            case Imputer.IMPUTE_ROW_MEAN:
                return imputeRowsMean(raw_data);
            case Imputer.IMPUTE_COLUMN_MEAN:
                return imputeColsMean(raw_data);
            case Imputer.IMPUTE_ROW_MEDIAN:
                return imputeRowsMedian(raw_data);
            case Imputer.IMPUTE_COLUMN_MEDIAN:
                return imputeColsMedian(raw_data);
            default:
                return null;
        }
    }
    
    public float[][] imputeRowsMean(float[][] raw_data) {
        
        float[] row_means = new float[nFeatures];
        for (int i=0; i<nFeatures; i++) {
            if (row_mask[i]) {
                SummaryStatistics stats = new SummaryStatistics();
                for (int j=0; j<nSamples; j++) {
                    if (!cell_mask[i][j]) {
                        stats.addValue(raw_data[i][j]);
                    }
                }
                row_means[i] = (float)stats.getMean();
                stats.clear();
            }
        }
        
        for (int i = 0; i < nFeatures; i++) {
            for (int j = 0; j < nSamples; j++) {
                if (cell_mask[i][j]) {
                    raw_data[i][j] = row_means[i];
                }
            }
        }
        
        return raw_data;
    }
    
    public float[][] imputeRowsMedian(float[][] raw_data) {
        
        float[] row_medians = new float[nFeatures];
        for (int i=0; i<nFeatures; i++) {
            if (row_mask[i]) {
                DescriptiveStatistics stats = new DescriptiveStatistics();
                for (int j=0; j<nSamples; j++) {
                    if (!cell_mask[i][j]) {
                        stats.addValue(raw_data[i][j]);
                    }
                }
                row_medians[i] = (float)stats.getPercentile(50.0);
                stats.clear();
            }
        }
        
        for (int i = 0; i < nFeatures; i++) {
            for (int j = 0; j < nSamples; j++) {
                if (cell_mask[i][j]) {
                    raw_data[i][j] = row_medians[i];
                }
            }
        }
        
        return raw_data;
    }
    
    public float[][] imputeColsMean(float[][] raw_data) {
        
        float[] col_means = new float[nSamples];
        for (int j=0; j<nSamples; j++) {
            if (col_mask[j]) {
                SummaryStatistics stats = new SummaryStatistics();
                for (int i=0; i<nFeatures; i++) {
                    if (!cell_mask[i][j]) {
                        stats.addValue(raw_data[i][j]);
                    }
                }
                col_means[j] = (float)stats.getMean();
                stats.clear();
            }
        }
        
        for (int i = 0; i < nFeatures; i++) {
            for (int j = 0; j < nSamples; j++) {
                if (cell_mask[i][j]) {
                    raw_data[i][j] = col_means[j];
                }
            }
        }
        
        return raw_data;
    }
    
    public float[][] imputeColsMedian(float[][] raw_data) {
        
        float[] col_means = new float[nSamples];
        for (int j=0; j<nSamples; j++) {
            if (col_mask[j]) {
                DescriptiveStatistics stats = new DescriptiveStatistics();
                for (int i=0; i<nFeatures; i++) {
                    if (!cell_mask[i][j]) {
                        stats.addValue(raw_data[i][j]);
                    }
                }
                col_means[j] = (float)stats.getPercentile(50.0);
                stats.clear();
            }
        }
        
        for (int i = 0; i < nFeatures; i++) {
            for (int j = 0; j < nSamples; j++) {
                if (cell_mask[i][j]) {
                    raw_data[i][j] = col_means[j];
                }
            }
        }
        
        return raw_data;
    }
    
    private boolean checkBadRows() throws DataParsingException {
        
        for (int i = 0; i < nFeatures; i++) {
            if (row_mask[i]) {
                int count = 0;
                for (int j=0; j<nSamples; j++) {
                    if (cell_mask[i][j]) {
                        count++;
                    }
                }
                if (count == nSamples) {
                    throw new DataParsingException("Data row " + (i+1) + " has no numeric values.");
                }
            }
        }
        
        return true;
    }
    
    private boolean checkBadCols() throws DataParsingException {
        
        for (int j = 0; j < nSamples; j++) {
            if (col_mask[j]) {
                int count = 0;
                for (int i=0; i<nFeatures; i++) {
                    if (cell_mask[i][j]) {
                        count++;
                    }
                }
                if (count == nFeatures) {
                    throw new DataParsingException("Data column " + (j+1) + " has no numeric values.");
                }
            }
        }
        
        return true;
    }
    
    public void knnImpute(float[][] raw_data, ArrayList<ArrayList<Integer>> impute_indices, int K, double cull_percent){
        
        // impute_indices - each arraylist entry is n-D array: [row_index, column_index, column_index, ...]
        // with one row_index and a list of column_indices that follow
        
        // cull_percent: if more than cull_percent data is missing in a row the row will be culled
        
        int nCull = (int)Math.ceil(raw_data[0].length*cull_percent);
        int nRowsToCull = 0;
        for (int i = 0; i < impute_indices.size(); i++) {
            if ( impute_indices.get(i).size() < nCull ) {
                nRowsToCull++;
            }
        }
        
        float[][] temp_raw_data = new float[nRowsToCull][raw_data[0].length];
        int count = 0;
        for (int i = 0; i < impute_indices.size(); i++) {
            if ( impute_indices.get(i).size() < nCull ) {
                temp_raw_data[count] = raw_data[i];
                count++;
            }
        }
        
        raw_data = temp_raw_data;
        
        for (int i=0; i<raw_data.length; i++) {
            
            if ( impute_indices.get(i).size() > 0 ) {
                
                int nAvailCols = raw_data[0].length - impute_indices.get(i).size();
                for (int j = 0; j < raw_data[0].length; j++){
                    if (!impute_indices.get(i).contains(j)) {
                        
                    }
                }
                
                // build k-d tree using non-missing columns
                KDTree kdtree = new KDTree(nAvailCols);
                for (int j = 0; j < raw_data.length; j++){
                    double[] temp = new double[nAvailCols];
                    count = 0;
                    for (int k = 0; k < raw_data[0].length; k++) {
                        if (!impute_indices.get(i).contains(k)) {
                            temp[count++] = raw_data[j][k];
                        }
                    }
                    kdtree.insert(temp, j);
                }
                
                for( int x = 0; x < impute_indices.get(i).size(); x++){
                    int impute_col_x = impute_indices.get(i).get(x);
                    double[] temp = new double[nAvailCols];
                    count = 0;
                    for (int k = 0; k < raw_data[0].length; k++) {
                        if (!impute_indices.get(i).contains(k)) {
                            temp[count++] = raw_data[i][k];
                        }
                    }
                    Object[] nearest_rows = kdtree.nearest(temp, K+1);
                    float substitute_value = 0;           
                    for(int nn = 1; nn < nearest_rows.length; nn++){
                        substitute_value += raw_data[(int)nearest_rows[nn]][impute_col_x];
                    }
                    substitute_value /= nearest_rows.length;
                    raw_data[i][impute_col_x] = substitute_value;
                }
                
            }
        }
        
    }
}
