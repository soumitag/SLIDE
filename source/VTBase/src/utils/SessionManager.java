/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Soumita
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import structure.AnalysisContainer;

public class SessionManager {
    
    public static String getInstallPath (InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            br.close();
            in.close();
            return line;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getPyModulePath (String installPath) {
        return installPath + File.separator + "src";
    }
    
    public static String getBasePath (String installPath, String session_id, String analysis_name) {
        return installPath + File.separator + "temp" + File.separator + session_id + File.separator + analysis_name;
    }
    
    public static String createSessionDir (String installPath, String session_id) {
        String session_dir_path = installPath + File.separator + "temp" + File.separator + session_id;
        File session_dir = new File(session_dir_path);
        if (!session_dir.exists()) { 
            session_dir.mkdir();
        }
        return session_dir_path;
    }
    
    public static String getSessionDir (String installPath, String session_id) {
        String session_dir_path = installPath + File.separator + "temp" + File.separator + session_id;
        return session_dir_path;
    }
    
    public static void createAnalysisDirs (AnalysisContainer analysis) {
        
        File analysis_dir = new File(analysis.base_path);
        if (!analysis_dir.exists()) {
            analysis_dir.mkdir();
        }
        
        File images_dir = new File(analysis.base_path + File.separator + "images");
        if (!images_dir.exists()) {
            images_dir.mkdir();
        }
            
        File data_dir = new File(analysis.base_path + File.separator + "data");
        if (!data_dir.exists()) {
            data_dir.mkdir();
        }
    }
    
    public static String[] moveInputFilesToAnalysisDir (
            String installPath, String session_id, String analysis_name,
            String filename_in, 
            String sample_series_mapping_filename_in) {
        
        // copy the data file from temp location to data dir
        //String tempFolder = pageContext.getServletContext().getRealPath("") + File.separator + "temp" + File.separator + request.getSession().getId();
        String session_dir = installPath + File.separator + "temp" + File.separator + session_id;
        
        // source file paths
        String temp_data_filepath = session_dir + File.separator + 
                                    analysis_name + "_" + filename_in;
        
        String temp_map_filepath = session_dir + File.separator + 
                                   analysis_name + "_" + sample_series_mapping_filename_in;
        
        // target file paths
        String filename = session_dir + File.separator + 
                          analysis_name + File.separator + 
                          "data" + File.separator + 
                          filename_in;
        
        String sample_series_mapping_filename = session_dir + File.separator + 
                                                analysis_name + File.separator + 
                                                "data" + File.separator + 
                                                sample_series_mapping_filename_in;

        // copy two files from source to target
        File data_file = new File(temp_data_filepath);
        data_file.renameTo(new File(filename));
        File map_file = new File(temp_map_filepath);
        map_file.renameTo(new File(sample_series_mapping_filename));
        
        return new String[]{filename, sample_series_mapping_filename};
    }
    
}
