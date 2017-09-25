/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import structure.AnalysisContainer;
import structure.Serializer;

/**
 *
 * @author abhik
 */
public class SerializeAnalysisServlet extends HttpServlet {

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
        String filename = analysis_name + ".slide";
        
        String status = "";
        
        try {
            Serializer serializer = new Serializer();

            AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
            String analysis_filepath = analysis.base_path + File.separator + "data";
            serializer.serializeAnalysis(analysis, analysis_filepath);

            response.setContentType("application/download");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);

            //ServletContext ctx = getServletContext();
            //InputStream is = ctx.getResourceAsStream("http://localhost:8080/VTBox/analysis_files/" + filename);

            int read = 0;
            File f = new File(analysis_filepath + File.separator + filename);
            InputStream is = new FileInputStream(f);
            byte[] bytes = new byte[(int)f.length()];
            OutputStream os = response.getOutputStream();

            while ((read = is.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            os.close();
        } catch (Exception e) {
            status = e.getMessage();
        }
    
        //getServletContext().getRequestDispatcher(
        //        "/downloadCompleted.jsp?status=" + status + "&analysis_filename=" + filename
        //).forward(request, response);
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
