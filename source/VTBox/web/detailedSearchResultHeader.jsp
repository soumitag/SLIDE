<%-- 
    Document   : detailedSearchResultHeader
    Created on : Apr 6, 2017, 11:33:14 PM
    Author     : soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    ArrayList <String> search_strings = analysis.search_strings;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            .marker {
                position: absolute; 
                width: 20px; 
                height: 1px;
                background: #73AD21;
                border: none;
                cursor: pointer;
            }
            
            .cross {
                border: 1px; 
                display: none; 
                opacity: 0.5; 
                cursor: pointer;
            }
            
            table {
                border-spacing: 5px 0px;
            }
            
            td {
                background: #EEEEEE;
                width: 18px;
                height: 35px;
                text-align: center;
                vertical-align: bottom;
                font-family: verdana;
                font-size: 12px;
                background-color:#EEEEEE;
            }
        </style>
        <script>
            
            function showCross(id) {
                document.getElementById("cross_" + id).style.display = "inline";
            }
            
            function hideCross(id) {
                document.getElementById("cross_" + id).style.display = "none";
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
            var search_num_to_delete = -1;

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
                    response = response.trim();
                    //alert(response);
                    
                    selected_search_number = parent.parent.selected_search_number;
                    if (search_num_to_delete == selected_search_number) {
                        parent.parent.selected_search_number = -1;
                        parent.parent.selected_search_tag_label = "";
                    }

                    parent.refreshAllSearchPanes(response);
                }
            }

        
            function deleteSearch (search_num) {
                delete_url = 'DeleteSearchServlet?search_number=' + search_num + "&analysis_name=<%=analysis_name%>&timestamp=" + Math.random();
                //delete_url = 'http://localhost:8080/VTBox/deleteSearch.jsp?search_number=1&analysis_name=abc'
                //alert(delete_url);
                makeGetRequest(delete_url);
                search_num_to_delete = search_num;
            }
            
        </script>
    </head>
    <body>
        
        <table id="srhTable" name="srhTable" height="50px" border="0px" style="position: absolute; top: 0px; left: 0px">
            <tr id="srhTableRow" name="srhTableRow">
                
                <%  for(int i = 0; i < search_strings.size(); i++) {     %>
                <td id='<%=i%>' onmouseover="showCross(<%=i%>)" onmouseout="hideCross(<%=i%>)">
                    <img name="cross_<%=i%>" id="cross_<%=i%>" class="cross" src="images/cross.png" width="18px" height="18px" onclick="deleteSearch(<%=i%>)"> 
                    <br>
                    <%=i%>
                </td>
                <%  }   %>
                
            </tr>
        </table>
        
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>