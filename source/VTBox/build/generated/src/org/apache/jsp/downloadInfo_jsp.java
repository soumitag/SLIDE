package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;

public final class downloadInfo_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
try {
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Saving Analysis</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <script>\n");
      out.write("                function closeButton(){\n");
      out.write("                    var modal = parent.document.getElementById('myModal');\n");
      out.write("                    var btnval = document.getElementById('btnok');\n");
      out.write("                    window.onclick = function(event){\n");
      out.write("                        if(event.target == btnval)\n");
      out.write("                            modal.style.display = \"none\";\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <table>\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                ");
 if (mode.equals("start")) { 
      out.write("\n");
      out.write("                <label class=\"sys_msg\"> \n");
      out.write("                    Preparing <i>");
      out.print(analysis_name);
      out.write(".slide </i> file for download. \n");
      out.write("                    Choose the <i>Save</i> option when prompted by your browser. \n");
      out.write("                    Meanwhile, you can close this window and continue using SLIDE.\n");
      out.write("                    <br>\n");
      out.write("                    <br>\n");
      out.write("                </label>\n");
      out.write("                ");
 } else if (mode.equals("completed")) { 
      out.write("\n");
      out.write("                \n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td colspan=\"2\">\n");
      out.write("            &nbsp;\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td align=\"center\">\n");
      out.write("                <button class=\"dropbtn\" id=\"btnok\" title=\"OK\" onclick=\"closeButton()\">&nbsp;&nbsp;OK&nbsp;&nbsp;</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
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
