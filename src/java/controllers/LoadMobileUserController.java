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
 * Load Mobile for User Controller
 * Display all available mobiles (notSale = 0) for User
 * @author MindyCoding by Tran
 */
public class LoadMobileUserController extends HttpServlet {

    private static final String MOBILE_LIST_USER_PAGE = "mobile_list_user.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MOBILE_LIST_USER_PAGE;

        try {
            // Check authentication
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser.getRole() != 0) {  // User role = 0
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // Load all mobiles with notSale = 0
            MobileDAO mobileDAO = new MobileDAO();
            List<MobileDTO> listMobile = mobileDAO.getAllAvailableMobiles();

            // Set attributes
            request.setAttribute("LIST_MOBILE", listMobile);
            
            System.out.println("User " + loginUser.getUserId() + " loaded all products: " + listMobile.size() + " items");

        } catch (Exception e) {
            log("Error at LoadMobileUserController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "Error loading products!");
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
        return "Load Mobile User Controller";
    }
}