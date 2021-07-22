<%-- 
    Document   : objectInfo.jsp
    Created on : Sep 22, 2016, 12:38:40 AM
    Author     : SOUMITA GHOSH
--%>

<%@page import="structure.AnalysisContainer"%>
<%@page import="vtbox.SessionUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="searcher.Searcher, searcher.GeneObject, searcher.PathwayObject, searcher.GoObject, java.util.ArrayList, java.util.HashMap;"%>
<!DOCTYPE html>
<%
    
try {
    
    String entrez_id = (String)request.getParameter("entrez_id");
    
    if (entrez_id == null || entrez_id.equals("")) {
        String msg = "No feature selected. <br> Select features in visualization panels to display details here.";
        getServletContext().getRequestDispatcher("/Exception.jsp?msg=" + msg).forward(request, response);
        return;
    }
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <GeneObject> genes = ((Searcher)analysis.searcher).processEntrezidGeneSymbol(entrez_id);
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
            if(genes != null) {
                for (int g=0; g<genes.size();g++) {
                    GeneObject gene = genes.get(g);
        %>
        <table cellspacing="5" >
            <tr class="eh">
                <td >ENTREZ ID</td>                
                <td>&nbsp;&nbsp;</td>
                <td><b> GENE SYMBOL</b></td>
            </tr>
            <tr>
                <td class="e" id="<%= gene.entrez_id %>" style=""><%=gene.entrez_id%></td>
                <td>&nbsp;&nbsp;</td>
                <td class="e" id="<%= gene.gene_identifier %>" style=""><%=gene.gene_identifier.toUpperCase() %></td>                
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
                      
            <tr class="eh">
                <td colspan="3">ALIASES</td>
            </tr>
            <tr>
                <%  String alias_str = ""; %>
                <%  for (int i = 0; i < gene.aliases.size()-1; i++) { 
                        alias_str += gene.aliases.get(i) + ", "; 
                    } 
                    alias_str += gene.aliases.get(gene.aliases.size()-1);
                %>
                <td colspan= "3" class="e" id="<%=alias_str %>" style=""><%=alias_str.toUpperCase() %></td>
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
            <tr class="eh">
               <td colspan="3">GENE ONTOLOGIES</td>
            </tr>
                <% for (int i = 0; i < gene.goTerms.size(); i++) { %>
            <tr>
                <td colspan="3" class="e" id="<%= gene.goTerms.get(i).goID %>" style=""><%=gene.goTerms.get(i).goTerm  + "\t" %> <b> (GO:<%=gene.goTerms.get(i).goID  + ") \t" %> [<%=gene.goTerms.get(i).ontology.toUpperCase() + "] \t" %> </b></td>
                <% } %>                
            </tr>
            <tr>
            <td colspan="3"></td>
            </tr>
            <tr class="eh">
                <td colspan="3">PATHWAYS</td>
            </tr>
                <% for (int i = 0; i < gene.pathways.size(); i++) { %>
            <tr>
                <td colspan="3" class="e" id="<%= gene.pathways.get(i)._id %>" style=""><%=gene.pathways.get(i).pathway + "\t" %> <b>(<%=gene.pathways.get(i)._id + ") \t" %> [<%=gene.pathways.get(i).source.toUpperCase() + "] \t" %> </b></td>
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