<%-- 
    Document   : viewFeatureList
    Created on : 2 Sep, 2017, 12:07:58 AM
    Author     : Soumita
--%>

<%@page import="searcher.GeneObject"%>
<%@page import="structure.MetaData"%>
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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Feature List</title>
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">
        <script>
            function backtoList(){
                //alert("21");
                window.location.href = "viewDeleteFeatureList.jsp?analysis_name=<%=analysis_name%>";
            }
        
        </script>

    </head>
    <body>
        <form name="viewFeatureListForm" id="viewFeatureListForm" >
            <button type="button" class="dropbtn" title="Back to Feature List" id="btnBack" onclick="backtoList()">Back</button>
            <table cellpadding="5" style="width: 100%;" border="0">
                
                <% 
                    HashMap <String, ArrayList <Integer>> filterList = analysis.filterListMap;
                    ArrayList <Integer> filterListIndices = filterList.get(feature_list_name);
                %>
                <tr>
                    <td height="20" align="left">
                    <b><label><%=feature_list_name%></label></b>
                    </td>
                </tr>
                <% for (int i=0; i < filterListIndices.size(); i++){ %>
                <tr>
                    <% 
                       
                       String genesymbol = analysis.database.features.get(filterListIndices.get(i)).identifier;
                       String entrez = analysis.database.features.get(filterListIndices.get(i)).entrez;
                       
                       /*
                       int index = filterListIndices.get(i);
                       String entrez = analysis.database.metadata.getFeature(index, GeneObject.ENTREZ);
                       String genesymbol = analysis.database.metadata.getFeature(index, GeneObject.GENESYMBOL);
                       */
                    %>
                    <td height="20" align="left">
                        <label><%=genesymbol%>, <%=entrez%></label>
                    </td>
                    
                </tr>
                <% } %>
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