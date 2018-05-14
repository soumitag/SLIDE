<%-- 
    Document   : createNewList
    Created on : Apr 10, 2017, 5:19:11 PM
    Author     : soumita
--%>

<%@page import="searcher.GeneObject"%>
<%@page import="structure.MetaData"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="utils.SessionManager"%>
<%@page import="utils.ReadConfig"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
try {
    
    String base_url = (String)session.getAttribute("base_url");
    
    String mode = request.getParameter("mode");
    String analysis_name = request.getParameter("analysis_name");
    // mode can be session, file, list, input
    // mode input: show input form, in this mode a new sub-analysis is not created, this is the default mode
    // mode session: create sub-analysis from one of the lists in session
    // mode file: create sub-analysis from a file of entrez ids
    // mode list: create sub-analysis from user-specified comma delimited list of entrez ids
    // mode name_error: the name provided for the sub-analysis is already in use. Give this message and show the input form again.
    // mode emptylist_error: the selected feature lists are empty 
    
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
    String session_id = request.getSession().getId();        
    
    String list_name = request.getParameter("list_name");
    
    // for mode = name_error, list, file, and session
    if (!mode.equals("input")) {
        list_name = list_name.trim();
    }
    
    // check if the new name provided for sub-analysis is already in use
    if (mode.equals("list") || mode.equals("file") || mode.equals("session")) {
        if (session.getAttribute(list_name) != null) {
            String url = "createSubAnalysis.jsp?mode=name_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
    }
    
    AnalysisContainer sub_analysis = null;
    
    if (mode.equals("list")) {
        //create sub-analysis from user-specified comma delimited list of entrez ids
        
        String list_data = request.getParameter("entrez_ids");
        
        ArrayList <String> specified_entrez_list = new ArrayList <String> ();
        StringTokenizer st = new StringTokenizer (list_data, ",");
        while (st.hasMoreTokens()) {
            specified_entrez_list.add(st.nextToken().trim().toLowerCase());
        }
        
        ArrayList<Integer> filterListMap = new ArrayList<Integer>();
        for (int i = 0; i < analysis.database.raw_data.length; i++) {
            String db_eid = analysis.database.features.get(i).entrez;
            //String db_eid = analysis.database.metadata.getFeature(i, GeneObject.ENTREZ);
            if (specified_entrez_list.contains(db_eid)) {
                filterListMap.add(i);
            }
        }
        
        if (filterListMap.size() == 0) {
            String url = "createSubAnalysis.jsp?mode=emptylist_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        
        sub_analysis = analysis.createSubAnalysis(list_name, filterListMap, installPath, session_id);
        // finally add sub-analysis to session
        session.setAttribute(sub_analysis.analysis_name, sub_analysis);
        
    } else if (mode.equals("file")) {
        //create sub-analysis from a file of entrez ids
        
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
            String url = "createSubAnalysis.jsp?mode=emptylist_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        
        sub_analysis = analysis.createSubAnalysis(list_name, filterListMap, installPath, session_id);
        // finally add sub-analysis to session
        session.setAttribute(sub_analysis.analysis_name, sub_analysis);
        
    } else if (mode.equals("session")) {
        //create sub-analysis from one of the lists in session
        
        String session_list_name = request.getParameter("session_list_name");
        ArrayList <Integer> filterList = analysis.filterListMap.get(session_list_name);
        
        if (filterList.size() == 0) {
            String url = "createSubAnalysis.jsp?mode=emptylist_error&analysis_name=" + analysis_name + "&list_name=" + list_name;
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        
        sub_analysis = analysis.createSubAnalysis(list_name, filterList, installPath, session_id);
        
        // finally add sub-analysis to session
        session.setAttribute(sub_analysis.analysis_name, sub_analysis);
    }
    
    String ua = request.getHeader( "User-Agent" );
    boolean isChrome = false;
    isChrome = ( ua != null && ua.indexOf( "Chrome" ) != -1 );
    
%>

<% if (mode.equals("input") || mode.equals("name_error") || mode.equals("emptylist_error") || mode.equals("fileupload_error")) {  %>
<html>
    <head>
        <link rel="stylesheet" href="vtbox-main.css">
        <link rel="stylesheet" href="vtbox-tables.css">

    <script>
        
        function submitSubAnalysisRequest_1(){
            
            var v1 = document.getElementById("list_name").value;
            var e = document.getElementById("session_list_name");
            var v2 = e.options[e.selectedIndex].value;
            if(v1 == "" || v2 == "dashS"){
                alert('Please provide a name and list for the analysis.');
            } else {
                createNewList_Session();
            }
        }
        
        function submitSubAnalysisRequest_2(){
            
            var v1 = document.getElementById("list_name").value;
            var v2 = document.getElementById("entrez_ids").value;
            if(v1 == "" || v2 == ""){
                alert('Please provide a name and list for the analysis.');
            } else {
                createNewList_Text();
            }
        }
        
        /*
        function submitSubAnalysisRequest_3(){
            
            var v1 = document.getElementById("list_name").value;
            var v2 = document.getElementById("txtentrezfilename").value; 
            var e = document.getElementById("delimS");
            var v3 = e.options[e.selectedIndex].value;
            if(v1 === "" || v2 === "" || v3 === "hyphenS"){
                alert('Please provide a name and list for the analysis.');
            } else {
                createNewList_File();
            }
        }
        
        
        function createNewList_File() {
            document.getElementById("mode").value = "file";
            document.getElementById("createFilteredListForm").submit();
        }
        */
        
        function createNewList_Text() {
            document.getElementById("mode").value = "list";
            document.getElementById("createFilteredListForm").submit();
        }
        
        function createNewList_Session() {
            document.getElementById("mode").value = "session";
            document.getElementById("createFilteredListForm").submit();
        }
        
        function getinputfilenamefromtext(){
            var filename = document.getElementById("txtentrezfilename").value;
            document.getElementById("file_name").value = filename;
        }
        
        function getfilepathfrombrowse(){
            var filename = document.getElementById("selectentrezfilename").value;
            document.getElementById("txtentrezfilename").value = filename;
            document.getElementById("file_name").value = filename;
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

    <form name="createFilteredListForm" id="createFilteredListForm" method="get" action="createSubAnalysis.jsp">
    <table cellpadding="5" style="width: 100%;" border="0">

        <tr>
            <td height="75" align="center" colspan="2">
                <b><label>Create Sub-Analysis</label></b>
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
                <b><label>Enter a Name for the Analysis</label></b>
            </td>
            <td>
                <input type="text" id="list_name" name="list_name" size="35">
            </td>
        </tr>

        <tr>
            <td height="50" colspan="2">
                <b><label>Specify Entrez IDs to be included in the Sub-Analysis:</label></b>
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label>From one of the Feature Lists</label></b>
            </td>
            <td colspan="2">
                <select name="session_list_name" id="session_list_name">
                    <option value="dashS" >-</option>
                    <% Iterator it = analysis.filterListMap.entrySet().iterator();  %>
                    <% while (it.hasNext()) {   %>
                        <% String featurelist_name = ((Map.Entry)it.next()).getKey().toString(); %>
                        <option value="<%=featurelist_name%>" ><%=featurelist_name%></option>
                    <% }    %>
                </select>
                &nbsp;
                <button type="button" class="dropbtn" title="Create Sub-Analysis." onclick="submitSubAnalysisRequest_1();">Create</button>
            </td>
        </tr>

        <tr height="5px">
            <td height="5px" colspan="2" align="center">
                OR
            </td>
        </tr>
        
        <tr>
            <td>
                <b><label>As a Comma Delimited List</label></b>
            </td>
            <td>
                <input type="text" id="entrez_ids" name="entrez_ids" size="50">
                &nbsp;
                <button type="button" class="dropbtn" title="Create Sub-Analysis." onclick="submitSubAnalysisRequest_2();">Create</button>
            </td>
        </tr>

        <input type="hidden" id="mode" name="mode" value="input">
        <input type="hidden" id="analysis_name" name="analysis_name" value="<%=analysis_name%>">
        
    </form>
        
        <tr height="5px">
            <td height="5px" colspan="2" align="center">
                OR
            </td>
        </tr>

        <tr>
            <td>
                <b><label>In a Delimited File</label></b>
            </td>
            <td>
                <form name="uploadEntrezListForm" id="uploadEntrezListForm" action="<%=base_url%>DataUploader?analysis_name=<%=analysis_name%>&upload_type=entrez_list" method="post" enctype="multipart/form-data" target="" >
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
                <button type="button" class="dropbtn" title="Create Sub-Analysis." onclick="submitSubAnalysisRequest_3();">Create</button>
            </td>
        </tr>

    </table>
</body>

</html>

<%  } else if (mode.equals("list") || mode.equals("file") || mode.equals("session")) {  %>

<html>
<head>
    <style>

            table {
                font-family: verdana;
                border-collapse: collapse;
                width: 100%;
            }

            td, th {
                border: 1px solid #dddddd;
                padding: 8px;
            }

            tr:nth-child(even) {
                background-color: #efefef;
            }

        </style>
</head>

<body>
    <table cellpadding="5" style="width: 100%;" border="0">
        <tr>
            <td height="15">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td height="75">
                Sub-analysis <i><%=list_name%></i> created. <br>
                Click <a href="<%=base_url%>displayHome.jsp?analysis_name=<%=list_name%>&load_type=sub_analysis" target="_blank">here</a> to open.
            </td>
        </tr>
        <tr>
            <td height="75" align="center">
                To save the sub-analysis open it and click the "Save Analysis" button.
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