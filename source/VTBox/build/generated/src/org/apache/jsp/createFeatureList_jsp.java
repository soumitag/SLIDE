package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import vtbox.SessionUtils;
import java.util.ArrayList;
import structure.AnalysisContainer;

public final class createFeatureList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
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

      out.write('\n');
      out.write('\n');
 if (mode.equals("input") || mode.equals("name_error") || mode.equals("emptylist_error") || mode.equals("fileupload_error")) {  
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    \n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        \n");
      out.write("        function submitCreateRequest(){\n");
      out.write("            \n");
      out.write("            var value1 = document.getElementById(\"list_name\").value;\n");
      out.write("            if(value1 == \"\"){\n");
      out.write("                alert('Please provide a list name.');\n");
      out.write("            } else {\n");
      out.write("                createFeatureList();\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function createFeatureList() {\n");
      out.write("            document.getElementById(\"mode\").value = \"process\";\n");
      out.write("            document.getElementById(\"createFeatureListForm\").setAttribute(\"action\",\"createFeatureList.jsp\");\n");
      out.write("            document.getElementById(\"createFeatureListForm\").submit();\n");
      out.write("        }\n");
      out.write("        \n");
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
      out.write("    </script>\n");
      out.write("    \n");
      out.write("    \n");
      out.write("    \n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    \n");
      out.write("    <form name=\"createFeatureListForm\" id=\"createFeatureListForm\" method=\"get\" action=\"\">\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\" align=\"center\" colspan=\"2\">\n");
      out.write("                <b><label>Create a New Feature List</label></b>\n");
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
      out.write("                <b><label>Enter a Name for the New List</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <input type=\"text\" id=\"list_name\" name=\"list_name\" size=\"30\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td colspan=\"2\" height=\"50\">\n");
      out.write("                &nbsp;\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Create an Empty Feature List</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create Feature Lists.\" onclick=\"submitCreateRequest();\">Create</button>\n");
      out.write("                <input type=\"hidden\" name=\"mode\" id=\"mode\" value=\"process\">\n");
      out.write("                <input type=\"hidden\" name=\"analysis_name\" id=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\">\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("        \n");
      out.write("    </form>\n");
      out.write("    \n");
      out.write("        <tr height=\"5px\">\n");
      out.write("            <td height=\"5px\" colspan=\"2\" align=\"center\">\n");
      out.write("                OR\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("\n");
      out.write("        <tr>\n");
      out.write("            <td>\n");
      out.write("                <b><label>Upload a List from File</label></b>\n");
      out.write("            </td>\n");
      out.write("            <td>\n");
      out.write("                <form name=\"uploadEntrezListForm\" id=\"uploadEntrezListForm\" action=\"");
      out.print(base_url);
      out.write("DataUploader?analysis_name=");
      out.print(analysis_name);
      out.write("&upload_type=entrez_feature_list\" method=\"post\" enctype=\"multipart/form-data\" target=\"\" >\n");
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
      out.write("                <button type=\"button\" class=\"dropbtn\" title=\"Create Sub-Analysis.\" onclick=\"submitSubAnalysisRequest_3();\">Upload</button>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("   \n");
      out.write("    </table>\n");
      out.write("            \n");
      out.write("</body>\n");
      out.write("</html>\n");
      out.write("\n");
  } else if (mode.equals("process") || mode.equals("file")) {  
      out.write("\n");
      out.write("<html>\n");
      out.write("<script>\n");
      out.write("    parent.updateFeatureLists('");
      out.print(list_name);
      out.write("', 1);\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("    <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">        \n");
      out.write("        <tr>\n");
      out.write("            <td height=\"75\">\n");
      out.write("                Feature list <i>");
      out.print(list_name);
      out.write("</i> created.\n");
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
