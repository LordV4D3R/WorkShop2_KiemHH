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
        <title>All Products - MobileShop</title>
    </head>
    <body>
        <h1>All Products</h1>
        <p>Welcome, <strong>${LOGIN_USER.fullName}</strong></p>
        <hr/>

        <!-- Navigation -->
        <p>
            <a href="user_home.jsp">Home</a> | 
            <a href="search_price.jsp">Search by Price</a> | 
            <a href="ViewCartController">View Cart</a> | 
            <a href="LogoutController">Logout</a>
        </p>
        <hr/>

        <!-- Messages -->
        <c:if test="${not empty SUCCESS_MESSAGE}">
            <p style="color: green;"><strong>${SUCCESS_MESSAGE}</strong></p>
                </c:if>

        <c:if test="${not empty ERROR_MESSAGE}">
            <p style="color: red;"><strong>${ERROR_MESSAGE}</strong></p>
                </c:if>

        <!-- Product List -->
        <h3>Available Products</h3>

        <c:choose>
            <c:when test="${not empty LIST_MOBILE}">
                <p>Total: ${LIST_MOBILE.size()} product(s) available</p>

                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Mobile ID</th>
                            <th>Mobile Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Year</th>
                            <th>Stock</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mobile" items="${LIST_MOBILE}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${mobile.mobileId}</td>
                                <td><strong>${mobile.mobileName}</strong></td>
                                <td>${mobile.description}</td>
                                <td><strong>$${mobile.price}</strong></td>
                                <td>${mobile.yearOfProduction}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mobile.quantity > 0}">
                                            ${mobile.quantity}
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: red;">Out of Stock</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mobile.quantity > 0}">
                                            <form action="AddToCartController" method="POST">
                                                <input type="hidden" name="mobileId" value="${mobile.mobileId}">
                                                <input type="hidden" name="returnUrl" value="LoadMobileUserController">
                                                <input type="submit" value="Add to Cart" />
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <button disabled>Out of Stock</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p><strong>No products available at the moment.</strong></p>
            </c:otherwise>
        </c:choose>
    </body>
</html>