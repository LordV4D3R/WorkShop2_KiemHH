package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBUtils;

/**
 * User Data Access Object
 * Handles all database operations related to Users
 * @author MindyCoding by TranAn
 */
public class UserDAO {
    
    private static final String LOGIN = "SELECT userId, password, fullName, role FROM Users WHERE userId = ? AND password = ?";
    private static final String CHECK_USER_EXISTS = "SELECT userId FROM Users WHERE userId = ?";
    private static final String GET_USER_BY_ID = "SELECT userId, fullName, role FROM Users WHERE userId = ?";

    /**
     * Check login credentials
     * @param userId User ID
     * @param password Password
     * @return UserDTO if valid credentials, null otherwise
     * @throws SQLException
     */
    public UserDTO checkLogin(String userId, int password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, userId);
                ptm.setInt(2, password);
                
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    String id = rs.getString("userId");
                    String fullName = rs.getString("fullName");
                    int role = rs.getInt("role");
                    
                    // Don't include password in the returned object for security
                    user = new UserDTO(id, fullName, role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        
        return user;
    }

    /**
     * Check if user exists in database
     * @param userId User ID to check
     * @return true if user exists, false otherwise
     * @throws SQLException
     */
    public boolean checkUserExists(String userId) throws SQLException {
        boolean exists = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_USER_EXISTS);
                ptm.setString(1, userId);
                
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    exists = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        
        return exists;
    }

    /**
     * Get user details by ID (without password)
     * @param userId User ID
     * @return UserDTO if found, null otherwise
     * @throws SQLException
     */
    public UserDTO getUserById(String userId) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_USER_BY_ID);
                ptm.setString(1, userId);
                
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    String id = rs.getString("userId");
                    String fullName = rs.getString("fullName");
                    int role = rs.getInt("role");
                    
                    user = new UserDTO(id, fullName, role);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        
        return user;
    }
}