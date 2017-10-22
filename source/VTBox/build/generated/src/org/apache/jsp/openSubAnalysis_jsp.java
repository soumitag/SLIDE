package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public final class openSubAnalysis_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    
    String delete_analysis_name = request.getParameter("delete_analysis_name");
    if (mode.equals("delete")) {
        session.removeAttribute(delete_analysis_name);
    }
    
    ArrayList <String> sub_analyses_names = new ArrayList <String> ();
    Enumeration attributeNames = session.getAttributeNames();
    while(attributeNames.hasMoreElements()) {
        String sub_analysis_name = (String) attributeNames.nextElement();
        if(session.getAttribute(sub_analysis_name) instanceof structure.AnalysisContainer)  {
            if (!sub_analysis_name.equalsIgnoreCase(analysis_name)) {
                sub_analyses_names.add(sub_analysis_name);
            }
        }
    }

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        \n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        \n");
      out.write("        <title> Open / Delete Sub-Analysis </title>\n");
      out.write("        \n");
      out.write("        <style>\n");
      out.write("\n");
      out.write("            table {\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-collapse: collapse;\n");
      out.write("                width: 100%;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            td, th {\n");
      out.write("                border: 1px solid #dddddd;\n");
      out.write("                padding: 8px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            tr:nth-child(even) {\n");
      out.write("                background-color: #efefef;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("            \n");
      out.write("            ");
  if (mode.equals("delete")) { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td width=\"100%\" height=\"35\" align=\"center\" colspan=\"3\">\n");
      out.write("                    <b><label>Sub-analysis <i>");
      out.print(delete_analysis_name);
      out.write("</i> deleted.</label></b>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
 } else { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td width=\"100%\" height=\"35\" align=\"center\" colspan=\"3\">\n");
      out.write("                    &nbsp;\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            \n");
      out.write("            ");
  for (int i=0; i<sub_analyses_names.size(); i++)  {    
      out.write("\n");
      out.write("        \n");
      out.write("            <tr>\n");
      out.write("                <td width=\"50%\" height=\"35\" align=\"center\">\n");
      out.write("                    <b><label>");
      out.print(sub_analyses_names.get(i));
      out.write("</label></b>\n");
      out.write("                </td>\n");
      out.write("                <td align=\"center\">\n");
      out.write("                    <button name=\"openSub\" class=\"dropbtn\" id=\"openSub\" onclick=\"window.open('");
      out.print(base_url);
      out.write("displayHome.jsp?analysis_name=");
      out.print(sub_analyses_names.get(i));
      out.write("')\">Open</button>\n");
      out.write("                </td>\n");
      out.write("                <td align=\"center\">\n");
      out.write("                    <button name=\"deleteSub\" class=\"dropbtn\" id=\"deleteSub\" onclick=\"location.href='");
      out.print(base_url);
      out.write("openSubAnalysis.jsp?mode=delete&delete_analysis_name=");
      out.print(sub_analyses_names.get(i));
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("'\">Delete</button>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("        \n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td width=\"100%\" height=\"35\" align=\"center\" colspan=\"3\">\n");
      out.write("                    To save a sub-analysis open it and click the \"Save Analysis\" button.\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("        </table>\n");
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
