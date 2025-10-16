<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Check if user is logged in -->
<c:if test="${empty LOGIN_USER}">
    <c:redirect url="login.jsp"/>
</c:if>

<!-- Check if user has correct role (User = 0) -->
<c:if test="${LOGIN_USER.role != 0}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Home - MobileShop</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: #f5f5f5;
        }
        
        .header {
            background: white;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .welcome {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }
        
        .user-info {
            color: #666;
            margin-bottom: 15px;
        }
        
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        
        .btn:hover {
            background: #5568d3;
        }
        
        .btn-logout {
            background: #dc3545;
        }
        
        .btn-logout:hover {
            background: #c82333;
        }
        
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        
        .feature-card {
            background: white;
            padding: 30px;
            border-radius: 5px;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        
        .feature-card h3 {
            color: #333;
            margin-bottom: 15px;
        }
        
        .feature-card p {
            color: #666;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="welcome">
            Welcome, ${LOGIN_USER.fullName}! 👋
        </div>
        <div class="user-info">
            User ID: <strong>${LOGIN_USER.userId}</strong> | 
            Role: <strong>Customer</strong>
        </div>
        <a href="LogoutController" class="btn btn-logout">Logout</a>
    </div>
    
    <div class="features">
        <div class="feature-card">
            <h3>🔍 Search Mobiles</h3>
            <p>Search for mobiles by price range</p>
            <a href="search_price.jsp" class="btn">Search</a>
        </div>
        
        <div class="feature-card">
            <h3>📱 View All Products</h3>
            <p>Browse our complete mobile collection</p>
            <a href="LoadMobileUserController" class="btn">View Products</a>
        </div>
        
        <div class="feature-card">
            <h3>🛒 Shopping Cart</h3>
            <p>View your shopping cart and checkout</p>
            <a href="ViewCartController" class="btn">View Cart</a>
        </div>
    </div>
</body>
</html>