package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import utils.SessionManager;
import utils.ReadConfig;
import java.io.File;
import structure.AnalysisContainer;

public final class newExperiment_jsp extends org.apache.jasper.runtime.HttpJspBase
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

try {

      out.write("\n");
      out.write("\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
      out.write("<title>SLIDE</title>\n");
      out.write("<script type = \"text/javascript\" language = \"JavaScript\" src=\"userInput.js\"></script>\n");
      out.write("\n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("    <link rel=\"stylesheet\" href=\"vtbox-tables.css\">\n");
      out.write("\n");
      out.write("<script>\n");
      out.write("    \n");
      out.write("    function uploadCompletion (status, data_filename, mapping_filename) {\n");
      out.write("        \n");
      out.write("        document.getElementById('data_upload_btn').innerHTML = 'Upload';\n");
      out.write("        document.getElementById(\"data_upload_btn\").disabled = false;\n");
      out.write("        alert(\"Data File \" + data_filename + \" and Sample Mapping File \" + mapping_filename + \" has been uploaded.\");\n");
      out.write("        if (status == \"\") {\n");
      out.write("            // if upload succeded display rest of the input interface\n");
      out.write("            document.getElementById('input_table').style.display = 'inline';\n");
      out.write("            document.getElementById(\"fileinputname\").value = data_filename;\n");
      out.write("            document.getElementById(\"mapfilename\").value = mapping_filename;\n");
      out.write("            // reset all remaining fields\n");
      out.write("            //document.getElementById('fileInputForm').reset();\n");
      out.write("            //$('#upload_form')[0].reset();\n");
      out.write("        } else {\n");
      out.write("            // if upload failed display upload status message\n");
      out.write("            document.getElementById('notice_board').style.display = 'inline';\n");
      out.write("            document.getElementById('notice_board').innerHTML = status;\n");
      out.write("        }\n");
      out.write("\n");
      out.write("    }\n");
      out.write("\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("\n");
 
    
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("            <table cellpadding=\"5\" style=\"width: 100%;\" border=\"0\">\n");
      out.write("                <tr>\n");
      out.write("                    <td colspan=\"1\" align=\"center\"><h2>New Experiment Input</h2></td>\n");
      out.write("                </tr>\n");
      out.write("            ");
 if (msg != null) { 
      out.write("\n");
      out.write("                <tr>\n");
      out.write("                    <td class=\"error_msg\" colspan=\"2\"  align=\"center\">\n");
      out.write("                        ");
      out.print(msg);
      out.write("\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            <form name=\"upload_form\" id=\"upload_form\" action=\"");
      out.print(base_url);
      out.write("DataUploader?analysis_name=");
      out.print(analysis_name);
      out.write("\" method=\"post\" enctype=\"multipart/form-data\" target=\"upload_target\" >\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>Input Data File Name</label></b>\n");
      out.write("            \t</td>\n");
      out.write("               \n");
      out.write("                <td>\n");
      out.write("                    <input type=\"file\" id=\"selectmrnafilename\" name=\"selectmrnafilename\" size=\"70\"/>    \n");
      out.write("                    <iframe id=\"upload_target\" name=\"upload_target\" src=\"#\" style=\"width:0;height:0;border:0px solid #fff;\"></iframe>\n");
      out.write("                    <div id=\"notice_board\" name=\"notice_board\" style=\"display:none\"></div>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("             <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>Input Sample to Column Mapping File</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                \n");
      out.write("                <td>\n");
      out.write("                <input type=\"file\" id=\"selectmapfilename\" name=\"selectmapfilename\" size=\"70\"/>\n");
      out.write("                 &nbsp;&nbsp;\n");
      out.write("                    <button type=\"button\" id=\"data_upload_btn\" title=\"Upload Selected Datafile To Server\" onclick=\"uploadData();\">Upload</button>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            </table>\n");
      out.write("            \n");
      out.write("            </form>\n");
      out.write("\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <form name=\"fileInputForm\" method =\"get\" action=\"\">\n");
      out.write("            \n");
      out.write("            <input type=\"hidden\" name=\"fileinputname\" id=\"fileinputname\" />\n");
      out.write("            <input type=\"hidden\" name=\"newexperimentname\" id=\"newexperimentname\" value=\"");
      out.print(analysis_name);
      out.write("\" />\n");
      out.write("            <input type=\"hidden\" name=\"mapfilename\" id=\"mapfilename\" />\n");
      out.write("            <table id=\"input_table\" cellpadding=\"5\" style=\"width: 100%; display: none\" border=\"0\">\n");
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("            \t&nbsp;\n");
      out.write("            \t</td>\n");
      out.write("\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"checkbox\" id=\"headerflag\" name=\"headerflag\" checked=\"checked\"> The input file has headers included</input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>Specify k for k Nearest Neighbor based data imputation</label></b>\n");
      out.write("            \t</td>\n");
      out.write("\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"text\" id=\"txtKNN\" name=\"txtKNN\" size=\"3\" /> \n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("            \t&nbsp;\n");
      out.write("            \t</td>\n");
      out.write("                          \n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"checkbox\" id=\"log2flag\" name=\"log2flag\"> Perform log base 2 transformation of the data</input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>How Many Rows Should be Read from the File?</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"radio\" name=\"rowLoading\" value=\"1\" checked=\"checked\"> All\n");
      out.write("                    &nbsp; \n");
      out.write("                    <input type=\"radio\" name=\"rowLoading\" value=\"0\"> \n");
      out.write("                    From Row &nbsp; <input type=\"text\" id=\"txtFromRow\" name=\"txtFromRow\" size=\"5\" />\n");
      out.write("                    &nbsp; To &nbsp; <input type=\"text\" id=\"txtToRow\" name=\"txtToRow\" size=\"5\" />\n");
      out.write("                    &nbsp; (count 2nd Row as 1 if data has header)\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("             <tr>\n");
      out.write("                <td> \n");
      out.write("                    <b><label>Data Imputation</label></b>                    \n");
      out.write("                </td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <select id=\"imputeD\" name=\"imputeD\" onchange=\"getimputevalue();\">\n");
      out.write("                        <option id=\"imputeHyphen\" value=\"-1\" selected>-</option>\n");
      out.write("                        <option id=\"imputeNone\" value=\"0\">None</option>\n");
      out.write("                        <option id=\"imputeRowMean\" value=\"1\" >Impute with row mean</option>\n");
      out.write("                        <option id=\"imputeColMean\" value=\"2\" >Impute with column mean</option>\n");
      out.write("                        <option id=\"imputeRowMedian\" value=\"3\" >Impute with row median</option>\n");
      out.write("                        <option id=\"imputeColMedian\" value=\"4\">Impute with column median</option>\n");
      out.write("                    </select>                  \n");
      out.write("                <input type=\"hidden\" name=\"imputeval\" id=\"imputeval\" />\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td> \n");
      out.write("                    <b><label>File Delimiter</label></b>                    \n");
      out.write("                </td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <select id=\"delimS\" name=\"delimS\" onchange=\"getdelimitervalue();\">\n");
      out.write("                        <option id=\"hyphenS\" value=\"hyphenS\" >-</option>\n");
      out.write("                        <option id=\"commaS\" value=\"commaS\" >Comma</option>\n");
      out.write("                        <option id=\"tabS\" value=\"tabS\" >Tab</option>\n");
      out.write("                        <option id=\"spaceS\" value=\"spaceS\" >Space</option>\n");
      out.write("                        <option id=\"semiS\" value=\"semiS\">Semicolon</option>\n");
      out.write("                    </select>\n");
      out.write("                    <button type=\"button\" class=\"dropbtn\" id=\"Preview\" title=\"File Preview\" style=\"visibility: hidden\" onclick=\"filePreview('");
      out.print(analysis_name);
      out.write("');\">Preview</button>\n");
      out.write("                    <!--<input type=\"button\" id=\"Preview\" value=\"Preview\" style=\"visibility: hidden\" onClick=\"filePreview();\"></input>--> \n");
      out.write("                </td>\n");
      out.write("                <input type=\"hidden\" name=\"delimval\" id=\"delimval\" />\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"2\">\n");
      out.write("                    <iframe name=\"previewFrame\" id=\"previewFrame\" src=\"\" style=\"display: hidden\" height=\"0\" width=\"0\"> </iframe>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("            \t<td>\n");
      out.write("                    <b><label>Enter the Species name</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <select id=\"species\" onchange=\"getspeciesname();\">\n");
      out.write("                        <option id=\"nospecies\" value=\"nospecies\" >-</option>\n");
      out.write("                        <option id=\"human\" value=\"human\">Homo sapiens</option>\n");
      out.write("                    \t<option id=\"mouse\" value=\"mouse\" >Mus musculus</option>\n");
      out.write("                    </select>\n");
      out.write("                </td>\n");
      out.write("                <input type=\"hidden\" name=\"species_name\" id=\"species_name\" />\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("            \t<td>\n");
      out.write("                    <b><label>Enter the Non-numeric Features Column Numbers</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"text\" name=\"txtNumMetaCols\" id=\"txtNumMetaCols\" value=\"\" onchange=\"chknumrange(this);\" />\n");
      out.write("                    &nbsp; &nbsp; \n");
      out.write("                    (Specify range as 1-4 or specific columns as 1,3,4)\n");
      out.write("                </td>\n");
      out.write("            </tr>    \n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("            \t<td>\n");
      out.write("                    <b><label>Enter the Gene Symbol Column Numbers (if any)</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"text\" name=\"txtGeneSymbolCol\" id=\"txtGeneSymbolCol\" value=\"\" onchange=\"chkisEmptyOrNumber(this);\" />\n");
      out.write("                    &nbsp; &nbsp; \n");
      out.write("                    (The gene symbol column should be one of the meta-data columns specified above)\n");
      out.write("                </td>\n");
      out.write("                                \n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("            \t<td>\n");
      out.write("                    <b><label>Enter the Entrez ID Column Numbers (if any)</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"text\" name=\"txtEntrezCol\" id=\"txtEntrezCol\" value=\"\" onchange=\"chkisEmptyOrNumber(this);\" />\n");
      out.write("                    &nbsp; &nbsp; \n");
      out.write("                    (The entrez id column should be one of the meta-data columns specified above)\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td>\n");
      out.write("                    <b><label>Is this a Time Course Data?</label></b>\n");
      out.write("            \t</td>\n");
      out.write("                <td colspan=\"1\">\n");
      out.write("                    <input type=\"radio\" name=\"timeSeries\" value=\"no\" checked=\"checked\" onclick=\"setTimeSeriesAs('no')\"> No\n");
      out.write("                    &nbsp; \n");
      out.write("                    <input type=\"radio\" name=\"timeSeries\" value=\"yes\" onclick=\"setTimeSeriesAs('yes')\"> Yes\n");
      out.write("                </td>\n");
      out.write("                <input type=\"hidden\" name=\"isTimeSeries\" id=\"isTimeSeries\" value=\"no\"/>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("                     \n");
      out.write("           \n");
      out.write("             \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"2\">&nbsp;</td>\n");
      out.write("            </tr>\n");
      out.write("                \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"2\" style=\"text-align: center\">\n");
      out.write("                    <button type=\"button\" class=\"dropbtn\" id=\"EnterNewExperiment\" title=\"Enter New Experiment\" onclick=\"createNewExp()\">Create</button>                    \n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            </table>\n");
      out.write("            \n");
      out.write("    <p> &nbsp;</p>\n");
      out.write("\n");
      out.write("</form> \n");
      out.write("\n");
      out.write("  \n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");
      out.write("\n");

  
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
