<%-- 
    Document   : searcher
    Created on : 19 Feb, 2017, 4:38:52 AM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
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
    <body style="overflow:hidden; margin-top: 2px">
        
        <table height="35px" valign="middle">
             <form name="SearchForm" action="">
                <tr style="vertical-align: top">
                             
                    <td valign="middle">
                        <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
                        <select name="queryType" id="queryType" form="SearchForm">
                            <option value="entrez">Entrez ID</option>
                            <option value="genesymbol">Gene symbol</option>
                            <option value="goid">GO ID</option>
                            <option value="goterm">GO Term</option>
                            <option value="pathid">Path ID</option>
                            <option value="pathname">Pathway</option>
                        </select>
                        <%  } else if(analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {       %>
                        <select name="queryType" id="queryType" form="SearchForm">
                            <option value="pathid">Path ID</option>
                            <option value="pathname">Pathway</option>
                        </select>
                        <%  } else if(analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {       %>
                        <select name="queryType" id="queryType" form="SearchForm">
                            <option value="goid">GO ID</option>
                            <option value="goterm">GO Term</option>
                        </select>
                        <%  }   %>
                    </td>
                    <td valign="middle">
                        <select name="searchType" id="searchType" form="SearchForm">
                            <option value="exact">&equals;</option>
                            <option value="contains">&cong;</option>                       
                        </select>
                    </td>
                    <td valign="middle" style="font-family:verdana; font-size:6;">
                        <input type="text" id="searchText" name="searchText" size="30">
                    </td>
                    <td valign="middle">
                        <!--<input type="button" value="Search" onclick="callSearcher()">-->
                        &nbsp;
                        <button type="button" name="Search" onclick="callSearcher()"> Search </button>
                        <!--<img src="images/upload.png" alt="Upload" height="20" width="20"/>-->
                    </td>
                
                </tr>
            </form>
            
        </table>
        
    </body>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>