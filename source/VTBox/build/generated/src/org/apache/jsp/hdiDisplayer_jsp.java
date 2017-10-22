package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import structure.Data;
import graphics.Heatmap;
import algorithms.clustering.HierarchicalClustering;
import utils.Logger;
import algorithms.clustering.BinaryTree;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

public final class hdiDisplayer_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Logger logger = analysis.logger;
    
    Data database = analysis.database;
    HashMap <String, String> visualization_params = analysis.visualization_params;
    
    BinaryTree linkage_tree;
    
    boolean do_clustering = Boolean.parseBoolean(analysis.clustering_params.get("do_clustering"));
    
    if (do_clustering) {
        
        HashMap <String, String> clustering_params = analysis.clustering_params;
        String linkage = clustering_params.get("linkage");
        String distance_func = clustering_params.get("distance_func");    
        int leaf_ordering_strategy = Integer.parseInt(visualization_params.get("leaf_ordering_strategy"));
        boolean use_cached = Boolean.parseBoolean(clustering_params.get("use_cached"));
    
        String py_module_path = ((HashMap <String, String>)session.getAttribute("slide_config")).get("py-module-path");
        String py_home = ((HashMap <String, String>)session.getAttribute("slide_config")).get("python-dir");
            
        HierarchicalClustering hac = new HierarchicalClustering (
                database, linkage, distance_func, leaf_ordering_strategy, 
                analysis.base_path + File.separator + "data",
                py_module_path, py_home);
        
        linkage_tree = hac.doClustering(use_cached);
        analysis.setLinkageTree(linkage_tree, true);
        
    } else {
        
        linkage_tree = new BinaryTree(database.features.size());
        analysis.setLinkageTree(linkage_tree, false);
        
    }

    HashMap <String, ArrayList <Integer>> entrezPosMap = new HashMap <String, ArrayList <Integer>> ();
    for (int i = 0 ; i < linkage_tree.leaf_ordering.size(); i++){
        
        ArrayList <Integer> pos;
        String eid = database.features.get(linkage_tree.leaf_ordering.get(i)).entrezId;
        if(entrezPosMap.containsKey(eid)){
            pos = entrezPosMap.get(eid);
            pos.add(i);
        } else {
            pos = new ArrayList <Integer> ();
            pos.add(i);
        }
        entrezPosMap.put(eid, pos);
    }
    analysis.setEntrezPosMap(entrezPosMap);
    System.out.println("3");
    
    int nBins = Integer.parseInt(visualization_params.get("nBins"));
    String bin_range_type = visualization_params.get("bin_range_type");
    Heatmap heatmap = null;
    if (bin_range_type.equals("start_end_bins")) {
        double range_start = Double.parseDouble(visualization_params.get("bin_range_start"));
        double range_end = Double.parseDouble(visualization_params.get("bin_range_end"));
        heatmap = new Heatmap(database, nBins, bin_range_type, range_start, range_end, linkage_tree.leaf_ordering);
    } else {
        heatmap = new Heatmap(database, nBins, bin_range_type, linkage_tree.leaf_ordering);
    }
    heatmap.genColorData();
    heatmap.assignBinsToRows();
    analysis.setHeatmap(heatmap);
    System.out.println("4");

      out.write('\n');
      out.write('\n');
      if (true) {
        _jspx_page_context.forward("drillDownPanel.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("show_dendrogram", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("no", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("analysis_name", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(analysis_name), request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("type", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("global_map", request.getCharacterEncoding()));
        return;
      }
      out.write('\n');
      out.write('\n');

  
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
