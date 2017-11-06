<%-- 
    Document   : sysreq
    Created on : 4 Sep, 2017, 10:33:05 AM
    Author     : soumitag
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="vtbox.SessionUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
try {
    
    String browser_app = request.getParameter("browser_app");
    String browser_res_w = request.getParameter("browser_res_w");
    String browser_res_h = request.getParameter("browser_res_h");
    
    int browser_resolution_x = Integer.parseInt(browser_res_w);
    int browser_resolution_y = Integer.parseInt(browser_res_h);
    
    ArrayList <String> supported_browsers = new ArrayList <String> ();
    supported_browsers.add("Internet Explorer".toUpperCase());
%>

    <html>
        <head>
            <title>System Specifications</title>
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
        
        <%
                     
        %>
    
    <body>
        <table>
        <tr>
            <td colspan="2">       
            
            <label class="sys_msg">
                <% if (browser_resolution_x < 1919 || browser_resolution_y < 1079) { %>
                    SLIDE works best at a resolution of 1920&times;1040 or higher. 
                    Your current browser resolution is <%=browser_resolution_x%>&times;<%=browser_resolution_y%>. 
                    To get the best performance try changing your browser's zoom.
                    Note that changing the zoom will affect all open tabs in this browser window.
                <% } %>
                <br>
            </label>
            </td>
        </tr>
        <tr>
            <td colspan="2">
            &nbsp;
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button class="dropbtn" id="btnok" title="OK" onclick="closeButton()">&nbsp;&nbsp;Ok&nbsp;&nbsp;</button>
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