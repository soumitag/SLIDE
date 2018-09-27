<%-- 
    Document   : mapView
    Created on : 23 Apr, 2017, 1:35:27 PM
    Author     : Soumita
--%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page import="graphics.layouts.ScrollViewLayout"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="graphics.Heatmap"%>
<%@page import="structure.Data"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

try {
    
    String analysis_name = request.getParameter("analysis_name");
    String imagename = request.getParameter("imagename");
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Data database = analysis.database;
    String[] headers = database.current_sample_names;
    
    Heatmap heatmap = analysis.heatmap;
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    
    ScrollViewLayout layout = analysis.visualization_params.detailed_view_map_layout;
    
    int TABLE_HEIGHT = (int)layout.MAP_HEIGHT;
    int CELL_HEIGHT = (int)layout.CELL_HEIGHT;
    double BORDER_STROKE_WIDTH = layout.BORDER_STROKE_WIDTH;
    int CELL_WIDTH = (int)layout.CELL_WIDTH;
    int TABLE_WIDTH = (int)layout.MAP_WIDTH;
    int HEADER_LABEL_SIZE = (int)layout.SAMPLE_LABEL_FONT_SIZE;
    int HEADER_HEIGHT = (int)ScrollViewLayout.COLUMN_HEADER_HEIGHT;

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
        
            .map_container{
                position: absolute;
                border: 0px solid black;
                left: 2px; 
                top: <%=HEADER_HEIGHT%>px;
                width: <%=TABLE_WIDTH%>px;
            }
            
            .header_container{
                position: absolute;
                border: 0px solid black;
                left: 2px; 
                top: 0px;
                width: <%=TABLE_WIDTH%>px;
            }
            
        </style>
        
        <script>
    
            function updateMap (data) {
                var container = document.getElementById('containerDiv');
                container.innerHTML = '' + data;
            }
            
            function selectGene(i, j, entrez, genesymbol) {
                alert('Entrez: ' + entrez + ', Genesymbol: ' + genesymbol);
            }
            
            function loadHistogram() {
                var iframe = opener.document.getElementById('histPanel');
                var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
                //alert(innerDoc);
                
                var hist_g = innerDoc.getElementById('histogram_g');
                //alert(hist_g);
                
                var chart = document.getElementById('save_img');
                
                var hist_g1 = cloneToDoc(hist_g);
                //alert(hist_g1);
                chart.appendChild(hist_g1);
                
                hist_g1.transform.baseVal.getItem(0).setTranslate(500,500);
                
                //alert('Done');
            }
            
            function cloneToDoc(node,doc){
                if (!doc) doc=document;
                var clone = doc.createElementNS(node.namespaceURI,node.nodeName);
                for (var i=0,len=node.attributes.length;i<len;++i){
                  var a = node.attributes[i];
                  if (/^xmlns\b/.test(a.nodeName)) continue; // IE won't set xmlns or xmlns:foo attributes
                  clone.setAttributeNS(a.namespaceURI,a.nodeName,a.nodeValue);
                }
                for (var i=0,len=node.childNodes.length;i<len;++i){
                  var c = node.childNodes[i];
                  clone.insertBefore(
                    c.nodeType==1 ? cloneToDoc(c,doc) : doc.createTextNode(c.nodeValue),
                    null
                  )
                }
                return clone;
            }
    
        </script>
        
    </head>
    <body style="overflow-y: hidden; margin: 0px">
        <div class="header_container" id='headerDiv' border="0">
        <svg id='svg_header' 
             xmlns:svg="http://www.w3.org/2000/svg"
             xmlns="http://www.w3.org/2000/svg"
             version="1.1"
             width="<%=TABLE_WIDTH%>"
             height="<%=HEADER_HEIGHT%>">
        
        <g id="heatmap_header">
            <% double hy = HEADER_HEIGHT-5; %>
            <% for(int i = 0; i < headers.length; i++) { %>
                <%
                    double hx_text = (int)((i+0.5)*CELL_WIDTH) + HEADER_LABEL_SIZE/2.0;
                %>
                <text x='<%=hx_text%>' y='<%=hy%>' font-family="Verdana" font-size="<%=HEADER_LABEL_SIZE%>" fill="black" transform="rotate(-90 <%=hx_text%> <%=hy%>)"> <%=headers[i]%> </text>
            <% } %>
        </g>
        </svg>
        </div>
        
        <div class="map_container" id='containerDiv' border="0">
        <svg id='svg_heat_map' 
             xmlns:svg="http://www.w3.org/2000/svg"
             xmlns="http://www.w3.org/2000/svg"
             version="1.1"
             width='<%=TABLE_WIDTH%>'
             height='<%=TABLE_HEIGHT%>'>
            
            <% 
                double x = 0;
                double y = 0;
            %>
            
            <g id="heatmap_table" width='<%=TABLE_WIDTH%>' height='<%=TABLE_HEIGHT%>'>
            
                <% for(int i = 0; i < rgb.length; i++) { %>
                    <g id="col_rect_<%=i%>">
                    <% for(int j = 0; j < rgb[0].length; j++) { %>
                    <%
                        String color_str = "rgb(" + (int)rgb[i][j][0] + "," + (int)rgb[i][j][1] +  "," + (int)rgb[i][j][2] + ")";
                        String td_id = "td_" + i + "_" + j;
                        
                        x = i*CELL_WIDTH;
                        y = j*CELL_HEIGHT;
                        
                        int original_row_id = analysis.linkage_tree.leaf_ordering.get(j);
                    %>
                        <rect x='<%=x%>' y='<%=y%>' id='<%=td_id%>' width = '<%=CELL_WIDTH%>' height = '<%=CELL_HEIGHT%>' style="fill:<%=color_str%>; " stroke="black" stroke-width="<%=BORDER_STROKE_WIDTH%>">
                            <title>
                                <% if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) { %>
                                Value: <%= analysis.database.datacells.dataval[original_row_id][i] %>
                                <% } else { %>
                                Value: <%= analysis.database.datacells.dataval[original_row_id][i] %>
                                <% } %>
                            </title>
                        </rect>
                    <% } %>
                    </g>
                <% } %>
        
            </g>
        </svg>
        </div>
        
    </body>
    
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>