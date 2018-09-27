package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import algorithms.clustering.BinaryTree;
import graphics.layouts.ScrollViewLayout;
import vtbox.SessionUtils;
import java.util.ArrayList;
import structure.CompactSearchResultContainer;
import graphics.Heatmap;
import structure.Data;
import structure.AnalysisContainer;

public final class mapView_jsp extends org.apache.jasper.runtime.HttpJspBase
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


try {
    
    String analysis_name = request.getParameter("analysis_name");
    String imagename = request.getParameter("imagename");
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Data database = analysis.database;
    String[] headers = database.current_sample_names;
    
    Heatmap heatmap = analysis.heatmap;
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    
    int TABLE_HEIGHT = (int)layout.MAP_HEIGHT;
    int CELL_HEIGHT = (int)layout.CELL_HEIGHT;
    double BORDER_STROKE_WIDTH = layout.BORDER_STROKE_WIDTH;
    int CELL_WIDTH = (int)layout.CELL_WIDTH;
    int TABLE_WIDTH = (int)layout.MAP_WIDTH;
    int HEADER_LABEL_SIZE = (int)layout.SAMPLE_LABEL_FONT_SIZE;
    int HEADER_HEIGHT = (int)ScrollViewLayout.COLUMN_HEADER_HEIGHT;


      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <style>\n");
      out.write("        \n");
      out.write("            .map_container{\n");
      out.write("                position: absolute;\n");
      out.write("                border: 0px solid black;\n");
      out.write("                left: 2px; \n");
      out.write("                top: ");
      out.print(HEADER_HEIGHT);
      out.write("px;\n");
      out.write("                width: ");
      out.print(TABLE_WIDTH);
      out.write("px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .header_container{\n");
      out.write("                position: absolute;\n");
      out.write("                border: 0px solid black;\n");
      out.write("                left: 2px; \n");
      out.write("                top: 0px;\n");
      out.write("                width: ");
      out.print(TABLE_WIDTH);
      out.write("px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("    \n");
      out.write("            function updateMap (data) {\n");
      out.write("                var container = document.getElementById('containerDiv');\n");
      out.write("                container.innerHTML = '' + data;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function selectGene(i, j, entrez, genesymbol) {\n");
      out.write("                alert('Entrez: ' + entrez + ', Genesymbol: ' + genesymbol);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function loadHistogram() {\n");
      out.write("                var iframe = opener.document.getElementById('histPanel');\n");
      out.write("                var innerDoc = iframe.contentDocument || iframe.contentWindow.document;\n");
      out.write("                //alert(innerDoc);\n");
      out.write("                \n");
      out.write("                var hist_g = innerDoc.getElementById('histogram_g');\n");
      out.write("                //alert(hist_g);\n");
      out.write("                \n");
      out.write("                var chart = document.getElementById('save_img');\n");
      out.write("                \n");
      out.write("                var hist_g1 = cloneToDoc(hist_g);\n");
      out.write("                //alert(hist_g1);\n");
      out.write("                chart.appendChild(hist_g1);\n");
      out.write("                \n");
      out.write("                hist_g1.transform.baseVal.getItem(0).setTranslate(500,500);\n");
      out.write("                \n");
      out.write("                //alert('Done');\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function cloneToDoc(node,doc){\n");
      out.write("                if (!doc) doc=document;\n");
      out.write("                var clone = doc.createElementNS(node.namespaceURI,node.nodeName);\n");
      out.write("                for (var i=0,len=node.attributes.length;i<len;++i){\n");
      out.write("                  var a = node.attributes[i];\n");
      out.write("                  if (/^xmlns\\b/.test(a.nodeName)) continue; // IE won't set xmlns or xmlns:foo attributes\n");
      out.write("                  clone.setAttributeNS(a.namespaceURI,a.nodeName,a.nodeValue);\n");
      out.write("                }\n");
      out.write("                for (var i=0,len=node.childNodes.length;i<len;++i){\n");
      out.write("                  var c = node.childNodes[i];\n");
      out.write("                  clone.insertBefore(\n");
      out.write("                    c.nodeType==1 ? cloneToDoc(c,doc) : doc.createTextNode(c.nodeValue),\n");
      out.write("                    null\n");
      out.write("                  )\n");
      out.write("                }\n");
      out.write("                return clone;\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body style=\"overflow-y: hidden; margin: 0px\">\n");
      out.write("        <div class=\"header_container\" id='headerDiv' border=\"0\">\n");
      out.write("        <svg id='svg_header' \n");
      out.write("             xmlns:svg=\"http://www.w3.org/2000/svg\"\n");
      out.write("             xmlns=\"http://www.w3.org/2000/svg\"\n");
      out.write("             version=\"1.1\"\n");
      out.write("             width=\"");
      out.print(TABLE_WIDTH);
      out.write("\"\n");
      out.write("             height=\"");
      out.print(HEADER_HEIGHT);
      out.write("\">\n");
      out.write("        \n");
      out.write("        <g id=\"heatmap_header\">\n");
      out.write("            ");
 double hy = HEADER_HEIGHT-5; 
      out.write("\n");
      out.write("            ");
 for(int i = 0; i < headers.length; i++) { 
      out.write("\n");
      out.write("                ");

                    double hx_text = (int)((i+0.5)*CELL_WIDTH) + HEADER_LABEL_SIZE/2.0;
                
      out.write("\n");
      out.write("                <text x='");
      out.print(hx_text);
      out.write("' y='");
      out.print(hy);
      out.write("' font-family=\"Verdana\" font-size=\"");
      out.print(HEADER_LABEL_SIZE);
      out.write("\" fill=\"black\" transform=\"rotate(-90 ");
      out.print(hx_text);
      out.write(' ');
      out.print(hy);
      out.write(")\"> ");
      out.print(headers[i]);
      out.write(" </text>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("        </g>\n");
      out.write("        </svg>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        <div class=\"map_container\" id='containerDiv' border=\"0\">\n");
      out.write("        <svg id='svg_heat_map' \n");
      out.write("             xmlns:svg=\"http://www.w3.org/2000/svg\"\n");
      out.write("             xmlns=\"http://www.w3.org/2000/svg\"\n");
      out.write("             version=\"1.1\"\n");
      out.write("             width='");
      out.print(TABLE_WIDTH);
      out.write("'\n");
      out.write("             height='");
      out.print(TABLE_HEIGHT);
      out.write("'>\n");
      out.write("            \n");
      out.write("            ");
 
                double x = 0;
                double y = 0;
            
      out.write("\n");
      out.write("            \n");
      out.write("            <g id=\"heatmap_table\" width='");
      out.print(TABLE_WIDTH);
      out.write("' height='");
      out.print(TABLE_HEIGHT);
      out.write("'>\n");
      out.write("            \n");
      out.write("                ");
 for(int i = 0; i < rgb.length; i++) { 
      out.write("\n");
      out.write("                    <g id=\"col_rect_");
      out.print(i);
      out.write("\">\n");
      out.write("                    ");
 for(int j = 0; j < rgb[0].length; j++) { 
      out.write("\n");
      out.write("                    ");

                        String color_str = "rgb(" + (int)rgb[i][j][0] + "," + (int)rgb[i][j][1] +  "," + (int)rgb[i][j][2] + ")";
                        String td_id = "td_" + i + "_" + j;
                        
                        x = i*CELL_WIDTH;
                        y = j*CELL_HEIGHT;
                        
                        int original_row_id = analysis.linkage_tree.leaf_ordering.get(j);
                    
      out.write("\n");
      out.write("                        <rect x='");
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
      out.write("                            <title>\n");
      out.write("                                ");
 if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) { 
      out.write("\n");
      out.write("                                Value: ");
      out.print( analysis.database.datacells.dataval[original_row_id][i] );
      out.write("\n");
      out.write("                                ");
 } else { 
      out.write("\n");
      out.write("                                Value: ");
      out.print( analysis.database.datacells.dataval[original_row_id][i] );
      out.write("\n");
      out.write("                                ");
 } 
      out.write("\n");
      out.write("                            </title>\n");
      out.write("                        </rect>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                    </g>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("            </g>\n");
      out.write("        </svg>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("    </body>\n");
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
