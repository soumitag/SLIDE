package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.HeatmapData;
import vtbox.SessionUtils;
import java.util.Arrays;
import utils.SessionManager;
import structure.AnalysisContainer;
import structure.Data;
import graphics.Heatmap;

public final class detailedHeatMap_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

    
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

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js\"></script>\n");
      out.write("        <style>\n");
      out.write("        \n");
      out.write("            ");
  if(TYPE.equals("global_map"))   {     
      out.write("\n");
      out.write("            \n");
      out.write("                .containerI{\n");
      out.write("                    position: absolute;\n");
      out.write("                    border: 0px solid black;\n");
      out.write("                    left: 20px; \n");
      out.write("                    top: 2px;\n");
      out.write("                    height: 750px;\n");
      out.write("                    width: 250px;\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                .containerD{\n");
      out.write("                    position: absolute;\n");
      out.write("                    border: 0px solid black;\n");
      out.write("                    left: 20px; \n");
      out.write("                    top: 2px;\n");
      out.write("                    height: 750px;\n");
      out.write("                    width: 270px;\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("            ");
  } else  {     
      out.write("\n");
      out.write("            \n");
      out.write("                .containerI{\n");
      out.write("                    position: absolute;\n");
      out.write("                    border: 0px solid black;\n");
      out.write("                    left: 2px; \n");
      out.write("                    top: 20px;\n");
      out.write("                    height: 750px;\n");
      out.write("                    width: 250px;\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                .containerD{\n");
      out.write("                    position: absolute;\n");
      out.write("                    border: 0px solid black;\n");
      out.write("                    left: 2px; \n");
      out.write("                    top: 0px;\n");
      out.write("                    height: 780px;\n");
      out.write("                    width: 270px;\n");
      out.write("                }\n");
      out.write("            \n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            .slider {\n");
      out.write("                position: absolute; \n");
      out.write("                left: -20px; \n");
      out.write("                top: 0px; \n");
      out.write("                width: 15px; \n");
      out.write("                height: ");
      out.print(scrollbar_height);
      out.write("px;\n");
      out.write("                color: red;\n");
      out.write("                border: 1px solid black;\n");
      out.write("                cursor: all-scroll;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .slider_marker {\n");
      out.write("                position: absolute; \n");
      out.write("                left: 0px; \n");
      out.write("                top: 0px; \n");
      out.write("                width: 250px; \n");
      out.write("                height: ");
      out.print(scrollbox_height);
      out.write("px;\n");
      out.write("                color: red;\n");
      out.write("                border: 1px solid black;\n");
      out.write("                cursor: all-scroll;\n");
      out.write("                z-index: 1;\n");
      out.write("                background-color: rgba(255, 255, 255, 0.4); /* Color white with alpha 0.9*/\n");
      out.write("                display: none;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            var num_rows = ");
      out.print(num_features);
      out.write(";\n");
      out.write("            \n");
      out.write("            ");
      if(TYPE.equals("global_map"))   {   
      out.write("\n");
      out.write("            var heatmap_top_buffer = 0;\n");
      out.write("            ");
      }   else   {   
      out.write("\n");
      out.write("            var heatmap_top_buffer = 20;\n");
      out.write("            ");
      }   
      out.write("\n");
      out.write("            \n");
      out.write("            $(function() {\n");
      out.write("                $('.slider').slider();\n");
      out.write("            });\n");
      out.write("\n");
      out.write("            $.fn.slider = function() {\n");
      out.write("                return this.each(function() {\n");
      out.write("                    var $el = $(this);\n");
      out.write("                    var $el_box = $('.slider_marker');\n");
      out.write("                    $el.css('top', 0);\n");
      out.write("                    var dragging = false;\n");
      out.write("                    var startY = 0;\n");
      out.write("                    var startT = 0;\n");
      out.write("                    $el.mousedown(function(ev) {\n");
      out.write("                        dragging = true;\n");
      out.write("                        startY = ev.clientY;\n");
      out.write("                        startT = $el.css('top');\n");
      out.write("                    });\n");
      out.write("                    $(window).mousemove(function(ev) {\n");
      out.write("                        if (dragging) {\n");
      out.write("                            // calculate new top\n");
      out.write("                            var newTop = parseInt(startT) + (ev.clientY - startY);\n");
      out.write("\n");
      out.write("                            //stay in parent\n");
      out.write("                            var maxTop =  $el.parent().height()-$el_box.height();\n");
      out.write("                            newTop = newTop<0?0:newTop>maxTop?maxTop:newTop;\n");
      out.write("                            $el.css('top', newTop );\n");
      out.write("                            $el_box.css('top', newTop );\n");
      out.write("                        }\n");
      out.write("                    }).mouseup(function() {\n");
      out.write("                        dragging = false;\n");
      out.write("                        updateMap();\n");
      out.write("                    });\n");
      out.write("                });\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function updateMap() {\n");
      out.write("                \n");
      out.write("                //var marker_height = document.getElementById('scrollerDiv').style.height;\n");
      out.write("                var marker_top = document.getElementById('scrollerDiv').style.top;\n");
      out.write("                var imgH = 750.0;\n");
      out.write("                //var marker_height_num = parseFloat(marker_height.substring(0,marker_height.length-2));\n");
      out.write("                var marker_top_num = parseFloat(marker_top.substring(0,marker_top.length-2));\n");
      out.write("                var start = Math.round( (marker_top_num/imgH)*num_rows );\n");
      out.write("                //var end = Math.floor( ((marker_top_num+marker_height_num)/imgH)*num_rows );\n");
      out.write("                                \n");
      out.write("                parent.updateMap(start, start+49);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showScrollerRect() {\n");
      out.write("                document.getElementById('scrollerBox').style.display = 'inline';\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideScrollerRect() {\n");
      out.write("                document.getElementById('scrollerBox').style.display = 'none';\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showRect(start, end) {\n");
      out.write("                //alert(\"Made it\");\n");
      out.write("                //alert(start);\n");
      out.write("                //alert(end);\n");
      out.write("                //var y = (start - MAP_START)*SCALING_FACTOR;\n");
      out.write("                //var h = (((end-start)+1)-MAP_START)*SCALING_FACTOR;\n");
      out.write("                //alert(y);\n");
      out.write("                //alert(h);\n");
      out.write("                \n");
      out.write("                var svgns = \"http://www.w3.org/2000/svg\";\n");
      out.write("                var rect = document.createElementNS(svgns, 'rect');\n");
      out.write("                rect.setAttributeNS(null, 'x', 0);\n");
      out.write("                rect.setAttributeNS(null, 'y', heatmap_top_buffer + (start - MAP_START)*SCALING_FACTOR);\n");
      out.write("                rect.setAttributeNS(null, 'height', ((end-start)+1)*SCALING_FACTOR);\n");
      out.write("                rect.setAttributeNS(null, 'width', '250');\n");
      out.write("                rect.setAttributeNS(null, 'fill', 'none');\n");
      out.write("                rect.setAttributeNS(null, 'id', 'highlight_rect');\n");
      out.write("                rect.setAttributeNS(null, 'style', 'stroke-width:3;stroke:rgb(0,0,0)');\n");
      out.write("                document.getElementById('heatmap_svg').appendChild(rect);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideRect() {\n");
      out.write("                svg = document.getElementById('heatmap_svg');\n");
      out.write("                rect = document.getElementById('highlight_rect');\n");
      out.write("                if (rect != null) {\n");
      out.write("                    svg.removeChild(rect);\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function showColHeader(h, colname){\n");
      out.write("                //alert(\"1\");\n");
      out.write("                var x = document.getElementById(\"colHeader_\" + h);\n");
      out.write("                x.setAttribute(\"style\", \"display: inline; stroke-width:1; stroke:rgb(0,0,0)\");\n");
      out.write("                parent.showColHeader(colname);\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function hideColHeader(h){\n");
      out.write("                var x = document.getElementById(\"colHeader_\" + h);\n");
      out.write("                x.setAttribute(\"style\", \"display: none;  stroke-width:0\");\n");
      out.write("                parent.hideColHeader();\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("            \n");
      out.write("        \n");
      out.write("    </head>\n");
      out.write("    <body style=\"overflow: hidden\">\n");
      out.write("\n");
      out.write("            <div class=\"containerD\" id='containerDiv' border=\"0\">\n");
      out.write("                \n");
      out.write("                ");
      if(TYPE.equals("global_map"))   {   
      out.write("\n");
      out.write("                \n");
      out.write("                ");
  if (show_scrollbar) {   
      out.write("\n");
      out.write("                <div class=\"slider\" id=\"scrollerDiv\" onmouseover=\"showScrollerRect()\" onmouseout=\"hideScrollerRect()\"></div>\n");
      out.write("                <div class=\"slider_marker\" id=\"scrollerBox\"></div>\n");
      out.write("                ");
  }   
      out.write("\n");
      out.write("                \n");
      out.write("                <svg width=\"250\" height=\"750\" id=\"heatmap_svg\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink= \"http://www.w3.org/1999/xlink\" style=\"position: absolute; top: 0px; left: 0px\">\n");
      out.write("                <g id=\"heatmap_g\">\n");
      out.write("                    <image class=\"containerI\" id='heatImg' xlink:href=\"");
      out.print(base_url);
      out.write("HeatmapImageServer?analysis_name=");
      out.print(analysis_name);
      out.write("&imagename=");
      out.print(imagename);
      out.write("\" x=\"0\" y=\"0\" height=\"750px\" width=\"250px\" />\n");
      out.write("                    <rect x=\"0\" y=\"0\" height=\"750px\" width=\"250px\" style=\"fill: none; stroke: black; stroke-width: 1\" /> \n");
      out.write("                </g>\n");
      out.write("               \n");
      out.write("                ");
      } else if (TYPE.equals("dendrogram_map"))   {   
      out.write("\n");
      out.write("                \n");
      out.write("                <svg  width=\"250\" height=\"770\" id=\"heatmap_svg\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink= \"http://www.w3.org/1999/xlink\" style=\"position: absolute; top: 0px; left: 0px\">\n");
      out.write("                <g id=\"heatmap_g\">\n");
      out.write("                    <image class=\"containerI\" id='heatImg' xlink:href=\"");
      out.print(base_url);
      out.write("HeatmapImageServer?analysis_name=");
      out.print(analysis_name);
      out.write("&imagename=");
      out.print(imagename);
      out.write("\" x=\"0\" y=\"20\" height=\"750px\" width=\"250px\" />\n");
      out.write("                    <rect x=\"0\" y=\"20\" height=\"750px\" width=\"250px\" style=\"fill: none; stroke: black; stroke-width: 1\" /> \n");
      out.write("                </g>\n");
      out.write("                \n");
      out.write("                <g>\n");
      out.write("                    ");
 for(int h = 0; h < headers.length; h++){ 
      out.write("\n");
      out.write("                    <rect x=\"");
      out.print(h*colWidth);
      out.write("\" y=\"20\" width=\"");
      out.print(colWidth);
      out.write("\" height=\"");
      out.print(imgHeight);
      out.write("\" style=\"fill:rgb(0,0,0); opacity: 0.0; stroke-width:1; stroke:rgb(0,0,0)\" onmouseover=\"showColHeader(");
      out.print(h);
      out.write(',');
      out.write('\'');
      out.print(headers[h]);
      out.write("')\" onmouseout=\"hideColHeader(");
      out.print(h);
      out.write(")\" />\n");
      out.write("                    <line id=\"colHeader_");
      out.print(h);
      out.write("\" x1=\"");
      out.print((h+0.5)*colWidth);
      out.write("\" y1=\"0\" x2=\"");
      out.print((h+0.5)*colWidth);
      out.write("\" y2=\"20\" style=\"display: none; stroke-width:1; stroke:rgb(0,0,0)\" />\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </g>\n");
      out.write("                \n");
      out.write("                ");
      }       
      out.write("\n");
      out.write("                \n");
      out.write("                </svg>\n");
      out.write("                \n");
      out.write("            </div>\n");
      out.write("                    \n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("        <script>\n");
      out.write("            ");
 double scaling_factor = (imgHeight*1.0)/(((end-start)+1)*1.0);  
      out.write("\n");
      out.write("            var SCALING_FACTOR = ");
      out.print(scaling_factor);
      out.write(";\n");
      out.write("            var MAP_START = ");
      out.print(start);
      out.write(";\n");
      out.write("        </script>\n");
      out.write("    \n");
      out.write("</html>\n");
      out.write("\n");

  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}


      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
