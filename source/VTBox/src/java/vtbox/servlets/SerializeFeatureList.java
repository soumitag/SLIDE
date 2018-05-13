/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import searcher.GeneObject;
import structure.AnalysisContainer;
import structure.MetaData;

/**
 *
 * @author Soumita
 */
public class SerializeFeatureList extends HttpServlet {

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
        
        HttpSession session = request.getSession(false);  
        String analysis_name = request.getParameter("analysis_name");
        String feature_list_name = request.getParameter("filename");
        String delimval = request.getParameter("delimiter");
        String filename = feature_list_name + ".txt";
        
        String delimiter = null;
        if (delimval.equals("lineS")) {
            delimiter = "\n";
        } else if (delimval.equals("commaS")) {
            delimiter = ",";
        } else if (delimval.equals("tabS")) {
            delimiter = "\t";
        } else if (delimval.equals("spaceS")) {
            delimiter = " ";
        } else if (delimval.equals("semiS")) {
            delimiter = ";";
        } else if (delimval.equals("hyphenS")) {
            delimiter = "-";
        }
        
        response.setContentType("application/download");
	response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        
        String str = "";
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        ArrayList <Integer> filterList = analysis.filterListMap.get(feature_list_name);
        
        for (int i=0; i<filterList.size(); i++) {
            str = str + analysis.database.features.get(filterList.get(i)).identifier + delimiter;
        }
        
        byte[] bytes = str.getBytes();
        OutputStream os = response.getOutputStream();
        os.write(bytes, 0, bytes.length);
        os.flush();
        os.close();
        
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
