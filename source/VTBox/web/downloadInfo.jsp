<%-- 
    Document   : downloadStarted
    Created on : 25 Sep, 2017, 4:32:36 PM
    Author     : Soumita
--%>
<%@page import="vtbox.SessionUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Saving Analysis</title>
        <link rel="stylesheet" href="vtbox-main.css">
        <script>
                function closeButton(){
                    var modal = parent.document.getElementById('myModal');
                    var btnval = document.getElementById('btnok');
                    window.onclick = function(event){
                        if(event.target == btnval)
                            modal.style.display = "none";
                    }
                }
        </script>
    </head>
    <body>
        <table>
        <tr>
            <td>
                <% if (mode.equals("start")) { %>
                <label class="sys_msg"> 
                    Preparing <i><%=analysis_name%>.slide </i> file for download. 
                    Choose the <i>Save</i> option when prompted by your browser. 
                    Meanwhile, you can close this window and continue using SLIDE.
                    <br>
                    <br>
                </label>
                <% } else if (mode.equals("completed")) { %>
                
                <% } %>
            </td>
        </tr>
        <tr>
            <td colspan="2">
            &nbsp;
            </td>
        </tr>
        <tr>
            <td align="center">
                <button class="dropbtn" id="btnok" title="OK" onclick="closeButton()">&nbsp;&nbsp;OK&nbsp;&nbsp;</button>
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