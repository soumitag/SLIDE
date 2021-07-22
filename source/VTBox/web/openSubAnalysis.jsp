<%-- 
    Document   : openSubAnalysis
    Created on : Jun 16, 2017, 9:27:23 PM
    Author     : soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    
    String delete_analysis_name = request.getParameter("delete_analysis_name");
    if (mode.equals("delete")) {
        session.removeAttribute(delete_analysis_name);
    }
    
    ArrayList <String> sub_analyses_names = new ArrayList <String> ();
    Enumeration attributeNames = session.getAttributeNames();
    while(attributeNames.hasMoreElements()) {
        String sub_analysis_name = (String) attributeNames.nextElement();
        if(session.getAttribute(sub_analysis_name) instanceof structure.AnalysisContainer)  {
            if (!sub_analysis_name.equalsIgnoreCase(analysis_name)) {
                sub_analyses_names.add(sub_analysis_name);
            }
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        
        <link rel="stylesheet" href="vtbox-main.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title> Open / Delete Sub-Analysis </title>
        
        <style>

            table {
                font-family: verdana;
                border-collapse: collapse;
                width: 100%;
            }

            td, th {
                border: 1px solid #dddddd;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color: #efefef;
            }

        </style>

        
    </head>
    <body>
        <table cellpadding="5" style="width: 100%;" border="0">
            
            <%  if (mode.equals("delete")) { %>
            <tr>
                <td width="100%" height="35" align="center" colspan="3">
                    <b><label>Sub-analysis <i><%=delete_analysis_name%></i> deleted.</label></b>
                </td>
            </tr>
            <% } else { %>
            <tr>
                <td width="100%" height="35" align="center" colspan="3">
                    &nbsp;
                </td>
            </tr>
            <% } %>
            
            <%  for (int i=0; i<sub_analyses_names.size(); i++)  {    %>
        
            <tr>
                <td width="50%" height="35" align="center">
                    <b><label><%=sub_analyses_names.get(i)%></label></b>
                </td>
                <td align="center">
                    <button name="openSub" class="dropbtn" id="openSub" onclick="window.open('<%=base_url%>displayHome.jsp?analysis_name=<%=sub_analyses_names.get(i)%>')">Open</button>
                </td>
                <td align="center">
                    <button name="deleteSub" class="dropbtn" id="deleteSub" onclick="location.href='<%=base_url%>openSubAnalysis.jsp?mode=delete&delete_analysis_name=<%=sub_analyses_names.get(i)%>&analysis_name=<%=analysis_name%>'">Delete</button>
                </td>
            </tr>
        
            <%  }   %>
            
            <tr>
                <td width="100%" height="35" align="center" colspan="3">
                    To save a sub-analysis open it and click the "Save Analysis" button.
                </td>
            </tr>
            
        </table>
    </body>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>