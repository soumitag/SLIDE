package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import searcher.GeneObject;
import structure.MetaData;
import utils.Utils;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import algorithms.clustering.BinaryTree;
import java.util.HashMap;
import structure.Data;
import java.util.ArrayList;
import structure.CompactSearchResultContainer;

public final class featurelabels_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    Data db = analysis.database;
    
    double num_features = db.metadata.nFeatures;
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
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
    
    double image_height = 750.0;
    num_features = end - start + 1;
    double feature_height = image_height/num_features;
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
    
    //double image_width = left_buffer + search_results.size()*column_width + (search_results.size()-1)*gap;
    double image_width = 200.0;
    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
        image_width = 200.0;
    } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION  ||
               analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {
        image_width = 350.0;
    }
    

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <svg height=\"");
      out.print(image_height);
      out.write("\" width=\"");
      out.print(image_width);
      out.write("\" style=\"position: absolute; top: 20px; left: 0px\">\n");
      out.write("        \n");
      out.write("        <g>\n");
      out.write("    \n");
      out.write("    ");

        if (feature_height >= 15.0) {
            
            BinaryTree linkage_tree = analysis.linkage_tree;
            
            for (int i=(int)start; i<=(int)end; i++) {
                int index = linkage_tree.leaf_ordering.get(i);
                
                String genes = db.features.get(index).getFormattedFeatureName(analysis);
                
                double mid = feature_height*(i - start) + feature_height*0.60;
    
      out.write("\n");
      out.write("    \n");
      out.write("                <text id=\"label_");
      out.print(i);
      out.write("\" x=\"0\" y=\"");
      out.print(mid);
      out.write("\" font-family=\"Verdana\" font-size=\"12\" fill=\"black\" style=\"display: inline\" >");
      out.print(genes);
      out.write("</text>\n");
      out.write("    \n");
      out.write("    ");

            }
        }
    
      out.write("\n");
      out.write("    \n");
      out.write("        </g>\n");
      out.write("            \n");
      out.write("        </svg>\n");
      out.write("        \n");
      out.write("    </body>\n");
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
