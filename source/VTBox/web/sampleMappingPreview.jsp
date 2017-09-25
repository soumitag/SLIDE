<%-- 
    Document   : sampleMappingPreview
    Created on : Apr 20, 2017, 5:34:36 PM
    Author     : soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String metafilename = request.getParameter("file");
    
    BufferedReader br = new BufferedReader(new FileReader(metafilename));
    String[] lineData = null;
    String line;
%>

<html>
    <head>
        <title>Sample Column Mapping Preview</title>
        <style>
            table {
                font-family: arial, sans-serif;
                border-collapse: collapse;
                width: 100%;
            }

            td, th {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color: #dddddd;
            }
        </style>
    </head>
    <body>
        <table style="border:1px solid black">
            <tr>
                <th>Column Name</th>
                <th>Sample Group</th>
                <th>Time Point</th>
            </tr>
    <%
        while ((line = br.readLine()) != null) {

            if (line.startsWith("#") || line.equals("")) {

                continue;

            } else {

                lineData = line.split("\t");

                String datacol_header = lineData[0].trim();
                String sample_groupname = lineData[1].trim();
                String timestamp = lineData[2].trim();
    %>
    
        <tr>
            <td><%=datacol_header%></td>
            <td><%=sample_groupname%></td>
            <td><%=timestamp%></td>
        </tr>
    
    <%
            }
        }
    %>
        </table>
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>