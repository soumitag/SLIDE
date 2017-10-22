package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import structure.AnalysisContainer;
import structure.CompactSearchResultContainer;
import java.util.ArrayList;
import structure.Data;

public final class drillDownPanel_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Data db = analysis.database;
    int num_features = db.features.size();
    
    String start_node_id = request.getParameter("start_node_id");
    String showDendrogram = request.getParameter("show_dendrogram");

    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    int start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        end = num_features - 1;
    }
    
    String TYPE = request.getParameter("type");
    if(TYPE == null) {
        TYPE = "dendrogram_map";
    }
    
    if (TYPE.equals("dendrogram_map")) {
        String pop_one_history = request.getParameter("pop_one_history");
        if (pop_one_history != null && pop_one_history.equals("1")) {
            analysis.state_variables.popDendrogramHistory();
            Integer[] dendrogram_history = analysis.state_variables.peekDendrogramHistory();
            start_node_id = (dendrogram_history[0] ==  null) ? "" : dendrogram_history[0] + "";
            start = (dendrogram_history[1] ==  null) ? 0 : dendrogram_history[1];
            end = (dendrogram_history[2] ==  null) ? (num_features - 1) : dendrogram_history[2];
        } else {
            Integer root_node_id;
            if (start_node_id != null && start_node_id != "" && !start_node_id.equals("null")) {
                root_node_id = Integer.parseInt(start_node_id);
            } else {
                root_node_id = null;
            }
            analysis.state_variables.pushDendrogramHistory(new Integer[]{root_node_id, start, end});
        }
    }

    double image_height = 750.0;
    num_features = end - start + 1;
    double feature_height = image_height/num_features;
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;

    String detailedSearchPanel_width = "0";
    if (search_results.size() == 0) {
        detailedSearchPanel_width = "0";
    } else {
        detailedSearchPanel_width = (search_results.size()*32) + "";
    }
    
    String featureLabelsPanel_width = "0";
    if (feature_height >= 15) {
        featureLabelsPanel_width = "250";
    }
    
    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set <String> keys = filterListMap.keySet();
    Iterator iter = keys.iterator();

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js\"></script>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <script>\n");
      out.write("            function toggleHighlightGenes(pathid, state) {\n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.toggleHighlightGenes(pathid, state);\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function loadSubTree(node_id, start, end) {\n");
      out.write("                window.location = \"drillDownPanel.jsp?show_dendrogram=yes&start_node_id=\" + node_id + \"&start=\" + start + \"&end=\" + end + \"&analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function loadPrevious() {\n");
      out.write("                window.location = \"drillDownPanel.jsp?show_dendrogram=yes&analysis_name=");
      out.print(analysis_name);
      out.write("&pop_one_history=1\";\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function updateMap(start, end) {\n");
      out.write("                parent.updateMap(start, end);\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function toggleHistogramPanel() {\n");
      out.write("                var sph = document.getElementById(\"histHandle\");\n");
      out.write("                var sp = document.getElementById(\"histPanel\");\n");
      out.write("                if (sp.style.display === 'inline') {\n");
      out.write("                    sp.style.display = 'none';\n");
      out.write("                    sph.innerHTML = 'Show Histogram';\n");
      out.write("                } else {\n");
      out.write("                    sp.style.display = 'inline';\n");
      out.write("                    sph.innerHTML = 'Hide Histogram';\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function refreshAllSearchPanes(num_current_searches) {\n");
      out.write("                //alert('Here');\n");
      out.write("                parent.update_search_result_post_deletion('");
      out.print(analysis_name);
      out.write("', num_current_searches);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function refreshSearchPane() {\n");
      out.write("                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) + 32;\n");
      out.write("                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) + 32;\n");
      out.write("                var s = document.getElementById(\"start\").value;\n");
      out.write("                var e = document.getElementById(\"end\").value;\n");
      out.write("                var url_text = \"detailedSearchResultDisplayer.jsp?start=\" + s + \"&end=\" + e + \"&analysis_name=");
      out.print(analysis_name);
      out.write("&type=");
      out.print(TYPE);
      out.write("\";\n");
      out.write("                \n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);\n");
      out.write("                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace(\"detailedSearchResultHeader.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\");\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function saveAsPDF() {\n");
      out.write("                var popup = window.open(\"svg.jsp\");\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showRect(start, end) {\n");
      out.write("                document.getElementById('detailHeatMapPanel').contentWindow.showRect(start, end);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideRect() {\n");
      out.write("                document.getElementById('detailHeatMapPanel').contentWindow.hideRect();\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showDetailedInfo(eid, analysis_name) {\n");
      out.write("                parent.showDetailedInfo(eid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showAltText(eid) {\n");
      out.write("                var td = document.getElementById('entrez_td');\n");
      out.write("                td.innerHTML = eid;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideAltText() {\n");
      out.write("                var td = document.getElementById('entrez_td');\n");
      out.write("                td.innerHTML = '';\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showColHeader(colname) {\n");
      out.write("                var td = document.getElementById('column_header_td');\n");
      out.write("                td.innerHTML = colname;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideColHeader() {\n");
      out.write("                var td = document.getElementById('column_header_td');\n");
      out.write("                td.innerHTML = '';\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            $(window).load(function() {\n");
      out.write("                    $(\".loader\").fadeOut(\"slow\");\n");
      out.write("            })\n");
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
      out.write("            var selected_list_name = \"\";\n");
      out.write("            var http = createRequestObject();\n");
      out.write("           \n");
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
      out.write("                    //alert(response.trim());\n");
      out.write("                    \n");
      out.write("                    if (response.trim() == \"1\") {\n");
      out.write("                        alert('Features Added to ' + selected_list_name + '.');\n");
      out.write("                    } else {\n");
      out.write("                        alert('Features Could Not Be Added. Please Try Again.');\n");
      out.write("                    }\n");
      out.write("                    \n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function addToList (list_name) {\n");
      out.write("                //alert(list_name);\n");
      out.write("                selected_list_name = list_name;\n");
      out.write("                search_url = 'AddDataToList?mode=cluster' + '&list_name=' + list_name + '&start=' + ");
      out.print(start);
      out.write(" + '&end=' + ");
      out.print(end);
      out.write(" + '&analysis_name=");
      out.print(analysis_name);
      out.write("';\n");
      out.write("                //alert(search_url);\n");
      out.write("                makeGetRequest(search_url);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function addRemoveListName(list_name, add_remove_ind) {\n");
      out.write("                if (add_remove_ind === 1) {\n");
      out.write("                        // add\n");
      out.write("                    var node = document.createElement(\"a\");\n");
      out.write("                    var text = document.createTextNode(list_name);\n");
      out.write("                    node.appendChild(text);\n");
      out.write("                    node.setAttribute(\"href\", \"#\");\n");
      out.write("                    node.setAttribute(\"id\", \"flin_\" + list_name);\n");
      out.write("                    node.onclick = function () {\n");
      out.write("                        addToList(list_name);\n");
      out.write("                    };\n");
      out.write("                    document.getElementById(\"list_names_container\").appendChild(node);\n");
      out.write("                    //alert(document.getElementById(\"list_names_container\"));\n");
      out.write("                } else if (add_remove_ind === 0) {\n");
      out.write("                    // remove\n");
      out.write("                    var node = document.getElementById(\"flin_\" + list_name);\n");
      out.write("                    document.getElementById(\"list_names_container\").removeChild(node);\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"loader\"></div>\n");
      out.write("        <table border=\"0\">\n");
      out.write("            \n");
      out.write("            ");
  if (showDendrogram.equalsIgnoreCase("yes")) {   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td height=\"50px\">\n");
      out.write("                    <!--\n");
      out.write("                    &nbsp;<button name=\"histHandle\" id=\"histHandle\" onclick=\"toggleHistogramPanel()\"> Hide Histogram </button>\n");
      out.write("                    &nbsp;<button name=\"saver\" id=\"saver\" onclick=\"saveAsPDF()\"> Save Image </button>\n");
      out.write("                    -->\n");
      out.write("                    &nbsp;\n");
      out.write("                    <div class=\"dropdown\">\n");
      out.write("                    <button class=\"dropbtn\" title=\"Add All Features to Feature Lists. Click Feature Lists To Create New Feature Lists.\">Add To List</button>\n");
      out.write("                        <div id=\"list_names_container\" class=\"dropdown-content\">\n");
      out.write("                            ");
  
                                while (iter.hasNext()) {   
                                    String list_name = (String) iter.next();
                            
      out.write("\n");
      out.write("                            <a id=\"flin_");
      out.print(list_name);
      out.write("\" onclick=\"addToList('");
      out.print(list_name);
      out.write("')\" href=\"#\">");
      out.print(list_name);
      out.write("</a>\n");
      out.write("                            ");
  }   
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                        <button type=\"button\" onclick=\"loadPrevious();\"> Back </button>\n");
      out.write("                </td>\n");
      out.write("                <td id=\"column_header_td\" style=\"font-family: verdana; font-size: 10; color: black\">\n");
      out.write("                    \n");
      out.write("                </td>\n");
      out.write("                <td>\n");
      out.write("                    <iframe id=\"detailSearchHeaderPanel\" src=\"detailedSearchResultHeader.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"50\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td id=\"entrez_td\" style=\"font-family: verdana; font-size: 12; color: blue\">\n");
      out.write("                     \n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td style=\"vertical-align: top\">\n");
      out.write("                    <iframe id=\"dendrogramPanel\" src=\"dendrogram.jsp?start_node_id=");
      out.print(start_node_id);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"320\" height=\"790\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td>\n");
      out.write("                    <iframe id=\"detailHeatMapPanel\" src=\"detailedHeatMap.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("&type=dendrogram_map\" width=\"260\" height=\"820\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td style=\"vertical-align: top\">\n");
      out.write("                    <iframe id=\"detailSearchPanel\" src=\"detailedSearchResultDisplayer.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("&type=dendrogram_map\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"790\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td style=\"vertical-align: top\">\n");
      out.write("                ");
  if (feature_height >= 15.0) {   
      out.write("\n");
      out.write("                    <iframe id=\"featureLabelsPanel\" src=\"featurelabels.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(featureLabelsPanel_width);
      out.write("\" height=\"780\" frameBorder=\"0\"></iframe>\n");
      out.write("                ");
  }   
      out.write("\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    <iframe id=\"histPanel\" src=\"histogram.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"570\" height=\"250\" frameBorder==\"0\" style=\"display: inline;\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    &nbsp;\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            \n");
      out.write("            ");
  } else {    
      out.write("\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                \n");
      out.write("                <td height=\"50px\">\n");
      out.write("                    <!--\n");
      out.write("                    &nbsp;<button name=\"histHandle\" id=\"histHandle\" onclick=\"toggleHistogramPanel()\"> Show Histogram </button>\n");
      out.write("                    &nbsp;<button name=\"saver\" id=\"saver\" onclick=\"saveAsPDF()\"> Save Image </button>\n");
      out.write("                    -->\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("                <td>\n");
      out.write("                    <iframe id=\"detailSearchHeaderPanel\" src=\"detailedSearchResultHeader.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("&type=global_map\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"50\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td id=\"entrez_td\" style=\"font-family: verdana; font-size: 12; color: blue\">\n");
      out.write("                     \n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <iframe id=\"detailHeatMapPanel\" src=\"detailedHeatMap.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("&type=global_map\" width=\"280\" height=\"760\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    <iframe id=\"detailSearchPanel\" src=\"detailedSearchResultDisplayer.jsp?start=");
      out.print(start);
      out.write("&end=");
      out.print(end);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"760\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"3\">\n");
      out.write("                    <iframe id=\"histPanel\" src=\"histogram.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" height=\"250\" frameBorder==\"0\" style=\"display: none;\"></iframe>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            <input type=\"hidden\" id=\"start\" name=\"start\" value=\"");
      out.print(start);
      out.write("\" />\n");
      out.write("            <input type=\"hidden\" id=\"end\" name=\"end\" value=\"");
      out.print(end);
      out.write("\" />\n");
      out.write("            \n");
      out.write("        </table>\n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("\n");
      out.write("    ");
  if (showDendrogram.equalsIgnoreCase("yes")) {   
      out.write("\n");
      out.write("        parent.showGlobalMapRect(");
      out.print(start);
      out.write(',');
      out.write(' ');
      out.print(end);
      out.write(");\n");
      out.write("    ");
  } else {   
      out.write("\n");
      out.write("        parent.loadScrollViewPanel('");
      out.print(analysis_name);
      out.write("');\n");
      out.write("        parent.loadHistPanel('");
      out.print(analysis_name);
      out.write("');\n");
      out.write("        ");
  if (analysis.isTreeAvailable)   {   
      out.write("\n");
      out.write("            parent.loadDrillDownPanel('");
      out.print(analysis_name);
      out.write("');\n");
      out.write("        ");
  }   
      out.write("    \n");
      out.write("    ");
  }   
      out.write("\n");
      out.write("    </script>\n");
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
