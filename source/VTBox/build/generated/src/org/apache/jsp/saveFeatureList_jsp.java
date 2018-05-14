package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import structure.AnalysisContainer;
import java.util.ArrayList;
import java.util.HashMap;

public final class saveFeatureList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    
try {
    
    String mode = request.getParameter("mode");
    
    String filename = "";
    if (mode.equals("save")) {
        filename = request.getParameter("filename");
        String delimval = request.getParameter("delimiter");
        
        String delimiter = null;
        if (delimval.equals("lineS")) {
            delimiter = "line";
        } else if (delimval.equals("commaS")) {
            delimiter = ",";
        } else if (delimval.equals("tabS")) {
            delimiter = "\t";
        } else if (delimval.equals("spaceS")) {
            delimiter = " ";
        } else if (delimval.equals("semiS")) {
            delimiter = ";";
        } else if (delimval.equals("hyphenS")) {
            delimiter = "-";
        }
        
        
    }

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\n");
 if (mode.equals("input")) {  
      out.write('\n');

    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

      out.write("\n");
      out.write("    <head>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("\n");
      out.write("            function submitSaveFeatureListRequest_3(){\n");
      out.write("                \n");
      out.write("                var e1 = document.getElementById(\"list_name\");\n");
      out.write("                var v1 = e1.options[e1.selectedIndex].value;\n");
      out.write("                //var e3 = document.getElementById(\"delimS\");\n");
      out.write("                //var v3 = e3.options[e3.selectedIndex].value;\n");
      out.write("                var e2 = document.getElementById(\"selectedIdentifiers\");\n");
      out.write("                var v2 = e2.options[e2.selectedIndex].value;\n");
      out.write("                \n");
      out.write("                if (v1 === \"\" || v1 === \"dashS\"){\n");
      out.write("                    alert('Please select a list to save.');\n");
      out.write("                } else if (v2 === \"\" || v2 === \"dashS\") {\n");
      out.write("                    alert('Please select identifiers to save.');\n");
      out.write("                } else {\n");
      out.write("                    document.getElementById(\"mode\").value = \"save\";\n");
      out.write("                    document.getElementById(\"filename\").value = v1;\n");
      out.write("                    //document.getElementById(\"delimiter\").value = v3;\n");
      out.write("                    document.getElementById(\"identifier\").value = v2;\n");
      out.write("                    document.getElementById(\"saveFeatureListForm\").submit();\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("            }\n");
      out.write("\n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("        <form name=\"saveFeatureListForm\" id=\"saveFeatureListForm\" method=\"get\" action=\"SerializeFeatureList\">\n");
      out.write("        <table>\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>Select Feature List To Save</label></b>\n");
      out.write("                </td>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    <select name=\"list_name\" id=\"list_name\">\n");
      out.write("                        <option value=\"dashS\" >-</option>\n");
      out.write("                        ");
 Iterator it = analysis.filterListMap.entrySet().iterator();  
      out.write("\n");
      out.write("                        ");
 while (it.hasNext()) {   
      out.write("\n");
      out.write("                            ");
 String featurelist_name = ((Map.Entry)it.next()).getKey().toString(); 
      out.write("\n");
      out.write("                            <option value=\"");
      out.print(featurelist_name);
      out.write("\" >");
      out.print(featurelist_name);
      out.write("</option>\n");
      out.write("                        ");
 }    
      out.write("\n");
      out.write("                    </select>\n");
      out.write("                    <input type=\"hidden\" name=\"filename\" id=\"filename\" />\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>Select Identifiers To Save</label></b>\n");
      out.write("                </td>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    <select name=\"selectedIdentifiers\" id=\"selectedIdentifiers\">\n");
      out.write("                        <option value=\"dashS\" >-</option>\n");
      out.write("                        <option id=\"identifier_only\" value=\"identifier_only\" >Row Identifiers Only</option>\n");
      out.write("                        <option id=\"entrez_only\" value=\"entrez_only\" >Entrez IDs Only</option>\n");
      out.write("                        <option id=\"both\" value=\"both\" >Both Row Identifiers and Entrez IDs</option>\n");
      out.write("                    </select>\n");
      out.write("                    <input type=\"hidden\" name=\"identifier\" id=\"identifier\" />\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td> \n");
      out.write("                    <b><label>Select File Delimiter</label></b>                    \n");
      out.write("                </td>\n");
      out.write("                <td>\n");
      out.write("                    <select id=\"delimS\" name=\"delimS\">\n");
      out.write("                        <option id=\"hyphenS\" value=\"hyphenS\" >-</option>\n");
      out.write("                        <option id=\"lineS\" value=\"lineS\" >Line</option>\n");
      out.write("                        <option id=\"commaS\" value=\"commaS\" >Comma</option>\n");
      out.write("                        <option id=\"tabS\" value=\"tabS\" >Tab</option>\n");
      out.write("                        <option id=\"semiS\" value=\"semiS\">Semicolon</option>\n");
      out.write("                    </select>\n");
      out.write("                    <input type=\"hidden\" name=\"delimiter\" id=\"delimiter\" />\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    <input type=\"hidden\" name=\"mode\" id=\"mode\" value=\"\" />\n");
      out.write("                    <input type=\"hidden\" name=\"analysis_name\" id=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\" />\n");
      out.write("                    <button type=\"button\" class=\"dropbtn\" title=\"Download Feature List as File\" onclick=\"submitSaveFeatureListRequest_3();\">Save</button>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("        </table>\n");
      out.write("        </form>\n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("\n");
  } else if (mode.equals("save")) {  
      out.write("\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("        <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("            <tr>\n");
      out.write("                <td height=\"15\">\n");
      out.write("                    &nbsp;\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td height=\"75\">\n");
      out.write("                    File <i>");
      out.print(filename);
      out.write("</i> created. <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
      out.write("    </body>\n");
      out.write("\n");
  }  
      out.write("\n");
      out.write("\n");
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
