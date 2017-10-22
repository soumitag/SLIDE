/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function chkpassword(){
    var pass1 = document.getElementById("txtpass1").value;
    var pass2 = document.getElementById("txtpass2").value;
    if(pass1 != pass2){
        alert("Passwords do not match!");
        document.getElementById("txtpass1").value = "";
        document.getElementById("txtpass2").value = "";
    }
}

function chkemail(){
    var email_txt =  document.getElementById("txtemail").value;
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    
    var out = Boolean(re.test(email_txt));
    
    if(out !== true){
        document.getElementById("txtemail").value = "";
        alert("The email is not a valid one. Please check!");
    } 
}

function submitnewuser(f){
    document.signupPage.setAttribute("action","newuserdb.jsp");
    document.signupPage.submit();
}

function keyEnter(){
    //var formname = f.name;
    var keyVal = window.event.keyCode;
    if(keyVal == 13){
        document.homePage.setAttribute("action","newExperiment.jsp");
        document.homePage.submit();    
    }
}

function submitform_1(){
    var formname = "homePage";
    var value1 = document.getElementById("txtnewexperiment").value;
    //alert(value1);
    if (formname == "homePage") {
        if(value1 == ""){
            alert('Please insert a project name');
            //document.homePage.setAttribute("action","index.jsp");
            //document.homePage.submit();   
        } else{
            document.homePage.setAttribute("action","newExperiment.jsp");
            document.homePage.submit();        
        }
        
    }
}

// used after deletion
function update_search_result_post_deletion (analysis_name, num_current_searches) {
        
    document.getElementById('hdiFrame').contentWindow.refreshSearchPane();    
    document.getElementById('hdiFrame').width = parseFloat(document.getElementById('hdiFrame').width) - 32;
    
    document.getElementById('scrollPanel').width = parseFloat(document.getElementById('scrollPanel').width) - 32;
    document.getElementById('scrollPanel').contentWindow.refreshSearchPane();
    
    document.getElementById('searchKeysFrame').contentWindow.location.replace("searchKey.jsp?analysis_name=" + analysis_name);
    
    var drillDownPanel = document.getElementById('drillDownPanel');
    if (drillDownPanel === null) {
        
    } else {
        document.getElementById('drillDownPanel').width = parseFloat(document.getElementById('drillDownPanel').width) - 32;
        document.getElementById('drillDownPanel').contentWindow.refreshSearchPane();
    }
    
    if (num_current_searches == 0) {
        document.getElementById('searchKeysFrame').width = "250";
    }
}

function display_search_result (analysis_name) {
    document.getElementById('searchKeysFrame').width = "250";
    document.getElementById('hdiFrame').contentWindow.refreshSearchPane ();
    
    document.getElementById('hdiFrame').width = parseFloat(document.getElementById('hdiFrame').width) + 32;
    
    document.getElementById('scrollPanel').width = parseFloat(document.getElementById('scrollPanel').width) + 32;
    document.getElementById('scrollPanel').contentWindow.refreshSearchPane();
    
    document.getElementById('searchKeysFrame').contentWindow.location.replace("searchKey.jsp?analysis_name=" + analysis_name);
    
    var drillDownPanel = document.getElementById('drillDownPanel');
    if (drillDownPanel === null) {
        
    } else {
        document.getElementById('drillDownPanel').width = parseFloat(document.getElementById('drillDownPanel').width) + 32;
        document.getElementById('drillDownPanel').contentWindow.refreshSearchPane();
    }
}

function showDetailedInfo (eid, analysis_name) {
    var url_text = "gene.jsp?entrez_id=" + eid + "&analysis_name=" + analysis_name;
    document.getElementById('geneInfoFrame').contentWindow.location.replace(url_text);
}

function showDetailedPathInfo (pid, analysis_name) {
    var url_text = "pathway.jsp?pathway_id=" + pid + "&analysis_name=" + analysis_name;
    document.getElementById('geneInfoFrame').contentWindow.location.replace(url_text);
}

function showDetailedGOInfo (gid, analysis_name) {
    var url_text = "go.jsp?go_id=" + gid + "&analysis_name=" + analysis_name;
    document.getElementById('geneInfoFrame').contentWindow.location.replace(url_text);
}

var selected_search_number = -1;
var selected_search_tag_label = "";

function toggleHighlightGenes(search_no, pathid, state) {
    document.getElementById('hdiFrame').contentWindow.toggleHighlightGenes(pathid, state);
    document.getElementById('scrollPanel').contentWindow.toggleHighlightGenes(pathid, state);
    
    var drillDownPanel = document.getElementById('drillDownPanel');
    if (drillDownPanel === null) {
        
    } else {
        document.getElementById('drillDownPanel').contentWindow.toggleHighlightGenes(pathid, state);
    }
    
    selected_search_number = search_no;
    selected_search_tag_label = pathid;
}

function showGlobalMapRect(start, end) {
    document.getElementById('hdiFrame').contentWindow.hideRect();
    document.getElementById('hdiFrame').contentWindow.showRect(start, end);
}

function loadDrillDownPanel(analysis_name) {
    url_text = "drillDownPanel.jsp?show_dendrogram=yes&analysis_name=" + analysis_name;
    document.getElementById('drillDownPanel').contentWindow.location.replace(url_text);
}

function loadScrollViewPanel(analysis_name) {
    url_text = "scrollView.jsp?analysis_name=" + analysis_name;
    document.getElementById('scrollPanel').contentWindow.location.replace(url_text);
}

function loadHistPanel(analysis_name) {
    url_text = "histogram.jsp?analysis_name=" + analysis_name;
    document.getElementById('histPanel').contentWindow.location.replace(url_text);
}

function updateFeatureLists (list_name, add_remove_ind) {
    document.getElementById('scrollPanel').contentWindow.addRemoveListName(list_name, add_remove_ind);
    //alert(document.getElementById('searchKeysFrame').contentWindow);
    document.getElementById('searchKeysFrame').contentWindow.addRemoveListName(list_name, add_remove_ind);
    var drillDownPanel = document.getElementById('drillDownPanel');
    if (drillDownPanel === null) {
        
    } else {
        document.getElementById('drillDownPanel').contentWindow.addRemoveListName(list_name, add_remove_ind);
    }
    
}
