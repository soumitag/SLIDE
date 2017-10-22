package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import utils.SessionManager;
import utils.ReadConfig;
import java.util.ArrayList;
import utils.Utils;
import java.io.File;;

public final class inputPreview_jsp extends org.apache.jasper.runtime.HttpJspBase
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
        

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>File Preview</title>\n");
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
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    ");

        
        String filename_in = request.getParameter("file");
        String analysis_name = request.getParameter("analysis_name"); 
        
        //String tempFolder = pageContext.getServletContext().getRealPath("") + File.separator + "temp" + File.separator + request.getSession().getId();
        String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
        String tempFolder = installPath + File.separator + "temp" + File.separator + request.getSession().getId();
        String filename = tempFolder + File.separator + analysis_name + "_" + filename_in;
        
        String headerchk = request.getParameter("head");
        boolean hasHeader = true;
        if(headerchk == null || headerchk.equals("off") || headerchk.equals("false")){
            hasHeader = false;
        } else {
            hasHeader = true;
        }
      
        String delimval = request.getParameter("delim");
        String fileDelimiter = "";
        if(delimval.equals("commaS")){
            fileDelimiter = ",";
        } else if (delimval.equals("tabS")) {
            fileDelimiter = "\t";
        } else if (delimval.equals("spaceS")){
            fileDelimiter = " ";
        } else if (delimval.equals("semiS")) {
            fileDelimiter = ";";
        } else if (delimval.equals("hyphenS")) {
            fileDelimiter = "-";
        }
        
    
      out.write("\n");
      out.write("    \n");
      out.write("    ");

        String[] colheaders = null;
        String[][] data = Utils.loadDelimData(filename, fileDelimiter, false, 12);
        int start_row = 0;
        if (hasHeader) {
            colheaders = data[0];
            start_row = 1;
        }
        int nCols = data[0].length;
    
      out.write("\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("        <table style=\"border:1px solid black\">\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                ");
 for(int j = 0; j < nCols; j++) { 
      out.write("\n");
      out.write("                    ");
 if (hasHeader) { 
      out.write("\n");
      out.write("                            <th>");
      out.print(colheaders[j]);
      out.write("</th>\n");
      out.write("                    ");
} else { 
      out.write("\n");
      out.write("                            <th>");
      out.print(("Column_" + j));
      out.write("</th>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            ");
 for(int i = start_row; i < start_row + 11; i++) { 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    ");
 for(int j = 0; j < nCols; j++) { 
      out.write("\n");
      out.write("                        <td>");
      out.print(data[i][j]);
      out.write("</td>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            \n");
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
