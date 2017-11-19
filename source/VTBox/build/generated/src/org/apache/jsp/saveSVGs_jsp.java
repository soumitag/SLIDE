package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.AnalysisContainer;

public final class saveSVGs_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    //int start = analysis.state_variables.get("detailed_view_start").intValue();
    //int end = analysis.state_variables.get("detailed_view_end").intValue();
    int start = analysis.state_variables.getDetailedViewStart();
    int end = analysis.state_variables.getDetailedViewEnd();
    
    int nCols = analysis.database.datacells.width;
    int max_rows = (int)Math.floor(50000/nCols);
    
    /*
    String dendrogram_start = "";
    String dendrogram_end = "";
    String start_node_id = "";
    if (analysis.state_variables.containsKey("start_node_id")) {
        start_node_id = analysis.state_variables.get("start_node_id").intValue() + "";
    }
    if (analysis.state_variables.containsKey("dendrogram_start")) {
        dendrogram_start = analysis.state_variables.get("dendrogram_start").intValue() + "";
    }
    if (analysis.state_variables.containsKey("dendrogram_end")) {
        dendrogram_end = analysis.state_variables.get("dendrogram_end").intValue() + "";
    }
    */

    Integer start_node = analysis.state_variables.getStartNodeID();
    String start_node_id = (start_node == null) ? "" : start_node + "";
    
    Integer d_start = analysis.state_variables.getDendrogramStart();
    String dendrogram_start = (d_start == null) ? "" : d_start + "";
    
    Integer d_end = analysis.state_variables.getDendrogramEnd();
    String dendrogram_end = (d_end == null) ? "" : d_end + "";


      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Save Visualizations</title>\n");
      out.write("        <style>\n");
      out.write("            /* Style the tab */\n");
      out.write("            div.tab {\n");
      out.write("                overflow: hidden;\n");
      out.write("                border: 1px solid #ccc;\n");
      out.write("                background-color: #f1f1f1;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* Style the buttons inside the tab */\n");
      out.write("            div.tab button {\n");
      out.write("                background-color: inherit;\n");
      out.write("                float: left;\n");
      out.write("                border: none;\n");
      out.write("                outline: none;\n");
      out.write("                cursor: pointer;\n");
      out.write("                padding: 14px 16px;\n");
      out.write("                transition: 0.3s;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* Change background color of buttons on hover */\n");
      out.write("            div.tab button:hover {\n");
      out.write("                background-color: #ddd;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* Create an active/current tablink class */\n");
      out.write("            div.tab button.active {\n");
      out.write("                background-color: #ccc;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* Style the tab content */\n");
      out.write("            .tabcontent {\n");
      out.write("                display: none;\n");
      out.write("                padding: 6px 12px;\n");
      out.write("                border: 1px solid #ccc;\n");
      out.write("                border-top: none;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("    \n");
      out.write("            function openTab(evt, tabName) {\n");
      out.write("                // Declare all variables\n");
      out.write("                var i, tabcontent, tablinks;\n");
      out.write("\n");
      out.write("                // Get all elements with class=\"tabcontent\" and hide them\n");
      out.write("                tabcontent = document.getElementsByClassName(\"tabcontent\");\n");
      out.write("                for (i = 0; i < tabcontent.length; i++) {\n");
      out.write("                    tabcontent[i].style.display = \"none\";\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                // Get all elements with class=\"tablinks\" and remove the class \"active\"\n");
      out.write("                tablinks = document.getElementsByClassName(\"tablinks\");\n");
      out.write("                for (i = 0; i < tablinks.length; i++) {\n");
      out.write("                    tablinks[i].className = tablinks[i].className.replace(\" active\", \"\");\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                // Show the current tab, and add an \"active\" class to the button that opened the tab\n");
      out.write("                document.getElementById(tabName).style.display = \"block\";\n");
      out.write("                evt.currentTarget.className += \" active\";\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function saveGlobalView() {\n");
      out.write("                \n");
      out.write("                var incl_search_tags = document.getElementsByName('global_view_incl_search_tags');\n");
      out.write("                for(var i = 0; i < incl_search_tags.length; i++){\n");
      out.write("                    if (incl_search_tags[i].checked) {\n");
      out.write("                        gvi_search_tags = incl_search_tags[i].value;\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                var incl_hist = document.getElementsByName('global_view_incl_hist');\n");
      out.write("                for(var i = 0; i < incl_hist.length; i++){\n");
      out.write("                    if (incl_hist[i].checked) {\n");
      out.write("                        gvi_hist = incl_hist[i].value;\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                var gvi_dend = \"no\";\n");
      out.write("                var incl_dend = document.getElementsByName('global_view_incl_dend');\n");
      out.write("                //alert(incl_dend);\n");
      out.write("                for(var i = 0; i < incl_dend.length; i++){\n");
      out.write("                    if (incl_dend[i] != null) {\n");
      out.write("                        if (incl_dend[i].checked) {\n");
      out.write("                            gvi_dend = incl_dend[i].value;\n");
      out.write("                        }\n");
      out.write("                    } else {\n");
      out.write("                        gvi_dend = \"no\";\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                //alert(gvi_dend);\n");
      out.write("                \n");
      out.write("                var mapview_url = \"");
      out.print(base_url);
      out.write("saveViz_GlobalView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("&show_search_tags=\" + gvi_search_tags + \"&show_hist=\" + gvi_hist + \"&show_dendrogram=\" + gvi_dend;\n");
      out.write("                //alert(mapview_url);\n");
      out.write("                window.open(mapview_url);\n");
      out.write("                \n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function saveDendrogramView() {\n");
      out.write("                \n");
      out.write("                var incl_search_tags = document.getElementsByName('dend_view_incl_search_tags');                \n");
      out.write("                for(var i = 0; i < incl_search_tags.length; i++){\n");
      out.write("                    if (incl_search_tags[i].checked) {\n");
      out.write("                        gvi_search_tags = incl_search_tags[i].value;\n");
      out.write("                    }                    \n");
      out.write("                }   \n");
      out.write("               \n");
      out.write("                var incl_hist = document.getElementsByName('dend_view_incl_hist');\n");
      out.write("                for(var i = 0; i < incl_hist.length; i++){\n");
      out.write("                    if (incl_hist[i].checked) {\n");
      out.write("                        gvi_hist = incl_hist[i].value;\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                var gvi_dend = \"no\";\n");
      out.write("                var incl_dend = document.getElementsByName('dend_view_incl_dend');\n");
      out.write("                for(var i = 0; i < incl_dend.length; i++){\n");
      out.write("                    if (incl_dend[i] != null) {\n");
      out.write("                        if (incl_dend[i].checked) {\n");
      out.write("                            gvi_dend = incl_dend[i].value;\n");
      out.write("                        }\n");
      out.write("                    } else {\n");
      out.write("                        gvi_dend = \"no\";\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                                \n");
      out.write("                //alert(\"2\");\n");
      out.write("                \n");
      out.write("                var mapview_url = \"");
      out.print(base_url);
      out.write("saveViz_GlobalView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" + \n");
      out.write("                        \"&show_search_tags=\" + gvi_search_tags + \n");
      out.write("                        \"&show_hist=\" + gvi_hist + \n");
      out.write("                        \"&show_dendrogram=\" + gvi_dend + \n");
      out.write("                        \"&start_node_id=");
      out.print(start_node_id);
      out.write("\" + \n");
      out.write("                        \"&start=");
      out.print(dendrogram_start);
      out.write("\" + \n");
      out.write("                        \"&end=");
      out.print(dendrogram_end);
      out.write("\";\n");
      out.write("                //alert(mapview_url);\n");
      out.write("                window.open(mapview_url);\n");
      out.write("                \n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            function saveDetailedView() {\n");
      out.write("                var incl_search_tags = document.getElementsByName('detailed_view_incl_search_tags');\n");
      out.write("                for (var i = 0; i < incl_search_tags.length; i++){\n");
      out.write("                    if (incl_search_tags[i].checked) {\n");
      out.write("                        //alert(incl_search_tags[i].value);\n");
      out.write("                        dvi_search_tags = incl_search_tags[i].value;\n");
      out.write("                    }               \n");
      out.write("                }\n");
      out.write("                             \n");
      out.write("                var incl_hist = document.getElementsByName('detailed_view_incl_hist');\n");
      out.write("                for (var i = 0; i < incl_hist.length; i++){\n");
      out.write("                    if (incl_hist[i].checked){\n");
      out.write("                        dvi_hist = incl_hist[i].value;\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                //alert( document.getElementById('detailed_view_start').value);\n");
      out.write("                dvi_start = document.getElementById('detailed_view_start').value - 1;\n");
      out.write("                dvi_end = document.getElementById('detailed_view_end').value - 1;\n");
      out.write("                dvi_sample_height = document.getElementById('detailed_view_sample_height').value;\n");
      out.write("                dvi_feat_width = document.getElementById('detailed_view_feature_width').value;\n");
      out.write("                //alert(\"2\");\n");
      out.write("                \n");
      out.write("                row_range = dvi_end - dvi_start;\n");
      out.write("                if (row_range > ");
      out.print(max_rows);
      out.write(") {\n");
      out.write("                    alert(\"The maximum number of rows to be displayed must be less than ");
      out.print(max_rows);
      out.write(".\");\n");
      out.write("                } else {\n");
      out.write("                    var mapview_url = \"");
      out.print(base_url);
      out.write("saveViz_MapView.jsp?analysis_name=");
      out.print(analysis_name);
      out.write("\" + \n");
      out.write("                                      \"&detailed_view_incl_search_tags=\" + \n");
      out.write("                                      dvi_search_tags + \"&detailed_view_incl_hist=\" + \n");
      out.write("                                      dvi_hist + \"&start=\" + \n");
      out.write("                                      dvi_start + \"&end=\" + \n");
      out.write("                                      dvi_end + \"&sample_height=\" + \n");
      out.write("                                      dvi_sample_height + \"&feature_width=\" + dvi_feat_width;\n");
      out.write("                    var popup = window.open(mapview_url);\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("            }\n");
      out.write("    \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <div class=\"tab\">\n");
      out.write("            <button class=\"tablinks\" onclick=\"openTab(event, 'GlobalMap')\">Global Map</button>\n");
      out.write("            <button class=\"tablinks\" onclick=\"openTab(event, 'DetailedView')\">Detailed View</button>\n");
      out.write("            <button class=\"tablinks\" onclick=\"openTab(event, 'DendrogramView')\">Dendrogram View</button>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        \n");
      out.write("        <div id=\"GlobalMap\" class=\"tabcontent\">\n");
      out.write("            <table>\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\">\n");
      out.write("                        <label><b>Global Map</b></label>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td width=\"35%\">\n");
      out.write("                        Include Search Tags\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"global_view_incl_search_tags\" name=\"global_view_incl_search_tags\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"global_view_incl_search_tags\" name=\"global_view_incl_search_tags\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td width=\"35%\">\n");
      out.write("                        Include Histogram\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"global_view_incl_hist\" name=\"global_view_incl_hist\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"global_view_incl_hist\" name=\"global_view_incl_hist\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
 if (analysis.isTreeAvailable) { 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Include Dendrogram\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"global_view_incl_dend\" name=\"global_view_incl_dend\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"global_view_incl_dend\" name=\"global_view_incl_dend\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\">\n");
      out.write("                        <!--<input type=\"button\" value=\"Generate\" onclick=\"saveGlobalView()\">-->\n");
      out.write("                        <button type=\"button\" id=\"generate_svg\" title=\"Generate Visualization\" onclick=\"saveGlobalView()\">Generate</button>\n");
      out.write("                        \n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        \n");
      out.write("        <div id=\"DetailedView\" class=\"tabcontent\">\n");
      out.write("            <table>\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\">\n");
      out.write("                        <label><b>Detailed View</b></label>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Start Row\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"text\" id=\"detailed_view_start\" name=\"detailed_view_start\" value=\"");
      out.print((start+1));
      out.write("\">&nbsp;\n");
      out.write("                        Current Value: ");
      out.print((start+1));
      out.write("\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        End Row\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"text\" id=\"detailed_view_end\" name=\"detailed_view_end\" value=\"");
      out.print((end+1));
      out.write("\">&nbsp;\n");
      out.write("                        Current Value: ");
      out.print((end+1));
      out.write(". Maximum ");
      out.print(max_rows);
      out.write(" Rows Allowed.\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Include Search Tags\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"detailed_view_incl_search_tags\" name=\"detailed_view_incl_search_tags\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"detailed_view_incl_search_tags\" name=\"detailed_view_incl_search_tags\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Include Histogram\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"detailed_view_incl_hist\" name=\"detailed_view_incl_hist\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"detailed_view_incl_hist\" name=\"detailed_view_incl_hist\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Sample Name Height\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"text\" id=\"detailed_view_sample_height\" name=\"detailed_view_sample_height\" value=\"95\">&nbsp;\n");
      out.write("                        Default Value: 95.\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Feature Name Width\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        ");
 if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION) { 
      out.write("\n");
      out.write("                        <input type=\"text\" id=\"detailed_view_feature_width\" name=\"detailed_view_feature_width\" value=\"200\">&nbsp;\n");
      out.write("                        Default Value: 200.\n");
      out.write("                        ");
 } else if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION) { 
      out.write("\n");
      out.write("                        <input type=\"text\" id=\"detailed_view_feature_width\" name=\"detailed_view_feature_width\" value=\"350\">&nbsp;\n");
      out.write("                        Default Value: 350.\n");
      out.write("                        ");
 } 
      out.write("\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\">\n");
      out.write("                        <!--<input type=\"button\" value=\"Generate\" onclick=\"saveDetailedView()\">-->\n");
      out.write("                        <button type=\"button\" id=\"generate_svg\" title=\"Generate Visualization\" onclick=\"saveDetailedView()\">Generate</button>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div id=\"DendrogramView\" class=\"tabcontent\">\n");
      out.write("            <table>\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\">\n");
      out.write("                        <label><b>Dendrogram View</b></label>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td width=\"35%\">\n");
      out.write("                        Include Search Tags\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"dend_view_incl_search_tags\" name=\"dend_view_incl_search_tags\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"dend_view_incl_search_tags\" name=\"dend_view_incl_search_tags\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td width=\"35%\">\n");
      out.write("                        Include Histogram\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"dend_view_incl_hist\" name=\"dend_view_incl_hist\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"dend_view_incl_hist\" name=\"dend_view_incl_hist\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
 if (analysis.isTreeAvailable) { 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td>\n");
      out.write("                        Include Dendrogram\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <input type=\"radio\" id=\"dend_view_incl_dend\" name=\"dend_view_incl_dend\" value=\"yes\" checked=\"checked\"> Yes &nbsp;\n");
      out.write("                        <input type=\"radio\" id=\"dend_view_incl_dend\" name=\"dend_view_incl_dend\" value=\"no\"> No <br>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"2\">\n");
      out.write("                        <!--<input type=\"button\" value=\"Generate\" onclick=\"saveDendrogramView()\">-->\n");
      out.write("                        <button type=\"button\" id=\"generate_svg\" title=\"Generate Visualization\" onclick=\"saveDendrogramView()\">Generate</button>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
      out.write("\n");

  
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
