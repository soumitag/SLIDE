package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.AnalysisContainer;

public final class searcher_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("        <script>\n");
      out.write("                       \n");
      out.write("            function createRequestObject() {\n");
      out.write("                var tmpXmlHttpObject;\n");
      out.write("\n");
      out.write("                //depending on what the browser supports, use the right way to create the XMLHttpRequest object\n");
      out.write("                if (window.XMLHttpRequest) { \n");
      out.write("                    // Mozilla, Safari would use this method ...\n");
      out.write("                    tmpXmlHttpObject = new XMLHttpRequest();\n");
      out.write("\n");
      out.write("                } else if (window.ActiveXObject) { \n");
      out.write("                    // IE would use this method ...\n");
      out.write("                    tmpXmlHttpObject = new ActiveXObject(\"Microsoft.XMLHTTP\");\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                return tmpXmlHttpObject;\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            var http = createRequestObject();\n");
      out.write("           \n");
      out.write("\n");
      out.write("            function makeGetRequest (theGetText) {\n");
      out.write("                //make a connection to the server ... specifying that you intend to make a GET request \n");
      out.write("                //to the server. Specifiy the page name and the URL parameters to send\n");
      out.write("                //alert(theGetText);\n");
      out.write("                http.open('get', theGetText);\n");
      out.write("\n");
      out.write("                //assign a handler for the response\n");
      out.write("                http.onreadystatechange = processResponse;\n");
      out.write("\n");
      out.write("                //actually send the request to the server\n");
      out.write("                http.send(null);\n");
      out.write("\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function processResponse() {\n");
      out.write("                //check if the response has been received from the server\n");
      out.write("                if(http.readyState == 4){\n");
      out.write("\n");
      out.write("                    //read and assign the response from the server\n");
      out.write("                    var response = http.responseText;\n");
      out.write("\n");
      out.write("                    parent.display_search_result ('");
      out.print(analysis_name);
      out.write("');\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        \n");
      out.write("        function callSearcher () {\n");
      out.write("            \n");
      out.write("            var search_string = document.getElementById('searchText').value;\n");
      out.write("            var q = document.getElementById('queryType');\n");
      out.write("            var query_type = q.options[q.selectedIndex].value;\n");
      out.write("            var s = document.getElementById('searchType');\n");
      out.write("            var search_type = s.options[s.selectedIndex].value;\n");
      out.write("            var milliseconds = new Date().getTime();\n");
      out.write("            //search_url = 'doSearch.jsp?queryType=' + query_type + '&searchText=' + search_string + '&searchType=' + search_type + '&timestamp=' + milliseconds + '&analysis_name=");
      out.print(analysis_name);
      out.write("';\n");
      out.write("            search_url = 'DoSearch?queryType=' + query_type + \n");
      out.write("                         '&searchText=' + search_string + \n");
      out.write("                         '&searchType=' + search_type + \n");
      out.write("                         '&timestamp=' + milliseconds + \n");
      out.write("                         '&analysis_name=");
      out.print(analysis_name);
      out.write("';\n");
      out.write("            \n");
      out.write("            //alert(search_url);\n");
      out.write("            makeGetRequest(search_url);\n");
      out.write("            \n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function getfilepathfrombrowse(usecase){\n");
      out.write("        if (usecase == \"data\") {\n");
      out.write("            var filename = document.getElementById(\"selectmrnafilename\").value;\n");
      out.write("            document.getElementById(\"txtmrnafilename\").value = filename;\n");
      out.write("            document.getElementById(\"fileinputname\").value = filename;\n");
      out.write("        } else if  (usecase == \"map\") {\n");
      out.write("            var filename = document.getElementById(\"selectmapfilename\").value;\n");
      out.write("            document.getElementById(\"txtmapfilename\").value = filename;\n");
      out.write("            document.getElementById(\"mapfilename\").value = filename;\n");
      out.write("            document.getElementById(\"mappingPreview\").style.visibility = \"visible\";\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("    </head>\n");
      out.write("    <body style=\"overflow:hidden; margin-top: 2px;\">\n");
      out.write("        \n");
      out.write("        <table height=\"25px\" valign=\"middle\">\n");
      out.write("             <form name=\"SearchForm\" action=\"\">\n");
      out.write("                <tr style=\"vertical-align: middle\">\n");
      out.write("                             \n");
      out.write("                    <td valign=\"middle\">\n");
      out.write("                        ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("                        <select name=\"queryType\" id=\"queryType\" form=\"SearchForm\">\n");
      out.write("                            <option value=\"entrez\">Entrez ID</option>\n");
      out.write("                            <option value=\"genesymbol\">Gene symbol</option>\n");
      out.write("                            <option value=\"goid\">GO ID</option>\n");
      out.write("                            <option value=\"goterm\">GO Term</option>\n");
      out.write("                            <option value=\"pathid\">Path ID</option>\n");
      out.write("                            <option value=\"pathname\">Pathway</option>\n");
      out.write("                        </select>\n");
      out.write("                        ");
  } else if(analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {       
      out.write("\n");
      out.write("                        <select name=\"queryType\" id=\"queryType\" form=\"SearchForm\">\n");
      out.write("                            <option value=\"pathid\">Path ID</option>\n");
      out.write("                            <option value=\"pathname\">Pathway</option>\n");
      out.write("                        </select>\n");
      out.write("                        ");
  } else if(analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {       
      out.write("\n");
      out.write("                        <select name=\"queryType\" id=\"queryType\" form=\"SearchForm\">\n");
      out.write("                            <option value=\"goid\">GO ID</option>\n");
      out.write("                            <option value=\"goterm\">GO Term</option>\n");
      out.write("                        </select>\n");
      out.write("                        ");
  }   
      out.write("\n");
      out.write("                    </td>\n");
      out.write("                    <td valign=\"middle\">\n");
      out.write("                        <select name=\"searchType\" id=\"searchType\" form=\"SearchForm\">\n");
      out.write("                            <option value=\"exact\">&equals;</option>\n");
      out.write("                            <option value=\"contains\">&cong;</option>                       \n");
      out.write("                        </select>\n");
      out.write("                    </td>\n");
      out.write("                    <td valign=\"middle\" style=\"font-family:verdana; font-size:6;\">\n");
      out.write("                        <input type=\"text\" id=\"searchText\" name=\"searchText\" size=\"30\">\n");
      out.write("                    </td>\n");
      out.write("                    <td valign=\"middle\">\n");
      out.write("                        <!--<input type=\"button\" value=\"Search\" onclick=\"callSearcher()\">-->\n");
      out.write("                        <button type=\"button\" name=\"Search\" onclick=\"callSearcher()\"> Search </button>\n");
      out.write("                        <!--<img src=\"images/upload.png\" alt=\"Upload\" height=\"20\" width=\"20\"/>-->\n");
      out.write("                    </td>\n");
      out.write("                \n");
      out.write("                </tr>\n");
      out.write("            </form>\n");
      out.write("            \n");
      out.write("        </table>\n");
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
