/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getfilepathfrombrowse(usecase){
    if (usecase == "data") {
        var filename = document.getElementById("selectmrnafilename").value;
        document.getElementById("txtmrnafilename").value = filename;
        document.getElementById("fileinputname").value = filename;
    } else if  (usecase == "map") {
        var filename = document.getElementById("selectmapfilename").value;
        document.getElementById("txtmapfilename").value = filename;
        document.getElementById("mapfilename").value = filename;
        document.getElementById("mappingPreview").style.visibility = "visible";
    }
}

function getinputfilenamefromtext(usecase, app_type){
    
    if (usecase == "data" && app_type == "standalone") {
        var filename = document.getElementById("txtmrnafilename").value;
        document.getElementById("fileinputname").value = filename;
    }
    
    // if app_type = web no need to handle it here, it is already handled in uploadCompletion() in newExperiment.jsp
    
    if  (usecase == "map") {
        
        var filename = document.getElementById("txtmapfilename").value;
        document.getElementById("mapfilename").value = filename;
        document.getElementById("mappingPreview").style.visibility = "visible";
    }
}

function getimputevalue(){
    var d = document.getElementById("imputeD");
    var optionImpute = d.options[d.selectedIndex].value;
    document.getElementById("imputeval").value = optionImpute;   
}

function getdelimitervalue(){
    var d = document.getElementById("delimS");
    var optionDelim = d.options[d.selectedIndex].value;
    document.getElementById("delimval").value = optionDelim;
    document.getElementById("Preview").style.visibility = "visible";
}

function getmapdelimitervalue(){
    var d = document.getElementById("mapDelimS");
    var optionDelim = d.options[d.selectedIndex].value;
    document.getElementById("mapdelimval").value = optionDelim;
}

function getspeciesname(){
    var d = document.getElementById("species");
    var optionSpecies = d.options[d.selectedIndex].value;
    document.getElementById("species_name").value = optionSpecies;
    if (optionSpecies === "other") {
        document.getElementById("metadata_mapping_tr").style.display = "none";
        alert("When species is 'Other' search and tagging features will not be available.");
    } else {
        document.getElementById("metadata_mapping_tr").style.display = "table-row";
    }
}

function filePreview(analysis_name, upload_type){
    //alert(document.getElementById('Preview').innerText);
    if(document.getElementById('Preview').innerText.toLowerCase() === "preview"){
        var filestr = document.getElementById("fileinputname").value;
        var filedelim = document.getElementById("delimval").value; 
        //var fileheader = document.getElementById("headerflag").checked;
        var fileheader = "on";    
        var col_header_str = "";

        //var url_text = "inputPreview.jsp?file=" +filestr + "&delim=" + filedelim + "&analysis_name=" + analysis_name + "&head=" + fileheader + "&col_header=" + col_header_str;

        var url_text = "inputPreview.jsp?file=" +filestr + "&delim=" + filedelim + "&analysis_name=" + analysis_name + "&head=" + fileheader + "&upload_type=" + upload_type + "&col_header=" + col_header_str;

        document.getElementById('previewFrame').contentWindow.location.replace(url_text);
        document.getElementById('previewFrame').style.display = "inline";
        document.getElementById('previewFrame').style.height = 200;
        document.getElementById('previewFrame').style.width = "100%";
        document.getElementById('Preview').innerText = "Close Preview";
        //alert(document.getElementById('Preview').innerText);    
    }else if(document.getElementById('Preview').innerText.toLowerCase() === "close preview"){
        document.getElementById('previewFrame').style.display = "none";
        document.getElementById('Preview').innerText = "Preview";
    }
}

function mapFilePreview(){
    
    var filestr = document.getElementById("mapfilename").value;
    var url_text = "sampleMappingPreview.jsp?file=" + filestr;
    document.getElementById('mappingPreviewFrame').contentWindow.location.replace(url_text);
    
    document.getElementById('mappingPreviewFrame').style.display = "inline";
    document.getElementById('mappingPreviewFrame').style.height = 200;
    document.getElementById('mappingPreviewFrame').style.width = "100%";
    
}

function checkselectoptions(){
    
    var x = 1;
    if(document.getElementById("imputeval").value == ""){
        x = 0;
        alert("Please select a data imputation strategy.");
    }
    if(document.getElementById("delimval").value == ""){
        x = 0;
        alert("Please select a delimiter for the data file.");
    }
    if(document.getElementById("species_name").value == ""){
        x = 0;
        alert("Please select the species type.");
    }
    
    return x;
    
}


function setTimeSeriesAs(str) {
    document.getElementById('isTimeSeries').value = str;
    
    if(str === 'yes'){
        document.getElementById('sample_attributes_div').style.display = "inline";
        document.getElementById('isTimeSeries').value = 'yes';
        //alert(document.getElementById('isTimeSeries').value);
    } else if(str === 'no') {
        str1 = document.getElementById('hasReplicates').value;
        document.getElementById('isTimeSeries').value = 'no';
        if (str1 === 'no'){
            document.getElementById('sample_attributes_div').style.display = "none";
        }
    }
}

function setReplicatesAs(str) {
    document.getElementById('hasReplicates').value = str;
    
    if(str === 'yes'){
        document.getElementById('sample_attributes_div').style.display = "inline";
        document.getElementById('hasReplicates').value = 'yes';
        document.getElementById('time_series_td').style.display = "table-row";
        //alert(document.getElementById('hasReplicates').value);
    } else if(str === 'no') {
        str1 = document.getElementById('isTimeSeries').value;
        document.getElementById('hasReplicates').value = 'no';
        document.getElementById('sample_attributes_div').style.display = "none";
        document.getElementById('time_series_td').style.display = "none";
        if (str1 === 'no'){
            document.getElementById('sample_attributes_div').style.display = "none";
            document.getElementById('time_series_td').style.display = "none";
        }
    }
}

function showrepnumchk(){
    document.getElementById("lblreplnum").style.visibility = "visible";
}

function chkrepltextboxes(){
    
    document.getElementById("chkreplnum").checked = false;
    var flag = chkvalisnum(this.value);
    
    if(flag){
        alert("Please enter a numeric value");  
    }
}

function chkcolrange () {
    
    // ^(?!([ \d]*-){2})\d+(?: *[-,] *\d+)*$ (regex in java)
    var x = this.value;
    var y = dotrim(x);
    var pattern = /^(\s*\d+\s*\-\s*\d+\s*,?|\s*\d+\s*,?)+$/g;
    /* 
     * ^ matches beginning of input 
     * \d any digit character
     * g is for global search
     * 
     */
    
    var out = Boolean(pattern.test(y));
    
    if(out !== true) {
        this.value = "";
        alert("Please enter number in the correct format");
    } 

}

function fillreplbox(){
    var x = document.getElementById("chkreplnum").checked;
    if(x) {
        var y = document.getElementById("txtRep11").value;
        
        var z = document.getElementById("txtNumExp").value;
        for (var i = 2; i <= z ; i++){
            var tStr = "txtRep1"+i;
            document.getElementById(tStr).value = y;
        } 
    } 
}

function colheadername(){
    var x = document.getElementById("chkrecols").checked;
    if(x) {
        
        document.getElementById("chkrenameval").value = "yes";
        document.getElementById("updateHeader").style.visibility = "visible";
        var filestr = document.getElementById("fileinputname").value;
        var filedelim = document.getElementById("delimval").value;
        var fileheader = document.getElementById("headerflag").checked;
        
        var url_text = "colHeader.jsp?file=" +filestr + "&delim=" + filedelim + "&head=" + fileheader;
        
        document.getElementById('colHeaderFrame').contentWindow.location.replace(url_text);
        document.getElementById('colHeaderFrame').style.display = "inline";
        document.getElementById('colHeaderFrame').style.height = 250;
        document.getElementById('colHeaderFrame').style.width = "100%";
        
    } else {
        document.getElementById("chkrenameval").value = "no";
        document.getElementById('colHeaderFrame').style.display = "none";
        document.getElementById("updateHeader").style.visibility = "hidden";
    }
}

function addcolinfo(){
    document.getElementById("tdcolnum").style.visibility = "visible";
    var x = document.getElementById("txtNumExp").value;
    var x_num = parseInt(x);
    var index = 1;
    for (var i = 1; i <= x_num; i++) {
            // table row creation               
            document.getElementById("txtSCol"+index).style.visibility = "visible";
            index++;
        }    
}

function removecolinfo(){
    
    document.getElementById("tdcolnum").style.visibility = "hidden";
    var x = document.getElementById("txtNumExp").value;
    var x_num = parseInt(x);
    var index = 1;
    for (var i = 1; i <= x_num; i++) {
            // table row creation               
            document.getElementById("txtSCol"+index).style.visibility = "hidden";
            //document.getElementById("txtRep1"+index).value = "1";
            index++;
        }  
    
}

function handlechksort(){
    var x = document.getElementById("chksort").checked;
    
}

function disptimeseries(){
    
    var x = document.getElementById("txtNumExp").value;
    var x_num = parseInt(x);    
    var y = document.getElementById("txtnumseries").value;
    
    document.getElementById("btnstotime").value = "on"; 
    
    var flag = chkvalisnum(y);
    
    if(flag){
        document.getElementById("txtnumseries").value = "0";
        alert("Please enter a numeric value");
    } else {   
    
        var index = 1;
        var sample_name_str ="";
        for (var i = 1; i <= x_num; i++) {
                // table row creation               
                
                sample_name_str = sample_name_str +  document.getElementById("txtSamp1"+index).value + ",";
                index++;
                
        }    
        
        var url_text = "timeseriestablePreview.jsp?numexp=" +x + "&numtseries=" + y + "&samplenames=" + sample_name_str;
        
        document.getElementById('previewTimeSeries').contentWindow.location.replace(url_text);        
        document.getElementById('previewTimeSeries').style.display = "inline";
        document.getElementById('previewTimeSeries').style.height = 65 + x*35;
        document.getElementById('previewTimeSeries').style.width = "100%";
        
    }
    
}

function updateheaderinfo(){
    var col_ifrm = document.getElementById('colHeaderFrame');
    var doc = col_ifrm.contentDocument? col_ifrm.contentDocument: col_ifrm.contentWindow.document;
    var col_num = doc.forms['colHeaderForm'].elements['totalcols'].value;
    var col_header_str ="";
    for (var i = 0; i < col_num; i++){
        col_header_str = col_header_str + doc.forms['colHeaderForm'].elements['txtCH'+i].value + ",";
    }
    document.getElementById("colHeaderStr").value = col_header_str;
    
    var filestr = document.getElementById("fileinputname").value;
    
    var filedelim = document.getElementById("delimval").value;
        
    var fileheader = document.getElementById("headerflag").checked;
        
    var url_text = "inputPreview.jsp?file=" +filestr + "&delim=" + filedelim + "&head=" + fileheader + "&col_header=" + col_header_str;
    
    document.getElementById('previewFrame').contentWindow.location.replace(url_text);
    document.getElementById('previewFrame').style.display = "inline";
    document.getElementById('previewFrame').style.height = 200;
    document.getElementById('previewFrame').style.width = "100%";  
    
}      

function chkisnumber(f){
    
    var x = document.getElementById(f.name).value;
    if(isNaN(parseFloat(x)) || !isFinite(x)){        
        document.getElementById(f.name).value = "0";
        alert("Please enter a numeric value");        
    }    
}

function chkisEmptyOrNumber(f){
    
    var x = document.getElementById(f.name).value;   
    if (x !== "") {
        if(isNaN(parseFloat(x)) || !isFinite(x)){        
            document.getElementById(f.name).value = "0";
            alert("Please enter a numeric value");
        } else if (parseInt(x) < 1) {
            alert("Column number cannot be less than 1.");
        }
    }
}

function chkvalisnum(val){
    
    return (isNaN(parseFloat(val)) || !isFinite(val));
}

function dotrim(x) {
    return x.replace(/^\s+|\s+$/gm,'');
}

function chknumrange (f) {
    
    // ^(?!([ \d]*-){2})\d+(?: *[-,] *\d+)*$ (regex in java)
    var x = document.getElementById(f.name).value;
    var y = dotrim(x);
    var pattern = /^(\s*\d+\s*\-\s*\d+\s*,?|\s*\d+\s*,?)+$/g;
    
    /* 
     * ^ matches beginning of input 
     * \d any digit character
     * g is for global search
     * 
     */
    
    var out = Boolean(pattern.test(y));
    
    if(out !== true){
        document.getElementById(f.name).value = "0";
        alert("Please enter number in the correct format");
    } 
    
}

function uploadDataFile() {
    // start uploading file to server
    var x = document.getElementById('selectmrnafilename').value;
    var d = document.getElementById("delimval").value;
    
    if (x == ""){
        alert("Please select a data file.");
    } else if (d == "") {
        alert("Please select the delimiter used in data file.");
    } else {
        document.getElementById('notice_board').style.display = 'none';
        //document.getElementById('input_table').style.display = 'none';
        document.getElementById('analysis_params_div').style.display = 'none';
        document.getElementById("data_upload_btn").disabled = true;
        document.getElementById('data_upload_btn').innerHTML = 'Uploading...';
        var form = document.getElementById('data_upload_form');
        var form_action = document.getElementById('data_upload_form_action').value;
        form.action = form_action + "&delimval=" + document.getElementById('delimval').value;
        //alert(form.action);
        form.submit();
    }
}

function uploadMappingFile() {
    //alert('but why');
    var y = document.getElementById('select_map_filename').value;
    var d = document.getElementById("mapdelimval").value;
    var t = document.getElementById('isTimeSeries').value;
    var r = document.getElementById('hasReplicates').value;
    var x = document.getElementById('fileinputname').value;
    var e = document.getElementById("delimval").value;

    if (y === ""){
        alert("Please select a mapping file.");
    } else if (d === "") {
        alert("Please select the delimiter used in mapping file.");
    } else {
        document.getElementById('mapping_notice_board').style.display = 'none';
        document.getElementById("mapUploadButton").disabled = true;
        document.getElementById('mapUploadButton').innerHTML = 'Uploading...';
        var form_action = document.getElementById('mapping_upload_form_action').value;
        var form = document.getElementById('sampleAttributeForm');
        form.action = form_action + "&delimval=" + d + 
                                    "&is_time_course=" + t + 
                                    "&has_replicates=" + r + 
                                    "&data_filename=" + x + 
                                    "&data_fileDelimiter=" + e;
        //alert(form.action);
        form.submit();
    }
}

function uploadSlideFile() {
    // start uploading file to server
    document.getElementById("slide_upload_btn").disabled = true;
    document.getElementById('slide_upload_btn').innerHTML = 'Uploading...';
    //alert(document.getElementById('upload_slide_form'));
    document.getElementById('upload_slide_form').submit();    
}

