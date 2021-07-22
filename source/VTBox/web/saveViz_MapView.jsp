<%-- 
    Document   : mapView
    Created on : 23 Apr, 2017, 1:35:27 PM
    Author     : Soumita
--%>
<%@page import="graphics.layouts.ScrollViewLayout"%>
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
    
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    double TABLE_WIDTH = layout.MAP_WIDTH;
    //double TABLE_HEIGHT = layout.MAP_HEIGHT;
    double BORDER_STROKE_WIDTH = layout.BORDER_STROKE_WIDTH;
    double CELL_WIDTH = layout.CELL_WIDTH;
    double CELL_HEIGHT = layout.CELL_HEIGHT;
    double HEADER_LABEL_FONTSIZE = layout.SAMPLE_LABEL_FONT_SIZE;
    double FEATURE_LABEL_FONTSIZE = layout.FEATURE_LABEL_FONT_SIZE;
    
    ///int TABLE_HEIGHT = 760;
    ///int CELL_HEIGHT = 20;
    // feature label params
    ///double feature_height = 20.0;       // ideally, should be same as CELL_HEIGHT
    ///double BORDER_STROKE_WIDTH = 0.5;
    // search results params
    
    
    int start = Integer.parseInt(request.getParameter("start"));
    int end = Integer.parseInt(request.getParameter("end"));
    String show_search_tags = request.getParameter("detailed_view_incl_search_tags");
    String show_histogram = request.getParameter("detailed_view_incl_hist");

    String imagename = heatmap.buildMapImage(start, end, "saveviz_mapview_jsp", HeatmapData.TYPE_ARRAY);
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    int NUM_CELLS = rgb[0].length;
    double TABLE_HEIGHT = layout.CELL_HEIGHT * NUM_CELLS;
    
    ///int MIN_TABLE_WIDTH = 500;
    ///int CELL_WIDTH = (int)Math.max(20.0, Math.floor(MIN_TABLE_WIDTH/rgb.length));
    ///int TABLE_WIDTH = (int)Math.max(MIN_TABLE_WIDTH, CELL_WIDTH*rgb.length);
    ///TABLE_HEIGHT = (int)CELL_HEIGHT*rgb[0].length;
    
    // used only in save viz, not in mapView.jsp
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
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
        feature_label_width = layout.getFeatureLabelWidth(analysis.visualizationType);
    }
    
    String sample_height = request.getParameter("sample_height").trim();
    double sample_name_height = 95.0;
    try {
        sample_name_height = Double.parseDouble(sample_height);
    } catch (NumberFormatException e) {
        sample_name_height = (int)ScrollViewLayout.COLUMN_HEADER_HEIGHT;
    }
    
    int histHeight = 200;
    int header_map_top_buffer = 10;
    double svg_height = sample_name_height + header_map_top_buffer + TABLE_HEIGHT + 30.0 + histHeight + 100.0; // 30 is the gap between heatmap and histogram; 100 is for the histogram x-axis labels
    double svg_width = TABLE_WIDTH + search_result_width + 10 + feature_label_width;
    
    HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;
    
    double x, y = 0;
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
                    double hx_text = (int)((i+0.5)*CELL_WIDTH) + HEADER_LABEL_FONTSIZE/2.0;
                %>
                
                <text x='<%=hx_text%>' y='<%=hy%>' font-family="Verdana" font-size="<%=HEADER_LABEL_FONTSIZE%>" fill="black" transform="rotate(-90 <%=hx_text%> <%=hy%>)"> <%=headers[i]%> </text>
                
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
                    y = j * CELL_HEIGHT + ((int)sample_name_height + header_map_top_buffer);
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
            
                <rect x="<%=left%>" y="<%=(sample_name_height+header_map_top_buffer)%>" height="<%=layout.SEARCH_TAG_DIV_HEIGHT%>px" width="<%=column_width%>px" style="fill: #EEEEEE; z-index: 0" />
            
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
                                
                                double top = (sample_name_height + header_map_top_buffer) + CELL_HEIGHT*(positions.get(k) - start);
                                double feature_display_height = CELL_HEIGHT;
                                if (CELL_HEIGHT < 2.0) {
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
                    double mid = sample_name_height + header_map_top_buffer + CELL_HEIGHT*(i-start) + CELL_HEIGHT/2.0 + FEATURE_LABEL_FONTSIZE/2.0;
            %>
                    
                    <text id="label_<%=i%>" x="<%=TABLE_WIDTH+search_result_width+10%>" y="<%=mid%>" font-family="Verdana" font-size="<%=FEATURE_LABEL_FONTSIZE%>" fill="black" style="display: inline;">
                        <%=genes%>
                    </text>
    
            <% } %>
    
        </g>
            
        
        </g>
        
        <% if (show_histogram.equalsIgnoreCase("yes")) { %>
        
            <jsp:include page="/WEB-INF/jspf/histogram_fragment.jspf" flush="true">
                <jsp:param name="analysis_name" value="<%=analysis_name%>" />
                <jsp:param name="y_offset" value="<%=(sample_name_height + header_map_top_buffer + TABLE_HEIGHT + 30.0)%>" />
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