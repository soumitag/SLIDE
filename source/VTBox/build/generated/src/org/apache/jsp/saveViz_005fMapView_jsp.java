package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.layouts.ScrollViewLayout;
import searcher.GeneObject;
import structure.MetaData;
import utils.Utils;
import graphics.HeatmapData;
import vtbox.SessionUtils;
import java.util.ArrayList;
import structure.CompactSearchResultContainer;
import graphics.Heatmap;
import structure.Data;
import structure.AnalysisContainer;
import algorithms.clustering.BinaryTree;
import java.util.HashMap;

public final class saveViz_005fMapView_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    
    Data database = analysis.database;
    String[] headers = database.current_sample_names;
    
    Heatmap heatmap = analysis.heatmap;
    
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    double TABLE_WIDTH = layout.MAP_WIDTH;
    //double TABLE_HEIGHT = layout.MAP_HEIGHT;
    double BORDER_STROKE_WIDTH = layout.BORDER_STROKE_WIDTH;
    double CELL_WIDTH = layout.CELL_WIDTH;
    double CELL_HEIGHT = layout.CELL_HEIGHT;
    double HEADER_LABEL_FONTSIZE = layout.SAMPLE_LABEL_FONT_SIZE;
    double FEATURE_LABEL_FONTSIZE = layout.FEATURE_LABEL_FONT_SIZE;
    
    ///int TABLE_HEIGHT = 760;
    ///int CELL_HEIGHT = 20;
    // feature label params
    ///double feature_height = 20.0;       // ideally, should be same as CELL_HEIGHT
    ///double BORDER_STROKE_WIDTH = 0.5;
    // search results params
    
    
    int start = Integer.parseInt(request.getParameter("start"));
    int end = Integer.parseInt(request.getParameter("end"));
    String show_search_tags = request.getParameter("detailed_view_incl_search_tags");
    String show_histogram = request.getParameter("detailed_view_incl_hist");

    String imagename = heatmap.buildMapImage(start, end, "saveviz_mapview_jsp", HeatmapData.TYPE_ARRAY);
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    int NUM_CELLS = rgb[0].length;
    double TABLE_HEIGHT = layout.CELL_HEIGHT * NUM_CELLS;
    
    ///int MIN_TABLE_WIDTH = 500;
    ///int CELL_WIDTH = (int)Math.max(20.0, Math.floor(MIN_TABLE_WIDTH/rgb.length));
    ///int TABLE_WIDTH = (int)Math.max(MIN_TABLE_WIDTH, CELL_WIDTH*rgb.length);
    ///TABLE_HEIGHT = (int)CELL_HEIGHT*rgb[0].length;
    
    // used only in save viz, not in mapView.jsp
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    double search_result_width = 0.0;
    if (show_search_tags.equalsIgnoreCase("yes")) {
        search_result_width = left_buffer + search_results.size()*column_width + (search_results.size()-1)*gap;
    } else {
        search_result_width = 0.0;
    }
    
    String feature_width = request.getParameter("feature_width").trim();
    double feature_label_width = 200.0;
    try {
        feature_label_width = Double.parseDouble(feature_width);
    } catch (NumberFormatException e) {
        feature_label_width = layout.getFeatureLabelWidth(analysis.visualizationType);
    }
    
    String sample_height = request.getParameter("sample_height").trim();
    double sample_name_height = 95.0;
    try {
        sample_name_height = Double.parseDouble(sample_height);
    } catch (NumberFormatException e) {
        sample_name_height = (int)ScrollViewLayout.COLUMN_HEADER_HEIGHT;
    }
    
    int histHeight = 200;
    int header_map_top_buffer = 10;
    double svg_height = sample_name_height + header_map_top_buffer + TABLE_HEIGHT + 30.0 + histHeight + 100.0; // 30 is the gap between heatmap and histogram; 100 is for the histogram x-axis labels
    double svg_width = TABLE_WIDTH + search_result_width + 10 + feature_label_width;
    
    HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;
    
    double x, y = 0;

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <style>\n");
      out.write("        \n");
      out.write("            .container{\n");
      out.write("                position: absolute;\n");
      out.write("                border: 0px solid black;\n");
      out.write("                left: 2px; \n");
      out.write("                top: 100px;\n");
      out.write("                width: 250px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("    \n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <svg id='svg_save_map_view' \n");
      out.write("             xmlns:svg=\"http://www.w3.org/2000/svg\"\n");
      out.write("             xmlns=\"http://www.w3.org/2000/svg\"\n");
      out.write("             version=\"1.1\"\n");
      out.write("             width=\"");
      out.print(svg_width);
      out.write("\"\n");
      out.write("             height=\"");
      out.print(svg_height);
      out.write("\">\n");
      out.write("            \n");
      out.write("        <rect x=\"0\" y=\"0\" width=\"");
      out.print(svg_width);
      out.write("\" height=\"");
      out.print(svg_height);
      out.write("\" style=\"fill:white;\" />\n");
      out.write("        \n");
      out.write("        <g id=\"heatmap_header\">\n");
      out.write("            ");
 double hy = sample_name_height; 
      out.write("\n");
      out.write("            ");
 for(int i = 0; i < headers.length; i++) { 
      out.write("\n");
      out.write("                ");
 
                    double hx_text = (int)((i+0.5)*CELL_WIDTH) + HEADER_LABEL_FONTSIZE/2.0;
                
      out.write("\n");
      out.write("                \n");
      out.write("                <text x='");
      out.print(hx_text);
      out.write("' y='");
      out.print(hy);
      out.write("' font-family=\"Verdana\" font-size=\"");
      out.print(HEADER_LABEL_FONTSIZE);
      out.write("\" fill=\"black\" transform=\"rotate(-90 ");
      out.print(hx_text);
      out.write(' ');
      out.print(hy);
      out.write(")\"> ");
      out.print(headers[i]);
      out.write(" </text>\n");
      out.write("                \n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("        </g>\n");
      out.write("        \n");
      out.write("        <g id=\"heatmap_table\" width='");
      out.print(TABLE_WIDTH);
      out.write("' height='");
      out.print(TABLE_HEIGHT);
      out.write("'>\n");
      out.write("\n");
      out.write("            ");
 for (int i = 0; i < rgb.length; i++) {
      out.write("\n");
      out.write("            <g id=\"col_rect_");
      out.print(i);
      out.write("\">\n");
      out.write("                ");
 for (int j = 0; j < rgb[0].length; j++) { 
      out.write("\n");
      out.write("                ");

                    String color_str = "rgb(" + (int) rgb[i][j][0] + "," + (int) rgb[i][j][1] + "," + (int) rgb[i][j][2] + ")";
                    String td_id = "td_" + i + "_" + j;

                    x = i * CELL_WIDTH;
                    y = j * CELL_HEIGHT + ((int)sample_name_height + header_map_top_buffer);
                
      out.write("\n");
      out.write("                <rect x='");
      out.print(x);
      out.write("' y='");
      out.print(y);
      out.write("' id='");
      out.print(td_id);
      out.write("' width = '");
      out.print(CELL_WIDTH);
      out.write("' height = '");
      out.print(CELL_HEIGHT);
      out.write("' style=\"fill:");
      out.print(color_str);
      out.write("; \" stroke=\"black\" stroke-width=\"");
      out.print(BORDER_STROKE_WIDTH);
      out.write("\" /> \n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </g>\n");
      out.write("            ");
 }
      out.write("\n");
      out.write("\n");
      out.write("            \n");
      out.write("            ");
 if (show_search_tags.equalsIgnoreCase("yes")) {  
      out.write("\n");
      out.write("            <g id='search_g'>\n");
      out.write("        \n");
      out.write("            ");
  for(int i = 0; i < search_results.size(); i++) {     
      out.write("\n");
      out.write("            \n");
      out.write("                <g  id=\"search_");
      out.print(i);
      out.write("\">\n");
      out.write("                ");
        
                    double left_position = left_buffer + TABLE_WIDTH + i*column_width + (i-1)*gap;
                    String left = (int)left_position + "px";
                
      out.write("\n");
      out.write("            \n");
      out.write("                <rect x=\"");
      out.print(left);
      out.write("\" y=\"");
      out.print((sample_name_height+header_map_top_buffer));
      out.write("\" height=\"");
      out.print(layout.SEARCH_TAG_DIV_HEIGHT);
      out.write("px\" width=\"");
      out.print(column_width);
      out.write("px\" style=\"fill: #EEEEEE; z-index: 0\" />\n");
      out.write("            \n");
      out.write("                ");
        
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
                                
                                double top = (sample_name_height + header_map_top_buffer) + CELL_HEIGHT*(positions.get(k) - start);
                                double feature_display_height = CELL_HEIGHT;
                                if (CELL_HEIGHT < 2.0) {
                                    feature_display_height = 2.0;
                                }
                
      out.write("\n");
      out.write("                                <rect x=\"");
      out.print(left);
      out.write("\" y=\"");
      out.print(top);
      out.write("\" height=\"");
      out.print(feature_display_height);
      out.write("px\" width=\"");
      out.print(column_width);
      out.write("px\" style=\"fill: #73AD21; z-index: 1\" />\n");
      out.write("                ");

                            }
                        }
                    }
                }
                
      out.write("\n");
      out.write("                </g>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            </g>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("            \n");
      out.write("        <g id='feature_labels_g'>\n");
      out.write("    \n");
      out.write("            ");

                BinaryTree linkage_tree = analysis.linkage_tree;

                for (int i=(int)start; i<=(int)end; i++) {
                    int index = linkage_tree.leaf_ordering.get(i);
                    String genes = database.features.get(index).getFormattedFeatureName(analysis);
                    double mid = sample_name_height + header_map_top_buffer + CELL_HEIGHT*(i-start) + CELL_HEIGHT/2.0 + FEATURE_LABEL_FONTSIZE/2.0;
            
      out.write("\n");
      out.write("                    \n");
      out.write("                    <text id=\"label_");
      out.print(i);
      out.write("\" x=\"");
      out.print(TABLE_WIDTH+search_result_width+10);
      out.write("\" y=\"");
      out.print(mid);
      out.write("\" font-family=\"Verdana\" font-size=\"");
      out.print(FEATURE_LABEL_FONTSIZE);
      out.write("\" fill=\"black\" style=\"display: inline;\">\n");
      out.write("                        ");
      out.print(genes);
      out.write("\n");
      out.write("                    </text>\n");
      out.write("    \n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("    \n");
      out.write("        </g>\n");
      out.write("            \n");
      out.write("        \n");
      out.write("        </g>\n");
      out.write("        \n");
      out.write("        ");
 if (show_histogram.equalsIgnoreCase("yes")) { 
      out.write("\n");
      out.write("        \n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/jspf/histogram_fragment.jspf" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("analysis_name", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(analysis_name), request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("y_offset", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf((sample_name_height + header_map_top_buffer + TABLE_HEIGHT + 30.0)), request.getCharacterEncoding()), out, true);
      out.write("\n");
      out.write("        \n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        </svg>\n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("\n");
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
