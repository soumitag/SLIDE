package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.CompactSearchResultContainer;
import java.util.ArrayList;
import structure.AnalysisContainer;
import algorithms.clustering.BinaryTree;

public final class visualizationHome_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    String visualization_level = request.getParameter("visualization_level");
    
    boolean do_clustering = Boolean.parseBoolean(analysis.clustering_params.get("do_clustering"));
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;

    String hdiFrame_width = "285";
    if (search_results.size() == 0) {
        hdiFrame_width = "285";
    } else {
        hdiFrame_width = 285 + (search_results.size()*32) + "";
    }
    
    String scrollPanel_width = "730";
    if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) {
        if (search_results.size() == 0) {
            scrollPanel_width = "730";
        } else {
            scrollPanel_width = 730 + (search_results.size()*32) + "";
        }
    }  else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) {
        scrollPanel_width = "880";
    }
    
    String searchKeysFrame_width = "250";
    //if (search_results.size() > 0) {
    //    searchKeysFrame_width = "250";
    //}

      out.write("\n");
      out.write("<html>\n");
      out.write("    <script type = \"text/javascript\" language = \"JavaScript\" src=\"main.js\"></script>\n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("    <script>\n");
      out.write("        \n");
      out.write("        function toggleSelectionPanel() {\n");
      out.write("            var sp = parent.document.getElementById(\"selectionPanel\");\n");
      out.write("            var sph = document.getElementById(\"spHandle\");\n");
      out.write("            if (sp.style.display === 'inline') {\n");
      out.write("                sp.style.display = 'none';\n");
      out.write("                sph.innerHTML = 'Show Control Panel';\n");
      out.write("            } else {\n");
      out.write("                sp.style.display = 'inline';\n");
      out.write("                sph.innerHTML = 'Hide Control Panel';\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("         \n");
      out.write("        function updateMap(start, end) {\n");
      out.write("            document.getElementById(\"scrollPanel\").contentWindow.loadMap(start, end);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function toggleHistogramPanel() {\n");
      out.write("            var hh = document.getElementById(\"histHandle\");\n");
      out.write("            var hp = document.getElementById(\"histPanel\");\n");
      out.write("            var gif = document.getElementById(\"geneInfoFrame\");\n");
      out.write("            if (hp.style.display === 'inline') {\n");
      out.write("                hp.style.display = 'none';\n");
      out.write("                gif.height = '100%';\n");
      out.write("                hh.innerHTML = 'Show Histogram';\n");
      out.write("            } else {\n");
      out.write("                hp.style.display = 'inline';\n");
      out.write("                gif.height = '66%';\n");
      out.write("                hh.innerHTML = 'Hide Histogram';\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function saveAnalysis() {\n");
      out.write("            \n");
      out.write("            showModalWindow('downloadInfo.jsp?mode=start&analysis_name=");
      out.print(analysis_name);
      out.write("', '40%', '200px')\n");
      out.write("            \n");
      out.write("            var url = \"");
      out.print(base_url);
      out.write("SerializeAnalysisServlet?analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("            document.getElementById(\"invisible_Download_Frame\").contentWindow.location.replace(url);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function saveDetailedView() {\n");
      out.write("            var scrollPanel = document.getElementById('scrollPanel');\n");
      out.write("            var scrollView = scrollPanel.contentDocument || scrollPanel.contentWindow.document;\n");
      out.write("            var mapViewPanel = scrollView.getElementById('mapViewPanel');\n");
      out.write("            var mapview_url = mapViewPanel.contentWindow.location.href + \"&print_mode=yes\";\n");
      out.write("            var popup = window.open(mapview_url);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function downloadCompletion(status, filename) {\n");
      out.write("            var str = \"&nbsp;&nbsp;&nbsp;<span id='ribbon_close' class='close' onclick='closeRibbon()'>&times;</span>\";\n");
      out.write("            if (status === \"\") {\n");
      out.write("                document.getElementById(\"msg_ribbon_div\").innerHTML = \n");
      out.write("                        \"<i>\" + filename + \".slide </i> file has been downloaded.\" + str;\n");
      out.write("            } else {\n");
      out.write("                document.getElementById(\"msg_ribbon_div\").innerHTML = \n");
      out.write("                        \"Oops! Looks like something went wrong. Please try again.\" + str;\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function closeRibbon() {\n");
      out.write("            document.getElementById(\"msg_ribbon_div\").style.display = \"none\";\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function showGlobal() {\n");
      out.write("            var msg_iframe = document.getElementById(\"hdiFrameLoadingMsg\");\n");
      out.write("            var global_iframe = document.getElementById(\"hdiFrame\");\n");
      out.write("            msg_iframe.height = \"0px\";\n");
      out.write("            msg_iframe.width = \"0px\";\n");
      out.write("            msg_iframe.style.visibility = \"hidden\";\n");
      out.write("            global_iframe.style.visibility = \"visible\";\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </script>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <style>\n");
      out.write("\n");
      out.write("            .maintable {\n");
      out.write("                position: absolute; \n");
      out.write("                top: 0px; \n");
      out.write("                left: 0px; \n");
      out.write("                overflow-x: scroll; \n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            td {\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-width: 1px; \n");
      out.write("                border-color: #ededed; \n");
      out.write("                border-style: solid;\n");
      out.write("                background-color: #fcfcfc;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* The Modal (background) */\n");
      out.write("            .modal {\n");
      out.write("                display: none; /* Hidden by default */\n");
      out.write("                position: fixed; /* Stay in place */\n");
      out.write("                z-index: 1; /* Sit on top */\n");
      out.write("                padding-top: 100px; /* Location of the box */\n");
      out.write("                left: 0;\n");
      out.write("                top: 0;\n");
      out.write("                width: 100%; /* Full width */\n");
      out.write("                height: 100%; /* Full height */\n");
      out.write("                overflow: auto; /* Enable scroll if needed */\n");
      out.write("                background-color: rgb(0,0,0); /* Fallback color */\n");
      out.write("                background-color: rgba(0,0,0,0.4); /* Black w/ opacity */\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* Modal Content */\n");
      out.write("            .modal-content {\n");
      out.write("                background-color: #fefefe;\n");
      out.write("                margin: auto;\n");
      out.write("                padding: 20px;\n");
      out.write("                border: 1px solid #888;\n");
      out.write("                width: 80%;\n");
      out.write("                height: 500px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* The Close Button */\n");
      out.write("            .close {\n");
      out.write("                color: #aaaaaa;\n");
      out.write("                float: right;\n");
      out.write("                font-size: 28px;\n");
      out.write("                font-weight: bold;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            .close:hover,\n");
      out.write("            .close:focus {\n");
      out.write("                color: #000;\n");
      out.write("                text-decoration: none;\n");
      out.write("                cursor: pointer;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        <!-- The Modal -->\n");
      out.write("        <div id=\"myModal\" class=\"modal\">\n");
      out.write("\n");
      out.write("            <!-- Modal content -->\n");
      out.write("            <div id=\"modal_inside\" class=\"modal-content\">\n");
      out.write("                <span id=\"modal_close\" class=\"close\">&times;</span>\n");
      out.write("                <iframe name=\"modalFrame\" id=\"modalFrame\" src=\"\" marginwidth=\"0\" height=\"500\" width=\"100%\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        <div class=\"msg_ribbon\" id=\"msg_ribbon_div\">\n");
      out.write("            Testing\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        <table class=\"maintable\" height=\"99%\" width=\"1910px\" cellspacing=\"0\" cellpadding=\"0\"> \n");
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"5\" name=\"ribbonPanel\" id=\"ribbonPanel\" height=\"5%\">\n");
      out.write("\n");
      out.write("                    <table>\n");
      out.write("                        <tr>\n");
      out.write("                            <td valign=\"top\" style=\"min-height: 40px;\">\n");
      out.write("                                <iframe name=\"searchFrame\" id=\"searchFrame\" src=\"searcher.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" marginwidth=\"0\" height=\"29\" width=\"500\" frameBorder=\"0\" style=\"min-height: 35px;\"></iframe>\n");
      out.write("                            </td>\n");
      out.write("                            \n");
      out.write("                            <td>\n");
      out.write("                                &nbsp;\n");
      out.write("                                <button name=\"spHandle\" class=\"dropbtn\" id=\"spHandle\" onclick=\"toggleSelectionPanel()\"> Hide Control Panel </button> &nbsp;\n");
      out.write("                                <button name=\"saveAnalysis\" class=\"dropbtn\" id=\"saveAnalysis\" onclick=\"saveAnalysis()\"> Save Analysis </button> &nbsp;\n");
      out.write("                                <button name=\"saveViz\" class=\"dropbtn\" id=\"saveViz\" onclick=\"showModalWindow('saveSVGs.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("', '60%', '450px')\">Save Visualizations</button> &nbsp;\n");
      out.write("                                <div class=\"dropdown\">\n");
      out.write("                                    <button name=\"featureList\" class=\"dropbtn\" id=\"createList\"> Feature List </button> &nbsp;\n");
      out.write("                                    <div class=\"dropdown-content\">\n");
      out.write("                                        <a onclick=\"showModalWindow('createFeatureList.jsp?mode=input&analysis_name=");
      out.print(analysis_name);
      out.write("', '40%', '250px')\" href=\"#\"> Create </a>\n");
      out.write("                                        <a onclick=\"showModalWindow('saveFeatureList.jsp?mode=input&analysis_name=");
      out.print(analysis_name);
      out.write("', '40%', '250px')\" href=\"#\"> Save </a>\n");
      out.write("                                        <a onclick=\"showModalWindow('viewDeleteFeatureList.jsp?mode=input&analysis_name=");
      out.print(analysis_name);
      out.write("', '40%', '550px')\" href=\"#\"> View / Delete </a>\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                <div class=\"dropdown\">\n");
      out.write("                                    <button name=\"createSubAnal\" class=\"dropbtn\" id=\"createSubAnal\"> Sub-Analysis </button> &nbsp;\n");
      out.write("                                    <div class=\"dropdown-content\">\n");
      out.write("                                        <a onclick=\"showModalWindow('createSubAnalysis.jsp?mode=input&analysis_name=");
      out.print(analysis_name);
      out.write("', '60%', '500px')\" href=\"#\"> Create </a>\n");
      out.write("                                        <a onclick=\"showModalWindow('openSubAnalysis.jsp?mode=input&analysis_name=");
      out.print(analysis_name);
      out.write("', '60%', '500px')\" href=\"#\"> Open </a>\n");
      out.write("                                    </div>\n");
      out.write("                                </div>\n");
      out.write("                                ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("                                <button name=\"createFuncEnrichment\" class=\"dropbtn\" id=\"createFuncEnrichment\" onclick=\"showModalWindow('createEnrichmentAnalysis.jsp?mode=input&analysis_name=");
      out.print(analysis_name);
      out.write("', '60%', '550px')\"> Functional Enrichment </button> &nbsp;\n");
      out.write("                                ");
  }   
      out.write("\n");
      out.write("                                <button name=\"histHandle\" class=\"dropbtn\" id=\"histHandle\" onclick=\"toggleHistogramPanel()\"> Hide Histogram </button>\n");
      out.write("                            </td>\n");
      out.write("                            \n");
      out.write("                        </tr>\n");
      out.write("                    </table>\n");
      out.write("\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                \n");
      out.write("                <td rowspan=\"2\" align=\"top\" valign=\"top\" width=\"300\">\n");
      out.write("                    ");
 if (do_clustering) { 
      out.write("\n");
      out.write("                        <iframe name=\"hdiFrameLoadingMsg\" id=\"hdiFrameLoadingMsg\" src=\"hierarchicalClusteringFeedback.jsp?is_clustering=true\" marginwidth=\"0\" height=\"100%\" width=\"");
      out.print(hdiFrame_width);
      out.write("\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("                    ");
 } else { 
      out.write("\n");
      out.write("                        <iframe name=\"hdiFrameLoadingMsg\" id=\"hdiFrameLoadingMsg\" src=\"hierarchicalClusteringFeedback.jsp?is_clustering=false\" marginwidth=\"0\" height=\"100%\" width=\"");
      out.print(hdiFrame_width);
      out.write("\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                    <iframe name=\"hdiFrame\" id=\"hdiFrame\" src=\"HeatmapDendrogramGenerator?analysis_name=");
      out.print(analysis_name);
      out.write("\" marginwidth=\"0\" height=\"100%\" width=\"");
      out.print(hdiFrame_width);
      out.write("\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px; visibility: hidden\"></iframe>\n");
      out.write("                    <iframe name=\"invisible_Download_Frame\" id=\"invisible_Download_Frame\" src=\"\" marginwidth=\"0\" height=\"0\" width=\"0\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("                <td rowspan=\"2\" height=\"100%\" width=\"250\"> \n");
      out.write("                    <iframe name=\"searchKeysFrame\" id=\"searchKeysFrame\" width=\"");
      out.print(searchKeysFrame_width);
      out.write("\" height=\"100%\" frameBorder=\"0\" src=\"searchKey.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("                ");
  if (do_clustering) { 
      out.write("\n");
      out.write("                \n");
      out.write("                <td rowspan=\"2\" height=\"100%\"> \n");
      out.write("                    <iframe name=\"scrollPanel\" id=\"scrollPanel\" src=\"\" marginwidth=\"0\" height=\"100%\" width=\"");
      out.print(scrollPanel_width);
      out.write("\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("                <td rowspan=\"2\" height=\"100%\"> \n");
      out.write("                    <iframe name=\"drillDownPanel\" id=\"drillDownPanel\" src=\"\" marginwidth=\"0\" height=\"100%\" width=\"800\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("                ");
  } else {    
      out.write("\n");
      out.write("                \n");
      out.write("                <td rowspan=\"2\" colspan=\"2\" height=\"100%\"> \n");
      out.write("                    <iframe name=\"scrollPanel\" id=\"scrollPanel\" src=\"\" marginwidth=\"0\" height=\"100%\" width=\"");
      out.print(scrollPanel_width);
      out.write("\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("                ");
  }   
      out.write("\n");
      out.write("                \n");
      out.write("                <td  rowspan=\"2\" width=\"550\">     \n");
      out.write("                    <iframe id=\"histPanel\" src=\"\" width=\"550\" height=\"34%\" frameBorder=\"0\" style=\"display: inline\"></iframe>\n");
      out.write("                    <iframe name=\"geneInfoFrame\" id=\"geneInfoFrame\" src=\"gene.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" marginwidth=\"0\" width=\"550\" height=\"66%\" frameBorder=\"0\"></iframe>\n");
      out.write("                </td>\n");
      out.write("                \n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("        </table>\n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        \n");
      out.write("        // Get the modal\n");
      out.write("        var modal = document.getElementById('myModal');\n");
      out.write("        \n");
      out.write("        // Get the button that opens the modal\n");
      out.write("        var btn = document.getElementById(\"createSubAnal\");\n");
      out.write("        \n");
      out.write("        // Get the <span> element that closes the modal\n");
      out.write("        var span = document.getElementById(\"modal_close\");\n");
      out.write("        var ribbon_span = document.getElementById(\"ribbon_close\");\n");
      out.write("\n");
      out.write("        var modal_inside = document.getElementById('modal_inside');\n");
      out.write("        var modal_frame = document.getElementById('modalFrame');\n");
      out.write("\n");
      out.write("        // When the user clicks the button, open the modal \n");
      out.write("        function showModalWindow(src, width, height) {\n");
      out.write("            modal_frame.src = src;\n");
      out.write("            modal.style.display = \"block\";\n");
      out.write("            modal_inside.style.width = width;\n");
      out.write("            modal_inside.style.height = height;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        // When the user clicks on <span> (x), close the modal\n");
      out.write("        span.onclick = function() {\n");
      out.write("            modal.style.display = \"none\";\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        // When the user clicks anywhere outside of the modal, close it\n");
      out.write("        window.onclick = function(event) {\n");
      out.write("            if (event.target == modal) {\n");
      out.write("                modal.style.display = \"none\";\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("</html>\n");

  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}


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
