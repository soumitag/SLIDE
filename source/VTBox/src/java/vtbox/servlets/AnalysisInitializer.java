/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import structure.Data;
import structure.AnalysisContainer;
import structure.CompactSearchResultContainer;
import searcher.Searcher;
import utils.Utils;
import utils.Logger;
import utils.ReadConfig;
import utils.SessionManager;
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
            
            String[] fnames = SessionManager.moveInputFilesToAnalysisDir(installPath, 
                    request.getSession().getId(), 
                    analysis.analysis_name, filename_in, sample_series_mapping_filename_in);
            
            String filename = fnames[0];
            String sample_series_mapping_filename = fnames[1];

            String headerchk = request.getParameter("headerflag");
            String log2chk = request.getParameter("log2flag");
        
            boolean hasHeader = false;
            if(headerchk == null || !headerchk.equals("on")){
                hasHeader = false;
            } else {
                hasHeader = true;
            }
            String delimval = request.getParameter("delimval");

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

            String species = request.getParameter("species_name");

            String metacols = request.getParameter("txtNumMetaCols");
            ArrayList <Integer> metaColIds = Utils.getColIdFrmString(metacols);

            int genesymbolcol = Integer.parseInt(request.getParameter("txtGeneSymbolCol")) - 1;
            int entrezcol = Integer.parseInt(request.getParameter("txtEntrezCol")) - 1;
            
            // the height in data_height_width includes header rows if any
            int[] data_height_width = Utils.getFileDimensions(filename, delimiter);
            
            String istimeSeriesStr = request.getParameter("isTimeSeries");
            boolean isTimeSeries = false;
            if(istimeSeriesStr.equals("yes")){
                isTimeSeries = true;
            } else {
                isTimeSeries = false;
            }
            
            boolean logTransformData = false;
            if(log2chk == null || !log2chk.equals("on")){
                logTransformData = false;
            } else {
                logTransformData = true;
            }
            
            int rowLoading = Integer.parseInt(request.getParameter("rowLoading"));
            int start_row, end_row;
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

            int heatmap_normalization = Data.HEATMAP_ROW_NORMALIZATION_NONE;
            int clustering_normalization = Data.NORMALIZATION_NONE;
            int replicate_handling = Data.REPLICATE_HANDLING_NONE;

            Data database = null;
            try {
                database = new Data (filename, 
                                    delimiter, 
                                    hasHeader, 
                                    sample_series_mapping_filename,
                                    start_row, 
                                    end_row,
                                    data_height_width,  // the height in data_height_width includes header rows if any
                                    metaColIds, 
                                    genesymbolcol,
                                    entrezcol,
                                    species,
                                    isTimeSeries,
                                    logTransformData,
                                    heatmap_normalization,
                                    clustering_normalization,
                                    replicate_handling,
                                    5
                );
            } catch (DataParsingException e) {
                // send back to where it came from
                String msg = e.getLocalizedMessage();
                getServletContext().getRequestDispatcher("/newExperiment.jsp?txtnewexperiment=" + projectname + "&msg=" + msg).forward(request, response);
                return;
            }

            analysis.setDatabase(database);

            HashMap <String, String> clustering_params = new HashMap <String, String> ();
            clustering_params.put("linkage", "");
            clustering_params.put("distance_func", "");
            clustering_params.put("do_clustering", "false");
            clustering_params.put("use_cached", "false");
            analysis.setClusteringParams(clustering_params);
            
            HashMap <String, String> visualization_params = new HashMap <String, String> ();
            visualization_params.put("leaf_ordering_strategy", "0");   // largest cluster first
            visualization_params.put("heatmap_color_scheme", "blue_red");
            visualization_params.put("nBins", "21");
            visualization_params.put("bin_range_type", "data_bins");
            visualization_params.put("bin_range_start", "-1");
            visualization_params.put("bin_range_end", "-1");
            analysis.setVisualizationParams(visualization_params);
            
            /*
            HashMap <String, Double> state_variables = new HashMap <String, Double> ();
            state_variables.put("detailed_view_start", 0.0);
            state_variables.put("detailed_view_end", 37.0);
            analysis.setStateVariables(state_variables);
            */
            
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
            
            // Finally add analysis to session
            session.setAttribute(analysis.analysis_name, analysis);
            
            // and send to display home
            getServletContext().getRequestDispatcher("/displayHome.jsp?analysis_name=" + analysis.analysis_name).forward(request, response);
            
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
