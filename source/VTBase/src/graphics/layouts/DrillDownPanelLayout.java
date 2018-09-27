/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.layouts;

import java.io.Serializable;

/**
 *
 * @author Soumita
 */
public class DrillDownPanelLayout implements Serializable {
 
    private static final long serialVersionUID = 1L;
    
    public double HDI_FRAME_HEIGHT;
    public double GLOBAL_VIEW_TAB_HEIGHT;
    public double SEARCH_HEADER_HEIGHT;
    public double HEATMAP_PANEL_HEIGHT;
    public double GLOBAL_VIEW_FIG_HEIGHT;
    
    public double DRILLDOWN_PANEL_HEIGHT;
    public double DENDROGRAM_PANEL_HEIGHT;
    public double DDOWN_HEATMAP_PANEL_HEIGHT;
    public double DDOWN_SEARCH_RESULT_PANEL_HEIGHT;
    public double DDOWN_FEAT_LABEL_PANEL_HEIGHT;
    public double DENDROGRAM_VIEW_FIG_HEIGHT;
    
    public double DRILL_DOWN_PANEL_TOP_BUFFER;
    public double DRILL_DOWN_PANEL_BOTTOM_BUFFER;
    
    public DrillDownPanelLayout(VizualizationHomeLayout viz_layout) {
     
        double drill_down_panel_length = viz_layout.getAvailableDrillDownPanelLength();
        
        this.SEARCH_HEADER_HEIGHT = 55;
        this.HDI_FRAME_HEIGHT = drill_down_panel_length;
        this.GLOBAL_VIEW_TAB_HEIGHT = drill_down_panel_length - 10;
        this.HEATMAP_PANEL_HEIGHT = this.GLOBAL_VIEW_TAB_HEIGHT - this.SEARCH_HEADER_HEIGHT;
        this.GLOBAL_VIEW_FIG_HEIGHT = this.HEATMAP_PANEL_HEIGHT - 20.0 - 5.0;
        
        this.DRILL_DOWN_PANEL_TOP_BUFFER = 20;
        this.DRILL_DOWN_PANEL_BOTTOM_BUFFER = 0;
        
        this.DRILLDOWN_PANEL_HEIGHT = drill_down_panel_length - 10;
        this.DENDROGRAM_PANEL_HEIGHT = this.DRILLDOWN_PANEL_HEIGHT - this.SEARCH_HEADER_HEIGHT;
        this.DDOWN_HEATMAP_PANEL_HEIGHT = this.DENDROGRAM_PANEL_HEIGHT;
        this.DDOWN_SEARCH_RESULT_PANEL_HEIGHT = this.DENDROGRAM_PANEL_HEIGHT;
        this.DDOWN_FEAT_LABEL_PANEL_HEIGHT = this.DENDROGRAM_PANEL_HEIGHT;
        this.DENDROGRAM_VIEW_FIG_HEIGHT = this.DENDROGRAM_PANEL_HEIGHT - (this.DRILL_DOWN_PANEL_TOP_BUFFER + this.DRILL_DOWN_PANEL_BOTTOM_BUFFER);

    }
    
}
