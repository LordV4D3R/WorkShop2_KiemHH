package controllers;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserDTO;

/**
 * Update Cart Controller (User only)
 * Update quantity of item in cart
 * @author MindyCoding by Tran
 */
public class UpdateCartController extends HttpServlet {

    private static final String VIEW_CART_PAGE = "ViewCartController";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = VIEW_CART_PAGE;

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

            // Get parameters
            String mobileId = request.getParameter("mobileId");
            String quantityStr = request.getParameter("quantity");

            // Get cart from session
            Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("CART");

            if (cart != null && mobileId != null && quantityStr != null) {
                int newQuantity = Integer.parseInt(quantityStr);

                if (newQuantity > 0) {
                    // Update quantity
                    cart.put(mobileId, newQuantity);
                    session.setAttribute("CART", cart);
                    request.setAttribute("SUCCESS_MESSAGE", "Cart updated successfully!");
                } else {
                    // If quantity <= 0, remove item
                    cart.remove(mobileId);
                    session.setAttribute("CART", cart);
                    request.setAttribute("SUCCESS_MESSAGE", "Item removed from cart!");
                }

                System.out.println("User " + loginUser.getUserId() + " updated cart: " + mobileId + 
                                 " -> Quantity: " + newQuantity);
            }

        } catch (NumberFormatException e) {
            log("Invalid quantity format: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Invalid quantity!");
        } catch (Exception e) {
            log("Error at UpdateCartController: " + e.toString());
            e.printStackTrace();
            request.setAttribute("ERROR_MESSAGE", "Error updating cart!");
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
        return "Update Cart Controller - User only";
    }
}