/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

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
            int heatmap_normalization = 0;
            int clustering_normalization = 0;
            String leaf_ordering_strategy = request.getParameter("leafOrder");
            String nBins = request.getParameter("txtNBins");
            String log2chk = null;
            String clippingType = "none";
            String binRangeType = "";

            // enrichment parameters' not used for gene level visualizations
            double significance_level = 0.0;
            int big_K = 0;
            int small_k = 0;

            HashMap <String, Double> enrichment_params;
            boolean[] feature_mask = null;  // used for filtering features in functional level visualizations, not used in gene level visualization (send null in this case)

            if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {

                replicate_handling = Integer.parseInt(request.getParameter("repHandle"));
                heatmap_normalization = Integer.parseInt(request.getParameter("normRules_Heatmap"));
                clustering_normalization = Integer.parseInt(request.getParameter("normRules_Clustering"));
                log2chk = request.getParameter("log2flag");
                clippingType = request.getParameter("clippingType");
                binRangeType = request.getParameter("binningRange");
                feature_mask = null;

            } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION)  {

                replicate_handling = 0;             // use seperately
                heatmap_normalization = 0;          // None
                clustering_normalization = 0;       // None
                log2chk = null;
                clippingType = "none";
                binRangeType = "symmetric_bins";

                significance_level = Double.parseDouble(request.getParameter("txtSignificanceLevel"));
                big_K = Integer.parseInt(request.getParameter("txtBig_K"));
                small_k = Integer.parseInt(request.getParameter("txtSmall_k"));

                enrichment_params = new HashMap <String, Double> ();
                enrichment_params.put("significance_level", significance_level);
                enrichment_params.put("small_k", (double)big_K);
                enrichment_params.put("big_K", (double)small_k);

                analysis.ea.testParams.filterFunctionalGroupList(big_K, small_k, significance_level);
                feature_mask = analysis.ea.testParams.funcgrp_mask;

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
            
            database.reprocessData (
                    selectedExps_clustering, 
                    logTransformData, 
                    heatmap_normalization, 
                    clustering_normalization, 
                    replicate_handling, 
                    groupBy,
                    clippingType, 
                    clip_min, 
                    clip_max,
                    feature_mask
            );
            
            HashMap <String, String> clustering_params = new HashMap <String, String> ();
            clustering_params.put("linkage", linkage);
            clustering_params.put("distance_func", distance_func);
            clustering_params.put("do_clustering", doCluster + "");
            if (analysis.equalsClusteringParam(clustering_params)) {
                clustering_params.put("use_cached", "true");
            } else {
                clustering_params.put("use_cached", "false");
            }
            analysis.setClusteringParams(clustering_params);
            
            HashMap <String, String> visualization_params = new HashMap <String, String> ();
            visualization_params.put("leaf_ordering_strategy", leaf_ordering_strategy);
            visualization_params.put("heatmap_color_scheme", heatmap_color_scheme);
            visualization_params.put("nBins", nBins);
            visualization_params.put("bin_range_type", binRangeType);
            visualization_params.put("bin_range_start", rangeStart + "");
            visualization_params.put("bin_range_end", rangeEnd + "");
            analysis.setVisualizationParams(visualization_params);
            
            HashMap <String, Double> state_variables = new HashMap <String, Double> ();
            state_variables.put("detailed_view_start", 0.0);
            state_variables.put("detailed_view_end", 37.0);
            analysis.setStateVariables(state_variables);
            
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
