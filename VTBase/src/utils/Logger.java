/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
/**
 *
 * @author soumitag
 */
public class Logger implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String MESSAGE = "MESSAGE";
    public static final String WARNING = "WARNING";
    public static final String FATAL = "FATAL";
    
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z");
    
    private final String logfilepath;
    //private String logfilepath;
    private FileWriter writer; 
    private BufferedWriter bw;
    private PrintWriter out; 
    
    private Date date;
    private String currentdate;
    
    public Logger(String path, String logfile) throws IOException{
        
        File f = new File(path + File.separator + logfile);
        if(!f.exists()){
            f.createNewFile();
        }
        
        this.logfilepath = path + File.separator + logfile;
    }
    
    public void writeLog(int error_code, String source, String severity, String inString) throws IOException{
        
        // Severity can be message, warning, fatal
        // Error code
        // Current date and time
        
        try {
        
            File f = new File(logfilepath);
            writer = new FileWriter(f, true);
            bw = new BufferedWriter(writer);
            out = new PrintWriter(bw);

            date = new Date();
            currentdate = DATEFORMAT.format(date);
            out.println(currentdate + "\t" + severity + "\t" + error_code + "\t" + source + "\t" +inString);  

            closeLog();
        
        } catch (Exception e) {
            
            System.out.println("An exception occured in Logger.writeLog() while logging...");
            System.out.println(e);
            System.out.println(Arrays.toString(e.getStackTrace()));
            
        }
    }
    
    public void writeLog(String inString) throws IOException{
               
        out.println(inString);      
        
    }
    
    public void closeLog() throws IOException {
        out.close();
        bw.close();
        writer.close();
    }
    
    
    
}
