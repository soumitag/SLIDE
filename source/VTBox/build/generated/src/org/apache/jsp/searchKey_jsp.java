package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import java.util.Set;
import structure.AnalysisContainer;
import java.util.Map;
import java.util.HashMap;
import structure.Data;
import java.util.ArrayList;
import java.util.Iterator;
import structure.CompactSearchResultContainer;

public final class searchKey_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    Data db = analysis.database;
    
    int num_features = db.features.size();
    
    String start_str = request.getParameter("start");
    String end_str = request.getParameter("end");
    
    int start, end;
    if (start_str != null && !start_str.equals("") && !start_str.equals("null")) {
        start = Integer.parseInt(start_str);
    } else {
        start = 0;
    }
    
    if (end_str != null && !end_str.equals("") && !end_str.equals("null")) {
        end = Integer.parseInt(end_str);
    } else {
        end = num_features;
    }
    
    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
    
    if (search_results == null || search_results.size() == 0) {
        String msg = "No search results to show.";
        getServletContext().getRequestDispatcher("/Exception.jsp?msg=" + msg).forward(request, response);
        return;
    }
    
    ArrayList <String> search_strings = analysis.search_strings;
    
    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set <String> keys = filterListMap.keySet();
    Iterator list_iter = keys.iterator();

      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        \n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("\n");
      out.write("    <script>\n");
      out.write("        \n");
      out.write("        function addSearchKey(text2, srcount){            \n");
      out.write("            document.getElementById('containerSK').innerHTML = \n");
      out.write("                    document.getElementById('containerSK').innerHTML + text2;            \n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        \n");
      out.write("        function callFrmSearchKeyP(pathid, analysis_name) {            \n");
      out.write("            parent.showDetailedPathInfo(pathid, analysis_name);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function callFrmSearchKeyG(goid, analysis_name) {            \n");
      out.write("            parent.showDetailedGOInfo(goid, analysis_name);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function callFrmSearchKeyE(eid, analysis_name) {            \n");
      out.write("            parent.showDetailedInfo(eid, analysis_name);\n");
      out.write("        }  \n");
      out.write("        \n");
      out.write("        var selected_object_id = \"\";\n");
      out.write("                \n");
      out.write("        function callHighlightPathwayGenes(search_no, pathid){\n");
      out.write("            \n");
      out.write("            //alert(search_no);\n");
      out.write("            //alert(pathid);\n");
      out.write("            if (pathid != selected_object_id) {\n");
      out.write("                // deHighlight selected_object_id\n");
      out.write("                if (selected_object_id != \"\") {\n");
      out.write("                    var this_div = document.getElementById(selected_object_id);\n");
      out.write("                    this_div.style.fontWeight = \"normal\";\n");
      out.write("                    parent.toggleHighlightGenes(search_no, selected_object_id, 0);\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                // highlight pathid\n");
      out.write("                var this_div = document.getElementById(pathid);\n");
      out.write("                this_div.style.fontWeight = \"bold\";\n");
      out.write("                parent.toggleHighlightGenes(search_no, pathid, 1);\n");
      out.write("                \n");
      out.write("                selected_object_id = pathid;\n");
      out.write("                \n");
      out.write("            } else {\n");
      out.write("                \n");
      out.write("                // deHighlight pathid\n");
      out.write("                var this_div = document.getElementById(pathid);\n");
      out.write("                this_div.style.fontWeight = \"normal\";\n");
      out.write("                parent.toggleHighlightGenes(pathid, 0);\n");
      out.write("            }         \n");
      out.write("      \n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function createRequestObject() {\n");
      out.write("            \n");
      out.write("            var tmpXmlHttpObject;\n");
      out.write("\n");
      out.write("            //depending on what the browser supports, use the right way to create the XMLHttpRequest object\n");
      out.write("            if (window.XMLHttpRequest) { \n");
      out.write("                // Mozilla, Safari would use this method ...\n");
      out.write("                tmpXmlHttpObject = new XMLHttpRequest();\n");
      out.write("\n");
      out.write("            } else if (window.ActiveXObject) { \n");
      out.write("                // IE would use this method ...\n");
      out.write("                tmpXmlHttpObject = new ActiveXObject(\"Microsoft.XMLHTTP\");\n");
      out.write("            }\n");
      out.write("\n");
      out.write("            return tmpXmlHttpObject;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        var http = createRequestObject();\n");
      out.write("        var selected_search_indices = [];\n");
      out.write("        var selected_list_name = '';\n");
      out.write("        \n");
      out.write("        function makeGetRequest_2 (selected_search_indices_str) {\n");
      out.write("            //make a connection to the server ... specifying that you intend to make a GET request \n");
      out.write("            //to the server. Specifiy the page name and the URL parameters to send\n");
      out.write("            var milliseconds = new Date().getTime();\n");
      out.write("            http.open('get', 'AddDataToList?mode=search_result&list_name=' + selected_list_name + '&search_indices=' + selected_search_indices_str + '&analysis_name=");
      out.print(analysis_name);
      out.write("' + '&timestamp=' + milliseconds);\n");
      out.write("\n");
      out.write("            //assign a handler for the response\n");
      out.write("            http.onreadystatechange = processResponse_2;\n");
      out.write("\n");
      out.write("            //actually send the request to the server\n");
      out.write("            http.send(null);\n");
      out.write("        }\n");
      out.write("            \n");
      out.write("        function processResponse_2 () {\n");
      out.write("            //check if the response has been received from the server\n");
      out.write("            if(http.readyState == 4){\n");
      out.write("\n");
      out.write("                //read and assign the response from the server\n");
      out.write("                var response = http.responseText;\n");
      out.write("\n");
      out.write("                //do additional parsing of the response, if needed\n");
      out.write("                if (response.trim() == \"1\") {\n");
      out.write("                    alert('Features Added to ' + selected_list_name + '.');\n");
      out.write("                } else {\n");
      out.write("                    alert('Features Could Not Be Added. Please Try Again.');\n");
      out.write("                }\n");
      out.write("\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function toggleSelection(search_index, search_text, label_id) {\n");
      out.write("            //alert(selected_search_indices);\n");
      out.write("            //alert(selected_search_indices.indexOf(search_index));\n");
      out.write("            var index = selected_search_indices.indexOf(search_index);\n");
      out.write("            var search_text_td = document.getElementById(label_id);\n");
      out.write("            if (index > -1) {\n");
      out.write("                selected_search_indices.splice(search_index, 1);\n");
      out.write("                search_text_td.textContent = search_index + \" : \" + search_text;\n");
      out.write("                search_text_td.style.color = \"black\";\n");
      out.write("            } else {\n");
      out.write("                selected_search_indices.push(search_index);\n");
      out.write("                search_text_td.textContent = \"\\u2713 \" + search_index + \" : \" + search_text;\n");
      out.write("                search_text_td.style.color = \"green\";\n");
      out.write("            }\n");
      out.write("            //alert(selected_search_indices);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function addToList (list_name) {\n");
      out.write("            //alert(selected_search_indices.toString());\n");
      out.write("            selected_list_name = list_name;\n");
      out.write("            makeGetRequest_2 (selected_search_indices.toString());\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        function addRemoveListName(list_name, add_remove_ind) {\n");
      out.write("            if (add_remove_ind === 1) {\n");
      out.write("                    // add\n");
      out.write("                var node = document.createElement(\"a\");\n");
      out.write("                var text = document.createTextNode(list_name);\n");
      out.write("                node.appendChild(text);\n");
      out.write("                node.setAttribute(\"href\", \"#\");\n");
      out.write("                node.setAttribute(\"id\", \"flin_\" + list_name);\n");
      out.write("                node.onclick = function () {\n");
      out.write("                    addToList(list_name);\n");
      out.write("                };\n");
      out.write("                document.getElementById(\"list_names_container\").appendChild(node);\n");
      out.write("                //alert(document.getElementById(\"list_names_container\"));\n");
      out.write("            } else if (add_remove_ind === 0) {\n");
      out.write("                // remove\n");
      out.write("                var node = document.getElementById(\"flin_\" + list_name);\n");
      out.write("                document.getElementById(\"list_names_container\").removeChild(node);\n");
      out.write("            }\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("    </script>\n");
      out.write("    </head>\n");
      out.write("    <body> \n");
      out.write("    \n");
      out.write("    <table width=\"100%\">\n");
      out.write("        <tr>\n");
      out.write("            <td align=\"center\" height=\"35\">\n");
      out.write("                <div class=\"dropdown\">\n");
      out.write("                    <button class=\"dropbtn\" title=\"Add Selected Search Tagged Features to Feature Lists. Click Search Keys To Select. Click Feature Lists To Create New Feature Lists.\">Add To List</button>\n");
      out.write("                    <div align=\"left\" id=\"list_names_container\" class=\"dropdown-content\">\n");
      out.write("                        ");

                            while (list_iter.hasNext()) {
                                String list_name = (String) list_iter.next();
                        
      out.write("\n");
      out.write("                        <a id=\"flin_");
      out.print(list_name);
      out.write("\" onclick=\"addToList('");
      out.print(list_name);
      out.write("')\" href=\"#\">");
      out.print(list_name);
      out.write("</a>\n");
      out.write("                        ");
  }   
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </td>\n");
      out.write("        </tr>\n");
      out.write("    </table>\n");
      out.write("                    \n");
      out.write("    <table id=\"containerSK1\" name=\"containerSK1\">\n");
      out.write("        <tr id=\"containerTRName\" name=\"containerTRName\">\n");
      out.write("            <td id=\"containerSK\" name=\"containerSK\">\n");
      out.write("                    \n");
      out.write("    ");
  for(int i = 0; i < search_results.size(); i++) {     
      out.write("\n");
      out.write("            \n");
      out.write("            <table><tr><td id=\"search_head_");
      out.print(i);
      out.write("\" class='h' onclick=\"toggleSelection('");
      out.print(i);
      out.write("', '");
      out.print(search_strings.get(i));
      out.write("', 'search_head_");
      out.print(i);
      out.write("')\">");
      out.print(i);
      out.write(" : ");
      out.print(search_strings.get(i));
      out.write(" </td></tr>\n");
      out.write("                <tr><td>\n");
      out.write("        \n");
      out.write("        ");

            ArrayList <CompactSearchResultContainer> search_results_i = search_results.get(i);
            if(search_results_i.size() > 0){
                if (search_results_i.get(0).type == CompactSearchResultContainer.TYPE_PATH) {

                    HashMap <String, String> path_eid_map = new HashMap <String, String> ();

                    for (int j = 0; j < search_results_i.size(); j++) {
                        CompactSearchResultContainer search_results_ij = search_results_i.get(j);            
                        path_eid_map.put(search_results_ij.getPathID(), search_results_ij.getPathName());
                    }

                    Iterator iter = path_eid_map.entrySet().iterator();
                    while(iter.hasNext()) {  

                        Map.Entry pair = (Map.Entry)iter.next();
                        if (search_strings.get(i).startsWith("pathname")) {
            
      out.write("\n");
      out.write("                            <div class='e' id='");
      out.print(i + "_" + pair.getKey());
      out.write("' style='font-weight:normal' onclick='callFrmSearchKeyP(\"");
      out.print(pair.getKey());
      out.write("\", \"");
      out.print(analysis_name);
      out.write("\"); callHighlightPathwayGenes(");
      out.print(i);
      out.write(", \"");
      out.print(i + "_" + pair.getKey());
      out.write("\")' > ");
      out.print(pair.getValue());
      out.write(" </div>\n");
      out.write("            ");
                    
                        } else if (search_strings.get(i).startsWith("pathid")) {
            
      out.write("\n");
      out.write("                            <div class='e' id='");
      out.print(i + "_" + pair.getKey());
      out.write("' style='font-weight:normal' onclick='callFrmSearchKeyP(\"");
      out.print(pair.getKey());
      out.write("\", \"");
      out.print(analysis_name);
      out.write("\"); callHighlightPathwayGenes(");
      out.print(i);
      out.write(", \"");
      out.print(i + "_" + pair.getKey());
      out.write("\")' > ");
      out.print(pair.getKey());
      out.write(" </div>\n");
      out.write("            ");

                        }
                    } 

                } else if (search_results_i.get(0).type == CompactSearchResultContainer.TYPE_GO) {

                    HashMap <String, String> goid_eid_map = new HashMap <String, String> ();

                    for (int j = 0; j < search_results_i.size(); j++) {    
                        CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                        goid_eid_map.put(search_results_ij.getGOID(), search_results_ij.getGOTerm());
                    }

                    Iterator iter = goid_eid_map.entrySet().iterator();
                    while(iter.hasNext()) {  

                        Map.Entry pair = (Map.Entry)iter.next();
                        if (search_strings.get(i).startsWith("goterm")) {
            
      out.write("\n");
      out.write("                            <div class='e' id='");
      out.print(i + "_" + pair.getKey());
      out.write("' style='font-weight:normal' onclick='callFrmSearchKeyG(\"");
      out.print(pair.getKey());
      out.write("\", \"");
      out.print(analysis_name);
      out.write("\"); callHighlightPathwayGenes(");
      out.print(i);
      out.write(", \"");
      out.print(i + "_" + pair.getKey());
      out.write("\")' > ");
      out.print(pair.getValue());
      out.write(" </div>\n");
      out.write("            ");
                    
                        } else if (search_strings.get(i).startsWith("goid")) {
            
      out.write("\n");
      out.write("                            <div class='e' id='");
      out.print(i + "_" + pair.getKey());
      out.write("' style='font-weight:normal' onclick='callFrmSearchKeyG(\"");
      out.print(pair.getKey());
      out.write("\", \"");
      out.print(analysis_name);
      out.write("\"); callHighlightPathwayGenes(");
      out.print(i);
      out.write(", \"");
      out.print(i + "_" + pair.getKey());
      out.write("\")' > ");
      out.print(pair.getKey());
      out.write(" </div>\n");
      out.write("            ");

                        }
                    }

                } else if (search_results_i.get(0).type == CompactSearchResultContainer.TYPE_GENE) {

                    HashMap <String, String> gene_entrez_map = new HashMap <String, String> ();

                    for (int j = 0; j < search_results_i.size(); j++) {
                        CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                        gene_entrez_map.put(search_results_ij.getEntrezID(), search_results_ij.getGeneSymbol());
                    }

                    Iterator iter = gene_entrez_map.entrySet().iterator();
                    while(iter.hasNext()){

                            Map.Entry pair = (Map.Entry)iter.next();
                            if (search_strings.get(i).startsWith("entrez")) {
                
      out.write("\n");
      out.write("                                <div class='e' id='");
      out.print(i + "_" + pair.getKey());
      out.write("' style='font-weight:normal' onclick='callFrmSearchKeyE(\"");
      out.print(pair.getKey());
      out.write("\", \"");
      out.print(analysis_name);
      out.write("\"); callHighlightPathwayGenes(");
      out.print(i);
      out.write(", \"");
      out.print(i + "_" + pair.getKey());
      out.write("\")' > ");
      out.print(pair.getKey());
      out.write(" </div>\n");
      out.write("                ");
                    
                            } else if (search_strings.get(i).startsWith("genesymbol")) {
                
      out.write("\n");
      out.write("                                <div class='e' id='");
      out.print(i + "_" + pair.getKey());
      out.write("' style='font-weight:normal' onclick='callFrmSearchKeyE(\"");
      out.print(pair.getKey());
      out.write("\", \"");
      out.print(analysis_name);
      out.write("\"); callHighlightPathwayGenes(");
      out.print(i);
      out.write(", \"");
      out.print(i + "_" + pair.getKey());
      out.write("\")' > ");
      out.print(pair.getValue() );
      out.write(" </div>\n");
      out.write("                ");

                            }
                    }
                }

            }
    
      out.write("\n");
      out.write("    \n");
      out.write("        </td></tr></table>\n");
      out.write("\n");
      out.write("    ");
  }   
      out.write("\n");
      out.write("                    \n");
      out.write("        </td>\n");
      out.write("        </tr>\n");
      out.write("        </table>\n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        //alert(selected_search_indices);\n");
      out.write("    </script>\n");
      out.write("    \n");
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
