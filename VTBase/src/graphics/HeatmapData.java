/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.image.BufferedImage;

/**
 *
 * @author Soumita
 */
public class HeatmapData {
    
    public static int TYPE_IMAGE = 0;
    public static int TYPE_ARRAY = 1;
    
    public int type;
    public BufferedImage current_raster_as_image;
    public short[][][] current_raster_as_array;
    
    public HeatmapData(BufferedImage current_raster_as_image) {
        this.current_raster_as_image = current_raster_as_image;
        this.type = HeatmapData.TYPE_IMAGE;
    }
    
    public HeatmapData(short[][][] current_raster_as_array) {
        this.current_raster_as_array = current_raster_as_array;
        this.type = HeatmapData.TYPE_ARRAY;
    }
    
}
