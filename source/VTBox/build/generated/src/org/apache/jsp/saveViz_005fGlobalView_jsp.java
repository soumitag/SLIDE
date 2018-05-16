package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import structure.Data;
import searcher.GeneObject;
import structure.MetaData;
import utils.Utils;
import graphics.HeatmapData;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import java.util.ArrayList;
import java.util.Iterator;
import algorithms.clustering.BinaryTree;
import algorithms.clustering.TreeNode;
import java.util.HashMap;
import graphics.Heatmap;
import structure.CompactSearchResultContainer;

public final class saveViz_005fGlobalView_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");


try {
    
    String analysis_name = request.getParameter("analysis_name");
    String base_url = (String)session.getAttribute("base_url");
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    String showDendrogram = request.getParameter("show_dendrogram");
    String showHistogram = request.getParameter("show_hist");
    String showSearchTags = request.getParameter("show_search_tags");
    
    int imgHeight = 750;
    int histHeight = 200;
    double search_result_header_height = 40.0;
    double svg_height = imgHeight + search_result_header_height + 30.0 + histHeight + 100;
    
    // heatmap params
    int start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        int max_features = analysis.database.metadata.nFeatures;
        end = max_features - 1;
    }
    
    // dendrogram params
    BinaryTree btree = analysis.linkage_tree;
    int root_node_id;
    String start_node_id = request.getParameter("start_node_id");

    if (start_node_id != null && start_node_id != "" && !start_node_id.equals("null")) {
        root_node_id = Integer.parseInt(start_node_id);
    } else {
        root_node_id = btree.root_node_id;
    }

    double dendrogramWidth = 300;
    HashMap <Integer, TreeNode> treeMap = null;
    if (showDendrogram.equalsIgnoreCase("yes")) { 
        dendrogramWidth = 300;
        ArrayList <Integer> sub_tree = btree.breadthFirstSearch(root_node_id, 25);
        treeMap = btree.buildSubTree(sub_tree);
        btree.computeEdgeCoordinatesPostOrder (treeMap, treeMap.get(root_node_id));
        btree.rescaleEdgeCoordinates(treeMap, imgHeight, dendrogramWidth);
    }
                
    // search results params
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    double search_results_column_width = 20.0;
    double search_results_column_gap = 5.0;
    double search_results_width = search_results.size()*search_results_column_width + (search_results.size()-1)*search_results_column_gap;
    
    int num_features = end - start + 1;
    double feature_height = (imgHeight*1.0)/(num_features*1.0);
    
    // feature labels params
    int featureLabelsWidth = 0;
    if (feature_height >= 15) {
        featureLabelsWidth = 250;
    }
    
    int globalHeatmapWidth = 250;
    Heatmap heatmap = analysis.heatmap;
    String imagename = heatmap.buildMapImage(
            start, end, globalHeatmapWidth, imgHeight, "saveviz_globalview_jsp", HeatmapData.TYPE_IMAGE);
    //String imagewebpath = session.getAttribute("base_url") + "/temp/images/" + imagename;
    //String imagewebpath = "http://localhost:8080/VTBox/images/" + imagename;
    
    double left_offset = 10;
    
    double svg_width = 10 + dendrogramWidth + 10.0 + globalHeatmapWidth + 10.0 + search_results_width + 10.0 + featureLabelsWidth;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <svg height=\"");
      out.print(svg_height);
      out.write("\" width=\"");
      out.print(svg_width);
      out.write("\" style=\"position: absolute; top: 20px; left: 0px\">\n");
      out.write("        <rect x=\"0\" y=\"20\" width=\"");
      out.print(svg_width);
      out.write("\" height=\"");
      out.print(svg_height);
      out.write("\" style=\"fill:white;\" />\n");
      out.write("            ");
 if (showDendrogram.equalsIgnoreCase("yes")) { 
      out.write("\n");
      out.write("\n");
      out.write("                <g>\n");
      out.write("                ");


                    Iterator iterator = treeMap.keySet().iterator(); 
                    while (iterator.hasNext()){

                        int node_id = (Integer)iterator.next();
                        TreeNode N = treeMap.get(node_id);

                        if (!N.isSubTreeLeaf) {

                            double p0_x = N.p0_x + 5.0 + left_offset;
                            double p1_x = N.p1_x + 5.0 + left_offset;
                            double p2_x = N.p2_x + 5.0 + left_offset;
                            double p3_x = N.p3_x + 5.0 + left_offset;

                            double p0_y = N.p0_y + search_result_header_height;
                            double p1_y = N.p1_y + search_result_header_height;
                            double p2_y = N.p2_y + search_result_header_height;
                            double p3_y = N.p3_y + search_result_header_height;
                
      out.write("\n");
      out.write("\n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_1\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p0_x);
      out.write("\" y1=\"");
      out.print(p0_y);
      out.write("\" x2=\"");
      out.print(p1_x);
      out.write("\" y2=\"");
      out.print(p1_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\"  onclick=\"loadSubTree(");
      out.print(N.leftChild.nodeID);
      out.write(',');
      out.print(N.leftChild.start);
      out.write(',');
      out.print(N.leftChild.end);
      out.write(")\" onmouseover=\"highlightCluster(");
      out.print(N.leftChild.nodeID);
      out.write(",'')\" onmouseout=\"deHighlightCluster(");
      out.print(N.leftChild.nodeID);
      out.write(",'')\" />\n");
      out.write("       \n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_2\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p1_x);
      out.write("\" y1=\"");
      out.print(p1_y);
      out.write("\" x2=\"");
      out.print(p2_x);
      out.write("\" y2=\"");
      out.print(p2_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\" onclick=\"loadSubTree(");
      out.print(node_id);
      out.write(',');
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\" onmouseover=\"highlightCluster(");
      out.print(node_id);
      out.write(",'',");
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\" onmouseout=\"deHighlightCluster(");
      out.print(node_id);
      out.write(",'',");
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\" onmousedown='hideContextMenu()' onmouseup='hideContextMenu()' oncontextmenu=\"showContextMenu(event,");
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\"/>\n");
      out.write("        \n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_3\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p2_x);
      out.write("\" y1=\"");
      out.print(p2_y);
      out.write("\" x2=\"");
      out.print(p3_x);
      out.write("\" y2=\"");
      out.print(p3_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\" />\n");
      out.write("\n");
      out.write("            ");
  
                        }
                    }

                    left_offset = left_offset + dendrogramWidth + 10;

                }
            
      out.write("\n");
      out.write("\n");
      out.write("                </g>\n");
      out.write("\n");
      out.write("                <g id=\"heatmap_g\">\n");
      out.write("                    <image xlink:href=\"");
      out.print(base_url);
      out.write("HeatmapImageServer?analysis_name=");
      out.print(analysis_name);
      out.write("&imagename=");
      out.print(imagename);
      out.write("\" x=\"");
      out.print(left_offset);
      out.write("px\" y=\"40px\" height=\"750px\" width=\"250px\" />\n");
      out.write("                </g>\n");
      out.write("            ");
 
                left_offset = left_offset + globalHeatmapWidth + 10.0; 
            
      out.write("\n");
      out.write("                \n");
      out.write("            ");
 if (showSearchTags.equalsIgnoreCase("yes")) {
                
                HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;

                //imgWidth = globalHeatmapWidth + 10.0 + search_results.size()*column_width + (search_results.size()-1)*gap;
                double left_position = 0;
                
                search_results = analysis.search_results;
                
                for(int i = 0; i < search_results.size(); i++) {     
      out.write("\n");
      out.write("                \n");
      out.write("                    <g  id=\"search_");
      out.print(i);
      out.write("\">\n");
      out.write("                    ");
        
                        left_position = left_offset + i*search_results_column_width + (i-1)*search_results_column_gap;
                        double search_head_center = left_position + (search_results_column_width/2.0);
                    
      out.write("\n");
      out.write("                        <rect x=\"");
      out.print(left_position);
      out.write("px\" y=\"0\" height=\"38px\" width=\"");
      out.print(search_results_column_width);
      out.write("px\" style=\"fill: #EEEEEE; z-index: 0\" />\n");
      out.write("                        <text x=\"");
      out.print(search_head_center);
      out.write("px\" text-anchor=\"middle\" y=\"15\" font-family=\"Verdana\" font-size=\"12\" fill=\"black\">");
      out.print(i);
      out.write("</text>\n");
      out.write("                        <rect x=\"");
      out.print(left_position);
      out.write("px\" y=\"40\" height=\"");
      out.print(imgHeight);
      out.write("px\" width=\"");
      out.print(search_results_column_width);
      out.write("px\" style=\"fill: #EEEEEE; z-index: 0\" />\n");
      out.write("            \n");
      out.write("                    ");
        
                        ArrayList <CompactSearchResultContainer> search_results_i = search_results.get(i);

                        for (int j = 0; j < search_results_i.size(); j++) {

                            CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                            ArrayList <Integer> positions = null;
                            if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
                                positions = entrezPosMap.get(search_results_ij.entrez_id);
                            } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
                                       analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION){
                                positions = entrezPosMap.get(search_results_ij.group_id.toUpperCase());
                            }

                            if (positions != null) {

                                for (int k = 0; k < positions.size(); k++) {

                                    if (positions.get(k) >= start && positions.get(k) <= end) {

                                        double top = 40.0 + feature_height*(positions.get(k) - start);
                                        double feature_display_height = feature_height;
                                        if (feature_height < 2.0) {
                                            feature_display_height = 2.0;
                                        }
                    
      out.write("\n");
      out.write("                                            <rect x=\"");
      out.print(left_position);
      out.write("\" y=\"");
      out.print(top);
      out.write("\" height=\"");
      out.print(feature_display_height);
      out.write("px\" width=\"");
      out.print(search_results_column_width);
      out.write("px\" style=\"fill: #73AD21;\" />\n");
      out.write("                                                     \n");
      out.write("            ");
                      }
                                }
                            }
                        }
                }

                left_offset = left_offset + search_results_width + 10.0; 
            }
            
      out.write("\n");
      out.write("                    </g>\n");
      out.write("    \n");
      out.write("    \n");
      out.write("                ");
 if (feature_height >= 15) { 
      out.write("\n");
      out.write("                    \n");
      out.write("                    <g>\n");
      out.write("    \n");
      out.write("                    ");

                        BinaryTree linkage_tree = analysis.linkage_tree;
                        
                        for (int i=(int)start; i<=(int)end; i++) {
                            
                            int index = linkage_tree.leaf_ordering.get(i);
                            
                            String genes = analysis.database.features.get(index).getFormattedFeatureName(analysis);
                            
                            double mid = search_result_header_height + feature_height*(i - start) + feature_height*0.60;
                    
      out.write("\n");
      out.write("\n");
      out.write("                            <text id=\"label_");
      out.print(i);
      out.write("\" x=\"");
      out.print(left_offset);
      out.write("\" y=\"");
      out.print(mid);
      out.write("\" font-family=\"Verdana\" font-size=\"12\" fill=\"black\" style=\"display: inline\">\n");
      out.write("                                ");
      out.print(genes);
      out.write("\n");
      out.write("                            </text>\n");
      out.write("\n");
      out.write("                    ");

                        }
                    
      out.write("\n");
      out.write("\n");
      out.write("                    </g>\n");
      out.write("                    \n");
      out.write("                ");

                    }
                
      out.write("\n");
      out.write("                    \n");
      out.write("                ");
 if (showHistogram.equalsIgnoreCase("yes")) { 
      out.write("\n");
      out.write("        \n");
      out.write("                    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/jspf/histogram_fragment.jspf" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("analysis_name", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(analysis_name), request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("y_offset", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf((imgHeight+search_result_header_height+30.0)), request.getCharacterEncoding()), out, true);
      out.write("\n");
      out.write("\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("        </svg>\n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("</html>\n");

  
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
