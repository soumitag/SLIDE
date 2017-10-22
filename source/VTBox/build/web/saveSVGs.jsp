<%-- 
    Document   : saveSVGs
    Created on : 19 Aug, 2017, 7:02:58 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    //int start = analysis.state_variables.get("detailed_view_start").intValue();
    //int end = analysis.state_variables.get("detailed_view_end").intValue();
    int start = analysis.state_variables.getDetailedViewStart();
    int end = analysis.state_variables.getDetailedViewEnd();
    
    int nCols = analysis.database.datacells.width;
    int max_rows = (int)Math.floor(50000/nCols);
    
    /*
    String dendrogram_start = "";
    String dendrogram_end = "";
    String start_node_id = "";
    if (analysis.state_variables.containsKey("start_node_id")) {
        start_node_id = analysis.state_variables.get("start_node_id").intValue() + "";
    }
    if (analysis.state_variables.containsKey("dendrogram_start")) {
        dendrogram_start = analysis.state_variables.get("dendrogram_start").intValue() + "";
    }
    if (analysis.state_variables.containsKey("dendrogram_end")) {
        dendrogram_end = analysis.state_variables.get("dendrogram_end").intValue() + "";
    }
    */

    Integer start_node = analysis.state_variables.getStartNodeID();
    String start_node_id = (start_node == null) ? "" : start_node + "";
    
    Integer d_start = analysis.state_variables.getDendrogramStart();
    String dendrogram_start = (d_start == null) ? "" : d_start + "";
    
    Integer d_end = analysis.state_variables.getDendrogramEnd();
    String dendrogram_end = (d_end == null) ? "" : d_end + "";

%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Save Visualizations</title>
        <style>
            /* Style the tab */
            div.tab {
                overflow: hidden;
                border: 1px solid #ccc;
                background-color: #f1f1f1;
            }

            /* Style the buttons inside the tab */
            div.tab button {
                background-color: inherit;
                float: left;
                border: none;
                outline: none;
                cursor: pointer;
                padding: 14px 16px;
                transition: 0.3s;
            }

            /* Change background color of buttons on hover */
            div.tab button:hover {
                background-color: #ddd;
            }

            /* Create an active/current tablink class */
            div.tab button.active {
                background-color: #ccc;
            }

            /* Style the tab content */
            .tabcontent {
                display: none;
                padding: 6px 12px;
                border: 1px solid #ccc;
                border-top: none;
            }
        </style>
        
        <script>
    
            function openTab(evt, tabName) {
                // Declare all variables
                var i, tabcontent, tablinks;

                // Get all elements with class="tabcontent" and hide them
                tabcontent = document.getElementsByClassName("tabcontent");
                for (i = 0; i < tabcontent.length; i++) {
                    tabcontent[i].style.display = "none";
                }

                // Get all elements with class="tablinks" and remove the class "active"
                tablinks = document.getElementsByClassName("tablinks");
                for (i = 0; i < tablinks.length; i++) {
                    tablinks[i].className = tablinks[i].className.replace(" active", "");
                }

                // Show the current tab, and add an "active" class to the button that opened the tab
                document.getElementById(tabName).style.display = "block";
                evt.currentTarget.className += " active";
            }
            
            function saveGlobalView() {
                
                if (document.getElementById('global_view_incl_search_tags').checked) {
                    gvi_search_tags = document.getElementById('global_view_incl_search_tags').value;
                }
                
                if (document.getElementById('global_view_incl_hist').checked) {
                    gvi_hist = document.getElementById('global_view_incl_hist').value;
                }
                
                if (document.getElementById('global_view_incl_dend') != null) {
                    if (document.getElementById('global_view_incl_dend').checked) {
                        gvi_dend = document.getElementById('global_view_incl_dend').value;
                    }
                } else {
                    gvi_dend = "no";
                }
                
                //alert("2");
                
                var mapview_url = "<%=base_url%>saveViz_GlobalView.jsp?analysis_name=<%=analysis_name%>&show_search_tags=" + gvi_search_tags + "&show_hist=" + gvi_hist + "&show_dendrogram=" + gvi_dend;
                //alert(mapview_url);
                window.open(mapview_url);
                
            }
            
            function saveDendrogramView() {
                
                if (document.getElementById('dend_view_incl_search_tags').checked) {
                    gvi_search_tags = document.getElementById('dend_view_incl_search_tags').value;
                }
                
                if (document.getElementById('dend_view_incl_hist').checked) {
                    gvi_hist = document.getElementById('dend_view_incl_hist').value;
                }
                
                if (document.getElementById('dend_view_incl_dend') != null) {
                    if (document.getElementById('dend_view_incl_dend').checked) {
                        gvi_dend = document.getElementById('dend_view_incl_dend').value;
                    }
                } else {
                    gvi_dend = "no";
                }
                
                //alert("2");
                
                var mapview_url = "<%=base_url%>saveViz_GlobalView.jsp?analysis_name=<%=analysis_name%>" + 
                        "&show_search_tags=" + gvi_search_tags + 
                        "&show_hist=" + gvi_hist + 
                        "&show_dendrogram=" + gvi_dend + 
                        "&start_node_id=<%=start_node_id%>" + 
                        "&start=<%=dendrogram_start%>" + 
                        "&end=<%=dendrogram_end%>";
                //alert(mapview_url);
                window.open(mapview_url);
                
            }
    
            function saveDetailedView() {
                
                if (document.getElementById('detailed_view_incl_search_tags').checked) {
                    dvi_search_tags = document.getElementById('detailed_view_incl_search_tags').value;
                }
                
                if (document.getElementById('detailed_view_incl_hist').checked) {
                    dvi_hist = document.getElementById('detailed_view_incl_hist').value;
                }
                //alert( document.getElementById('detailed_view_start').value);
                dvi_start = document.getElementById('detailed_view_start').value;
                dvi_end = document.getElementById('detailed_view_end').value;
                //alert("2");
                
                row_range = dvi_end - dvi_start;
                if (row_range > <%=max_rows%>) {
                    alert("The maximum number of rows to be displayed must be less than <%=max_rows%>.");
                } else {
                    var mapview_url = "<%=base_url%>saveViz_MapView.jsp?analysis_name=<%=analysis_name%>&detailed_view_incl_search_tags=" + dvi_search_tags + "&detailed_view_incl_hist=" + dvi_hist + "&start=" + dvi_start + "&end=" + dvi_end;
                    var popup = window.open(mapview_url);
                }
                
            }
    
        </script>
    </head>
    <body>
        
        <div class="tab">
            <button class="tablinks" onclick="openTab(event, 'GlobalMap')">Global Map</button>
            <button class="tablinks" onclick="openTab(event, 'DetailedView')">Detailed View</button>
            <button class="tablinks" onclick="openTab(event, 'DendrogramView')">Dendrogram View</button>
        </div>
        
        
        <div id="GlobalMap" class="tabcontent">
            <h3>Global Map</h3>
            <table>
                <tr>
                    <td width="35%">
                        Include Search Tags
                    </td>
                    <td>
                        <input type="radio" id="global_view_incl_search_tags" name="global_view_incl_search_tags" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="global_view_incl_search_tags" name="global_view_incl_search_tags" value="no"> No <br>
                    </td>
                </tr>
                <tr>
                    <td width="35%">
                        Include Histogram
                    </td>
                    <td>
                        <input type="radio" id="global_view_incl_hist" name="global_view_incl_hist" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="global_view_incl_hist" name="global_view_incl_hist" value="no"> No <br>
                    </td>
                </tr>
                <% if (analysis.isTreeAvailable) { %>
                <tr>
                    <td>
                        Include Dendrogram
                    </td>
                    <td>
                        <input type="radio" id="global_view_incl_dend" name="global_view_incl_dend" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="global_view_incl_dend" name="global_view_incl_dend" value="no"> No <br>
                    </td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="2">
                        <!--<input type="button" value="Generate" onclick="saveGlobalView()">-->
                        <button type="button" id="generate_svg" title="Generate Visualization" onclick="saveGlobalView()">Generate</button>
                        
                    </td>
                </tr>
            </table>
        </div>

        
        <div id="DetailedView" class="tabcontent">
            <h3>Detailed View</h3>
            <table>
                <tr>
                    <td>
                        Start Row
                    </td>
                    <td>
                        <input type="text" id="detailed_view_start" name="detailed_view_start" value="<%=start%>">&nbsp;
                        Current Value: <%=start%>
                    </td>
                </tr>
                <tr>
                    <td>
                        End Row
                    </td>
                    <td>
                        <input type="text" id="detailed_view_end" name="detailed_view_end" value="<%=end%>">&nbsp;
                        Current Value: <%=end%>. Maximum <%=max_rows%> Rows Allowed.
                    </td>
                </tr>
                <tr>
                    <td>
                        Include Search Tags
                    </td>
                    <td>
                        <input type="radio" id="detailed_view_incl_search_tags" name="detailed_view_incl_search_tags" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="detailed_view_incl_search_tags" name="detailed_view_incl_search_tags" value="no"> No <br>
                    </td>
                </tr>
                <tr>
                    <td>
                        Include Histogram
                    </td>
                    <td>
                        <input type="radio" id="detailed_view_incl_hist" name="detailed_view_incl_hist" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="detailed_view_incl_hist" name="detailed_view_incl_hist" value="no"> No <br>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <!--<input type="button" value="Generate" onclick="saveDetailedView()">-->
                        <button type="button" id="generate_svg" title="Generate Visualization" onclick="saveDetailedView()">Generate</button>
                    </td>
                </tr>
            </table>
        </div>

        <div id="DendrogramView" class="tabcontent">
            <h3>Dendrogram View</h3>
            <table>
                <tr>
                    <td width="35%">
                        Include Search Tags
                    </td>
                    <td>
                        <input type="radio" id="dend_view_incl_search_tags" name="dend_view_incl_search_tags" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="dend_view_incl_search_tags" name="dend_view_incl_search_tags" value="no"> No <br>
                    </td>
                </tr>
                <tr>
                    <td width="35%">
                        Include Histogram
                    </td>
                    <td>
                        <input type="radio" id="dend_view_incl_hist" name="dend_view_incl_hist" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="dend_view_incl_hist" name="dend_view_incl_hist" value="no"> No <br>
                    </td>
                </tr>
                <% if (analysis.isTreeAvailable) { %>
                <tr>
                    <td>
                        Include Dendrogram
                    </td>
                    <td>
                        <input type="radio" id="dend_view_incl_dend" name="dend_view_incl_dend" value="yes" checked="checked"> Yes &nbsp;
                        <input type="radio" id="dend_view_incl_dend" name="dend_view_incl_dend" value="no"> No <br>
                    </td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="2">
                        <!--<input type="button" value="Generate" onclick="saveDendrogramView()">-->
                        <button type="button" id="generate_svg" title="Generate Visualization" onclick="saveDendrogramView()">Generate</button>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>