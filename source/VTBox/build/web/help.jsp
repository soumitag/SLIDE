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
        <title>SLIDE Help</title>
        <script>
			
			function giveManual() {
				var win = window.open("https://github.com/soumitag/SLIDE/raw/master/application/slide/SLIDE_Users_Manual.pdf", '_blank');
				win.focus();
			}
			
			function giveDemoDataFile() {
				var win = window.open("https://github.com/soumitag/SLIDE/blob/master/data/GSE42641_data_formatted_final_small_114.txt", '_blank');
				win.focus();
			}
			
			function giveDemoMAppingFile() {
				var win = window.open("https://github.com/soumitag/SLIDE/blob/master/data/GSE42638_SampleMappings.txt", '_blank');
				win.focus();
			}
			
			function takeToIssues() {
				var win = window.open("https://github.com/soumitag/SLIDE/issues", '_blank');
				win.focus();
			}
			
		</script>
                <script> 
                if (top != window) {
                    top.location = window.location;
            }
        </script>
    </head>
    <body>
        <table class="input" style="width: 100%">
        <tr>
            <td>
                <button class="dropbtn" id="download_manual" title="OK" onclick="giveManual()">Download User Manual</button>
            </td>
        </tr>
        <tr>
            <td>
				<button class="dropbtn" id="download_manual" title="OK" onclick="takeToIssues()">Report An Issue</button>
            </td>
        </tr>
        <tr>
            <td>
				<button class="dropbtn" id="download_manual" title="OK" onclick="giveDemoDataFile()">Download Example Data File</button>
            </td>
        </tr>
		<tr>
            <td>
				<button class="dropbtn" id="download_manual" title="OK" onclick="giveDemoMAppingFile()">Download Example Sample Mapping File</button>
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