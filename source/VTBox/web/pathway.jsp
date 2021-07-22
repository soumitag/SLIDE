<%-- 
    Document   : pathway
    Created on : 18 Mar, 2017, 11:40:29 PM
    Author     : Soumita
--%>

<%@page import="structure.AnalysisContainer"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.StringTokenizer"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="searcher.Searcher, searcher.GeneObject, searcher.PathwayObject, searcher.GoObject, java.util.ArrayList, java.util.HashMap;"%>
<!DOCTYPE html>
<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    String id = (String)request.getParameter("pathway_id");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <PathwayObject> paths = ((Searcher)analysis.searcher).processPathid(id);
%>
    
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <style>
        
        .e {
            text-align:left;
            padding: 2px; 
            background-color:#FDFDFD;
            margin-left: 10px;
            margin-top: 3px;
            margin-bottom: 3px;
            font-family: verdana;
            font-size: 12px;
        }
        
        .eh {
            text-align:left;
            padding: 2px; 
            background-color:#FDFDFD;
            margin-left: 10px;
            margin-top: 3px;
            margin-bottom: 3px;
            font-family: verdana;
            font-size: 12px;
            font-weight: bold;
        }
        
        .e1 {
            text-align:center;
            cursor:pointer;
            border-radius: 7px; 
            border: 1px solid #CCCCCC; 
            padding: 2px; 
            float: left; 
            width: 100px;
            background-color:#FDFDFD;
            margin-left: 10px;
            margin-top: 3px;
            margin-bottom: 3px;
            font-family: verdana;
            font-size: 12px;
        }
        
        .ebad {
            text-align:center;
            cursor:pointer;
            border-radius: 7px; 
            border: 1px solid #CCCCCC; 
            padding: 2px; 
            float: left; 
            width: 100px;
            background-color:#FDFDFD;
            margin-left: 10px;
            margin-top: 3px;
            margin-bottom: 3px;
            font-family: verdana;
            font-size: 12px;
            color: #AAAAAA;
        }
        
    </style>

    <body>
        
               
        
        
        <% 
            if(paths != null) {
                for (int g=0; g <paths.size();g++) {
                    PathwayObject path = paths.get(g);
        %>
        <table cellspacing="5" >
            <tr class="eh">
                <td >PATHWAY ID</td>                
                <td>&nbsp;&nbsp;</td>
                <td><b>SOURCE</b></td>
            </tr>
            <tr>
                <td class="e" id="<%= path._id %>" style=""><%=path._id %></td>
                <td>&nbsp;&nbsp;</td>
                <td class="e" id="<%= path.source %>" style=""><%= path.source.toUpperCase() %></td>                
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
                      
            <tr class="eh">
                <td colspan="3">PATHWAY NAME</td>
            </tr>
            <tr>
                <td colspan= "3" class="e" id="<%= path.pathway %>" style=""><%= path.pathway %></td>
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
            <tr class="eh">
                <td>ENTREZ IDs</td>
                <td>GENESYMBOLS</td>
                <td>&nbsp;</td>
            </tr>
                <% for (int i = 0; i < path.entrez_ids.size(); i++) { %>
                <% if (path.genesymbols.get(i).contains("_genesymbol_not_found")) { 
                    StringTokenizer st = new StringTokenizer(path.genesymbols.get(i), "_");
                    String genesym_str = "";
                    while(st.hasMoreTokens()) {
                        genesym_str += st.nextToken() + " ";
                    } %>
                
                <tr>
                    <td class="e" id="<%= path.entrez_ids.get(i) %>" style=""><%= path.entrez_ids.get(i) %></td>
                    <td class="e" id="<%= path.genesymbols.get(i) %>" style=""><%= genesym_str.toUpperCase() %> </td>
                </tr>
                <% } else { %>
                <tr>
                <td class="e" id="<%= path.entrez_ids.get(i) %>" style=""><%= path.entrez_ids.get(i) %></td>
                <td class="e" id="<%= path.genesymbols.get(i) %>" style=""><%= path.genesymbols.get(i).toUpperCase() %> </td>
                <td>&nbsp;</td>
                <% } 
                } %>                
                </tr>
            
        </table>
        
        <%  
                }
            }
        %>
      
    </body>
    
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>