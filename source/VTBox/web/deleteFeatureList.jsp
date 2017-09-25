<%-- 
    Document   : deleteFeatureList
    Created on : 3 Sep, 2017, 1:13:27 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    String feature_list_name = request.getParameter("feature_list_name");
    
    analysis.filterListMap.remove(feature_list_name);

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Feature List</title>
        <link rel="stylesheet" href="vtbox-main.css">
        <script>
            function backtoList(){
                window.location.href = "viewDeleteFeatureList.jsp?analysis_name=<%=analysis_name%>";
            }
        </script>
    </head>
    <body>
        <label>
            <%=feature_list_name%> has been deleted.
        </label>
        <button type="button" class="dropbtn" title="Back to Feature List" id="btnBack" onclick="backtoList()">Back</button>
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>