package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import searcher.GeneObject;
import structure.MetaData;
import vtbox.SessionUtils;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import structure.AnalysisContainer;
import java.util.ArrayList;
import java.util.HashMap;

public final class viewFeatureList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    String feature_list_name = request.getParameter("feature_list_name");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>View Feature List</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("        <script>\n");
      out.write("            function backtoList(){\n");
      out.write("                //alert(\"21\");\n");
      out.write("                window.location.href = \"viewDeleteFeatureList.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("            }\n");
      out.write("        \n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <form name=\"viewFeatureListForm\" id=\"viewFeatureListForm\" >\n");
      out.write("            <button type=\"button\" class=\"dropbtn\" title=\"Back to Feature List\" id=\"btnBack\" onclick=\"backtoList()\">Back</button>\n");
      out.write("            <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("                \n");
      out.write("                ");
 
                    HashMap <String, ArrayList <Integer>> filterList = analysis.filterListMap;
                    ArrayList <Integer> filterListIndices = filterList.get(feature_list_name);
                
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td height=\"20\" align=\"left\">\n");
      out.write("                    <b><label>");
      out.print(feature_list_name);
      out.write("</label></b>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
 for (int i=0; i < filterListIndices.size(); i++){ 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    ");
 
                       
                       String genesymbol = analysis.database.features.get(filterListIndices.get(i)).identifier;
                       String entrez = analysis.database.features.get(filterListIndices.get(i)).entrez;
                       
                       /*
                       int index = filterListIndices.get(i);
                       String entrez = analysis.database.metadata.getFeature(index, GeneObject.ENTREZ);
                       String genesymbol = analysis.database.metadata.getFeature(index, GeneObject.GENESYMBOL);
                       */
                    
      out.write("\n");
      out.write("                    <td height=\"20\" align=\"left\">\n");
      out.write("                        <label>");
      out.print(genesymbol);
      out.write(',');
      out.write(' ');
      out.print(entrez);
      out.write("</label>\n");
      out.write("                    </td>\n");
      out.write("                    \n");
      out.write("                </tr>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </table>\n");
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
