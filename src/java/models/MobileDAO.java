package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 * Mobile Data Access Object
 *
 * @author MindyCoding by Tran
 */
public class MobileDAO {

    private static final String GET_ALL_MOBILES = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity, notSale FROM Mobiles";
    private static final String SEARCH_MOBILE = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity, notSale FROM Mobiles WHERE mobileId LIKE ? OR mobileName LIKE ?";
    private static final String GET_MOBILE_BY_ID = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity, notSale FROM Mobiles WHERE mobileId = ?";
    private static final String INSERT_MOBILE = "INSERT INTO Mobiles(mobileId, description, price, mobileName, yearOfProduction, quantity, notSale) VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_MOBILE = "UPDATE Mobiles SET description=?, price=?, quantity=?, notSale=? WHERE mobileId=?";
    private static final String DELETE_MOBILE = "DELETE FROM Mobiles WHERE mobileId=?";

    /**
     * Get all mobiles from database
     *
     * @return List of all mobiles
     * @throws SQLException
     */
    public List<MobileDTO> getAllMobiles() throws SQLException {
        List<MobileDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL_MOBILES);
                rs = ptm.executeQuery();

                while (rs.next()) {
                    String mobileId = rs.getString("mobileId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    boolean notSale = rs.getBoolean("notSale");

                    list.add(new MobileDTO(mobileId, description, price, mobileName,
                            yearOfProduction, quantity, notSale));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    /**
     * Search mobiles by ID or Name (case-insensitive)
     *
     * @param searchValue Search keyword
     * @return List of mobiles matching the search criteria
     * @throws SQLException
     */
    public List<MobileDTO> searchMobile(String searchValue) throws SQLException {
        List<MobileDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_MOBILE);

                // Add wildcards for LIKE search
                String searchPattern = "%" + searchValue + "%";
                ptm.setString(1, searchPattern);  // Search by mobileId
                ptm.setString(2, searchPattern);  // Search by mobileName

                rs = ptm.executeQuery();

                while (rs.next()) {
                    String mobileId = rs.getString("mobileId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    boolean notSale = rs.getBoolean("notSale");

                    list.add(new MobileDTO(mobileId, description, price, mobileName,
                            yearOfProduction, quantity, notSale));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    /**
     * Get mobile details by ID
     *
     * @param mobileId Mobile ID
     * @return MobileDTO if found, null otherwise
     * @throws SQLException
     */
    public MobileDTO getMobileById(String mobileId) throws SQLException {
        MobileDTO mobile = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_MOBILE_BY_ID);
                ptm.setString(1, mobileId);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    String id = rs.getString("mobileId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    boolean notSale = rs.getBoolean("notSale");

                    mobile = new MobileDTO(id, description, price, mobileName,
                            yearOfProduction, quantity, notSale);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return mobile;
    }

    public boolean deleteMobile(String mobileId) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_MOBILE);

                // Set mobileId parameter
                ptm.setString(1, mobileId);

                // Execute delete
                int rows = ptm.executeUpdate();
                if (rows > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    /**
     * Update mobile information (inline update) Only update: price,
     * description, quantity, notSale
     *
     * @param mobileId Mobile ID (cannot be changed)
     * @param description New description
     * @param price New price
     * @param quantity New quantity
     * @param notSale New sale status
     * @return true if updated successfully
     * @throws SQLException
     */
    public boolean updateMobile(String mobileId, String description, float price,
            int quantity, boolean notSale) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_MOBILE);

                // Set parameters (thứ tự theo SQL)
                ptm.setString(1, description);
                ptm.setFloat(2, price);
                ptm.setInt(3, quantity);
                ptm.setBoolean(4, notSale);
                ptm.setString(5, mobileId);  // WHERE clause

                int rows = ptm.executeUpdate();
                if (rows > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    /**
     * Add new mobile to database
     *
     * @return true if added successfully
     * @throws SQLException
     */
    public boolean addMobile(String mobileId, String description, float price,
            String mobileName, int yearOfProduction,
            int quantity, boolean notSale) throws SQLException {
        boolean result = false;
        Connection conn = null;
        PreparedStatement ptm = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_MOBILE);

                ptm.setString(1, mobileId);
                ptm.setString(2, description);
                ptm.setFloat(3, price);
                ptm.setString(4, mobileName);
                ptm.setInt(5, yearOfProduction);
                ptm.setInt(6, quantity);
                ptm.setBoolean(7, notSale);

                int rows = ptm.executeUpdate();
                if (rows > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    /**
     * Search mobiles by price range (for User) Only show mobiles with notSale =
     * 0 (available for sale)
     *
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of mobiles in price range
     * @throws SQLException
     */
    public List<MobileDTO> searchMobileByPriceRange(float minPrice, float maxPrice) throws SQLException {
        List<MobileDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        String sql = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity, notSale "
                + "FROM Mobiles WHERE price >= ? AND price <= ? AND notSale = 0";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setFloat(1, minPrice);
                ptm.setFloat(2, maxPrice);

                rs = ptm.executeQuery();

                while (rs.next()) {
                    String mobileId = rs.getString("mobileId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    boolean notSale = rs.getBoolean("notSale");

                    list.add(new MobileDTO(mobileId, description, price, mobileName,
                            yearOfProduction, quantity, notSale));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }

    /**
     * Get all available mobiles (notSale = 0) for User
     *
     * @return List of available mobiles
     * @throws SQLException
     */
    public List<MobileDTO> getAllAvailableMobiles() throws SQLException {
        List<MobileDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        String sql = "SELECT mobileId, description, price, mobileName, yearOfProduction, quantity, notSale "
                + "FROM Mobiles WHERE notSale = 0 ORDER BY mobileName";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();

                while (rs.next()) {
                    String mobileId = rs.getString("mobileId");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    int quantity = rs.getInt("quantity");
                    boolean notSale = rs.getBoolean("notSale");

                    list.add(new MobileDTO(mobileId, description, price, mobileName,
                            yearOfProduction, quantity, notSale));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return list;
    }
}
