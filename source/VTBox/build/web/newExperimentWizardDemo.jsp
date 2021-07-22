<%-- 
    Document   : newExperimentWizardDemo
    Created on : 8 May, 2018, 11:51:44 AM
    Author     : Soumita
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


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SLIDE</title>
    <script type = "text/javascript" language = "JavaScript" src="userInput.js"></script>
    <link rel="stylesheet" href="vtbox-main.css">
    
<script>
    
    var selected_metacol_names = [];
    var selected_metacol_indexes = [];
    var metacol_identifer_mappings = new Array();
    
    function uploadCompletionData (status, filename, colnames) {
        
        document.getElementById('data_upload_btn').innerHTML = 'Upload';
        document.getElementById("data_upload_btn").disabled = false;
        //alert("Data File " + data_filename + " and Sample Mapping File " + mapping_filename + " has been uploaded.");
        if (status == "") {
            // if upload successfull display upload status message
            document.getElementById('notice_board').style.display = 'inline';
            status = "Data File <q><i><b>" + filename + "</b></i></q> uploaded successfully!";
            document.getElementById('notice_board').innerHTML = status;
            document.getElementById('notice_board').style.display = 'inline-block';
            // if upload succeded display rest of the input interface
            document.getElementById('analysis_params_div').style.display = 'inline';
            document.getElementById("fileinputname").value = filename;
            
            var i;
            // clear cols drop down list
            var cols = document.getElementById('cols');
            for(i = cols.options.length - 1 ; i >= 0 ; i--) {
                cols.remove(i);
            }
            
            while (selected_metacol_names.length > 0) {
                i = selected_metacol_names.length-1;
                deSelectMetaCol(selected_metacol_names[i], selected_metacol_indexes[i]);
            }
            
            // add new colnames to drop down cols
            for(i=0; i<colnames.length; i++) {
                cols.options[cols.options.length] = new Option(colnames[i], colnames[i]);
            }
            
            selected_metacol_names = [];
            selected_metacol_indexes = [];
            metacol_identifer_mappings = new Array();
            
            document.getElementById('col_names').value = colnames;
            
        } else {
            // if upload failed display upload status message
            document.getElementById('notice_board').innerHTML = status;
            document.getElementById('notice_board').style.display = 'inline-block';
        }

    }
    
    function uploadCompletionMapping (status, filename) {
        
        document.getElementById('mapUploadButton').innerHTML = 'Upload';
        document.getElementById("mapUploadButton").disabled = false;
        //alert("Data File " + data_filename + " and Sample Mapping File " + mapping_filename + " has been uploaded.");
        if (status == "") {
            // if upload successfull display upload status message
            document.getElementById('mapping_notice_board').style.display = 'inline';
            status = "Data File <q><i><b>" + filename + "</b></i></q> uploaded successfully!";
            document.getElementById('mapping_notice_board').innerHTML = status;
            document.getElementById('mapping_notice_board').style.display = 'inline-block';
            // if upload succeded display rest of the input interface
            document.getElementById("mapfilename").value = filename;
            
            mapping_data_uploaded = true;
            
            //createNewExp();

        } else {
            // if upload failed display upload status message
            document.getElementById('mapping_notice_board').innerHTML = status;
            document.getElementById('mapping_notice_board').style.display = 'inline-block';
            mapping_data_uploaded = false;
        }

    }
    
    function updateMetaColsList() {
                
        var d = document.getElementById("cols");
        var selected_metacol_name = d.options[d.selectedIndex].value;
        var selected_metacol_index = d.selectedIndex;
        //alert(selected_metacol_index);
        
        if(selected_metacol_indexes.indexOf(selected_metacol_index) == -1){
                
            var div_node = document.createElement("div");
            var text = document.createTextNode(selected_metacol_name + '\u00A0 \u00A0' );
            div_node.appendChild(text);
            div_node.setAttribute("id", "div_" + selected_metacol_name);
            div_node.setAttribute("class", "e1");

            var close_node = document.createElement("a");
            var cross_text = document.createTextNode('\u2715');
            close_node.appendChild(cross_text);
            close_node.setAttribute("href", "#");
            close_node.setAttribute('title', 'Click to undo ADD');
            close_node.onclick = function () {
                deSelectMetaCol(selected_metacol_name, selected_metacol_index);
            };
            div_node.appendChild(close_node);

            document.getElementById("meta_col_container").appendChild(div_node);

            selected_metacol_indexes.push(selected_metacol_index);
            selected_metacol_names.push(selected_metacol_name); 
        } else {
            alert("The column has already been marked as a metadata column");
        }
        
        metacols = document.getElementById('metacols');
        for(i=0; i<selected_metacol_names.length; i++) {
            metacols.options[i] = new Option(selected_metacol_names[i], selected_metacol_names[i]);
            metacols.options[i].setAttribute("id", "metacols_element_" + selected_metacol_indexes[i]);
        }
        
        //alert(selected_metacol_names);
        //alert(selected_metacol_indexes);
    }
    
    function deSelectMetaCol(col_name, col_index) {
        
        var node = document.getElementById("div_" + col_name);
        document.getElementById("meta_col_container").removeChild(node);
                
        var pos = selected_metacol_names.indexOf(col_name);
        selected_metacol_names.splice(pos, 1);
        selected_metacol_indexes.splice(pos, 1);
        
        metacols = document.getElementById('metacols');
        opt = document.getElementById('metacols_element_' + col_index);
        metacols.removeChild(opt);
        
        for (i=0; i<metacol_identifer_mappings.length; i++) {
            t = metacol_identifer_mappings[i];
            if (t[0] === col_name) {
                deSelectIdCol(t[0], t[1]);
            }
        }
    }
    
    function addIdentifierCol(){
        var metacols = document.getElementById("metacols");
        var identifiers = document.getElementById("identifiers");
        
        var selected_metacol = metacols.options[metacols.selectedIndex].value;
        var selected_identifier = identifiers.options[identifiers.selectedIndex].value;
        var selected_identifier_text = identifiers.options[identifiers.selectedIndex].text;
        
        if (selected_metacol_names.length === 0) {
            alert("Please specify a metadata column first, to map it to an identifier.");
            return;
        }
        
        for (var i=0; i<metacol_identifer_mappings.length; i++) {
            t = metacol_identifer_mappings[i];
            if (t[0] === selected_metacol) {
                alert("The selected metadata column '" + selected_metacol + "' is already mapped to another identifier. Please delete the existing mapping to create a new one.");
                return;
            }
            if (t[1] === selected_identifier) {
                alert("The selected identifier '" + selected_identifier_text + "' is already mapped to another column. Please delete the existing mapping to create a new one.");
                return;
            }
        }
        
        var div_node = document.createElement("div");
        var text = document.createTextNode(selected_metacol + '\u00A0 \u2192 \u00A0' + selected_identifier_text + '\u00A0 \u00A0');
        div_node.appendChild(text);
        div_node.setAttribute("id", "div_" + selected_metacol + "_" + selected_identifier);
        div_node.setAttribute("class", "e1");

        var close_node = document.createElement("a");
        var cross_text = document.createTextNode('\u2715');
        close_node.appendChild(cross_text);
        close_node.setAttribute("href", "#");
        close_node.setAttribute('title', 'Click to undo ADD');
        close_node.onclick = function () {
            deSelectIdCol(selected_metacol, selected_identifier);
        };
        div_node.appendChild(close_node);

        document.getElementById("identifier_col_container").appendChild(div_node);       
        
        var t = new Array(2);
        t[0] = selected_metacol;
        t[1] = selected_identifier;
        metacol_identifer_mappings.push(t);
        //alert(metacol_identifer_mappings);
    }
    
    function deSelectIdCol(selected_metacol, selected_identifier) {             
        var node = document.getElementById("div_" + selected_metacol + "_" + selected_identifier);
        document.getElementById("identifier_col_container").removeChild(node);
        
        for (i=0; i<metacol_identifer_mappings.length; i++) {
            t = metacol_identifer_mappings[i];
            if (t[0] === selected_metacol && t[1] === selected_identifier) {
                metacol_identifer_mappings.splice(i, 1);
            }
        }
        
        //alert(metacol_identifer_mappings);
    }

    function downloadMappingTemplate() {
        var colnames = document.getElementById('col_names').value;
        var is_time_course = document.getElementById('isTimeSeries').value;
        var has_replicates = document.getElementById('hasReplicates').value;
        var delimiter = document.getElementById('mapdelimval').value;
        var url_text = "<%=base_url%>TemplateSampleMapping?analysis_name=<%=analysis_name%>" + "&metacolnames=" + selected_metacol_names
                    + "&colnames=" + colnames + "&is_time_course=" + is_time_course + "&has_replicates=" + has_replicates
                    + "&delimiter=" + delimiter;
        //alert(url_text);
        document.getElementById('upload_target').contentWindow.location.replace(url_text);
    }
    
    function createNewExp(){
        //document.fileInputForm.setAttribute("action","init.jsp");
        var x = checkselectoptions();
        //alert(x);
        
        var hasReps = document.getElementById('hasReplicates').value;
        var isTS = document.getElementById('isTimeSeries').value;
        if (hasReps === 'yes' || isTS === 'yes') {
            if (!mapping_data_uploaded) {
                x = 0;
                alert("Please Select and Upload a sample mapping file.");
            }
        }
        
        if(x === 1) {
            document.getElementById('metacolnames').value = selected_metacol_names;
            document.getElementById('identifier_mappings').value = metacol_identifer_mappings;
            document.getElementById('EnterNewExperiment').innerHTML = 'Creating Analysis...';
            document.getElementById("EnterNewExperiment").disabled = true;
            document.fileInputForm.setAttribute("action","AnalysisInitializer");
            document.fileInputForm.submit();
        }
    }
    
    function createNewExp_1(){
        document.getElementById('EnterNewExperiment').innerHTML = 'Creating Analysis...';
        document.getElementById("EnterNewExperiment").disabled = true;
        document.fileInputFormDemo.setAttribute("action","AnalysisInitializer");
        document.fileInputFormDemo.submit();
    }
    
    
    function giveManual() {
        var win = window.open("https://github.com/soumitag/SLIDE/raw/master/application/slide/SLIDE_Users_Manual.pdf", '_blank');
	win.focus();
    }
			
    function takeToData() {
        var win = window.open("https://github.com/soumitag/SLIDE/tree/master/data", '_blank');
	win.focus();
    }
			
    function takeToIssues() {
        var win = window.open("https://github.com/soumitag/SLIDE/issues", '_blank');
	win.focus();
    }
    
    var mapping_data_uploaded = false;
    
</script>
<script> 
            if (top != window) {
                    top.location = window.location;
            }
</script>

</head>


<body>
    
    <div class="create_analysis_container_div">
        
        <div class="page_title_div">
            Create Demo Analysis
        </div>
        
        <div style="position: absolute; top: 20px; right: 10px" >
            <div class="dropdown">
                <button id="man_create_analysis">Help</button>
                <div class="dropdown-content" style="z-index: 10">
                    <a href="#" onclick="giveManual()" > Download User Manual </a>
                    <a href="#" onclick="takeToIssues()" > Report An Issue </a>
                    <a href="#" onclick="takeToData()" > Download Example Data and Sample Information Files </a>
                </div>
            </div>
        </div>
        
        <label style="height: 40px;font-family: verdana;font-size: 20px;font-weight: normal;text-align: center;background-color: #ffff80;" >
            This is a demo of SLIDE's input interface with the inputs preset. Simply click 'Create Analysis' to explore Brandes et al. whole lung mouse transcriptome data. The data can be downloaded <a href="https://github.com/soumitag/SLIDE/tree/master/data" target="_blank">here</a>.
        </label>
        
    
        <% if (msg != null) { %>
        <div class="error_msg_div">
            <%=msg%>
        </div>
        <% } %>
        
        <div class="analysis_data_div">

                         
            <table class="input">
            <tr>
                <th colspan="2">Analysis Data</th>
            </tr>
            
            <tr>
                <td width="30%">
                    <b><label>Select Input Data File</label></b><br>
                    Select a delimited text file. The first row of the input data file must contain UNIQUE column headers.
            	</td>
                
                <td>
                    <input type="text" id="selectmrnafilename" name="selectmrnafilename" size="70" value="Brandes_et_al_GSE42638_Quantiled_Log_Transformed_Data.txt" disabled/>
                    <iframe id="upload_target" name="upload_target" src="#" style="width:0;height:0;border:0px solid #fff;"></iframe>
                    <div id="notice_board" class="notes" name="notice_board" style="display:none"></div>
                </td>
            </tr>
            
            <tr>
                <td> 
                    <b><label>Select Delimiter used in the Text File</label></b>                    
                </td>
                <td colspan="1">
                    <select id="delimS" name="delimS" onchange="getdelimitervalue();" disabled >
                        <option id="hyphenS" value="hyphenS" >-</option>
                        <option id="commaS" value="commaS" >Comma</option>
                        <option id="tabS" value="tabS" selected>Tab</option>
                        <option id="spaceS" value="spaceS" >Space</option>
                        <option id="semiS" value="semiS">Semicolon</option>
                        <option id="pipeS" value="pipeS">Pipe</option>
                    </select>
                    
                </td>
            </tr>
            
            <tr>
                <td colspan="2" style="text-align: center;">
                    <button type="button" id="data_upload_btn" title="Upload Selected Datafile To Server" onclick="uploadDataFile();" disabled>Upload</button>
                </td>
            </tr>
                       
        </table>
        </div>
            
    
            
        <div id="analysis_params_div" class="analysis_params_div">
            
            <div id="main_params_div" class="analysis_params_inner_divs">
            
                <form name="fileInputForm" method ="get" action="">

                <input type="hidden" name="fileinputname" id="fileinputname" />
                <input type="hidden" name="newexperimentname" id="newexperimentname" value="<%=analysis_name%>" />
                <input type="hidden" name="mapfilename" id="mapfilename" />
                <input type="hidden" name="delimval" id="delimval" value="" />
                <input type="hidden" name="mapdelimval" id="mapdelimval" value="" />
                <input type="hidden" name="col_names" id="col_names" value="" />
                <input type="hidden" name="metacolnames" id="metacolnames" value="" />
                <input type="hidden" name="identifier_mappings" id="identifier_mappings" value="" />

                <table id="input_table" class="input">

                <tr>
                    <th colspan="2">
                        Analysis Parameters
                    </th>
                </tr>

                <tr>
                    <td colspan="2">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="dropbtn" id="Preview" title="File Preview" style="visibility: hidden" onclick="filePreview('<%=analysis_name%>', 'data')">Preview</button>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">
                        <iframe name="previewFrame" id="previewFrame" src="" style="display: hidden" height="0" width="0"> </iframe>
                    </td>
                </tr>


                <input type="hidden" name="headerflag" id="headerflag" value="on"/>

                <tr>
                    <td>
                        <b><label>Select Species</label></b>
                    </td>
                    <td colspan="1">
                        <select id="species" onchange="getspeciesname();" disabled>
                            <option id="nospecies" value="nospecies" >-</option>
                            <option id="human" value="human">Homo sapiens</option>
                            <option id="mouse" value="mouse" selected>Mus musculus</option>
                            <option id="other" value="other" >Other</option>
                        </select>
                    </td>
                    <input type="hidden" name="species_name" id="species_name" />
                </tr>

                <tr>
                    <td width="30%">
                        <b><label>How Many Rows Should be Read from the Data File?</label></b><br>
                        The first row (containing column names) is row 0 and is always read. The first row of data is row 1. 
                    </td>
                    <td colspan="1">
                        <input type="radio" name="rowLoading" value="1" checked="checked" disabled> All
                        &nbsp; 
                        <input type="radio" name="rowLoading" value="0" disabled> 
                        From Row &nbsp; <input type="text" id="txtFromRow" name="txtFromRow" size="5" />
                        &nbsp; To &nbsp; <input type="text" id="txtToRow" name="txtToRow" size="5" />
                        &nbsp; (both inclusive)
                    </td>
                </tr>
                 <tr>
                    <td> 
                        <b><label>Select a Data Imputation Strategy</label></b><br>
                        If your data has missing values please select an imputation strategy, otherwise select "None"
                    </td>
                    <td colspan="1">
                        <select id="imputeD" name="imputeD" onchange="getimputevalue();" disabled>
                            <option id="imputeHyphen" value="-1" >-</option>
                            <option id="imputeNone" value="0" selected>None</option>
                            <option id="imputeRowMean" value="1" >Impute with row mean</option>
                            <option id="imputeColMean" value="2" >Impute with column mean</option>
                            <option id="imputeRowMedian" value="3" >Impute with row median</option>
                            <option id="imputeColMedian" value="4">Impute with column median</option>
                        </select>
                    <input type="hidden" name="imputeval" id="imputeval" />
                </tr>

                <tr>
                    <td valign="top">
                        <b><label>Identify All Metadata Columns</label></b><br>
                        Metadata columns contain non-expression data that should not be visualized in the heatmap. 
                        To identify a metadata column select it from the drop-down list and click ADD.
                    </td>
                    <td colspan="1">
                        <input type="hidden" name="txtNumMetaCols" id="txtNumMetaCols" value="" />
                        <select id="cols" style="max-width: 500px;" disabled>
                            <option value="ID_REF" selected>ID_REF</option>
                        </select>
                        &nbsp;&nbsp;
                        <button type="button" class="dropbtn" id="addMetaCol" title="Mark Selected Column as Metadata Column" onclick="updateMetaColsList();" disabled>Add</button>
                        <br>
                        <div id="meta_col_container" style="padding-top: 10px; text-align:left; display: inline-block; width: 800px; min-height: 60px;">
                            <div class="e1">ID_REF </div>
                            <div class="e1">Gene </div>
                            <div class="e1">Entrez </div>
                        </div>
                    </td>
                </tr>

                <tr id="metadata_mapping_tr" style="display: table-row">
                    <td>
                        <b><label>Mark Metadata Columns as Row Identifiers (if any)</label></b><br>
                        If any of the metadata columns is a row identifier (such as Entrez, Genesymbol), 
                        please select them from the drop-down list, identify their type and click ADD.
                        You can add multiple identifiers if available.
                    </td>
                    <td colspan="1">
                        Metadata Column &nbsp;
                        <select id="metacols" style="max-width: 500px;" disabled>
                            <option id="None" value="None" >-</option>
                            <option id="None" value="ID_REF" selected>ID_REF</option>
                            <option id="None" value="Gene" >Gene</option>
                            <option id="None" value="Entrez" selected >Entrez</option>
                        </select>
                        &nbsp; &nbsp; Identifier Type &nbsp;
                        <select id="identifiers" disabled>
                            <option id="entrez" value="entrez_2021158607524066" >Entrez</option>
                            <option id="genesymbol" value="genesymbol_2021158607524066" >Gene Symbol</option>
                            <option id="refseq" value="refseq_2021158607524066" >RefSeq ID</option>
                            <option id="ensembl_gene_id" value="ensembl_gene_id_2021158607524066" >Ensembl gene ID</option>
                            <option id="ensembl_transcript_id" value="ensembl_transcript_id_2021158607524066" >Ensembl transcript ID</option>
                            <option id="ensembl_protein_id" value="ensembl_protein_id_2021158607524066" >Ensembl protein ID</option>
                            <option id="uniprot_id" value="uniprot_id_2021158607524066" >UniProt ID</option>
                        </select>
                        &nbsp;&nbsp;
                        <button type="button" class="dropbtn" id="addIdentifiers" title="Mark Selected Metadata Column as the Selected Identifier" onclick="addIdentifierCol()" disabled>Add</button>
                        <br>
                        <div id="identifier_col_container" style="padding-top: 10px; text-align:left; display: inline-block; width: 800px; min-height: 60px;">
                            <div class="e1">Gene &nbsp;&rarr;&nbsp; Gene Symbol </div>
                            <div class="e1">Entrez &nbsp;&rarr;&nbsp; Entrez </div>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td>
                        <b><label>Does the Data Contain Sample Groups?</label></b><br>
                        Samples can be grouped by up to two factors. For instance, the two factors can be 
                        experimental conditions (such as Treatment vs Control) and time points. 
                    </td>
                    <td colspan="1">
                        <input type="radio" name="replicates" value="no" onclick="setReplicatesAs('no')" disabled> No
                        &nbsp; 
                        <input type="radio" name="replicates" value="yes" checked="checked" onclick="setReplicatesAs('yes')" disabled> Yes
                    </td>
                    <input type="hidden" name="hasReplicates" id="hasReplicates" value="yes"/>
                </tr>

                </table>
                </form> 
                    
            </div>
                    
            <div id="sample_attributes_div" class="analysis_params_inner_divs" >
                <br>

                <form name="sampleAttributeForm" id="sampleAttributeForm" action="" method="post" enctype="multipart/form-data" target="upload_target" >
                <input type="hidden" id="mapping_upload_form_action" name="mapping_upload_form_action" value="<%=base_url%>DataUploader?analysis_name=<%=analysis_name%>&upload_type=mapping" />
                <table id="sample_table" class="input">

                <tr>
                    <th colspan="2">
                        Sample Attributes
                    </th>
                </tr>
                
                <tr>
                    <td width="30%">
                        <b><label>How many Sample Grouping Factors are there?</label></b>
                    </td>
                    <td colspan="1">
                        <input type="radio" name="timeSeries" value="no" onclick="setTimeSeriesAs('no')" disabled> 1
                        &nbsp; 
                        <input type="radio" name="timeSeries" value="yes" onclick="setTimeSeriesAs('yes')" checked="checked" disabled> 2
                    </td>
                </tr>

                <tr>
                    <td width="30%">
                        <b><label>Select a Sample Information File</label></b><br>
                        Click the "Download Sample Information Template" button to download a dummy sample information file for this dataset.
                        Click "Help" for further information.
                    </td>
                    <td>
                        <input type="text" id="select_map_filename" name="select_map_filename" size="70" value="Brandes_et_al_GSE42638_Sample_Information.txt" disabled />
                        &nbsp;&nbsp;&nbsp;
                        <div id="mapping_notice_board" class="notes" name="mapping_notice_board" style="display:none"></div>
                    </td>
                </tr>
                
                <tr>
                    <td> 
                        <b><label>Select Delimiter used in Sample Information File</label></b>                    
                    </td>
                    <td colspan="1">
                        <select id="mapDelimS" name="mapDelimS" onchange="getmapdelimitervalue();" disabled>
                            <option id="hyphenS" value="hyphenS" >-</option>
                            <option id="commaS" value="commaS" >Comma</option>
                            <option id="tabS" value="tabS" selected >Tab</option>
                            <option id="spaceS" value="spaceS" >Space</option>
                            <option id="semiS" value="semiS">Semicolon</option>
                            <option id="pipeS" value="pipeS">Pipe</option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center;">
                        <button type="button" class="dropbtn" id="mapUploadButton" title="Upload the selected mapping file to SLIDE server." onclick="uploadMappingFile()" disabled>Upload</button>
                        &nbsp;&nbsp;&nbsp;
                        <button type="button" class="dropbtn" id="mapDownloadButton" title="Download a partially filled template of sample information file for this data." onclick="downloadMappingTemplate()" disabled>Sample Information Template</button>
                    </td>
                </tr>
                
                </table>
                </form>
            </div>
            
            <div id="create_button_div" class="analysis_params_inner_divs" style="padding-top: 20px; text-align: center;">
                <div style="padding: 10px; text-align: center; background: #efefef; border: 1px solid #dddddd;">
                    <button type="button" class="dropbtn" id="EnterNewExperiment" title="Create Analysis" onclick="createNewExp_1()">Create Analysis</button>
                </div>
            </div>
                    
        </div>
                
        
        
    </div>
                
    <form name="fileInputFormDemo" method ="get" action="">
    <input type="hidden" name="newexperimentname" id="newexperimentname" value="demo" />           
    <input type="hidden" name="fileinputname" id="fileinputname" value="Brandes_et_al_GSE42638_Quantiled_Log_Transformed_Data.txt" />
    <input type="hidden" name="mapfilename" id="mapfilename" value="Brandes_et_al_GSE42638_Sample_Information.txt" />
    <!--<input type="hidden" name="headerflag" id="headerflag" value="on" />-->
    <input type="hidden" name="imputeval" id="imputeval" value="0" />
    <input type="hidden" name="delimval" id="delimval" value="tabS" />
    <input type="hidden" name="mapdelimval" id="mapdelimval" value="tabS" />
    <input type="hidden" name="species_name" id="species_name" value="mouse" />
    <input type="hidden" name="col_names" id="col_names" value="ID_REF,Gene,Entrez,SHAM_d01_rep1,SHAM_d01_rep2,SHAM_d01_rep3,SHAM_d01_rep4,SHAM_d01_rep5,SHAM_d01_rep6,SHAM_d01_rep7,SHAM_d02_rep1,SHAM_d02_rep2,SHAM_d02_rep3,SHAM_d02_rep4,SHAM_d02_rep5,SHAM_d02_rep6,SHAM_d02_rep7,SHAM_d03_rep1,SHAM_d03_rep2,SHAM_d03_rep3,SHAM_d03_rep4,SHAM_d03_rep5,SHAM_d03_rep6,SHAM_d03_rep7,SHAM_d10_rep1,SHAM_d10_rep2,SHAM_d10_rep3,SHAM_d10_rep4,SHAM_d10_rep5,SHAM_d10_rep6,SHAM_d10_rep7,TX91_d01_rep1,TX91_d01_rep2,TX91_d01_rep3,TX91_d01_rep4,TX91_d01_rep5,TX91_d01_rep6,TX91_d01_rep7,TX91_d02_rep1,TX91_d02_rep2,TX91_d02_rep3,TX91_d02_rep4,TX91_d02_rep5,TX91_d02_rep6,TX91_d02_rep7,TX91_d03_rep1,TX91_d03_rep2,TX91_d03_rep3,TX91_d03_rep4,TX91_d03_rep5,TX91_d03_rep6,TX91_d03_rep7,TX91_d10_rep1,TX91_d10_rep2,TX91_d10_rep3,TX91_d10_rep4,TX91_d10_rep5,TX91_d10_rep6,TX91_d10_rep7,PR80.2_d03_rep1,PR80.2_d03_rep2,PR80.2_d03_rep3,PR80.2_d03_rep4,PR80.2_d03_rep5,PR80.2_d03_rep6,PR80.2_d03_rep7,PR80.2_d10_rep1,PR80.2_d10_rep2,PR80.2_d10_rep3,PR80.2_d10_rep4,PR80.2_d10_rep5,PR80.2_d10_rep6,PR80.2_d10_rep7,PR80.6_d01_rep1,PR80.6_d01_rep2,PR80.6_d01_rep3,PR80.6_d01_rep4,PR80.6_d01_rep5,PR80.6_d01_rep6,PR80.6_d01_rep7,PR80.6_d03_rep1,PR80.6_d03_rep2,PR80.6_d03_rep3,PR80.6_d03_rep4,PR80.6_d03_rep5,PR80.6_d03_rep6,PR80.6_d03_rep7,PR80.6_d10_rep1,PR80.6_d10_rep2,PR80.6_d10_rep3,PR80.6_d10_rep4,PR80.6_d10_rep5,PR80.6_d10_rep6,PR80.6_d10_rep7,PR810_d01_rep1,PR810_d01_rep2,PR810_d01_rep3,PR810_d01_rep4,PR810_d01_rep5,PR810_d01_rep6,PR810_d01_rep7,PR810_d02_rep1,PR810_d02_rep2,PR810_d02_rep3,PR810_d02_rep4,PR810_d02_rep5,PR810_d02_rep6,PR810_d02_rep7,PR810_d03_rep1,PR810_d03_rep2,PR810_d03_rep3,PR810_d03_rep4,PR810_d03_rep5,PR810_d03_rep6,PR810_d03_rep7,PR8100_d01_rep1,PR8100_d01_rep2,PR8100_d01_rep3,PR8100_d01_rep4,PR8100_d01_rep5,PR8100_d01_rep6,PR8100_d01_rep7,PR8100_d02_rep1,PR8100_d02_rep2,PR8100_d02_rep3,PR8100_d02_rep4,PR8100_d02_rep5,PR8100_d02_rep6,PR8100_d02_rep7,PR8100_d03_rep1,PR8100_d03_rep2,PR8100_d03_rep3,PR8100_d03_rep4,PR8100_d03_rep5,PR8100_d03_rep6,PR8100_d03_rep7,ALUM_h06_rep1,ALUM_h06_rep2,ALUM_h06_rep3,ALUM_h06_rep4,ALUM_h06_rep5" />
    <input type="hidden" name="identifier_mappings" id="identifier_mappings" value="Gene,genesymbol_2021158607524066,Entrez,entrez_2021158607524066" />
    <input type="hidden" name="metacolnames" id="metacolnames" value="ID_REF,Gene,Entrez" />
    <input type="hidden" name="isTimeSeries" id="isTimeSeries" value="yes" />
    <input type="hidden" name="hasReplicates" id="hasReplicates" value="yes" />
    <input type="hidden" name="rowLoading" id="rowLoading" value="1" />
    <input type="hidden" name="txtFromRow" id="txtFromRow" value="" />
    <input type="hidden" name="txtToRow" id="txtToRow" value="" />
    <input type="hidden" name="isDemo" id="isDemo" value="yes" />
    <input type="hidden" name="fileinput_fullpath" id="fileinputname" value="<%=installPath + File.separator%>demo_data<%=File.separator%>Brandes_et_al_GSE42638_Quantiled_Log_Transformed_Data.txt" />
    <input type="hidden" name="mapfile_fullpath" id="mapfilename" value="<%=installPath + File.separator%>demo_data<%=File.separator%>Brandes_et_al_GSE42638_Sample_Information.txt" />
    
    

</form>                  



  
</body>

</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>