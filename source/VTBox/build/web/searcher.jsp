<%-- 
    Document   : searcher
    Created on : 19 Feb, 2017, 4:38:52 AM
    Author     : Soumita
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <String> nonstandard_metacolnames = analysis.database.metadata.getNonStandardMetaColNames();
    
    boolean isSearchable = false;
    
    if (analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {
        if (analysis.database.species.equalsIgnoreCase("human") || analysis.database.species.equalsIgnoreCase("mouse")) {
            isSearchable = true;
        }
    } else {
        if ((analysis.database.metadata.hasStandardMetaData() || nonstandard_metacolnames.size() > 0)) {
            isSearchable = true;
        } else {
            isSearchable = false;
        }
    }
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="vtbox-main.css">
        <title>JSP Page</title>
        <script>
                       
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

            function makeGetRequest (theGetText) {
                //make a connection to the server ... specifying that you intend to make a GET request 
                //to the server. Specifiy the page name and the URL parameters to send
                //alert(theGetText);
                http.open('get', theGetText);

                //assign a handler for the response
                http.onreadystatechange = processResponse;

                //actually send the request to the server
                http.send(null);

        }
        
        function processResponse() {
                //check if the response has been received from the server
                if(http.readyState == 4){

                    //read and assign the response from the server
                    var response = http.responseText;

                    parent.display_search_result ('<%=analysis_name%>');
                }
            }

        
        function keypressCallSearcher(){
            if(window.event.keyCode == 13){
                callSearcher();
            }                
        }
        
        function callSearcher () {
            
            var search_string = document.getElementById('searchText').value;
            var q = document.getElementById('queryType');
            var query_type = q.options[q.selectedIndex].value;
            var s = document.getElementById('searchType');
            var search_type = s.options[s.selectedIndex].value;
            var milliseconds = new Date().getTime();
            //search_url = 'doSearch.jsp?queryType=' + query_type + '&searchText=' + search_string + '&searchType=' + search_type + '&timestamp=' + milliseconds + '&analysis_name=<%=analysis_name%>';
            search_url = 'DoSearch?queryType=' + query_type + 
                         '&searchText=' + search_string + 
                         '&searchType=' + search_type + 
                         '&timestamp=' + milliseconds + 
                         '&analysis_name=<%=analysis_name%>';
            
            //alert(search_url);
            makeGetRequest(search_url);
            
        }
        
        function getfilepathfrombrowse(usecase){
        if (usecase == "data") {
            var filename = document.getElementById("selectmrnafilename").value;
            document.getElementById("txtmrnafilename").value = filename;
            document.getElementById("fileinputname").value = filename;
        } else if  (usecase == "map") {
            var filename = document.getElementById("selectmapfilename").value;
            document.getElementById("txtmapfilename").value = filename;
            document.getElementById("mapfilename").value = filename;
            document.getElementById("mappingPreview").style.visibility = "visible";
            }
        }
        
    </script>
    
    </head>
    <body style="overflow:hidden; margin-top: 2px;">
        
        <table height="25px" valign="middle">
             <!--<form name="SearchForm" action=""> -->
                <tr style="vertical-align: middle">
                             
                    <td valign="middle">
                        <select name="queryType" id="queryType" form="SearchForm">
                        <%  
                        if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
                            if (analysis.database.species.equalsIgnoreCase("human") || analysis.database.species.equalsIgnoreCase("mouse")) {
                        %>
                                <option value="entrez">Entrez ID</option>
                                <option value="genesymbol">Gene Symbol</option>
                                <option value="refseq" >RefSeq ID</option>
                                <option value="ensembl_gene_id" >Ensembl gene ID</option>
                                <option value="ensembl_transcript_id" >Ensembl transcript ID</option>
                                <option value="ensembl_protein_id" >Ensembl protein ID</option>
                                <option value="uniprot_id" >UniProt ID</option>
                                <option value="goid">GO ID</option>
                                <option value="goterm">GO Term</option>
                                <option value="pathid">Path ID</option>
                                <option value="pathname">Pathway</option>
                                <option value="feature_list">Feature List</option>
                        <%  }    %>
                        
                        <%
                                for (int i=0; i<nonstandard_metacolnames.size(); i++) {
                                    String name = nonstandard_metacolnames.get(i);
                        %>
                                    <option id="<%=name%>" value="_<%=name%>" ><%=name%></option>
                        <%      }   %>
                        
                        <%  } else if(analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {   %>
                        
                            <option value="pathid">Path ID</option>
                            <option value="pathname">Pathway</option>
                        
                        <%  } else if(analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION) {  %>
                        
                            <option value="goid">GO ID</option>
                            <option value="goterm">GO Term</option>
                        
                        <%  }   %>
                        </select>
                    </td>
                    <td valign="middle">
                        <select name="searchType" id="searchType" form="SearchForm">
                            <option value="exact">&equals;</option>
                            <option value="contains">&cong;</option>                       
                        </select>
                    </td>
                    <td valign="middle" style="font-family:verdana; font-size:6;">
                        <% if (isSearchable) { %>
                        <input type="text" id="searchText" name="searchText" size="30" onkeypress="keypressCallSearcher()" />
                        <% } else { %>
                        <input type="text" id="searchText" name="searchText" size="30" onkeypress="keypressCallSearcher()" disabled/>
                        <% } %>
                    </td>
                    <td valign="middle">
                        <% if (isSearchable) { %>
                        <button type="button" name="Search" onclick="callSearcher()"> Search </button>
                        <% } else { %>
                        <button type="button" name="Search" onclick="alert('When species is Other / no metadata columns have been mapped to standard identifiers, search and tagging features will not be available.')"> Search </button>
                        <% } %>
                    </td>
                
                </tr>
            <!--</form>-->
            
        </table>
        
    </body>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>