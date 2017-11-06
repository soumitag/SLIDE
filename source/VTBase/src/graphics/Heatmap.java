/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import structure.Data;
import structure.Cell;
import utils.Utils;

/**
 *
 * @author Soumita
 */
public class Heatmap implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public Data data;
    public ArrayList <Integer> leaf_ordering;
    public Histogram hist;
    public double MIN_VAL;
    public double MAX_VAL;
    public String range_type;
    
    //public double[][][] rgb_data;
    //public WritableRaster current_raster;
    //public short[][][] current_raster;
    public HashMap <String, HeatmapData> current_rasters;
    
    public Heatmap() {}
    
    public Heatmap(Data data, int nBins, String range_type, ArrayList <Integer> leaf_ordering){
        this.data = data;
        this.range_type = range_type;
        //double[] min_max = data.computeDataRange();
        double[] min_max = data.DATA_MIN_MAX;
        if(range_type.equals("data_bins")) {
            this.hist = new Histogram(nBins, min_max);
        } else if(range_type.equals("symmetric_bins")) {
            this.hist = new Histogram(nBins, Math.max(Math.abs(min_max[0]), Math.abs(min_max[1])));
        }
        this.leaf_ordering = leaf_ordering;
        current_rasters = new HashMap <String, HeatmapData> ();
    }
    
    public Heatmap(Data data, int nBins, String range_type, double range_start, double range_end, ArrayList <Integer> leaf_ordering){
        this.data = data;
        this.hist = new Histogram(nBins, range_start, range_end);
        this.leaf_ordering = leaf_ordering;
        current_rasters = new HashMap <String, HeatmapData> ();
    }
    
    public void genColorData(){
        
        double[] normalized_bincenters = new double[hist.nBins];
        for(int i = 0; i < hist.nBins; i++) {
            normalized_bincenters[i] = ((hist.bincenters[i] - hist.MIN_VAL)/(hist.MAX_VAL - hist.MIN_VAL));
        }
        hist.rgb = genLinearColorScale(normalized_bincenters);
        
        /*
        double[] normalized_binstarts = new double[hist.nBins];
        for(int i = 0; i < hist.nBins; i++) {
            normalized_binstarts[i] = ((hist.binstarts[i] - hist.MIN_VAL)/(hist.MAX_VAL - hist.MIN_VAL));
        }
        hist.rgb = genLinearColorScale(normalized_binstarts);
        */
    }
    
    /*
    public void genColorData(){
        for(int i = 0; i < data.datacells.length; i++) {
            genLinearColorScale(data.getRow(i));
        }
    }
    */
    
    public void assignBinsToRows() {
        for(int i = 0; i < data.datacells.height; i++) {
            for(int j = 0; j < data.datacells.width; j++) {
                int binno = hist.getBinNum_Int(data.datacells.dataval[i][j]);
                data.datacells.setBinNo(binno, i, j);
                hist.addToBin_Int(binno);
            }
        }
    }
    
    public double[][] genLinearColorScale(double[] row_data){
        
        double[][] rgb = new double[row_data.length][3];
        
        if((row_data.length%2) == 0) {
            // Even case: two central columns will be white
            int max_blue_index = (int)((row_data.length/2) - 1);
            double stepSize = 1.0/(max_blue_index*1.0);
            // color the blues
            for (int i = 0; i <= max_blue_index; i++){
                rgb[i][0] = i * stepSize * 255;
                rgb[i][1] = rgb[i][0];
                rgb[i][2] = 255;
            }
            // color the reds
            int min_red_index = max_blue_index + 1;
            for (int i = min_red_index; i < row_data.length; i++){
                rgb[i][0] = 255;
                rgb[i][1] = 255 - ((i-min_red_index) * stepSize * 255);
                rgb[i][2] = rgb[i][1];
            }
        } else {
            // Odd case: one central columns will be white
            int max_blue_index = (int)Math.floor((row_data.length/2) - 1);
            double stepSize = 1.0/((max_blue_index+1)*1.0);
            // color the blues
            for (int i = 0; i <= max_blue_index; i++){
                rgb[i][0] = i * stepSize * 255;
                rgb[i][1] = rgb[i][0];
                rgb[i][2] = 255;
            }
            // color the white
            int i = max_blue_index + 1;
            rgb[i][0] = 255;
            rgb[i][1] = 255;
            rgb[i][2] = 255;
            // color the reds
            int count = 1;
            double colorQuanta = 255.0/(max_blue_index + 1.0);
            int min_red_index = max_blue_index + 2;
            for (i = min_red_index; i < row_data.length; i++){
                rgb[i][0] = 255;
                rgb[i][1] = 255 - colorQuanta*count;
                rgb[i][2] = rgb[i][1];
                count++;
            }
        }
        /*
        int max_blue_position = (int)Math.ceil((row_data.length/2) - 1);
        double stepSize = 1.0/(max_blue_position*1.0);
        
        // color the blues
        for (int i = 0; i <= max_blue_position; i++){
            rgb[i][0] = i * stepSize * 255;
            rgb[i][1] = rgb[i][0];
            rgb[i][2] = 255;
        }
        
        // color the reds
        for (int i = max_blue_position+1; i < row_data.length; i++){
            rgb[i][0] = 255;
            rgb[i][1] = 
            rgb[i][2] = 
        }
        
        // row_data must be in range [0,1]
        // 0, 1, 2 index for r g b
        //double[][] rgb = new double[row_data.length][3];
        stepSize = 1/(Math.floor(row_data.length/2)-1);
        for (int i = 0; i < row_data.length; i++){
            double r, g, b;
            
            if(row_data[i] < 0.5){
                r = i * stepSize * 255;
                g = r;
                b = 255; 
            } else if (row_data[i] > 0.5){
                r = 255;
                g = (1 - (i - Math.ceil(row_data.length/2))*stepSize) * 255;
                b = g;
            } else {
                r = 255;
                g = 255;
                b = 255;
            }
            
            rgb[i][0] = r;
            rgb[i][1] = g;
            rgb[i][2] = b;

        }
        */
        return rgb;
    }
    
    /*
    public void genLinearColorScale(Cell[] row_data){
        // 0, 1, 2 index for r g b
        double[][] rgb = new double[row_data.length][3];
        
        for (int i = 0; i < row_data.length; i++){
            double r, g, b;
            
            if(row_data[i].visualization_normval < 0.5){
                r = row_data[i].visualization_normval  * 2 * 255;
                g = r;
                b = 255; 
            } else if (row_data[i].visualization_normval > 0.5){
                r = 255;
                g = 255 * 2 * (1 - row_data[i].visualization_normval);
                b = g;
            } else {
                r = 255;
                g = 255;
                b = 255;
            }
            
            rgb[i][0] = r;
            rgb[i][1] = g;
            rgb[i][2] = b;
            
            row_data[i].setCellColor(rgb[i]);
            hist.add(row_data[i].visualization_normval, rgb[i]);
        }
        
        hist.normalizeHist();
    }
    */
    
    public String buildMapImage (int start, int end, String caller_id, int as_type) {
        return buildMapImage (start, end, -1, -1, caller_id, as_type);
    }
    
    /*
    public String buildMapImage (String path, int width, int height) {
        return buildMapImage (0, data.datacells.height-1, width, height, false);
    }
    */
    public String buildMapImage (int start, int end, int width, int height, String caller_id, int as_type) {
        
        int img_height = end - start + 1;
        int img_width = data.datacells.width;
        
        HeatmapData heatmapData = null;
        String imagename = System.currentTimeMillis() + caller_id;
        
        if (as_type == HeatmapData.TYPE_IMAGE) {
            
            BufferedImage fullImage = new BufferedImage(img_width, img_height, BufferedImage.TYPE_3BYTE_BGR);
            WritableRaster img_raster = fullImage.getRaster();
            
            int ho, bin;
            double[] rgb;
            for (int w=0; w<data.datacells.width; w++) {
                for (int h=start; h<=end; h++) {
                    ho = leaf_ordering.get(h);
                    bin = data.datacells.getBinNo(ho, w);
                    rgb = hist.rgb[bin];
                    img_raster.setSample(w, h-start, 0, rgb[0]);
                    img_raster.setSample(w, h-start, 1, rgb[1]);
                    img_raster.setSample(w, h-start, 2, rgb[2]);
                }
            }
            
            Image mapImage = fullImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage mapImageB = Utils.convertToBufferedImage(mapImage);
            heatmapData = new HeatmapData(mapImageB);
            
        } else if (as_type == HeatmapData.TYPE_ARRAY) {
            
            short[][][] raster = new short[img_width][img_height][3];
            
            int ho, bin;
            double[] rgb;
            for (int w=0; w<data.datacells.width; w++) {
                for (int h=start; h<=end; h++) {
                    ho = leaf_ordering.get(h);
                    bin = data.datacells.getBinNo(ho, w);
                    rgb = hist.rgb[bin];
                    raster[w][h-start][0] = (short)rgb[0];
                    raster[w][h-start][1] = (short)rgb[1];
                    raster[w][h-start][2] = (short)rgb[2];
                }
            }
            
            heatmapData = new HeatmapData(raster);
            
        } 
        
        current_rasters.put(imagename, heatmapData);
        
        return imagename;
    }
    
    public BufferedImage getRasterAsImage(String imagename) {
        HeatmapData mapData = current_rasters.get(imagename);
        if (mapData.type == HeatmapData.TYPE_IMAGE) {
            BufferedImage image = current_rasters.get(imagename).current_raster_as_image;
            current_rasters.remove(imagename);
            return image;
        } else {
            return null;
        }
    }
    
    public short[][][] getRasterAsArray(String imagename) {
        HeatmapData mapData = current_rasters.get(imagename);
        if (mapData.type == HeatmapData.TYPE_ARRAY) {
            short[][][] raster = current_rasters.get(imagename).current_raster_as_array;
            current_rasters.remove(imagename);
            return raster;
        } else {
            return null;
        }
    }
    
    /*
    public short[][][] getRaster() {
        return this.current_raster;
    }
    */
    
    /*
    public double[][][] getRaster() {
        double[][][] rgb_data = new double[current_raster.getWidth()][current_raster.getHeight()][3];
        for (int w = 0; w < current_raster.getWidth(); w++) {
            for (int h = 0; h < current_raster.getHeight(); h++) {
                rgb_data[w][h][0] = current_raster.getSample(w, h, 0);
                rgb_data[w][h][1] = current_raster.getSample(w, h, 1);
                rgb_data[w][h][2] = current_raster.getSample(w, h, 2);
            }
        }
        return rgb_data;
    }
    */
    
}
