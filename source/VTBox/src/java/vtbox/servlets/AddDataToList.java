/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import algorithms.clustering.BinaryTree;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import structure.AnalysisContainer;
import structure.CompactSearchResultContainer;
import structure.Data;

/**
 *
 * @author abhik
 */
public class AddDataToList extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            int retval = 1;
            
            try {

                String analysis_name = request.getParameter("analysis_name");
                String mode = request.getParameter("mode");
                String list_name = request.getParameter("list_name");

                // get session
                HttpSession session = request.getSession(false);
                AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
                HashMap <String, ArrayList<Integer>> filterListMap = analysis.filterListMap;

                ArrayList <Integer> list_i = new ArrayList <Integer> ();

                if (mode.equals("cluster")) {

                    // the start and end row indices (of the leaf ordered data) as a comma delimited string
                    int start = Integer.parseInt(request.getParameter("start"));
                    int end = Integer.parseInt(request.getParameter("end"));

                    Data db = analysis.database;
                    BinaryTree linkage_tree = analysis.linkage_tree;

                    list_i = filterListMap.get(list_name);
                    for (int i=(int)start; i<=(int)end; i++) {
                        int original_row_id = linkage_tree.leaf_ordering.get(i);
                        list_i.add(original_row_id);
                    }

                } else if (mode.equals("selected_list")) {

                    // the user selected gene indices (of the original data) as a comma delimited string
                    String list_data = request.getParameter("list_data");

                    list_i = filterListMap.get(list_name);
                    StringTokenizer st = new StringTokenizer(list_data, ",");
                    while (st.hasMoreTokens()) {
                        list_i.add(Integer.parseInt(st.nextToken()));
                    }

                } else if (mode.equals("search_result")) {
                    System.out.println("This seems impossible");;
                    // the user selected gene indices (of the original data) as a comma delimited string
                    String search_indices = request.getParameter("search_indices");
                    HashMap <String, ArrayList <Integer>> entrezPosMap = analysis.entrezPosMap;
                    BinaryTree linkage_tree = analysis.linkage_tree;
                    ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;

                    list_i = filterListMap.get(list_name);
                    StringTokenizer st = new StringTokenizer(search_indices, ",");
                    while (st.hasMoreTokens()) {
                        int index = Integer.parseInt(st.nextToken());
                        ArrayList <CompactSearchResultContainer> search_results_i = search_results.get(index);
                        for (int j = 0; j < search_results_i.size(); j++) {
                            CompactSearchResultContainer search_results_ij = search_results_i.get(j);
                            ArrayList <Integer> positions = entrezPosMap.get(search_results_ij.entrez_id);

                            if (positions != null) {
                                for (int k = 0; k < positions.size(); k++) {
                                    int original_row_id = linkage_tree.leaf_ordering.get(positions.get(k));
                                    list_i.add(original_row_id);
                                    System.out.println(original_row_id);
                                }
                            }
                        }
                    }

                }

                filterListMap.put(list_name, list_i);

            } catch (Exception e) {
                retval = 0;
            }
            
            
            out.println(retval);
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
