package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import structure.AnalysisContainer;
import java.util.Enumeration;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
 
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

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>SLIDE</title>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <script type = \"text/javascript\" language = \"JavaScript\" src=\"main.js\"></script>\n");
      out.write("        <script type = \"text/javascript\" language = \"JavaScript\" src=\"userInput.js\"></script>\n");
      out.write("        <script>\n");
      out.write("                \n");
      out.write("            function show_tab (tabname) {\n");
      out.write("                \n");
      out.write("                var create_tab = document.getElementById('create_table');\n");
      out.write("                var load_tab = document.getElementById('load_table');\n");
      out.write("                var open_tab = document.getElementById('open_table');\n");
      out.write("                var error_tab = document.getElementById('errormsg_table');\n");
      out.write("                \n");
      out.write("                if (tabname === 'creater') {\n");
      out.write("                    load_tab.style.display = 'none';\n");
      out.write("                    open_tab.style.display = 'none';\n");
      out.write("                    create_tab.style.display = 'inline';\n");
      out.write("                } else if (tabname === 'loader') {\n");
      out.write("                    create_tab.style.display = 'none';\n");
      out.write("                    open_tab.style.display = 'none';\n");
      out.write("                    load_tab.style.display = 'inline';\n");
      out.write("                } else if (tabname === 'opener') {\n");
      out.write("                    load_tab.style.display = 'none';\n");
      out.write("                    create_tab.style.display = 'none';\n");
      out.write("                    open_tab.style.display = 'inline';\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                if (error_msg_target !== tabname) {\n");
      out.write("                    error_tab.style.display = 'none';\n");
      out.write("                } else {\n");
      out.write("                    error_tab.style.display = 'inline';\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function stopAnalysis(analysis_to_stop){\n");
      out.write("                //alert(\"Stop it\");\n");
      out.write("                window.location.href(\"");
      out.print(base_url);
      out.write("/index.jsp?analysis_to_stop=\" + analysis_to_stop);\n");
      out.write("               \n");
      out.write("            }\n");
      out.write("                \n");
      out.write("        </script>\n");
      out.write("        <style>\n");
      out.write("\n");
      out.write("            .maintable {\n");
      out.write("                position: absolute;\n");
      out.write("                left: 20%; \n");
      out.write("                width: 60%;\n");
      out.write("                background-color: #fbfbfb;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .index_tables {\n");
      out.write("                position: absolute;\n");
      out.write("                left: 23%; \n");
      out.write("                width: 54%;\n");
      out.write("                display: none;\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-width: 1px; \n");
      out.write("                border-color: #eaeaea; \n");
      out.write("                border-style: solid;\n");
      out.write("                background-color: #ffffff;\n");
      out.write("                padding: 25px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .error_table {\n");
      out.write("                position: absolute;\n");
      out.write("                left: 23%; \n");
      out.write("                width: 54%;\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-width: 1px; \n");
      out.write("                border-color: #eaeaea; \n");
      out.write("                border-style: solid;\n");
      out.write("                padding: 0px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .index_tables.td {\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-width: 1px; \n");
      out.write("                border-color: #eaeaea; \n");
      out.write("                border-style: solid;\n");
      out.write("                background-color: #f8f8f8;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .index_tables.td.error_msg {\n");
      out.write("                background-color: #ffff80;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* The Modal (background) */\n");
      out.write("            .modal {\n");
      out.write("                display: none; /* Hidden by default */\n");
      out.write("                position: fixed; /* Stay in place */\n");
      out.write("                z-index: 1; /* Sit on top */\n");
      out.write("                padding-top: 100px; /* Location of the box */\n");
      out.write("                left: 0;\n");
      out.write("                top: 0;\n");
      out.write("                width: 100%; /* Full width */\n");
      out.write("                height: 100%; /* Full height */\n");
      out.write("                overflow: auto; /* Enable scroll if needed */\n");
      out.write("                background-color: rgb(0,0,0); /* Fallback color */\n");
      out.write("                background-color: rgba(0,0,0,0.4); /* Black w/ opacity */\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* Modal Content */\n");
      out.write("            .modal-content {\n");
      out.write("                background-color: #fefefe;\n");
      out.write("                margin: auto;\n");
      out.write("                padding: 20px;\n");
      out.write("                border: 1px solid #888;\n");
      out.write("                width: 80%;\n");
      out.write("                height: 500px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            /* The Close Button */\n");
      out.write("            .close {\n");
      out.write("                color: #aaaaaa;\n");
      out.write("                float: right;\n");
      out.write("                font-size: 28px;\n");
      out.write("                font-weight: bold;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            .close:hover,\n");
      out.write("            .close:focus {\n");
      out.write("                color: #000;\n");
      out.write("                text-decoration: none;\n");
      out.write("                cursor: pointer;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .slide_head {\n");
      out.write("                text-align: center;\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 34px;\n");
      out.write("                font-stretch: extra-expanded;\n");
      out.write("                font-weight: bold;\n");
      out.write("                text-align: center;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    ");
 if (status == null){ 
      out.write("\n");
      out.write("        <body onload=\"doSysReq()\">\n");
      out.write("    ");
 } else { 
      out.write("\n");
      out.write("        <body>\n");
      out.write("    ");
}
      out.write("\n");
      out.write("        <!-- The Modal -->\n");
      out.write("        <div id=\"myModal\" class=\"modal\">\n");
      out.write("\n");
      out.write("            <!-- Modal content -->\n");
      out.write("            <div id=\"modal_inside\" class=\"modal-content\">\n");
      out.write("                <span id=\"close_span\" class=\"close\">&times;</span>\n");
      out.write("                <iframe name=\"modalFrame\" id=\"modalFrame\" src=\"\" marginwidth=\"0\" height=\"500\" width=\"100%\" frameBorder=\"0\" style=\"position: relative; top: 0px; left: 0px\"></iframe>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        \n");
      out.write("                <table class=\"maintable\" cellpadding=\"5\" border=\"0\" style=\"top: 10px\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"slide_head\" align=\"center\" colspan=\"3\">\n");
      out.write("                                S&nbsp;L&nbsp;I&nbsp;D&nbsp;E\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"msg\" align=\"center\" colspan=\"3\">\n");
      out.write("                                Systems Level Interactive Data Exploration\n");
      out.write("                                <br><br>\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                        <tr>\n");
      out.write("                            <td align=\"center\">\n");
      out.write("                               <button class=\"dropbtn\" onclick=\"show_tab('creater')\"> Create New Analysis </button> \n");
      out.write("                            </td>\n");
      out.write("                            <td align=\"center\">\n");
      out.write("                                <button class=\"dropbtn\" onclick=\"show_tab('loader')\"> Load Analysis From File </button> \n");
      out.write("                            </td>\n");
      out.write("                            <td align=\"center\">\n");
      out.write("                                <button class=\"dropbtn\" onclick=\"show_tab('opener')\"> Open Active Analysis </button> \n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                </table>\n");
      out.write("            \n");
      out.write("                <br>\n");
      out.write("            \n");
      out.write("                ");
 
                    if (status != null && status.equalsIgnoreCase("analysis_exists")) { 
                        String analysis_name = request.getParameter("analysis_name");
                
      out.write("\n");
      out.write("                <table id=\"errormsg_table\" class=\"error_table\" cellpadding=\"5\" border=\"0\" style=\"top: 170px\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"error_msg\" align=\"center\" style=\"background-color: #ffff80;\">\n");
      out.write("                                Please select a different analysis name. ");
      out.print(analysis_name);
      out.write(" already exists.\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                </table>\n");
      out.write("                ");
 } else if (status != null) { 
      out.write("\n");
      out.write("                <table id=\"errormsg_table\" class=\"error_table\" cellpadding=\"5\" border=\"0\" style=\"top: 170px\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"error_msg\" align=\"center\" style=\"background-color: #ffff80;\">\n");
      out.write("                                ");
      out.print(status);
      out.write("\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                </table>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                \n");
      out.write("                <table id=\"create_table\" class=\"index_tables\" cellpadding=\"5\" border=\"0\" style=\"top: 204px\">\n");
      out.write("                        <tr>\n");
      out.write("                            <td>\n");
      out.write("                                <b>\n");
      out.write("                                    Create New Experiment\n");
      out.write("                                </b>\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <form name=\"homePage\" method =\"get\" action=\"\">\n");
      out.write("                        <td colspan=\"2\">\n");
      out.write("                            <label>Experiment Name</label>\n");
      out.write("                            <input type=\"text\" id=\"txtnewexperiment\" name=\"txtnewexperiment\" onkeypress=\"keyEnter()\" />\n");
      out.write("                            &nbsp;&nbsp;\n");
      out.write("                            <button class=\"dropbtn\" id=\"NewExperiment\" title=\"New Experiment\" onclick=\"submitform_1();\">Create</button>\n");
      out.write("                        </td>\n");
      out.write("                        </form>\n");
      out.write("                    </tr>\n");
      out.write("                </table>\n");
      out.write("                        \n");
      out.write("                <br>\n");
      out.write("                <br>\n");
      out.write("                \n");
      out.write("                <table id=\"load_table\" class=\"index_tables\" cellpadding=\"5\" border=\"0\" style=\"top: 204px\">\n");
      out.write("                    <tr>\n");
      out.write("                        <td>\n");
      out.write("                            <b>\n");
      out.write("                                Load Analysis From File\n");
      out.write("                            </b>\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <td>\n");
      out.write("                            <form name=\"upload_slide_form\" id=\"upload_slide_form\" action=\"");
      out.print(base_url);
      out.write("/LoadAnalysis\" method=\"post\" enctype=\"multipart/form-data\" target=\"_self\" >\n");
      out.write("                                <input type=\"file\" id=\"slidefilename\" name=\"slidefilename\" size=\"70\"/>\n");
      out.write("                                &nbsp;\n");
      out.write("                                <button type=\"button\" id=\"slide_upload_btn\" title=\"Open Selected Analysis File\" onclick=\"uploadSlideFile();\">Upload</button>\n");
      out.write("                                <br>\n");
      out.write("                                <label class=\"msg\">Select a \"*.slide\" file to load</label>\n");
      out.write("                            </form>\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                </table>\n");
      out.write("                                \n");
      out.write("                <br>\n");
      out.write("                <br>\n");
      out.write("                \n");
      out.write("                <table id=\"open_table\" class=\"index_tables\" cellpadding=\"5\" border=\"0\" style=\"top: 204px\">\n");
      out.write("                    <tr>\n");
      out.write("                        <td colspan=\"2\"><label><b> Active Analyses</b></label></td>\n");
      out.write("                    </tr>\n");
      out.write("                    ");
 
                        boolean hasRunningOnes = false;
                        if(session != null) {

                            Enumeration keys = session.getAttributeNames();

                            while (keys.hasMoreElements()){
                                String key = (String)keys.nextElement();
                                Object obj = session.getAttribute(key);
                                if (obj instanceof AnalysisContainer){
                                    AnalysisContainer ac = (AnalysisContainer)obj;
                                    hasRunningOnes = true;
                    
      out.write("\n");
      out.write("                                    <tr>\n");
      out.write("                                        <td>\n");
      out.write("                                            <label>\n");
      out.write("                                                ");
      out.print(ac.analysis_name);
      out.write("\n");
      out.write("                                            </label>\n");
      out.write("                                        </td>\n");
      out.write("                                        <td>\n");
      out.write("                                            <button class=\"dropbtn\" id=\"openAnalysis\" title=\"Go to Analysis\" onclick=\"window.open('");
      out.print(base_url);
      out.write("displayHome.jsp?analysis_name=");
      out.print(ac.analysis_name);
      out.write("&load_type=reopen')\">Go To Analysis</button>\n");
      out.write("                                            <button class=\"dropbtn\" id=\"stopAnalysis\" title=\"Stop Analysis\" onclick=\"stopAnalysis('");
      out.print(ac.analysis_name);
      out.write("')\">Stop</button>\n");
      out.write("                                        </td>\n");
      out.write("                                    </tr>\n");
      out.write("                    ");
            
                                }
                            }
                        }
                    
      out.write("\n");
      out.write("                    \n");
      out.write("                    ");
  
                        if (!hasRunningOnes) {  
                    
      out.write("\n");
      out.write("                        <tr>\n");
      out.write("                            <td class=\"msg\" colspan=\"2\">\n");
      out.write("                                <label>\n");
      out.write("                                    <i> No active analysis found. </i>\n");
      out.write("                                </label>\n");
      out.write("                            </td>\n");
      out.write("                        </tr>\n");
      out.write("                    \n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                    \n");
      out.write("                </table>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("    ");

        String target_tab = request.getParameter("target");
        if (target_tab != null) {
            if (target_tab.equalsIgnoreCase("creater")) {
    
      out.write("\n");
      out.write("                var error_msg_target = 'creater';    \n");
      out.write("                show_tab('creater');\n");
      out.write("    ");
    
            } else if (target_tab.equalsIgnoreCase("loader")) {
    
      out.write("\n");
      out.write("                var error_msg_target = 'loader';\n");
      out.write("                show_tab('loader');\n");
      out.write("    ");
    
            } else if (target_tab.equalsIgnoreCase("opener")) {
    
      out.write("\n");
      out.write("                var error_msg_target = 'opener';\n");
      out.write("                show_tab('opener');\n");
      out.write("    ");
    
            }
        }
    
      out.write("\n");
      out.write("    \n");
      out.write("    \n");
      out.write("            // Get the modal\n");
      out.write("            var modal = document.getElementById('myModal');\n");
      out.write("            // Get the <span> element that closes the modal\n");
      out.write("            var span = document.getElementById(\"close_span\");\n");
      out.write("\n");
      out.write("            var modal_inside = document.getElementById('modal_inside');\n");
      out.write("            var modal_frame = document.getElementById('modalFrame');\n");
      out.write("\n");
      out.write("            // When the user clicks the button, open the modal \n");
      out.write("            function showModalWindow(src, width, height) {\n");
      out.write("                modal_frame.src = src;\n");
      out.write("                modal.style.display = \"block\";\n");
      out.write("                modal_inside.style.width = width;\n");
      out.write("                modal_inside.style.height = height;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            // When the user clicks on <span> (x), close the modal\n");
      out.write("            span.onclick = function() {\n");
      out.write("                modal.style.display = \"none\";\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            // When the user clicks anywhere outside of the modal, close it\n");
      out.write("            window.onclick = function(event) {\n");
      out.write("                if (event.target == modal) {\n");
      out.write("                    modal.style.display = \"none\";\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        \n");
      out.write("            function doSysReq() { \n");
      out.write("                var browser = \"\";\n");
      out.write("                var browserApp= \"\";\n");
      out.write("                var browserVersion = \"\";\n");
      out.write("                    if((navigator.userAgent.indexOf(\"Opera\") || navigator.userAgent.indexOf('OPR')) != -1 ) {\n");
      out.write("                        browserApp = \"Opera\";\n");
      out.write("                        browserVersion = navigator.appVersion; \n");
      out.write("                    } else if(navigator.userAgent.indexOf(\"Chrome\") != -1 ){\n");
      out.write("                        if(navigator.userAgent.indexOf(\"Edge\") > -1){\n");
      out.write("                            browserApp = \"Edge\";\n");
      out.write("                            browserVersion = navigator.appVersion; \n");
      out.write("                        }else{\n");
      out.write("                            browserApp = \"Chrome\";\n");
      out.write("                            browserVersion = navigator.appVersion; \n");
      out.write("                        }\n");
      out.write("                    } else if(navigator.userAgent.indexOf(\"Safari\") != -1){\n");
      out.write("                        browserApp = \"Safari\";\n");
      out.write("                        browserVersion = navigator.appVersion;                     \n");
      out.write("                    } else if(navigator.userAgent.indexOf(\"Firefox\") != -1 ){\n");
      out.write("                        browserApp = \"Firefox\";\n");
      out.write("                        browserVersion = navigator.appVersion; \n");
      out.write("                    } else if((navigator.userAgent.indexOf(\"MSIE\") != -1 || navigator.appVersion.indexOf('Trident/') > 0)) {//IF IE > 10 \n");
      out.write("                        browser = \"ie\";\n");
      out.write("                        browserApp = \"Internet Explorer\";\n");
      out.write("                        browserVersion = navigator.appVersion; \n");
      out.write("                    } else {\n");
      out.write("                        browserApp = \"Unknown\";\n");
      out.write("                    }\n");
      out.write("\n");
      out.write("                    var OSName = \"Unknown\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Windows NT 10.0\")!= -1) \n");
      out.write("                            OSName=\"Windows 10\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Windows NT 6.2\") != -1) \n");
      out.write("                            OSName=\"Windows 8\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Windows NT 6.1\") != -1) \n");
      out.write("                            OSName=\"Windows 7\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Windows NT 6.0\") != -1) \n");
      out.write("                            OSName=\"Windows Vista\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Windows NT 5.1\") != -1) \n");
      out.write("                            OSName=\"Windows XP\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Windows NT 5.0\") != -1) \n");
      out.write("                            OSName=\"Windows 2000\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Mac\")!= -1) \n");
      out.write("                            OSName=\"Mac/iOS\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"X11\")!= -1) \n");
      out.write("                            OSName=\"UNIX\";\n");
      out.write("                        if (window.navigator.userAgent.indexOf(\"Linux\")!= -1) \n");
      out.write("                            OSName=\"Linux\";\n");
      out.write("\n");
      out.write("                    var browserRes_W = window.screen.availWidth;\n");
      out.write("                    var browserRes_H = window.screen.availHeight; \n");
      out.write("                    //var screenRes = screen.width + \" X \" + screen.height; \n");
      out.write("\n");
      out.write("                    //showModalWindow('sysreq.jsp?browser_app='+browserApp+'&browser_ver='+browserVersion+'&os_name='+OSName+'&browser_res='+browserRes+'&screen_res='+screenRes, '50%', '30%');\n");
      out.write("                    \n");
      out.write("                    if (browserApp !== \"Internet Explorer\" || (browserRes_W < 1920 || browserRes_H < 1080)) {\n");
      out.write("                        showModalWindow('sysreq.jsp?browser_app='+ browserApp + '&browser_res_w=' + browserRes_W + '&browser_res_h=' + browserRes_H, '50%', '30%');\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("    \n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
