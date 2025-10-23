package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.MobileDAO;
import models.UserDTO;

/**
 * Add Mobile Controller (Staff only)
 * @author MindyCoding by Tran
 */
public class AddMobileController extends HttpServlet {

    private static final String ADD_MOBILE_PAGE = "add_mobile.jsp";
    private static final String LOAD_MOBILE_PAGE = "LoadMobileController";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ADD_MOBILE_PAGE;

        try {
            // Check authentication
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser.getRole() != 1) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // Get parameters
            String mobileId = request.getParameter("mobileId");
            String mobileName = request.getParameter("mobileName");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String yearStr = request.getParameter("yearOfProduction");
            String quantityStr = request.getParameter("quantity");
            String notSaleStr = request.getParameter("notSale");

            // Parse data
            float price = Float.parseFloat(priceStr);
            int year = Integer.parseInt(yearStr);
            int quantity = Integer.parseInt(quantityStr);
            boolean notSale = Boolean.parseBoolean(notSaleStr);

            // Add mobile
            MobileDAO mobileDAO = new MobileDAO();
            boolean result = mobileDAO.addMobile(mobileId, description, price, 
                                                mobileName, year, quantity, notSale);

            if (result) {
                request.setAttribute("SUCCESS_MESSAGE", 
                    "Mobile '" + mobileName + "' added successfully!");
                System.out.println("Added by: " + loginUser.getUserId() + " - Mobile: " + mobileId);
                url = LOAD_MOBILE_PAGE;
            } else {
                request.setAttribute("ERROR_MESSAGE", "Failed to add mobile!");
                url = ADD_MOBILE_PAGE;
            }

        } catch (NumberFormatException e) {
            log("Invalid number format: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Invalid number format!");
            url = ADD_MOBILE_PAGE;
        } catch (Exception e) {
            log("Error at AddMobileController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "System error. Mobile ID may already exist!");
            url = ADD_MOBILE_PAGE;
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
        return "Add Mobile Controller - Staff only";
    }
}