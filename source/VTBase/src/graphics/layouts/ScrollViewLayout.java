/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.layouts;

import java.io.Serializable;
import structure.AnalysisContainer;

/**
 *
 * @author Soumita
 */
public class ScrollViewLayout implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int RESOLUTION_PROFILE_8 = 8;
    public static final int RESOLUTION_PROFILE_12 = 12;
    public static final int RESOLUTION_PROFILE_16 = 16;
    public static final int RESOLUTION_PROFILE_20 = 20;
    public static final int RESOLUTION_PROFILE_24 = 24;
    
    public static final double COLUMN_HEADER_HEIGHT = 95.0;
    public static final double MINIMUM_MAP_WIDTH = 500.0;
    
    public static final double FEATURE_LABEL_WIDTH_GENE_LEVEL = 200.0;
    public static final double FEATURE_LABEL_WIDTH_GROUP_LEVEL = 350.0;
    
    public double DISPLAY_HEIGHT;
    public double MAP_HEIGHT;
    public double MAP_WIDTH;
    public double DETAILED_VIEW_IFRAME_WIDTH;
    public double CELL_HEIGHT;
    public double CELL_WIDTH;
    public int NUM_DISPLAY_FEATURES;
    public double FEATURE_LABEL_FONT_SIZE;
    public double SAMPLE_LABEL_FONT_SIZE;
    public boolean USE_SQUARE_CELLS;
    public double BORDER_STROKE_WIDTH = 0.5;
    public double SEARCH_TAG_DIV_HEIGHT;
    
    public ScrollViewLayout(double display_height, boolean use_square_cells, int nCols, int visualization_type) {
        this(ScrollViewLayout.RESOLUTION_PROFILE_16, display_height, use_square_cells, nCols, visualization_type);
    }
    
    public ScrollViewLayout(int resolution_profile, double display_height, boolean use_square_cells, int nCols, int visualization_type) {
        
        this.USE_SQUARE_CELLS = use_square_cells;
        this.DISPLAY_HEIGHT = display_height;
        
        switch (resolution_profile) {
            case RESOLUTION_PROFILE_20:
                this.CELL_HEIGHT = 20.0;
                this.FEATURE_LABEL_FONT_SIZE = 12.0;
                this.SAMPLE_LABEL_FONT_SIZE = 12.0;
                this.BORDER_STROKE_WIDTH = 0.5;
                break;
            case RESOLUTION_PROFILE_16:
                this.CELL_HEIGHT = 16.0;
                this.FEATURE_LABEL_FONT_SIZE = 10.0;
                this.SAMPLE_LABEL_FONT_SIZE = 10.0;
                this.BORDER_STROKE_WIDTH = 0.5;
                break;
            case RESOLUTION_PROFILE_12:
                this.CELL_HEIGHT = 12.0;
                this.FEATURE_LABEL_FONT_SIZE = 8.0;
                this.SAMPLE_LABEL_FONT_SIZE = 8.0;
                this.BORDER_STROKE_WIDTH = 0.5;
                break;
            case RESOLUTION_PROFILE_8:
                this.CELL_HEIGHT = 8.0;
                this.FEATURE_LABEL_FONT_SIZE = 6.0;
                this.SAMPLE_LABEL_FONT_SIZE = 6.0;
                this.BORDER_STROKE_WIDTH = 0.25;
                break;
            case RESOLUTION_PROFILE_24:
                this.CELL_HEIGHT = 24.0;
                this.FEATURE_LABEL_FONT_SIZE = 14.0;
                this.SAMPLE_LABEL_FONT_SIZE = 14.0;
                this.BORDER_STROKE_WIDTH = 0.5;
                break;
            default:
                this.CELL_HEIGHT = 20.0;
                this.FEATURE_LABEL_FONT_SIZE = 12.0;
                this.SAMPLE_LABEL_FONT_SIZE = 12.0;
                this.BORDER_STROKE_WIDTH = 0.5;
                break;
        }
        
        this.MAP_HEIGHT = this.DISPLAY_HEIGHT - ScrollViewLayout.COLUMN_HEADER_HEIGHT;
        this.NUM_DISPLAY_FEATURES = (int)Math.floor(this.MAP_HEIGHT/(this.CELL_HEIGHT + this.BORDER_STROKE_WIDTH));
        
        if (this.USE_SQUARE_CELLS) {
            this.CELL_WIDTH = this.CELL_HEIGHT;
        } else {
            this.CELL_WIDTH = (int)Math.max(20.0, Math.floor(ScrollViewLayout.MINIMUM_MAP_WIDTH/nCols));
        }
        
        this.MAP_WIDTH = this.CELL_WIDTH*nCols;
        this.DETAILED_VIEW_IFRAME_WIDTH = Math.min(500.0, this.MAP_WIDTH);
        this.SEARCH_TAG_DIV_HEIGHT = (this.NUM_DISPLAY_FEATURES+1)*this.CELL_HEIGHT;
    }
    
    public int getEnd(int start, int nFeaturesInDataset) {
        int end = start + this.NUM_DISPLAY_FEATURES;
        end = Math.min(end, nFeaturesInDataset-1);
        return end;
    }
    
    public final double getFeatureLabelWidth (int visualization_type) {
        switch (visualization_type) {
            case AnalysisContainer.GENE_LEVEL_VISUALIZATION:
                return ScrollViewLayout.FEATURE_LABEL_WIDTH_GENE_LEVEL;
            case AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION:
            case AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION:
                return ScrollViewLayout.FEATURE_LABEL_WIDTH_GROUP_LEVEL;
            default:
                return ScrollViewLayout.FEATURE_LABEL_WIDTH_GENE_LEVEL;
        }
    }
    
    public int getResolutionProfileType() {
        switch ((int)this.CELL_WIDTH) {
            case RESOLUTION_PROFILE_8:
                return 0;
            case RESOLUTION_PROFILE_12:
                return 1;
            case RESOLUTION_PROFILE_16:
                return 2;
            case RESOLUTION_PROFILE_20:
                return 3;
            case RESOLUTION_PROFILE_24:
                return 4;
            default:
                return -1;
        }
    }
}
