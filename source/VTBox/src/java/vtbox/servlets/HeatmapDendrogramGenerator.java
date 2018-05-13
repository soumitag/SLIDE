/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import algorithms.clustering.BinaryTree;
import algorithms.clustering.HierarchicalClusterer;
import graphics.Heatmap;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import searcher.GeneObject;
import structure.AnalysisContainer;
import structure.Data;
import structure.Feature;
import structure.MetaData;
import vtbox.SessionUtils;

/**
 *
 * @author Soumita
 */
public class HeatmapDendrogramGenerator extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            
            String analysis_name = request.getParameter("analysis_name");
            
            HttpSession session = request.getSession(false);
            AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

            Data database = analysis.database;
            
            BinaryTree linkage_tree;

            boolean do_clustering = analysis.clustering_params.do_clustering;

            if (do_clustering) {

                String linkage = analysis.clustering_params.linkage;
                String distance_func = analysis.clustering_params.distance_func;    
                int leaf_ordering_strategy = Integer.parseInt(analysis.visualization_params.leaf_ordering_strategy);

                linkage_tree = analysis.hac.doClustering(
                        analysis, linkage, distance_func, leaf_ordering_strategy
                );
                
                analysis.setLinkageTree(linkage_tree, true);

            } else {

                linkage_tree = new BinaryTree(database.metadata.nFeatures);
                analysis.setLinkageTree(linkage_tree, false);

            }

            HashMap <String, ArrayList <Integer>> entrezPosMap = new HashMap <String, ArrayList <Integer>> ();
            
            for (int i = 0 ; i < linkage_tree.leaf_ordering.size(); i++) {
                Feature f = database.features.get(linkage_tree.leaf_ordering.get(i));
                if (f.hasBadEntrez) {
                    
                    String eid = f.entrez;
                    ArrayList <Integer> pos;
                    if(entrezPosMap.containsKey(eid)){
                        pos = entrezPosMap.get(eid);
                        pos.add(i);
                    } else {
                        pos = new ArrayList <Integer> ();
                        pos.add(i);
                    }
                    entrezPosMap.put(eid, pos);
                        
                } else {
                    ArrayList <String> entrez_ids = f.entrez_ids;

                    for (int j=0; j<entrez_ids.size(); j++) {
                        String eid = entrez_ids.get(j);
                        ArrayList <Integer> pos;
                        if(entrezPosMap.containsKey(eid)){
                            pos = entrezPosMap.get(eid);
                            pos.add(i);
                        } else {
                            pos = new ArrayList <Integer> ();
                            pos.add(i);
                        }
                        entrezPosMap.put(eid, pos);
                    }
                }
            }
            analysis.setEntrezPosMap(entrezPosMap);

            int nBins = analysis.visualization_params.nBins;
            String bin_range_type = analysis.visualization_params.bin_range_type;
            Heatmap heatmap = null;
            if (bin_range_type.equals("start_end_bins")) {
                double range_start = analysis.visualization_params.bin_range_start;
                double range_end = analysis.visualization_params.bin_range_end;
                heatmap = new Heatmap(database, nBins, bin_range_type, range_start, range_end, linkage_tree.leaf_ordering);
            } else {
                heatmap = new Heatmap(database, nBins, bin_range_type, linkage_tree.leaf_ordering);
            }
            heatmap.genColorData(analysis.visualization_params.heatmap_color_scheme);
            heatmap.assignBinsToRows();
            analysis.setHeatmap(heatmap);
            
            getServletContext().getRequestDispatcher(
                    "/drillDownPanel.jsp?analysis_name=" + analysis_name + "&show_dendrogram=no&type=global_map"
            ).forward(request, response);
            
        } catch (Exception e) {
            
            HttpSession session = request.getSession(false);
            SessionUtils.logException(session, request, e);
            getServletContext().getRequestDispatcher("/Exception.jsp?type=clustering_error").forward(request, response);
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
