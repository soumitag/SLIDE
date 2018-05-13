<%-- 
    Document   : inputPreview
    Created on : 6 Jan, 2017, 1:49:16 PM
    Author     : soumitag
--%>

<%@page import="utils.FileHandler"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="utils.SessionManager"%>
<%@page import="utils.ReadConfig"%>
<%@page import="utils.Utils"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
        
try {
        
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Preview</title>
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
    
    <%
        
        String filename_in = request.getParameter("file");
        String analysis_name = request.getParameter("analysis_name"); 
        String type = request.getParameter("upload_type"); 
        
        //String tempFolder = pageContext.getServletContext().getRealPath("") + File.separator + "temp" + File.separator + request.getSession().getId();
        String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
        String tempFolder = installPath + File.separator + "temp" + File.separator + request.getSession().getId();
        
        String filename =   tempFolder + File.separator + analysis_name + "_" + type + "_" + filename_in;
        //String filename = tempFolder + File.separator + analysis_name + "_" + filename_in;
        
        String headerchk = request.getParameter("head");
        boolean hasHeader = true;
        if(headerchk == null || headerchk.equals("off") || headerchk.equals("false")){
            hasHeader = false;
        } else {
            hasHeader = true;
        }
      
        String delimval = request.getParameter("delim");
        String fileDelimiter = Utils.getDelimiter(delimval);
                
    %>
    
    <%
        String[] colheaders = null;
        String[][] data = FileHandler.loadDelimData(filename, fileDelimiter, false, 12);
        int start_row = 0;
        if (hasHeader) {
            colheaders = data[0];
            start_row = 1;
        }
        int nCols = data[0].length;
    %>
    
    <body>
        <table style="border:1px solid black">
            
            <tr>
                <% for(int j = 0; j < nCols; j++) { %>
                    <% if (hasHeader) { %>
                            <th><%=colheaders[j]%></th>
                    <%} else { %>
                            <th><%=("Column_" + j)%></th>
                    <% } %>
                <% } %>
            </tr>
            
            <% for(int i = start_row; i < start_row + 11; i++) { %>
                <tr>
                    <% for(int j = 0; j < nCols; j++) { %>
                        <td><%=data[i][j]%></td>
                    <% } %>
                </tr>
            <% } %>
            
        </table>
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>