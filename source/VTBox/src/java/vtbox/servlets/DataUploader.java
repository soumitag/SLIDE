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
import utils.Utils;

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

        String type = request.getParameter("upload_type");
        String analysis_name = request.getParameter("analysis_name");
        String delimval = request.getParameter("delimval");
        
        
        if (type.equals("entrez_list")) {
            handleEntrezListUpload(request, response, analysis_name, delimval, upload, uploadFolder);
            return;
        }
        
        
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
                    
                    if (fileName.toLowerCase().endsWith(".xls") ||
                        fileName.toLowerCase().endsWith(".xlsx")) {
                        
                        String msg = "Upload Failed! SLIDE cannot process Excel files. Please provide a delimited file in tsv, csv or txt format.";
                        getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=" + msg + "&filename=" + fileName + "&type=" + type).forward(request, response);
                        return;
                        
                    } else {
                    
                        filePath = uploadFolder + File.separator + analysis_name + "_" + type + "_" + fileName;
                        File uploadedFile = new File(filePath);
                        System.out.println(filePath);
                        // saves the file to upload directory
                        item.write(uploadedFile);
                        item.delete();
                    }
                }
            }
            
            // displays uploadCompleted.jsp page after upload finished
            if (type.equals("mapping")) {
                String hasTwoGroupingFactors = request.getParameter("is_time_course");
                String hasGroupingFactor = request.getParameter("has_replicates");
                String data_filename = request.getParameter("data_filename");
                String data_fileDelimiter = request.getParameter("data_fileDelimiter");
                getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=&filename=" + fileName + 
                                                         "&analysis_name=" + analysis_name + 
                                                         "&delimval=" + delimval + 
                                                         "&type=" + type +
                                                         "&hasTwoGroupingFactors=" + hasTwoGroupingFactors + 
                                                         "&hasGroupingFactor=" + hasGroupingFactor + 
                                                         "&data_filename=" + data_filename +
                                                         "&data_fileDelimiter=" + data_fileDelimiter
                ).forward(request, response);
            } else {
                getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=&filename=" + fileName + 
                                                         "&analysis_name=" + analysis_name + 
                                                         "&delimval=" + delimval  + 
                                                         "&type=" + type
                ).forward(request, response);
            }

        } catch (FileUploadException ex) {
            // displays uploadCompleted.jsp page after upload finished
            getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=" + ex.toString() + "&filename=" + fileName + "&type=" + type).forward(request, response);
            //throw new ServletException(ex);
        } catch (Exception ex) {
            // displays uploadCompleted.jsp page after upload finished
            getServletContext().getRequestDispatcher("/uploadCompleted.jsp?status=" + ex.toString() + "&filename=" + fileName + "&type=" + type).forward(request, response);
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

    
    private void handleEntrezListUpload(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        String analysis_name,
                                        String delimval,
                                        ServletFileUpload upload,
                                        String sessionFolder) 
    throws ServletException, IOException {
        
        String list_name = request.getParameter("list_name");
        
        String fileName = "";
        String filePath = "";
        
        try {
            
            // Parse the request
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    
                    filePath = sessionFolder + File.separator + analysis_name + File.separator + fileName;
                    File uploadedFile = new File(filePath);
                    System.out.println(filePath);
                    // saves the file to upload directory
                    item.write(uploadedFile);
                    item.delete();
                    
                }
            }
            
            getServletContext().getRequestDispatcher("/createSubAnalysis.jsp?mode=file&analysis_name=" + analysis_name + "&list_name=" + list_name + "&file_name=" + filePath + "&delimval=" + delimval).forward(request, response);
            
        } catch (Exception e) {

            System.out.println(e);
            getServletContext().getRequestDispatcher("/createSubAnalysis.jsp?mode=fileupload_error&analysis_name=" + analysis_name + "&list_name=" + list_name).forward(request, response);
            
        }
        
    }
    
}
