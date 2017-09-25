/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.ReadConfig;
import utils.SessionManager;

/**
 *
 * @author Soumita
 */
public class DataUploader extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String DATA_DIRECTORY = "data";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 1024;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 1024;
    
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
        
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            return;
        }

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Sets the size threshold beyond which files are written directly to
        // disk.
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        // Sets the directory used to temporarily store files that are larger
        // than the configured size threshold. We use temporary directory for
        // java
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // get base path for analysis
        String installPath = SessionManager.getInstallPath(
                getServletContext().getResourceAsStream("/WEB-INF/slide-web-config.txt"));
        
        // data will be uploaded temporarily to session-dir. session-dir is already created in newExperiment.jsp
        // here just get the path
        String uploadFolder = SessionManager.getSessionDir(installPath, request.getSession().getId());
        
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Set overall request size constraint
        upload.setSizeMax(MAX_REQUEST_SIZE);

        String analysis_name = request.getParameter("analysis_name");
        
        String filePath = "";
        String fileName = "";
        String data_filename = "", mapping_filename = "";
        int count = 0;
        try {
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    if (fileName.toLowerCase().endsWith(".txt")) {
                        filePath = uploadFolder + File.separator + analysis_name + "_" + fileName;
                        File uploadedFile = new File(filePath);
                        System.out.println(filePath);
                        // saves the file to upload directory
                        item.write(uploadedFile);
                        item.delete();
                        
                        if(count == 0){
                            data_filename = fileName;
                        } else if (count == 1){
                            mapping_filename = fileName;
                        }
                        count++;
                    } else {
                        String msg = "Upload Failed. Files with only .txt extensions can be uploaded";
                        getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=" + msg + "&filename=" + fileName).forward(request, response);
                        return;
                    }
                }
            }
            
            // displays uploadCompleted.jsp page after upload finished
            getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=&data_filename=" + data_filename + "&mapping_filename=" + mapping_filename).forward(request, response);

        } catch (FileUploadException ex) {
            // displays uploadCompleted.jsp page after upload finished
            getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=" + ex.toString() + "&filename=" + fileName).forward(request, response);
            //throw new ServletException(ex);
        } catch (Exception ex) {
            // displays uploadCompleted.jsp page after upload finished
            getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=" + ex.toString() + "&filename=" + fileName).forward(request, response);
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
