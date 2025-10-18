package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.MobileDAO;
import models.MobileDTO;
import models.UserDTO;

/**
 * Delete Mobile Controller (Staff only)
 * Handles mobile deletion from database
 * @author MindyCoding by Tran
 */
public class DeleteMobileController extends HttpServlet {

    private static final String LOAD_MOBILE_PAGE = "LoadMobileController";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOAD_MOBILE_PAGE;

        try {
            // Check if user is logged in and is Staff
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            
            // Check if user is Staff (role = 1)
            if (loginUser.getRole() != 1) {
                request.setAttribute("ERROR_MESSAGE", "Access denied! Staff only.");
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // Get mobileId from request
            String mobileId = request.getParameter("mobileId");

            // Validate mobileId
            if (mobileId == null || mobileId.trim().isEmpty()) {
                request.setAttribute("ERROR_MESSAGE", "Mobile ID is required!");
                url = LOAD_MOBILE_PAGE;
            } else {
                // Trim whitespace
                mobileId = mobileId.trim();

                // Get mobile details first (for confirmation message)
                MobileDAO mobileDAO = new MobileDAO();
                MobileDTO mobile = mobileDAO.getMobileById(mobileId);

                if (mobile == null) {
                    // Mobile not found
                    request.setAttribute("ERROR_MESSAGE", 
                        "Mobile ID '" + mobileId + "' not found!");
                    url = LOAD_MOBILE_PAGE;
                } else {
                    // Delete mobile
                    boolean result = mobileDAO.deleteMobile(mobileId);

                    if (result) {
                        // Delete successful
                        request.setAttribute("SUCCESS_MESSAGE", 
                            "Mobile '" + mobile.getMobileName() + "' (ID: " + mobileId + ") deleted successfully!");
                        
                        // Log for debugging
                        System.out.println("Mobile deleted by: " + loginUser.getUserId());
                        System.out.println("Deleted mobile ID: " + mobileId);
                        System.out.println("Deleted mobile name: " + mobile.getMobileName());
                    } else {
                        // Delete failed
                        request.setAttribute("ERROR_MESSAGE", 
                            "Failed to delete mobile '" + mobile.getMobileName() + "'. Please try again.");
                    }
                    
                    url = LOAD_MOBILE_PAGE;
                }
            }

        } catch (Exception e) {
            log("Error at DeleteMobileController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "System error occurred. Please try again.");
            url = LOAD_MOBILE_PAGE;
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
        return "Delete Mobile Controller - Staff only";
    }
}