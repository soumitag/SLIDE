<%-- 
    Document   : createFeatureList
    Created on : May 31, 2017, 3:29:00 PM
    Author     : soumitag
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {    
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    
    String list_name = "";
    
    if (mode.equals("process")) {
    
        list_name = request.getParameter("list_name");
        list_name = list_name.trim();
        
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
        if (analysis.filterListMap.containsKey(list_name)) {
            String url = "createFeatureList.jsp?mode=name_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        } else {
            ArrayList <Integer> newFeatureList = new ArrayList <Integer> ();
            analysis.filterListMap.put(list_name, newFeatureList);
        }
    }
    
    if (mode.equals("name_error")) {
        list_name = request.getParameter("list_name");
    }
%>

<% if (mode.equals("input") || mode.equals("name_error")) {  %>
<html>
<head>
    
    <link rel="stylesheet" href="vtbox-main.css">
    <link rel="stylesheet" href="vtbox-tables.css">
    
    <script>
        
        function submitCreateRequest(){
            
            var value1 = document.getElementById("list_name").value;
            if(value1 == ""){
                alert('Please provide a list name.');
            } else {
                createFeatureList();
            }
            
        }
        
        function createFeatureList() {
            document.getElementById("mode").value = "process";
            document.getElementById("createFeatureListForm").setAttribute("action","createFeatureList.jsp");
            document.getElementById("createFeatureListForm").submit();
        }
        
    </script>
    
</head>

<body>
    
    <form name="createFeatureListForm" id="createFeatureListForm" method="get" action="">
    <table cellpadding="5" style="width: 100%;" border="0">
        
        <tr>
            <td height="75" align="center" colspan="2">
                <b><label>Create a New Feature List</label></b>
            </td>
        </tr>

        <% if (mode.equals("name_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>The name <%=list_name%> is already in use. Please specify another name.</label></b>
            </td>
        </tr>
        <% } %>
        
        <tr>
            <td>
                <b><label>Enter a Name for the New List</label></b>
            </td>
            <td>
                <input type="text" id="list_name" name="list_name" size="30">
                &nbsp;
                <button type="button" class="dropbtn" title="Create Feature Lists." onclick="submitCreateRequest();">Create</button>
                <input type="hidden" name="mode" id="mode" value="process">
                <input type="hidden" name="analysis_name" id="analysis_name" value="<%=analysis_name%>">
            </td>
        </tr>

    </table>
    </form>
    
</body>
</html>

<%  } else if (mode.equals("process")) {  %>
<html>
<script>
    parent.updateFeatureLists('<%=list_name%>', 1);
</script>

<body>
    <table cellpadding="5" style="width: 100%;" border="0">        
        <tr>
            <td height="75">
                Feature list <i><%=list_name%></i> created.
            </td>
        </tr>
    </table>
</body>
</html>
<%  }  %>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>