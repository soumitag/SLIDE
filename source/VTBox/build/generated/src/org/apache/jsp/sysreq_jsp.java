package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import vtbox.SessionUtils;

public final class sysreq_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

try {
    
    String browser_app = request.getParameter("browser_app");
    String browser_res_w = request.getParameter("browser_res_w");
    String browser_res_h = request.getParameter("browser_res_h");
    
    int browser_resolution_x = Integer.parseInt(browser_res_w);
    int browser_resolution_y = Integer.parseInt(browser_res_h);
    
    ArrayList <String> supported_browsers = new ArrayList <String> ();
    supported_browsers.add("Internet Explorer".toUpperCase());

      out.write("\n");
      out.write("\n");
      out.write("    <html>\n");
      out.write("        <head>\n");
      out.write("            <title>System Specifications</title>\n");
      out.write("            <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("            <script>\n");
      out.write("                function closeButton(){\n");
      out.write("                    var modal = parent.document.getElementById('myModal');\n");
      out.write("                    var btnval = document.getElementById('btnok');\n");
      out.write("                    window.onclick = function(event){\n");
      out.write("                        if(event.target == btnval)\n");
      out.write("                            modal.style.display = \"none\";\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            </script>\n");
      out.write("            \n");
      out.write("        </head>\n");
      out.write("        \n");
      out.write("        ");

                     
        
      out.write("\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("        <table>\n");
      out.write("        <tr>\n");
      out.write("            <td colspan=\"2\">       \n");
      out.write("            \n");
      out.write("            <label class=\"sys_msg\">\n");
      out.write("                ");
 if (browser_resolution_x < 1919 || browser_resolution_y < 1079) { 
      out.write("\n");
      out.write("                    SLIDE works best at a resolution of 1920&times;1040 or higher. \n");
      out.write("                    Your current browser resolution is ");
      out.print(browser_resolution_x);
      out.write("&times;");
      out.print(browser_resolution_y);
      out.write(". \n");
      out.write("                    To get the best performance try changing your browser's zoom.\n");
      out.write("                    Note that changing the zoom will affect all open tabs in this browser window.\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                <br>\n");
      out.write("            </label>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td colspan=\"2\">\n");
      out.write("            &nbsp;\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td colspan=\"2\" align=\"center\">\n");
      out.write("                <button class=\"dropbtn\" id=\"btnok\" title=\"OK\" onclick=\"closeButton()\">&nbsp;&nbsp;Ok&nbsp;&nbsp;</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        </table>\n");
      out.write("        \n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("    </html>\n");
      out.write("    \n");

  
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
