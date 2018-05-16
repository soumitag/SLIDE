<%-- 
    Document   : mapView
    Created on : 23 Apr, 2017, 1:35:27 PM
    Author     : Soumita
--%>
<%@page import="searcher.GeneObject"%>
<%@page import="structure.MetaData"%>
<%@page import="utils.Utils"%>
<%@page import="graphics.HeatmapData"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="graphics.Heatmap"%>
<%@page import="structure.Data"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Data database = analysis.database;
    String[] headers = database.current_sample_names;
    
    Heatmap heatmap = analysis.heatmap;
    
    int TABLE_HEIGHT = 760;
    int CELL_HEIGHT = 20;
    // feature label params
    double feature_height = 20.0;       // ideally, should be same as CELL_HEIGHT
    double BORDER_STROKE_WIDTH = 0.5;
    // search results params
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
    
    int start = Integer.parseInt(request.getParameter("start"));
    int end = Integer.parseInt(request.getParameter("end"));
    String show_search_tags = request.getParameter("detailed_view_incl_search_tags");
    String show_histogram = request.getParameter("detailed_view_incl_hist");

    String imagename = heatmap.buildMapImage(start, end, "saveviz_mapview_jsp", HeatmapData.TYPE_ARRAY);
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    
    int MIN_TABLE_WIDTH = 500;
    int CELL_WIDTH = (int)Math.max(20.0, Math.floor(MIN_TABLE_WIDTH/rgb.length));
    int TABLE_WIDTH = (int)Math.max(MIN_TABLE_WIDTH, CELL_WIDTH*rgb.length);
    TABLE_HEIGHT = (int)CELL_HEIGHT*rgb[0].length;
    
    // used only in save viz, not in mapView.jsp
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    double search_result_width = 0.0;
    if (show_search_tags.equalsIgnoreCase("yes")) {
        search_result_width = left_buffer + search_results.size()*column_width + (search_results.size()-1)*gap;
    } else {
        search_result_width = 0.0;
    }
    
    String feature_width = request.getParameter("feature_width").trim();
    double feature_label_width = 200.0;
    try {
        feature_label_width = Double.parseDouble(feature_width);
    } catch (NumberFormatException e) {
        if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
            feature_label_width = 200.0;
        } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) {
            feature_label_width = 350.0;
        }
    }
    
    String sample_height = request.getParameter("sample_height").trim();
    double sample_name_height = 95.0;
    try {
        sample_name_height = Double.parseDouble(sample_height);
    } catch (NumberFormatException e) {
        sample_name_height = 95.0;
    }
    
    int histHeight = 200;
    double svg_height = sample_name_height + TABLE_HEIGHT + 30.0 + histHeight + 100.0; // 30 is the gap between heatmap and histogram; 100 is for the histogram x-axis labels
    double svg_width = TABLE_WIDTH + search_result_width + 10 + feature_label_width;
    
    HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;
    
    int x, y = 0;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
        
            .container{
                position: absolute;
                border: 0px solid black;
                left: 2px; 
                top: 100px;
                width: 250px;
            }
            
        </style>
        
        <script>
    
        </script>
        
    </head>
    <body>
        <svg id='svg_save_map_view' 
             xmlns:svg="http://www.w3.org/2000/svg"
             xmlns="http://www.w3.org/2000/svg"
             version="1.1"
             width="<%=svg_width%>"
             height="<%=svg_height%>">
            
        <rect x="0" y="0" width="<%=svg_width%>" height="<%=svg_height%>" style="fill:white;" />
        
        <g id="heatmap_header">
            <% double hy = sample_name_height; %>
            <% for(int i = 0; i < headers.length; i++) { %>
                <% 
                    double hx_line = (int)((i+1)*CELL_WIDTH);
                    double hx_text = (int)((i+0.5)*CELL_WIDTH); //(heatmap.CELL_WIDTH)/2.0 + j*heatmap.CELL_WIDTH - 5.0;
                %>
                <text x='<%=hx_text%>' y='<%=hy%>' font-family="Verdana" font-size="10" fill="black" transform="translate(4 -6) rotate(-45 <%=hx_text%> <%=hy%>)"> <%=headers[i]%> </text>
                <line x1='<%=hx_line%>' y1='<%=hy%>' x2='<%=hx_line+100%>' y2='<%=hy%>' style="stroke:rgb(100,100,155); stroke-width:1" transform="translate(0 -6) rotate(-45 <%=hx_line%> <%=hy%>)"/>
            <% } %>
        </g>
        
        <g id="heatmap_table" width='<%=TABLE_WIDTH%>' height='<%=TABLE_HEIGHT%>'>

            <% for (int i = 0; i < rgb.length; i++) {%>
            <g id="col_rect_<%=i%>">
                <% for (int j = 0; j < rgb[0].length; j++) { %>
                <%
                    String color_str = "rgb(" + (int) rgb[i][j][0] + "," + (int) rgb[i][j][1] + "," + (int) rgb[i][j][2] + ")";
                    String td_id = "td_" + i + "_" + j;

                    x = i * CELL_WIDTH;
                    y = j * CELL_HEIGHT + ((int)sample_name_height - 5);
                %>
                <rect x='<%=x%>' y='<%=y%>' id='<%=td_id%>' width = '<%=CELL_WIDTH%>' height = '<%=CELL_HEIGHT%>' style="fill:<%=color_str%>; " stroke="black" stroke-width="<%=BORDER_STROKE_WIDTH%>" /> 
                <% } %>
            </g>
            <% }%>

            
            <% if (show_search_tags.equalsIgnoreCase("yes")) {  %>
            <g id='search_g'>
        
            <%  for(int i = 0; i < search_results.size(); i++) {     %>
            
                <g  id="search_<%=i%>">
                <%        
                    double left_position = left_buffer + TABLE_WIDTH + i*column_width + (i-1)*gap;
                    String left = (int)left_position + "px";
                %>
            
                <rect x="<%=left%>" y="<%=(sample_name_height - 5.0)%>" height="<%=TABLE_HEIGHT%>px" width="<%=column_width%>px" style="fill: #EEEEEE; z-index: 0" />
            
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
                                
                                double top = (sample_name_height - 5.0) + feature_height*(positions.get(k) - start);
                                double feature_display_height = feature_height;
                                if (feature_height < 2.0) {
                                    feature_display_height = 2.0;
                                }
                %>
                                <rect x="<%=left%>" y="<%=top%>" height="<%=feature_display_height%>px" width="<%=column_width%>px" style="fill: #73AD21; z-index: 1" />
                <%
                            }
                        }
                    }
                }
                %>
                </g>
            <% } %>
            </g>
        <% } %>
            
        <g id='feature_labels_g'>
    
            <%
                BinaryTree linkage_tree = analysis.linkage_tree;

                for (int i=(int)start; i<=(int)end; i++) {
                    
                    int index = linkage_tree.leaf_ordering.get(i);
                    
                    String genes = database.features.get(index).getFormattedFeatureName(analysis);
                    
                    double mid = (feature_height*(i - start) + feature_height/2.0) + sample_name_height;
            %>
                
                    <text id="label_<%=i%>" x="<%=TABLE_WIDTH+search_result_width+10%>" y="<%=mid%>" font-family="Verdana" font-size="12" fill="black" style="display: inline;">
                        <%=genes%>
                    </text>
    
            <% } %>
    
        </g>
            
        
        </g>
        
        <% if (show_histogram.equalsIgnoreCase("yes")) { %>
        
            <jsp:include page="/WEB-INF/jspf/histogram_fragment.jspf" flush="true">
                <jsp:param name="analysis_name" value="<%=analysis_name%>" />
                <jsp:param name="y_offset" value="<%=(sample_name_height + TABLE_HEIGHT + 30.0)%>" />
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