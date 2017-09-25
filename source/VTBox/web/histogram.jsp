<%-- 
    Document   : histogram
    Created on : 19 Feb, 2017, 8:24:54 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="graphics.Heatmap"%>
<%@page import="utils.Logger"%>
<%@page import="structure.Data"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    
    double y_label_width = 60.0;
    double x_label_height = 80.0;
    double histWidth = 400.0;
    double histHeight = 200.0;
    
    double imgWidth = histWidth + y_label_width;
    double imgHeight = histHeight + x_label_height;
%>

<!DOCTYPE html>
<html>
    
    <body>
        
        <svg width="<%=imgWidth%>" height="<%=imgHeight%>" xmlns="http://www.w3.org/2000/svg">
        
        <jsp:include page="/WEB-INF/jspf/histogram_fragment.jspf" flush="true">
            <jsp:param name="analysis_name" value="<%=analysis_name%>" />
            <jsp:param name="y_offset" value="10.0" />
        </jsp:include>
        
        </svg>
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>