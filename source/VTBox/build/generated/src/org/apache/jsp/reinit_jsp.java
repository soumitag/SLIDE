package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.Serializer;
import structure.AnalysisContainer;
import java.util.HashMap;
import searcher.Searcher;
import java.util.Arrays;
import java.util.ArrayList;
import structure.Data;
import utils.Utils;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import utils.Logger;

public final class reinit_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

        
try {
        
            String analysis_name = request.getParameter("analysis_name");
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

            Logger log = analysis.logger;

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
            //analysis.setStateVariables(state_variables);
            //Serializer serializer = new Serializer();
            //serializer.serializeAnalysis(analysis, "D:/ad/sg/26_May_2017");
            
            //AnalysisContainer analysis_1 = serializer.loadAnalysis("D:/ad/sg/26_May_2017/abc.slide");
            //session.setAttribute(analysis_1.analysis_name, analysis_1);
    
      out.write("\n");
      out.write("    \n");
      out.write("    ");
      if (true) {
        _jspx_page_context.forward("visualizationHome.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("analysis_name", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(analysis_name), request.getCharacterEncoding()));
        return;
      }
      out.write("\n");
      out.write("    \n");

  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}


    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
