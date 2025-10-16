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
 * Load Mobile Controller (Staff only)
 * Load all mobiles for management
 * @author MindyCoding by Tran
 */
public class LoadMobileController extends HttpServlet {

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

            // Load all mobiles
            MobileDAO mobileDAO = new MobileDAO();
            List<MobileDTO> listMobile = mobileDAO.getAllMobiles();

            // Set attributes
            request.setAttribute("LIST_MOBILE", listMobile);
            
            // Log for debugging
            System.out.println("Loaded " + listMobile.size() + " mobiles for staff: " + loginUser.getUserId());

        } catch (Exception e) {
            log("Error at LoadMobileController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "Error loading mobiles. Please try again.");
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
        return "Load Mobile Controller - Staff only";
    }
}