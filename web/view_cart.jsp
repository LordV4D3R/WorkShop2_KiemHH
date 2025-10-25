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
    <title>Shopping Cart</title>
</head>
<body>
    <h1>Shopping Cart</h1>
    <p>User: <strong>${LOGIN_USER.fullName}</strong></p>
    <hr/>
    
    <!-- Navigation -->
    <p>
        <a href="search_price.jsp">Search Mobiles</a> | 
        <a href="user_home.jsp">Home</a> | 
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
    
    <!-- Cart Content -->
    <c:choose>
        <c:when test="${empty CART or CART.size() == 0}">
            <h3>Your cart is empty</h3>
            <p><a href="search_price.jsp">Start shopping</a></p>
        </c:when>
        <c:otherwise>
            <h3>Cart Items (${CART.size()} item(s))</h3>
            
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Mobile ID</th>
                        <th>Mobile Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="count" value="0"/>
                    <c:forEach var="entry" items="${CART}">
                        <c:set var="count" value="${count + 1}"/>
                        <c:set var="mobileId" value="${entry.key}"/>
                        <c:set var="quantity" value="${entry.value}"/>
                        <c:set var="mobile" value="${CART_DETAILS[mobileId]}"/>
                        
                        <c:if test="${not empty mobile}">
                            <tr>
                                <td>${count}</td>
                                <td>${mobile.mobileId}</td>
                                <td><strong>${mobile.mobileName}</strong></td>
                                <td>$${mobile.price}</td>
                                <td>
                                    <form action="UpdateCartController" method="POST" style="display: inline;">
                                        <input type="hidden" name="mobileId" value="${mobileId}">
                                        <input type="number" name="quantity" value="${quantity}" 
                                               min="1" size="5" />
                                        <input type="submit" value="Update" />
                                    </form>
                                </td>
                                <td><strong>$${mobile.price * quantity}</strong></td>
                                <td>
                                    <form action="RemoveFromCartController" method="POST">
                                        <input type="hidden" name="mobileId" value="${mobileId}">
                                        <input type="submit" value="Remove" 
                                               onclick="return confirm('Remove ${mobile.mobileName} from cart?');" />
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    
                    <!-- Total Row -->
                    <tr>
                        <td colspan="5" align="right"><strong>Total Amount:</strong></td>
                        <td colspan="2"><strong style="color: red; font-size: 18px;">$${TOTAL_AMOUNT}</strong></td>
                    </tr>
                </tbody>
            </table>
            
            <br/>
            <p>
                <a href="search_price.jsp">Continue Shopping</a>
            </p>
        </c:otherwise>
    </c:choose>
</body>
</html>