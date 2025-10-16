package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout Controller
 * Handles user logout
 * @author MindyCoding by Tran
 */
public class LogoutController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Get current session
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                // Invalidate session (clear all attributes)
                session.invalidate();
                System.out.println("User logged out successfully.");
            }
            
            // Set success message
            request.setAttribute("SUCCESS_MESSAGE", "You have been logged out successfully!");
            
        } catch (Exception e) {
            log("Error at LogoutController: " + e.toString());
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Logout Controller - Handles user logout";
    }
}