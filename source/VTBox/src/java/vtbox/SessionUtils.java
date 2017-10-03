/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vtbox;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import structure.AnalysisContainer;

/**
 *
 * @author soumita
 */
public class SessionUtils {

    public static void logException (HttpSession session, HttpServletRequest request, Exception e) {

        try {

            System.out.println("An exception occured while serving: " + request.getRequestURI());
            System.out.println(e);
            System.out.println(Arrays.toString(e.getStackTrace()));

            String analysis_name = request.getParameter("analysis_name");
            AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
            analysis.logger.writeLog(1, request.getRequestURI(), "FATAL", e.toString());

        } catch (Exception e1) {

            System.out.println("An exception occured in SessionUtils.logException() while logging...");
            System.out.println(e1);
            System.out.println(Arrays.toString(e1.getStackTrace()));
        }

    }
}
