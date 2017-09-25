/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import structure.AnalysisContainer;
import structure.CompactSearchResultContainer;
import vtbox.SessionUtils;

/**
 *
 * @author Soumita
 */
public class DeleteSearchServlet extends HttpServlet {

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
    
            int search_number = Integer.parseInt(request.getParameter("search_number"));
            String analysis_name = request.getParameter("analysis_name");
            HttpSession session = request.getSession(false);
            AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
            ArrayList <ArrayList<CompactSearchResultContainer>> search_results = analysis.search_results;
            search_results.remove(search_number);

            ArrayList <String> search_strings = analysis.search_strings;
            search_strings.remove(search_number);
            
            PrintWriter out = response.getWriter();
            out.println(search_strings.size());
            
        } catch (Exception e) {
            
            HttpSession session = request.getSession(false);
            SessionUtils.logException(session, request, e);
            getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
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
