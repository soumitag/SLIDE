/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author soumitag
 */
public class SampleMappings implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public HashMap <String, ArrayList<Integer>> sampleToColumnMap;
    public HashMap <String, ArrayList<String>> sampleNameToTimeMap;
    public HashMap <String, ArrayList<String>> timeToSampleNameMap;

    public ArrayList <String> sampleNames;
    public ArrayList <String> timeStamps;

    public SampleMappings() {
        
        sampleToColumnMap = new HashMap <String, ArrayList<Integer>>();
        sampleNameToTimeMap = new HashMap <String, ArrayList<String>>();
        timeToSampleNameMap = new HashMap <String, ArrayList<String>>();
        
        sampleNames = new ArrayList <String> ();
        timeStamps = new ArrayList <String> ();
        
    }

}
