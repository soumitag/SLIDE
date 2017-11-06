<%-- 
    Document   : detailedHeatMap
    Created on : 26 Mar, 2017, 7:30:37 PM
    Author     : Soumita
--%>

<%@page import="graphics.HeatmapData"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.Arrays"%>
<%@page import="utils.SessionManager"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="structure.Data"%>
<%@page import="graphics.Heatmap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    String base_url = (String)session.getAttribute("base_url");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Data db = analysis.database;
    int num_features = db.features.size();
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    String TYPE = request.getParameter("type");
    if(TYPE == null) {
        TYPE = "dendrogram_map";
    }
    //TYPE = "global_map";
    //TYPE = "dendrogram_map";
    
    int start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        end = num_features - 1;
    }
    
    Heatmap heatmap = analysis.heatmap;
    int imgHeight = 750;
    String imagename = heatmap.buildMapImage(
            start, end, 250, imgHeight, "detailed_heatmap_jsp", HeatmapData.TYPE_IMAGE);
    //String imagewebpath = session.getAttribute("base_url") + "/temp/images/" + imagename;
    //String imagewebpath = "http://localhost:8080/VTBox/images/" + imagename;
    
    double scrollbox_height = (38.0/num_features)*(imgHeight*1.0);
    scrollbox_height = Math.min(scrollbox_height, imgHeight);
    
    double scrollbar_height = Math.max(scrollbox_height, 5.0);
    
    boolean show_scrollbar = true;
    if (num_features<=38.0) {
        show_scrollbar = false;
    }
    
    Data database = analysis.database;
    String[] headers = database.current_sample_names;
    double colWidth = 250.0/headers.length;
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <style>
        
            <%  if(TYPE.equals("global_map"))   {     %>
            
                .containerI{
                    position: absolute;
                    border: 0px solid black;
                    left: 20px; 
                    top: 2px;
                    height: 750px;
                    width: 250px;
                }

                .containerD{
                    position: absolute;
                    border: 0px solid black;
                    left: 20px; 
                    top: 2px;
                    height: 750px;
                    width: 270px;
                }
                
            <%  } else  {     %>
            
                .containerI{
                    position: absolute;
                    border: 0px solid black;
                    left: 2px; 
                    top: 20px;
                    height: 750px;
                    width: 250px;
                }

                .containerD{
                    position: absolute;
                    border: 0px solid black;
                    left: 2px; 
                    top: 0px;
                    height: 780px;
                    width: 270px;
                }
            
            <%  }   %>
            
            .slider {
                position: absolute; 
                left: -20px; 
                top: 0px; 
                width: 15px; 
                height: <%=scrollbar_height%>px;
                color: red;
                border: 1px solid black;
                cursor: all-scroll;
            }
            
            .slider_marker {
                position: absolute; 
                left: 0px; 
                top: 0px; 
                width: 250px; 
                height: <%=scrollbox_height%>px;
                color: red;
                border: 1px solid black;
                cursor: all-scroll;
                z-index: 1;
                background-color: rgba(255, 255, 255, 0.4); /* Color white with alpha 0.9*/
                display: none;
            }
            
        </style>
        
        <script>
            
            var num_rows = <%=num_features%>;
            
            <%      if(TYPE.equals("global_map"))   {   %>
            var heatmap_top_buffer = 0;
            <%      }   else   {   %>
            var heatmap_top_buffer = 20;
            <%      }   %>
            
            $(function() {
                $('.slider').slider();
            });

            $.fn.slider = function() {
                return this.each(function() {
                    var $el = $(this);
                    var $el_box = $('.slider_marker');
                    $el.css('top', 0);
                    var dragging = false;
                    var startY = 0;
                    var startT = 0;
                    $el.mousedown(function(ev) {
                        dragging = true;
                        startY = ev.clientY;
                        startT = $el.css('top');
                    });
                    $(window).mousemove(function(ev) {
                        if (dragging) {
                            // calculate new top
                            var newTop = parseInt(startT) + (ev.clientY - startY);

                            //stay in parent
                            var maxTop =  $el.parent().height()-$el_box.height();
                            newTop = newTop<0?0:newTop>maxTop?maxTop:newTop;
                            $el.css('top', newTop );
                            $el_box.css('top', newTop );
                        }
                    }).mouseup(function() {
                        dragging = false;
                        updateMap();
                    });
                });
            }
            
            function updateMap() {
                
                //var marker_height = document.getElementById('scrollerDiv').style.height;
                var marker_top = document.getElementById('scrollerDiv').style.top;
                var imgH = 750.0;
                //var marker_height_num = parseFloat(marker_height.substring(0,marker_height.length-2));
                var marker_top_num = parseFloat(marker_top.substring(0,marker_top.length-2));
                var start = Math.round( (marker_top_num/imgH)*num_rows );
                //var end = Math.floor( ((marker_top_num+marker_height_num)/imgH)*num_rows );
                                
                parent.updateMap(start, start+49);
            }
            
            function showScrollerRect() {
                document.getElementById('scrollerBox').style.display = 'inline';
            }
            
            function hideScrollerRect() {
                document.getElementById('scrollerBox').style.display = 'none';
            }
            
            function showRect(start, end) {
                //alert("Made it");
                //alert(start);
                //alert(end);
                //var y = (start - MAP_START)*SCALING_FACTOR;
                //var h = (((end-start)+1)-MAP_START)*SCALING_FACTOR;
                //alert(y);
                //alert(h);
                
                var svgns = "http://www.w3.org/2000/svg";
                var rect = document.createElementNS(svgns, 'rect');
                rect.setAttributeNS(null, 'x', 0);
                rect.setAttributeNS(null, 'y', heatmap_top_buffer + (start - MAP_START)*SCALING_FACTOR);
                rect.setAttributeNS(null, 'height', ((end-start)+1)*SCALING_FACTOR);
                rect.setAttributeNS(null, 'width', '250');
                rect.setAttributeNS(null, 'fill', 'none');
                rect.setAttributeNS(null, 'id', 'highlight_rect');
                rect.setAttributeNS(null, 'style', 'stroke-width:3;stroke:rgb(0,0,0)');
                document.getElementById('heatmap_svg').appendChild(rect);
            }
            
            function hideRect() {
                svg = document.getElementById('heatmap_svg');
                rect = document.getElementById('highlight_rect');
                if (rect != null) {
                    svg.removeChild(rect);
                }
            }
            
            function showColHeader(h, colname){
                //alert("1");
                var x = document.getElementById("colHeader_" + h);
                x.setAttribute("style", "display: inline; stroke-width:1; stroke:rgb(0,0,0)");
                parent.showColHeader(colname);
            }
            
            function hideColHeader(h){
                var x = document.getElementById("colHeader_" + h);
                x.setAttribute("style", "display: none;  stroke-width:0");
                parent.hideColHeader();
            }
            
        </script>
            
        
    </head>
    <body style="overflow: hidden">

            <div class="containerD" id='containerDiv' border="0">
                
                <%      if(TYPE.equals("global_map"))   {   %>
                
                <%  if (show_scrollbar) {   %>
                <div class="slider" id="scrollerDiv" onmouseover="showScrollerRect()" onmouseout="hideScrollerRect()"></div>
                <div class="slider_marker" id="scrollerBox"></div>
                <%  }   %>
                
                <svg width="250" height="750" id="heatmap_svg" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink= "http://www.w3.org/1999/xlink" style="position: absolute; top: 0px; left: 0px">
                <g id="heatmap_g">
                    <image class="containerI" id='heatImg' xlink:href="<%=base_url%>HeatmapImageServer?analysis_name=<%=analysis_name%>&imagename=<%=imagename%>" x="0" y="0" height="750px" width="250px" />
                    <rect x="0" y="0" height="750px" width="250px" style="fill: none; stroke: black; stroke-width: 1" /> 
                </g>
               
                <%      } else if (TYPE.equals("dendrogram_map"))   {   %>
                
                <svg  width="250" height="770" id="heatmap_svg" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink= "http://www.w3.org/1999/xlink" style="position: absolute; top: 0px; left: 0px">
                <g id="heatmap_g">
                    <image class="containerI" id='heatImg' xlink:href="<%=base_url%>HeatmapImageServer?analysis_name=<%=analysis_name%>&imagename=<%=imagename%>" x="0" y="20" height="750px" width="250px" />
                    <rect x="0" y="20" height="750px" width="250px" style="fill: none; stroke: black; stroke-width: 1" /> 
                </g>
                
                <g>
                    <% for(int h = 0; h < headers.length; h++){ %>
                    <rect x="<%=h*colWidth%>" y="20" width="<%=colWidth%>" height="<%=imgHeight%>" style="fill:rgb(0,0,0); opacity: 0.0; stroke-width:1; stroke:rgb(0,0,0)" onmouseover="showColHeader(<%=h%>,'<%=headers[h]%>')" onmouseout="hideColHeader(<%=h%>)" />
                    <line id="colHeader_<%=h%>" x1="<%=(h+0.5)*colWidth%>" y1="0" x2="<%=(h+0.5)*colWidth%>" y2="20" style="display: none; stroke-width:1; stroke:rgb(0,0,0)" />
                    <% } %>
                </g>
                
                <%      }       %>
                
                </svg>
                
            </div>
                    
        
    </body>
    
        <script>
            <% double scaling_factor = (imgHeight*1.0)/(((end-start)+1)*1.0);  %>
            var SCALING_FACTOR = <%=scaling_factor%>;
            var MAP_START = <%=start%>;
        </script>
    
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>
