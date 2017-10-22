package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.ArrayList;
import structure.AnalysisContainer;

public final class createFeatureList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    
    String list_name = "";
    
    if (mode.equals("process")) {
    
        list_name = request.getParameter("list_name");
        list_name = list_name.trim();
        
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
        if (analysis.filterListMap.containsKey(list_name)) {
            String url = "createFeatureList.jsp?mode=name_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        } else {
            ArrayList <Integer> newFeatureList = new ArrayList <Integer> ();
            analysis.filterListMap.put(list_name, newFeatureList);
        }
    }
    
    if (mode.equals("name_error")) {
        list_name = request.getParameter("list_name");
    }

      out.write('\n');
      out.write('\n');
 if (mode.equals("input") || mode.equals("name_error")) {  
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    \n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        \n");
      out.write("        function submitCreateRequest(){\n");
      out.write("            \n");
      out.write("            var value1 = document.getElementById(\"list_name\").value;\n");
      out.write("            if(value1 == \"\"){\n");
      out.write("                alert('Please provide a list name.');\n");
      out.write("            } else {\n");
      out.write("                createFeatureList();\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function createFeatureList() {\n");
      out.write("            document.getElementById(\"mode\").value = \"process\";\n");
      out.write("            document.getElementById(\"createFeatureListForm\").setAttribute(\"action\",\"createFeatureList.jsp\");\n");
      out.write("            document.getElementById(\"createFeatureListForm\").submit();\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    \n");
      out.write("    <form name=\"createFeatureListForm\" id=\"createFeatureListForm\" method=\"get\" action=\"\">\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>Create a New Feature List</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        ");
 if (mode.equals("name_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>The name ");
      out.print(list_name);
      out.write(" is already in use. Please specify another name.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Enter a Name for the New List</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <input type=\"text\" id=\"list_name\" name=\"list_name\" size=\"30\">\n");
      out.write("                &nbsp;\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create Feature Lists.\" onclick=\"submitCreateRequest();\">Create</button>\n");
      out.write("                <input type=\"hidden\" name=\"mode\" id=\"mode\" value=\"process\">\n");
      out.write("                <input type=\"hidden\" name=\"analysis_name\" id=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("    </table>\n");
      out.write("    </form>\n");
      out.write("    \n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
  } else if (mode.equals("process")) {  
      out.write("\n");
      out.write("<html>\n");
      out.write("<script>\n");
      out.write("    parent.updateFeatureLists('");
      out.print(list_name);
      out.write("', 1);\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">        \n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\">\n");
      out.write("                Feature list <i>");
      out.print(list_name);
      out.write("</i> created.\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("    </table>\n");
      out.write("</body>\n");
      out.write("</html>\n");
  }  
      out.write('\n');
      out.write('\n');

  
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
