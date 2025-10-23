<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Check if user is logged in -->
<c:if test="${empty LOGIN_USER}">
    <c:redirect url="login.jsp"/>
</c:if>

<!-- Check if user is User (role = 0) -->
<c:if test="${LOGIN_USER.role != 0}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search Mobiles by Price</title>
</head>
<body>
    <h1>Search Mobiles by Price Range</h1>
    <p>Welcome, <strong>${LOGIN_USER.fullName}</strong></p>
    <hr/>
    
    <!-- Navigation -->
    <p>
        <a href="user_home.jsp">Home</a> | 
        <a href="ViewCartController">View Cart</a> | 
        <a href="LogoutController">Logout</a>
    </p>
    <hr/>
    
    <!-- Search Form -->
    <h3>Enter Price Range</h3>
    <form action="SearchPriceController" method="POST">
        <table>
            <tr>
                <td>Min Price ($):</td>
                <td><input type="number" name="minPrice" step="0.01" min="0" required /></td>
            </tr>
            <tr>
                <td>Max Price ($):</td>
                <td><input type="number" name="maxPrice" step="0.01" min="0" required /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Search" />
                    <input type="reset" value="Clear" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>