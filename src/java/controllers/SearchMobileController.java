package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.MobileDAO;
import models.MobileDTO;
import models.UserDTO;

/**
 * Search Mobile Controller (Staff only)
 * Search mobiles by ID or Name (case-insensitive)
 * @author MindyCoding by Tran
 */
public class SearchMobileController extends HttpServlet {

    private static final String STAFF_MOBILE_PAGE = "mobile_management.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = STAFF_MOBILE_PAGE;

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

            // Get search keyword from request
            String searchValue = request.getParameter("searchValue");

            // Validate search value
            if (searchValue == null || searchValue.trim().isEmpty()) {
                request.setAttribute("ERROR_MESSAGE", "Please enter a search keyword!");
                url = STAFF_MOBILE_PAGE;
            } else {
                // Trim whitespace
                searchValue = searchValue.trim();

                // Search mobiles
                MobileDAO mobileDAO = new MobileDAO();
                List<MobileDTO> listMobile = mobileDAO.searchMobile(searchValue);

                // Set results and search value to display
                request.setAttribute("LIST_MOBILE", listMobile);
                request.setAttribute("SEARCH_VALUE", searchValue);

                // Set message based on results
                if (listMobile.isEmpty()) {
                    request.setAttribute("INFO_MESSAGE", 
                        "No mobiles found matching '" + searchValue + "'");
                } else {
                    request.setAttribute("SUCCESS_MESSAGE", 
                        "Found " + listMobile.size() + " mobile(s) matching '" + searchValue + "'");
                }

                // Log for debugging
                System.out.println("Search performed by: " + loginUser.getUserId());
                System.out.println("Search keyword: " + searchValue);
                System.out.println("Results found: " + listMobile.size());
            }

        } catch (Exception e) {
            log("Error at SearchMobileController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "Error occurred while searching. Please try again.");
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
        return "Search Mobile Controller - Staff only";
    }
}