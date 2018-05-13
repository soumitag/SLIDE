/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import algorithms.clustering.HierarchicalClusterer;
import data.transforms.Normalizer;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import params.ClusteringParams;
import params.TransformationParams;
import params.VisualizationParams;
import structure.Data;
import structure.AnalysisContainer;
import structure.CompactSearchResultContainer;
import searcher.Searcher;
import utils.FileHandler;
import utils.Logger;
import utils.ReadConfig;
import utils.SessionManager;
import utils.Utils;
import vtbase.DataParsingException;
import vtbox.SessionUtils;

/**
 *
 * @author Soumita
 */
public class AnalysisInitializer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            
            String projectname = request.getParameter("newexperimentname");

            // create a new analysis
            AnalysisContainer analysis = new AnalysisContainer();
            analysis.setAnalysisName(projectname);

            // set base path for analysis
            String installPath = SessionManager.getInstallPath(getServletContext().getResourceAsStream("/WEB-INF/slide-web-config.txt"));
            analysis.setBasePath(SessionManager.getBasePath(installPath, request.getSession().getId(), analysis.analysis_name));

            // create analysis sub-folder if it does not already exist
            SessionManager.createAnalysisDirs(analysis);

            Logger log = new Logger(analysis.base_path, "server.log");
            log.writeLog(0, "AnalysisInitializer.java", Logger.MESSAGE, "Log Created");
       
            String filename_in = request.getParameter("fileinputname");
            String sample_series_mapping_filename_in = request.getParameter("mapfilename");
            
            String isDemo = request.getParameter("isDemo");
            String[] fnames = null;
            if(isDemo != null && isDemo.equalsIgnoreCase("yes")){
                
                String full_filepath = request.getParameter("fileinput_fullpath");
                String full_samplepath = request.getParameter("mapfile_fullpath");
                
                fnames = SessionManager.moveInputFilesToAnalysisDir_Demo(installPath, 
                    request.getSession().getId(), 
                    analysis.analysis_name, filename_in, 
                    sample_series_mapping_filename_in,
                    full_filepath, full_samplepath);
                
            } else {
            
                fnames = SessionManager.moveInputFilesToAnalysisDir(installPath, 
                    request.getSession().getId(), 
                    analysis.analysis_name, filename_in, sample_series_mapping_filename_in);
            }
            
            String filename = fnames[0];
            String sample_series_mapping_filename = fnames[1];
            
            int impute_type = Integer.parseInt(request.getParameter("imputeval"));
            
            String delimiter = Utils.getDelimiter(request.getParameter("delimval"));
            String sample_series_mapping_delimiter = Utils.getDelimiter(request.getParameter("mapdelimval"));

            /*
            String delimiter = null;
            if(delimval.equals("commaS")){
                delimiter = ",";
            } else if (delimval.equals("tabS")) {
                delimiter = "\t";
            } else if (delimval.equals("spaceS")){
                delimiter = " ";
            } else if (delimval.equals("semiS")) {
                delimiter = ";";
            } else if (delimval.equals("hyphenS")) {
                delimiter = "-";
            }
            */

            String species = request.getParameter("species_name");

            String[] col_names = request.getParameter("col_names").split(",");
            HashMap <String, Integer> col_map = new HashMap <> ();
            for (int i=0; i<col_names.length; i++) {
                col_map.put(col_names[i], i);
            }
            
            String temp = request.getParameter("identifier_mappings");
            HashMap <String, Integer> metacol_identifier_mappings = new HashMap <> ();
            String[] identifier_mappings = new String[0];
            if (temp != null && !temp.equals("")) {
                identifier_mappings = temp.split(",");
                for (int i=0; i<identifier_mappings.length; i+=2) {
                    metacol_identifier_mappings.put(identifier_mappings[i+1], col_map.get(identifier_mappings[i]));
                }
            }
            
            temp = request.getParameter("metacolnames");
            HashMap <String, Integer> unmapped_metacols = new HashMap <> ();
            String[] metacolnames = new String[0];
            if (temp != null && !temp.equals("")) {
                metacolnames = temp.split(",");
                for (int i=0; i<metacolnames.length; i++) {
                    int index = -1;
                    for (int j=0; j<identifier_mappings.length; j+=2) {
                        if (identifier_mappings[j].equals(metacolnames[i])) {
                            index = j;
                        }
                    }
                    if (index == -1) {
                        unmapped_metacols.put(metacolnames[i], col_map.get(metacolnames[i]));
                    }
                }
            }

            // the height in data_height_width includes header rows if any
            int[] data_height_width = FileHandler.getFileDimensions(filename, delimiter);
            
            String istimeSeriesStr = request.getParameter("isTimeSeries");
            boolean isTimeSeries;
            isTimeSeries = istimeSeriesStr.equals("yes");
            
            String hasReplicatesStr = request.getParameter("hasReplicates");
            boolean hasReplicates;
            hasReplicates = hasReplicatesStr.equals("yes");
            
            int rowLoading = Integer.parseInt(request.getParameter("rowLoading"));
            int start_row, end_row;
            if (rowLoading == 1) {
                start_row = 1;
                end_row = data_height_width[0] - 1;
            } else {
                start_row = Integer.parseInt(request.getParameter("txtFromRow"));
                end_row = Integer.parseInt(request.getParameter("txtToRow"));
            }
            /*
            boolean hasHeader = true;
            if (rowLoading == 1) {
                if (hasHeader) {
                    start_row = 1;
                    end_row = data_height_width[0] - 1;
                } else {
                    start_row = 1;
                    end_row = data_height_width[0];
                }
            } else {
                start_row = Integer.parseInt(request.getParameter("txtFromRow"));
                end_row = Integer.parseInt(request.getParameter("txtToRow"));
            }
            
            if (!hasHeader) {
                start_row = start_row - 1;
                end_row = end_row - 1;
            }
            */
            int column_normalization = Normalizer.COL_NORMALIZATION_NONE;
            int row_normalization = Normalizer.ROW_NORMALIZATION_NONE;
            int replicate_handling = Data.REPLICATE_HANDLING_NONE;

            Data database = null;
            try {
                database = new Data (filename, 
                                    impute_type,
                                    delimiter, 
                                    true,                   // hasHeader - headers in data file are now mandatory
                                    sample_series_mapping_filename,
                                    sample_series_mapping_delimiter,
                                    start_row, 
                                    end_row,
                                    data_height_width,      // the height in data_height_width includes header rows if any
                                    metacolnames,
                                    metacol_identifier_mappings, 
                                    unmapped_metacols,
                                    species,
                                    isTimeSeries,
                                    false,                  // logTransformData - removed support for log transforming at start
                                    column_normalization,
                                    row_normalization,
                                    hasReplicates,
                                    replicate_handling,
                                    5
                );
            } catch (DataParsingException e) {
                // send back to where it came from
                String msg = e.getLocalizedMessage();
                getServletContext().getRequestDispatcher("/newExperimentWizard.jsp?txtnewexperiment=" + projectname + "&msg=" + msg).forward(request, response);
                return;
            }

            analysis.setDatabase(database);
            
            analysis.setDataTransformationParams(new TransformationParams());
            analysis.setClusteringParams(new ClusteringParams());
            analysis.setVisualizationParams(new VisualizationParams());
            if(isDemo != null && isDemo.equalsIgnoreCase("yes")) {
                analysis.visualization_params.bin_range_type = "start_end_bins";
                analysis.visualization_params.bin_range_start = -3;
                analysis.visualization_params.bin_range_end = 3;
            }
            analysis.visualization_params.setRowLabelType(database.identifier_name);
            
            ArrayList <ArrayList<CompactSearchResultContainer>> search_results = 
                                    new ArrayList <ArrayList<CompactSearchResultContainer>> ();

            ArrayList <String> search_strings = new ArrayList <String> ();

            analysis.setSearchResults(search_results);
            analysis.setSearchStrings(search_strings);

            HashMap <String, ArrayList <Integer>> filterListMap = new HashMap <String, ArrayList <Integer>> ();
            analysis.setFilterListMap(filterListMap);
            
            // add searcher and logger to analysis
            Searcher searcher = new Searcher(species);
            analysis.setSearcher(searcher);
            analysis.setLogger(log);
            
            // get session
            HttpSession session = request.getSession(false);
            
            // load system configuration details
            HashMap <String, String> slide_config = ReadConfig.getSlideConfig(installPath);
            session.setAttribute("slide_config", slide_config);
            
            // add HierarchicalClusterer to analysis
            String py_module_path = slide_config.get("py-module-path");
            String py_home = slide_config.get("python-dir");

            HierarchicalClusterer hac = new HierarchicalClusterer (
                            analysis.base_path + File.separator + "data",
                            py_module_path, py_home);
            analysis.setHierarchicalClusterer(hac);
            
            
            // Finally add analysis to session
            session.setAttribute(analysis.analysis_name, analysis);
            
            // and send to display home
            if (isDemo != null && isDemo.equalsIgnoreCase("yes")) {
                getServletContext().getRequestDispatcher("/displayHome.jsp?analysis_name=" + analysis.analysis_name + "&isDemo=yes").forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/displayHome.jsp?analysis_name=" + analysis.analysis_name).forward(request, response);
            }
            
        } catch (Exception e) {
            
            HttpSession session = request.getSession(false);
            SessionUtils.logException(session, request, e);
            getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
