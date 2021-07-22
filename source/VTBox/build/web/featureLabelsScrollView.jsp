<%-- 
    Document   : genelabels
    Created on : Apr 10, 2017, 2:09:33 PM
    Author     : soumita
--%>

<%@page import="graphics.layouts.ScrollViewLayout"%>
<%@page import="searcher.GeneObject"%>
<%@page import="structure.MetaData"%>
<%@page import="utils.Utils"%>
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
    
    String start_str = request.getParameter("start");
    double start;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    start = (start < 0) ? 0 : start;
    int current_start = analysis.state_variables.getDetailedViewStart();
    start = (start >= analysis.database.metadata.nFeatures) ? current_start : start;
    
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    int end = layout.getEnd((int)start, analysis.database.metadata.nFeatures);
    
    double feature_height = layout.CELL_HEIGHT;
    double image_width = layout.getFeatureLabelWidth(analysis.visualizationType);
    double image_height = layout.MAP_HEIGHT;
    double font_weight = layout.FEATURE_LABEL_FONT_SIZE;
 
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            
            var selected_features = [];
            function getSelectedFeatureIndices() {
                return selected_features;
            }
            
        </script>
    </head>
    <body>
        
        <svg height="<%=image_height%>" width="<%=image_width%>" style="position: absolute; top: 0px; left: 0px">
        
        <script type="text/ecmascript"><![CDATA[
            function toggleSelection(feature_index, feature_name, label_id) {
                var index = selected_features.indexOf(feature_index);
                var svg_txt_obj = document.getElementById(label_id);
                if (index > -1) {
                    selected_features.splice(index, 1);
                    svg_txt_obj.textContent = feature_name;
                    svg_txt_obj.style.fill = "black";
                    svg_txt_obj.style.fontWeight = "normal";
                } else {
                    selected_features.push(feature_index);
                    svg_txt_obj.textContent = "\u2713 " + feature_name;
                    svg_txt_obj.style.fill = "green";
                    svg_txt_obj.style.fontWeight = "bold";
                }
            }
            ]]>
        </script>
        
        
        <g id='feature_labels_g'>
    
    <%
            BinaryTree linkage_tree = analysis.linkage_tree;
            for (int i=(int)start; i<=(int)end; i++) {
                int index = linkage_tree.leaf_ordering.get(i);
                String genes = db.features.get(index).getFormattedFeatureName(analysis);
                double mid = feature_height*(i-start) + feature_height/2.0 + font_weight/2.0;
    %>
                <text id="label_<%=i%>" x="0" y="<%=mid%>" font-family="Verdana" font-size="<%=font_weight%>" fill="black" style="display: inline; " onclick="toggleSelection('<%=index%>', '<%=genes%>', 'label_<%=i%>')"><%=genes%></text>
    <%
            }
    %>
    
        </g>
            
        </svg>
        
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>