<%-- 
    Document   : go
    Created on : 18 Mar, 2017, 11:41:02 PM
    Author     : Soumita
--%>

<%@page import="structure.AnalysisContainer"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="searcher.GoObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="searcher.Searcher"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    
try {
    String analysis_name = request.getParameter("analysis_name");
    String id = (String)request.getParameter("go_id");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <GoObject> go_infos = analysis.searcher.processGOid(id);
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
            if(go_infos != null) {
                for (int g=0; g <go_infos.size();g++) {
                    GoObject go_info = go_infos.get(g);
        %>
        
        <table cellspacing="5" >
            <tr class="eh">
                <td >GO ID</td>                
                <td>&nbsp;&nbsp;</td>
                <td><b>ONTOLOGY</b></td>
            </tr>
            <tr>
                <td class="e" id="<%= go_info.goID %>" style="">GO:<%=go_info.goID %></td>
                <td>&nbsp;&nbsp;</td>
                <td class="e" id="<%= go_info.ontology %>" style=""><%= go_info.ontology.toUpperCase() %></td>                
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
                      
            <tr class="eh">
                <td colspan="3">GO TERM</td>
            </tr>
            <tr>
                <td colspan= "3" class="e" id="<%= go_info.goTerm %>" style=""><%= go_info.goTerm %></td>
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
            <tr class="eh">
                <td colspan="3">DEFINITION</td>
            </tr>
            <tr>
                <% if (go_info.definition != null) {%>
                <td colspan= "3" class="e" id="<%= go_info.definition %>" style=""><%= go_info.definition %></td>
                <% } else { %>
                 <td colspan= "3" class="e" id="<%= go_info.definition %>" style=""><%= "Definition not found" %></td>
                <% } %>
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
            <tr class="eh">
                <td colspan="3">EVIDENCES</td>
            </tr>
             </tr>
                <%  String evidence_str = ""; %>
                <%  for (int i = 0; i < go_info.evidences.size() - 1; i++) { 
                        evidence_str += go_info.evidences.get(i).toUpperCase() + ", "; 
                    }
                    evidence_str += go_info.evidences.get(go_info.evidences.size()-1).toUpperCase();
                %>
                
                <td colspan= "3" class="e" id="<%= evidence_str %>" style=""><%= evidence_str %></td>
                 
            <tr>
            <tr>
            <td colspan="3"></td>
            </tr>
            
            <tr class="eh">
                <td>ENTREZ IDs</td>
                <td>GENESYMBOLS</td>
                <td>&nbsp;</td>
            </tr>
                <% for (int j = 0; j < go_info.entrez_ids.size(); j++) { %>
            <tr>
                <td class="e" id="<%= go_info.entrez_ids.get(j) %>" style=""><%= go_info.entrez_ids.get(j) %></td>
                <td class="e" id="<%= go_info.genesymbols.get(j) %>" style=""><%= go_info.genesymbols.get(j).toUpperCase() %> </td>
                <td>&nbsp;</td>
                <% } %>                
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