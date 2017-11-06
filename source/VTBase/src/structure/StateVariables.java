/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Soumita
 */
public class StateVariables {

    public HashMap <String, Object> sv_map;
    
    public StateVariables() {
        sv_map = new HashMap <String, Object> ();
    }
    
    public void init(int nFeatures) {
        Stack dendrogram_history = new Stack();
        dendrogram_history.push(new Integer[]{null,null,null});
        sv_map.put("dendrogram_history", dendrogram_history);
        sv_map.put("detailed_view_start", 0);
        sv_map.put("detailed_view_end", Math.min(37, nFeatures-1));
    }
    
    public int getDetailedViewStart() {
        return (int)sv_map.get("detailed_view_start");
    }
    
    public int getDetailedViewEnd() {
        return (int)sv_map.get("detailed_view_end");
    }
    
    public void setDetailedViewStart(int start) {
        sv_map.put("detailed_view_start", start);
    }
    
    public void setDetailedViewEnd(int end) {
        sv_map.put("detailed_view_end", end);
    }
    
    public Integer[] peekDendrogramHistory() {
        return (Integer[])((Stack)sv_map.get("dendrogram_history")).peek();
    }
    
    public void pushDendrogramHistory(Integer[] new_history) {
        ((Stack)sv_map.get("dendrogram_history")).push(new_history);
    }
    
    public void popDendrogramHistory() {
        Stack dendrogram_history = ((Stack)sv_map.get("dendrogram_history"));
        if (dendrogram_history.size() > 1) {
            dendrogram_history.pop();
        }
    }
    
    public Integer getStartNodeID() {
        Integer[] dend_hist = peekDendrogramHistory();
        return dend_hist[0];
    }
    
    public Integer getDendrogramStart() {
        Integer[] dend_hist = peekDendrogramHistory();
        return dend_hist[1];
    }
    
    public Integer getDendrogramEnd() {
        Integer[] dend_hist = peekDendrogramHistory();
        return dend_hist[2];
    }
}
