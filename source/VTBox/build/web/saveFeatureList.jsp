<%-- 
    Document   : saveFeatureList
    Created on : 28 Aug, 2017, 9:04:09 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
try {
    
    String mode = request.getParameter("mode");
    
    String filename = "";
    if (mode.equals("save")) {
        filename = request.getParameter("filename");
        String delimval = request.getParameter("delimiter");
        
        String delimiter = null;
        if (delimval.equals("lineS")) {
            delimiter = "line";
        } else if (delimval.equals("commaS")) {
            delimiter = ",";
        } else if (delimval.equals("tabS")) {
            delimiter = "\t";
        } else if (delimval.equals("spaceS")) {
            delimiter = " ";
        } else if (delimval.equals("semiS")) {
            delimiter = ";";
        } else if (delimval.equals("hyphenS")) {
            delimiter = "-";
        }
        
        
    }
%>
<!DOCTYPE html>
<html>

<% if (mode.equals("input")) {  %>
<%
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
%>
    <head>
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">
    </head>
    <body>
        
        <script>

            function submitSaveFeatureListRequest_3(){
                
                var e1 = document.getElementById("list_name");
                var v1 = e1.options[e1.selectedIndex].value;
                var e3 = document.getElementById("delimS");
                var v3 = e3.options[e3.selectedIndex].value;
                
                if(v1 == "" || v1 == "dashS" || v3 == "" || v3 == "hyphenS"){
                    alert('Please select a list and delimiter to save.');
                } else {
                    document.getElementById("mode").value = "save";
                    document.getElementById("filename").value = v1;
                    document.getElementById("delimiter").value = v3;
                    document.getElementById("saveFeatureListForm").submit();
                }
                
            }

        </script>
        
        <form name="saveFeatureListForm" id="saveFeatureListForm" method="get" action="SerializeFeatureList">
        <table>
            <tr>
                <td>
                    <b><label>Select Feature List To Save</label></b>
                </td>
                <td colspan="2">
                    <select name="list_name" id="list_name">
                        <option value="dashS" >-</option>
                        <% Iterator it = analysis.filterListMap.entrySet().iterator();  %>
                        <% while (it.hasNext()) {   %>
                            <% String featurelist_name = ((Map.Entry)it.next()).getKey().toString(); %>
                            <option value="<%=featurelist_name%>" ><%=featurelist_name%></option>
                        <% }    %>
                    </select>
                    <input type="hidden" name="filename" id="filename" />
                </td>
            </tr>
            
            <tr>
                <td> 
                    <b><label>Enter the File Delimiter</label></b>                    
                </td>
                <td>
                    <select id="delimS" name="delimS">
                        <option id="hyphenS" value="hyphenS" >-</option>
                        <option id="lineS" value="lineS" >Line</option>
                        <option id="commaS" value="commaS" >Comma</option>
                        <option id="tabS" value="tabS" >Tab</option>
                        <option id="semiS" value="semiS">Semicolon</option>
                    </select>
                    <input type="hidden" name="delimiter" id="delimiter" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="hidden" name="mode" id="mode" value="" />
                    <input type="hidden" name="analysis_name" id="analysis_name" value="<%=analysis_name%>" />
                    <button type="button" class="dropbtn" title="Download Feature List as File" onclick="submitSaveFeatureListRequest_3();">Save</button>
                </td>
            </tr>
            
        </table>
        </form>
        
    </body>

<%  } else if (mode.equals("save")) {  %>

    <body>
        <table cellpadding="5" style="width: 100%;" border="0">
            <tr>
                <td height="15">
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td height="75">
                    File <i><%=filename%></i> created. <br>
                </td>
            </tr>
        </table>
    </body>

<%  }  %>

</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>