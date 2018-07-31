/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.io.Serializable;

/**
 *
 * @author Soumita
 */
public class Histogram implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public int nBins;
    public double binsize;
    public int[] frequencies;
    public double[] normedFrequencies;
    public double rgb[][];
    public double MIN_VAL;
    public double MAX_VAL;
    public double[] bincenters;
    //public double[] binstarts;
    
    // For data min-max binning
    public Histogram (int nBins, double[] min_max) {
        init(nBins, min_max[0], min_max[1]);
    }
    
    // For symmetric bins
    public Histogram (int nBins, double extreme_value) {
        init(nBins, -extreme_value, extreme_value);
    }
    
    // For user-specified min-max range
    public Histogram (int nBins, double range_start, double range_end) {
        init(nBins, range_start, range_end);
    }
    
    public final void init(int nBins, double min, double max) {
        
        if (nBins > 513) {
            nBins = 513;
        }
        
        this.nBins = nBins;
        binsize = (max - min)/nBins;
        frequencies = new int[nBins];
        rgb = new double[nBins][3];
        
        MIN_VAL = min;
        MAX_VAL = max;
        
        bincenters = new double[nBins];
        for(int i = 0; i < nBins; i++) {
            bincenters[i] = MIN_VAL + (i * binsize) + binsize/2;
        }
        
        /*
        binstarts = new double[nBins];
        for(int i = 0; i < nBins; i++) {
            binstarts[i] = MIN_VAL + (i * binsize);
        }
        */
    }
    
    public byte getBinNum_Int(double val){
        
        //byte binnum = (byte)Math.floor((val - MIN_VAL)/binsize);
        int binnum = (int)Math.floor((val - MIN_VAL)/binsize);
        
        if (binnum < 0) {
            return (byte)(0);
        } else if (binnum >= nBins) {
            return (byte)(nBins-1);
        } else {
            return (byte)binnum;
        }
    }
    
    public void addToBin_Int(int bin_no) {
        frequencies[bin_no]++;
    }
    
    public void normalizeHist() {
        double sum = 0;
        for (int i=0; i<frequencies.length; i++) {
            sum += frequencies[i];
        }
        normedFrequencies = new double[frequencies.length];
        for (int i=0; i<frequencies.length; i++) {
            normedFrequencies[i] = (double)frequencies[i]/sum;
        }
    }
    
    public String buildHistImage (String path, int width, int height) {
        return "";
    }
    
}
