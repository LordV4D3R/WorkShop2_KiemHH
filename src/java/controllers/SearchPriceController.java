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
 * Search Price Controller (User only)
 * Search mobiles by price range
 * @author MindyCoding by Tran
 */
public class SearchPriceController extends HttpServlet {

    private static final String SEARCH_RESULT_PAGE = "search_price_result.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SEARCH_RESULT_PAGE;

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

            // Get parameters
            String minPriceStr = request.getParameter("minPrice");
            String maxPriceStr = request.getParameter("maxPrice");

            // Parse prices
            float minPrice = Float.parseFloat(minPriceStr);
            float maxPrice = Float.parseFloat(maxPriceStr);

            // Search mobiles
            MobileDAO mobileDAO = new MobileDAO();
            List<MobileDTO> listMobile = mobileDAO.searchMobileByPriceRange(minPrice, maxPrice);

            // Set attributes
            request.setAttribute("LIST_MOBILE", listMobile);
            request.setAttribute("MIN_PRICE", minPrice);
            request.setAttribute("MAX_PRICE", maxPrice);

            if (listMobile.isEmpty()) {
                request.setAttribute("INFO_MESSAGE", 
                    "No mobiles found in price range $" + minPrice + " - $" + maxPrice);
            } else {
                request.setAttribute("SUCCESS_MESSAGE", 
                    "Found " + listMobile.size() + " mobile(s) in range $" + minPrice + " - $" + maxPrice);
            }

            System.out.println("User " + loginUser.getUserId() + " searched: $" + minPrice + " - $" + maxPrice);

        } catch (NumberFormatException e) {
            log("Invalid price format: " + e.toString());
            request.setAttribute("ERROR_MESSAGE", "Invalid price format!");
        } catch (Exception e) {
            log("Error at SearchPriceController: " + e.toString());
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
        return "Search Price Controller - User only";
    }
}