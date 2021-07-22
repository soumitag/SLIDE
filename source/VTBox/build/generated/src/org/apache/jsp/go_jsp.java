package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import structure.AnalysisContainer;
import vtbox.SessionUtils;
import searcher.GoObject;
import java.util.ArrayList;
import searcher.Searcher;

public final class go_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

    
try {
    String analysis_name = request.getParameter("analysis_name");
    String id = (String)request.getParameter("go_id");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <GoObject> go_infos = ((Searcher)analysis.searcher).processGOid(id);

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    <style>\n");
      out.write("        \n");
      out.write("        .e {\n");
      out.write("            text-align:left;\n");
      out.write("            padding: 2px; \n");
      out.write("            background-color:#FDFDFD;\n");
      out.write("            margin-left: 10px;\n");
      out.write("            margin-top: 3px;\n");
      out.write("            margin-bottom: 3px;\n");
      out.write("            font-family: verdana;\n");
      out.write("            font-size: 12px;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .eh {\n");
      out.write("            text-align:left;\n");
      out.write("            padding: 2px; \n");
      out.write("            background-color:#FDFDFD;\n");
      out.write("            margin-left: 10px;\n");
      out.write("            margin-top: 3px;\n");
      out.write("            margin-bottom: 3px;\n");
      out.write("            font-family: verdana;\n");
      out.write("            font-size: 12px;\n");
      out.write("            font-weight: bold;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .e1 {\n");
      out.write("            text-align:center;\n");
      out.write("            cursor:pointer;\n");
      out.write("            border-radius: 7px; \n");
      out.write("            border: 1px solid #CCCCCC; \n");
      out.write("            padding: 2px; \n");
      out.write("            float: left; \n");
      out.write("            width: 100px;\n");
      out.write("            background-color:#FDFDFD;\n");
      out.write("            margin-left: 10px;\n");
      out.write("            margin-top: 3px;\n");
      out.write("            margin-bottom: 3px;\n");
      out.write("            font-family: verdana;\n");
      out.write("            font-size: 12px;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .ebad {\n");
      out.write("            text-align:center;\n");
      out.write("            cursor:pointer;\n");
      out.write("            border-radius: 7px; \n");
      out.write("            border: 1px solid #CCCCCC; \n");
      out.write("            padding: 2px; \n");
      out.write("            float: left; \n");
      out.write("            width: 100px;\n");
      out.write("            background-color:#FDFDFD;\n");
      out.write("            margin-left: 10px;\n");
      out.write("            margin-top: 3px;\n");
      out.write("            margin-bottom: 3px;\n");
      out.write("            font-family: verdana;\n");
      out.write("            font-size: 12px;\n");
      out.write("            color: #AAAAAA;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </style>\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("        ");
 
            if(go_infos != null) {
                for (int g=0; g <go_infos.size();g++) {
                    GoObject go_info = go_infos.get(g);
        
      out.write("\n");
      out.write("        \n");
      out.write("        <table cellspacing=\"5\" >\n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td >GO ID</td>                \n");
      out.write("                <td>&nbsp;&nbsp;</td>\n");
      out.write("                <td><b>ONTOLOGY</b></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td class=\"e\" id=\"");
      out.print( go_info.goID );
      out.write("\" style=\"\">GO:");
      out.print(go_info.goID );
      out.write("</td>\n");
      out.write("                <td>&nbsp;&nbsp;</td>\n");
      out.write("                <td class=\"e\" id=\"");
      out.print( go_info.ontology );
      out.write("\" style=\"\">");
      out.print( go_info.ontology.toUpperCase() );
      out.write("</td>                \n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("                      \n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td colspan=\"3\">GO TERM</td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td colspan= \"3\" class=\"e\" id=\"");
      out.print( go_info.goTerm );
      out.write("\" style=\"\">");
      out.print( go_info.goTerm );
      out.write("</td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td colspan=\"3\">DEFINITION</td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                ");
 if (go_info.definition != null) {
      out.write("\n");
      out.write("                <td colspan= \"3\" class=\"e\" id=\"");
      out.print( go_info.definition );
      out.write("\" style=\"\">");
      out.print( go_info.definition );
      out.write("</td>\n");
      out.write("                ");
 } else { 
      out.write("\n");
      out.write("                 <td colspan= \"3\" class=\"e\" id=\"");
      out.print( go_info.definition );
      out.write("\" style=\"\">");
      out.print( "Definition not found" );
      out.write("</td>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td colspan=\"3\">EVIDENCES</td>\n");
      out.write("            </tr>\n");
      out.write("             </tr>\n");
      out.write("                ");
  String evidence_str = ""; 
      out.write("\n");
      out.write("                ");
  for (int i = 0; i < go_info.evidences.size() - 1; i++) { 
                        evidence_str += go_info.evidences.get(i).toUpperCase() + ", "; 
                    }
                    evidence_str += go_info.evidences.get(go_info.evidences.size()-1).toUpperCase();
                
      out.write("\n");
      out.write("                \n");
      out.write("                <td colspan= \"3\" class=\"e\" id=\"");
      out.print( evidence_str );
      out.write("\" style=\"\">");
      out.print( evidence_str );
      out.write("</td>\n");
      out.write("                 \n");
      out.write("            <tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td>ENTREZ IDs</td>\n");
      out.write("                <td>GENESYMBOLS</td>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("            </tr>\n");
      out.write("                ");
 for (int j = 0; j < go_info.entrez_ids.size(); j++) { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td class=\"e\" id=\"");
      out.print( go_info.entrez_ids.get(j) );
      out.write("\" style=\"\">");
      out.print( go_info.entrez_ids.get(j) );
      out.write("</td>\n");
      out.write("                <td class=\"e\" id=\"");
      out.print( go_info.genesymbols.get(j) );
      out.write("\" style=\"\">");
      out.print( go_info.genesymbols.get(j).toUpperCase() );
      out.write(" </td>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("                ");
 } 
      out.write("                \n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("        </table>\n");
      out.write("        \n");
      out.write("        ");
  
                }
            }
        
      out.write("\n");
      out.write("        \n");
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
