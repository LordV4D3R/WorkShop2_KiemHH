package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserDAO;
import models.UserDTO;

/**
 * Login Controller
 * Handles user authentication
 * @author MindyCoding by Tran
 */
public class LoginController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String STAFF_PAGE = "LoadMobileController";  // Staff home - load all mobiles
    private static final String USER_PAGE = "user_home.jsp";          // User home page

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url = LOGIN_PAGE;
        
        try {
            // Get parameters from login form
            String userId = request.getParameter("userId");
            String passwordStr = request.getParameter("password");
            
            // Validate input
            if (userId == null || userId.trim().isEmpty() || 
                passwordStr == null || passwordStr.trim().isEmpty()) {
                
                request.setAttribute("ERROR_MESSAGE", "Please enter both User ID and Password!");
                url = LOGIN_PAGE;
            } else {
                // Parse password to int
                int password = Integer.parseInt(passwordStr);
                
                // Check login
                UserDAO userDAO = new UserDAO();
                UserDTO user = userDAO.checkLogin(userId, password);
                
                if (user != null) {
                    // Login successful - Create session
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", user);
                    
                    // Log for debugging
                    System.out.println("Login successful: " + user.toString());
                    
                    // Redirect based on role
                    if (user.getRole() == 1) {
                        // Staff - redirect to mobile management
                        url = STAFF_PAGE;
                    } else {
                        // User - redirect to user home page
                        url = USER_PAGE;
                    }
                    
                } else {
                    // Login failed
                    request.setAttribute("ERROR_MESSAGE", "Invalid User ID or Password!");
                    url = LOGIN_PAGE;
                }
            }
            
        } catch (NumberFormatException e) {
            log("Invalid password format: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Password must be numeric!");
            url = LOGIN_PAGE;
            
        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "System error. Please try again later.");
            url = LOGIN_PAGE;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
        return "Login Controller - Handles user authentication";
    }
}