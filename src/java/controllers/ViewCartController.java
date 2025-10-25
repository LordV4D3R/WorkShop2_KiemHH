package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.MobileDAO;
import models.MobileDTO;
import models.UserDTO;

/**
 * View Cart Controller (User only)
 * Display shopping cart with mobile details
 * @author MindyCoding by Tran
 */
public class ViewCartController extends HttpServlet {

    private static final String CART_PAGE = "view_cart.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CART_PAGE;

        try {
            // Check authentication
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser.getRole() != 0) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // Get cart from session
            Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("CART");

            if (cart != null && !cart.isEmpty()) {
                // Get mobile details for each item in cart
                Map<String, MobileDTO> cartDetails = new HashMap<>();
                MobileDAO mobileDAO = new MobileDAO();
                float totalAmount = 0;

                for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                    String mobileId = entry.getKey();
                    int quantity = entry.getValue();

                    MobileDTO mobile = mobileDAO.getMobileById(mobileId);
                    if (mobile != null) {
                        cartDetails.put(mobileId, mobile);
                        totalAmount += mobile.getPrice() * quantity;
                    }
                }

                request.setAttribute("CART", cart);
                request.setAttribute("CART_DETAILS", cartDetails);
                request.setAttribute("TOTAL_AMOUNT", totalAmount);
            }

        } catch (Exception e) {
            log("Error at ViewCartController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "Error loading cart!");
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
        return "View Cart Controller - User only";
    }
}