/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import structure.SampleMappings;
import vtbase.DataParsingException;

/**
 *
 * @author soumitag
 */
public class UserInputParser {
 
    public SampleMappings sampleMappings;
    
    public String parseSampleMappingsFile (
            String metafilename, boolean isTimeSeries, String[] datacol_headers
    ) throws DataParsingException {
        
        BufferedReader br = null;
        String line;
        
        sampleMappings = new SampleMappings();
        
        try {

            br = new BufferedReader(new FileReader(metafilename));

            String[] lineData = null;
            
            while ((line = br.readLine()) != null) {

                if (line.startsWith("#") || line.equals("")) {
                    
                    continue;
                    
                } else {
                    
                    lineData = line.split("\t");
                    
                    if (isTimeSeries) {
                        
                        if (lineData.length < 3) {
                            
                            String msg = "Error while reading sample mapping file. Time-series sample mappings must have at least three columns";
                            throw new DataParsingException(msg);
                            
                        } else {
                            
                            String datacol_header = lineData[0].trim();
                            String sample_name = lineData[1].trim();
                            String timestamp = lineData[2].trim();
                            
                            String unique_sample_name = sample_name + ", " + timestamp;
                            
                            if (!sampleMappings.sampleNames.contains(unique_sample_name)) {
                                sampleMappings.sampleNames.add(unique_sample_name);
                            }
                            
                            if (!sampleMappings.timeStamps.contains(timestamp)) {
                                sampleMappings.timeStamps.add(timestamp);
                            }
                            
                            if (sampleMappings.sampleNameToTimeMap.containsKey(unique_sample_name)) {
                                ArrayList <String> times = sampleMappings.sampleNameToTimeMap.get(unique_sample_name);
                                if (!times.contains(timestamp)) {
                                    times.add(timestamp);
                                }
                            } else {
                                ArrayList <String> times = new ArrayList <String> ();
                                times.add(timestamp);
                                sampleMappings.sampleNameToTimeMap.put(unique_sample_name, times);
                            }
                            
                            if (sampleMappings.timeToSampleNameMap.containsKey(timestamp)) {
                                ArrayList <String> names = sampleMappings.timeToSampleNameMap.get(timestamp);
                                if (!names.contains(unique_sample_name)) {
                                    names.add(unique_sample_name);
                                }
                            } else {
                                ArrayList <String> names = new ArrayList <String> ();
                                names.add(unique_sample_name);
                                sampleMappings.timeToSampleNameMap.put(timestamp, names);
                            }
                            
                            int datacol_number = -1;
                            for (int i=0; i<datacol_headers.length; i++) {
                                if (datacol_headers[i].equals(datacol_header)) {
                                    datacol_number = i;
                                    break;
                                }
                            }
                            if (datacol_number == -1) {
                                String msg = "Error while reading sample mapping file. Column names provided must exactly match column headers in data file. No match found for " + datacol_header + " in data file.";
                                throw new DataParsingException(msg);
                            }
                            
                            if (sampleMappings.sampleToColumnMap.containsKey(unique_sample_name)) {
                                ArrayList <Integer> col_ids = sampleMappings.sampleToColumnMap.get(unique_sample_name);
                                if (!col_ids.contains(datacol_number)) {
                                    col_ids.add(datacol_number);
                                }
                            } else {
                                ArrayList <Integer> col_ids = new ArrayList <Integer> ();
                                col_ids.add(datacol_number);
                                sampleMappings.sampleToColumnMap.put(unique_sample_name, col_ids);
                            }
                            
                        }
                        
                    } else {
                        
                        if (lineData.length < 2) {
                            
                            String msg = "Error while reading sample mapping file. Sample mappings must have at least two columns";
                            throw new DataParsingException(msg);
                            
                        } else {
                            
                            String datacol_header = lineData[0].trim();
                            String sample_name = lineData[1].trim();
                            
                            if (!sampleMappings.sampleNames.contains(sample_name)) {
                                sampleMappings.sampleNames.add(sample_name);
                            }
                            
                            int datacol_number = -1;
                            for (int i=0; i<datacol_headers.length; i++) {
                                if (datacol_headers[i].equals(datacol_header)) {
                                    datacol_number = i;
                                    break;
                                }
                            }
                            if (datacol_number == -1) {
                                String msg = "Error while reading sample mapping file. Column names provided must exactly match column headers in data file. No match found for " + datacol_header + " in data file.";
                                throw new DataParsingException(msg);
                            }
                            
                            if (sampleMappings.sampleToColumnMap.containsKey(sample_name)) {
                                ArrayList <Integer> col_ids = sampleMappings.sampleToColumnMap.get(sample_name);
                                if (!col_ids.contains(datacol_number)) {
                                    col_ids.add(datacol_number);
                                }
                            } else {
                                ArrayList <Integer> col_ids = new ArrayList <Integer> ();
                                col_ids.add(datacol_number);
                                sampleMappings.sampleToColumnMap.put(sample_name, col_ids);
                            }
                            
                        }
                    }
                }
                
            }
            
        } catch (DataParsingException e) {
            
            throw e;
            
        } catch (Exception e) {
            
            System.out.println("Error reading sample mapping file");
            System.out.println(e);
            String msg = "Error while reading sample mapping file.";
            throw new DataParsingException(msg);
        }
        
        return "done";
        
    }
    
    public SampleMappings getMappings() {
        return this.sampleMappings;
    }
    
}
