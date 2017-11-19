/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package params;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author soumita
 */
public class TransformationParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public int replicate_handling;
    public String clipping_type;
    public float clip_min;
    public float clip_max;
    public boolean log_transform;
    public int column_normalization;
    public int row_normalization;
    public String group_by;
    
    public TransformationParams() {
        replicate_handling = 0;
        clipping_type = "none";
        clip_min = Float.NEGATIVE_INFINITY;
        clip_max = Float.POSITIVE_INFINITY;
        log_transform = false;
        column_normalization = 0;
        row_normalization = 0;
        group_by = "sample";
    }
    
    public void setReplicateHandling(int replicate_handling) {
        this.replicate_handling = replicate_handling;
    }
    
    public void setClippingType(String clipping_type) {
        this.clipping_type = clipping_type;
    }
    
    public void setClipMin(float clip_min) {
        this.clip_min = clip_min;
    }
    
    public void setClipMax(float clip_max) {
        this.clip_max = clip_max;
    }
    
    public void setLogTransform(boolean log_transform) {
        this.log_transform = log_transform;
    }
    
    public void setColumnNormalization(int column_normalization) {
        this.column_normalization = column_normalization;
    }
    
    public void setRowNormalization(int row_normalization) {
        this.row_normalization = row_normalization;
    }
    
    public void setGroupBy(String group_by) {
        this.group_by = group_by;
    }
    
    public int getGroupByIndex() {
        if (this.group_by.equalsIgnoreCase("sample")) {
            return 0;
        } else if (this.group_by.equalsIgnoreCase("time")) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public int getClippingType() {
        if (this.clipping_type.equalsIgnoreCase("none")) {
            return 0;
        } else if (this.clipping_type.equalsIgnoreCase("absv")) {
            return 1;
        } else if (this.clipping_type.equalsIgnoreCase("ptile")) {
            return 2;
        } else {
            return -1;
        }
    }
    
    /*
    public boolean equals(TransformationParams that) {
        
        return (this.replicate_handling == that.replicate_handling && 
                this.clipping_type.equals(that.clipping_type) &&
                Math.abs(this.clip_max - that.clip_max) < 0.00000001 &&
                Math.abs(this.clip_min - that.clip_min) < 0.00000001 &&
                this.log_transform == that.log_transform &&
                this.column_normalization == that.column_normalization &&
                this.row_normalization == that.row_normalization &&
                this.group_by.equals(that.group_by)
        );
    
    }
    */
    
    public String getHashString() {
        
        //DecimalFormat df = new DecimalFormat();
        //df.setMaximumFractionDigits(10);
        
        return replicate_handling + "_" +
                clipping_type + "_" +
                clip_max + "_" +
                clip_min + "_" +
                log_transform + "_" +
                column_normalization + "_" +
                row_normalization + "_" +
                group_by;
    }
}