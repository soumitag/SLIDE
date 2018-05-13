/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import vtbase.DataParsingException;

/**
 *
 * @author Soumita
 */
public class FileHandler {
    
    public static String[][] loadDelimData (String inFile, 
                                            String delim, 
                                            boolean hasHeader, 
                                            int maxRows) 
    throws DataParsingException, IOException{
             
        BufferedReader br = null;
        String line;
        
        ArrayList <String []> dataVec = new ArrayList <> ();

        try {
        
            br = new BufferedReader(new FileReader(inFile));

            int count = 0;
            String[] lineData = null;
            boolean isFirst = true;
            int line_length = -1;
            while ((line = br.readLine()) != null) {

                if (isFirst & hasHeader) {
                    isFirst = false;
                } else {
                    if(!delim.equals("|")){
                        lineData = line.split(delim, -1);
                    } else {
                        lineData = line.split("\\" + delim, -1);
                    }
                    if (count > 0) {
                        if (line_length != lineData.length) {
                            br.close();
                            throw new DataParsingException(
                                    "Error while reading data: Rows " + count + " and " + (count + 1)
                                    + " have different number of columns. All rows must have the same number of columns. Please verify that the selected delimiter is correct.");
                        }
                    }
                    line_length = lineData.length;
                    dataVec.add(lineData);
                    count++;
                }

                if (count == maxRows) {
                    break;
                }
            }

            br.close();

            String[][] data = new String[dataVec.size()][lineData.length];
            for (int i = 0; i < dataVec.size(); i++) {
                for (int j = 0; j < lineData.length; j++) {
                    data[i][j] = dataVec.get(i)[j];
                }
            }

            return data;

        } finally {
            if (br != null) {
                br.close();
            }
        }
        
    }
    
    public static String[][] loadDelimData (String inFile, String delim, boolean hasHeader) 
    throws DataParsingException, IOException {
             
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        String line;
        
        String[][] data;
        String[] lineData = null;
                
        try {

            int count = 1;
            br1 = new BufferedReader(new FileReader(inFile));
            line = br1.readLine();
            if(!delim.equals("|")){
                lineData = line.split(delim, -1);
            } else {
                lineData = line.split("\\" + delim, -1);
            }
            
            while ((line = br1.readLine()) != null) {
                count++;
            }
            br1.close();
            
            if (hasHeader) {
                data = new String[count-1][lineData.length];
            } else {
                data = new String[count][lineData.length];
            }
            
            count = 0;
            br2 = new BufferedReader(new FileReader(inFile));
            boolean isFirst = true;
            int line_length = -1;
            while ((line = br2.readLine()) != null) {

                if (isFirst & hasHeader) {
                    isFirst = false;
                } else {
                    if(!delim.equals("|")){
                        lineData = line.split(delim, -1);
                    } else {
                        lineData = line.split("\\" + delim, -1);
                    }
                    if (count > 0) {
                        if (line_length != lineData.length) {
                            br2.close();
                            throw new DataParsingException(
                                    "Error while reading data: Rows " + count + " and " + (count + 1)
                                    + " have different number of columns. All rows must have the same number of columns. Please verify that the selected delimiter is correct.");
                        }
                    }
                    line_length = lineData.length;
                    data[count++] = lineData;
                }
            }
            br2.close();
            
            return data;
        
        } finally {
            if (br1 != null) {
                br1.close();
            }
            if (br2 != null) {
                br2.close();
            }
        }
        
    }
    
    public static int[] getFileDimensions (String inFile, String delim) {
        
        boolean isFirst = true;
        int[] height_width = new int[2];
        String line;
        
        BufferedReader br = null;
        
        try {
            
            br = new BufferedReader(new FileReader(inFile));
        
            while ((line = br.readLine()) != null) {
                if (isFirst) {
                    if(!delim.equals("|")){
                        height_width[1] = (line.split(delim, -1)).length;
                    } else {
                        height_width[1] = (line.split("\\" + delim, -1)).length;
                    }
                    isFirst = false;
                }
                height_width[0]++;
            }
            
            return height_width;
            
        } catch (Exception e) {
            
            System.out.println("Error reading input data:");
            System.out.println(e);
            return null;
            
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch(IOException ioe) {
                System.out.println("Error reading input data:");
                System.out.println(ioe);
                return null;
            }
            
        }
        
    }
    
    public static String[] getFileHeader (String inFile, String delim) {
        
        String[] headers = null;
        
        BufferedReader br = null;
        
        try {
            
            br = new BufferedReader(new FileReader(inFile));
            String line = br.readLine();
            if (line != null) {
                if(!delim.equals("|")){
                    headers = line.split(delim, -1);
                } else {
                    headers = line.split("\\" + delim, -1);
                }
            }
        
            return headers;
            
        } catch (Exception e) {
            
            System.out.println("Error reading input data:");
            System.out.println(e);
            return null;
            
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch(IOException ioe) {
                System.out.println("Error reading input data:");
                System.out.println(ioe);
                return null;
            }
        }
        
    }
    
    public static double[][] loadDoubleDelimData (String inFile, String delim, boolean hasHeader) 
    throws DataParsingException, IOException {
        String[][] data = loadDelimData (inFile, delim, hasHeader);
        double[][] doubleData = new double[data.length][data[0].length];
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<data[0].length; j++) {
                doubleData[i][j] = Double.parseDouble(data[i][j]);
            }
        }
        return doubleData;
    }
    
}
