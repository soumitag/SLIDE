package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.layouts.ScrollViewLayout;
import graphics.HeatmapData;
import vtbox.SessionUtils;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import structure.CompactSearchResultContainer;
import java.util.ArrayList;
import graphics.Heatmap;
import structure.Data;
import structure.AnalysisContainer;

public final class scrollView_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
    Heatmap heatmap = analysis.heatmap;
    
    int start = 0;
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    int num_features = layout.NUM_DISPLAY_FEATURES;
    int mapframe_width = (int)layout.DETAILED_VIEW_IFRAME_WIDTH + 3;
    int map_height = (int)layout.MAP_HEIGHT;
    int header_height = (int)ScrollViewLayout.COLUMN_HEADER_HEIGHT;
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;

    String detailedSearchPanel_width = "0";
    if (search_results.size() == 0) {
        detailedSearchPanel_width = "0";
    } else {
        detailedSearchPanel_width = (search_results.size()*32) + "";
    }
    
    int FEATURE_LABEL_FRAME_WIDTH = 200;
    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
        FEATURE_LABEL_FRAME_WIDTH = 200;
    } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
            analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {
        FEATURE_LABEL_FRAME_WIDTH = 350;
    }
    
    int end = layout.getEnd(start, analysis.database.metadata.nFeatures);
    String imagename = heatmap.buildMapImage(0, end, "scroll_view_jsp", HeatmapData.TYPE_ARRAY);
    
    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set <String> keys = filterListMap.keySet();
    Iterator iter = keys.iterator();

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("    \n");
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
      out.write("\n");
      out.write("            //call the above function to create the XMLHttpRequest object\n");
      out.write("            var http = createRequestObject();\n");
      out.write("            var selected_list_name = '';\n");
      out.write("\n");
      out.write("            function makeGetRequest (start, end) {\n");
      out.write("                //make a connection to the server ... specifying that you intend to make a GET request \n");
      out.write("                //to the server. Specifiy the page name and the URL parameters to send\n");
      out.write("                http.open('get', 'getHeatmapData.jsp?start=' + start + '&end=' + end + '&analysis_name=");
      out.print(analysis_name);
      out.write("' + '&rand=' + Math.random());\n");
      out.write("\n");
      out.write("                //assign a handler for the response\n");
      out.write("                http.onreadystatechange = processResponse;\n");
      out.write("\n");
      out.write("                //actually send the request to the server\n");
      out.write("                http.send(null);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function processResponse() {\n");
      out.write("                //check if the response has been received from the server\n");
      out.write("                if(http.readyState == 4){\n");
      out.write("\n");
      out.write("                    //read and assign the response from the server\n");
      out.write("                    var response = http.responseText;\n");
      out.write("\n");
      out.write("                    //do additional parsing of the response, if needed\n");
      out.write("                    //alert (response);\n");
      out.write("                    updateMap (response);\n");
      out.write("\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function makeGetRequest_2 (feature_list) {\n");
      out.write("                //make a connection to the server ... specifying that you intend to make a GET request \n");
      out.write("                //to the server. Specifiy the page name and the URL parameters to send\n");
      out.write("                http.open('get', 'AddDataToList?mode=selected_list&list_name=' + selected_list_name + '&list_data=' + feature_list + '&analysis_name=");
      out.print(analysis_name);
      out.write("' + '&rand=' + Math.random());\n");
      out.write("\n");
      out.write("                //assign a handler for the response\n");
      out.write("                http.onreadystatechange = processResponse_2;\n");
      out.write("\n");
      out.write("                //actually send the request to the server\n");
      out.write("                http.send(null);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showDetailedPathInfo (pid, analysis_name) {\n");
      out.write("                parent.showDetailedPathInfo(pid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showDetailedGOInfo (gid, analysis_name) {\n");
      out.write("                parent.showDetailedGOInfo(gid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showDetailedInfo(eid, analysis_name) {\n");
      out.write("                //alert(eid);\n");
      out.write("                //alert(analysis_name);\n");
      out.write("                parent.showDetailedInfo(eid, analysis_name);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function processResponse_2 () {\n");
      out.write("                //check if the response has been received from the server\n");
      out.write("                if(http.readyState == 4){\n");
      out.write("\n");
      out.write("                    //read and assign the response from the server\n");
      out.write("                    var response = http.responseText;\n");
      out.write("\n");
      out.write("                    //do additional parsing of the response, if needed\n");
      out.write("                    //alert (response);\n");
      out.write("                    //alert(response.trim());\n");
      out.write("                    \n");
      out.write("                    if (response.trim() == \"1\") {\n");
      out.write("                        alert('Features Added to ' + selected_list_name + '.');\n");
      out.write("                    } else {\n");
      out.write("                        alert('Features Could Not Be Added. Please Try Again.');\n");
      out.write("                    }\n");
      out.write("\n");
      out.write("                }\n");
      out.write("            }   \n");
      out.write("            \n");
      out.write("            function updateMap (data) {\n");
      out.write("                document.getElementById('mapViewPanel').contentWindow.updateMap(data);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            var current_start = 0;\n");
      out.write("            function loadMap (start, end) {\n");
      out.write("                var labels_url = \"featureLabelsScrollView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&start=\" + start + \"&rand=\" + Math.random();\n");
      out.write("                var search_url = \"detailedSearchResultDisplayerScrollView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&start=\" + start + \"&rand=\" + Math.random() + \"&type=scroll\";\n");
      out.write("                document.getElementById('featureLabelsPanel').contentWindow.location.replace(labels_url);\n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.location.replace(search_url);\n");
      out.write("                makeGetRequest (start, end);\n");
      out.write("                document.getElementById('dispString').innerHTML = 'Showing ' + (start+1) + ' to ' + (end+1) + ' of ");
      out.print(analysis.database.metadata.nFeatures);
      out.write("';\n");
      out.write("                current_start = start;\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function selectGene(i, j, entrez, genesymbol) {\n");
      out.write("                alert('Entrez: ' + entrez + ', Genesymbol: ' + genesymbol);\n");
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
      out.write("                var url_text = \"detailedSearchResultDisplayerScrollView.jsp?type=search&analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("                //alert(document.getElementById('detailSearchPanel').width);\n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);\n");
      out.write("                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace(\"detailedSearchResultHeader.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\");\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function refreshSearchPane_PostDeletion() {\n");
      out.write("                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) - 32;\n");
      out.write("                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) - 32;\n");
      out.write("                var url_text = \"detailedSearchResultDisplayerScrollView.jsp?start=");
      out.print(start);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("                //alert(document.getElementById('detailSearchPanel').width);\n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);\n");
      out.write("                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace(\"detailedSearchResultHeader.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\");\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function toggleHighlightGenes(pathid, state) {\n");
      out.write("                //alert(\"2\");\n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.toggleHighlightGenes(pathid, state);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function addToList(list_name) {\n");
      out.write("                var flp = document.getElementById('featureLabelsPanel');\n");
      out.write("                var selected_features = flp.contentWindow.getSelectedFeatureIndices();\n");
      out.write("                selected_list_name = list_name;\n");
      out.write("                makeGetRequest_2 (selected_features.toString());\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function addRemoveListName(list_name, add_remove_ind) {\n");
      out.write("                //alert(\"2\");\n");
      out.write("                if (add_remove_ind === 1) {\n");
      out.write("                    // add\n");
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
      out.write("            function scrollBack() {\n");
      out.write("                next_start = current_start-");
      out.print(num_features);
      out.write(";\n");
      out.write("                if (next_start < 0) {\n");
      out.write("                    next_start = 0;\n");
      out.write("                }\n");
      out.write("                loadMap (next_start, next_start+");
      out.print(num_features);
      out.write(");\n");
      out.write("                parent.scrollGlobalTo(next_start);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function scrollForward() {\n");
      out.write("                next_start = current_start+");
      out.print(num_features);
      out.write(";\n");
      out.write("                if (next_start >= ");
      out.print(analysis.database.metadata.nFeatures);
      out.write(") {\n");
      out.write("                    next_start = current_start;\n");
      out.write("                }\n");
      out.write("                loadMap (next_start, next_start+");
      out.print(num_features);
      out.write(");\n");
      out.write("                parent.scrollGlobalTo(next_start);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body style=\"overflow-y: hidden; overflow-x: hidden\">\n");
      out.write("        <table border=\"0\" height=\"");
      out.print(map_height+header_height+62);
      out.write("\" style=\"border-spacing: 0px; padding: 0px\">\n");
      out.write("            <tr>\n");
      out.write("                <td rowspan=\"3\" width=\"");
      out.print(mapframe_width);
      out.write("\" height=\"");
      out.print(map_height+header_height);
      out.write("\" valign=\"top\">\n");
      out.write("                    <iframe id=\"mapViewPanel\" src=\"mapView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&imagename=");
      out.print(imagename);
      out.write("\" width=\"");
      out.print(mapframe_width);
      out.write("\" height=\"");
      out.print(map_height+header_height);
      out.write("\" frameBorder=\"0px\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td height=\"35\">&nbsp;</td>\n");
      out.write("                <td rowspan=\"2\"  height=\"");
      out.print(header_height-3);
      out.write("\">\n");
      out.write("                    <div class=\"dropdown\">\n");
      out.write("                    <button class=\"dropbtn\" title=\"Add Selected Features to Feature Lists. Click Feature Name To Select. Click Feature Lists To Create New Feature Lists.\">Add To List</button>\n");
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
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td height=\"50\">\n");
      out.write("                    <iframe id=\"detailSearchHeaderPanel\" src=\"detailedSearchResultHeader.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"50\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td height=\"");
      out.print(map_height);
      out.write("\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\">\n");
      out.write("                    <iframe id=\"detailSearchPanel\" src=\"detailedSearchResultDisplayerScrollView.jsp?start=");
      out.print(start);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"");
      out.print(map_height);
      out.write("\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td height=\"");
      out.print(map_height);
      out.write("\">\n");
      out.write("                    <iframe id=\"featureLabelsPanel\" src=\"featureLabelsScrollView.jsp?start=");
      out.print(start);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(FEATURE_LABEL_FRAME_WIDTH);
      out.write("\" height=\"");
      out.print(map_height);
      out.write("\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"3\" style=\"height: 62px; align-content: center\">\n");
      out.write("                    <div style=\"display: inline-block; width: 350px; padding: 10px\">\n");
      out.write("                        <button type=\"button\" name=\"prev\" onclick=\"scrollBack()\"> Prev </button>\n");
      out.write("                        <div id=\"dispString\" style=\"width: 170px; display: inline-block; text-align: center; font-family: verdana; font-size: 14px; font-weight: normal; font-style: italic; color: #444;\">\n");
      out.write("                        Showing 1 to ");
      out.print(num_features);
      out.write(" of ");
      out.print(analysis.database.metadata.nFeatures);
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                        <button type=\"button\" name=\"next\" onclick=\"scrollForward()\"> Next </button>\n");
      out.write("                    </div>\n");
      out.write("                </td>\n");
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
