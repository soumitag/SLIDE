/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function replicateHandling(num){
    var repl = document.getElementsByName("repHandle");      
    repl[num].checked = true;
}

function columnScaling(num){
    var colscale = document.getElementsByName("normRule_Col");
    colscale[num].checked = true;
}

function rowScaling(num){
    //alert("row scale option" +num);
    var rowscale = document.getElementsByName("normRule_Row");
    rowscale[num].checked = true;
}

function groupBy(num){
    var grp = document.getElementsByName("groupBy");
    grp[num].checked = true;
}

function linkageFunc(num){
    var linkage_func = document.getElementsByName("linkFunc");
    linkage_func[num].checked = true;
}

function distFunc(num){
    var dist_func = document.getElementsByName("distFunc");
    dist_func[num].checked = true;
}

function binRange(num){
    var binning_range = document.getElementsByName("binningRange");
    binning_range[num].checked = true;
}

function leafOrder(num){
    var leaf_order = document.getElementsByName("leafOrder");
    leaf_order[num].checked = true;
}

function checkLogTransform(flag){
    if(flag){
        document.getElementById("log2flag").checked = true;
    } else {
        document.getElementById("log2flag").checked = false;
    }
}

function checkHierarchical(flag){
    if(flag){
        document.getElementById("do_cluster_flag").checked = true;
    } else {
        document.getElementById("do_cluster_flag").checked = false;
    }
}

function checkDataClipMinMax(num, min, max){    
    var clip = document.getElementById("clippingType");
    clip.selectedIndex = num;
    if (num === 0){
        document.getElementById("txtClipMin").value = "";
    } else {
        document.getElementById("txtClipMin").value = min;
    }
    
    if(num === 0){
        document.getElementById("txtClipMax").value = "";
    } else {
        document.getElementById("txtClipMax").value = max;
    }
}

function colorBins(val){
    //alert(val);
    document.getElementById("txtNBins").value = val;
}

function rowLabelType(label) {
    var rlabel = document.getElementById("identifierType");
    for (var i = 0; i < rlabel.options.length; i++) {
        if (rlabel.options[i].value === label) {
            rlabel.options[i].selected = true;
            break;
        }
    }
}

function binRangeStartEnd(num, start, end){
    //alert(num);
    //alert(start);
    //alert(end);
    if (num === 2) {
        document.getElementById("txtRangeStart").value = start;
        document.getElementById("txtRangeEnd").value = end;
        document.getElementById("txtRangeStart").disabled = false;
        document.getElementById("txtRangeEnd").disabled = false;
    }
    
}

function setSignificanceLevel (val) {
    document.getElementById("txtSignificanceLevel").value = val;
}

function set_small_k (val) {
    document.getElementById("txtSmall_k").value = val;
}

function set_Big_K (val) {
    document.getElementById("txtBig_K").value = val;
}

