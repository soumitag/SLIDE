<%-- 
    Document   : visualizationHome
    Created on : 24 Feb, 2017, 9:32:26 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    String visualization_level = request.getParameter("visualization_level");
    
    boolean do_clustering = Boolean.parseBoolean(analysis.clustering_params.get("do_clustering"));
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;

    String hdiFrame_width = "285";
    if (search_results.size() == 0) {
        hdiFrame_width = "285";
    } else {
        hdiFrame_width = 285 + (search_results.size()*32) + "";
    }
    
    String scrollPanel_width = "730";
    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
        if (search_results.size() == 0) {
            scrollPanel_width = "730";
        } else {
            scrollPanel_width = 730 + (search_results.size()*32) + "";
        }
    }  else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) {
        scrollPanel_width = "880";
    }
    
    String searchKeysFrame_width = "250";
    //if (search_results.size() > 0) {
    //    searchKeysFrame_width = "250";
    //}
%>
<html>
    <script type = "text/javascript" language = "JavaScript" src="main.js"></script>
    <link rel="stylesheet" href="vtbox-main.css">
    <script>
        
        function toggleSelectionPanel() {
            var sp = parent.document.getElementById("selectionPanel");
            var sph = document.getElementById("spHandle");
            if (sp.style.display === 'inline') {
                sp.style.display = 'none';
                sph.innerHTML = 'Show Control Panel';
            } else {
                sp.style.display = 'inline';
                sph.innerHTML = 'Hide Control Panel';
            }
        }
         
        function updateMap(start, end) {
            document.getElementById("scrollPanel").contentWindow.loadMap(start, end);
        }
        
        function toggleHistogramPanel() {
            var hh = document.getElementById("histHandle");
            var hp = document.getElementById("histPanel");
            var gif = document.getElementById("geneInfoFrame");
            if (hp.style.display === 'inline') {
                hp.style.display = 'none';
                gif.height = '100%';
                hh.innerHTML = 'Show Histogram';
            } else {
                hp.style.display = 'inline';
                gif.height = '66%';
                hh.innerHTML = 'Hide Histogram';
            }
        }
        
        function saveAnalysis() {
            
            showModalWindow('downloadInfo.jsp?mode=start&analysis_name=<%=analysis_name%>', '40%', '200px')
            
            var url = "<%=base_url%>SerializeAnalysisServlet?analysis_name=<%=analysis_name%>";
            document.getElementById("invisible_Download_Frame").contentWindow.location.replace(url);
        }
        
        function saveDetailedView() {
            var scrollPanel = document.getElementById('scrollPanel');
            var scrollView = scrollPanel.contentDocument || scrollPanel.contentWindow.document;
            var mapViewPanel = scrollView.getElementById('mapViewPanel');
            var mapview_url = mapViewPanel.contentWindow.location.href + "&print_mode=yes";
            var popup = window.open(mapview_url);
        }
        
        function downloadCompletion(status, filename) {
            var str = "&nbsp;&nbsp;&nbsp;<span id='ribbon_close' class='close' onclick='closeRibbon()'>&times;</span>";
            if (status === "") {
                document.getElementById("msg_ribbon_div").innerHTML = 
                        "<i>" + filename + ".slide </i> file has been downloaded." + str;
            } else {
                document.getElementById("msg_ribbon_div").innerHTML = 
                        "Oops! Looks like something went wrong. Please try again." + str;
            }
        }
        
        function closeRibbon() {
            document.getElementById("msg_ribbon_div").style.display = "none";
        }
        
        function showGlobal() {
            var msg_iframe = document.getElementById("hdiFrameLoadingMsg");
            var global_iframe = document.getElementById("hdiFrame");
            msg_iframe.height = "0px";
            msg_iframe.width = "0px";
            msg_iframe.style.visibility = "hidden";
            global_iframe.style.visibility = "visible";
        }
        
    </script>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>

            .maintable {
                position: absolute; 
                top: 0px; 
                left: 0px; 
                overflow-x: scroll; 
            }
            
            td {
                font-family: verdana;
                border-width: 1px; 
                border-color: #ededed; 
                border-style: solid;
                background-color: #fcfcfc;
            }

            /* The Modal (background) */
            .modal {
                display: none; /* Hidden by default */
                position: fixed; /* Stay in place */
                z-index: 1; /* Sit on top */
                padding-top: 100px; /* Location of the box */
                left: 0;
                top: 0;
                width: 100%; /* Full width */
                height: 100%; /* Full height */
                overflow: auto; /* Enable scroll if needed */
                background-color: rgb(0,0,0); /* Fallback color */
                background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            }

            /* Modal Content */
            .modal-content {
                background-color: #fefefe;
                margin: auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                height: 500px;
            }

            /* The Close Button */
            .close {
                color: #aaaaaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }
            
        </style>
    </head>
    <body>

        <!-- The Modal -->
        <div id="myModal" class="modal">

            <!-- Modal content -->
            <div id="modal_inside" class="modal-content">
                <span id="modal_close" class="close">&times;</span>
                <iframe name="modalFrame" id="modalFrame" src="" marginwidth="0" height="500" width="100%" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
            </div>

        </div>
        
        <div class="msg_ribbon" id="msg_ribbon_div">
            Testing
        </div>
        
        <table class="maintable" height="99%" width="1910px" cellspacing="0" cellpadding="0"> 

            <tr>
                <td colspan="5" name="ribbonPanel" id="ribbonPanel" height="5%">

                    <table>
                        <tr>
                            <td valign="top" style="min-height: 40px;">
                                <iframe name="searchFrame" id="searchFrame" src="searcher.jsp?analysis_name=<%=analysis_name%>" marginwidth="0" height="29" width="500" frameBorder="0" style="min-height: 35px;"></iframe>
                            </td>
                            
                            <td>
                                &nbsp;
                                <button name="spHandle" class="dropbtn" id="spHandle" onclick="toggleSelectionPanel()"> Hide Control Panel </button> &nbsp;
                                <button name="saveAnalysis" class="dropbtn" id="saveAnalysis" onclick="saveAnalysis()"> Save Analysis </button> &nbsp;
                                <button name="saveViz" class="dropbtn" id="saveViz" onclick="showModalWindow('saveSVGs.jsp?analysis_name=<%=analysis_name%>', '60%', '450px')">Save Visualizations</button> &nbsp;
                                <div class="dropdown">
                                    <button name="featureList" class="dropbtn" id="createList"> Feature List </button> &nbsp;
                                    <div class="dropdown-content">
                                        <a onclick="showModalWindow('createFeatureList.jsp?mode=input&analysis_name=<%=analysis_name%>', '40%', '250px')" href="#"> Create </a>
                                        <a onclick="showModalWindow('saveFeatureList.jsp?mode=input&analysis_name=<%=analysis_name%>', '40%', '250px')" href="#"> Save </a>
                                        <a onclick="showModalWindow('viewDeleteFeatureList.jsp?mode=input&analysis_name=<%=analysis_name%>', '40%', '550px')" href="#"> View / Delete </a>
                                    </div>
                                </div>
                                <div class="dropdown">
                                    <button name="createSubAnal" class="dropbtn" id="createSubAnal"> Sub-Analysis </button> &nbsp;
                                    <div class="dropdown-content">
                                        <a onclick="showModalWindow('createSubAnalysis.jsp?mode=input&analysis_name=<%=analysis_name%>', '60%', '500px')" href="#"> Create </a>
                                        <a onclick="showModalWindow('openSubAnalysis.jsp?mode=input&analysis_name=<%=analysis_name%>', '60%', '500px')" href="#"> Open </a>
                                    </div>
                                </div>
                                <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
                                <button name="createFuncEnrichment" class="dropbtn" id="createFuncEnrichment" onclick="showModalWindow('createEnrichmentAnalysis.jsp?mode=input&analysis_name=<%=analysis_name%>', '60%', '550px')"> Functional Enrichment </button> &nbsp;
                                <%  }   %>
                                <button name="histHandle" class="dropbtn" id="histHandle" onclick="toggleHistogramPanel()"> Hide Histogram </button>
                            </td>
                            
                        </tr>
                    </table>

                </td>
            </tr>
            
            <tr>
                
                <td rowspan="2" align="top" valign="top" width="300">
                    <% if (do_clustering) { %>
                        <iframe name="hdiFrameLoadingMsg" id="hdiFrameLoadingMsg" src="hierarchicalClusteringFeedback.jsp?is_clustering=true" marginwidth="0" height="100%" width="<%=hdiFrame_width%>" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
                    <% } else { %>
                        <iframe name="hdiFrameLoadingMsg" id="hdiFrameLoadingMsg" src="hierarchicalClusteringFeedback.jsp?is_clustering=false" marginwidth="0" height="100%" width="<%=hdiFrame_width%>" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
                    <% } %>
                    <iframe name="hdiFrame" id="hdiFrame" src="HeatmapDendrogramGenerator?analysis_name=<%=analysis_name%>" marginwidth="0" height="100%" width="<%=hdiFrame_width%>" frameBorder="0" style="position: relative; top: 0px; left: 0px; visibility: hidden"></iframe>
                    <iframe name="invisible_Download_Frame" id="invisible_Download_Frame" src="" marginwidth="0" height="0" width="0" frameBorder="0"></iframe>
                </td>
                
                <td rowspan="2" height="100%" width="250"> 
                    <iframe name="searchKeysFrame" id="searchKeysFrame" width="<%=searchKeysFrame_width%>" height="100%" frameBorder="0" src="searchKey.jsp?analysis_name=<%=analysis_name%>"></iframe>
                </td>
                
                <%  if (do_clustering) { %>
                
                <td rowspan="2" height="100%"> 
                    <iframe name="scrollPanel" id="scrollPanel" src="" marginwidth="0" height="100%" width="<%=scrollPanel_width%>" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
                </td>
                
                <td rowspan="2" height="100%"> 
                    <iframe name="drillDownPanel" id="drillDownPanel" src="" marginwidth="0" height="100%" width="800" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
                </td>
                
                <%  } else {    %>
                
                <td rowspan="2" colspan="2" height="100%"> 
                    <iframe name="scrollPanel" id="scrollPanel" src="" marginwidth="0" height="100%" width="<%=scrollPanel_width%>" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
                </td>
                
                <%  }   %>
                
                <td  rowspan="2" width="550">     
                    <iframe id="histPanel" src="" width="550" height="34%" frameBorder="0" style="display: inline"></iframe>
                    <iframe name="geneInfoFrame" id="geneInfoFrame" src="gene.jsp?analysis_name=<%=analysis_name%>" marginwidth="0" width="550" height="66%" frameBorder="0"></iframe>
                </td>
                
            </tr>
            
        </table>
    </body>
    
    <script>
        
        // Get the modal
        var modal = document.getElementById('myModal');
        
        // Get the button that opens the modal
        var btn = document.getElementById("createSubAnal");
        
        // Get the <span> element that closes the modal
        var span = document.getElementById("modal_close");
        var ribbon_span = document.getElementById("ribbon_close");

        var modal_inside = document.getElementById('modal_inside');
        var modal_frame = document.getElementById('modalFrame');

        // When the user clicks the button, open the modal 
        function showModalWindow(src, width, height) {
            modal_frame.src = src;
            modal.style.display = "block";
            modal_inside.style.width = width;
            modal_inside.style.height = height;
        }

        // When the user clicks on <span> (x), close the modal
        span.onclick = function() {
            modal.style.display = "none";
        }
        
        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
        
    </script>
    
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>