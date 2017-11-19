<%-- 
    Document   : confirmFeatureListDeletion.jsp
    Created on : 3 Sep, 2017, 1:18:28 PM
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
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Feature List Deletion</title>
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">
        <script>
            function backtoView(){
                window.location.href = "viewDeleteFeatureList.jsp?analysis_name=<%=analysis_name%>";
            }
            
            function confirmFeatureListDeletion(){
                //alert("Please");
                window.location.href = "deleteFeatureList.jsp?analysis_name=<%=analysis_name%>&feature_list_name=<%=feature_list_name%>";
            }
        
        </script>
    </head>
    <body>
        <form name="confirmFeatureListDeletionForm" id="confirmFeatureListDeletionForm" >
            <table cellpadding="5" style="width: 100%;" border="0">
                <tr>
                    <td height="25" align="center">
                        <label>Do you want to delete <%=feature_list_name%> ? </label>
                    </td>
                </tr>
                <tr>
                    <td height="25" align="center">
                        <button type="button" class="dropbtn" title="Yes delete feature list" id="btnYes" onclick="confirmFeatureListDeletion()">Yes</button>
                        &nbsp;
                        <button type="button" class="dropbtn" title="Back to Feature List" id="btnNo" onclick="backtoView()">No</button>
                    </td>
                </tr>
                
            </table>
        </form>
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>