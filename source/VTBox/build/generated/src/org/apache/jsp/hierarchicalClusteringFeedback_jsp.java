package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class hierarchicalClusteringFeedback_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');

    String is_clustering = request.getParameter("is_clustering");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <!--<link rel=\"stylesheet\" href=\"vtbox-main.css\">-->\n");
      out.write("        <style>\n");
      out.write("            .tab1 {\n");
      out.write("                margin:auto;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .tab1 td{\n");
      out.write("                text-align:center;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .exception_msg {\n");
      out.write("                text-align: center;\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 14px;\n");
      out.write("                font-weight: normal;\n");
      out.write("                font-style: italic;\n");
      out.write("                color: #BBB;\n");
      out.write("                text-align: center;\n");
      out.write("                position: absolute;\n");
      out.write("                width: 100%;\n");
      out.write("                top: 50px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <table border=\"0\" style=\"width: 285px\" class=\"tab1\">\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <img src=\"images/loader1.gif\" height=\"207\" width=\"285\" border=\"0\" />\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td class=\"exception_msg\" style=\"top: 130px; width: 285px\">   \n");
      out.write("                    ");
 if (is_clustering.equalsIgnoreCase("true")) { 
      out.write("\n");
      out.write("                    <!--<div class=\"exception_msg\" style=\"top: 130px\">\n");
      out.write("                    <label>-->\n");
      out.write("                        Hierarchical Clustering is running.  \n");
      out.write("                        For large datasets this might take several minutes.\n");
      out.write("                        Please do not close this window.\n");
      out.write("                    <!--</label>\n");
      out.write("                    </div>-->\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </td>                \n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("</html>\n");
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
