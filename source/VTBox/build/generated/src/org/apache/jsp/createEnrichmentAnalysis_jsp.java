package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import utils.SessionManager;
import java.io.FileReader;
import java.io.BufferedReader;
import algorithms.enrichment.EnrichmentAnalysis;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.ArrayList;
import structure.AnalysisContainer;
import java.util.Map;
import java.util.Iterator;

public final class createEnrichmentAnalysis_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    
try {
    
    String base_url = (String)session.getAttribute("base_url");

    // mode can be input, process, and name_error
    // mode input: shows input form, this is the default mode
    // mode process: the enrichment analysis is created and a link to open the enrichment analysis is provided
    // mode name_error: shows input form, with the message The name blah blah is already in use. Please specify another name.
    // mode filter_error: shows input form, with the message No enriched functional groups could be found. Please relax the filter conditions and try again. 
    // mode emptylist_error: shows input form, with the message All the selected feature lists are empty. Enrichment analysis requires at least one non-empty feature list.
    // mode ontology_error: shows input form, with the message No gene ontology selected. GO Enrichment analysis requires at least one gene ontology to be selected.
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    String enrichment_name = request.getParameter("enrichment_name");
    
    if (mode.equals("process")) {
        
        enrichment_name = enrichment_name.trim();
        
        // check if the new name provided for sub-analysis is already in use
        if (session.getAttribute(enrichment_name) != null) {
            
            String url = "createEnrichmentAnalysis.jsp?mode=name_error&analysis_name=" + analysis_name + "&enrichment_name=" + enrichment_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
            
        } else {
            
            // create the enrichment analysis
            String enrichment_type = request.getParameter("enrichment_type");
            String[] ontologies = request.getParameterValues("ontologies");
            
            if (enrichment_type.equals("go")) {
                if (ontologies == null || ontologies.length == 0) {
                    String url = "createEnrichmentAnalysis.jsp?mode=ontology_error&analysis_name=" + analysis_name + "&enrichment_name=" + enrichment_name;
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }
            }
            
            double significance_level = Double.parseDouble(request.getParameter("txtSignificanceLevel"));
            int big_K = Integer.parseInt(request.getParameter("txtBig_K"));
            int small_k = Integer.parseInt(request.getParameter("txtSmall_k"));
            
            String feature_list_names = request.getParameter("feature_list_names");
            
            ArrayList <String> specified_feature_list_names = new ArrayList <String> ();
            HashMap <Integer, String> list_order_map = new HashMap <Integer, String> ();
            StringTokenizer st = new StringTokenizer (feature_list_names, ",");
            int lcount = 0;
            while (st.hasMoreTokens()) {
                String list_name = st.nextToken().trim();
                specified_feature_list_names.add(list_name);
                list_order_map.put(lcount++, list_name);
            }
            
            HashMap <String, ArrayList <Integer>> selected_filter_list_maps = 
                    new HashMap <String, ArrayList <Integer>> ();
            
            boolean hasNonEmptyList = false;
            for (int i=0; i<specified_feature_list_names.size(); i++) {
                String name = specified_feature_list_names.get(i);
                ArrayList <Integer> list_i = analysis.filterListMap.get(name);
                selected_filter_list_maps.put(name, list_i);
                if (list_i != null && list_i.size() > 0) {
                    hasNonEmptyList = true;
                }
            }
            
            if (!hasNonEmptyList) {
                String url = "createEnrichmentAnalysis.jsp?mode=emptylist_error&analysis_name=" + analysis_name + "&enrichment_name=" + enrichment_name;
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            /*
            HashMap <String, ArrayList <Integer>> selected_filter_list_maps = 
                    new HashMap <String, ArrayList <Integer>> ();
            
            for (int i=0; i<5; i++) {
                ArrayList <Integer> list_i = new ArrayList <Integer> ();
                for (int j=i*100; j<(i*100)+100; j++) {
                    list_i.add(j);
                }
                selected_filter_list_maps.put("List_" + i, list_i);
            }
            */
            
            /*
            ArrayList <Integer> row_numbers_a8 = new ArrayList <Integer> ();
            BufferedReader br = null;
            String line = null;
            try {
                br = new BufferedReader(new FileReader("D:\\ad\\sg\\16_June_2017\\A8_Row_Numbers.txt"));
                while ((line = br.readLine()) != null) {
                    row_numbers_a8.add(Integer.parseInt(line.trim().toLowerCase()));
                }
            } catch (Exception e) {
                System.out.println("Error reading input data:");
                System.out.println(e);
            }
            selected_filter_list_maps.put("A8", row_numbers_a8);

            ArrayList <Integer> row_numbers_b7 = new ArrayList <Integer> ();
            try {
                br = new BufferedReader(new FileReader("D:\\ad\\sg\\16_June_2017\\B7_Row_Numbers.txt"));
                while ((line = br.readLine()) != null) {
                    row_numbers_b7.add(Integer.parseInt(line.trim().toLowerCase()));
                }
            } catch (Exception e) {
                System.out.println("Error reading input data:");
                System.out.println(e);
            }
            selected_filter_list_maps.put("B7", row_numbers_b7);
            */

            EnrichmentAnalysis ea = new EnrichmentAnalysis(
                    analysis.database.features, analysis.entrezPosMap, selected_filter_list_maps,
                    analysis.database.species, enrichment_type, big_K, small_k, significance_level, 
                    ontologies);
            ea.run(list_order_map);
            //ea.closeDBconn();
            
            if (ea.testParams.nonmasked_funcgrp_count > 0) {
                String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
                String session_id = request.getSession().getId();
                AnalysisContainer sub_analysis = analysis.createSubAnalysis(enrichment_name, ea, installPath, session_id);
                session.setAttribute(sub_analysis.analysis_name, sub_analysis);
            } else {
                String url = "createEnrichmentAnalysis.jsp?mode=filter_error&analysis_name=" + analysis_name + "&enrichment_name=" + enrichment_name;
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            
        }
        
    }
    
    

      out.write('\n');
      out.write('\n');
 if (mode.equals("input") || mode.equals("name_error") || mode.equals("filter_error") || mode.equals("emptylist_error") || mode.equals("ontology_error")) {  
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        \n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("\n");
      out.write("            var selected_feature_list_names = [];\n");
      out.write("\n");
      out.write("            function get_enrichment_type(){\n");
      out.write("                var d = document.getElementById(\"enrichment_type_selector\");\n");
      out.write("                //alert(optionDelim);\n");
      out.write("                var option_etype = d.options[d.selectedIndex].value;\n");
      out.write("                document.getElementById(\"enrichment_type\").value = option_etype;\n");
      out.write("                //alert(optionDelim); \n");
      out.write("            }\n");
      out.write("\n");
      out.write("            function add_feature_list() {\n");
      out.write("                \n");
      out.write("                var d = document.getElementById(\"session_list_name\");\n");
      out.write("                var selected_list_name = d.options[d.selectedIndex].value;\n");
      out.write("                \n");
      out.write("                var div_node = document.createElement(\"div\");\n");
      out.write("                var text = document.createTextNode(selected_list_name + \" \");\n");
      out.write("                div_node.appendChild(text);\n");
      out.write("                div_node.setAttribute(\"id\", \"div_\" + selected_list_name);\n");
      out.write("                div_node.setAttribute(\"class\", \"e1\");\n");
      out.write("                \n");
      out.write("                var close_node = document.createElement(\"a\");\n");
      out.write("                var cross_text = document.createTextNode('\\u2715');\n");
      out.write("                close_node.appendChild(cross_text);\n");
      out.write("                close_node.setAttribute(\"href\", \"#\");\n");
      out.write("                close_node.onclick = function () {\n");
      out.write("                    deSelectFeatureList(selected_list_name);\n");
      out.write("                };\n");
      out.write("                div_node.appendChild(close_node);\n");
      out.write("                \n");
      out.write("                document.getElementById(\"list_box_td\").appendChild(div_node);\n");
      out.write("                \n");
      out.write("                selected_feature_list_names.push(selected_list_name);\n");
      out.write("\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function deSelectFeatureList(list_name) {\n");
      out.write("                \n");
      out.write("                var node = document.getElementById(\"div_\" + list_name);\n");
      out.write("                document.getElementById(\"list_box_td\").removeChild(node);\n");
      out.write("                \n");
      out.write("                var pos = selected_feature_list_names.indexOf(list_name);\n");
      out.write("                selected_feature_list_names.splice(pos, 1);\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            function openEnrichmentWindow() {\n");
      out.write("                var url = \"");
      out.print(base_url);
      out.write("SerializeAnalysisServlet?analysis_name=");
      out.print(analysis_name);
      out.write("\";\n");
      out.write("                //alert(url);\n");
      out.write("                document.getElementById(\"invisible_Download_Frame\").contentWindow.location.replace(url);\n");
      out.write("                //makeGetRequest (url);\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            function createEnrichmentAnalysis() {\n");
      out.write("                document.getElementById(\"feature_list_names\").value = selected_feature_list_names.toString();\n");
      out.write("                //alert(document.getElementById(\"feature_list_names\").value);\n");
      out.write("                document.getElementById(\"mode\").value = \"process\";\n");
      out.write("                document.getElementById(\"createEA_button\").innerHTML = \"Creating Analysis...\"\n");
      out.write("                document.getElementById(\"createEA_button\").disabled = true;\n");
      out.write("                document.getElementById(\"createEnrichmentForm\").submit();\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("<body>\n");
      out.write("    \n");
      out.write("    <form name=\"createEnrichmentForm\" id=\"createEnrichmentForm\" method=\"get\" action=\"createEnrichmentAnalysis.jsp\">\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;height:500px\" border=\"0\">\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"70\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>Create Enrichment Analysis</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        ");
 if (mode.equals("name_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>The name ");
      out.print(enrichment_name);
      out.write(" is already in use. Please specify another name.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        ");
 if (mode.equals("filter_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>No enriched functional groups could be found. Please relax the filter conditions and try again.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        ");
 if (mode.equals("emptylist_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>All the selected feature lists are empty. Enrichment analysis requires at least one non-empty feature list.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        ");
 if (mode.equals("ontology_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>No gene ontology selected. GO Enrichment analysis requires at least one gene ontology to be selected.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td width=\"50%\">\n");
      out.write("                <b><label>Enter a Name for the Enrichment Analysis</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <input type=\"text\" id=\"enrichment_name\" name=\"enrichment_name\" size=\"35\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Enter the Enrichment Type</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <select id=\"enrichment_type_selector\" name=\"enrichment_type_selector\" onchange=\"get_enrichment_type();\">\n");
      out.write("                    <option id=\"pathway\" value=\"pathway\" >Pathway</option>\n");
      out.write("                    <option id=\"go\" value=\"go\" >Gene Ontology</option>\n");
      out.write("                </select>\n");
      out.write("                <input type=\"hidden\" id=\"enrichment_type\" name=\"enrichment_type\" value=\"\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Select Feature Lists to be included in the Enrichment Analysis and Click Add</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td colspan=\"2\">\n");
      out.write("                <select name=\"session_list_name\" id=\"session_list_name\">\n");
      out.write("                    ");
 Iterator it = analysis.filterListMap.entrySet().iterator();  
      out.write("\n");
      out.write("                    ");
 while (it.hasNext()) {   
      out.write("\n");
      out.write("                        ");
 String featurelist_name = ((Map.Entry)it.next()).getKey().toString(); 
      out.write("\n");
      out.write("                        <option value=\"");
      out.print(featurelist_name);
      out.write("\" >");
      out.print(featurelist_name);
      out.write("</option>\n");
      out.write("                    ");
 }    
      out.write("\n");
      out.write("                </select>\n");
      out.write("                &nbsp;\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Add\" onclick=\"add_feature_list()\">Add</button>\n");
      out.write("                <!--<input type=\"button\" value=\"Add\" onclick=\"add_feature_list()\">-->\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Select Feature Lists </label></b>\n");
      out.write("            </td>\n");
      out.write("            <td id=\"list_box_td\" height=\"5px\" align=\"center\">\n");
      out.write("                \n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Include Functional Groups with p-value Lower Than: <br> (Significance Level) </label></b>\n");
      out.write("            </td>\n");
      out.write("            <td height=\"5px\" align=\"center\">\n");
      out.write("                <input type=\"text\" name=\"txtSignificanceLevel\" value=\"0.05\" maxlength=\"4\" size=\"4\" >\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label> Include Functional Groups That Contain At Least These Many Feature List Genes: </label></b>\n");
      out.write("            </td>\n");
      out.write("            <td height=\"5px\" align=\"center\">\n");
      out.write("                <input type=\"text\" name=\"txtSmall_k\" value=\"0\" maxlength=\"4\" size=\"4\" >\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Include Functional Groups That Contain At Least These Many Genes: </label></b>\n");
      out.write("            </td>\n");
      out.write("            <td height=\"5px\" align=\"center\">\n");
      out.write("                <input type=\"text\" name=\"txtBig_K\" value=\"0\" maxlength=\"4\" size=\"4\" >\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Include Ontologies (Only used for Gene Ontology Enrichment): </label></b><br>\n");
      out.write("            </td>\n");
      out.write("            <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                <input type=\"checkbox\" id=\"ontologies\" name=\"ontologies\" value=\"bp\" checked> Biological Processes</input>\n");
      out.write("                <input type=\"checkbox\" id=\"ontologies\" name=\"ontologies\" value=\"mf\" checked> Molecular Function</input>\n");
      out.write("                <input type=\"checkbox\" id=\"ontologies\" name=\"ontologies\" value=\"cc\" checked> Cellular Components</input>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td height=\"5px\" colspan=\"2\" align=\"center\">\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create\" id=\"createEA_button\" onclick=\"createEnrichmentAnalysis()\">Create</button>\n");
      out.write("                <!--<input type=\"button\" value=\"Create\" onclick=\"createEnrichmentAnalysis()\">-->\n");
      out.write("                <input type=\"hidden\" id=\"feature_list_names\" name=\"feature_list_names\" value=\"\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <input type=\"hidden\" id=\"mode\" name=\"mode\" value=\"input\">\n");
      out.write("        <input type=\"hidden\" id=\"analysis_name\" name=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\">\n");
      out.write("        \n");
      out.write("        </form>\n");
      out.write("    </table>\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");
      out.write("\n");
  } else if (mode.equals("process")) { 
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <style>\n");
      out.write("\n");
      out.write("            table {\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-collapse: collapse;\n");
      out.write("                width: 100%;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            td, th {\n");
      out.write("                border: 1px solid #dddddd;\n");
      out.write("                padding: 8px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            tr:nth-child(even) {\n");
      out.write("                background-color: #efefef;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        </style>\n");
      out.write("        \n");
      out.write("        <script>\n");
      out.write("    \n");
      out.write("            $(window).load(function() {\n");
      out.write("                    $(\".loader\").fadeOut(\"slow\");\n");
      out.write("            })\n");
      out.write("    \n");
      out.write("        </script>\n");
      out.write("        \n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    <div class=\"loader\"></div>\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"15\">\n");
      out.write("                &nbsp;\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\">\n");
      out.write("                Enrichment analysis <i>");
      out.print(enrichment_name);
      out.write("</i> created. <br>\n");
      out.write("                Click <a href=\"");
      out.print(base_url);
      out.write("displayHome.jsp?analysis_name=");
      out.print(enrichment_name);
      out.write("\" target=\"_blank\">here</a> to open.\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\" align=\"center\">\n");
      out.write("                To save the enrichment analysis open it and click the \"Save Analysis\" button.\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("    </table>\n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
  }   
      out.write('\n');
      out.write('\n');

  
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
