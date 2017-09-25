/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author soumitag
 */
public class Sample implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public String samplename;
    public Integer num_replicates;
    public ArrayList <Integer> raw_data_column_numbers;
    public ArrayList <Integer> file_column_numbers;
    public ArrayList <String> column_headers; 
    public String timestamp;
    public long _timestamp;
    
    public Sample(String samplename){
        this.samplename = samplename;
        raw_data_column_numbers = new ArrayList <> ();
        file_column_numbers = new ArrayList <> ();
        column_headers = new ArrayList <> ();
    }
    
    public void setNumReplicates(int repnum){
        this.num_replicates = repnum; 
    }
    
    public void addRawDataColNumbers (ArrayList <Integer> colnumarr){   // in case of replicate assay design
        this.raw_data_column_numbers = colnumarr;        
    }
    
    public void addRawDataColNumber (int colnum){    // in case of independent assay design 
        raw_data_column_numbers.add(colnum);
    }
    
    public void addFileColNumbers (ArrayList <Integer> colnumarr){    // in case of replicate assay design 
        file_column_numbers = colnumarr;
    }
    
    public void addFileColNumber (int colnum){    // in case of independent assay design 
        file_column_numbers.add(colnum);
    }
    
    public void addSampleColHeaders(String colheader){
        column_headers.add(colheader);
    }
    
    public void addSampleColHeaders(ArrayList <String> colheader){
        this.column_headers = colheader;
    }
    
    public void setTimeStamp (String timestamp) {
        this.timestamp = timestamp;
        StringTokenizer st = new StringTokenizer(timestamp, ":");
        int days = Integer.parseInt(st.nextToken());
        int hours = Integer.parseInt(st.nextToken());
        int mins = Integer.parseInt(st.nextToken());
        int secs = Integer.parseInt(st.nextToken());
        this._timestamp = days*24*60*60 + hours*60*60 + mins*60 + secs;
    }
    
}
