<%-- 
    Document   : scrollView
    Created on : 14 Apr, 2017, 12:02:01 PM
    Author     : Soumita
--%>

<%@page import="graphics.HeatmapData"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="graphics.Heatmap"%>
<%@page import="structure.Data"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
    Heatmap heatmap = analysis.heatmap;
    
    int start = 0;
    
    double image_height = 760.0;
    double feature_height = 20;
    int num_features = (int)(image_height/feature_height);
    num_features = Math.min(num_features, analysis.database.metadata.nFeatures);
    
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
    
    String imagename = heatmap.buildMapImage(0, num_features-1, "scroll_view_jsp", HeatmapData.TYPE_ARRAY);
    
    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set <String> keys = filterListMap.keySet();
    Iterator iter = keys.iterator();
%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="vtbox-main.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <script>
    
            function createRequestObject() {
                var tmpXmlHttpObject;

                //depending on what the browser supports, use the right way to create the XMLHttpRequest object
                if (window.XMLHttpRequest) { 
                    // Mozilla, Safari would use this method ...
                    tmpXmlHttpObject = new XMLHttpRequest();

                } else if (window.ActiveXObject) { 
                    // IE would use this method ...
                    tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
                }

                return tmpXmlHttpObject;
            }

            //call the above function to create the XMLHttpRequest object
            var http = createRequestObject();
            var selected_list_name = '';

            function makeGetRequest (start, end) {
                //make a connection to the server ... specifying that you intend to make a GET request 
                //to the server. Specifiy the page name and the URL parameters to send
                http.open('get', 'getHeatmapData.jsp?start=' + start + '&end=' + end + '&analysis_name=<%=analysis_name%>' + '&rand=' + Math.random());

                //assign a handler for the response
                http.onreadystatechange = processResponse;

                //actually send the request to the server
                http.send(null);
            }
            
            function processResponse() {
                //check if the response has been received from the server
                if(http.readyState == 4){

                    //read and assign the response from the server
                    var response = http.responseText;

                    //do additional parsing of the response, if needed
                    //alert (response);
                    updateMap (response);

                }
            }
            
            function makeGetRequest_2 (feature_list) {
                //make a connection to the server ... specifying that you intend to make a GET request 
                //to the server. Specifiy the page name and the URL parameters to send
                http.open('get', 'AddDataToList?mode=selected_list&list_name=' + selected_list_name + '&list_data=' + feature_list + '&analysis_name=<%=analysis_name%>' + '&rand=' + Math.random());

                //assign a handler for the response
                http.onreadystatechange = processResponse_2;

                //actually send the request to the server
                http.send(null);
            }
            
            function showDetailedPathInfo (pid, analysis_name) {
                parent.showDetailedPathInfo(pid, analysis_name);
            }
            
            function showDetailedGOInfo (gid, analysis_name) {
                parent.showDetailedGOInfo(gid, analysis_name);
            }
            
            function showDetailedInfo(eid, analysis_name) {
                //alert(eid);
                //alert(analysis_name);
                parent.showDetailedInfo(eid, analysis_name);
            }
            
            function processResponse_2 () {
                //check if the response has been received from the server
                if(http.readyState == 4){

                    //read and assign the response from the server
                    var response = http.responseText;

                    //do additional parsing of the response, if needed
                    //alert (response);
                    //alert(response.trim());
                    
                    if (response.trim() == "1") {
                        alert('Features Added to ' + selected_list_name + '.');
                    } else {
                        alert('Features Could Not Be Added. Please Try Again.');
                    }

                }
            }   
            
            function updateMap (data) {
                document.getElementById('mapViewPanel').contentWindow.updateMap(data);
            }
            
            function loadMap (start, end) {
                var labels_url = "featureLabelsScrollView.jsp?analysis_name=<%=analysis_name%>&start=" + start + "&rand=" + Math.random();
                var search_url = "detailedSearchResultDisplayerScrollView.jsp?analysis_name=<%=analysis_name%>&start=" + start + "&rand=" + Math.random() + "&type=scroll";
                document.getElementById('featureLabelsPanel').contentWindow.location.replace(labels_url);
                document.getElementById('detailSearchPanel').contentWindow.location.replace(search_url);
                makeGetRequest (start, end);
            }
    
            function selectGene(i, j, entrez, genesymbol) {
                alert('Entrez: ' + entrez + ', Genesymbol: ' + genesymbol);
            }
            
            function refreshAllSearchPanes(num_current_searches) {
                //alert('Here');
                parent.update_search_result_post_deletion('<%=analysis_name%>', num_current_searches);
            }
            
            function refreshSearchPane() {
                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) + 32;
                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) + 32;
                var url_text = "detailedSearchResultDisplayerScrollView.jsp?type=search&analysis_name=<%=analysis_name%>";
                //alert(document.getElementById('detailSearchPanel').width);
                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);
                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace("detailedSearchResultHeader.jsp?analysis_name=<%=analysis_name%>");
            }
            
            function refreshSearchPane_PostDeletion() {
                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) - 32;
                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) - 32;
                var url_text = "detailedSearchResultDisplayerScrollView.jsp?start=<%=start%>&analysis_name=<%=analysis_name%>";
                //alert(document.getElementById('detailSearchPanel').width);
                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);
                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace("detailedSearchResultHeader.jsp?analysis_name=<%=analysis_name%>");
            }
    
            function toggleHighlightGenes(pathid, state) {
                //alert("2");
                document.getElementById('detailSearchPanel').contentWindow.toggleHighlightGenes(pathid, state);
            }
            
            function addToList(list_name) {
                var flp = document.getElementById('featureLabelsPanel');
                var selected_features = flp.contentWindow.getSelectedFeatureIndices();
                selected_list_name = list_name;
                makeGetRequest_2 (selected_features.toString());
            }
            
            function addRemoveListName(list_name, add_remove_ind) {
                //alert("2");
                if (add_remove_ind === 1) {
                    // add
                    var node = document.createElement("a");
                    var text = document.createTextNode(list_name);
                    node.appendChild(text);
                    node.setAttribute("href", "#");
                    node.setAttribute("id", "flin_" + list_name);
                    node.onclick = function () {
                        addToList(list_name);
                    };
                    document.getElementById("list_names_container").appendChild(node);
                    //alert(document.getElementById("list_names_container"));
                } else if (add_remove_ind === 0) {
                    // remove
                    var node = document.getElementById("flin_" + list_name);
                    document.getElementById("list_names_container").removeChild(node);
                }
            }
            
        </script>
        
    </head>
    <body>
        <table border="0">
            <tr>
                <td rowspan="3" width="500" valign="top">
                    <iframe id="mapViewPanel" src="mapView.jsp?analysis_name=<%=analysis_name%>&imagename=<%=imagename%>" width="500" height="880" frameBorder="0px"></iframe>
                </td>
                <td height="35">&nbsp;</td>
                <td rowspan="2" height="35">
                    <div class="dropdown">
                    <button class="dropbtn" title="Add Selected Features to Feature Lists. Click Feature Name To Select. Click Feature Lists To Create New Feature Lists.">Add To List</button>
                        <div id="list_names_container" class="dropdown-content">
                            <%  
                                while (iter.hasNext()) {   
                                    String list_name = (String) iter.next();
                            %>
                            <a id="flin_<%=list_name%>" onclick="addToList('<%=list_name%>')" href="#"><%=list_name%></a>
                            <%  }   %>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td height="50">
                    <iframe id="detailSearchHeaderPanel" src="detailedSearchResultHeader.jsp?analysis_name=<%=analysis_name%>" width="<%=detailedSearchPanel_width%>" height="50" frameBorder="0"></iframe>
                </td>
            </tr>
            <tr>
                <td>
                    <iframe id="detailSearchPanel" src="detailedSearchResultDisplayerScrollView.jsp?start=<%=start%>&analysis_name=<%=analysis_name%>" width="<%=detailedSearchPanel_width%>" height="780" frameBorder="0"></iframe>
                </td>
                <td>
                    <iframe id="featureLabelsPanel" src="featureLabelsScrollView.jsp?start=<%=start%>&analysis_name=<%=analysis_name%>" width="<%=FEATURE_LABEL_FRAME_WIDTH%>" height="780" frameBorder="0"></iframe>
                </td>
            </tr>
        </table>
        
        
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>