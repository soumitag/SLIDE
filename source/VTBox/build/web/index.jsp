<%-- 
    Document   : index
    Created on : 26 Jan, 2017, 10:33:05 AM
    Author     : soumitag
--%>

<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    String analysis_to_stop = request.getParameter("analysis_to_stop");
    if(analysis_to_stop != null){
        Object ac1 = session.getAttribute(analysis_to_stop);
        if(ac1 != null){
            session.removeAttribute(analysis_to_stop);
        }
    }
    
    String status = request.getParameter("status");
    
    session = request.getSession(false);
    String url = request.getRequestURL().toString();
    String base_url = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SLIDE</title>
        <link rel="stylesheet" href="vtbox-main.css">
        <script type = "text/javascript" language = "JavaScript" src="main.js"></script>
        <script type = "text/javascript" language = "JavaScript" src="userInput.js"></script>
        <script>
                
            function show_tab (tabname) {
                
                var create_tab = document.getElementById('create_table');
                var load_tab = document.getElementById('load_table');
                var open_tab = document.getElementById('open_table');
                var error_tab = document.getElementById('errormsg_table');
                
                if (tabname === 'creater') {
                    load_tab.style.display = 'none';
                    open_tab.style.display = 'none';
                    create_tab.style.display = 'inline';
                } else if (tabname === 'loader') {
                    create_tab.style.display = 'none';
                    open_tab.style.display = 'none';
                    load_tab.style.display = 'inline';
                } else if (tabname === 'opener') {
                    load_tab.style.display = 'none';
                    create_tab.style.display = 'none';
                    open_tab.style.display = 'inline';
                }
                
                if (error_msg_target !== tabname) {
                    error_tab.style.display = 'none';
                } else {
                    error_tab.style.display = 'inline';
                }
            }
            
            function stopAnalysis(analysis_to_stop){
                alert("Stop it");
                window.location.href("<%=base_url%>/index.jsp?analysis_to_stop=" + analysis_to_stop);
               
            }
                
        </script>
        <style>

            .maintable {
                position: absolute;
                left: 20%; 
                width: 60%;
                background-color: #fbfbfb;
            }
            
            .index_tables {
                position: absolute;
                left: 23%; 
                width: 54%;
                display: none;
                font-family: verdana;
                border-width: 1px; 
                border-color: #eaeaea; 
                border-style: solid;
                background-color: #ffffff;
                padding: 25px;
            }
            
            .error_table {
                position: absolute;
                left: 23%; 
                width: 54%;
                font-family: verdana;
                border-width: 1px; 
                border-color: #eaeaea; 
                border-style: solid;
                padding: 0px;
            }
            
            .index_tables.td {
                font-family: verdana;
                border-width: 1px; 
                border-color: #eaeaea; 
                border-style: solid;
                background-color: #f8f8f8;
            }
            
            .index_tables.td.error_msg {
                background-color: #ffff80;
            }

            /* The Modal (background) */
            .modal {
                display: none; /* Hidden by default */
                position: fixed; /* Stay in place */
                z-index: 1; /* Sit on top */
                padding-top: 100px; /* Location of the box */
                left: 0;
                top: 0;
                width: 100%; /* Full width */
                height: 100%; /* Full height */
                overflow: auto; /* Enable scroll if needed */
                background-color: rgb(0,0,0); /* Fallback color */
                background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
            }

            /* Modal Content */
            .modal-content {
                background-color: #fefefe;
                margin: auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                height: 500px;
            }

            /* The Close Button */
            .close {
                color: #aaaaaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close:hover,
            .close:focus {
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }
            
            .slide_head {
                text-align: center;
                font-family: verdana;
                font-size: 34px;
                font-stretch: extra-expanded;
                font-weight: bold;
                text-align: center;
            }
            
        </style>
    </head>
    <% if (status == null){ %>
        <body onload="doSysReq()">
    <% } else { %>
        <body>
    <%}%>
        <!-- The Modal -->
        <div id="myModal" class="modal">

            <!-- Modal content -->
            <div id="modal_inside" class="modal-content">
                <span id="close_span" class="close">&times;</span>
                <iframe name="modalFrame" id="modalFrame" src="" marginwidth="0" height="500" width="100%" frameBorder="0" style="position: relative; top: 0px; left: 0px"></iframe>
            </div>

        </div>
        
        
                <table class="maintable" cellpadding="5" border="0" style="top: 10px">
                        <tr>
                            <td class="slide_head" align="center" colspan="3">
                                S&nbsp;L&nbsp;I&nbsp;D&nbsp;E
                            </td>
                        </tr>
                        <tr>
                            <td class="msg" align="center" colspan="3">
                                Systems Level Interactive Data Exploration
                                <br><br>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                               <button class="dropbtn" onclick="show_tab('creater')"> Create New Analysis </button> 
                            </td>
                            <td align="center">
                                <button class="dropbtn" onclick="show_tab('loader')"> Load Analysis From File </button> 
                            </td>
                            <td align="center">
                                <button class="dropbtn" onclick="show_tab('opener')"> Open Active Analysis </button> 
                            </td>
                        </tr>
                </table>
            
                <br>
            
                <% 
                    if (status != null && status.equalsIgnoreCase("analysis_exists")) { 
                        String analysis_name = request.getParameter("analysis_name");
                %>
                <table id="errormsg_table" class="error_table" cellpadding="5" border="0" style="top: 170px">
                        <tr>
                            <td class="error_msg" align="center" style="background-color: #ffff80;">
                                Please select a different analysis name. <%=analysis_name%> already exists.
                            </td>
                        </tr>
                </table>
                <% } else if (status != null) { %>
                <table id="errormsg_table" class="error_table" cellpadding="5" border="0" style="top: 170px">
                        <tr>
                            <td class="error_msg" align="center" style="background-color: #ffff80;">
                                <%=status%>
                            </td>
                        </tr>
                </table>
                <% } %>
                
                <table id="create_table" class="index_tables" cellpadding="5" border="0" style="top: 204px">
                        <tr>
                            <td>
                                <b>
                                    Create New Experiment
                                </b>
                            </td>
                        </tr>
                    </tr>
                    <tr>
                        <form name="homePage" method ="get" action="">
                        <td colspan="2">
                            <label>Experiment Name</label>
                            <input type="text" id="txtnewexperiment" name="txtnewexperiment" onkeypress="keyEnter()" />
                            &nbsp;&nbsp;
                            <button class="dropbtn" id="NewExperiment" title="New Experiment" onclick="submitform_1();">Create</button>
                        </td>
                        </form>
                    </tr>
                </table>
                        
                <br>
                <br>
                
                <table id="load_table" class="index_tables" cellpadding="5" border="0" style="top: 204px">
                    <tr>
                        <td>
                            <b>
                                Load Analysis From File
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form name="upload_slide_form" id="upload_slide_form" action="<%=base_url%>/LoadAnalysis" method="post" enctype="multipart/form-data" target="_self" >
                                <input type="file" id="slidefilename" name="slidefilename" size="70"/>
                                &nbsp;
                                <button type="button" id="slide_upload_btn" title="Open Selected Analysis File" onclick="uploadSlideFile();">Upload</button>
                                <br>
                                <label class="msg">Select a "*.slide" file to load</label>
                            </form>
                        </td>
                    </tr>
                </table>
                                
                <br>
                <br>
                
                <table id="open_table" class="index_tables" cellpadding="5" border="0" style="top: 204px">
                    <tr>
                        <td colspan="2"><label><b> Active Analyses</b></label></td>
                    </tr>
                    <% 
                        boolean hasRunningOnes = false;
                        if(session != null) {

                            Enumeration keys = session.getAttributeNames();

                            while (keys.hasMoreElements()){
                                String key = (String)keys.nextElement();
                                Object obj = session.getAttribute(key);
                                if (obj instanceof AnalysisContainer){
                                    AnalysisContainer ac = (AnalysisContainer)obj;
                                    hasRunningOnes = true;
                    %>
                                    <tr>
                                        <td>
                                            <label>
                                                <%=ac.analysis_name%>
                                            </label>
                                        </td>
                                        <td>
                                            <button class="dropbtn" id="openAnalysis" title="Go to Analysis" onclick="window.open('<%=base_url%>displayHome.jsp?analysis_name=<%=ac.analysis_name%>&load_type=reopen')">Go To Analysis</button>
                                            <button class="dropbtn" id="stopAnalysis" title="Stop Analysis" onclick="stopAnalysis('<%=ac.analysis_name%>')">Stop</button>
                                        </td>
                                    </tr>
                    <%            
                                }
                            }
                        }
                    %>
                    
                    <%  
                        if (!hasRunningOnes) {  
                    %>
                        <tr>
                            <td class="msg" colspan="2">
                                <label>
                                    <i> No active analysis found. </i>
                                </label>
                            </td>
                        </tr>
                    
                    <% } %>
                    
                </table>

    </body>
    
    <script>
    <%
        String target_tab = request.getParameter("target");
        if (target_tab != null) {
            if (target_tab.equalsIgnoreCase("creater")) {
    %>
                var error_msg_target = 'creater';    
                show_tab('creater');
    <%    
            } else if (target_tab.equalsIgnoreCase("loader")) {
    %>
                var error_msg_target = 'loader';
                show_tab('loader');
    <%    
            } else if (target_tab.equalsIgnoreCase("opener")) {
    %>
                var error_msg_target = 'opener';
                show_tab('opener');
    <%    
            }
        }
    %>
    
    
            // Get the modal
            var modal = document.getElementById('myModal');
            // Get the <span> element that closes the modal
            var span = document.getElementById("close_span");

            var modal_inside = document.getElementById('modal_inside');
            var modal_frame = document.getElementById('modalFrame');

            // When the user clicks the button, open the modal 
            function showModalWindow(src, width, height) {
                modal_frame.src = src;
                modal.style.display = "block";
                modal_inside.style.width = width;
                modal_inside.style.height = height;
            }

            // When the user clicks on <span> (x), close the modal
            span.onclick = function() {
                modal.style.display = "none";
            }

            // When the user clicks anywhere outside of the modal, close it
            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
            
        
            function doSysReq() { 
                var browser = "";
                var browserApp= "";
                var browserVersion = "";
                    if((navigator.userAgent.indexOf("Opera") || navigator.userAgent.indexOf('OPR')) != -1 ) {
                        browserApp = "Opera";
                        browserVersion = navigator.appVersion; 
                    } else if(navigator.userAgent.indexOf("Chrome") != -1 ){
                        if(navigator.userAgent.indexOf("Edge") > -1){
                            browserApp = "Edge";
                            browserVersion = navigator.appVersion; 
                        }else{
                            browserApp = "Chrome";
                            browserVersion = navigator.appVersion; 
                        }
                    } else if(navigator.userAgent.indexOf("Safari") != -1){
                        browserApp = "Safari";
                        browserVersion = navigator.appVersion;                     
                    } else if(navigator.userAgent.indexOf("Firefox") != -1 ){
                        browserApp = "Firefox";
                        browserVersion = navigator.appVersion; 
                    } else if((navigator.userAgent.indexOf("MSIE") != -1 || navigator.appVersion.indexOf('Trident/') > 0)) {//IF IE > 10 
                        browser = "ie";
                        browserApp = "Internet Explorer";
                        browserVersion = navigator.appVersion; 
                    } else {
                        browserApp = "Unknown";
                    }

                    var OSName = "Unknown";
                        if (window.navigator.userAgent.indexOf("Windows NT 10.0")!= -1) 
                            OSName="Windows 10";
                        if (window.navigator.userAgent.indexOf("Windows NT 6.2") != -1) 
                            OSName="Windows 8";
                        if (window.navigator.userAgent.indexOf("Windows NT 6.1") != -1) 
                            OSName="Windows 7";
                        if (window.navigator.userAgent.indexOf("Windows NT 6.0") != -1) 
                            OSName="Windows Vista";
                        if (window.navigator.userAgent.indexOf("Windows NT 5.1") != -1) 
                            OSName="Windows XP";
                        if (window.navigator.userAgent.indexOf("Windows NT 5.0") != -1) 
                            OSName="Windows 2000";
                        if (window.navigator.userAgent.indexOf("Mac")!= -1) 
                            OSName="Mac/iOS";
                        if (window.navigator.userAgent.indexOf("X11")!= -1) 
                            OSName="UNIX";
                        if (window.navigator.userAgent.indexOf("Linux")!= -1) 
                            OSName="Linux";

                    var browserRes_W = window.screen.availWidth;
                    var browserRes_H = window.screen.availHeight; 
                    //var screenRes = screen.width + " X " + screen.height; 

                    //showModalWindow('sysreq.jsp?browser_app='+browserApp+'&browser_ver='+browserVersion+'&os_name='+OSName+'&browser_res='+browserRes+'&screen_res='+screenRes, '50%', '30%');
                    
                    if (browserApp !== "Internet Explorer" || (browserRes_W < 1920 || browserRes_H < 1080)) {
                        showModalWindow('sysreq.jsp?browser_app='+ browserApp + '&browser_res_w=' + browserRes_W + '&browser_res_h=' + browserRes_H, '50%', '30%');
                    }
                }
    
    </script>
    
</html>
