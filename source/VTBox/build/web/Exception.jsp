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
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="vtbox-main.css">
    </head>
    <body>
        <div class="exception_msg">
            <label><%=msg%></label>
        </div>
    </body>
    
</html>
