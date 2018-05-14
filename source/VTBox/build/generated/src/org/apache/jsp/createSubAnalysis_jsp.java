package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import searcher.GeneObject;
import structure.MetaData;
import vtbox.SessionUtils;
import utils.SessionManager;
import utils.ReadConfig;
import java.util.Map;
import java.util.Iterator;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import structure.AnalysisContainer;
import java.util.ArrayList;
import java.util.HashMap;

public final class createSubAnalysis_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    
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
    

      out.write('\n');
      out.write('\n');
 if (mode.equals("input") || mode.equals("name_error") || mode.equals("emptylist_error") || mode.equals("fileupload_error")) {  
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("\n");
      out.write("    <script>\n");
      out.write("        \n");
      out.write("        function submitSubAnalysisRequest_1(){\n");
      out.write("            \n");
      out.write("            var v1 = document.getElementById(\"list_name\").value;\n");
      out.write("            var e = document.getElementById(\"session_list_name\");\n");
      out.write("            var v2 = e.options[e.selectedIndex].value;\n");
      out.write("            if(v1 == \"\" || v2 == \"dashS\"){\n");
      out.write("                alert('Please provide a name and list for the analysis.');\n");
      out.write("            } else {\n");
      out.write("                createNewList_Session();\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function submitSubAnalysisRequest_2(){\n");
      out.write("            \n");
      out.write("            var v1 = document.getElementById(\"list_name\").value;\n");
      out.write("            var v2 = document.getElementById(\"entrez_ids\").value;\n");
      out.write("            if(v1 == \"\" || v2 == \"\"){\n");
      out.write("                alert('Please provide a name and list for the analysis.');\n");
      out.write("            } else {\n");
      out.write("                createNewList_Text();\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        /*\n");
      out.write("        function submitSubAnalysisRequest_3(){\n");
      out.write("            \n");
      out.write("            var v1 = document.getElementById(\"list_name\").value;\n");
      out.write("            var v2 = document.getElementById(\"txtentrezfilename\").value; \n");
      out.write("            var e = document.getElementById(\"delimS\");\n");
      out.write("            var v3 = e.options[e.selectedIndex].value;\n");
      out.write("            if(v1 === \"\" || v2 === \"\" || v3 === \"hyphenS\"){\n");
      out.write("                alert('Please provide a name and list for the analysis.');\n");
      out.write("            } else {\n");
      out.write("                createNewList_File();\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        \n");
      out.write("        function createNewList_File() {\n");
      out.write("            document.getElementById(\"mode\").value = \"file\";\n");
      out.write("            document.getElementById(\"createFilteredListForm\").submit();\n");
      out.write("        }\n");
      out.write("        */\n");
      out.write("        \n");
      out.write("        function createNewList_Text() {\n");
      out.write("            document.getElementById(\"mode\").value = \"list\";\n");
      out.write("            document.getElementById(\"createFilteredListForm\").submit();\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function createNewList_Session() {\n");
      out.write("            document.getElementById(\"mode\").value = \"session\";\n");
      out.write("            document.getElementById(\"createFilteredListForm\").submit();\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function getinputfilenamefromtext(){\n");
      out.write("            var filename = document.getElementById(\"txtentrezfilename\").value;\n");
      out.write("            document.getElementById(\"file_name\").value = filename;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function getfilepathfrombrowse(){\n");
      out.write("            var filename = document.getElementById(\"selectentrezfilename\").value;\n");
      out.write("            document.getElementById(\"txtentrezfilename\").value = filename;\n");
      out.write("            document.getElementById(\"file_name\").value = filename;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        function getdelimitervalue(){\n");
      out.write("            var d = document.getElementById(\"delimS\");\n");
      out.write("            //alert(optionDelim);\n");
      out.write("            var optionDelim = d.options[d.selectedIndex].value;\n");
      out.write("            document.getElementById(\"delimval\").value = optionDelim;\n");
      out.write("            //alert(optionDelim); \n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function submitSubAnalysisRequest_3() {\n");
      out.write("            \n");
      out.write("            var y = document.getElementById(\"txtentrezfilename\").value;\n");
      out.write("            var d = document.getElementById(\"delimval\").value;\n");
      out.write("            var n = document.getElementById(\"list_name\").value;\n");
      out.write("\n");
      out.write("            if (n === \"\") {\n");
      out.write("                alert(\"Please specify a name for the sub-analysis.\");\n");
      out.write("            } else if (y === \"\"){\n");
      out.write("                alert(\"Please select a file to upload.\");\n");
      out.write("            } else if (d === null || d === \"\" || d === \"hyphenS\") {\n");
      out.write("                alert(\"Please select the delimiter used in the file.\");\n");
      out.write("            } else {\n");
      out.write("                var upform = document.getElementById(\"uploadEntrezListForm\");\n");
      out.write("                upform.action = upform.action + \"&delimval=\" + d + \"&list_name=\" + n;\n");
      out.write("                upform.submit();\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </script>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("<body>\n");
      out.write("\n");
      out.write("    <form name=\"createFilteredListForm\" id=\"createFilteredListForm\" method=\"get\" action=\"createSubAnalysis.jsp\">\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>Create Sub-Analysis</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        ");
 if (mode.equals("name_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>The name ");
      out.print(list_name);
      out.write(" is already in use. Please specify another name.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        ");
 if (mode.equals("emptylist_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>The selected feature list is empty.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        ");
 if (mode.equals("fileupload_error")) {  
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td class=\"error_msg\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>The file could not be uploaded. Please try again.</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Enter a Name for the Analysis</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <input type=\"text\" id=\"list_name\" name=\"list_name\" size=\"35\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"50\" colspan=\"2\">\n");
      out.write("                <b><label>Specify Entrez IDs to be included in the Sub-Analysis:</label></b>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>From one of the Feature Lists</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td colspan=\"2\">\n");
      out.write("                <select name=\"session_list_name\" id=\"session_list_name\">\n");
      out.write("                    <option value=\"dashS\" >-</option>\n");
      out.write("                    ");
 Iterator it = analysis.filterListMap.entrySet().iterator();  
      out.write("\n");
      out.write("                    ");
 while (it.hasNext()) {   
      out.write("\n");
      out.write("                        ");
 String featurelist_name = ((Map.Entry)it.next()).getKey().toString(); 
      out.write("\n");
      out.write("                        <option value=\"");
      out.print(featurelist_name);
      out.write("\" >");
      out.print(featurelist_name);
      out.write("</option>\n");
      out.write("                    ");
 }    
      out.write("\n");
      out.write("                </select>\n");
      out.write("                &nbsp;\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create Sub-Analysis.\" onclick=\"submitSubAnalysisRequest_1();\">Create</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr height=\"5px\">\n");
      out.write("            <td height=\"5px\" colspan=\"2\" align=\"center\">\n");
      out.write("                OR\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>As a Comma Delimited List</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <input type=\"text\" id=\"entrez_ids\" name=\"entrez_ids\" size=\"50\">\n");
      out.write("                &nbsp;\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create Sub-Analysis.\" onclick=\"submitSubAnalysisRequest_2();\">Create</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <input type=\"hidden\" id=\"mode\" name=\"mode\" value=\"input\">\n");
      out.write("        <input type=\"hidden\" id=\"analysis_name\" name=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\">\n");
      out.write("        \n");
      out.write("    </form>\n");
      out.write("        \n");
      out.write("        <tr height=\"5px\">\n");
      out.write("            <td height=\"5px\" colspan=\"2\" align=\"center\">\n");
      out.write("                OR\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>In a Delimited File</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <form name=\"uploadEntrezListForm\" id=\"uploadEntrezListForm\" action=\"");
      out.print(base_url);
      out.write("DataUploader?analysis_name=");
      out.print(analysis_name);
      out.write("&upload_type=entrez_list\" method=\"post\" enctype=\"multipart/form-data\" target=\"\" >\n");
      out.write("                    <input type=\"file\" id=\"txtentrezfilename\" name=\"txtentrezfilename\"/>\n");
      out.write("                </form>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td> \n");
      out.write("                <b><label>Enter the File Delimiter</label></b>                    \n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <select id=\"delimS\" name=\"delimS\" onchange=\"getdelimitervalue();\">\n");
      out.write("                    <option id=\"hyphenS\" value=\"hyphenS\" >-</option>\n");
      out.write("                    <option id=\"lineS\" value=\"lineS\" >Line</option>\n");
      out.write("                    <option id=\"commaS\" value=\"commaS\" >Comma</option>\n");
      out.write("                    <option id=\"tabS\" value=\"tabS\" >Tab</option>\n");
      out.write("                    <option id=\"spaceS\" value=\"spaceS\" >Space</option>\n");
      out.write("                    <option id=\"semiS\" value=\"semiS\">Semicolon</option>\n");
      out.write("                </select>\n");
      out.write("                <input type=\"hidden\" name=\"delimval\" id=\"delimval\" />\n");
      out.write("                &nbsp;\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create Sub-Analysis.\" onclick=\"submitSubAnalysisRequest_3();\">Create</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("    </table>\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");
      out.write("\n");
  } else if (mode.equals("list") || mode.equals("file") || mode.equals("session")) {  
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <style>\n");
      out.write("\n");
      out.write("            table {\n");
      out.write("                font-family: verdana;\n");
      out.write("                border-collapse: collapse;\n");
      out.write("                width: 100%;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            td, th {\n");
      out.write("                border: 1px solid #dddddd;\n");
      out.write("                padding: 8px;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            tr:nth-child(even) {\n");
      out.write("                background-color: #efefef;\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        </style>\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"15\">\n");
      out.write("                &nbsp;\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\">\n");
      out.write("                Sub-analysis <i>");
      out.print(list_name);
      out.write("</i> created. <br>\n");
      out.write("                Click <a href=\"");
      out.print(base_url);
      out.write("displayHome.jsp?analysis_name=");
      out.print(list_name);
      out.write("&load_type=sub_analysis\" target=\"_blank\">here</a> to open.\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\" align=\"center\">\n");
      out.write("                To save the sub-analysis open it and click the \"Save Analysis\" button.\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("    </table>\n");
      out.write("</body>\n");
      out.write("</html>\n");
  }  
      out.write('\n');
      out.write('\n');

  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}


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
