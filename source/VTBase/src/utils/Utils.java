package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.text.DecimalFormat;

import java.io.StringWriter;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;

import java.util.regex.Pattern;
import structure.DataCells;
import structure.DataMask;

public class Utils { 

    public String logFilename;
    public boolean debugModeOn;
    public boolean loggingOn;
    public String tempSavePath;
    
    public Utils() {
        tempSavePath = "";
    }
    
    public Utils(String logFilename, String tempSavePath, boolean debugModeOn, boolean loggingOn) {
        this.logFilename = logFilename;
        this.tempSavePath = tempSavePath;
        this.debugModeOn = debugModeOn;
        this.loggingOn = loggingOn;
        
        try {
            File f = new File(this.tempSavePath);
            if (!f.exists() || !f.isDirectory()) {
                f.mkdir();
            }
        } catch (Exception e) {
            this.tempSavePath = "";
            System.out.println(e);
        }
    }
    
    public static String getDelimiter(String delimval) {
        
        String fileDelimiter = "";
        if(delimval.equals("commaS")){
            fileDelimiter = ",";
        } else if (delimval.equals("tabS")) {
            fileDelimiter = "\t";
        } else if (delimval.equals("spaceS")){
            fileDelimiter = " ";
        } else if (delimval.equals("semiS")) {
            fileDelimiter = ";";
        } else if (delimval.equals("hyphenS")) {
            fileDelimiter = "-";
        } else if (delimval.equals("pipeS")) {
            fileDelimiter = "|";
        }
        
        return fileDelimiter;
    }
    
    /*
    public double[] getDataBB(Point[] data) {
        
        double min_x = data[0].x;
        double min_y = data[0].y;
        double max_x = data[0].x;
        double max_y = data[0].y;
        
        for(int i=1; i<data.length; i++) {
            
            if(data[i].x < min_x) {
                min_x = data[i].x;
            }  
                
            if(data[i].y < min_y) {
                min_y = data[i].y;
            }  
            
            if(data[i].x > max_x) {
                max_x = data[i].x;
            }  
                
            if(data[i].y > max_y) {
                max_y = data[i].y;
            } 
        }
        
        double[] retVal = new double[4];
        retVal[0] = min_x;
        retVal[1] = min_y;
        retVal[2] = max_x;
        retVal[3] = max_y;
        
        return retVal;  
    }
    */
    
    public static double[][] transpose(double[][] inData) {
        
        double[][] data = new double[inData[0].length][inData.length];
        for (int i=0; i<inData.length; i++) {
            for (int j=0; j<inData[0].length; j++) {
                data[j][i] = inData[i][j];
            }
        }
        return data;
    }
    
    public int[][] transpose(int[][] inData) {
        
        int[][] data = new int[inData[0].length][inData.length];
        for (int i=0; i<inData.length; i++) {
            for (int j=0; j<inData[0].length; j++) {
                data[j][i] = inData[i][j];
            }
        }
        return data;
    }
    
    public static String[][] transpose(String[][] inData) {
        
        String[][] data = new String[inData[0].length][inData.length];
        for (int i=0; i<inData.length; i++) {
            for (int j=0; j<inData[0].length; j++) {
                data[j][i] = inData[i][j];
            }
        }
        return data;
    }
    
    public double[] scaleVecTo01(double[] data) {
        
        double min = data[0];
        double max = data[0];
        
        for(int i=1; i<data.length; i++) {
            
            if(data[i] < min) {
                min = data[i];
            }  
                
            if(data[i] > max) {
                max = data[i];
            }  
        }
        
        double range = max - min;
        double[] scaledData = new double[data.length];
        for(int i=0; i<data.length; i++) {
            scaledData[i] = (data[i] - min)/range;
        }
        
        return scaledData;
    }
    
    public int[] convertToIntArr(Object[] int_objs) {
        int[] ints = new int[int_objs.length];
        for (int i=0; i<int_objs.length; i++) {
            ints[i] = (Integer)int_objs[i];
        }
        return ints;
    }
    
    public boolean[] convertToBoolArr(Object[] bool_objs) {
        boolean[] bools = new boolean[bool_objs.length];
        for (int i=0; i<bool_objs.length; i++) {
            bools[i] = (Boolean)bool_objs[i];
        }
        return bools;
    }
    
    public double[][] sortDatabyCol(double[][] data, int col) {
        
            double[][] sorted_data;
            sorted_data = Arrays.copyOf(data, data.length);
            
            boolean flag = true;                                        // set flag to true to begin first pass
            double[] temp_row;
            
            while ( flag ) {
                flag = false;                                           //set flag to false awaiting a possible swap
                for (int j=0;  j < sorted_data.length-1;  j++ ) {
                    if ( sorted_data[j][col] > sorted_data[j+1][col] ) {    // change to > for ascending sort
                        temp_row = sorted_data[j];                      //swap elements
                        sorted_data[j] = sorted_data[j+1];
                        sorted_data[j+1] = temp_row;
                        flag = true;                                    //shows a swap occurred  
                    } 
                } 
            } 
            
            return sorted_data;
    }
    
    public static long[][] sortDatabyCol(long[][] data, int col) {
        
            long[][] sorted_data;
            sorted_data = Arrays.copyOf(data, data.length);
            
            boolean flag = true;                                        // set flag to true to begin first pass
            long[] temp_row;
            
            while ( flag ) {
                flag = false;                                           //set flag to false awaiting a possible swap
                for (int j=0;  j < sorted_data.length-1;  j++ ) {
                    if ( sorted_data[j][col] > sorted_data[j+1][col] ) {    // change to > for ascending sort
                        temp_row = sorted_data[j];                      //swap elements
                        sorted_data[j] = sorted_data[j+1];
                        sorted_data[j+1] = temp_row;
                        flag = true;                                    //shows a swap occurred  
                    } 
                } 
            } 
            
            return sorted_data;
    }
    
    public static int[] getRandomIntsExactCount (int num, int lowerLimit, int upperLimit) {
        
        int[] vals = new int[num];
        if ( (upperLimit - lowerLimit) == (num - 1) ) {
            for (int i=0; i<vals.length; i++) {
                vals[i] = lowerLimit + i;
            }
        }
        
        int count = 0;
        Random rand = new Random();
        
        while (count < num) {
            int nextRand = lowerLimit + rand.nextInt((upperLimit - lowerLimit));
            boolean randIsNew = true;
            for (int j=0; j<count; j++) {
                if (vals[j] == nextRand) {
                    randIsNew = false;
                }
            }
            if (randIsNew) {
                vals[count++] = nextRand;
            }
        }
        
        return vals;
    }
    
    public static int[] getRandomInts (int num, int lowerLimit, int upperLimit) {
        
        Random rand = new Random();
        int[] vals = new int[num];
        
        for (int i=0; i<vals.length; i++) {
            vals[i] = lowerLimit + rand.nextInt((upperLimit - lowerLimit));
        }
        
        LinkedHashSet <Integer> setUniqueVals = new LinkedHashSet <Integer> ();
        for(int x : vals) {
            setUniqueVals.add(x);
        }
        
        Object[] uniqueValsObj = setUniqueVals.toArray();
        int[] uniqueVals = new int[uniqueValsObj.length];
        for (int i=0; i<uniqueVals.length; i++) {
            uniqueVals[i] = (Integer) uniqueValsObj[i];
        }
        
        return uniqueVals;
    }
    
    public static double getMean(double[] data) {
        double mean = 0.0;
        for (int i=0; i<data.length; i++) {
            mean += data[i];
        }
        return mean/data.length;
    }
    
    public static double getMean(ArrayList <Double> data) {
        double sum = getSum(data);
        return sum/data.size();
    }
    
    /*
    public static Point getMean(Point[] points) {
        double mean_x = 0.0;
        double mean_y = 0.0;
        for (int i=0; i<points.length; i++) {
            mean_x += points[i].x;
            mean_y += points[i].y;
        }
        return new Point (mean_x/points.length, mean_y/points.length);
    }
    */
    
    public static double getSum(ArrayList <Double> data) {
        double sum = 0.0;
        for (int i=0; i<data.size(); i++) {
            sum += data.get(i);
        }
        return sum;
    }
    
    public static double[][] getCols(double[][] data, int[] cols) {
        double[][] selectedData = new double[data.length][cols.length];
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<cols.length; j++) {
                selectedData[i][j] = data[i][cols[j]];
            }
        }
        return selectedData;
    }
    
    public static double[] getCol(double[][] data, int col) {
        double[] selectedData = new double[data.length];
        for (int i=0; i<data.length; i++) {
            selectedData[i] = data[i][col];
        }
        return selectedData;
    }
    
    public double getRandomDouble (double lowerLimit, double upperLimit) {
        
        Random rand = new Random();
        double val = lowerLimit + rand.nextDouble()*(upperLimit - lowerLimit);
        return val;
    }
    
    public double getL2Distance(double[] arr1, double[] arr2) {
        
        if (arr1.length != arr2.length) {
            return -1.0;
        }
        
        double l2 = 0.0;
        for (int i=0; i<arr1.length; i++) {
            l2 += (arr1[i] - arr2[i])*(arr1[i] - arr2[i]);
        }
        
        return Math.sqrt(l2);
    }
    
    public static String getFormattedNumber (double d, int numChars) {
        String str = d + "";
        if (str.length() == numChars) {
            return str;
        } else if (str.length() > numChars) {
            return str.substring(0, numChars);
        } else {
            while (str.length() < numChars) {
                str += "";
            }
            return str;
        }
    }
    
    public static String formatData (double[] vals, String format) {
        
        String formattedString = "";
        StringTokenizer st = new StringTokenizer(format, " ");
        
        int count = 0;
        while (st.hasMoreElements()) {

            String strNum = "";
            int currHeaderLen = st.nextToken().length();
            if ( (vals[count] % 1) == 0 ) {
                strNum = vals[count] + "";
            } else {
                DecimalFormat df = new DecimalFormat("#.00");
                strNum = df.format(vals[count]);
            }

            if (strNum.length() < currHeaderLen) {
                while (strNum.length() < currHeaderLen) {
                    strNum = "0" + strNum;
                }
            } else if (strNum.length() > currHeaderLen) {
                while (strNum.length() >= currHeaderLen) {
                    strNum = strNum.substring(0, strNum.length()-2);
                }
                strNum = strNum + "-";
            }

            formattedString += " " + strNum;
            count++;
        }
        
        return formattedString;
    }
    
    public static int[] findUniqueElements(int[] column_indices, int level, int[][] labels){
        ArrayList<Integer> uniqueElems = new ArrayList<Integer>();
        ArrayList<Integer> arrVals = new ArrayList<Integer>();
        for(int i = 0; i < column_indices.length; i++) {
            arrVals.add(labels[level][i]);
        }
        
        for(int i=0;i<arrVals.size();i++){
            if(!uniqueElems.contains(arrVals.get(i))){
                uniqueElems.add(arrVals.get(i));
            }
        }
        int[] arr = new int[uniqueElems.size()];
        for(int i = 0;i < arr.length;i++){
            arr[i] = uniqueElems.get(i);
        }
        return arr;
    }
    
    
    
    public void testUniqueElements () {
        
        int[][] labels = {{0,1,2,3,4},{0,1,2,3,4},{0,1,2,3,4},{7,7,7,6,6},{8,8,8,8,8}};
        int[] column_indices = {0,1,2};
        int[] uele = findUniqueElements(column_indices, 3, labels);
        /// u get the idea... right yes  i thought we must write in the main function usuall i copy all the functions in main test it and paste back
        /// also can
        // okwa:)
        // :) okwa fix the uniqueElementsOne... and tell me then we do buildTree... and see how it goes... okwa
        // yes panchu potash i fix it.. let me get a sub
        // okwa fsat fast...
        // yes one more thing i want to show u wait 2 mins
    }
    
    public static int[] extractColumnIndices(int val, int[] columnVals){
        ArrayList<Integer> columnIndex = new ArrayList<Integer>();
        ArrayList<Integer> columnValues = new ArrayList<Integer>();
        for(int i = 0;i < columnVals.length; i++){
            columnValues.add(columnVals[i]);
        }
        
        for(int i = 0;i < columnValues.size(); i++){
            if(columnValues.get(i).equals(val)){
                columnIndex.add(i);
            }
        }
        
        int[] arr = new int[columnIndex.size()];
        for(int i = 0; i < arr.length; i++){
            arr[i] = columnIndex.get(i);
        }
        return arr;
    }
    
    
    public void buildTree(int level, int[] column_indices){
        int[][] labels = {{0,1,2,3,4},{0,1,2,3,4},{0,1,2,3,4},{7,7,7,6,6},{8,8,8,8,8}};
        int[] uniqColVals = findUniqueElements(column_indices, level, labels);
        //try {
            //BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for(int i = 0; i < uniqColVals.length; i++){
                System.out.println("name:" + uniqColVals[i] + ",");
                System.out.println("children");
                int[] extractColumns = extractColumnIndices(uniqColVals[i], column_indices);
                if(extractColumns.length <= 1){
                    System.out.println("{name:" + uniqColVals[i] + "}");
                } else {
                    buildTree(level-1, uniqColVals);
                }
            }
            //writer.close();
        //}catch (Exception e) {
           // System.out.println(e);
        //}
        
        
    }
    
    /*
    public static void savePoints (Point[] points, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<points.length; i++) {
                writer.append(points[i].x+ "\t" + points[i].y);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void savePointsList (ArrayList <Point> points, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<points.size(); i++) {
                writer.append(points.get(i).x+ "\t" + points.get(i).y);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void savePoints (ArrayList <Point[]> pointsList, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int j=0; j<pointsList.size(); j++) {
                Point[] points = pointsList.get(j);
                for (int i=0; i<points.length; i++) {
                    writer.append(points[i].x+ "\t" + points[i].y);
                    writer.newLine();
                }
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    */
    
    public static void appendLineToFile (String logfilepath, String line) throws IOException {
        File f = new File(logfilepath);
        FileWriter writer = new FileWriter(f, true);
        BufferedWriter bw = new BufferedWriter(writer);
        PrintWriter out = new PrintWriter(bw);
        out.println(line);
        out.close();
        bw.close();
        writer.close();
    }
    
    public static void saveDataMatrix (String filename, String delim, DataCells datacells, DataMask mask) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<datacells.height; i++) {
                if(mask.row_mask[i]) {
                    for (int j=0; j<datacells.width - 1; j++) {
                        if (mask.col_mask[j]) {
                            writer.append(datacells.dataval[i][j] + delim);
                        }
                    }
                    writer.append(datacells.dataval[i][datacells.width-1] + "");
                    writer.newLine();
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (Vector data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.size(); i++) {
                writer.append(data.elementAt(i).toString());
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (int[] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                writer.append(Integer.toString(data[i]));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveVec (double[] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            for (int i=0; i<data.length; i++) {
                writer.append(Double.toString(data[i]) + ", ");
            }
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (double[] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                writer.append(Double.toString(data[i]));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (int[][] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                for (int j=0; j<data[i].length - 1; j++) {
                    writer.append(Integer.toString(data[i][j]) + ",");
                }
                writer.append(Integer.toString(data[i][data[i].length - 1]));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (double[][] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                for (int j=0; j<data[i].length - 1; j++) {
                    writer.append(Double.toString(data[i][j]) + ",");
                }
                writer.append(Double.toString(data[i][data[i].length - 1]));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (double[][] data, String filename, String delim) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                for (int j=0; j<data[i].length - 1; j++) {
                    writer.append(data[i][j] + delim);
                }
                writer.append(data[i][data[i].length - 1] + "");
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void saveData (String[][] data, String filename, String delim) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                for (int j=0; j<data[i].length - 1; j++) {
                    writer.append(data[i][j] + delim);
                }
                writer.append(data[i][data[i].length - 1]);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveData (long[] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
            for (int i=0; i<data.length; i++) {
                writer.append(Long.toString(data[i]) + ",");
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveText (String[] data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            for (int i=0; i<data.length; i++) {
                writer.append(data[i]);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void saveText (String data, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.append(data);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /*
    public int[][] loadIntDelimData (String inFile, String delim, boolean hasHeader) 
    throws IOException {
        String[][] data = loadDelimData (inFile, delim, hasHeader);
        int[][] intData = new int[data.length][data[0].length];
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<data[0].length; j++) {
                intData[i][j] = Integer.parseInt(data[i][j]);
            }
        }
        return intData;
    }
    
    public static double[][] loadDoubleDelimData (String inFile, String delim, boolean hasHeader) 
    throws IOException {
        String[][] data = loadDelimData (inFile, delim, hasHeader);
        double[][] doubleData = new double[data.length][data[0].length];
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<data[0].length; j++) {
                doubleData[i][j] = Double.parseDouble(data[i][j]);
            }
        }
        return doubleData;
    }
    */
    
    
    
    
    
       
    public void println (Object o) {
        if (debugModeOn) {
            String str = o.toString();
            System.out.println(str);
            if (loggingOn) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename, true));
                    writer.append(str);
                    writer.newLine();
                    writer.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    public void print (Object o) {
        if (debugModeOn) {
            String str = o.toString();
            System.out.print(str);
            if (loggingOn) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(logFilename, true));
                    writer.append(str);
                    writer.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
    
    public static String extractFileName (String originalFilename) {
        int filenameStartIndex = originalFilename.lastIndexOf("\\");
        int extensionStartIndex = originalFilename.lastIndexOf(".");
        String extensionRemoved = originalFilename.substring( filenameStartIndex+1, extensionStartIndex );
        return extensionRemoved;
    }
    
    public static String changeFileExtension (String originalFilename, String newExtension, String optionalNameExtension) {
        int extensionStartIndex = originalFilename.lastIndexOf(".");
        String extensionRemoved = originalFilename.substring(0,extensionStartIndex);
        String newFilename = extensionRemoved + optionalNameExtension + "." + newExtension;
        return newFilename;
    }
    
    public static void saveImage (BufferedImage img, String filePathName) {
        try {
            File outputfile = new File(filePathName);
            ImageIO.write(img, "png", outputfile);
        } catch (Exception e) { System.out.println (e); }
    }
    
    public static void saveBMPImage (BufferedImage img, String filePathName) {
        try {
            File outputfile = new File(filePathName);
            ImageIO.write(img, "bmp", outputfile);
        } catch (Exception e) { 
            System.out.println (e); 
        }
    }
    
    public static BufferedImage loadImage (String filePathName) {
        BufferedImage image = null;
        try {
            File outputfile = new File(filePathName);
            image = ImageIO.read(outputfile);
        } catch (Exception e) { System.out.println (e); }
        return image;
    }
    
    public static BufferedImage convertToBufferedImage(Image image) {
    
        BufferedImage newImage = new BufferedImage(
            image.getWidth(null), image.getHeight(null),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    public static String getStackTraceAsString (Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }
    
    public static ArrayList <Integer> getColIdFrmString(String inString){

            if (inString.trim().equals("0")) {
                ArrayList <Integer> metaColId = new ArrayList<Integer>(0); 
                return metaColId;
            }

            inString = inString.trim();
            ArrayList <Integer> metaColId = new ArrayList<Integer>(); 
            int loc_comma = inString.indexOf(",");
            int loc_dash = inString.indexOf("-"); 

            if(loc_comma!= -1 && loc_dash!= -1){
                String[] parts = inString.split(",");
                for(int i = 0; i < parts.length; i++){
                    int loc_dash_inparts = parts[i].indexOf("-");
                    
                    if(loc_dash_inparts!= -1){
                        String parts_0 = parts[i].split("-")[0];
                        String parts_1 = parts[i].split("-")[1];
                        int len = (Integer.parseInt(parts_1) - Integer.parseInt(parts_0)) + 1;
                        int[] arr = new int[len];
                        int start_range = Integer.parseInt(parts_0);
                        for(int k = 0; k < len; k++ ){
                            arr[k] = start_range;
                            start_range++;
                        }
                        //int[] arr = IntStream.range(Integer.parseInt(parts_0), Integer.parseInt(parts_1)).toArray(); 

                        for(int j = 0; j < arr.length; j++){
                            metaColId.add(arr[j]-1);
                        }
                    } else {
                        metaColId.add(Integer.parseInt(parts[i])-1);

                    }
                }
               
            } else if (loc_comma!= -1){
                String[] parts = inString.split(",");
                
                for(int i = 0; i < parts.length; i++){
                    metaColId.add(Integer.parseInt(parts[i])-1);
                }
            } else if (loc_dash!= -1){
                String[] parts = inString.split("-");
                String parts_0 = parts[0];
                String parts_1 = parts[1];

                int len = (Integer.parseInt(parts_1) - Integer.parseInt(parts_0)) + 1;
                int[] arr = new int[len];
                int start_range = Integer.parseInt(parts_0);
                for(int k = 0; k < len; k++ ){
                    arr[k] = start_range;
                    start_range++;
                }
                //int[] arr = IntStream.range(Integer.parseInt(parts_0), Integer.parseInt(parts_1)).toArray(); 

                for(int j = 0; j < arr.length; j++){
                    metaColId.add(arr[j]-1);
                }
            }   

            return metaColId;

        }
    
	// adapted from post by Phil Haack and modified to match better
	/*public final static String tagStart=
		"\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
	public final static String tagEnd=
		"\\</\\w+\\>";
	public final static String tagSelfClosing=
		"\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
	public final static String htmlEntity=
		"&[a-zA-Z][a-zA-Z0-9]+;";
    
        */
    
        public final static String tagHTML = "\\<.*?\\>";
        public final static String endtagHTML = "\\</\\w+\\>";
        public final static String starttagHTML = "\\<";
        
	/**
	 * Will return true if s contains HTML markup tags or entities.
	 *
	 * @param s String to test
	 * @return true if string contains HTML
	 */
	public static String checkAndRemoveHtml(String s) {
            
            //Pattern p = Pattern.compile("("+tagStart+".*"+tagEnd+")|("+tagSelfClosing+")|("+htmlEntity+")", Pattern.DOTALL);
            
            
            
            //String output = s.replaceAll("\\<.*?\\>", "");
            Pattern p = Pattern.compile("(<([a-zA-Z]+)\\b[^>]*>)|(</([a-zA-Z]+) *>)");
            String output = p.matcher(s).replaceAll("");
                      
            
            /*Matcher m = p.matcher(s);
            
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                m.appendReplacement(sb, "");
            }
            m.appendTail(sb);
            
            return sb.toString(); */
            return output;
            
	}
        
        


}




