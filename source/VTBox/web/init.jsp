<%-- 
    Document   : init
    Created on : 12 Jan, 2017, 4:28:45 PM
    Author     : soumitag
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="utils.SessionManager"%>
<%@page import="utils.ReadConfig"%>
<%@page import="structure.Serializer"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.HashMap"%>
<%@page import="searcher.Searcher"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="structure.Data"%>
<%@page import="utils.Utils"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="utils.Logger"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

    <%
        
        try {
        
            String projectname = request.getParameter("newexperimentname");

            // create a new analysis
            AnalysisContainer analysis = new AnalysisContainer();
            analysis.setAnalysisName(projectname);

            // set base path for analysis
            String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
            analysis.setBasePath(SessionManager.getBasePath(installPath, request.getSession().getId(), analysis.analysis_name));

            // create analysis sub-folder if it does not already exist
            SessionManager.createAnalysisDirs(analysis);

            Logger log = new Logger(analysis.base_path, "server.log");
            log.writeLog(0, "AnalysisInitializer", Logger.MESSAGE, "Log Created");
       
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

            //System.out.println(delimiter);

            // String[][] rawData = Utils.loadDelimData(filename, delimiter, hasHeader);

            String species = request.getParameter("species_name");

            String metacols = request.getParameter("txtNumMetaCols");
            ArrayList <Integer> metaColIds = Utils.getColIdFrmString(metacols);

            int genesymbolcol = Integer.parseInt(request.getParameter("txtGeneSymbolCol")) - 1;
            int entrezcol = Integer.parseInt(request.getParameter("txtEntrezCol")) - 1;
            //String exptype = request.getParameter("exptype");
            
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

            Data database = new Data (filename, 
                                    delimiter, 
                                    hasHeader, 
                                    sample_series_mapping_filename,
                                    start_row, 
                                    end_row,
                                    data_height_width,  // the height in data_height_width includes header rows if any
                                    //col_headers,
                                    //dataColIds,
                                    metaColIds, 
                                    genesymbolcol,
                                    entrezcol,
                                    species,
                                    //exptype,
                                    //ordcol,
                                    //sample_info,
                                    isTimeSeries,
                                    //timeSeriesNames,
                                    //seriesSampleMappings,
                                    //timeStamps,
                                    logTransformData,
                                    heatmap_normalization,
                                    clustering_normalization,
                                    replicate_handling,
                                    5
            );

            
            
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
            
            HashMap <String, Double> state_variables = new HashMap <String, Double> ();
            state_variables.put("detailed_view_start", 0.0);
            state_variables.put("detailed_view_end", 37.0);
            analysis.setStateVariables(state_variables);

            ArrayList <ArrayList<CompactSearchResultContainer>> search_results = 
                                    new ArrayList <ArrayList<CompactSearchResultContainer>> ();

            ArrayList <String> search_strings = new ArrayList <String> ();

            analysis.setSearchResults(search_results);
            analysis.setSearchStrings(search_strings);

            HashMap <String, ArrayList <Integer>> filterListMap = new HashMap <String, ArrayList <Integer>> ();
            //ArrayList <Integer> blanks = new ArrayList <Integer> ();
            //filterListMap.put("Detailed View", blanks);
            analysis.setFilterListMap(filterListMap);
            
            // add searcher and logger to analysis
            Searcher searcher = new Searcher(species);
            analysis.setSearcher(searcher);
            analysis.setLogger(log);
            
            // load system configuration details
            HashMap <String, String> slide_config = ReadConfig.getSlideConfig(installPath);
            session.setAttribute("slide_config", slide_config);
            
            // Finally add analysis to session
            session.setAttribute(analysis.analysis_name, analysis);
            
    %>
    
    <jsp:forward page="displayHome.jsp">
        <jsp:param name="analysis_name" value="<%=analysis.analysis_name%>" />
    </jsp:forward>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>