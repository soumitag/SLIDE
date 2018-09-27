/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package params;

import graphics.layouts.DrillDownPanelLayout;
import graphics.layouts.ScrollViewLayout;
import graphics.layouts.VizualizationHomeLayout;
import java.io.Serializable;

/**
 *
 * @author soumita
 */
public class VisualizationParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public String leaf_ordering_strategy;
    public int nBins;
    public String bin_range_type;
    public float bin_range_start;
    public float bin_range_end;
    public String heatmap_color_scheme;
    public String row_label_type;
    public ScrollViewLayout detailed_view_map_layout;
    public DrillDownPanelLayout drill_down_layout;
    public VizualizationHomeLayout viz_layout;
    
    public VisualizationParams(int nCols, int visualization_type) {
        leaf_ordering_strategy = "0";
        nBins = 21;
        bin_range_type = "data_bins";
        this.bin_range_start = -1;
        this.bin_range_end = -1;
        heatmap_color_scheme = "blue_white_red";
        this.viz_layout = new VizualizationHomeLayout();
        double panel_lengths = this.viz_layout.getAvailableDetailedViewLength();
        this.detailed_view_map_layout = new ScrollViewLayout(panel_lengths, true, nCols, visualization_type);
        this.drill_down_layout = new DrillDownPanelLayout(this.viz_layout);
    }
    
    public void setLeafOrderingStrategy(String leaf_ordering_strategy) {
        this.leaf_ordering_strategy = leaf_ordering_strategy;
    }
    
    public void setNBins(int nBins) {
        this.nBins = nBins;
    }
    
    public void setBinRangeType(String bin_range_type) {
        this.bin_range_type = bin_range_type;
    }
    
    public void setBinRangeStart(float bin_range_start) {
        this.bin_range_start = bin_range_start;
    }
    
    public void setBinRangeEnd(float bin_range_end) {
        this.bin_range_end = bin_range_end;
    }
    
    public void setHeatmapColorScheme(String heatmap_color_scheme) {
        this.heatmap_color_scheme = heatmap_color_scheme;
    }
    
    public void setRowLabelType(String row_label_type) {
        this.row_label_type = row_label_type;
    }
    
    public void setDetailedViewMapLayout(ScrollViewLayout detailed_view_map_dims) {
        this.detailed_view_map_layout = detailed_view_map_dims;
    }
    
    public void setDrillDownPanelLayout(DrillDownPanelLayout drill_down_layout) {
        this.drill_down_layout = drill_down_layout;
    }
    
    public void setViewLayout(VizualizationHomeLayout viz_layout) {
        this.viz_layout = viz_layout;
    }
    
    public ScrollViewLayout getDetailedViewMapLayout() {
        return this.detailed_view_map_layout;
    }
    
     public DrillDownPanelLayout getDrillDownPanelLayout() {
        return this.drill_down_layout;
    }
    
    public VizualizationHomeLayout getViewLayout() {
        return this.viz_layout;
    }
    
    public int getBinRangeTypeIndex() {
        if (this.bin_range_type.equalsIgnoreCase("data_bins")) {
            return 0;
        } else if (this.bin_range_type.equalsIgnoreCase("symmetric_bins")) {
            return 1;
        } else if (this.bin_range_type.equalsIgnoreCase("start_end_bins")) {
            return 2;
        } else {
            return -1;
        }
    }
    
    public int getLeafOrderingStrategyIndex() {
        return Integer.parseInt(this.leaf_ordering_strategy);
    }
}

