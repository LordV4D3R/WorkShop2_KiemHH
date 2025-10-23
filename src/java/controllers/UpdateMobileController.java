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
 * Update Mobile Controller (Staff only)
 * Update price, description, quantity, notSale inline
 * @author MindyCoding by Tran
 */
public class UpdateMobileController extends HttpServlet {

    private static final String LOAD_MOBILE_PAGE = "LoadMobileController";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOAD_MOBILE_PAGE;

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
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String quantityStr = request.getParameter("quantity");
            String notSaleStr = request.getParameter("notSale");

            // Parse data
            float price = Float.parseFloat(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            boolean notSale = Boolean.parseBoolean(notSaleStr);

            // Update mobile
            MobileDAO mobileDAO = new MobileDAO();
            boolean result = mobileDAO.updateMobile(mobileId, description, price, quantity, notSale);

            if (result) {
                request.setAttribute("SUCCESS_MESSAGE", 
                    "Mobile '" + mobileId + "' updated successfully!");
                System.out.println("Updated by: " + loginUser.getUserId() + " - Mobile: " + mobileId);
            } else {
                request.setAttribute("ERROR_MESSAGE", "Failed to update mobile!");
            }

        } catch (NumberFormatException e) {
            log("Invalid number format: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Invalid price or quantity format!");
        } catch (Exception e) {
            log("Error at UpdateMobileController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "System error occurred!");
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
        return "Update Mobile Controller - Staff only";
    }
}