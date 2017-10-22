package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import vtbox.SessionUtils;
import structure.AnalysisContainer;
import java.util.Set;
import java.util.Iterator;
import algorithms.clustering.TreeNode;
import java.util.HashMap;
import algorithms.clustering.BinaryTree;
import java.util.ArrayList;
import structure.Data;
import utils.Logger;
import graphics.Heatmap;
import utils.Utils;

public final class dendrogram_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");

    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer) session.getAttribute(analysis_name);

    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set<String> keys = filterListMap.keySet();
    Iterator iter = keys.iterator();
    

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <style>\n");
      out.write("            \n");
      out.write("            .dend_edge {\n");
      out.write("                position: absolute;\n");
      out.write("                text-align: left;\n");
      out.write("                cursor: pointer;\n");
      out.write("                border-radius: 7px; \n");
      out.write("                border: 1px solid #CCCCCC; \n");
      out.write("                padding: 2px; \n");
      out.write("                float: left; \n");
      out.write("                display: inline-block;\n");
      out.write("                background-color:#FDFDFD;\n");
      out.write("                margin-left: 10px;\n");
      out.write("                margin-top: 3px;\n");
      out.write("                margin-bottom: 3px;\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 12px;\n");
      out.write("                font-weight: normal;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .contextItem  {\n");
      out.write("                background-color:White;\n");
      out.write("                color:Black;\n");
      out.write("                font-weight:normal;\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 12px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .contextItem1  {\n");
      out.write("                background-color:White;\n");
      out.write("                color:Black;\n");
      out.write("                font-weight:normal;\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 12px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .contextItem:hover  {\n");
      out.write("                background-color:#0066FF;\n");
      out.write("                color:White;\n");
      out.write("                font-weight:bold;            \n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            .container {\n");
      out.write("                position: absolute;\n");
      out.write("                border: 1px solid black;\n");
      out.write("                left: 2px; \n");
      out.write("                top: 0px;\n");
      out.write("                height: 780px;\n");
      out.write("                width: 270px;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </style>\n");
      out.write("        <script>\n");
      out.write("    \n");
      out.write("            function highlightCluster(node_id, descendant_ids, start, end) {\n");
      out.write("                var i,j;\n");
      out.write("                var dend_lines = document.getElementsByName('clust_' + node_id);\n");
      out.write("                for (i = 0; i < dend_lines.length; i++) {\n");
      out.write("                    dend_lines[i].style.strokeWidth = \"4\";\n");
      out.write("                    dend_lines[i].style.stroke = \"rgb(0,0,0)\";\n");
      out.write("                }\n");
      out.write("                //alert(node_id);\n");
      out.write("                var tag_div = document.getElementById('tag_' + node_id);\n");
      out.write("                //alert(tag_div);\n");
      out.write("                tag_div.style.display = \"inline\";\n");
      out.write("                //alert(tag_div);\n");
      out.write("                \n");
      out.write("                //alert(descendant_ids);\n");
      out.write("                var idarray = descendant_ids.split(',');\n");
      out.write("                for (j = 0; j < idarray.length; j++) {\n");
      out.write("                    var dend_lines = document.getElementsByName('clust_' + idarray[j]);\n");
      out.write("                    for (i = 0; i < dend_lines.length; i++) {\n");
      out.write("                        //alert(dend_lines[i]);\n");
      out.write("                        dend_lines[i].style.strokeWidth = \"4\";\n");
      out.write("                        dend_lines[i].style.stroke = \"rgb(0,0,0)\";\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                parent.showRect(start, end);\n");
      out.write("                \n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function deHighlightCluster(node_id, descendant_ids) {\n");
      out.write("                \n");
      out.write("                var i,j;\n");
      out.write("                var dend_lines = document.getElementsByName('clust_' + node_id);\n");
      out.write("                for (i = 0; i < dend_lines.length; i++) {\n");
      out.write("                    dend_lines[i].style.strokeWidth = \"2\";\n");
      out.write("                    dend_lines[i].style.stroke = \"rgb(192,192,192)\";\n");
      out.write("                }\n");
      out.write("                //alert(node_id);\n");
      out.write("                var tag_div = document.getElementById('tag_' + node_id);\n");
      out.write("                //alert(tag_div);\n");
      out.write("                tag_div.style.display = \"none\";\n");
      out.write("                //alert(tag_div);\n");
      out.write("                \n");
      out.write("                //alert(descendant_ids);\n");
      out.write("                var idarray = descendant_ids.split(',');\n");
      out.write("                for (j = 0; j < idarray.length; j++) {\n");
      out.write("                    var dend_lines = document.getElementsByName('clust_' + idarray[j]);\n");
      out.write("                    for (i = 0; i < dend_lines.length; i++) {\n");
      out.write("                        dend_lines[i].style.strokeWidth = \"2\";\n");
      out.write("                        dend_lines[i].style.stroke = \"rgb(192,192,192)\";\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                parent.hideRect();\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function loadSubTree(node_id, start, end) {\n");
      out.write("                //alert(start);\n");
      out.write("                //alert(end);\n");
      out.write("                parent.loadSubTree(node_id, start, end);\n");
      out.write("            }\n");
      out.write("    \n");
      out.write("            var right_click_start;\n");
      out.write("            var right_click_end;\n");
      out.write("\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    <body style=\"overflow: hidden\">\n");
      out.write("        \n");
      out.write("        ");
  
            
            /*
            double[][] linkage_tree = new double[][]{
                                        {6, 5, 0.5, 2},
                                        {1, 0, 0.75, 2},
                                        {3, 2, 0.80, 2},
                                        {7, 4, 0.85, 3},
                                        {8, 9, 0.90, 4},
                                        {11, 10, 0.95, 7}
                                    };
            
            BinaryTree btree = new BinaryTree(linkage_tree, BinaryTree.LARGEST_CHILD_FIRST_LEAF_ORDER);
            */
            
            /*
            BinaryTree btree = new BinaryTree(
                    Utils.loadDoubleDelimData("D:/ad/sg/phd_project/VTBox/old_pages/ClusteringOutput_0.txt", " ", false),
                    BinaryTree.LARGEST_CHILD_FIRST_LEAF_ORDER
            );
            */
            
            BinaryTree btree = analysis.linkage_tree;
            
            int root_node_id;
            String start_node_id = request.getParameter("start_node_id");
            if (start_node_id != null && start_node_id != "" && !start_node_id.equals("null")) {
                root_node_id = Integer.parseInt(start_node_id);
            } else {
                root_node_id = btree.root_node_id;
            }
            
            ArrayList <Integer> sub_tree = btree.breadthFirstSearch(root_node_id, 25);
            
            HashMap <Integer, TreeNode> treeMap = btree.buildSubTree(sub_tree);
            
            btree.computeEdgeCoordinatesPostOrder (treeMap, treeMap.get(root_node_id));
            
            int imgWidth = 300;
            int imgHeight = 750;

            btree.rescaleEdgeCoordinates(treeMap, imgHeight, imgWidth);
        
      out.write("\n");
      out.write("        \n");
      out.write("        <svg height=\"");
      out.print(imgHeight);
      out.write("\" width=\"");
      out.print(imgWidth);
      out.write("\" style=\"position: absolute; top: 20px; left: 0px\">\n");
      out.write("        ");

            
            Iterator iterator = treeMap.keySet().iterator(); 
            while (iterator.hasNext()){
                
                int node_id = (Integer)iterator.next();
                TreeNode N = treeMap.get(node_id);
                
                if (!N.isSubTreeLeaf) {
                
                    double p0_x = N.p0_x + 5.0;
                    double p1_x = N.p1_x + 5.0;
                    double p2_x = N.p2_x + 5.0;
                    double p3_x = N.p3_x + 5.0;
                    
                    double p0_y = N.p0_y;
                    double p1_y = N.p1_y;
                    double p2_y = N.p2_y;
                    double p3_y = N.p3_y;
                    
                    String descendants = "";
                    for (int i=0; i<N.descendants.size(); i++) {
                        descendants += N.descendants.get(i) + ",";
                    }
                    
                    String left_descendants = "";
                    boolean isLeftClickable = false;
                    if (!N.leftChild.isRealLeaf) {
                        isLeftClickable = true;
                        for (int i=0; i<N.leftChild.descendants.size(); i++) {
                            left_descendants += N.leftChild.descendants.get(i) + ",";
                        }
                    }
                    
                    String right_descendants = "";
                    boolean isRightClickable = false;
                    if (!N.rightChild.isRealLeaf) {
                        isRightClickable = true;
                        for (int i=0; i<N.rightChild.descendants.size(); i++) {
                            right_descendants += N.rightChild.descendants.get(i) + ",";
                        }
                    }
            
        
      out.write("\n");
      out.write("        \n");
      out.write("        ");
  if (isLeftClickable) {  
      out.write("\n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_1\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p0_x);
      out.write("\" y1=\"");
      out.print(p0_y);
      out.write("\" x2=\"");
      out.print(p1_x);
      out.write("\" y2=\"");
      out.print(p1_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\"  onclick=\"loadSubTree(");
      out.print(N.leftChild.nodeID);
      out.write(',');
      out.print(N.leftChild.start);
      out.write(',');
      out.print(N.leftChild.end);
      out.write(")\" onmouseover=\"highlightCluster(");
      out.print(N.leftChild.nodeID);
      out.write(',');
      out.write('\'');
      out.print(left_descendants);
      out.write("')\" onmouseout=\"deHighlightCluster(");
      out.print(N.leftChild.nodeID);
      out.write(',');
      out.write('\'');
      out.print(left_descendants);
      out.write("')\" />\n");
      out.write("        ");
  } else {   
      out.write("    \n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_1\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p0_x);
      out.write("\" y1=\"");
      out.print(p0_y);
      out.write("\" x2=\"");
      out.print(p1_x);
      out.write("\" y2=\"");
      out.print(p1_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\" />\n");
      out.write("        ");
  }   
      out.write("\n");
      out.write("        \n");
      out.write("        <line id=\"");
      out.print(node_id);
      out.write("_2\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p1_x);
      out.write("\" y1=\"");
      out.print(p1_y);
      out.write("\" x2=\"");
      out.print(p2_x);
      out.write("\" y2=\"");
      out.print(p2_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\" onclick=\"loadSubTree(");
      out.print(node_id);
      out.write(',');
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\" onmouseover=\"highlightCluster(");
      out.print(node_id);
      out.write(',');
      out.write('\'');
      out.print(descendants);
      out.write('\'');
      out.write(',');
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\" onmouseout=\"deHighlightCluster(");
      out.print(node_id);
      out.write(',');
      out.write('\'');
      out.print(descendants);
      out.write('\'');
      out.write(',');
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\" onmousedown='hideContextMenu()' onmouseup='hideContextMenu()' oncontextmenu=\"showContextMenu(event,");
      out.print(N.start);
      out.write(',');
      out.print(N.end);
      out.write(")\"/>\n");
      out.write("            \n");
      out.write("        ");
  if (isRightClickable) {  
      out.write("\n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_3\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p2_x);
      out.write("\" y1=\"");
      out.print(p2_y);
      out.write("\" x2=\"");
      out.print(p3_x);
      out.write("\" y2=\"");
      out.print(p3_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\"  onclick=\"loadSubTree(");
      out.print(N.rightChild.nodeID);
      out.write(',');
      out.print(N.rightChild.start);
      out.write(',');
      out.print(N.rightChild.end);
      out.write(")\" onmouseover=\"highlightCluster(");
      out.print(N.rightChild.nodeID);
      out.write(',');
      out.write('\'');
      out.print(right_descendants);
      out.write("')\" onmouseout=\"deHighlightCluster(");
      out.print(N.rightChild.nodeID);
      out.write(',');
      out.write('\'');
      out.print(right_descendants);
      out.write("')\" />\n");
      out.write("        ");
  } else {   
      out.write("    \n");
      out.write("            <line id=\"");
      out.print(node_id);
      out.write("_3\" name=\"clust_");
      out.print(node_id);
      out.write("\" x1=\"");
      out.print(p2_x);
      out.write("\" y1=\"");
      out.print(p2_y);
      out.write("\" x2=\"");
      out.print(p3_x);
      out.write("\" y2=\"");
      out.print(p3_y);
      out.write("\" style=\"stroke:rgb(192,192,192);stroke-width:2\" />\n");
      out.write("        ");
  }   
      out.write("\n");
      out.write("        \n");
      out.write("        <text id=\"tag_");
      out.print(node_id);
      out.write("\" x=\"");
      out.print((p2_x+5));
      out.write("\" y=\"");
      out.print((p2_y + p1_y)/2-5);
      out.write("\" font-family=\"Verdana\" font-size=\"10\" fill=\"blue\" style=\"display: none\">");
      out.print(node_id);
      out.write(',');
      out.write(' ');
      out.print(N.start);
      out.write(',');
      out.write(' ');
      out.print(N.end);
      out.write("</text>\n");
      out.write("        \n");
      out.write("        ");
  
                }
            }
        
      out.write("\n");
      out.write("        \n");
      out.write("        </svg>\n");
      out.write("        \n");
      out.write("        ");

            //TreeNode N = treeMap.get(root_node_id);
            //analysis.state_variables.put("start_node_id", (double)root_node_id);
            //analysis.state_variables.put("dendrogram_start", (double)N.start);
            //analysis.state_variables.put("dendrogram_end", (double)N.end);
            //analysis.state_variables.pushDendrogramHistory(new Integer[]{root_node_id, N.start, N.end});
        
      out.write("\n");
      out.write("        \n");
      out.write("    </body>\n");
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
