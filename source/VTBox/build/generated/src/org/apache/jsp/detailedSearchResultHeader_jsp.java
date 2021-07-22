package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import java.util.ArrayList;

public final class detailedSearchResultHeader_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    ArrayList <String> search_strings = analysis.search_strings;

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <style>\n");
      out.write("            .marker {\n");
      out.write("                position: absolute; \n");
      out.write("                width: 20px; \n");
      out.write("                height: 1px;\n");
      out.write("                background: #73AD21;\n");
      out.write("                border: none;\n");
      out.write("                cursor: pointer;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .cross {\n");
      out.write("                border: 1px; \n");
      out.write("                display: none; \n");
      out.write("                opacity: 0.5; \n");
      out.write("                cursor: pointer;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            table {\n");
      out.write("                border-spacing: 5px 0px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            td {\n");
      out.write("                background: #EEEEEE;\n");
      out.write("                width: 18px;\n");
      out.write("                height: 35px;\n");
      out.write("                text-align: center;\n");
      out.write("                vertical-align: bottom;\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 12px;\n");
      out.write("                background-color:#EEEEEE;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            function showCross(id) {\n");
      out.write("                document.getElementById(\"cross_\" + id).style.display = \"inline\";\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideCross(id) {\n");
      out.write("                document.getElementById(\"cross_\" + id).style.display = \"none\";\n");
      out.write("            }\n");
      out.write("            \n");
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
      out.write("            var search_num_to_delete = -1;\n");
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
      out.write("            }\n");
      out.write("        \n");
      out.write("            function processResponse() {\n");
      out.write("                //check if the response has been received from the server\n");
      out.write("                if(http.readyState == 4){\n");
      out.write("\n");
      out.write("                    //read and assign the response from the server\n");
      out.write("                    var response = http.responseText;\n");
      out.write("                    response = response.trim();\n");
      out.write("                    //alert(response);\n");
      out.write("                    \n");
      out.write("                    selected_search_number = parent.parent.selected_search_number;\n");
      out.write("                    if (search_num_to_delete == selected_search_number) {\n");
      out.write("                        parent.parent.selected_search_number = -1;\n");
      out.write("                        parent.parent.selected_search_tag_label = \"\";\n");
      out.write("                    }\n");
      out.write("\n");
      out.write("                    parent.refreshAllSearchPanes(response);\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        \n");
      out.write("            function deleteSearch (search_num) {\n");
      out.write("                delete_url = 'DeleteSearchServlet?search_number=' + search_num + \"&analysis_name=");
      out.print(analysis_name);
      out.write("&timestamp=\" + Math.random();\n");
      out.write("                //delete_url = 'http://localhost:8080/VTBox/deleteSearch.jsp?search_number=1&analysis_name=abc'\n");
      out.write("                //alert(delete_url);\n");
      out.write("                makeGetRequest(delete_url);\n");
      out.write("                search_num_to_delete = search_num;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <table id=\"srhTable\" name=\"srhTable\" height=\"50px\" border=\"0px\" style=\"position: absolute; top: 0px; left: 0px\">\n");
      out.write("            <tr id=\"srhTableRow\" name=\"srhTableRow\">\n");
      out.write("                \n");
      out.write("                ");
  for(int i = 0; i < search_strings.size(); i++) {     
      out.write("\n");
      out.write("                <td id='");
      out.print(i);
      out.write("' onmouseover=\"showCross(");
      out.print(i);
      out.write(")\" onmouseout=\"hideCross(");
      out.print(i);
      out.write(")\">\n");
      out.write("                    <img name=\"cross_");
      out.print(i);
      out.write("\" id=\"cross_");
      out.print(i);
      out.write("\" class=\"cross\" src=\"images/cross.png\" width=\"18px\" height=\"18px\" onclick=\"deleteSearch(");
      out.print(i);
      out.write(")\"> \n");
      out.write("                    <br>\n");
      out.write("                    ");
      out.print(i);
      out.write("\n");
      out.write("                </td>\n");
      out.write("                ");
  }   
      out.write("\n");
      out.write("                \n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
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
