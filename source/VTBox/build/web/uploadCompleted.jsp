<%-- 
    Document   : uploadCompleted
    Created on : 20 Aug, 2017, 9:13:59 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String status = request.getParameter("status");
    String data_filename = request.getParameter("data_filename");
    String mapping_filename = request.getParameter("mapping_filename");
    System.out.println(data_filename);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Upload Completed</title>
    </head>
    <script>
        function notifyUploadCompletion () {
            parent.uploadCompletion('<%=status%>', '<%=data_filename%>', '<%=mapping_filename%>');
        }
    </script>
    <body onload="notifyUploadCompletion()">
    </body>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>