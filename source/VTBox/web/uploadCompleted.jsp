<%-- 
    Document   : uploadCompleted
    Created on : 20 Aug, 2017, 9:13:59 PM
    Author     : Soumita
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page import="utils.UserInputParser"%>
<%@page import="java.io.IOException"%>
<%@page import="vtbase.DataParsingException"%>
<%@page import="utils.Utils"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="utils.SessionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String filename = request.getParameter("filename");
    String status = request.getParameter("status");
    String type = request.getParameter("type");
    String analysis_name = request.getParameter("analysis_name");
    String delimval = request.getParameter("delimval");
    
    String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
        
    String fileDelimiter = Utils.getDelimiter(delimval);
    
    String[] column_names = null;
    
    if (type.equalsIgnoreCase("data") && status.equals("")) {
        
        try {
            column_names = SessionManager.getColumnHeaders(installPath, 
                                                 request.getSession().getId(), 
                                                 analysis_name, 
                                                 filename, 
                                                 type,
                                                 true,
                                                 fileDelimiter);
            
            String key;
            HashMap <String, Boolean> temp = new HashMap <String, Boolean> ();
            for (int i=0; i<column_names.length; i++) {
                key = column_names[i].trim().toLowerCase();
                if(!temp.containsKey(key)) {
                    temp.put(key, new Boolean(true));
                } else {
                    status = "Upload Failed! Column headers must be unique. Header \'" + key + "\' found twice.";
                    break;
                }
            }
            
        } catch (DataParsingException dpe) {
            
            status = "Upload Failed! " + dpe.getMessage();
            
        } catch (IOException ioe) {
            
            status = "Upload Failed! Unable to read file. Please provide a delimited file in tsv, csv or txt format.";
            
        }
        
    } else if (type.equalsIgnoreCase("mapping") && status.equals("")) {
        
        try {
            boolean hasGroupingFactor = request.getParameter("hasGroupingFactor").equalsIgnoreCase("yes");
            boolean hasTwoGroupingFactors = request.getParameter("hasTwoGroupingFactors").equalsIgnoreCase("yes");
            String data_filename = request.getParameter("data_filename");
            String data_delimval = request.getParameter("data_fileDelimiter");
            
            String data_fileDelimiter = Utils.getDelimiter(data_delimval);
            
            column_names = SessionManager.getColumnHeaders(installPath, 
                                                 request.getSession().getId(), 
                                                 analysis_name, 
                                                 data_filename, 
                                                 "data",
                                                 true,
                                                 data_fileDelimiter);
            
            String qfilename = installPath + File.separator + 
                               "temp" + File.separator + 
                               request.getSession().getId() + File.separator + 
                               analysis_name + "_mapping_" + filename;
        
            int nGroupFactors = 0;
            if (hasGroupingFactor) {
                if (hasTwoGroupingFactors) {
                    nGroupFactors = 2;
                } else {
                    nGroupFactors = 1;
                }
            }
            UserInputParser.parseSampleMappingsFile(qfilename, fileDelimiter, nGroupFactors, column_names);
            
        } catch (DataParsingException dpe) {
            
            status = "Upload Failed! " + dpe.getMessage();
            
        } catch (IOException ioe) {
            
            status = "Upload Failed! Unable to read file.";
            
        }
        
    }
    System.out.println(filename);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Upload Completed</title>
    </head>
    <script>
        function notifyUploadCompletion () {
        <% if(type.equalsIgnoreCase("data"))  {     %>
            var col_names = new Array();
            <% if (status.equals("")) { %>
                <% for (int i=0; i<column_names.length; i++) { %>
                    col_names.push('<%=column_names[i]%>');
                <% } %>
            <% } %>
            parent.uploadCompletionData("<%=status%>", '<%=filename%>', col_names);
        <% } else { %>
            parent.uploadCompletionMapping("<%=status%>", '<%=filename%>');
        <% } %>
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