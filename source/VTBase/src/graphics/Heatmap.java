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
    
    public void genColorData(String colormap){
        
        double[] normalized_bincenters = new double[hist.nBins];
        for(int i = 0; i < hist.nBins; i++) {
            normalized_bincenters[i] = ((hist.bincenters[i] - hist.MIN_VAL)/(hist.MAX_VAL - hist.MIN_VAL));
        }
        if(colormap.equalsIgnoreCase("green_black_red")) {
            hist.rgb = genLinearColorScale_GreenRed(normalized_bincenters);
        } else if(colormap.equalsIgnoreCase("blue_white_red")) {
            hist.rgb = genLinearColorScale_BlueRed(normalized_bincenters);
        } else if(colormap.equalsIgnoreCase("blue_black_yellow")) {
            hist.rgb = genLinearColorScale_BlueYellow(normalized_bincenters);
        } else if(colormap.equalsIgnoreCase("blue_white_maroon")) {
            hist.rgb = genLinearColorScale_BlueMaroon(normalized_bincenters);
        }
    }
    
    public void assignBinsToRows() {
        for(int i = 0; i < data.datacells.height; i++) {
            for(int j = 0; j < data.datacells.width; j++) {
                int binno = hist.getBinNum_Int(data.datacells.dataval[i][j]);
                data.datacells.setBinNo(binno, i, j);
                hist.addToBin_Int(binno);
            }
        }
    }
    
    public double[][] genLinearColorScale_GreenRed(double[] row_data){
        
        double[][] rgb = new double[row_data.length][3];
        
        int half_length = (int)Math.ceil(row_data.length/2);
        
        if((row_data.length%2) == 0) {
            
            // Even case: two central columns will have same values
            
            float[] g_left_to_center = getLinearInterpolations(255, 0, half_length);
            float[] r_center_to_right = getLinearInterpolations(0, 255, half_length);
            
            int count = 0;
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = 0;
                rgb[count][1] = g_left_to_center[i];
                rgb[count][2] = 0;
                count++;
            }
            
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = r_center_to_right[i];
                rgb[count][1] = 0;
                rgb[count][2] = 0;
                count++;
            }
            
        } else {
            
            // Odd case: one central column
            
            float[] g_left_to_center = getLinearInterpolations(255, 0, half_length+1);
            float[] r_center_to_right = getLinearInterpolations(0, 255, half_length+1);
            
            int count = 0;
            for (int i = 0; i <= half_length; i++) {
                rgb[count][0] = 0;
                rgb[count][1] = g_left_to_center[i];
                rgb[count][2] = 0;
                count++;
            }
            
            for (int i = 1; i <= half_length; i++) {
                rgb[count][0] = r_center_to_right[i];
                rgb[count][1] = 0;
                rgb[count][2] = 0;
                count++;
            }
            
        }
        
        return rgb;
    }
    
    public double[][] genLinearColorScale_BlueRed(double[] row_data){
        
        double[][] rgb = new double[row_data.length][3];
        
        int half_length = (int)Math.ceil(row_data.length/2);
        
        if((row_data.length%2) == 0) {
            
            // Even case: two central columns will have same values
            
            float[] rg_left_to_center = getLinearInterpolations(0, 255, half_length);
            float[] gb_center_to_right = getLinearInterpolations(255, 0, half_length);
            
            int count = 0;
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = rg_left_to_center[i];
                rgb[count][1] = rg_left_to_center[i];
                rgb[count][2] = 255;
                count++;
            }
            
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = 255;
                rgb[count][1] = gb_center_to_right[i];
                rgb[count][2] = gb_center_to_right[i];
                count++;
            }
            
        } else {
            
            // Odd case: one central column
            
            float[] rg_left_to_center = getLinearInterpolations(0, 255, half_length+1);
            float[] gb_center_to_right = getLinearInterpolations(255, 0, half_length+1);
            
            int count = 0;
            for (int i = 0; i <= half_length; i++) {
                rgb[count][0] = rg_left_to_center[i];
                rgb[count][1] = rg_left_to_center[i];
                rgb[count][2] = 255;
                count++;
            }
            
            for (int i = 1; i <= half_length; i++) {
                rgb[count][0] = 255;
                rgb[count][1] = gb_center_to_right[i];
                rgb[count][2] = gb_center_to_right[i];
                count++;
            }
            
        }
        
        return rgb;
    }
    
    public double[][] genLinearColorScale_BlueYellow(double[] row_data){
        
        double[][] rgb = new double[row_data.length][3];
        
        int half_length = (int)Math.ceil(row_data.length/2);
            
        
        
        if((row_data.length%2) == 0) {
            
            // Even case: two central columns will have same values
            
            float[] b_left_to_center = getLinearInterpolations(255, 0, half_length);
            float[] rg_center_to_right = getLinearInterpolations(0, 255, half_length);
            
            int count = 0;
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = 0;
                rgb[count][1] = 0;
                rgb[count][2] = b_left_to_center[i];
                count++;
            }
            
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = rg_center_to_right[i];
                rgb[count][1] = rg_center_to_right[i];
                rgb[count][2] = 0;
                count++;
            }
            
        } else {
            
            float[] b_left_to_center = getLinearInterpolations(255, 0, half_length + 1);
            float[] rg_center_to_right = getLinearInterpolations(0, 255, half_length + 1);
            
            // Odd case: one central column
            
            int count = 0;
            for (int i = 0; i <= half_length; i++) {
                rgb[count][0] = 0;
                rgb[count][1] = 0;
                rgb[count][2] = b_left_to_center[i];
                count++;
            }
            
            for (int i = 1; i <= half_length; i++) {
                rgb[count][0] = rg_center_to_right[i];
                rgb[count][1] = rg_center_to_right[i];
                rgb[count][2] = 0;
                count++;
            }
            
        }
        
        return rgb;
    }
    
    public double[][] genLinearColorScale_BlueMaroon(double[] row_data){
        
        double[][] rgb = new double[row_data.length][3];
        
        int half_length = (int)Math.ceil(row_data.length/2);
        
        if((row_data.length%2) == 0) {
            
            // Even case: two central columns will have same values
            
            float[] r_left_to_center = getLinearInterpolations(7, 255, half_length);
            float[] g_left_to_center = getLinearInterpolations(52, 255, half_length);
            float[] b_left_to_center = getLinearInterpolations(103, 255, half_length);
            
            float[] r_center_to_right = getLinearInterpolations(255, 103, half_length);
            float[] g_center_to_right = getLinearInterpolations(255, 0, half_length);
            float[] b_center_to_right = getLinearInterpolations(255, 31, half_length);
            
            int count = 0;
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = r_left_to_center[i];
                rgb[count][1] = g_left_to_center[i];
                rgb[count][2] = b_left_to_center[i];
                count++;
            }
            
            for (int i = 0; i < half_length; i++) {
                rgb[count][0] = r_center_to_right[i];
                rgb[count][1] = g_center_to_right[i];
                rgb[count][2] = b_center_to_right[i];
                count++;
            }
            
        } else {
            
            float[] r_left_to_center = getLinearInterpolations(5, 247, half_length+1);
            float[] g_left_to_center = getLinearInterpolations(48, 247, half_length+1);
            float[] b_left_to_center = getLinearInterpolations(97, 247, half_length+1);
            
            float[] r_center_to_right = getLinearInterpolations(247, 103, half_length+1);
            float[] g_center_to_right = getLinearInterpolations(247, 0, half_length+1);
            float[] b_center_to_right = getLinearInterpolations(247, 31, half_length+1);
            
            // Odd case: one central column
            
            int count = 0;
            for (int i = 0; i <= half_length; i++) {
                rgb[count][0] = r_left_to_center[i];
                rgb[count][1] = g_left_to_center[i];
                rgb[count][2] = b_left_to_center[i];
                count++;
            }
            
            for (int i = 1; i <= half_length; i++) {
                rgb[count][0] = r_center_to_right[i];
                rgb[count][1] = g_center_to_right[i];
                rgb[count][2] = b_center_to_right[i];
                count++;
            }
            
        }
        
        return rgb;
    }
    
    public double[][] genLinearColorScale_BlueRed_O(double[] row_data){
        
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
        
        return rgb;
    }
    
    public float[] getLinearInterpolations (float start, float end, int n_values) {
        
        float[] interp_values = new float[n_values];
        float stepSize = (end - start)/(float)(n_values-1);
        for (int i=0; i<n_values; i++) {
            interp_values[i] = start + i*stepSize;
        }
        return interp_values;
    }
    
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
