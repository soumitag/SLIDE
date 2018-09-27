/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import data.transforms.Normalizer;
import graphics.layouts.DrillDownPanelLayout;
import graphics.layouts.ScrollViewLayout;
import graphics.layouts.VizualizationHomeLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import java.util.HashMap;
import params.ClusteringParams;
import params.EnrichmentParams;
import params.TransformationParams;
import params.VisualizationParams;
import structure.Data;

/**
 *
 * @author Soumita
 */
public class AnalysisReInitializer extends HttpServlet {

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
            
            String analysis_name = request.getParameter("analysis_name");
            
            // get session
            HttpSession session = request.getSession(false);
            AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

            Data database = analysis.database;

            String linkage = request.getParameter("linkFunc");
            String distance_func = request.getParameter("distFunc");
            int replicate_handling = 0;
            String heatmap_color_scheme = request.getParameter("colorScheme");
            int column_normalization = Normalizer.COL_NORMALIZATION_NONE;
            int row_normalization = Normalizer.ROW_NORMALIZATION_NONE;
            String leaf_ordering_strategy = request.getParameter("leafOrder");
            String nBins = request.getParameter("txtNBins");
            String log2chk = null;
            String clippingType = "none";
            String binRangeType = "";
            String identifierType = "";

            int detailed_view_res = ScrollViewLayout.RESOLUTION_PROFILE_20;
            String detailed_view_sz = request.getParameter("detailed_view_sz");
            if (detailed_view_sz != null && !detailed_view_sz.equals("")){
                detailed_view_res = Integer.parseInt(detailed_view_sz);
            }
            
            boolean use_square_cells;
            String square_cell_flag = request.getParameter("square_cell_flag");
            if(square_cell_flag == null || !square_cell_flag.equals("on")){
                use_square_cells = false;
            } else {
                use_square_cells = true;
            }
            
            int panel_lengths = VizualizationHomeLayout.VISUALIZATION_PANE_LENGTH_0;
            String panel_sz = request.getParameter("panel_lengths");
            if (panel_sz != null && !panel_sz.equals("")){
                panel_lengths = Integer.parseInt(panel_sz);
            }
            
            // enrichment parameters' not used for gene level visualizations
            double significance_level = 0.0;
            int big_K = 0;
            int small_k = 0;

            EnrichmentParams enrichment_params;

            if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {

                replicate_handling = Integer.parseInt(request.getParameter("repHandle"));
                column_normalization = Integer.parseInt(request.getParameter("normRule_Col"));
                row_normalization = Integer.parseInt(request.getParameter("normRule_Row"));
                log2chk = request.getParameter("log2flag");
                clippingType = request.getParameter("clippingType");
                binRangeType = request.getParameter("binningRange");
                identifierType = request.getParameter("identifierType");

            } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION)  {

                replicate_handling = 0;                                     // use seperately
                column_normalization = Normalizer.COL_NORMALIZATION_NONE;   // None
                row_normalization = Normalizer.ROW_NORMALIZATION_NONE;      // None
                log2chk = null;
                clippingType = "none";
                binRangeType = "symmetric_bins";

                significance_level = Double.parseDouble(request.getParameter("txtSignificanceLevel"));
                big_K = Integer.parseInt(request.getParameter("txtBig_K"));
                small_k = Integer.parseInt(request.getParameter("txtSmall_k"));

                enrichment_params = new EnrichmentParams ();
                enrichment_params.setSignificanceLevel((float)significance_level);
                enrichment_params.setSmallk(small_k);
                enrichment_params.setBigK(big_K);
                
                analysis.ea.testParams.filterFunctionalGroupList(big_K, small_k, significance_level);
                analysis.setEnrichmentParams(enrichment_params);
            }

            ArrayList <String> selectedExps_clustering = new ArrayList <String> ();
            ArrayList <String> selectedExps_visuals = new ArrayList <String> ();
            for (int i = 0; i < database.samples.size(); i++) {
                String selected_ci = request.getParameter("expts_" + i + "_c");
                if (selected_ci != null) {
                    selectedExps_clustering.add(selected_ci);
                }
                String selected_vi = request.getParameter("expts_" + i + "_v");
                if (selected_vi != null) {
                    selectedExps_visuals.add(selected_vi);
                }
            }

            float clip_min = Float.NEGATIVE_INFINITY;
            float clip_max = Float.POSITIVE_INFINITY;
            if (!clippingType.equals("none")) {
                clip_min = Float.parseFloat(request.getParameter("txtClipMin"));
                clip_max = Float.parseFloat(request.getParameter("txtClipMax"));
            }

            double rangeStart = -1.0;
            double rangeEnd = 1.0;
            if(binRangeType.equals("start_end_bins")){
                if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {
                    rangeStart = Double.parseDouble(request.getParameter("txtRangeStart"));
                    rangeEnd = Double.parseDouble(request.getParameter("txtRangeEnd"));
                } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {
                    rangeStart = -10.0;
                    rangeEnd = 10.0;
                }
            }
            
            boolean logTransformData = false;
            if(log2chk == null || !log2chk.equals("on")){
                logTransformData = false;
            } else {
                logTransformData = true;
            }

            String cluster_chk = request.getParameter("do_cluster_flag");
            boolean doCluster = false;
            if(cluster_chk == null || cluster_chk.equals("false")){
                doCluster = false;
            } else {
                doCluster = true;
            }
            
            String groupBy = "";
            if (database.isTimeSeries) {
                groupBy = request.getParameter("groupBy");
            } else {
                groupBy = "sample";
            }
            
            if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {
                
                database.reprocessData_FeatureLevel (
                        selectedExps_clustering, 
                        logTransformData, 
                        column_normalization, 
                        row_normalization, 
                        replicate_handling, 
                        groupBy,
                        clippingType, 
                        clip_min, 
                        clip_max,
                        identifierType
                );
                
            } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION)  {
                
                database.reprocessData_GroupLevel (
                        selectedExps_clustering,
                        column_normalization, 
                        row_normalization, 
                        replicate_handling, 
                        groupBy,
                        clippingType, 
                        clip_min, 
                        clip_max,
                        analysis.ea
                );
                
            }
            
            TransformationParams data_transformation_params = new TransformationParams();
            data_transformation_params.setReplicateHandling(replicate_handling);
            data_transformation_params.setClippingType(clippingType);
            data_transformation_params.setClipMin(clip_min);
            data_transformation_params.setClipMax(clip_max);
            data_transformation_params.setLogTransform(logTransformData);
            data_transformation_params.setColumnNormalization(column_normalization);
            data_transformation_params.setRowNormalization(row_normalization);
            data_transformation_params.setGroupBy(groupBy);
            
            ClusteringParams clustering_params = new ClusteringParams();
            clustering_params.setDistanceFunc(distance_func);
            clustering_params.setLinkage(linkage);
            clustering_params.setDoClustering(doCluster);
            
            VisualizationParams visualization_params = new VisualizationParams(database.datacells.width, analysis.visualizationType);
            visualization_params.setLeafOrderingStrategy(leaf_ordering_strategy);
            visualization_params.setNBins(Integer.parseInt(nBins));
            visualization_params.setBinRangeType(binRangeType);
            visualization_params.setBinRangeStart((float)rangeStart);
            visualization_params.setBinRangeEnd((float)rangeEnd);
            visualization_params.setHeatmapColorScheme(heatmap_color_scheme);
            visualization_params.setRowLabelType(identifierType);
            visualization_params.setViewLayout(new VizualizationHomeLayout(panel_lengths));
            visualization_params.setDetailedViewMapLayout(new ScrollViewLayout(
                    detailed_view_res, visualization_params.viz_layout.getAvailableDetailedViewLength(), 
                    use_square_cells, database.datacells.width, analysis.visualizationType));
            visualization_params.setDrillDownPanelLayout(new DrillDownPanelLayout(visualization_params.viz_layout));
            
            analysis.setDataTransformationParams(data_transformation_params);
            analysis.setClusteringParams(clustering_params);
            analysis.setVisualizationParams(visualization_params);
            
            analysis.state_variables.setDetailedViewStart(0);
            analysis.state_variables.setDetailedViewEnd(
                    Math.min(visualization_params.detailed_view_map_layout.NUM_DISPLAY_FEATURES, 
                    analysis.database.metadata.nFeatures));
            
            getServletContext().getRequestDispatcher("/visualizationHome.jsp?analysis_name=" + analysis_name).forward(request, response);
            
        }  catch (Exception e) {
            
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
