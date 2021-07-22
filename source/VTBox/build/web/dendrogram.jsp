<%-- 
    Document   : dendrogram
    Created on : 15 Mar, 2017, 11:29:53 AM
    Author     : soumitag
--%>
<%@page import="graphics.layouts.DrillDownPanelLayout"%>
<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="algorithms.clustering.TreeNode"%>
<%@page import="java.util.HashMap"%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="structure.Data, utils.Logger, graphics.Heatmap, utils.Utils"%>
<!DOCTYPE html>
<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer) session.getAttribute(analysis_name);

    HashMap <String, ArrayList <Integer>> filterListMap = analysis.filterListMap;
    Set<String> keys = filterListMap.keySet();
    Iterator iter = keys.iterator();
    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            
            .dend_edge {
                position: absolute;
                text-align: left;
                cursor: pointer;
                border-radius: 7px; 
                border: 1px solid #CCCCCC; 
                padding: 2px; 
                float: left; 
                display: inline-block;
                background-color:#FDFDFD;
                margin-left: 10px;
                margin-top: 3px;
                margin-bottom: 3px;
                font-family: verdana;
                font-size: 12px;
                font-weight: normal;
            }
            
            .contextItem  {
                background-color:White;
                color:Black;
                font-weight:normal;
                font-family: verdana;
                font-size: 12px;
            }
            
            .contextItem1  {
                background-color:White;
                color:Black;
                font-weight:normal;
                font-family: verdana;
                font-size: 12px;
            }
            
            .contextItem:hover  {
                background-color:#0066FF;
                color:White;
                font-weight:bold;            
            }
            
            .container {
                position: absolute;
                border: 1px solid black;
                left: 2px; 
                top: 0px;
                height: 780px;
                width: 270px;
            }
            
        </style>
        <script>
    
            function highlightCluster(node_id, descendant_ids, start, end) {
                var i,j;
                var dend_lines = document.getElementsByName('clust_' + node_id);
                for (i = 0; i < dend_lines.length; i++) {
                    dend_lines[i].style.strokeWidth = "4";
                    dend_lines[i].style.stroke = "rgb(0,0,0)";
                }
                //alert(node_id);
                //var tag_div = document.getElementById('tag_' + node_id);
                //alert(tag_div);
                //tag_div.style.display = "inline";
                //alert(tag_div);
                
                //alert(descendant_ids);
                var idarray = descendant_ids.split(',');
                for (j = 0; j < idarray.length; j++) {
                    var dend_lines = document.getElementsByName('clust_' + idarray[j]);
                    for (i = 0; i < dend_lines.length; i++) {
                        //alert(dend_lines[i]);
                        dend_lines[i].style.strokeWidth = "4";
                        dend_lines[i].style.stroke = "rgb(0,0,0)";
                    }
                }
                
                parent.showRect(start, end);
                
            }
            
            function deHighlightCluster(node_id, descendant_ids) {
                
                var i,j;
                var dend_lines = document.getElementsByName('clust_' + node_id);
                for (i = 0; i < dend_lines.length; i++) {
                    dend_lines[i].style.strokeWidth = "2";
                    dend_lines[i].style.stroke = "rgb(192,192,192)";
                }
                //alert(node_id);
                //var tag_div = document.getElementById('tag_' + node_id);
                //alert(tag_div);
                //tag_div.style.display = "none";
                //alert(tag_div);
                
                //alert(descendant_ids);
                var idarray = descendant_ids.split(',');
                for (j = 0; j < idarray.length; j++) {
                    var dend_lines = document.getElementsByName('clust_' + idarray[j]);
                    for (i = 0; i < dend_lines.length; i++) {
                        dend_lines[i].style.strokeWidth = "2";
                        dend_lines[i].style.stroke = "rgb(192,192,192)";
                    }
                }
                
                parent.hideRect();
            }
            
            function loadSubTree(node_id, start, end) {
                //alert(start);
                //alert(end);
                parent.loadSubTree(node_id, start, end);
            }
    
            var right_click_start;
            var right_click_end;

        </script>
        
    </head>
    
    <body style="overflow: hidden">
        
        <%  
            
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
            //int imgHeight = 750;
            DrillDownPanelLayout layout = analysis.visualization_params.drill_down_layout;
            int imgHeight = (int)layout.DENDROGRAM_VIEW_FIG_HEIGHT;

            btree.rescaleEdgeCoordinates(treeMap, imgHeight, imgWidth);
        %>
        
        <svg height="<%=imgHeight%>" width="<%=imgWidth%>" style="position: absolute; top: 20px; left: 0px">
        <%
            
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
            
        %>
        
        <%  if (isLeftClickable) {  %>
            <line id="<%=node_id%>_1" name="clust_<%=node_id%>" x1="<%=p0_x%>" y1="<%=p0_y%>" x2="<%=p1_x%>" y2="<%=p1_y%>" style="stroke:rgb(192,192,192);stroke-width:2"  onclick="loadSubTree(<%=N.leftChild.nodeID%>,<%=N.leftChild.start%>,<%=N.leftChild.end%>)" onmouseover="highlightCluster(<%=N.leftChild.nodeID%>,'<%=left_descendants%>')" onmouseout="deHighlightCluster(<%=N.leftChild.nodeID%>,'<%=left_descendants%>')" />
        <%  } else {   %>    
            <line id="<%=node_id%>_1" name="clust_<%=node_id%>" x1="<%=p0_x%>" y1="<%=p0_y%>" x2="<%=p1_x%>" y2="<%=p1_y%>" style="stroke:rgb(192,192,192);stroke-width:2" />
        <%  }   %>
        
        <line id="<%=node_id%>_2" name="clust_<%=node_id%>" x1="<%=p1_x%>" y1="<%=p1_y%>" x2="<%=p2_x%>" y2="<%=p2_y%>" style="stroke:rgb(192,192,192);stroke-width:2" onclick="loadSubTree(<%=node_id%>,<%=N.start%>,<%=N.end%>)" onmouseover="highlightCluster(<%=node_id%>,'<%=descendants%>',<%=N.start%>,<%=N.end%>)" onmouseout="deHighlightCluster(<%=node_id%>,'<%=descendants%>',<%=N.start%>,<%=N.end%>)" onmousedown='hideContextMenu()' onmouseup='hideContextMenu()' oncontextmenu="showContextMenu(event,<%=N.start%>,<%=N.end%>)"/>
            
        <%  if (isRightClickable) {  %>
            <line id="<%=node_id%>_3" name="clust_<%=node_id%>" x1="<%=p2_x%>" y1="<%=p2_y%>" x2="<%=p3_x%>" y2="<%=p3_y%>" style="stroke:rgb(192,192,192);stroke-width:2"  onclick="loadSubTree(<%=N.rightChild.nodeID%>,<%=N.rightChild.start%>,<%=N.rightChild.end%>)" onmouseover="highlightCluster(<%=N.rightChild.nodeID%>,'<%=right_descendants%>')" onmouseout="deHighlightCluster(<%=N.rightChild.nodeID%>,'<%=right_descendants%>')" />
        <%  } else {   %>    
            <line id="<%=node_id%>_3" name="clust_<%=node_id%>" x1="<%=p2_x%>" y1="<%=p2_y%>" x2="<%=p3_x%>" y2="<%=p3_y%>" style="stroke:rgb(192,192,192);stroke-width:2" />
        <%  }   %>
        
        <!--
        <text id="tag_<%=node_id%>" x="<%=(p2_x+5)%>" y="<%=(p2_y + p1_y)/2-5%>" font-family="Verdana" font-size="10" fill="blue" style="display: none"><%=node_id%>, <%=N.start%>, <%=N.end%></text>
        -->
        <%  
                }
            }
        %>
        
        </svg>
        
        <%
            //TreeNode N = treeMap.get(root_node_id);
            //analysis.state_variables.put("start_node_id", (double)root_node_id);
            //analysis.state_variables.put("dendrogram_start", (double)N.start);
            //analysis.state_variables.put("dendrogram_end", (double)N.end);
            //analysis.state_variables.pushDendrogramHistory(new Integer[]{root_node_id, N.start, N.end});
        %>
        
    </body>
</html>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>