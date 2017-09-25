/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import searcher.Searcher;
import structure.Serializer;
import structure.AnalysisContainer;
import utils.Logger;
import utils.ReadConfig;
import utils.SessionManager;

/**
 *
 * @author Soumita
 */
public class LoadAnalysis extends HttpServlet {

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
        
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // get session
        HttpSession session = request.getSession(false);
        // check if a session already exists 
        if (session == null) {
            // if not, create new session
            session = request.getSession(true);
        }
        
        // constructs the folder where uploaded file will be stored
        String installPath = SessionManager.getInstallPath(
                getServletContext().getResourceAsStream("slide-web-config.txt"));
        String session_dir = SessionManager.createSessionDir(installPath, request.getSession().getId());
        
        String filePath = "";
        String fileName = "";

        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    if (fileName.toLowerCase().endsWith(".slide")) {
                        filePath = session_dir + File.separator + fileName;
                        File uploadedFile = new File(filePath);
                        System.out.println(filePath);
                        // saves the file to upload directory
                        item.write(uploadedFile);
                    } else {
                        String msg = "Upload Failed for " + fileName + ". Only .slide files can be opened.";
                        getServletContext().getRequestDispatcher("/index.jsp?status=" + msg + "&target=loader").forward(request, response);
                        return;
                    }
                }
            }
            
            Serializer serializer = new Serializer();
            AnalysisContainer analysis_1 = serializer.loadAnalysis(filePath);
            
            // check if analysis of same name already exists
            if (session.getAttribute(analysis_1.analysis_name) != null) {
                // if it does send back to index.jsp
                getServletContext().getRequestDispatcher(
                        "/index.jsp?status=analysis_exists&analysis_name=" + analysis_1.analysis_name + "&target=loader"
                ).forward(request, response);
                return;
            }
            
            // set base URL
            String url = request.getRequestURL().toString();
            String base_url = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
            session.setAttribute("base_url", base_url);
            
            // load system configuration details
            HashMap <String, String> slide_config = ReadConfig.getSlideConfig(installPath);
            session.setAttribute("slide_config", slide_config);
            
            // reset basepath as session id has changed
            analysis_1.setBasePath(SessionManager.getBasePath(
                    installPath, request.getSession().getId(), analysis_1.analysis_name));
            
            // create analysis sub-folder if it does not already exist
            SessionManager.createAnalysisDirs(analysis_1);
            
            // finally add analysis to session
            session.setAttribute(analysis_1.analysis_name, analysis_1);
            
            // displays uploadCompleted.jsp page after upload finished
            getServletContext().getRequestDispatcher("/displayHome.jsp?analysis_name=" + analysis_1.analysis_name + "&load_type=file").forward(request, response);

        } catch (FileUploadException ex) {
            // displays uploadCompleted.jsp page after upload finished
            String msg = "Upload Failed for " + fileName + ". Check server log for details.";
            getServletContext().getRequestDispatcher("/index.jsp?status=" + msg + "&target=loader").forward(request, response);
            //throw new ServletException(ex);
        } catch (Exception ex) {
            // displays uploadCompleted.jsp page after upload finished
            String msg = "Upload Failed for " + fileName + ". Check server log for details.";
            getServletContext().getRequestDispatcher("/index.jsp?status=" + msg + "&target=loader").forward(request, response);
            //throw new ServletException(ex);
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
