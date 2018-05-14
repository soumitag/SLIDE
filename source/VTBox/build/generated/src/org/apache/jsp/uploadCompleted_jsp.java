package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.io.File;
import utils.UserInputParser;
import java.io.IOException;
import vtbase.DataParsingException;
import utils.Utils;
import vtbox.SessionUtils;
import utils.SessionManager;

public final class uploadCompleted_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
try {
    
    String filename = request.getParameter("filename");
    String status = request.getParameter("status");
    String type = request.getParameter("type");
    String analysis_name = request.getParameter("analysis_name");
    String delimval = request.getParameter("delimval");
    
    String installPath = SessionManager.getInstallPath(application.getResourceAsStream("/WEB-INF/slide-web-config.txt"));
        
    String fileDelimiter = Utils.getDelimiter(delimval);
    
    String[] column_names = null;
    
    if (type.equalsIgnoreCase("data") && status.equals("")) {
        
        try {
            column_names = SessionManager.getColumnHeaders(installPath, 
                                                 request.getSession().getId(), 
                                                 analysis_name, 
                                                 filename, 
                                                 type,
                                                 true,
                                                 fileDelimiter);
            
            String key;
            HashMap <String, Boolean> temp = new HashMap <String, Boolean> ();
            for (int i=0; i<column_names.length; i++) {
                key = column_names[i].trim().toLowerCase();
                if(!temp.containsKey(key)) {
                    temp.put(key, new Boolean(true));
                } else {
                    status = "Upload Failed! Column headers must be unique. Header \'" + key + "\' found twice.";
                    break;
                }
            }
            
        } catch (DataParsingException dpe) {
            
            status = "Upload Failed! " + dpe.getMessage();
            
        } catch (IOException ioe) {
            
            status = "Upload Failed! Unable to read file. Please provide a delimited file in tsv, csv or txt format.";
            
        }
        
    } else if (type.equalsIgnoreCase("mapping") && status.equals("")) {
        
        try {
            boolean hasGroupingFactor = request.getParameter("hasGroupingFactor").equalsIgnoreCase("yes");
            boolean hasTwoGroupingFactors = request.getParameter("hasTwoGroupingFactors").equalsIgnoreCase("yes");
            String data_filename = request.getParameter("data_filename");
            String data_delimval = request.getParameter("data_fileDelimiter");
            
            String data_fileDelimiter = Utils.getDelimiter(data_delimval);
            
            column_names = SessionManager.getColumnHeaders(installPath, 
                                                 request.getSession().getId(), 
                                                 analysis_name, 
                                                 data_filename, 
                                                 "data",
                                                 true,
                                                 data_fileDelimiter);
            
            String qfilename = installPath + File.separator + 
                               "temp" + File.separator + 
                               request.getSession().getId() + File.separator + 
                               analysis_name + "_mapping_" + filename;
        
            int nGroupFactors = 0;
            if (hasGroupingFactor) {
                if (hasTwoGroupingFactors) {
                    nGroupFactors = 2;
                } else {
                    nGroupFactors = 1;
                }
            }
            UserInputParser.parseSampleMappingsFile(qfilename, fileDelimiter, nGroupFactors, column_names);
            
        } catch (DataParsingException dpe) {
            
            status = "Upload Failed! " + dpe.getMessage();
            
        } catch (IOException ioe) {
            
            status = "Upload Failed! Unable to read file.";
            
        }
        
    }
    System.out.println(filename);

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Data Upload Completed</title>\n");
      out.write("    </head>\n");
      out.write("    <script>\n");
      out.write("        function notifyUploadCompletion () {\n");
      out.write("        ");
 if(type.equalsIgnoreCase("data"))  {     
      out.write("\n");
      out.write("            var col_names = new Array();\n");
      out.write("            ");
 if (status.equals("")) { 
      out.write("\n");
      out.write("                ");
 for (int i=0; i<column_names.length; i++) { 
      out.write("\n");
      out.write("                    col_names.push('");
      out.print(column_names[i]);
      out.write("');\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            parent.uploadCompletionData(\"");
      out.print(status);
      out.write("\", '");
      out.print(filename);
      out.write("', col_names);\n");
      out.write("        ");
 } else { 
      out.write("\n");
      out.write("            parent.uploadCompletionMapping(\"");
      out.print(status);
      out.write("\", '");
      out.print(filename);
      out.write("');\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("        }\n");
      out.write("    </script>\n");
      out.write("    <body onload=\"notifyUploadCompletion()\">\n");
      out.write("    </body>\n");
      out.write("</html>\n");

  
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
