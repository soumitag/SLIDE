package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Exception_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    String msg = request.getParameter("msg");
    if (msg == null) {
        msg = "Oops! Looks like something went wrong. Please retry the last action with different parameters.";
    }
    
    String exception_type = request.getParameter("type");
    if (exception_type == null) {
        exception_type = "";
    }

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <script>\n");
      out.write("            function reportClusteringError() {\n");
      out.write("                alert(\"Hierarchical clustering could not be performed for the data. Please select a different set of data transformation / clustering parameters and try again. This issue could be because the selected transformations produced NaNs or Infs. Note that some data transformations (like aggresive data clippping) can result in rows with all zero values / zero standard deviation. Distance functions such as cosine and correlation cannot be computed for such rows.\");\n");
      out.write("                parent.showGlobal();\n");
      out.write("            }\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    ");
 if (exception_type.equals("clustering_error")) { 
      out.write("\n");
      out.write("    <body onload=\"reportClusteringError()\">\n");
      out.write("    ");
 } else { 
      out.write("\n");
      out.write("    <body>\n");
      out.write("    ");
 } 
      out.write("\n");
      out.write("        <div class=\"exception_msg\">\n");
      out.write("            <label>");
      out.print(msg);
      out.write("</label>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("    \n");
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
