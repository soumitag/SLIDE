package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.io.FileReader;
import java.io.BufferedReader;

public final class sampleMappingPreview_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    
try {
    
    String metafilename = request.getParameter("file");
    
    BufferedReader br = new BufferedReader(new FileReader(metafilename));
    String[] lineData = null;
    String line;

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <title>Sample Column Mapping Preview</title>\n");
      out.write("        <style>\n");
      out.write("            table {\n");
      out.write("                font-family: arial, sans-serif;\n");
      out.write("                border-collapse: collapse;\n");
      out.write("                width: 100%;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            td, th {\n");
      out.write("                border: 1px solid #dddddd;\n");
      out.write("                text-align: left;\n");
      out.write("                padding: 8px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            tr:nth-child(even) {\n");
      out.write("                background-color: #dddddd;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <table style=\"border:1px solid black\">\n");
      out.write("            <tr>\n");
      out.write("                <th>Column Name</th>\n");
      out.write("                <th>Sample Group</th>\n");
      out.write("                <th>Time Point</th>\n");
      out.write("            </tr>\n");
      out.write("    ");

        while ((line = br.readLine()) != null) {

            if (line.startsWith("#") || line.equals("")) {

                continue;

            } else {

                lineData = line.split("\t");

                String datacol_header = lineData[0].trim();
                String sample_groupname = lineData[1].trim();
                String timestamp = lineData[2].trim();
    
      out.write("\n");
      out.write("    \n");
      out.write("        <tr>\n");
      out.write("            <td>");
      out.print(datacol_header);
      out.write("</td>\n");
      out.write("            <td>");
      out.print(sample_groupname);
      out.write("</td>\n");
      out.write("            <td>");
      out.print(timestamp);
      out.write("</td>\n");
      out.write("        </tr>\n");
      out.write("    \n");
      out.write("    ");

            }
        }
    
      out.write("\n");
      out.write("        </table>\n");
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
