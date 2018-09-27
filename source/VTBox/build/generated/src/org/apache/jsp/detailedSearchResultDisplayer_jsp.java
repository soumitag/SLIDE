package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.layouts.DrillDownPanelLayout;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import algorithms.clustering.BinaryTree;
import java.util.HashMap;
import structure.Data;
import java.util.ArrayList;
import structure.CompactSearchResultContainer;

public final class detailedSearchResultDisplayer_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");


try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    Data db = analysis.database;
    
    HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;
    
    double num_features = db.metadata.nFeatures;
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    String TYPE = request.getParameter("type");
    if(TYPE == null) {
        TYPE = "dendrogram_map";
    }
    
    double start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        end = num_features;
    }
    
    DrillDownPanelLayout layout = analysis.visualization_params.drill_down_layout;
    double image_height = layout.GLOBAL_VIEW_FIG_HEIGHT;
    num_features = end - start + 1;
    double feature_height = image_height/num_features;
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
    
    double image_width = left_buffer + search_results.size()*column_width + (search_results.size()-1)*gap;
    double left_position = 0;

    String updaterFuncName = "";
    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
        updaterFuncName = "callDetailedInfoUpdater";
    } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) {
        updaterFuncName = "callDetailedPathInfoUpdater";
    } else if (analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {
        updaterFuncName = "callDetailedGOInfoUpdater";
    }

      out.write("\n");
      out.write("\n");
      out.write("    <html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <style>\n");
      out.write("                        \n");
      out.write("            .marker {\n");
      out.write("                position: absolute;\n");
      out.write("                left: -1px;\n");
      out.write("                width: 20px; \n");
      out.write("                height: 3px;\n");
      out.write("                background: #73AD21;\n");
      out.write("                border: none;\n");
      out.write("                cursor: pointer;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .marker_container {\n");
      out.write("                position: relative; \n");
      out.write("                top: 0px; \n");
      out.write("                left: 0px; \n");
      out.write("                height: 750px; \n");
      out.write("                background: #EEEEEE; \n");
      out.write("                border: 1px\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .cross {\n");
      out.write("                border: 1px; \n");
      out.write("                display: none; \n");
      out.write("                opacity: 0.5; \n");
      out.write("                cursor: pointer;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            table {\n");
      out.write("                border-spacing: 5px 0px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .td_head {\n");
      out.write("                 background: #EEEEEE;\n");
      out.write("                 width: 18px;\n");
      out.write("                 height: 35px;\n");
      out.write("                 text-align: center;\n");
      out.write("                 vertical-align: bottom;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            td {\n");
      out.write("                width: 18px;\n");
      out.write("                background: #EEEEEE;\n");
      out.write("            }\n");
      out.write("        \n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("        \n");
      out.write("            function callDetailedInfoUpdater(eid, analysis_name) {\n");
      out.write("                parent.showDetailedInfo(eid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function callDetailedPathInfoUpdater(pid, analysis_name) {\n");
      out.write("                parent.showDetailedPathInfo(pid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function callDetailedGOInfoUpdater(gid, analysis_name) {\n");
      out.write("                parent.showDetailedGOInfo(gid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            function toggleHighlightGenes(pathid, state) {\n");
      out.write("                //alert(\"3\");\n");
      out.write("                //alert(state);\n");
      out.write("                //alert(pathid);\n");
      out.write("                var x = document.getElementsByName(pathid);\n");
      out.write("                var i;\n");
      out.write("                if (state === 1) {\n");
      out.write("                    //alert(\"in highlight\");\n");
      out.write("                    //alert(x[0].getAttribute(\"style\"));\n");
      out.write("                    for (i = 0; i < x.length; i++) {\n");
      out.write("                        //x[i].style.background = \"#FFA500\";\n");
      out.write("                        x[i].setAttribute(\"style\",\"fill: #800000\");\n");
      out.write("                    }\n");
      out.write("                } else {\n");
      out.write("                    for (i = 0; i < x.length; i++) {\n");
      out.write("                        //x[i].style.background = \"#73AD21\";\n");
      out.write("                        x[i].setAttribute(\"style\",\"fill: #73AD21\");\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function loadGeneHighlights() {\n");
      out.write("                //alert(\"Looking for highlights\");\n");
      out.write("                pathid = parent.parent.selected_search_tag_label;\n");
      out.write("                //alert(pathid);\n");
      out.write("                \n");
      out.write("                var x = document.getElementsByName(pathid);\n");
      out.write("                var i;\n");
      out.write("                for (i = 0; i < x.length; i++) {\n");
      out.write("                    x[i].setAttribute(\"style\",\"fill: #800000\");\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("        \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("    \n");
      out.write("    ");
  if(TYPE.equals("global_map"))   {     
      out.write("\n");
      out.write("        <svg height=\"");
      out.print(image_height);
      out.write("\" width=\"");
      out.print(image_width);
      out.write("\" style=\"position: absolute; top: 2px; left: 0px\">\n");
      out.write("    ");
  }   else if(TYPE.equals("dendrogram_map"))   {     
      out.write("\n");
      out.write("        <svg height=\"");
      out.print(image_height);
      out.write("\" width=\"");
      out.print(image_width);
      out.write("\" style=\"position: absolute; top: 20px; left: 0px\">\n");
      out.write("    ");
  }     
      out.write("\n");
      out.write("        \n");
      out.write("        \n");
      out.write("    ");
  for(int i = 0; i < search_results.size(); i++) {     
      out.write("\n");
      out.write("            \n");
      out.write("            <g  id=\"search_");
      out.print(i);
      out.write("\">\n");
      out.write("    ");
        
            left_position = left_buffer + i*column_width + (i-1)*gap;
            String left = left_position + "px";
    
      out.write("\n");
      out.write("            \n");
      out.write("            <rect x=\"");
      out.print(left);
      out.write("\" y=\"0\" height=\"");
      out.print(image_height);
      out.write("px\" width=\"");
      out.print(column_width);
      out.write("px\" style=\"fill: #EEEEEE; z-index: 0\" />\n");
      out.write("            \n");
      out.write("    ");
        
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
                                
                                double top = feature_height*(positions.get(k) - start);
                                double feature_display_height = feature_height;
                                if (feature_height < 2.0) {
                                    feature_display_height = 2.0;
                                }
                                //double top = mid - feature_display_height/2.0;
                                
                                if (search_results_ij.type == CompactSearchResultContainer.TYPE_PATH) {

                                    String eid = search_results_ij.getEntrezID();
                                    String pathid = search_results_ij.getPathID();
        
      out.write("\n");
      out.write("\n");
      out.write("                                    <rect id='");
      out.print(i + "_" + pathid);
      out.write("' name='");
      out.print(i + "_" + pathid);
      out.write("' x=\"");
      out.print(left);
      out.write("\" y=\"");
      out.print(top);
      out.write("\" height=\"");
      out.print(feature_display_height);
      out.write("px\" width=\"");
      out.print(column_width);
      out.write("px\" style=\"fill: #73AD21; z-index: 1\" onclick='");
      out.print(updaterFuncName);
      out.write('(');
      out.print(eid);
      out.write(", \"");
      out.print(analysis_name);
      out.write("\")' />\n");
      out.write("                                    \n");
      out.write("        ");

                                } else if (search_results_ij.type == CompactSearchResultContainer.TYPE_GO) {

                                    String eid = search_results_ij.getEntrezID();
                                    String goid = search_results_ij.getGOID();
        
      out.write("\n");
      out.write("\n");
      out.write("                                    <rect id='");
      out.print(i + "_" + goid);
      out.write("' name='");
      out.print(i + "_" + goid);
      out.write("' x=\"");
      out.print(left);
      out.write("\" y=\"");
      out.print(top);
      out.write("\" height=\"");
      out.print(feature_display_height);
      out.write("px\" width=\"");
      out.print(column_width);
      out.write("px\" style=\"fill: #73AD21\" onclick='");
      out.print(updaterFuncName);
      out.write('(');
      out.print(eid);
      out.write(", \"");
      out.print(analysis_name);
      out.write("\")' />\n");
      out.write("                                    \n");
      out.write("        ");

                                } else if (search_results_ij.type == CompactSearchResultContainer.TYPE_GENE) {

                                    String eid = search_results_ij.getEntrezID();
        
      out.write("\n");
      out.write("\n");
      out.write("                                    <rect id='");
      out.print(i + "_" + eid);
      out.write("' name='");
      out.print(i + "_" + eid);
      out.write("' x=\"");
      out.print(left);
      out.write("\" y=\"");
      out.print(top);
      out.write("\" height=\"");
      out.print(feature_display_height);
      out.write("px\" width=\"");
      out.print(column_width);
      out.write("px\" style=\"fill: #73AD21\" onclick='");
      out.print(updaterFuncName);
      out.write('(');
      out.print(eid);
      out.write(", \"");
      out.print(analysis_name);
      out.write("\")' />\n");
      out.write("                                    \n");
      out.write("        ");
                      }
                            }
                        }
                    }
                }
        
      out.write("\n");
      out.write("            </g>\n");
      out.write("        ");
    
        }
        
      out.write("\n");
      out.write("    \n");
      out.write("        </svg>\n");
      out.write("    \n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        loadGeneHighlights ();\n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("</html>\n");
      out.write("\n");

  
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
