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
public class VizualizationHomeLayout implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int VISUALIZATION_PANE_LENGTH_0 = 1000;
    public static final int VISUALIZATION_PANE_LENGTH_1 = 1350;
    public static final int VISUALIZATION_PANE_LENGTH_2 = 1700;
    public static final int VISUALIZATION_PANE_LENGTH_3 = 2050;
    
    public static final int RIBBON_HEIGHT = 50;
    public static final int BOTTOM_BUFFER = 100;
    
    public int VISUALIZATION_PANE_LENGTH;
    
    public VizualizationHomeLayout() {
        this.VISUALIZATION_PANE_LENGTH = VizualizationHomeLayout.VISUALIZATION_PANE_LENGTH_2;
    }
    
    public VizualizationHomeLayout(int L) {
        this.VISUALIZATION_PANE_LENGTH = L;
    }
    
    public double getAvailableDetailedViewLength() {
        return this.VISUALIZATION_PANE_LENGTH - (VizualizationHomeLayout.RIBBON_HEIGHT + VizualizationHomeLayout.BOTTOM_BUFFER);
    }
    
    public double getAvailableDrillDownPanelLength() {
        return this.VISUALIZATION_PANE_LENGTH - (VizualizationHomeLayout.RIBBON_HEIGHT);
    }
    
    public int getVisualizationPanelLengthType() {
        switch (this.VISUALIZATION_PANE_LENGTH) {
            case VISUALIZATION_PANE_LENGTH_0:
                return 0;
            case VISUALIZATION_PANE_LENGTH_1:
                return 1;
            case VISUALIZATION_PANE_LENGTH_2:
                return 2;
            case VISUALIZATION_PANE_LENGTH_3:
                return 3;
            default:
                return -1;
        }
    }
}
