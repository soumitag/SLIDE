<%-- 
    Document   : saveViz_GlobalView
    Created on : Aug 30, 2017, 9:50:30 AM
    Author     : abhik
--%>
<%@page import="graphics.layouts.DrillDownPanelLayout"%>
<%@page import="structure.Data"%>
<%@page import="searcher.GeneObject"%>
<%@page import="structure.MetaData"%>
<%@page import="utils.Utils"%>
<%@page import="graphics.HeatmapData"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page import="algorithms.clustering.TreeNode"%>
<%@page import="java.util.HashMap"%>
<%@page import="graphics.Heatmap"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

try {
    
    String analysis_name = request.getParameter("analysis_name");
    String base_url = (String)session.getAttribute("base_url");
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    String showDendrogram = request.getParameter("show_dendrogram");
    String showHistogram = request.getParameter("show_hist");
    String showSearchTags = request.getParameter("show_search_tags");
    
    //int imgHeight = 750;
    DrillDownPanelLayout drill_down_layout = analysis.visualization_params.drill_down_layout;
    int imgHeight = (int)drill_down_layout.GLOBAL_VIEW_FIG_HEIGHT;
    int histHeight = 200;
    double search_result_header_height = 40.0;
    double svg_height = imgHeight + search_result_header_height + 30.0 + histHeight + 100;
    
    // heatmap params
    int start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        int max_features = analysis.database.metadata.nFeatures;
        end = max_features - 1;
    }
    
    // dendrogram params
    BinaryTree btree = analysis.linkage_tree;
    int root_node_id;
    String start_node_id = request.getParameter("start_node_id");

    if (start_node_id != null && start_node_id != "" && !start_node_id.equals("null")) {
        root_node_id = Integer.parseInt(start_node_id);
    } else {
        root_node_id = btree.root_node_id;
    }

    double dendrogramWidth = 300;
    HashMap <Integer, TreeNode> treeMap = null;
    if (showDendrogram.equalsIgnoreCase("yes")) { 
        dendrogramWidth = 300;
        ArrayList <Integer> sub_tree = btree.breadthFirstSearch(root_node_id, 25);
        treeMap = btree.buildSubTree(sub_tree);
        btree.computeEdgeCoordinatesPostOrder (treeMap, treeMap.get(root_node_id));
        btree.rescaleEdgeCoordinates(treeMap, imgHeight, dendrogramWidth);
    }
                
    // search results params
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    double search_results_column_width = 20.0;
    double search_results_column_gap = 5.0;
    double search_results_width = search_results.size()*search_results_column_width + (search_results.size()-1)*search_results_column_gap;
    
    int num_features = end - start + 1;
    double feature_height = (imgHeight*1.0)/(num_features*1.0);
    
    // feature labels params
    int featureLabelsWidth = 0;
    if (feature_height >= 15) {
        featureLabelsWidth = 250;
    }
    
    int globalHeatmapWidth = 250;
    Heatmap heatmap = analysis.heatmap;
    String imagename = heatmap.buildMapImage(
            start, end, globalHeatmapWidth, imgHeight, "saveviz_globalview_jsp", HeatmapData.TYPE_IMAGE);
    //String imagewebpath = session.getAttribute("base_url") + "/temp/images/" + imagename;
    //String imagewebpath = "http://localhost:8080/VTBox/images/" + imagename;
    
    double left_offset = 10;
    
    double svg_width = 10 + dendrogramWidth + 10.0 + globalHeatmapWidth + 10.0 + search_results_width + 10.0 + featureLabelsWidth;
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

    </head>
    <body>
        <svg height="<%=svg_height%>" width="<%=svg_width%>" style="position: absolute; top: 20px; left: 0px">
        <rect x="0" y="20" width="<%=svg_width%>" height="<%=svg_height%>" style="fill:white;" />
            <% if (showDendrogram.equalsIgnoreCase("yes")) { %>

                <g>
                <%

                    Iterator iterator = treeMap.keySet().iterator(); 
                    while (iterator.hasNext()){

                        int node_id = (Integer)iterator.next();
                        TreeNode N = treeMap.get(node_id);

                        if (!N.isSubTreeLeaf) {

                            double p0_x = N.p0_x + 5.0 + left_offset;
                            double p1_x = N.p1_x + 5.0 + left_offset;
                            double p2_x = N.p2_x + 5.0 + left_offset;
                            double p3_x = N.p3_x + 5.0 + left_offset;

                            double p0_y = N.p0_y + search_result_header_height;
                            double p1_y = N.p1_y + search_result_header_height;
                            double p2_y = N.p2_y + search_result_header_height;
                            double p3_y = N.p3_y + search_result_header_height;
                %>

            <line id="<%=node_id%>_1" name="clust_<%=node_id%>" x1="<%=p0_x%>" y1="<%=p0_y%>" x2="<%=p1_x%>" y2="<%=p1_y%>" style="stroke:rgb(192,192,192);stroke-width:2"  onclick="loadSubTree(<%=N.leftChild.nodeID%>,<%=N.leftChild.start%>,<%=N.leftChild.end%>)" onmouseover="highlightCluster(<%=N.leftChild.nodeID%>,'')" onmouseout="deHighlightCluster(<%=N.leftChild.nodeID%>,'')" />
       
            <line id="<%=node_id%>_2" name="clust_<%=node_id%>" x1="<%=p1_x%>" y1="<%=p1_y%>" x2="<%=p2_x%>" y2="<%=p2_y%>" style="stroke:rgb(192,192,192);stroke-width:2" onclick="loadSubTree(<%=node_id%>,<%=N.start%>,<%=N.end%>)" onmouseover="highlightCluster(<%=node_id%>,'',<%=N.start%>,<%=N.end%>)" onmouseout="deHighlightCluster(<%=node_id%>,'',<%=N.start%>,<%=N.end%>)" onmousedown='hideContextMenu()' onmouseup='hideContextMenu()' oncontextmenu="showContextMenu(event,<%=N.start%>,<%=N.end%>)"/>
        
            <line id="<%=node_id%>_3" name="clust_<%=node_id%>" x1="<%=p2_x%>" y1="<%=p2_y%>" x2="<%=p3_x%>" y2="<%=p3_y%>" style="stroke:rgb(192,192,192);stroke-width:2" />

            <%  
                        }
                    }

                    left_offset = left_offset + dendrogramWidth + 10;

                }
            %>

                </g>

                <g id="heatmap_g">
                    <image xlink:href="<%=base_url%>HeatmapImageServer?analysis_name=<%=analysis_name%>&imagename=<%=imagename%>" x="<%=left_offset%>px" y="40px" height="<%=imgHeight%>px" width="250px" />
                </g>
            <% 
                left_offset = left_offset + globalHeatmapWidth + 10.0; 
            %>
                
            <% if (showSearchTags.equalsIgnoreCase("yes")) {
                
                HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;

                //imgWidth = globalHeatmapWidth + 10.0 + search_results.size()*column_width + (search_results.size()-1)*gap;
                double left_position = 0;
                
                search_results = analysis.search_results;
                
                for(int i = 0; i < search_results.size(); i++) {     %>
                
                    <g  id="search_<%=i%>">
                    <%        
                        left_position = left_offset + i*search_results_column_width + (i-1)*search_results_column_gap;
                        double search_head_center = left_position + (search_results_column_width/2.0);
                    %>
                        <rect x="<%=left_position%>px" y="0" height="38px" width="<%=search_results_column_width%>px" style="fill: #EEEEEE; z-index: 0" />
                        <text x="<%=search_head_center%>px" text-anchor="middle" y="15" font-family="Verdana" font-size="12" fill="black"><%=i%></text>
                        <rect x="<%=left_position%>px" y="40" height="<%=imgHeight%>px" width="<%=search_results_column_width%>px" style="fill: #EEEEEE; z-index: 0" />
            
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

                                        double top = 40.0 + feature_height*(positions.get(k) - start);
                                        double feature_display_height = feature_height;
                                        if (feature_height < 2.0) {
                                            feature_display_height = 2.0;
                                        }
                    %>
                                            <rect x="<%=left_position%>" y="<%=top%>" height="<%=feature_display_height%>px" width="<%=search_results_column_width%>px" style="fill: #73AD21;" />
                                                     
            <%                      }
                                }
                            }
                        }
                }

                left_offset = left_offset + search_results_width + 10.0; 
            }
            %>
                    </g>
    
    
                <% if (feature_height >= 15) { %>
                    
                    <g>
    
                    <%
                        BinaryTree linkage_tree = analysis.linkage_tree;
                        
                        for (int i=(int)start; i<=(int)end; i++) {
                            
                            int index = linkage_tree.leaf_ordering.get(i);
                            
                            String genes = analysis.database.features.get(index).getFormattedFeatureName(analysis);
                            
                            double mid = search_result_header_height + feature_height*(i - start) + feature_height*0.60;
                    %>

                            <text id="label_<%=i%>" x="<%=left_offset%>" y="<%=mid%>" font-family="Verdana" font-size="12" fill="black" style="display: inline">
                                <%=genes%>
                            </text>

                    <%
                        }
                    %>

                    </g>
                    
                <%
                    }
                %>
                    
                <% if (showHistogram.equalsIgnoreCase("yes")) { %>
        
                    <jsp:include page="/WEB-INF/jspf/histogram_fragment.jspf" flush="true">
                        <jsp:param name="analysis_name" value="<%=analysis_name%>" />
                        <jsp:param name="y_offset" value="<%=(imgHeight+search_result_header_height+30.0)%>" />
                    </jsp:include>

                <% } %>
        </svg>
        
    </body>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>