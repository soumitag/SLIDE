package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import structure.AnalysisContainer;
import vtbox.SessionUtils;
import searcher.Searcher;
import searcher.GeneObject;
import searcher.PathwayObject;
import searcher.GoObject;
import java.util.ArrayList;
import java.util.HashMap;;

public final class gene_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    
try {
    
    String entrez_id = (String)request.getParameter("entrez_id");
    
    if (entrez_id == null || entrez_id.equals("")) {
        String msg = "No feature selected. <br> Select features in visualization panels to display details here.";
        getServletContext().getRequestDispatcher("/Exception.jsp?msg=" + msg).forward(request, response);
        return;
    }
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <GeneObject> genes = ((Searcher)analysis.searcher).processEntrezidGeneSymbol(entrez_id);

      out.write("\n");
      out.write("    \n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    </head>\n");
      out.write("\n");
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
      out.write("    <body>\n");
      out.write("        \n");
      out.write("               \n");
      out.write("        \n");
      out.write("        \n");
      out.write("        ");
 
            if(genes != null) {
                for (int g=0; g<genes.size();g++) {
                    GeneObject gene = genes.get(g);
        
      out.write("\n");
      out.write("        <table cellspacing=\"5\" >\n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td >ENTREZ ID</td>                \n");
      out.write("                <td>&nbsp;&nbsp;</td>\n");
      out.write("                <td><b> GENE SYMBOL</b></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td class=\"e\" id=\"");
      out.print( gene.entrez_id );
      out.write("\" style=\"\">");
      out.print(gene.entrez_id);
      out.write("</td>\n");
      out.write("                <td>&nbsp;&nbsp;</td>\n");
      out.write("                <td class=\"e\" id=\"");
      out.print( gene.genesymbol );
      out.write("\" style=\"\">");
      out.print(gene.genesymbol.toUpperCase() );
      out.write("</td>                \n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("                      \n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td colspan=\"3\">ALIASES</td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                ");
  String alias_str = ""; 
      out.write("\n");
      out.write("                ");
  for (int i = 0; i < gene.aliases.size()-1; i++) { 
                        alias_str += gene.aliases.get(i) + ", "; 
                    } 
                    alias_str += gene.aliases.get(gene.aliases.size()-1);
                
      out.write("\n");
      out.write("                <td colspan= \"3\" class=\"e\" id=\"");
      out.print(alias_str );
      out.write("\" style=\"\">");
      out.print(alias_str.toUpperCase() );
      out.write("</td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr class=\"eh\">\n");
      out.write("               <td colspan=\"3\">GENE ONTOLOGIES</td>\n");
      out.write("            </tr>\n");
      out.write("                ");
 for (int i = 0; i < gene.goTerms.size(); i++) { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"3\" class=\"e\" id=\"");
      out.print( gene.goTerms.get(i).goID );
      out.write("\" style=\"\">");
      out.print(gene.goTerms.get(i).goTerm  + "\t" );
      out.write(" <b> (GO:");
      out.print(gene.goTerms.get(i).goID  + ") \t" );
      out.write(' ');
      out.write('[');
      out.print(gene.goTerms.get(i).ontology.toUpperCase() + "] \t" );
      out.write(" </b></td>\n");
      out.write("                ");
 } 
      out.write("                \n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("            <td colspan=\"3\"></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr class=\"eh\">\n");
      out.write("                <td colspan=\"3\">PATHWAYS</td>\n");
      out.write("            </tr>\n");
      out.write("                ");
 for (int i = 0; i < gene.pathways.size(); i++) { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"3\" class=\"e\" id=\"");
      out.print( gene.pathways.get(i)._id );
      out.write("\" style=\"\">");
      out.print(gene.pathways.get(i).pathway + "\t" );
      out.write(" <b>(");
      out.print(gene.pathways.get(i)._id + ") \t" );
      out.write(' ');
      out.write('[');
      out.print(gene.pathways.get(i).source.toUpperCase() + "] \t" );
      out.write(" </b></td>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
      out.write("        \n");
      out.write("        ");
  
                }
            }
        
      out.write("\n");
      out.write("      \n");
      out.write("    </body>\n");
      out.write("    \n");
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
