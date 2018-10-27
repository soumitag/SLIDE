/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import searcher.GeneObject;
import searcher.GoObject;
import searcher.PathwayObject;
import searcher.Searcher;
import structure.AnalysisContainer;
import structure.CompactSearchResultContainer;

/**
 *
 * @author Soumita
 */
public class DoSearch extends HttpServlet {

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
            
            HttpSession session = request.getSession(false);
            
            String analysis_name = request.getParameter("analysis_name");
            String searchString = request.getParameter("searchText");
            String queryType = request.getParameter("queryType");
            String searchType = request.getParameter("searchType");

            AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
            Searcher searcher = (Searcher)analysis.searcher;

            ArrayList <CompactSearchResultContainer> current_search_results = new ArrayList <CompactSearchResultContainer> ();

            StringTokenizer st = new StringTokenizer(searchString, ",");
            if (queryType != null) {
                if (queryType.equals("pathid") || queryType.equals("pathname")) {

                    while(st.hasMoreTokens()) {
                        ArrayList <PathwayObject> part_paths = searcher.processPathQuery(st.nextToken(), searchType, queryType);
                        for(int i = 0; i < part_paths.size(); i++) {
                            PathwayObject path = part_paths.get(i);
                            for (int j = 0; j < path.entrez_ids.size(); j++) {
                                CompactSearchResultContainer csrc = new CompactSearchResultContainer();
                                csrc.createPathwaySearchResult(path.entrez_ids.get(j), path._id, path.pathway);
                                current_search_results.add(csrc);
                            }
                        }
                    }

                } else if (queryType.equals("goid") || queryType.equals("goterm")) {

                    while(st.hasMoreTokens()) {
                        ArrayList <GoObject> part_go_terms = searcher.processGOQuery(st.nextToken(), searchType, queryType);
                        for(int i = 0; i < part_go_terms.size(); i++){
                            GoObject go = part_go_terms.get(i);
                            for (int j = 0; j < go.entrez_ids.size(); j++) {
                                CompactSearchResultContainer csrc = new CompactSearchResultContainer();
                                csrc.createGOSearchResult(go.entrez_ids.get(j), go.goID, go.goTerm);
                                current_search_results.add(csrc);
                            }
                        }
                    }

                } else if (queryType.equals("entrez") || queryType.equals("genesymbol") || queryType.equals("refseq")
                            || queryType.equals("ensembl_gene_id") || queryType.equals("ensembl_transcript_id") || queryType.equals("ensembl_protein_id")
                            || queryType.equals("uniprot_id")) {

                    while(st.hasMoreTokens()) {
                        String qString = st.nextToken();
                        ArrayList <GeneObject> part_genes = searcher.processGeneQuery(qString, searchType, queryType);
                        for(int i = 0; i < part_genes.size(); i++){
                            GeneObject gene = part_genes.get(i);
                            CompactSearchResultContainer csrc = new CompactSearchResultContainer();
                            csrc.createGeneSearchResult(gene.entrez_id, gene.gene_identifier);
                            current_search_results.add(csrc);
                        }
                        current_search_results.addAll(
                                analysis.database.metadata.searchMetadata(analysis.database.features, queryType, qString)
                        );
                    }

                } else if (queryType.startsWith("_")) {
                    while(st.hasMoreTokens()) {
                        current_search_results.addAll(
                            analysis.database.metadata.searchNonStandardMetadata(analysis.database.features, queryType, st.nextToken())
                        );
                    }
                } else if (queryType.startsWith("feature_list")) {
                    
                    while(st.hasMoreTokens()) {
                        String qString = st.nextToken().trim();
                        ArrayList <Integer> row_ids = analysis.filterListMap.get(qString);
                        for (int r = 0; r < row_ids.size(); r++) {
                            String entrez_id = analysis.database.features.get(row_ids.get(r)).entrez;
                            String gene_identifier = analysis.database.features.get(row_ids.get(r)).identifier;
                            CompactSearchResultContainer csrc = new CompactSearchResultContainer();
                            csrc.createGeneSearchResult(entrez_id, gene_identifier);
                            current_search_results.add(csrc);
                        }
                    }
                    
                }
            }

            ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
            search_results.add(current_search_results);

            ArrayList <String> search_strings = analysis.search_strings;
            String eq = "";
            if(searchType.equals("exact")){
                eq = " = ";
            } else {
                eq = " &cong; ";
            }
            String current_search_string = queryType + eq + searchString;
            search_strings.add(current_search_string);
            
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
