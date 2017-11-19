<%-- 
    Document   : viewDeleteFeatureList
    Created on : 1 Sep, 2017, 11:15:03 PM
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
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View/Delete Feature List</title>
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">
        <script>
            function viewFeatureList(list_name){
                window.location.href = "viewFeatureList.jsp?feature_list_name=" + list_name + "&analysis_name=<%=analysis_name%>";
            }
            
            function deleteFeatureList(list_name){
                window.location.href = "confirmFeatureListDeletion.jsp?feature_list_name=" + list_name + "&analysis_name=<%=analysis_name%>";
            }
        
        </script>
    </head>
    <body>
        <form name="viewDeleteFilteredListForm" id="viewDeleteFilteredListForm" >
            <table cellpadding="5" style="width: 100%;" border="0">
                <tr>
                <td height="25" align="center">
                    <b><label>Feature List</label></b>
                </td>
                 <td height="25" align="center">
                    <b><label>View/Delete</label></b>
                </td>
                </tr>
                
                <% 
                    HashMap <String, ArrayList <Integer>> filterList = analysis.filterListMap;
                    Iterator it = filterList.entrySet().iterator();
                    while (it.hasNext()) { 
                        String featurelist_name = ((Map.Entry)it.next()).getKey().toString();
                %>
                <tr>
                <td height="25" align="center">
                    <label><%=featurelist_name%></label>
                    
                </td>
                 <td height="25" align="center">
                    <button type="button" class="dropbtn" title="View Feature List" id="<%=featurelist_name%>_view_btn" onclick="viewFeatureList('<%=featurelist_name%>')">View</button>
                    &nbsp;
                    <button type="button" class="dropbtn" title="Delete Feature List" id="<%=featurelist_name%>_del_btn" onclick="deleteFeatureList('<%=featurelist_name%>')">Delete</button>
                </td>
                </tr>
                <% }  %>
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