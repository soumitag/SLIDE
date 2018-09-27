package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.layouts.ScrollViewLayout;
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

public final class featureLabelsScrollView_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Data db = analysis.database;
    
    String start_str = request.getParameter("start");
    double start;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    start = (start < 0) ? 0 : start;
    int current_start = analysis.state_variables.getDetailedViewStart();
    start = (start >= analysis.database.metadata.nFeatures) ? current_start : start;
    
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    int end = layout.getEnd((int)start, analysis.database.metadata.nFeatures);
    
    double feature_height = layout.CELL_HEIGHT;
    double image_width = layout.getFeatureLabelWidth(analysis.visualizationType);
    double image_height = layout.MAP_HEIGHT;
    double font_weight = layout.FEATURE_LABEL_FONT_SIZE;
 

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            var selected_features = [];\n");
      out.write("            function getSelectedFeatureIndices() {\n");
      out.write("                return selected_features;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <svg height=\"");
      out.print(image_height);
      out.write("\" width=\"");
      out.print(image_width);
      out.write("\" style=\"position: absolute; top: 0px; left: 0px\">\n");
      out.write("        \n");
      out.write("        <script type=\"text/ecmascript\"><![CDATA[\n");
      out.write("            function toggleSelection(feature_index, feature_name, label_id) {\n");
      out.write("                var index = selected_features.indexOf(feature_index);\n");
      out.write("                var svg_txt_obj = document.getElementById(label_id);\n");
      out.write("                if (index > -1) {\n");
      out.write("                    selected_features.splice(index, 1);\n");
      out.write("                    svg_txt_obj.textContent = feature_name;\n");
      out.write("                    svg_txt_obj.style.fill = \"black\";\n");
      out.write("                    svg_txt_obj.style.fontWeight = \"normal\";\n");
      out.write("                } else {\n");
      out.write("                    selected_features.push(feature_index);\n");
      out.write("                    svg_txt_obj.textContent = \"\\u2713 \" + feature_name;\n");
      out.write("                    svg_txt_obj.style.fill = \"green\";\n");
      out.write("                    svg_txt_obj.style.fontWeight = \"bold\";\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            ]]>\n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("        <g id='feature_labels_g'>\n");
      out.write("    \n");
      out.write("    ");

            BinaryTree linkage_tree = analysis.linkage_tree;
            for (int i=(int)start; i<=(int)end; i++) {
                int index = linkage_tree.leaf_ordering.get(i);
                String genes = db.features.get(index).getFormattedFeatureName(analysis);
                double mid = feature_height*(i-start) + feature_height/2.0 + font_weight/2.0;
    
      out.write("\n");
      out.write("                <text id=\"label_");
      out.print(i);
      out.write("\" x=\"0\" y=\"");
      out.print(mid);
      out.write("\" font-family=\"Verdana\" font-size=\"");
      out.print(font_weight);
      out.write("\" fill=\"black\" style=\"display: inline; \" onclick=\"toggleSelection('");
      out.print(index);
      out.write("', '");
      out.print(genes);
      out.write("', 'label_");
      out.print(i);
      out.write("')\">");
      out.print(genes);
      out.write("</text>\n");
      out.write("    ");

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
