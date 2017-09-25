<%-- 
    Document   : getHeatmapData
    Created on : 23 Apr, 2017, 4:42:13 AM
    Author     : Soumita
--%>

<%@page import="graphics.HeatmapData"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="graphics.Heatmap"%>
<%@page import="structure.Data"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    int start = Integer.parseInt(request.getParameter("start"));
    
    String r = request.getParameter("rand");
    
    Heatmap heatmap = analysis.heatmap;
    
    int TABLE_HEIGHT = 760;
    int CELL_HEIGHT = 20;
    
    double BORDER_STROKE_WIDTH = 0.5;
    
    int end = start + 37;
    end = Math.min(end, analysis.database.features.size()-1);
    
    String imagename = heatmap.buildMapImage(start, end, "get_heatmap_data_jsp", HeatmapData.TYPE_ARRAY);
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    
    int MIN_TABLE_WIDTH = 500;
    int CELL_WIDTH = (int)Math.max(20.0, Math.floor(MIN_TABLE_WIDTH/rgb.length));
    int TABLE_WIDTH = (int)Math.max(MIN_TABLE_WIDTH, CELL_WIDTH*rgb.length);
    
    double x = 0;
    double y = 0;
    
    analysis.state_variables.put("detailed_view_start", (double)start);
    analysis.state_variables.put("detailed_view_end", (double)end);
    
    response.setHeader("Cache-Control", "max-age=0,no-cache,no-store,post-check=0,pre-check=0");
%>

<svg width='<%=TABLE_WIDTH%>' height='<%=TABLE_HEIGHT%>' id='svg_heat_map' 
             xmlns:svg="http://www.w3.org/2000/svg"
             xmlns="http://www.w3.org/2000/svg"
             version="1.1"
             width='<%=TABLE_WIDTH%>'
             height='<%=TABLE_HEIGHT%>'>

    <g id="heatmap_table" width='<%=TABLE_WIDTH%>' height='<%=TABLE_HEIGHT%>'>

    <% for (int i = 0; i < rgb.length; i++) {%>
        <g id="row_rect_<%=i%>">
            <% for (int j = 0; j < rgb[0].length; j++) { %>
            <%
                String color_str = "rgb(" + (int) rgb[i][j][0] + "," + (int) rgb[i][j][1] + "," + (int) rgb[i][j][2] + ")";
                String td_id = "td_" + i + "_" + j;

                x = i * CELL_WIDTH;
                y = j * CELL_HEIGHT;
            %>
                <rect x='<%=x%>' y='<%=y%>' id='<%=td_id%>' width = '<%=CELL_WIDTH%>' height = '<%=CELL_HEIGHT%>' style="fill:<%=color_str%>; " stroke="black" stroke-width="<%=BORDER_STROKE_WIDTH%>" /> 
            <% } %>
        </g>
    <% }%>

    </g>
    
</svg>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>