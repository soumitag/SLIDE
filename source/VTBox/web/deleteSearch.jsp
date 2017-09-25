<%-- 
    Document   : deleteSearch
    Created on : 20 May, 2017, 1:59:55 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="searcher.Searcher"%>
<%@page import="structure.CompactSearchResultContainer"%>
<%@page import="java.util.ArrayList"%>

<%
    
try {
    
    int search_number = Integer.parseInt(request.getParameter("search_number"));
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    search_results.remove(search_number);
    
    ArrayList <String> search_strings = analysis.search_strings;
    search_strings.remove(search_number);
%>

<%=search_strings.size()%>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>