<%-- 
    Document   : Exception
    Created on : Sep 20, 2017, 2:34:24 PM
    Author     : abhik
--%>

<%
    String msg = request.getParameter("msg");
    if (msg == null) {
        msg = "Oops! Looks like something went wrong. Please retry the last action with different parameters.";
    }
    
    String exception_type = request.getParameter("type");
    if (exception_type == null) {
        exception_type = "";
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="vtbox-main.css">
        <script>
            function reportClusteringError() {
                alert("Hierarchical clustering could not be performed for the data. Please select a different set of data transformation / clustering parameters and try again. This issue could be because the selected transformations produced NaNs or Infs. Note that some data transformations (like aggresive data clippping) can result in rows with all zero values / zero standard deviation. Distance functions such as cosine and correlation cannot be computed for such rows.");
                parent.showGlobal();
            }
        </script>
        
    </head>
    <% if (exception_type.equals("clustering_error")) { %>
    <body onload="reportClusteringError()">
    <% } else { %>
    <body>
    <% } %>
        <div class="exception_msg">
            <label><%=msg%></label>
        </div>
    </body>
    
</html>
