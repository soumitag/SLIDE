<%-- 
    Document   : createEnrichmentAnalysis
    Created on : Jun 17, 2017, 12:30:53 PM
    Author     : soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="utils.SessionManager"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="algorithms.enrichment.EnrichmentAnalysis"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
try {
    
    String base_url = (String)session.getAttribute("base_url");

    // mode can be input, process, and name_error
    // mode input: shows input form, this is the default mode
    // mode process: the enrichment analysis is created and a link to open the enrichment analysis is provided
    // mode name_error: shows input form, with the message The name blah blah is already in use. Please specify another name.
    // mode filter_error: shows input form, with the message No enriched functional groups could be found. Please relax the filter conditions and try again. 
    // mode emptylist_error: shows input form, with the message All the selected feature lists are empty. Enrichment analysis requires at least one non-empty feature list.
    
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
            double significance_level = Double.parseDouble(request.getParameter("txtSignificanceLevel"));
            int big_K = Integer.parseInt(request.getParameter("txtBig_K"));
            int small_k = Integer.parseInt(request.getParameter("txtSmall_k"));
            
            String feature_list_names = request.getParameter("feature_list_names");
            
            ArrayList <String> specified_feature_list_names = new ArrayList <String> ();
            StringTokenizer st = new StringTokenizer (feature_list_names, ",");
            while (st.hasMoreTokens()) {
                specified_feature_list_names.add(st.nextToken().trim());
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
                    analysis.database.features, selected_filter_list_maps,
                    analysis.database.species, enrichment_type, big_K, small_k, significance_level, 
                    ontologies);
            ea.run();
            ea.closeDBconn();
            
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
    
    
%>

<% if (mode.equals("input") || mode.equals("name_error") || mode.equals("filter_error") || mode.equals("emptylist_error")) {  %>

<html>
    <head>
        
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">
        
        <script>

            var selected_feature_list_names = [];

            function get_enrichment_type(){
                var d = document.getElementById("enrichment_type_selector");
                //alert(optionDelim);
                var option_etype = d.options[d.selectedIndex].value;
                document.getElementById("enrichment_type").value = option_etype;
                //alert(optionDelim); 
            }

            function add_feature_list() {
                
                var d = document.getElementById("session_list_name");
                var selected_list_name = d.options[d.selectedIndex].value;
                
                var div_node = document.createElement("div");
                var text = document.createTextNode(selected_list_name + " ");
                div_node.appendChild(text);
                div_node.setAttribute("id", "div_" + selected_list_name);
                div_node.setAttribute("class", "e1");
                
                var close_node = document.createElement("a");
                var cross_text = document.createTextNode('\u2715');
                close_node.appendChild(cross_text);
                close_node.setAttribute("href", "#");
                close_node.onclick = function () {
                    deSelectFeatureList(selected_list_name);
                };
                div_node.appendChild(close_node);
                
                document.getElementById("list_box_td").appendChild(div_node);
                
                selected_feature_list_names.push(selected_list_name);

            }
            
            function deSelectFeatureList(list_name) {
                
                var node = document.getElementById("div_" + list_name);
                document.getElementById("list_box_td").removeChild(node);
                
                var pos = selected_feature_list_names.indexOf(list_name);
                selected_feature_list_names.splice(pos, 1);
            }

            function openEnrichmentWindow() {
                var url = "<%=base_url%>SerializeAnalysisServlet?analysis_name=<%=analysis_name%>";
                //alert(url);
                document.getElementById("invisible_Download_Frame").contentWindow.location.replace(url);
                //makeGetRequest (url);
            }

            function createEnrichmentAnalysis() {
                document.getElementById("feature_list_names").value = selected_feature_list_names.toString();
                alert(document.getElementById("feature_list_names").value);
                document.getElementById("mode").value = "process";
                document.getElementById("createEnrichmentForm").submit();
            }

            
        </script>
    </head>
    
<body>
    
    <form name="createEnrichmentForm" id="createEnrichmentForm" method="get" action="createEnrichmentAnalysis.jsp">
    <table cellpadding="5" style="width: 100%;" border="0">

        <tr>
            <td height="75" align="center" colspan="2">
                <b><label>Create Enrichment Analysis</label></b>
            </td>
        </tr>

        <% if (mode.equals("name_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>The name <%=enrichment_name%> is already in use. Please specify another name.</label></b>
            </td>
        </tr>
        <% } %>
        
        <% if (mode.equals("filter_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>No enriched functional groups could be found. Please relax the filter conditions and try again.</label></b>
            </td>
        </tr>
        <% } %>
        
        <% if (mode.equals("emptylist_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>All the selected feature lists are empty. Enrichment analysis requires at least one non-empty feature list.</label></b>
            </td>
        </tr>
        <% } %>
        
        <tr>
            <td width="50%">
                <b><label>Enter a Name for the Enrichment Analysis</label></b>
            </td>
            <td>
                <input type="text" id="enrichment_name" name="enrichment_name" size="35">
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label>Enter the Enrichment Type</label></b>
            </td>
            <td>
                <select id="enrichment_type_selector" name="enrichment_type_selector" onchange="get_enrichment_type();">
                    <option id="pathway" value="pathway" >Pathway</option>
                    <option id="go" value="go" >Gene Ontology</option>
                </select>
                <input type="hidden" id="enrichment_type" name="enrichment_type" value="">
            </td>
        </tr>

        <tr>
            <td>
                <b><label>Select Feature Lists to be included in the Enrichment Analysis and Click Add</label></b>
            </td>
            <td colspan="2">
                <select name="session_list_name" id="session_list_name">
                    <% Iterator it = analysis.filterListMap.entrySet().iterator();  %>
                    <% while (it.hasNext()) {   %>
                        <% String featurelist_name = ((Map.Entry)it.next()).getKey().toString(); %>
                        <option value="<%=featurelist_name%>" ><%=featurelist_name%></option>
                    <% }    %>
                </select>
                &nbsp;
                <input type="button" value="Add" onclick="add_feature_list()">
            </td>
        </tr>

        <tr>
            <td>
                <b><label>Select Feature Lists </label></b>
            </td>
            <td id="list_box_td" height="5px" align="center">
                
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label>Include Functional Groups with p-value Lower Than: <br> (Significance Level) </label></b>
            </td>
            <td height="5px" align="center">
                <input type="text" name="txtSignificanceLevel" value="0.05" maxlength="4" size="4" >
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label> Include Functional Groups That Contain At Least These Many Feature List Genes: </label></b>
            </td>
            <td height="5px" align="center">
                <input type="text" name="txtSmall_k" value="0" maxlength="4" size="4" >
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label>Include Functional Groups That Contain At Least These Many Genes: </label></b>
            </td>
            <td height="5px" align="center">
                <input type="text" name="txtBig_K" value="0" maxlength="4" size="4" >
            </td>
        </tr>

        <tr>
            <td>
                <b><label>Include Ontologies (Only used for Gene Ontology Enrichment): </label></b><br>
            </td>
            <td colspan="4" style="padding: 10px;">
                <input type="checkbox" id="ontologies" name="ontologies" value="biological_processes"> Biological Processes</input>
                <input type="checkbox" id="ontologies" name="ontologies" value="molecular_function"> Molecular Function</input>
                <input type="checkbox" id="ontologies" name="ontologies" value="cellular_components"> Cellular Components</input>
            </td>
        </tr>
        
        <tr>
            <td height="5px" colspan="2" align="center">
                <input type="button" value="Create" onclick="createEnrichmentAnalysis()">
                &nbsp;
                <input type="hidden" id="feature_list_names" name="feature_list_names" value="">
            </td>
        </tr>
        
        <input type="hidden" id="mode" name="mode" value="input">
        <input type="hidden" id="analysis_name" name="analysis_name" value="<%=analysis_name%>">
        
        </form>
    </table>
</body>

</html>

<%  } else if (mode.equals("process")) { %>

<html>
<head>
    <style>

            table {
                font-family: verdana;
                border-collapse: collapse;
                width: 100%;
            }

            td, th {
                border: 1px solid #dddddd;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color: #efefef;
            }

        </style>
        
        <script>
    
            $(window).load(function() {
                    $(".loader").fadeOut("slow");
            })
    
        </script>
        
</head>

<body>
    <div class="loader"></div>
    <table cellpadding="5" style="width: 100%;" border="0">
        <tr>
            <td height="15">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td height="75">
                Enrichment analysis <i><%=enrichment_name%></i> created. <br>
                Click <a href="<%=base_url%>displayHome.jsp?analysis_name=<%=enrichment_name%>" target="_blank">here</a> to open.
            </td>
        </tr>
        <tr>
            <td height="75" align="center">
                To save the enrichment analysis open it and click the "Save Analysis" button.
            </td>
        </tr>
    </table>
</body>
</html>

<%  }   %>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>