<%-- 
    Document   : createFeatureList
    Created on : May 31, 2017, 3:29:00 PM
    Author     : soumitag
--%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
try {    
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    String base_url = (String)session.getAttribute("base_url");
    String list_name = "";
    
    if (mode.equals("process")) {
    
        list_name = request.getParameter("list_name");
        list_name = list_name.trim();
        
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
        if (analysis.filterListMap.containsKey(list_name)) {
            String url = "createFeatureList.jsp?mode=name_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        } else {
            ArrayList <Integer> newFeatureList = new ArrayList <Integer> ();
            analysis.filterListMap.put(list_name, newFeatureList);
        }
    } else if (mode.equals("file")) {
    
        list_name = request.getParameter("list_name");
        list_name = list_name.trim();
        
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        
        if (analysis.filterListMap.containsKey(list_name)) {
            String url = "createFeatureList.jsp?mode=name_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        } else {
            
            String file_name = request.getParameter("file_name");
            String delimval = request.getParameter("delimval");

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

            BufferedReader br = null;
            String line = null;

            ArrayList <String> file_entrez_list = new ArrayList <String> ();

            if (delimiter.equals("line")) {

                try {
                    br = new BufferedReader(new FileReader(file_name));
                    while ((line = br.readLine()) != null) {
                        file_entrez_list.add(line.trim().toLowerCase());
                    }
                } catch (Exception e) {
                    System.out.println("Error reading input data:");
                    System.out.println(e);
                }

            } else {

                try {
                    br = new BufferedReader(new FileReader(file_name));
                    line = br.readLine();
                } catch (Exception e) {
                    System.out.println("Error reading input data:");
                    System.out.println(e);
                }

                StringTokenizer st = new StringTokenizer (line, delimiter);
                while (st.hasMoreTokens()) {
                    file_entrez_list.add(st.nextToken().trim().toLowerCase());
                }

            }
            
            ArrayList <Integer> filterListMap = new ArrayList <Integer>();
            for (int i = 0; i < analysis.database.raw_data.length; i++) {
                String db_eid = analysis.database.features.get(i).entrez;
                //String db_eid = analysis.database.metadata.getFeature(i, GeneObject.ENTREZ);
                if (file_entrez_list.contains(db_eid)) {
                    filterListMap.add(i);
                }
            }

            if (filterListMap.size() == 0) {
                String url = "createFeatureList.jsp?mode=emptylist_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            
            analysis.filterListMap.put(list_name, filterListMap);
        }
    }
    
    if (mode.equals("name_error")) {
        list_name = request.getParameter("list_name");
    }
%>

<% if (mode.equals("input") || mode.equals("name_error") || mode.equals("emptylist_error") || mode.equals("fileupload_error")) {  %>
<html>
<head>
    
    <link rel="stylesheet" href="vtbox-main.css">
    <link rel="stylesheet" href="vtbox-tables.css">
    
    <script>
        
        function submitCreateRequest(){
            
            var value1 = document.getElementById("list_name").value;
            if(value1 == ""){
                alert('Please provide a list name.');
            } else {
                createFeatureList();
            }
            
        }
        
        function createFeatureList() {
            document.getElementById("mode").value = "process";
            document.getElementById("createFeatureListForm").setAttribute("action","createFeatureList.jsp");
            document.getElementById("createFeatureListForm").submit();
        }
        
        function getdelimitervalue(){
            var d = document.getElementById("delimS");
            //alert(optionDelim);
            var optionDelim = d.options[d.selectedIndex].value;
            document.getElementById("delimval").value = optionDelim;
            //alert(optionDelim); 
        }
        
        function submitSubAnalysisRequest_3() {
            
            var y = document.getElementById("txtentrezfilename").value;
            var d = document.getElementById("delimval").value;
            var n = document.getElementById("list_name").value;

            if (n === "") {
                alert("Please specify a name for the sub-analysis.");
            } else if (y === ""){
                alert("Please select a file to upload.");
            } else if (d === null || d === "" || d === "hyphenS") {
                alert("Please select the delimiter used in the file.");
            } else {
                var upform = document.getElementById("uploadEntrezListForm");
                upform.action = upform.action + "&delimval=" + d + "&list_name=" + n;
                upform.submit();
            }
        }
    </script>
    
</head>

<body>
    
    <form name="createFeatureListForm" id="createFeatureListForm" method="get" action="">
    <table cellpadding="5" style="width: 100%;" border="0">
        
        <tr>
            <td height="75" align="center" colspan="2">
                <b><label>Create a New Feature List</label></b>
            </td>
        </tr>

        <% if (mode.equals("name_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>The name <%=list_name%> is already in use. Please specify another name.</label></b>
            </td>
        </tr>
        <% } %>
        
        <% if (mode.equals("emptylist_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>The selected feature list is empty.</label></b>
            </td>
        </tr>
        <% } %>
        
        <% if (mode.equals("fileupload_error")) {  %>
        <tr>
            <td class="error_msg" align="center" colspan="2">
                <b><label>The file could not be uploaded. Please try again.</label></b>
            </td>
        </tr>
        <% } %>
        
        <tr>
            <td>
                <b><label>Enter a Name for the New List</label></b>
            </td>
            <td>
                <input type="text" id="list_name" name="list_name" size="30">
            </td>
        </tr>
        
        <tr>
            <td colspan="2" height="50">
                &nbsp;
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label>Create an Empty Feature List</label></b>
            </td>
            <td>
                <button type="button" class="dropbtn" title="Create Feature Lists." onclick="submitCreateRequest();">Create</button>
                <input type="hidden" name="mode" id="mode" value="process">
                <input type="hidden" name="analysis_name" id="analysis_name" value="<%=analysis_name%>">
            </td>
        </tr>
        
    </form>
    
        <tr height="5px">
            <td height="5px" colspan="2" align="center">
                OR
            </td>
        </tr>

        <tr>
            <td>
                <b><label>Upload a List from File</label></b>
            </td>
            <td>
                <form name="uploadEntrezListForm" id="uploadEntrezListForm" action="<%=base_url%>DataUploader?analysis_name=<%=analysis_name%>&upload_type=entrez_feature_list" method="post" enctype="multipart/form-data" target="" >
                    <input type="file" id="txtentrezfilename" name="txtentrezfilename"/>
                </form>
            </td>
        </tr>

        <tr>
            <td> 
                <b><label>Enter the File Delimiter</label></b>                    
            </td>
            <td>
                <select id="delimS" name="delimS" onchange="getdelimitervalue();">
                    <option id="hyphenS" value="hyphenS" >-</option>
                    <option id="lineS" value="lineS" >Line</option>
                    <option id="commaS" value="commaS" >Comma</option>
                    <option id="tabS" value="tabS" >Tab</option>
                    <option id="spaceS" value="spaceS" >Space</option>
                    <option id="semiS" value="semiS">Semicolon</option>
                </select>
                <input type="hidden" name="delimval" id="delimval" />
                &nbsp;
                <button type="button" class="dropbtn" title="Create Sub-Analysis." onclick="submitSubAnalysisRequest_3();">Upload</button>
            </td>
        </tr>
   
    </table>
            
</body>
</html>

<%  } else if (mode.equals("process") || mode.equals("file")) {  %>
<html>
<script>
    parent.updateFeatureLists('<%=list_name%>', 1);
</script>

<body>
    <table cellpadding="5" style="width: 100%;" border="0">        
        <tr>
            <td height="75">
                Feature list <i><%=list_name%></i> created.
            </td>
        </tr>
    </table>
</body>
</html>
<%  }  %>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>