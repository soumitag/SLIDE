<%-- 
    Document   : searchKey
    Created on : 19 Mar, 2017, 1:12:34 PM
    Author     : Soumita
--%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.Set"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="structure.Data"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    Data db = analysis.database;
    
    int num_features = db.features.size();
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    int start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        end = num_features;
    }
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    
    if (search_results == null || search_results.size() == 0) {
        String msg = "No search results to show.";
        getServletContext().getRequestDispatcher("/Exception.jsp?msg=" + msg).forward(request, response);
        return;
    }
    
    ArrayList <String> search_strings = analysis.search_strings;
    
    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set <String> keys = filterListMap.keySet();
    Iterator list_iter = keys.iterator();
%>
<html>
    <head>
        
        <link rel="stylesheet" href="vtbox-main.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <script>
        
        function addSearchKey(text2, srcount){            
            document.getElementById('containerSK').innerHTML = 
                    document.getElementById('containerSK').innerHTML + text2;            
        }
        
        
        function callFrmSearchKeyP(pathid, analysis_name) {            
            parent.showDetailedPathInfo(pathid, analysis_name);
        }
        
        function callFrmSearchKeyG(goid, analysis_name) {            
            parent.showDetailedGOInfo(goid, analysis_name);
        }
        
        function callFrmSearchKeyE(eid, analysis_name) {            
            parent.showDetailedInfo(eid, analysis_name);
        }  
        
        var selected_object_id = "";
                
        function callHighlightPathwayGenes(search_no, pathid){
                        
            if (pathid != selected_object_id) {
                // deHighlight selected_object_id
                if (selected_object_id != "") {
                    var this_div = document.getElementById(selected_object_id);
                    this_div.style.fontWeight = "normal";
                    parent.toggleHighlightGenes(search_no, selected_object_id, 0);
                }
                
                // highlight pathid
                var this_div = document.getElementById(pathid);
                this_div.style.fontWeight = "bold";
                parent.toggleHighlightGenes(search_no, pathid, 1);
                
                selected_object_id = pathid;
                
            } else {
                
                // deHighlight pathid
                var this_div = document.getElementById(pathid);
                this_div.style.fontWeight = "normal";
                parent.toggleHighlightGenes(pathid, 0);
            }         
      
        }
        
        function createRequestObject() {
            
            var tmpXmlHttpObject;

            //depending on what the browser supports, use the right way to create the XMLHttpRequest object
            if (window.XMLHttpRequest) { 
                // Mozilla, Safari would use this method ...
                tmpXmlHttpObject = new XMLHttpRequest();

            } else if (window.ActiveXObject) { 
                // IE would use this method ...
                tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
            }

            return tmpXmlHttpObject;
        }
        
        var http = createRequestObject();
        var selected_search_indices = [];
        var selected_list_name = '';
        
        function makeGetRequest_2 (selected_search_indices_str) {
            //make a connection to the server ... specifying that you intend to make a GET request 
            //to the server. Specifiy the page name and the URL parameters to send
            http.open('get', 'AddDataToList?mode=search_result&list_name=' + selected_list_name + '&search_indices=' + selected_search_indices_str + '&analysis_name=<%=analysis_name%>');

            //assign a handler for the response
            http.onreadystatechange = processResponse_2;

            //actually send the request to the server
            http.send(null);
        }
            
        function processResponse_2 () {
            //check if the response has been received from the server
            if(http.readyState == 4){

                //read and assign the response from the server
                var response = http.responseText;

                //do additional parsing of the response, if needed
                if (response.trim() == "1") {
                    alert('Features Added to ' + selected_list_name + '.');
                } else {
                    alert('Features Could Not Be Added. Please Try Again.');
                }

            }
        }
        
        function toggleSelection(search_index, search_text, label_id) {
            //alert(selected_search_indices);
            //alert(selected_search_indices.indexOf(search_index));
            var index = selected_search_indices.indexOf(search_index);
            var search_text_td = document.getElementById(label_id);
            if (index > -1) {
                selected_search_indices.splice(search_index, 1);
                search_text_td.textContent = search_index + " : " + search_text;
                search_text_td.style.color = "black";
            } else {
                selected_search_indices.push(search_index);
                search_text_td.textContent = "\u2713 " + search_index + " : " + search_text;
                search_text_td.style.color = "green";
            }
            //alert(selected_search_indices);
        }
        
        function addToList (list_name) {
            //alert(selected_search_indices.toString());
            selected_list_name = list_name;
            makeGetRequest_2 (selected_search_indices.toString());
        }
        
        function addRemoveListName(list_name, add_remove_ind) {
            if (add_remove_ind === 1) {
                    // add
                var node = document.createElement("a");
                var text = document.createTextNode(list_name);
                node.appendChild(text);
                node.setAttribute("href", "#");
                node.setAttribute("id", "flin_" + list_name);
                node.onclick = function () {
                    addToList(list_name);
                };
                document.getElementById("list_names_container").appendChild(node);
                //alert(document.getElementById("list_names_container"));
            } else if (add_remove_ind === 0) {
                // remove
                var node = document.getElementById("flin_" + list_name);
                document.getElementById("list_names_container").removeChild(node);
            }
        }
        
    </script>
    </head>
    <body> 
    
    <table width="100%">
        <tr>
            <td align="center" height="35">
                <div class="dropdown">
                    <button class="dropbtn" title="Add Selected Search Tagged Features to Feature Lists. Click Search Keys To Select. Click Feature Lists To Create New Feature Lists.">Add To List</button>
                    <div align="left" id="list_names_container" class="dropdown-content">
                        <%
                            while (list_iter.hasNext()) {
                                String list_name = (String) list_iter.next();
                        %>
                        <a id="flin_<%=list_name%>" onclick="addToList('<%=list_name%>')" href="#"><%=list_name%></a>
                        <%  }   %>
                    </div>
                </div>
            </td>
        </tr>
    </table>
                    
    <table id="containerSK1" name="containerSK1">
        <tr id="containerTRName" name="containerTRName">
            <td id="containerSK" name="containerSK">
                    
    <%  for(int i = 0; i < search_results.size(); i++) {     %>
            
            <table><tr><td id="search_head_<%=i%>" class='h' onclick="toggleSelection('<%=i%>', '<%=search_strings.get(i)%>', 'search_head_<%=i%>')"><%=i%> : <%=search_strings.get(i)%> </td></tr>
                <tr><td>
        
        <%
            ArrayList <CompactSearchResultContainer> search_results_i = search_results.get(i);
            if(search_results_i.size() > 0){
                if (search_results_i.get(0).type == CompactSearchResultContainer.TYPE_PATH) {

                    HashMap <String, String> path_eid_map = new HashMap <String, String> ();

                    for (int j = 0; j < search_results_i.size(); j++) {
                        CompactSearchResultContainer search_results_ij = search_results_i.get(j);            
                        path_eid_map.put(search_results_ij.getPathID(), search_results_ij.getPathName());
                    }

                    Iterator iter = path_eid_map.entrySet().iterator();
                    while(iter.hasNext()) {  

                        Map.Entry pair = (Map.Entry)iter.next();
                        if (search_strings.get(i).startsWith("pathname")) {
            %>
                            <div class='e' id='<%=i + "_" + pair.getKey()%>' style='font-weight:normal' onclick='callFrmSearchKeyP("<%=pair.getKey()%>", "<%=analysis_name%>"); callHighlightPathwayGenes(<%=i%>, "<%=i + "_" + pair.getKey()%>")' > <%=pair.getValue()%> </div>
            <%                    
                        } else if (search_strings.get(i).startsWith("pathid")) {
            %>
                            <div class='e' id='<%=i + "_" + pair.getKey()%>' style='font-weight:normal' onclick='callFrmSearchKeyP("<%=pair.getKey()%>", "<%=analysis_name%>"); callHighlightPathwayGenes(<%=i%>, "<%=i + "_" + pair.getKey()%>")' > <%=pair.getKey()%> </div>
            <%
                        }
                    } 

                } else if (search_results_i.get(0).type == CompactSearchResultContainer.TYPE_GO) {

                    HashMap <String, String> goid_eid_map = new HashMap <String, String> ();

                    for (int j = 0; j < search_results_i.size(); j++) {    
                        CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                        goid_eid_map.put(search_results_ij.getGOID(), search_results_ij.getGOTerm());
                    }

                    Iterator iter = goid_eid_map.entrySet().iterator();
                    while(iter.hasNext()) {  

                        Map.Entry pair = (Map.Entry)iter.next();
                        if (search_strings.get(i).startsWith("goterm")) {
            %>
                            <div class='e' id='<%=i + "_" + pair.getKey()%>' style='font-weight:normal' onclick='callFrmSearchKeyG("<%=pair.getKey()%>", "<%=analysis_name%>"); callHighlightPathwayGenes(<%=i%>, "<%=i + "_" + pair.getKey()%>")' > <%=pair.getValue()%> </div>
            <%                    
                        } else if (search_strings.get(i).startsWith("goid")) {
            %>
                            <div class='e' id='<%=i + "_" + pair.getKey()%>' style='font-weight:normal' onclick='callFrmSearchKeyG("<%=pair.getKey()%>", "<%=analysis_name%>"); callHighlightPathwayGenes(<%=i%>, "<%=i + "_" + pair.getKey()%>")' > <%=pair.getKey()%> </div>
            <%
                        }
                    }

                } else if (search_results_i.get(0).type == CompactSearchResultContainer.TYPE_GENE) {

                    for (int j = 0; j < search_results_i.size(); j++) {

                        CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                        if (search_strings.get(i).startsWith("entrez")) {
            %>
                            <div class='e' id='<%=i + "_" + search_results_ij.entrez_id%>' style='font-weight:normal' onclick='callFrmSearchKeyE("<%=search_results_ij.entrez_id%>", "<%=analysis_name%>"); callHighlightPathwayGenes("<%=i + "_" + search_results_ij.entrez_id%>")' > <%=search_results_ij.entrez_id%> </div>
            <%                    
                        } else if (search_strings.get(i).startsWith("genesymbol")) {
            %>
                            <div class='e' id='<%=i + "_" + search_results_ij.entrez_id%>' style='font-weight:normal' onclick='callFrmSearchKeyE("<%=search_results_ij.entrez_id%>", "<%=analysis_name%>"); callHighlightPathwayGenes("<%=i + "_" + search_results_ij.entrez_id%>")' > <%=search_results_ij.getGeneSymbol()%> </div>
            <%
                        }
                    }

                }
            }
    %>
    
        </td></tr></table>

    <%  }   %>
                    
        </td>
        </tr>
        </table>
        
    </body>
    
    <script>
        //alert(selected_search_indices);
    </script>
    
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>