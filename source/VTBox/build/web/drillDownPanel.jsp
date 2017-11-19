<%-- 
    Document   : drillDownPanel
    Created on : 26 Mar, 2017, 7:13:58 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.Data"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
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
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <link rel="stylesheet" href="vtbox-main.css">
        <script>
            function toggleHighlightGenes(pathid, state) {
                document.getElementById('detailSearchPanel').contentWindow.toggleHighlightGenes(pathid, state);
            }
    
            function loadSubTree(node_id, start, end) {
                window.location = "drillDownPanel.jsp?show_dendrogram=yes&start_node_id=" + node_id + "&start=" + start + "&end=" + end + "&analysis_name=<%=analysis_name%>";
            }
            
            function loadPrevious() {
                window.location = "drillDownPanel.jsp?show_dendrogram=yes&analysis_name=<%=analysis_name%>&pop_one_history=1";
            }
    
            function updateMap(start, end) {
                parent.updateMap(start, end);
            }
    
            function toggleHistogramPanel() {
                var sph = document.getElementById("histHandle");
                var sp = document.getElementById("histPanel");
                if (sp.style.display === 'inline') {
                    sp.style.display = 'none';
                    sph.innerHTML = 'Show Histogram';
                } else {
                    sp.style.display = 'inline';
                    sph.innerHTML = 'Hide Histogram';
                }
            }
            
            function refreshAllSearchPanes(num_current_searches) {
                //alert('Here');
                parent.update_search_result_post_deletion('<%=analysis_name%>', num_current_searches);
            }
            
            function refreshSearchPane() {
                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) + 32;
                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) + 32;
                var s = document.getElementById("start").value;
                var e = document.getElementById("end").value;
                var url_text = "detailedSearchResultDisplayer.jsp?start=" + s + "&end=" + e + "&analysis_name=<%=analysis_name%>&type=<%=TYPE%>";
                
                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);
                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace("detailedSearchResultHeader.jsp?analysis_name=<%=analysis_name%>");
            }
            
            function refreshSearchPane_PostDeletion() {
                document.getElementById('detailSearchPanel').width = parseFloat(document.getElementById('detailSearchPanel').width) - 32;
                document.getElementById('detailSearchHeaderPanel').width = parseFloat(document.getElementById('detailSearchHeaderPanel').width) - 32;
                var s = document.getElementById("start").value;
                var e = document.getElementById("end").value;
                var url_text = "detailedSearchResultDisplayer.jsp?start=" + s + "&end=" + e + "&analysis_name=<%=analysis_name%>&type=<%=TYPE%>";
                
                document.getElementById('detailSearchPanel').contentWindow.location.replace(url_text);
                document.getElementById('detailSearchHeaderPanel').contentWindow.location.replace("detailedSearchResultHeader.jsp?analysis_name=<%=analysis_name%>");
            }
            
            function saveAsPDF() {
                var popup = window.open("svg.jsp");
            }
            
            function showRect(start, end) {
                document.getElementById('detailHeatMapPanel').contentWindow.showRect(start, end);
            }
            
            function hideRect() {
                document.getElementById('detailHeatMapPanel').contentWindow.hideRect();
            }
            
            function showDetailedPathInfo (pid, analysis_name) {
                parent.showDetailedPathInfo(pid, analysis_name);
            }
            
            function showDetailedGOInfo (gid, analysis_name) {
                parent.showDetailedGOInfo(gid, analysis_name);
            }
            
            function showDetailedInfo(eid, analysis_name) {
                parent.showDetailedInfo(eid, analysis_name);
            }
            
            function showColHeader(colname) {
                var td = document.getElementById('column_header_td');
                td.innerHTML = colname;
            }
            
            function hideColHeader() {
                var td = document.getElementById('column_header_td');
                td.innerHTML = '';
            }
            
            $(window).load(function() {
                    $(".loader").fadeOut("slow");
            })
            
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
    
            var selected_list_name = "";
            var http = createRequestObject();
           
            function makeGetRequest (theGetText) {
                //make a connection to the server ... specifying that you intend to make a GET request 
                //to the server. Specifiy the page name and the URL parameters to send
                //alert(theGetText);
                http.open('get', theGetText);

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
                    //alert(response.trim());
                    
                    if (response.trim() == "1") {
                        alert('Features Added to ' + selected_list_name + '.');
                    } else {
                        alert('Features Could Not Be Added. Please Try Again.');
                    }
                    
                }
            }
            
            function addToList (list_name) {
                //alert(list_name);
                selected_list_name = list_name;
                var milliseconds = new Date().getTime();
                search_url = 'AddDataToList?mode=cluster' + '&list_name=' + 
                              list_name + '&start=' + 
                              <%=start%> + '&end=' + 
                              <%=end%> + '&timestamp=' + milliseconds +
                              '&analysis_name=<%=analysis_name%>';
                //alert(search_url);
                makeGetRequest(search_url);
            }
            
            function addRemoveListName(list_name, add_remove_ind) {
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
                } else if (add_remove_ind === 0) {
                    // remove
                    var node = document.getElementById("flin_" + list_name);
                    document.getElementById("list_names_container").removeChild(node);
                }
            }
            
            function askParentToShowMe() {
                parent.showGlobal();
            }
            
        </script>
        
    </head>
    <body onload="askParentToShowMe()">
        <div class="loader"></div>
        <table border="0">
            
            <%  if (showDendrogram.equalsIgnoreCase("yes")) {   %>
            
            <tr>
                <td height="50px">
                    <!--
                    &nbsp;<button name="histHandle" id="histHandle" onclick="toggleHistogramPanel()"> Hide Histogram </button>
                    &nbsp;<button name="saver" id="saver" onclick="saveAsPDF()"> Save Image </button>
                    -->
                    &nbsp;
                    <div class="dropdown">
                    <button class="dropbtn" title="Add All Features to Feature Lists. Click Feature Lists To Create New Feature Lists.">Add To List</button>
                        <div id="list_names_container" class="dropdown-content">
                            <%  
                                while (iter.hasNext()) {   
                                    String list_name = (String) iter.next();
                            %>
                            <a id="flin_<%=list_name%>" onclick="addToList('<%=list_name%>')" href="#"><%=list_name%></a>
                            <%  }   %>
                        </div>
                    </div>
                        <button type="button" onclick="loadPrevious();"> Back </button>
                </td>
                <td id="column_header_td" style="font-family: verdana; font-size: 10; color: black">
                    
                </td>
                <td>
                    <iframe id="detailSearchHeaderPanel" src="detailedSearchResultHeader.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>" width="<%=detailedSearchPanel_width%>" height="50" frameBorder="0"></iframe>
                </td>
                <td id="entrez_td" style="font-family: verdana; font-size: 12; color: blue">
                     
                </td>
            </tr>
            
            <tr>
                <td style="vertical-align: top">
                    <iframe id="dendrogramPanel" src="dendrogram.jsp?start_node_id=<%=start_node_id%>&analysis_name=<%=analysis_name%>" width="320" height="790" frameBorder="0"></iframe>
                </td>
                <td>
                    <iframe id="detailHeatMapPanel" src="detailedHeatMap.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>&type=dendrogram_map" width="260" height="820" frameBorder="0"></iframe>
                </td>
                <td style="vertical-align: top">
                    <iframe id="detailSearchPanel" src="detailedSearchResultDisplayer.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>&type=dendrogram_map" width="<%=detailedSearchPanel_width%>" height="790" frameBorder="0"></iframe>
                </td>
                <td style="vertical-align: top">
                <%  if (feature_height >= 15.0) {   %>
                    <iframe id="featureLabelsPanel" src="featurelabels.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>" width="<%=featureLabelsPanel_width%>" height="780" frameBorder="0"></iframe>
                <%  }   %>
                </td>
            </tr>
            <!--
            <tr>
                <td colspan="2">
                    <iframe id="histPanel" src="histogram.jsp?analysis_name=<%=analysis_name%>" width="570" height="250" frameBorder=="0" style="display: inline;"></iframe>
                </td>
                <td colspan="2">
                    &nbsp;
                </td>
            </tr>
            -->
            
            <%  } else {    %>
            
            
            <tr>
                
                <td height="50px">
                    <!--
                    &nbsp;<button name="histHandle" id="histHandle" onclick="toggleHistogramPanel()"> Show Histogram </button>
                    &nbsp;<button name="saver" id="saver" onclick="saveAsPDF()"> Save Image </button>
                    -->
                </td>
                
                <td>
                    <iframe id="detailSearchHeaderPanel" src="detailedSearchResultHeader.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>&type=global_map" width="<%=detailedSearchPanel_width%>" height="50" frameBorder="0"></iframe>
                </td>
                <td id="entrez_td" style="font-family: verdana; font-size: 12; color: blue">
                     
                </td>
            </tr>
            
            <tr>
                <td>
                    <iframe id="detailHeatMapPanel" src="detailedHeatMap.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>&type=global_map" width="280" height="760" frameBorder="0"></iframe>
                </td>
                <td colspan="2">
                    <iframe id="detailSearchPanel" src="detailedSearchResultDisplayer.jsp?start=<%=start%>&end=<%=end%>&analysis_name=<%=analysis_name%>&type=global_map" width="<%=detailedSearchPanel_width%>" height="760" frameBorder="0"></iframe>
                </td>
            </tr>
            
            <!--
            <tr>
                <td colspan="3">
                    <iframe id="histPanel" src="histogram.jsp?analysis_name=<%=analysis_name%>" height="250" frameBorder=="0" style="display: none;"></iframe>
                </td>
            </tr>
            -->
            <%  }   %>
            
            <input type="hidden" id="start" name="start" value="<%=start%>" />
            <input type="hidden" id="end" name="end" value="<%=end%>" />
            
        </table>
    </body>
    
    <script>

    <%  if (showDendrogram.equalsIgnoreCase("yes")) {   %>
        parent.showGlobalMapRect(<%=start%>, <%=end%>);
    <%  } else {   %>
        parent.loadScrollViewPanel('<%=analysis_name%>');
        parent.loadHistPanel('<%=analysis_name%>');
        <%  if (analysis.isTreeAvailable)   {   %>
            parent.loadDrillDownPanel('<%=analysis_name%>');
        <%  }   %>    
    <%  }   %>
    </script>
    
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>