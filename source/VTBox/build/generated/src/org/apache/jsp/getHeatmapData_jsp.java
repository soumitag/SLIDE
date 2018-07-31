package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.util.ArrayList;
import structure.DataCells;
import graphics.HeatmapData;
import vtbox.SessionUtils;
import graphics.Heatmap;
import structure.Data;
import structure.AnalysisContainer;

public final class getHeatmapData_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");


try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    int start = Integer.parseInt(request.getParameter("start"));
    
    String r = request.getParameter("rand");
    
    Heatmap heatmap = analysis.heatmap;
    
    int TABLE_HEIGHT = 760;
    int CELL_HEIGHT = 20;
    
    double BORDER_STROKE_WIDTH = 0.5;
    
    int end = start + 37;
    end = Math.min(end, analysis.database.metadata.nFeatures-1);
    
    String imagename = heatmap.buildMapImage(start, end, "get_heatmap_data_jsp", HeatmapData.TYPE_ARRAY);
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    
    int MIN_TABLE_WIDTH = 500;
    int CELL_WIDTH = (int)Math.max(20.0, Math.floor(MIN_TABLE_WIDTH/rgb.length));
    int TABLE_WIDTH = (int)Math.max(MIN_TABLE_WIDTH, CELL_WIDTH*rgb.length);
    
    double x = 0;
    double y = 0;
    
    //analysis.state_variables.put("detailed_view_start", (double)start);
    //analysis.state_variables.put("detailed_view_end", (double)end);
    analysis.state_variables.setDetailedViewStart(start);
    analysis.state_variables.setDetailedViewEnd(end);
    
    response.setHeader("Cache-Control", "max-age=0,no-cache,no-store,post-check=0,pre-check=0");
    
    DataCells cells = analysis.database.datacells;

      out.write("\n");
      out.write("\n");
      out.write("<svg width='");
      out.print(TABLE_WIDTH);
      out.write("' height='");
      out.print(TABLE_HEIGHT);
      out.write("' id='svg_heat_map' \n");
      out.write("             xmlns:svg=\"http://www.w3.org/2000/svg\"\n");
      out.write("             xmlns=\"http://www.w3.org/2000/svg\"\n");
      out.write("             version=\"1.1\"\n");
      out.write("             width='");
      out.print(TABLE_WIDTH);
      out.write("'\n");
      out.write("             height='");
      out.print(TABLE_HEIGHT);
      out.write("'>\n");
      out.write("\n");
      out.write("    <g id=\"heatmap_table\" width='");
      out.print(TABLE_WIDTH);
      out.write("' height='");
      out.print(TABLE_HEIGHT);
      out.write("'>\n");
      out.write("\n");
      out.write("    ");
 for (int i = 0; i < rgb.length; i++) {
      out.write("\n");
      out.write("        <g id=\"col_rect_");
      out.print(i);
      out.write("\">\n");
      out.write("            ");
 for (int j = 0; j < rgb[0].length; j++) { 
      out.write("\n");
      out.write("            ");

                String color_str = "rgb(" + (int) rgb[i][j][0] + "," + (int) rgb[i][j][1] + "," + (int) rgb[i][j][2] + ")";
                String td_id = "td_" + i + "_" + j;

                x = i * CELL_WIDTH;
                y = j * CELL_HEIGHT;
                
                int original_row_id = analysis.linkage_tree.leaf_ordering.get(start+j);
            
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
      out.write("\">\n");
      out.write("                    <title>\n");
      out.write("                        ");
 if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) { 
      out.write("\n");
      out.write("                        Value: ");
      out.print( cells.dataval[original_row_id][i] );
      out.write("\n");
      out.write("                        ");
 } else { 
      out.write("\n");
      out.write("                        Value: ");
      out.print( cells.dataval[original_row_id][i] );
      out.write("\n");
      out.write("                        ");
 } 
      out.write("\n");
      out.write("                    </title>\n");
      out.write("                </rect>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("        </g>\n");
      out.write("    ");
 }
      out.write("\n");
      out.write("\n");
      out.write("    </g>\n");
      out.write("    \n");
      out.write("</svg>\n");
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
