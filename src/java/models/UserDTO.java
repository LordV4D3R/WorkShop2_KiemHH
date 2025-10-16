package models;

/**
 * User Data Transfer Object
 * @author MindyCoding by TranAn
 */
public class UserDTO {
    private String userId;
    private int password;
    private String fullName;
    private int role;  // 0: user, 1: staff

    // Default Constructor
    public UserDTO() {
    }

    // Constructor with all fields
    public UserDTO(String userId, int password, String fullName, int role) {
        this.userId = userId;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Constructor without password (for security)
    public UserDTO(String userId, String fullName, int role) {
        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public int getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getRole() {
        return role;
    }

    // Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // Helper methods
    public boolean isStaff() {
        return this.role == 1;
    }

    public boolean isUser() {
        return this.role == 0;
    }

    public String getRoleName() {
        return this.role == 1 ? "Staff" : "User";
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                '}';
    }
}