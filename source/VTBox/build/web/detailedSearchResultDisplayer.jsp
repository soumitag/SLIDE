<%-- 
    Document   : detailedSearchResultDisplayer
    Created on : 24 Mar, 2017, 1:53:41 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page import="java.util.HashMap"%>
<%@page import="structure.Data"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    Data db = analysis.database;
    
    HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;
    
    double num_features = db.features.size();
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    String TYPE = request.getParameter("type");
    if(TYPE == null) {
        TYPE = "dendrogram_map";
    }
    
    double start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        end = num_features;
    }
    
    double image_height = 750.0;
    num_features = end - start + 1;
    double feature_height = image_height/num_features;
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
    
    double image_width = left_buffer + search_results.size()*column_width + (search_results.size()-1)*gap;
    double left_position = 0;

    String updaterFuncName = "";
    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
        updaterFuncName = "callDetailedInfoUpdater";
    } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) {
        updaterFuncName = "callDetailedPathInfoUpdater";
    } else if (analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {
        updaterFuncName = "callDetailedGOInfoUpdater";
    }
%>

    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
                        
            .marker {
                position: absolute;
                left: -1px;
                width: 20px; 
                height: 3px;
                background: #73AD21;
                border: none;
                cursor: pointer;
            }
            
            .marker_container {
                position: relative; 
                top: 0px; 
                left: 0px; 
                height: 750px; 
                background: #EEEEEE; 
                border: 1px
            }
            
            .cross {
                border: 1px; 
                display: none; 
                opacity: 0.5; 
                cursor: pointer;
            }
            
            table {
                border-spacing: 5px 0px;
            }
            
            .td_head {
                 background: #EEEEEE;
                 width: 18px;
                 height: 35px;
                 text-align: center;
                 vertical-align: bottom;
            }
            
            td {
                width: 18px;
                background: #EEEEEE;
            }
        
        </style>
        
        <script>
        
            function callDetailedInfoUpdater(eid, analysis_name) {
                parent.showDetailedInfo(eid, analysis_name);
            }
            
            function callDetailedPathInfoUpdater(pid, analysis_name) {
                parent.showDetailedPathInfo(pid, analysis_name);
            }
            
            function callDetailedGOInfoUpdater(gid, analysis_name) {
                parent.showDetailedGOInfo(gid, analysis_name);
            }
            
            
            function toggleHighlightGenes(pathid, state) {
                //alert("3");
                //alert(state);
                //alert(pathid);
                var x = document.getElementsByName(pathid);
                var i;
                if (state === 1) {
                    //alert("in highlight");
                    //alert(x[0].getAttribute("style"));
                    for (i = 0; i < x.length; i++) {
                        //x[i].style.background = "#FFA500";
                        x[i].setAttribute("style","fill: #800000");
                    }
                } else {
                    for (i = 0; i < x.length; i++) {
                        //x[i].style.background = "#73AD21";
                        x[i].setAttribute("style","fill: #73AD21");
                    }
                }
            }
            
            function loadGeneHighlights() {
                //alert("Looking for highlights");
                pathid = parent.parent.selected_search_tag_label;
                //alert(pathid);
                
                var x = document.getElementsByName(pathid);
                var i;
                for (i = 0; i < x.length; i++) {
                    x[i].setAttribute("style","fill: #800000");
                }
            }
        
        </script>
    </head>
    
    <body>
    
    <%  if(TYPE.equals("global_map"))   {     %>
        <svg height="<%=image_height%>" width="<%=image_width%>" style="position: absolute; top: 2px; left: 0px">
    <%  }   else if(TYPE.equals("dendrogram_map"))   {     %>
        <svg height="<%=image_height%>" width="<%=image_width%>" style="position: absolute; top: 20px; left: 0px">
    <%  }     %>
        
        
    <%  for(int i = 0; i < search_results.size(); i++) {     %>
            
            <g  id="search_<%=i%>">
    <%        
            left_position = left_buffer + i*column_width + (i-1)*gap;
            String left = left_position + "px";
    %>
            
            <rect x="<%=left%>" y="0" height="<%=image_height%>px" width="<%=column_width%>px" style="fill: #EEEEEE; z-index: 0" />
            
    <%        
            ArrayList <CompactSearchResultContainer> search_results_i = search_results.get(i);

                for (int j = 0; j < search_results_i.size(); j++) {
                    
                    CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                    ArrayList <Integer> positions = null;
                    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
                        positions = entrezPosMap.get(search_results_ij.entrez_id);
                    } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
                               analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION){
                        positions = entrezPosMap.get(search_results_ij.group_id.toUpperCase());
                    }
                    
                    if (positions != null) {
                        
                        for (int k = 0; k < positions.size(); k++) {
                            
                            if (positions.get(k) >= start && positions.get(k) <= end) {
                                
                                double top = feature_height*(positions.get(k) - start);
                                double feature_display_height = feature_height;
                                if (feature_height < 2.0) {
                                    feature_display_height = 2.0;
                                }
                                //double top = mid - feature_display_height/2.0;
                                
                                if (search_results_ij.type == CompactSearchResultContainer.TYPE_PATH) {

                                    String eid = search_results_ij.getEntrezID();
                                    String pathid = search_results_ij.getPathID();
        %>

                                    <rect id='<%=i + "_" + pathid%>' name='<%=i + "_" + pathid%>' x="<%=left%>" y="<%=top%>" height="<%=feature_display_height%>px" width="<%=column_width%>px" style="fill: #73AD21; z-index: 1" onclick='<%=updaterFuncName%>(<%=eid%>, "<%=analysis_name%>")' />
                                    
        <%
                                } else if (search_results_ij.type == CompactSearchResultContainer.TYPE_GO) {

                                    String eid = search_results_ij.getEntrezID();
                                    String goid = search_results_ij.getGOID();
        %>

                                    <rect id='<%=i + "_" + goid%>' name='<%=i + "_" + goid%>' x="<%=left%>" y="<%=top%>" height="<%=feature_display_height%>px" width="<%=column_width%>px" style="fill: #73AD21" onclick='<%=updaterFuncName%>(<%=eid%>, "<%=analysis_name%>")' />
                                    
        <%
                                } else if (search_results_ij.type == CompactSearchResultContainer.TYPE_GENE) {

                                    String eid = search_results_ij.getEntrezID();
        %>

                                    <rect id='<%=i + "_" + eid%>' name='<%=i + "_" + eid%>' x="<%=left%>" y="<%=top%>" height="<%=feature_display_height%>px" width="<%=column_width%>px" style="fill: #73AD21" onclick='<%=updaterFuncName%>(<%=eid%>, "<%=analysis_name%>")' />
                                    
        <%                      }
                            }
                        }
                    }
                }
        %>
            </g>
        <%    
        }
        %>
    
        </svg>
    
    </body>
    
    <script>
        loadGeneHighlights ();
    </script>
    
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>