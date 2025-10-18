package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main Controller - Front Controller Pattern
 * @author MindyCoding by Tran
 */
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "LoginController";
    private static final String LOGOUT_PAGE = "LogoutController";
    private static final String LOAD_MOBILE_PAGE = "LoadMobileController";
    private static final String SEARCH_MOBILE_PAGE = "SearchMobileController";
    private static final String DELETE_MOBILE_PAGE = "DeleteMobileController";
    private static final String UPDATE_MOBILE_PAGE = "UpdateMobileController";
    private static final String ADD_MOBILE_PAGE = "AddMobileController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        
        try {
            String action = request.getParameter("action");
            
            if (action == null || action.isEmpty()) {
                url = LOGIN_PAGE;
            } else if (action.equals("Login")) {
                url = LOGIN_PAGE;
            } else if (action.equals("Logout")) {
                url = LOGOUT_PAGE;
            } else if (action.equals("LoadMobile")) {
                url = LOAD_MOBILE_PAGE;
            } else if (action.equals("SearchMobile")) {
                url = SEARCH_MOBILE_PAGE;
            } else if (action.equals("DeleteMobile")) {
                url = DELETE_MOBILE_PAGE;
            } else if (action.equals("UpdateMobile")) {
                url = UPDATE_MOBILE_PAGE;
            } else if (action.equals("AddMobile")) {
                url = ADD_MOBILE_PAGE;
            }
            
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
            e.printStackTrace();
            url = LOGIN_PAGE;
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
        return "Main Controller - Front Controller Pattern";
    }
}