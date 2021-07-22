<%-- 
    Document   : visualizationHome
    Created on : 18 Feb, 2017, 12:55:24 PM
    Author     : Soumita
--%>

<%@page import="graphics.layouts.VizualizationHomeLayout"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {
    String analysis_name = request.getParameter("analysis_name");
    String isDemo = request.getParameter("isDemo");
    String load_type = request.getParameter("load_type");
    if (load_type != null && (load_type.equalsIgnoreCase("reopen") || load_type.equalsIgnoreCase("file"))) {
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        analysis.hac.clearCache();
    }
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    VizualizationHomeLayout viz_layout = analysis.visualization_params.viz_layout;
%>
<html>
    <head>
        <title><%=analysis_name%></title>
        <script>
            function toggleSelectionPanel() {
                var sp = document.getElementById("selectionPanel");
                var sph = document.getElementById("spHandle");
                if (sp.style.display === 'inline') {
                    sp.style.display = 'none';
                    sph.innerHTML = 'Show Control Panel';
                } else {
                    sp.style.display = 'inline';
                    sph.innerHTML = 'Hide Control Panel';
                }
             }
        </script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        
        <div style="position: absolute; top: 0px; left: 0px; width: 100%; height: 99%"> 
            <div style="position: relative; top: 0px; left: 0px; width: 300px; height: 100%; float:left; border-style: solid; border-color: #E1E1E1"> 
                <iframe name="selectionPanel" id="selectionPanel" src="selectionPanel.jsp?analysis_name=<%=analysis_name%>&load_type=<%=load_type%>&isDemo=<%=isDemo%>" marginwidth="0" height="100%" width="300" frameBorder="0" style="display: inline; position: relative; top: 0px; left: 0px"></iframe>
            </div>
            <div style="position: relative; top: 0px; left: 0px; width: calc(100% - 310px); height: 100%; float:left;"> 
                <iframe name="visualizationPanel" id="visualizationPanel" src="visualizationHome.jsp?analysis_name=<%=analysis_name%>" marginwidth="0" height="100%" width="100%" frameBorder="0"></iframe>
            </div>
        </div>
        
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>