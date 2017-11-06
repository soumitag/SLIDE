/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.transforms;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import structure.DataCells;

/**
 *
 * @author Soumita
 */
public class Normalizer {
    
    public static final int COL_NORMALIZATION_NONE = 0;
    public static final int COL_NORMALIZATION_AUTOSCALE = 1;
    public static final int COL_NORMALIZATION_STANDARDIZE = 2;
    public static final int COL_NORMALIZATION_PARETO = 3;
    
    public static final int ROW_NORMALIZATION_NONE = 0;
    public static final int ROW_NORMALIZATION_AUTOSCALE = 1;
    public static final int ROW_NORMALIZATION_MEANCENTERED = 2;
    public static final int ROW_NORMALIZATION_STANDARDIZED = 3;
    
    DataCells datacells;
    public Normalizer (DataCells datacells) {
        this.datacells = datacells;
    }
    
    public void meanCenterRows () {
        float rowmean;
        SummaryStatistics stats = new SummaryStatistics();
        for(int i = 0; i < datacells.height; i++) {
            for (int j=0; j<datacells.width; j++) {
                stats.addValue(datacells.dataval[i][j]);
            }
            rowmean = (float)stats.getMean();
            for (int j=0; j<datacells.width; j++) {
                datacells.dataval[i][j] = datacells.dataval[i][j] - rowmean;
            }
            stats.clear();
        }
    }
    
    public void standardizeRows () {
        float rowmean;
        float rowsdev;
        SummaryStatistics stats = new SummaryStatistics();
        for(int i = 0; i < datacells.height; i++) {
            for (int j=0; j<datacells.width; j++) {
                stats.addValue(datacells.dataval[i][j]);
            }
            rowmean = (float)stats.getMean();
            rowsdev = (float)stats.getStandardDeviation();
            for (int j=0; j<datacells.width; j++) {
                datacells.dataval[i][j] = (datacells.dataval[i][j] - rowmean)/rowsdev;
            }
            stats.clear();
        }
    }
    
    public void autoscaleRows () {
        float rowmin;
        float rowmax;
        SummaryStatistics stats = new SummaryStatistics();
        for(int i = 0; i < datacells.height; i++) {
            for (int j=0; j<datacells.width; j++) {
                stats.addValue(datacells.dataval[i][j]);
            }
            rowmin = (float)stats.getMin();
            rowmax = (float)stats.getMax();
            for (int j=0; j<datacells.width; j++) {
                datacells.dataval[i][j] = ((datacells.dataval[i][j] - rowmin)/(rowmax - rowmin));
            }
            stats.clear();
        }
    }
    
    public void autoscaleColumns () {
        float colmin;
        float colmax;
        SummaryStatistics stats = new SummaryStatistics();
        for(int j=0; j<datacells.width; j++) {
            for (int i = 0; i < datacells.height; i++) {
                stats.addValue(datacells.dataval[i][j]);
            }
            colmin = (float)stats.getMin();
            colmax = (float)stats.getMax();
            for (int i = 0; i < datacells.height; i++) {
                datacells.dataval[i][j] = ((datacells.dataval[i][j] - colmin)/(colmax - colmin));
            }
            stats.clear();
        }
    }
    
    public void standardizeColumns() {
        float colmean;
        float colsdev;
        SummaryStatistics stats = new SummaryStatistics();
        for(int j=0; j<datacells.width; j++) {
            for (int i = 0; i < datacells.height; i++) {
                stats.addValue(datacells.dataval[i][j]);
            }
            colmean = (float)stats.getMin();
            colsdev = (float)stats.getMax();
            for (int i = 0; i < datacells.height; i++) {
                datacells.dataval[i][j] = (datacells.dataval[i][j] - colmean)/colsdev;
            }
            stats.clear();
        }
    }
    
    public void paretoNormalizeColumns () {
        
        float[] col_means = new float[datacells.width];
        float[] col_stds = new float[datacells.width];
        
        for (int j=0; j<datacells.width; j++) {
            SummaryStatistics stats = new SummaryStatistics();
            for (int i=0; i<datacells.height; i++) {
                stats.addValue(datacells.dataval[i][j]);
            }
            col_means[j] = (float)stats.getMean();
            col_stds[j] = (float)stats.getStandardDeviation();
            stats.clear();
        }
        
        SummaryStatistics stats = new SummaryStatistics();
        for (int j=0; j<datacells.width; j++) {
            stats.addValue(col_means[j]);
        }
        float global_mean = (float)stats.getMean();
        stats.clear();
        
        for (int i=0; i<datacells.height; i++) {
            for (int j=0; j<datacells.width; j++) {
                datacells.dataval[i][j] = ((datacells.dataval[i][j] - col_means[j])/col_stds[j]) + global_mean;
            }
        }
        
    }
    
}
