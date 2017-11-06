<%-- 
    Document   : hierarchicalClusteringFeedback
    Created on : 6 Nov, 2017, 12:01:14 AM
    Author     : Soumita
--%>

<%
    String is_clustering = request.getParameter("is_clustering");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<link rel="stylesheet" href="vtbox-main.css">-->
        <style>
            .tab1 {
                margin:auto;
            }
            
            .tab1 td{
                text-align:center;
            }
            
            .exception_msg {
                text-align: center;
                font-family: verdana;
                font-size: 14px;
                font-weight: normal;
                font-style: italic;
                color: #BBB;
                text-align: center;
                position: absolute;
                width: 100%;
                top: 50px;
            }

        </style>
    </head>
    <body>
        <table border="0" style="width: 285px" class="tab1">
            <tr>
                <td>
                    <img src="images/loader1.gif" height="207" width="285" border="0" />
                </td>
            </tr>
            <tr>
                <td class="exception_msg" style="top: 130px; width: 285px">   
                    <% if (is_clustering.equalsIgnoreCase("true")) { %>
                    <!--<div class="exception_msg" style="top: 130px">
                    <label>-->
                        Hierarchical Clustering is running.  
                        For large datasets this might take several minutes.
                        Please do not close this window.
                    <!--</label>
                    </div>-->
                    <% } %>
                </td>                
            </tr>
        </table>
        
    </body>
</html>
