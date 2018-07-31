/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Soumita
 */
public class Sorter {
    
    public class SortObject implements Comparable {
        public Float value;
        public int index;
        
        public SortObject (int index, float value) {
            this.index = index;
            this.value = value;
        }
        
        @Override
        public int compareTo(Object o) {
            return this.value.compareTo(((SortObject)o).value);
        }
    }
    
    SortObject[] sort_objects;
    
    public Sorter(float[] values) {
        sort_objects = new SortObject[values.length];
        for (int i=0; i<values.length; i++) {
            sort_objects[i] = new SortObject(i, values[i]);
        }
    }
    
    public int[] getSortOrder() {
        Arrays.sort(sort_objects, Collections.reverseOrder());
        int[] sort_order = new int[sort_objects.length];
        for (int i=0; i<sort_objects.length; i++) {
            sort_order[i] = sort_objects[i].index;
        }
        return sort_order;
    }
}
