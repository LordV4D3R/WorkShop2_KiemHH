package models;

/**
 * Mobile Data Transfer Object
 * @author MindyCoding by TranAn
 */
public class MobileDTO {
    private String mobileId;
    private String description;
    private float price;
    private String mobileName;
    private int yearOfProduction;
    private int quantity;
    private boolean notSale;  // true = not for sale, false = available

    // Default Constructor
    public MobileDTO() {
    }

    // Constructor with all fields
    public MobileDTO(String mobileId, String description, float price, 
                     String mobileName, int yearOfProduction, int quantity, boolean notSale) {
        this.mobileId = mobileId;
        this.description = description;
        this.price = price;
        this.mobileName = mobileName;
        this.yearOfProduction = yearOfProduction;
        this.quantity = quantity;
        this.notSale = notSale;
    }

    // Constructor without notSale (default to false)
    public MobileDTO(String mobileId, String description, float price, 
                     String mobileName, int yearOfProduction, int quantity) {
        this.mobileId = mobileId;
        this.description = description;
        this.price = price;
        this.mobileName = mobileName;
        this.yearOfProduction = yearOfProduction;
        this.quantity = quantity;
        this.notSale = false;
    }

    // Getters
    public String getMobileId() {
        return mobileId;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getMobileName() {
        return mobileName;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isNotSale() {
        return notSale;
    }

    // Setters
    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNotSale(boolean notSale) {
        this.notSale = notSale;
    }

    // Helper methods
    public boolean isAvailableForSale() {
        return !notSale && quantity > 0;
    }

    public String getAvailabilityStatus() {
        if (notSale) {
            return "Not for Sale";
        } else if (quantity <= 0) {
            return "Out of Stock";
        } else if (quantity < 10) {
            return "Low Stock";
        } else {
            return "In Stock";
        }
    }

    public float getTotalPrice(int purchaseQuantity) {
        return this.price * purchaseQuantity;
    }

    @Override
    public String toString() {
        return "MobileDTO{" +
                "mobileId='" + mobileId + '\'' +
                ", mobileName='" + mobileName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", notSale=" + notSale +
                '}';
    }
}