package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.layouts.VizualizationHomeLayout;
import vtbox.SessionUtils;
import structure.AnalysisContainer;

public final class displayHome_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    String analysis_name = request.getParameter("analysis_name");
    String isDemo = request.getParameter("isDemo");
    String load_type = request.getParameter("load_type");
    if (load_type != null && (load_type.equalsIgnoreCase("reopen") || load_type.equalsIgnoreCase("file"))) {
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        analysis.hac.clearCache();
    }
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    VizualizationHomeLayout viz_layout = analysis.visualization_params.viz_layout;

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <title>");
      out.print(analysis_name);
      out.write("</title>\n");
      out.write("        <script>\n");
      out.write("            function toggleSelectionPanel() {\n");
      out.write("                var sp = document.getElementById(\"selectionPanel\");\n");
      out.write("                var sph = document.getElementById(\"spHandle\");\n");
      out.write("                if (sp.style.display === 'inline') {\n");
      out.write("                    sp.style.display = 'none';\n");
      out.write("                    sph.innerHTML = 'Show Control Panel';\n");
      out.write("                } else {\n");
      out.write("                    sp.style.display = 'inline';\n");
      out.write("                    sph.innerHTML = 'Hide Control Panel';\n");
      out.write("                }\n");
      out.write("             }\n");
      out.write("        </script>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <div style=\"position: absolute; top: 0px; left: 0px; width: 100%; height: 99%\"> \n");
      out.write("            <div style=\"position: relative; top: 0px; left: 0px; width: 300px; height: 100%; float:left; border-style: solid; border-color: #E1E1E1\"> \n");
      out.write("                <iframe name=\"selectionPanel\" id=\"selectionPanel\" src=\"selectionPanel.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&load_type=");
      out.print(load_type);
      out.write("&isDemo=");
      out.print(isDemo);
      out.write("\" marginwidth=\"0\" height=\"100%\" width=\"300\" frameBorder=\"0\" style=\"display: inline; position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("            </div>\n");
      out.write("            <div style=\"position: relative; top: 0px; left: 0px; width: calc(100% - 310px); height: 100%; float:left;\"> \n");
      out.write("                <iframe name=\"visualizationPanel\" id=\"visualizationPanel\" src=\"visualizationHome.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" marginwidth=\"0\" height=\"100%\" width=\"100%\" frameBorder=\"0\"></iframe>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        \n");
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
