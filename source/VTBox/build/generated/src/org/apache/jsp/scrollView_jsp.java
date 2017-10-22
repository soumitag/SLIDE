package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
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

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
    Heatmap heatmap = analysis.heatmap;
    
    int start = 0;
    
    double image_height = 760.0;
    double feature_height = 20;
    int num_features = (int)(image_height/feature_height);
    num_features = Math.min(num_features, analysis.database.features.size());
    
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
    } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) {
        FEATURE_LABEL_FRAME_WIDTH = 350;
    }
    
    String imagename = heatmap.buildMapImage(0, num_features-1, "scroll_view_jsp", HeatmapData.TYPE_ARRAY);
    
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
      out.write("            function loadMap (start, end) {\n");
      out.write("                var labels_url = \"featureLabelsScrollView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&start=\" + start + \"&rand=\" + Math.random();\n");
      out.write("                var search_url = \"detailedSearchResultDisplayerScrollView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&start=\" + start + \"&rand=\" + Math.random();\n");
      out.write("                document.getElementById('featureLabelsPanel').contentWindow.location.replace(labels_url);\n");
      out.write("                document.getElementById('detailSearchPanel').contentWindow.location.replace(search_url);\n");
      out.write("                makeGetRequest (start, end);\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function selectGene(i, j, entrez, genesymbol) {\n");
      out.write("                alert('Entrez: ' + entrez + ', Genesymbol: ' + genesymbol);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function refreshSearchPane() {\n");
      out.write("                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) + 32;\n");
      out.write("                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) + 32;\n");
      out.write("                var url_text = \"detailedSearchResultDisplayerScrollView.jsp?start=");
      out.print(start);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("                //alert(url_text);\n");
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
      out.write("        </script>\n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <table border=\"0\">\n");
      out.write("            <tr>\n");
      out.write("                <td rowspan=\"3\" width=\"500\" valign=\"top\">\n");
      out.write("                    <iframe id=\"mapViewPanel\" src=\"mapView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&imagename=");
      out.print(imagename);
      out.write("\" width=\"500\" height=\"880\" frameBorder=\"0px\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td height=\"35\">&nbsp;</td>\n");
      out.write("                <td rowspan=\"2\" height=\"35\">\n");
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
      out.write("                <td>\n");
      out.write("                    <iframe id=\"detailSearchPanel\" src=\"detailedSearchResultDisplayerScrollView.jsp?start=");
      out.print(start);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(detailedSearchPanel_width);
      out.write("\" height=\"780\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                <td>\n");
      out.write("                    <iframe id=\"featureLabelsPanel\" src=\"featureLabelsScrollView.jsp?start=");
      out.print(start);
      out.write("&analysis_name=");
      out.print(analysis_name);
      out.write("\" width=\"");
      out.print(FEATURE_LABEL_FRAME_WIDTH);
      out.write("\" height=\"780\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
      out.write("        \n");
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
