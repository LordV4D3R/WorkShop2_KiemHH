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
</head>
<body>
    <h1>Welcome to MobileShop</h1>
    <p>Hello, <strong>${LOGIN_USER.fullName}</strong>!</p>
    <p>User ID: ${LOGIN_USER.userId} | Role: Customer</p>
    <hr/>
    
    <!-- Navigation -->
    <h3>Main Menu:</h3>
    <ul>
        <li><a href="search_price.jsp">Search Mobiles by Price Range</a></li>
        <li><a href="LoadMobileUserController">View All Products</a></li>
        <li><a href="ViewCartController">View Shopping Cart</a></li>
        <li><a href="LogoutController">Logout</a></li>
    </ul>
</body>
</html>