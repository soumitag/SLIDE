package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;

public final class help_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("        <title>SLIDE Help</title>\n");
      out.write("        <script>\n");
      out.write("\t\t\t\n");
      out.write("\t\t\tfunction giveManual() {\n");
      out.write("\t\t\t\tvar win = window.open(\"https://github.com/soumitag/SLIDE/raw/master/application/slide/SLIDE_Users_Manual.pdf\", '_blank');\n");
      out.write("\t\t\t\twin.focus();\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\n");
      out.write("\t\t\tfunction giveDemoDataFile() {\n");
      out.write("\t\t\t\tvar win = window.open(\"https://github.com/soumitag/SLIDE/blob/master/data/GSE42641_data_formatted_final_small_114.txt\", '_blank');\n");
      out.write("\t\t\t\twin.focus();\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\n");
      out.write("\t\t\tfunction giveDemoMAppingFile() {\n");
      out.write("\t\t\t\tvar win = window.open(\"https://github.com/soumitag/SLIDE/blob/master/data/GSE42638_SampleMappings.txt\", '_blank');\n");
      out.write("\t\t\t\twin.focus();\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\n");
      out.write("\t\t\tfunction takeToIssues() {\n");
      out.write("\t\t\t\tvar win = window.open(\"https://github.com/soumitag/SLIDE/issues\", '_blank');\n");
      out.write("\t\t\t\twin.focus();\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\n");
      out.write("\t\t</script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <table class=\"input\" style=\"width: 100%\">\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <button class=\"dropbtn\" id=\"download_manual\" title=\"OK\" onclick=\"giveManual()\">Download User Manual</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("\t\t\t\t<button class=\"dropbtn\" id=\"download_manual\" title=\"OK\" onclick=\"takeToIssues()\">Report An Issue</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("\t\t\t\t<button class=\"dropbtn\" id=\"download_manual\" title=\"OK\" onclick=\"giveDemoDataFile()\">Download Example Data File</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\t\t<tr>\n");
      out.write("            <td>\n");
      out.write("\t\t\t\t<button class=\"dropbtn\" id=\"download_manual\" title=\"OK\" onclick=\"giveDemoMAppingFile()\">Download Example Sample Mapping File</button>\n");
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
