<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Check if user is logged in -->
<c:if test="${empty LOGIN_USER}">
    <c:redirect url="login.jsp"/>
</c:if>

<!-- Check if user is Staff (role = 1) -->
<c:if test="${LOGIN_USER.role != 1}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Mobile Management - Staff</title>
</head>
<body>
    <h1>Mobile Management</h1>
    <p>Welcome, <strong>${LOGIN_USER.fullName}</strong> (Staff)</p>
    <hr/>
    
    <!-- Navigation -->
    <p>
        <a href="add_mobile.jsp">Add New Mobile</a> | 
        <a href="LogoutController">Logout</a>
    </p>
    <hr/>
    
    <!-- Search Form -->
    <h3>Search Mobiles</h3>
    <form action="SearchMobileController" method="POST">
        Search by ID or Name: 
        <input type="text" name="searchValue" value="${SEARCH_VALUE}" required />
        <input type="submit" value="Search" />
        <a href="LoadMobileController">Show All</a>
    </form>
    <hr/>
    
    <!-- Messages -->
    <c:if test="${not empty SUCCESS_MESSAGE}">
        <p style="color: green;"><strong>${SUCCESS_MESSAGE}</strong></p>
    </c:if>
    
    <c:if test="${not empty ERROR_MESSAGE}">
        <p style="color: red;"><strong>${ERROR_MESSAGE}</strong></p>
    </c:if>
    
    <c:if test="${not empty INFO_MESSAGE}">
        <p style="color: blue;"><strong>${INFO_MESSAGE}</strong></p>
    </c:if>
    
    <!-- Mobile List Table -->
    <h3>Mobile List</h3>
    
    <c:choose>
        <c:when test="${not empty LIST_MOBILE}">
            <p>Total: ${LIST_MOBILE.size()} mobile(s)</p>
            
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Mobile ID</th>
                        <th>Mobile Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Year</th>
                        <th>Quantity</th>
                        <th>Not Sale</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mobile" items="${LIST_MOBILE}" varStatus="counter">
                        <tr>
                            <form action="UpdateMobileController" method="POST">
                                <td>${counter.count}</td>
                                
                                <!-- Mobile ID - Read only -->
                                <td>
                                    <strong>${mobile.mobileId}</strong>
                                    <input type="hidden" name="mobileId" value="${mobile.mobileId}">
                                </td>
                                
                                <!-- Mobile Name - Read only (không update) -->
                                <td>${mobile.mobileName}</td>
                                
                                <!-- Description - Editable -->
                                <td>
                                    <input type="text" name="description" 
                                           value="${mobile.description}" 
                                           size="40" required />
                                </td>
                                
                                <!-- Price - Editable -->
                                <td>
                                    <input type="number" name="price" 
                                           value="${mobile.price}" 
                                           step="0.01" min="0" 
                                           size="10" required />
                                </td>
                                
                                <!-- Year - Read only (không update) -->
                                <td>${mobile.yearOfProduction}</td>
                                
                                <!-- Quantity - Editable -->
                                <td>
                                    <input type="number" name="quantity" 
                                           value="${mobile.quantity}" 
                                           min="0" size="5" required />
                                </td>
                                
                                <!-- Not Sale - Editable -->
                                <td>
                                    <select name="notSale">
                                        <option value="false" ${!mobile.notSale ? 'selected' : ''}>Sale</option>
                                        <option value="true" ${mobile.notSale ? 'selected' : ''}>Not Sale</option>
                                    </select>
                                </td>
                                
                                <!-- Actions -->
                                <td>
                                    <input type="submit" value="Update" />
                                    
                                    <input type="button" value="Delete" 
                                           onclick="if(confirm('Delete ${mobile.mobileName}?')) 
                                                    window.location='DeleteMobileController?mobileId=${mobile.mobileId}';" />
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p><strong>No mobiles found.</strong></p>
            <c:if test="${not empty SEARCH_VALUE}">
                <p><a href="LoadMobileController">Show all mobiles</a></p>
            </c:if>
        </c:otherwise>
    </c:choose>
</body>
</html>