package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.ArrayList;
import structure.AnalysisContainer;
import structure.Data;

public final class selectionPanel_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    Data database = analysis.database;
    String data_min = String.format("%.3f", database.DATA_MIN_MAX[0]);
    String data_max = String.format("%.3f", database.DATA_MIN_MAX[1]);

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <style>\n");
      out.write("            td {\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 12px;\n");
      out.write("                background-color:#EEEEEE;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        <script>\n");
      out.write("            function chkisnumber(f){\n");
      out.write("                \n");
      out.write("                var x = document.getElementById(f.name).value;\n");
      out.write("                \n");
      out.write("                if(isNaN(parseFloat(x)) || !isFinite(x)){                \n");
      out.write("                    document.getElementById(f.name).value = \"0\";\n");
      out.write("                    alert(\"Please enter a numeric value\");\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function enabletext(){\n");
      out.write("                document.getElementById(\"txtRangeStart\").disabled = false;\n");
      out.write("                document.getElementById(\"txtRangeEnd\").disabled = false;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function disabletext(){\n");
      out.write("                document.getElementById(\"txtRangeStart\").disabled = true;\n");
      out.write("                document.getElementById(\"txtRangeEnd\").disabled = true;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function checkDataMin() {\n");
      out.write("                var curr = document.getElementById(\"log2flag\").checked;\n");
      out.write("                if (curr) {\n");
      out.write("                    var data_min = ");
      out.print(database.RAW_DATA_MIN);
      out.write(";\n");
      out.write("                    if (data_min < 0.0) {\n");
      out.write("                        alert(\"Data has negative values. Log base 2 transformation cannot be applied.\");\n");
      out.write("                        document.getElementById(\"log2flag\").checked = false;\n");
      out.write("                    } else if (data_min === 0.0) {\n");
      out.write("                        alert(\"Data has zero values. A small positive offset (2 raised to the power -149) will be added to all cells. If you do not wish to do this, uncheck the log base 2 transformation option.\");\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("        <form name=\"SelectionForm\" method=\"get\" action=\"AnalysisReInitializer\" target=\"visualizationPanel\"> \n");
      out.write("            <input type=\"hidden\" name=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\" />\n");
      out.write("            <input type=\"hidden\" name=\"do_clustering\" value=\"false\" />\n");
      out.write("            <table id=\"dataselectionTable\" width=\"100%\" border=0 align=center cellpadding=\"2px\" cellspacing=\"2px\"  style=\"padding-left: 5px; padding-right: 5px;\">\n");
      out.write("            \n");
      out.write("            ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Data Selection</b> </td></tr>\n");
      out.write("            <!--\n");
      out.write("            <tr> \n");
      out.write("                <td align=\"center\" height=\"30px\">\n");
      out.write("                    <input type=\"button\" value=\"Open Data Selection Panel\">\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Replicate Handling: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"repHandle\" value=\"0\" checked=\"checked\"> Use Separately<br>\n");
      out.write("                    <input type=\"radio\" name=\"repHandle\" value=\"1\"> Mean<br>\n");
      out.write("                    <input type=\"radio\" name=\"repHandle\" value=\"2\"> Median\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label>Data Clipping: </label></b> &nbsp; \n");
      out.write("                    <select id=\"clippingType\" name=\"clippingType\">\n");
      out.write("                        <option id=\"none\" value=\"none\" >None</option>\n");
      out.write("                        <option id=\"absv\" value=\"absv\" >Absolute Value</option>\n");
      out.write("                        <option id=\"ptile\" value=\"ptile\" >Percentile</option>\n");
      out.write("                    </select>\n");
      out.write("                    <br><br>\n");
      out.write("                    Min &nbsp; <input type=\"text\" id=\"txtClipMin\" name=\"txtClipMin\" size=\"5\" onchange=\"chkisnumber(this)\" />\n");
      out.write("                    &nbsp; \n");
      out.write("                    Max &nbsp; <input type=\"text\" id=\"txtClipMax\" name=\"txtClipMax\" size=\"5\" onchange=\"chkisnumber(this)\" />\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <input type=\"checkbox\" id=\"log2flag\" name=\"log2flag\" onclick=\"checkDataMin()\"> Perform log base 2 transformation</input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Column Scaling: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"0\" checked=\"checked\"> None <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"1\"> Scale Columns to 0-1 <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"2\"> Make Columns Standard Normal <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"3\"> Pareto Scaling  <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Row Scaling: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"0\" checked=\"checked\"> None <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"1\"> Scale Rows to 0-1 <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"2\"> Mean Center Rows <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"3\"> Make Rows Standard Normal <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            ");
 if (database.isTimeSeries) { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label>Group By: </label></b> &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"groupBy\" value=\"sample\" checked=\"checked\"> Sample &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"groupBy\" value=\"time\"> Time<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            \n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            ");
  if (analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Enrichment Parameters</b> </td> </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label style=\"line-height: 20px\">Significance Level: </label></b>\n");
      out.write("                    <input type=\"text\" name=\"txtSignificanceLevel\" value=\"0.05\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label style=\"line-height: 25px\"> Minimum Functional Group Feature List Intersection: </label></b>\n");
      out.write("                    <input type=\"text\" name=\"txtSmall_k\" value=\"0\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label style=\"line-height: 25px; padding-bottom: 10px\">Minimum Functional Group Size: </label></b>\n");
      out.write("                    <input type=\"text\" name=\"txtBig_K\" value=\"0\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("\n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Clustering Parameters</b> </td> </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <input type=\"checkbox\" id=\"do_cluster_flag\" name=\"do_cluster_flag\"> Perform Hierarchical Clustering</input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Linkage Function: </label></b><br>\n");
      out.write("                    <!--<input type=\"radio\" name=\"linkFunc\" value=\"single\" checked=\"checked\"> Single<br>-->\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"average\" checked=\"checked\"> Average<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"complete\"> Complete<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"median\"> Median<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"centroid\"> Centroid<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"ward\"> Ward<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"weighted\"> Weighted<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label>Distance Function: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"euclidean\" checked=\"checked\"> Euclidean<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"cityblock\"> Manhattan<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"cosine\"> Cosine<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"correlation\"> Correlation<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"chebyshev\"> Chebyshev<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Visualization Controls</b> </td></tr>\n");
      out.write("            \n");
      out.write("            ");
 // if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Data Scaling for Visualization: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"normRules_Heatmap\" value=\"0\" checked=\"checked\"> None <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRules_Heatmap\" value=\"2\"> Mean Center Rows <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRules_Heatmap\" value=\"3\"> Make Rows Standard Normal <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRules_Heatmap\" value=\"1\"> Scale Rows to 0-1 Range <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            ");
 // }   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Number of Color Bins: </label></b>\n");
      out.write("                    <input type=\"text\" name=\"txtNBins\" value=\"21\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Binning Range: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"data_bins\" checked=\"checked\" onclick=\"disabletext()\"> Use Min/Max of Data<br>\n");
      out.write("                    <p style=\"line-height: 10px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      out.write("                    Min <input type=\"text\" id=\"txtMin\" name=\"txtMin\" size=\"5\" value=\"");
      out.print(data_min);
      out.write("\" disabled />\n");
      out.write("                    and Max <input type=\"text\" id=\"txtMax\" name=\"txtMax\" size=\"5\" value=\"");
      out.print(data_max);
      out.write("\" disabled />\n");
      out.write("                    </p>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"symmetric_bins\" onclick=\"disabletext()\"> Use Symmetric Bins (about 0)<br>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"start_end_bins\" onclick=\"enabletext()\"> Use Range <br>\n");
      out.write("                    <p style=\"line-height: 10px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      out.write("                    Start <input type=\"text\" id=\"txtRangeStart\" name=\"txtRangeStart\" size=\"5\" onchange=\"chkisnumber(this)\" disabled />\n");
      out.write("                    and End <input type=\"text\" id=\"txtRangeEnd\" name=\"txtRangeEnd\" size=\"5\" onchange=\"chkisnumber(this)\" disabled />\n");
      out.write("                    </p>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Leaf Ordering: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"0\" checked=\"checked\"> Largest Child First<br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"1\"> Smallest Child First<br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"2\"> Most Diverse Child First<br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"3\"> Least Diverse Child First<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Heatmap Color Scheme: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"colorScheme\" value=\"row\" checked=\"checked\"> Blue-White-Red <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Dendrogram Depth: </label></b>\n");
      out.write("                    <input type=\"text\" name=\"txtDendogramDepth\" value=\"20\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" align=\"center\" style=\"padding: 10px;\"> \n");
      out.write("                    <input type=\"hidden\" id=\"vizType\" name=\"vizType\" value=\"Selection\">\n");
      out.write("                    <button type=\"submit\">Refresh</button>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("        </table>\n");
      out.write("            \n");
      out.write("        </form>\n");
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
