package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserDTO;

/**
 * Add to Cart Controller (User only)
 * Add mobile to cart with quantity +1
 * @author MindyCoding by Tran
 */
public class AddToCartController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Get return URL (from which page user clicked Add to Cart)
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isEmpty()) {
            returnUrl = "LoadMobileUserController";  // Default
        }

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

            // Get mobileId
            String mobileId = request.getParameter("mobileId");

            if (mobileId == null || mobileId.trim().isEmpty()) {
                request.setAttribute("ERROR_MESSAGE", "Invalid mobile!");
            } else {
                // Get or create cart from session
                @SuppressWarnings("unchecked")
                Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("CART");
                
                if (cart == null) {
                    cart = new HashMap<>();
                }

                // Add or increase quantity (+1)
                if (cart.containsKey(mobileId)) {
                    int currentQuantity = cart.get(mobileId);
                    cart.put(mobileId, currentQuantity + 1);
                } else {
                    cart.put(mobileId, 1);
                }

                // Save cart to session
                session.setAttribute("CART", cart);

                request.setAttribute("SUCCESS_MESSAGE", 
                    "Added to cart! (Total items: " + cart.size() + ")");

                System.out.println("User " + loginUser.getUserId() + " added: " + mobileId + 
                                 " | Cart size: " + cart.size());
            }

        } catch (Exception e) {
            log("Error at AddToCartController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "Error adding to cart!");
        } finally {
            request.getRequestDispatcher(returnUrl).forward(request, response);
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
        return "Add to Cart Controller - User only";
    }
}