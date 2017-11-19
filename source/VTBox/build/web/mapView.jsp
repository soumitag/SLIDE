<%-- 
    Document   : mapView
    Created on : 23 Apr, 2017, 1:35:27 PM
    Author     : Soumita
--%>
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
    
    int TABLE_HEIGHT = 760;
    int CELL_HEIGHT = 20;
    
    double BORDER_STROKE_WIDTH = 0.5;
    
    short[][][] rgb = heatmap.getRasterAsArray(imagename);
    
    int MIN_TABLE_WIDTH = 500;
    int CELL_WIDTH = (int)Math.max(20.0, Math.floor(MIN_TABLE_WIDTH/rgb.length));
    int TABLE_WIDTH = (int)Math.max(MIN_TABLE_WIDTH, CELL_WIDTH*rgb.length);
    
    String show_search_tags = "yes";     // default is always Yes; No is only used in print mode
    String show_histogram = "no";       // default is always No; Yes is only used in print mode
    
    String print_mode = request.getParameter("print_mode");
    if (print_mode == null) {
        print_mode = "no";
    } else if (print_mode.equalsIgnoreCase("yes")) {
        show_search_tags = request.getParameter("detailed_view_incl_search_tags");
        show_histogram = request.getParameter("detailed_view_incl_hist");
        //int start = Integer.parseInt(request.getParameter("print_start"));
        //int end = Integer.parseInt(request.getParameter("print_end"));
        //heatmap.buildMapImage(start, end);
        //rgb = heatmap.getRaster();
    }
    
    
    // used only in print mode
    double left_buffer = 10.0;
    double column_width = 20.0;
    double gap = 5.0;
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    double search_result_width = left_buffer + search_results.size()*column_width + (search_results.size()-1)*gap;
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
    
            function updateMap (data) {
                var container = document.getElementById('containerDiv');
                container.innerHTML = '' + data;
            }
            
            function selectGene(i, j, entrez, genesymbol) {
                alert('Entrez: ' + entrez + ', Genesymbol: ' + genesymbol);
            }
            
            function copyElementsForPrint() {
                
                // copy the feature labels
                var scrollPanel = opener.parent.document.getElementById('scrollPanel');
                var scrollView = scrollPanel.contentDocument || scrollPanel.contentWindow.document;
                
                var featureLabelsPanel = scrollView.getElementById('featureLabelsPanel');
                var featureLabels = featureLabelsPanel.contentDocument || featureLabelsPanel.contentWindow.document;
                
                var feature_labels_g = featureLabels.getElementById('feature_labels_g');
                var feature_labels_g_1 = cloneToDoc(feature_labels_g);
        
                <% if (show_search_tags.equals("yes")) { %>
                    // copy the search tags
                    var detailSearchPanel = scrollView.getElementById('detailSearchPanel');
                    var detailSearchView = detailSearchPanel.contentDocument || detailSearchPanel.contentWindow.document;

                    var search_g = detailSearchView.getElementById('search_g');
                    var search_g_1 = cloneToDoc(search_g);
                    //alert("1");
                    // transform and place elements
                    //feature_labels_g_1.transform.baseVal.getItem(0).setTranslate(<%=TABLE_WIDTH%>,0);
                    search_g_1.setAttribute('transform','translate(<%=TABLE_WIDTH%>,0)');
                <% } else {
                    search_result_width = 0;
                } %>
                
                <% if (show_histogram.equals("yes")) { %>
                    
                <% } %>
                
                feature_labels_g_1.setAttribute('transform','translate(<%=TABLE_WIDTH+search_result_width+3%>,0)');
                
                var chart = document.getElementById('svg_heat_map');
                chart.appendChild(feature_labels_g_1);
                chart.appendChild(search_g_1);
                
                //alert('Done');
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
    <body style="overflow-y: hidden">
        <svg id='svg_header' 
             xmlns:svg="http://www.w3.org/2000/svg"
             xmlns="http://www.w3.org/2000/svg"
             version="1.1"
             width="<%=TABLE_WIDTH+80%>"
             height="<%=TABLE_HEIGHT%>">
        
        <g id="heatmap_header">
            <% double hy = 95; %>
            <% for(int i = 0; i < headers.length; i++) { %>
                <% 
                    double hx_line = (int)((i+0.5)*CELL_WIDTH);
                    double hx_text = (int)(i*CELL_WIDTH); //(heatmap.CELL_WIDTH)/2.0 + j*heatmap.CELL_WIDTH - 5.0;
                %>
                <text x='<%=hx_text%>' y='<%=hy%>' font-family="Verdana" font-size="10" fill="black" transform="translate(4 -6) rotate(-45 <%=hx_text%> <%=hy%>)"> <%=headers[i]%> </text>
                <line x1='<%=hx_line%>' y1='<%=hy%>' x2='<%=hx_line+100%>' y2='<%=hy%>' style="stroke:rgb(100,100,155); stroke-width:1" transform="translate(0 -6) rotate(-45 <%=hx_line%> <%=hy%>)"/>
            <% } %>
        </g>
        
        </svg>
        <div class="container" id='containerDiv' border="0">
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
                                Raw Value: <%= analysis.database.raw_data[original_row_id][i] %>,
                                Transformed Value: <%= analysis.database.datacells.dataval[original_row_id][i] %>
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
    <script>
        <% if (print_mode.equals("yes"))  {    %>
            copyElementsForPrint();
        <% } %>
    </script>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>