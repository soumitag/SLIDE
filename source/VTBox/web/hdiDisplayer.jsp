<%-- 
    Document   : heatmapDendogramImages
    Created on : 18 Feb, 2017, 10:10:15 PM
    Author     : Soumita
--%>


<%@page import="vtbox.SessionUtils"%>
<%@page import="structure.AnalysisContainer"%>
<%@page import="structure.Data"%>
<%@page import="graphics.Heatmap"%>
<%@page import="algorithms.clustering.HierarchicalClustering"%>
<%@page import="utils.Logger"%>
<%@page import="algorithms.clustering.BinaryTree"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    
    Logger logger = analysis.logger;
    
    Data database = analysis.database;
    HashMap <String, String> visualization_params = analysis.visualization_params;
    
    BinaryTree linkage_tree;
    
    boolean do_clustering = Boolean.parseBoolean(analysis.clustering_params.get("do_clustering"));
    
    if (do_clustering) {
        
        HashMap <String, String> clustering_params = analysis.clustering_params;
        String linkage = clustering_params.get("linkage");
        String distance_func = clustering_params.get("distance_func");    
        int leaf_ordering_strategy = Integer.parseInt(visualization_params.get("leaf_ordering_strategy"));
        boolean use_cached = Boolean.parseBoolean(clustering_params.get("use_cached"));
    
        String py_module_path = ((HashMap <String, String>)session.getAttribute("slide_config")).get("py-module-path");
        String py_home = ((HashMap <String, String>)session.getAttribute("slide_config")).get("python-dir");
            
        HierarchicalClustering hac = new HierarchicalClustering (
                database, linkage, distance_func, leaf_ordering_strategy, 
                analysis.base_path + File.separator + "data",
                py_module_path, py_home);
        
        linkage_tree = hac.doClustering(use_cached);
        analysis.setLinkageTree(linkage_tree, true);
        
    } else {
        
        linkage_tree = new BinaryTree(database.features.size());
        analysis.setLinkageTree(linkage_tree, false);
        
    }

    HashMap <String, ArrayList <Integer>> entrezPosMap = new HashMap <String, ArrayList <Integer>> ();
    for (int i = 0 ; i < linkage_tree.leaf_ordering.size(); i++){
        
        ArrayList <Integer> pos;
        String eid = database.features.get(linkage_tree.leaf_ordering.get(i)).entrezId;
        if(entrezPosMap.containsKey(eid)){
            pos = entrezPosMap.get(eid);
            pos.add(i);
        } else {
            pos = new ArrayList <Integer> ();
            pos.add(i);
        }
        entrezPosMap.put(eid, pos);
    }
    analysis.setEntrezPosMap(entrezPosMap);
    System.out.println("3");
    
    int nBins = Integer.parseInt(visualization_params.get("nBins"));
    String bin_range_type = visualization_params.get("bin_range_type");
    Heatmap heatmap = null;
    if (bin_range_type.equals("start_end_bins")) {
        double range_start = Double.parseDouble(visualization_params.get("bin_range_start"));
        double range_end = Double.parseDouble(visualization_params.get("bin_range_end"));
        heatmap = new Heatmap(database, nBins, bin_range_type, range_start, range_end, linkage_tree.leaf_ordering);
    } else {
        heatmap = new Heatmap(database, nBins, bin_range_type, linkage_tree.leaf_ordering);
    }
    heatmap.genColorData();
    heatmap.assignBinsToRows();
    analysis.setHeatmap(heatmap);
    System.out.println("4");
%>

<jsp:forward page="drillDownPanel.jsp">
    <jsp:param name="show_dendrogram" value="no" />
    <jsp:param name="analysis_name" value="<%=analysis_name%>" />
    <jsp:param name="type" value="global_map" />
</jsp:forward>

<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>