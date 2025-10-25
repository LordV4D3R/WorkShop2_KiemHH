# 📱 Workshop 2 - MobileShop Web Application

A Java Web Application with authentication, role-based access control, and shopping cart functionality demonstrating advanced CRUD operations using JSP, Servlet, and SQL Server.

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Tomcat](https://img.shields.io/badge/Tomcat-9.0.108-yellow.svg)](https://tomcat.apache.org/)
[![SQL Server](https://img.shields.io/badge/SQL%20Server-2019-red.svg)](https://www.microsoft.com/sql-server/)

---

## 📄 Assignment Document

**⚠️ READ THE ASSIGNMENT FIRST**

The complete workshop requirements, database schema, and functional specifications are in:

📁 **[`docs/Workshop_02_MobileShop.pdf`](docs/Workshop_02_MobileShop.pdf)**

This document contains:
- ✅ Detailed requirements and objectives
- ✅ Database schema (MyShop database, Mobiles & Users tables)
- ✅ Complete functional requirements for Staff and User roles
- ✅ Feature specifications and grading criteria

**Start by reading the PDF to understand what you need to build.**

---

## 🎯 What This Repository Contains

This is a **sample solution** for Workshop 2 assignment demonstrating:

### **Authentication System:**
- ✅ Login with username/password
- ✅ Session management
- ✅ Role-based access control (Staff vs User)

### **Staff Features (Role = 1):**
- ✅ Search mobiles by ID or Name (case-insensitive)
- ✅ Delete mobile from database
- ✅ Update mobile (price, description, quantity, notSale) - inline edit
- ✅ Add new mobile to database

### **User Features (Role = 0):**
- ✅ Search mobiles by price range [min, max]
- ✅ View all available products (notSale = 0)
- ✅ Add to cart (quantity +1 each click)
- ✅ View shopping cart with total amount
- ✅ Update cart quantity
- ✅ Remove items from cart

### **Architecture:**
- ✅ MVC2 Pattern (Model-View-Controller)
- ✅ Front Controller Pattern (MainController)
- ✅ DAO Pattern for database operations
- ✅ DTO Pattern for data transfer
- ✅ Session-based shopping cart

**Purpose:** Educational reference for students learning Java web development with authentication and e-commerce features.

---

## 🛠 Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 8 | Backend language |
| Apache Tomcat | 9.0.108 | Servlet container |
| SQL Server | 2019 | Database |
| JSP & Servlet | 3.1 | Web framework |
| JSTL & EL | 1.2 | Tag library & Expression Language |
| MSSQL JDBC Driver | 9.4.1 | DB connectivity |
| JavaBeans | - | Data encapsulation |

---

## 📦 Prerequisites

1. **JDK 8** - [Download](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
2. **Apache Tomcat 9.0.108** - [Download](https://tomcat.apache.org/download-90.cgi)
3. **SQL Server 2019** - [Download](https://www.microsoft.com/sql-server/sql-server-downloads)
4. **SSMS** (optional but recommended) - [Download](https://docs.microsoft.com/sql/ssms/download-sql-server-management-studio-ssms)
5. **NetBeans IDE 13** (recommended) - [Download](https://netbeans.apache.org/download/)

---

## 🚀 Installation & Setup

### Step 1: Clone Repository
```bash
git clone https://github.com/your-username/workshop2-mobileshop.git
cd workshop2-mobileshop
```

### Step 2: Setup Database

#### Option A: Use SQL Script (Recommended)

Execute the provided SQL script:
```bash
database/MyShop-Create.sql
```

#### Option B: Manual Setup
```sql
-- 1. Create Database
CREATE DATABASE MyShop;
GO

USE MyShop;
GO

-- 2. Create Mobiles Table
CREATE TABLE Mobiles (
    mobileId VARCHAR(10) PRIMARY KEY,
    description VARCHAR(250) NOT NULL,
    price FLOAT NOT NULL,
    mobileName VARCHAR(20) NOT NULL,
    yearOfProduction INT NOT NULL,
    quantity INT NOT NULL,
    notSale BIT DEFAULT 0  -- 0: sale, 1: not for sale
);
GO

-- 3. Create Users Table
CREATE TABLE Users (
    userId VARCHAR(20) PRIMARY KEY,
    password INT NOT NULL,
    fullName VARCHAR(50) NOT NULL,
    role INT NOT NULL  -- 0: user, 1: staff
);
GO

-- 4. Insert Sample Users
INSERT INTO Users (userId, password, fullName, role) VALUES
('admin', 1, 'Administrator', 1),      -- Staff
('staff01', 1, 'Staff Member', 1),     -- Staff
('user01', 1, 'John Doe', 0),          -- User
('user02', 1, 'Jane Smith', 0);        -- User
GO

-- 5. Insert Sample Mobiles
INSERT INTO Mobiles VALUES
('IP13PM', 'iPhone 13 Pro Max 256GB - Pacific Blue', 1299.99, 'iPhone 13 Pro Max', 2021, 50, 0),
('SS21U', 'Samsung Galaxy S21 Ultra 5G 128GB', 1199.99, 'Galaxy S21 Ultra', 2021, 30, 0),
('IP12', 'iPhone 12 64GB - White', 799.99, 'iPhone 12', 2020, 100, 0),
('XIAO11', 'Xiaomi Mi 11 128GB - Midnight Gray', 749.99, 'Mi 11', 2021, 45, 0),
('ONE9P', 'OnePlus 9 Pro 256GB - Morning Mist', 969.99, 'OnePlus 9 Pro', 2021, 25, 0);
GO
```

### Step 3: Configure Database Connection

Edit `src/java/utils/DBUtils.java`:
```java
public class DBUtils {
    private static final String DB_NAME = "MyShop";
    private static final String DB_USER_NAME = "SA";              // ← Change this
    private static final String DB_PASSWORD = "your_password";    // ← Change this
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        return conn;
    }
}
```

**Configuration Notes:**

For **SQL Server Express:**
```java
String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=" + DB_NAME;
```

For **Windows Authentication:**
```java
String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME 
           + ";integratedSecurity=true;";
conn = DriverManager.getConnection(url); // No username/password
```

### Step 4: Import to NetBeans

1. Open NetBeans IDE
2. `File` → `Open Project`
3. Select the cloned folder
4. Add required JARs to Libraries:
   - `jstl-1.2.jar`
   - `mssql-jdbc-9.4.1.jre8.jar`

### Step 5: Configure Tomcat

1. `Tools` → `Servers` → `Add Server`
2. Select Apache Tomcat
3. Browse to Tomcat installation folder
4. Finish

---

## ▶️ How to Run

1. **Start SQL Server**
2. **Clean and Build** project
3. **Run** the project (F6)
4. Browser opens at:
```
http://localhost:8080/WorkShop2/
```

### Test Accounts

**Staff Account:**
- Username: `admin`
- Password: `1`

**User Account:**
- Username: `user01`
- Password: `1`

---

## 📁 Project Structure
```
src/java/
├── controllers/                    # 🎮 Servlets (Controller Layer)
│   ├── MainController.java             # Front controller - routes requests
│   ├── LoginController.java            # Handle login authentication
│   ├── LogoutController.java           # Handle logout
│   │
│   ├── Staff Controllers:
│   ├── LoadMobileController.java       # View all mobiles (Staff)
│   ├── SearchMobileController.java     # Search by ID/name (Staff)
│   ├── DeleteMobileController.java     # Delete mobile (Staff)
│   ├── UpdateMobileController.java     # Update mobile inline (Staff)
│   ├── AddMobileController.java        # Insert new mobile (Staff)
│   │
│   └── User Controllers:
│       ├── SearchPriceController.java  # Search by price range (User)
│       ├── LoadMobileUserController.java   # View all products (User)
│       ├── AddToCartController.java    # Add mobile to cart
│       ├── ViewCartController.java     # Display shopping cart
│       ├── UpdateCartController.java   # Update cart quantities
│       └── RemoveFromCartController.java   # Remove from cart
│
├── models/                         # 📊 Business Logic (Model Layer)
│   ├── MobileDTO.java                  # Mobile entity
│   ├── MobileDAO.java                  # Mobile database operations
│   ├── UserDTO.java                    # User entity
│   └── UserDAO.java                    # User database operations
│
└── utils/                          # 🔧 Utilities
    └── DBUtils.java                    # Database connection

web/
├── login.jsp                       # Login page
├── user_home.jsp                   # User homepage
├── search_price.jsp                # Search by price form (User)
├── search_price_result.jsp         # Search results (User)
├── mobile_list_user.jsp            # All products (User)
├── view_cart.jsp                   # Shopping cart (User)
├── mobile_management.jsp           # Mobile list with inline edit (Staff)
├── add_mobile.jsp                  # Add mobile form (Staff)
└── WEB-INF/
    ├── web.xml                     # Deployment descriptor
    └── lib/                        # Required JARs
```

---

## 🔄 Application Flow

### Authentication Flow
```
User → login.jsp → LoginController → UserDAO.checkLogin()
                         ↓
                   Session created
                         ↓
            ┌────────────┴────────────┐
            ↓                         ↓
      Staff (role=1)            User (role=0)
            ↓                         ↓
   LoadMobileController        user_home.jsp
   (mobile_management.jsp)
```

### Staff Flow: Update Mobile (Inline)
```
Staff → mobile_management.jsp → Edit fields inline
            ↓
    Click "Update" button
            ↓
    UpdateMobileController → MobileDAO.updateMobile()
            ↓
    Reload mobile_management.jsp with success message
```

### User Flow: Shopping Cart
```
User → Search by price → Search results
            ↓
    Click "Add to Cart"
            ↓
    AddToCartController → Add to session cart
            ↓
    ViewCartController → Display cart
            ↓
    User can: Update quantity OR Remove item
```

---

## 🔑 Key Features Implementation

### 1. Session-Based Shopping Cart
```java
// Cart structure: Map<mobileId, quantity>
Map<String, Integer> cart = new HashMap<>();

// Add to cart (+1)
if (cart.containsKey(mobileId)) {
    cart.put(mobileId, cart.get(mobileId) + 1);
} else {
    cart.put(mobileId, 1);
}

// Save to session
session.setAttribute("CART", cart);
```

### 2. Role-Based Access Control
```jsp
<!-- Staff only pages -->
<c:if test="${LOGIN_USER.role != 1}">
    <c:redirect url="login.jsp"/>
</c:if>

<!-- User only pages -->
<c:if test="${LOGIN_USER.role != 0}">
    <c:redirect url="login.jsp"/>
</c:if>
```

### 3. Inline Edit (Staff)

Each table row is a form:
```jsp
<tr>
    <form action="UpdateMobileController" method="POST">
        <td><input type="text" name="description" value="${mobile.description}"/></td>
        <td><input type="number" name="price" value="${mobile.price}"/></td>
        <td><input type="submit" value="Update"/></td>
    </form>
</tr>
```

---

## 📊 Database Schema

### Mobiles Table
```sql
mobileId VARCHAR(10) PRIMARY KEY
description VARCHAR(250) NOT NULL
price FLOAT NOT NULL
mobileName VARCHAR(20) NOT NULL
yearOfProduction INT NOT NULL
quantity INT NOT NULL
notSale BIT DEFAULT 0  -- 0: available, 1: not for sale
```

### Users Table
```sql
userId VARCHAR(20) PRIMARY KEY
password INT NOT NULL           -- Plain text (demo only)
fullName VARCHAR(50) NOT NULL
role INT NOT NULL               -- 0: user, 1: staff
```

**Security Note:** In production, passwords should be hashed (BCrypt, SHA-256, etc.)

---

## 🔧 Common Issues & Solutions

### ❌ Login Failed

**Solution:**
```sql
-- Verify users exist
SELECT * FROM Users;

-- Check credentials
SELECT * FROM Users WHERE userId = 'admin' AND password = 1;
```

### ❌ Cart Not Persisting

**Solution:**
```java
// Always use session.setAttribute after modifying cart
session.setAttribute("CART", cart);
```

### ❌ Access Denied for User Features

**Solution:**
- Login with User account: `user01` / `1`
- Staff cannot access User features and vice versa

### ❌ Mobile Not Found After Delete

**Solution:**
- This is expected behavior
- Reload mobile list: Click "Show All"

### ❌ Cannot Update Mobile

**Solution:**
```
1. Check all fields are filled (required)
2. Price and quantity must be valid numbers
3. Check database connection in DBUtils.java
```

---

## ⚠️ IMPORTANT: Academic Integrity

### 🚨 READ THIS BEFORE USING

This code is **FOR EDUCATIONAL REFERENCE ONLY**.

### ✅ ALLOWED:
- Study authentication implementation
- Learn shopping cart logic
- Understand role-based access control
- Reference when stuck on specific features
- Analyze MVC2 architecture

### ❌ PROHIBITED:
- Copy-paste for assignments
- Submit as your own work
- Share with classmates as solution
- Use without understanding

### 📚 How to Learn Properly:

1. **Read Workshop 2 PDF** (`docs/Workshop_02_MobileShop.pdf`)
2. **Understand requirements** - What features are needed?
3. **Study this code** - How is it implemented?
4. **Build YOUR solution** - Implement features yourself
5. **Reference when stuck** - Look at specific parts only

**Learning Path:**
- Week 1: Database + Authentication
- Week 2: Staff features (CRUD)
- Week 3: User features (Search + Cart)
- Week 4: Testing + Polish

### ⚖️ Consequences of Plagiarism:

- ❌ Zero on assignment
- ❌ Course failure
- ❌ Academic probation
- ❌ Permanent academic record

**Remember:** *Understanding > Grades*

---

## 🎓 Learning Objectives

Study this project to understand:

✅ **Authentication & Authorization** - Login systems with sessions  
✅ **Role-Based Access Control** - Different features per role  
✅ **Session Management** - HttpSession for user state & cart  
✅ **Shopping Cart Implementation** - E-commerce basics  
✅ **Multiple Table Database** - Users + Mobiles with relationships  
✅ **Advanced CRUD** - Inline edit, search, filter  
✅ **MVC2 Pattern** - Clean separation of concerns  
✅ **Front Controller** - Centralized request routing  
✅ **Security Basics** - Login validation, role checks  

---

## 📖 Additional Resources

- **Assignment PDF**: [`docs/Workshop_02_MobileShop.pdf`](docs/Workshop_02_MobileShop.pdf)
- **SQL Script**: [`database/MyShop-Create.sql`](database/MyShop-Create.sql)
- **Session Management**: [Oracle HttpSession Docs](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSession.html)
- **JSTL Guide**: [JSTL Tutorial](https://www.javatpoint.com/jstl)
- **MVC Pattern**: [MVC in Java](https://www.geeksforgeeks.org/mvc-design-pattern/)

---

## 🚀 Performance Optimization

### Speed up Tomcat deployment:

**1. Create `web/META-INF/context.xml`:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <JarScanner>
        <JarScanFilter 
            defaultPluggabilityScan="false"
            defaultTldScan="false"/>
    </JarScanner>
</Context>
```

**2. Enable Deploy on Save in NetBeans:**
- Project Properties → Run → Deploy on Save ✅

**3. Exclude from Antivirus:**
- Add project folder to Windows Defender exclusions

**Result:** Deploy time: 30s → 5s ⚡

---

## 👨‍💻 Credits

**Created by:** MindyCoding by Tran

**Purpose:** Educational sample for teaching Java Web Development with Authentication & E-commerce

**Institution:** FPT University - Java Web Development Course

**Contact:** [Your Email/Contact]

**Repository:** [GitHub Link]

---

## 📜 License

**Educational Use Only**

Copyright © 2025 MindyCoding by Tran. All rights reserved.

**Terms:**
- ✅ Study and learn
- ✅ Educational reference
- ❌ Commercial use prohibited
- ❌ Assignment submission prohibited
- ❌ Redistribution without credit prohibited

---

## 🙏 Acknowledgments

- FPT University - Workshop 2 Assignment
- Java EE Community
- Apache Tomcat Project
- Microsoft SQL Server Documentation
- Students learning web development

---

## 📝 Version History

**v1.0.0** (2025-01-XX) - Initial release
- Complete authentication system
- Staff CRUD operations
- User shopping cart
- Role-based access control
- MVC2 architecture

---

**Happy Learning! 🎓**

*"The best way to learn is to build, break, and rebuild."*

---

**Made with ❤️ for education by MindyCoding**