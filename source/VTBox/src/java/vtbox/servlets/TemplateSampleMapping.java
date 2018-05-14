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
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.SessionManager;
import utils.Utils;

/**
 *
 * @author Soumita
 */
public class TemplateSampleMapping extends HttpServlet {

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
        
        try {
            
            String analysis_name = request.getParameter("analysis_name");
            String colnames = request.getParameter("colnames");
            String metacolnames = request.getParameter("metacolnames");
            String has_group_1 = request.getParameter("has_replicates");
            String has_group_2 = request.getParameter("is_time_course");
            //String delimiter = Utils.getDelimiter(request.getParameter("delimiter"));
            String delimiter = "\t";
            
            //String installPath = SessionManager.getInstallPath(getServletContext().getResourceAsStream("/WEB-INF/slide-web-config.txt"));
            //String mapping_filepath = SessionManager.getBasePath(installPath, request.getSession().getId(), analysis_name);
            String mapping_filename = analysis_name + "_sample_mapping_template.txt";
            
            String[] colnames_arr = colnames.split(",");
            ArrayList <String> metacolnames_arr = new ArrayList(Arrays.asList(metacolnames.split(",")));
             
            String out_str = "#This is an auto generated sample mapping file for SLIDE\n";
            out_str += "#Edit the second and third columns (if any) of this file and upload it back in SLIDE\n";
            out_str += "#To edit the file replace the GroupName_for_* values with the actual group names\n";
            
            if(has_group_1.equalsIgnoreCase("yes") && has_group_2.equalsIgnoreCase("no")) {
                out_str += "#SampleName" + delimiter + "GroupName\n";
            } else if(has_group_1.equalsIgnoreCase("yes") && has_group_2.equalsIgnoreCase("yes")) {
                out_str += "#SampleName" + delimiter + "GroupName_1" + delimiter + "GroupName_2\n";
            } else if(has_group_1.equalsIgnoreCase("no") && has_group_2.equalsIgnoreCase("yes")) {
                out_str += "#SampleName" + delimiter + "GroupName\n";
            }
            
            for (int i=0; i<colnames_arr.length; i++) {
                if (!metacolnames_arr.contains(colnames_arr[i])) {
                    
                    if(has_group_1.equalsIgnoreCase("yes") && has_group_2.equalsIgnoreCase("no")) {
                        out_str += colnames_arr[i] + delimiter + "GroupName_for_" + colnames_arr[i] + "\n";
                    } else if(has_group_1.equalsIgnoreCase("yes") && has_group_2.equalsIgnoreCase("yes")) {
                        out_str += colnames_arr[i] + delimiter + "GroupName_for_" + colnames_arr[i] + delimiter + "GroupName_for_" + colnames_arr[i] + "\n";
                    } else if(has_group_1.equalsIgnoreCase("no") && has_group_2.equalsIgnoreCase("yes")) {
                        out_str += colnames_arr[i] + delimiter + "GroupName_for_" + colnames_arr[i] + "\n";
                    }
                    
                }
            }
            
            response.setContentType("application/download");
            response.setHeader("Content-Disposition", "attachment;filename=" + mapping_filename);
            
            byte[] bytes = out_str.getBytes();
            OutputStream os = response.getOutputStream();
            os.write(bytes, 0, bytes.length);
            os.flush();
            os.close();
            
        } catch (Exception e) {
            System.out.println(e);
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
