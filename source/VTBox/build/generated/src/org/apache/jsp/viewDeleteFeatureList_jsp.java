package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import structure.AnalysisContainer;
import java.util.ArrayList;
import java.util.HashMap;

public final class viewDeleteFeatureList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>View/Delete Feature List</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <script>\n");
      out.write("            function viewFeatureList(list_name){\n");
      out.write("                window.location.href = \"viewFeatureList.jsp?feature_list_name=\" + list_name + \"&analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function deleteFeatureList(list_name){\n");
      out.write("                window.location.href = \"confirmFeatureListDeletion.jsp?feature_list_name=\" + list_name + \"&analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("            }\n");
      out.write("        \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <form name=\"viewDeleteFilteredListForm\" id=\"viewDeleteFilteredListForm\" >\n");
      out.write("            <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("                <tr>\n");
      out.write("                <td height=\"75\" align=\"center\">\n");
      out.write("                    <b><label>Feature List</label></b>\n");
      out.write("                </td>\n");
      out.write("                 <td height=\"75\" align=\"center\">\n");
      out.write("                    <b><label>View/Delete</label></b>\n");
      out.write("                </td>\n");
      out.write("                </tr>\n");
      out.write("                \n");
      out.write("                ");
 
                    HashMap <String, ArrayList <Integer>> filterList = analysis.filterListMap;
                    Iterator it = filterList.entrySet().iterator();
                    while (it.hasNext()) { 
                        String featurelist_name = ((Map.Entry)it.next()).getKey().toString();
                
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                <td height=\"75\" align=\"center\">\n");
      out.write("                    <label>");
      out.print(featurelist_name);
      out.write("</label>\n");
      out.write("                    \n");
      out.write("                </td>\n");
      out.write("                 <td height=\"75\" align=\"center\">\n");
      out.write("                    <button type=\"button\" class=\"dropbtn\" title=\"View Feature List\" id=\"");
      out.print(featurelist_name);
      out.write("_view_btn\" onclick=\"viewFeatureList('");
      out.print(featurelist_name);
      out.write("')\">View</button>\n");
      out.write("                    &nbsp;\n");
      out.write("                    <button type=\"button\" class=\"dropbtn\" title=\"Delete Feature List\" id=\"");
      out.print(featurelist_name);
      out.write("_del_btn\" onclick=\"deleteFeatureList('");
      out.print(featurelist_name);
      out.write("')\">Delete</button>\n");
      out.write("                </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
 }  
      out.write("\n");
      out.write("            </table>\n");
      out.write("        </form>\n");
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
