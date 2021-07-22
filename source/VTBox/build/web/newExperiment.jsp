<%-- 
    Document   : index
    Created on : 6 Jan, 2017, 10:50:29 AM
    Author     : soumitag
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="utils.SessionManager"%>
<%@page import="utils.ReadConfig"%>
<%@page import="java.io.File"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
try {
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SLIDE</title>
<script type = "text/javascript" language = "JavaScript" src="userInput.js"></script>

    <link rel="stylesheet" href="vtbox-main.css">
    <link rel="stylesheet" href="vtbox-tables.css">

<script>
    
    function uploadCompletion (status, data_filename, mapping_filename) {
        
        document.getElementById('data_upload_btn').innerHTML = 'Upload';
        document.getElementById("data_upload_btn").disabled = false;
        alert("Data File " + data_filename + " and Sample Mapping File " + mapping_filename + " has been uploaded.");
        if (status == "") {
            // if upload succeded display rest of the input interface
            document.getElementById('input_table').style.display = 'inline';
            document.getElementById("fileinputname").value = data_filename;
            document.getElementById("mapfilename").value = mapping_filename;
            // reset all remaining fields
            //document.getElementById('fileInputForm').reset();
            //$('#upload_form')[0].reset();
        } else {
            // if upload failed display upload status message
            document.getElementById('notice_board').style.display = 'inline';
            document.getElementById('notice_board').innerHTML = status;
        }

    }

</script>


</head>

<% 
    
    String msg = request.getParameter("msg");
    String analysis_name = request.getParameter("txtnewexperiment");

    // get isntallation directory
    String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
    
    // get the current session's id
    String session_id = "";
    
    // check if a session already exists 
    if (request.getSession(false) == null) {
        // if not, create new session
        session = request.getSession(true);
        session_id = session.getId();
    } else {
        // if it does, check if an analysis of same name exists in the session
        if (session.getAttribute(analysis_name) == null) {
            // if not, create required temp folders for this analysis
            session_id = session.getId();
        } else {
            // if it does, send back to previous page with error message
            getServletContext().getRequestDispatcher("/index.jsp?status=analysis_exists&analysis_name=" + analysis_name).forward(request, response);
            return;
        }
    }
    
    // create session directory
    SessionManager.createSessionDir(installPath, session_id);
    
    // set base url
    String url = request.getRequestURL().toString();
    String base_url = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
    session.setAttribute("base_url", base_url);
    
    String ua = request.getHeader( "User-Agent" );
    boolean isChrome = false;
    isChrome = ( ua != null && ua.indexOf( "Chrome" ) != -1 );
    System.out.println(isChrome);
%>


<body>

            <table cellpadding="5" style="width: 100%;" border="0">
                <tr>
                    <td colspan="1" align="center"><h2>New Experiment Input</h2></td>
                </tr>
            <% if (msg != null) { %>
                <tr>
                    <td class="error_msg" colspan="2"  align="center">
                        <%=msg%>
                    </td>
                </tr>
            <% } %>
            <form name="upload_form" id="upload_form" action="<%=base_url%>DataUploader?analysis_name=<%=analysis_name%>" method="post" enctype="multipart/form-data" target="upload_target" >
            <tr>
                <td>
                    <b><label>Input Data File Name</label></b>
            	</td>
               
                <td>
                    <input type="file" id="selectmrnafilename" name="selectmrnafilename" size="70"/>    
                    <iframe id="upload_target" name="upload_target" src="#" style="width:0;height:0;border:0px solid #fff;"></iframe>
                    <div id="notice_board" name="notice_board" style="display:none"></div>
                </td>
            </tr>
             <tr>
                <td>
                    <b><label>Input Sample to Column Mapping File</label></b>
            	</td>
                
                <td>
                <input type="file" id="selectmapfilename" name="selectmapfilename" size="70"/>
                 &nbsp;&nbsp;
                    <button type="button" id="data_upload_btn" title="Upload Selected Datafile To Server" onclick="uploadData();">Upload</button>
                </td>
            </tr>
            </table>
            
            </form>

            
            
            <form name="fileInputForm" method ="get" action="">
            
            <input type="hidden" name="fileinputname" id="fileinputname" />
            <input type="hidden" name="newexperimentname" id="newexperimentname" value="<%=analysis_name%>" />
            <input type="hidden" name="mapfilename" id="mapfilename" />
            <table id="input_table" cellpadding="5" style="width: 100%; display: none" border="0">

            <tr>
                <td>
            	&nbsp;
            	</td>

                <td colspan="1">
                    <input type="checkbox" id="headerflag" name="headerflag" checked="checked"> The input file has headers included</input>
                </td>
            </tr>
            <!--
            <tr>
                <td>
                    <b><label>Specify k for k Nearest Neighbor based data imputation</label></b>
            	</td>

                <td colspan="1">
                    <input type="text" id="txtKNN" name="txtKNN" size="3" /> 
                </td>
            </tr>
            -->
            <!--
            <tr>
                <td>
            	&nbsp;
            	</td>
                          
                <td colspan="1">
                    <input type="checkbox" id="log2flag" name="log2flag"> Perform log base 2 transformation of the data</input>
                </td>
            </tr>
            -->
            <tr>
                <td>
                    <b><label>How Many Rows Should be Read from the File?</label></b>
            	</td>
                <td colspan="1">
                    <input type="radio" name="rowLoading" value="1" checked="checked"> All
                    &nbsp; 
                    <input type="radio" name="rowLoading" value="0"> 
                    From Row &nbsp; <input type="text" id="txtFromRow" name="txtFromRow" size="5" />
                    &nbsp; To &nbsp; <input type="text" id="txtToRow" name="txtToRow" size="5" />
                    &nbsp; (count 2nd Row as 1 if data has header)
                </td>
            </tr>
             <tr>
                <td> 
                    <b><label>Data Imputation</label></b>                    
                </td>
                <td colspan="1">
                    <select id="imputeD" name="imputeD" onchange="getimputevalue();">
                        <option id="imputeHyphen" value="-1" selected>-</option>
                        <option id="imputeNone" value="0">None</option>
                        <option id="imputeRowMean" value="1" >Impute with row mean</option>
                        <option id="imputeColMean" value="2" >Impute with column mean</option>
                        <option id="imputeRowMedian" value="3" >Impute with row median</option>
                        <option id="imputeColMedian" value="4">Impute with column median</option>
                    </select>                  
                <input type="hidden" name="imputeval" id="imputeval" />
            </tr>
            
            <tr>
                <td> 
                    <b><label>File Delimiter</label></b>                    
                </td>
                <td colspan="1">
                    <select id="delimS" name="delimS" onchange="getdelimitervalue();">
                        <option id="hyphenS" value="hyphenS" >-</option>
                        <option id="commaS" value="commaS" >Comma</option>
                        <option id="tabS" value="tabS" >Tab</option>
                        <option id="spaceS" value="spaceS" >Space</option>
                        <option id="semiS" value="semiS">Semicolon</option>
                    </select>
                    <button type="button" class="dropbtn" id="Preview" title="File Preview" style="visibility: hidden" onclick="filePreview('<%=analysis_name%>');">Preview</button>
                    <!--<input type="button" id="Preview" value="Preview" style="visibility: hidden" onClick="filePreview();"></input>--> 
                </td>
                <input type="hidden" name="delimval" id="delimval" />
            </tr>
            
            <tr>
                <td colspan="2">
                    <iframe name="previewFrame" id="previewFrame" src="" style="display: hidden" height="0" width="0"> </iframe>
                </td>
            </tr>
            
            <tr>
            	<td>
                    <b><label>Enter the Species name</label></b>
            	</td>
                <td colspan="1">
                    <select id="species" onchange="getspeciesname();">
                        <option id="nospecies" value="nospecies" >-</option>
                        <option id="human" value="human">Homo sapiens</option>
                    	<option id="mouse" value="mouse" >Mus musculus</option>
                    </select>
                </td>
                <input type="hidden" name="species_name" id="species_name" />
            </tr>
            
            <tr>
            	<td>
                    <b><label>Enter the Non-numeric Features Column Numbers</label></b>
            	</td>
                <td colspan="1">
                    <input type="text" name="txtNumMetaCols" id="txtNumMetaCols" value="" onchange="chknumrange(this);" />
                    &nbsp; &nbsp; 
                    (Specify range as 1-4 or specific columns as 1,3,4)
                </td>
            </tr>    
            
            <tr>
            	<td>
                    <b><label>Enter the Gene Symbol Column Numbers (if any)</label></b>
            	</td>
                <td colspan="1">
                    <input type="text" name="txtGeneSymbolCol" id="txtGeneSymbolCol" value="" onchange="chkisEmptyOrNumber(this);" />
                    &nbsp; &nbsp; 
                    (The gene symbol column should be one of the meta-data columns specified above)
                </td>
                                
            </tr>
            
            <tr>
            	<td>
                    <b><label>Enter the Entrez ID Column Numbers (if any)</label></b>
            	</td>
                <td colspan="1">
                    <input type="text" name="txtEntrezCol" id="txtEntrezCol" value="" onchange="chkisEmptyOrNumber(this);" />
                    &nbsp; &nbsp; 
                    (The entrez id column should be one of the meta-data columns specified above)
                </td>
            </tr>
            
            <tr>
                <td>
                    <b><label>Is this a Time Course Data?</label></b>
            	</td>
                <td colspan="1">
                    <input type="radio" name="timeSeries" value="no" checked="checked" onclick="setTimeSeriesAs('no')"> No
                    &nbsp; 
                    <input type="radio" name="timeSeries" value="yes" onclick="setTimeSeriesAs('yes')"> Yes
                </td>
                <input type="hidden" name="isTimeSeries" id="isTimeSeries" value="no"/>
            </tr>
            
                     
           
             
            <tr>
                <td colspan="2">&nbsp;</td>
            </tr>
                
            <tr>
                <td colspan="2" style="text-align: center">
                    <button type="button" class="dropbtn" id="EnterNewExperiment" title="Enter New Experiment" onclick="createNewExp()">Create</button>                    
                </td>
            </tr>
            
            </table>
            
    <p> &nbsp;</p>

</form> 

  
</body>

</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>