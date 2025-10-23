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
    <title>Add New Mobile</title>
</head>
<body>
    <h1>Add New Mobile</h1>
    <p>Staff: ${LOGIN_USER.fullName}</p>
    <hr/>
    
    <!-- Error Message -->
    <c:if test="${not empty ERROR_MESSAGE}">
        <p style="color: red;"><strong>${ERROR_MESSAGE}</strong></p>
    </c:if>
    
    <!-- Add Mobile Form -->
    <form action="AddMobileController" method="POST">
        <table>
            <tr>
                <td>Mobile ID:</td>
                <td><input type="text" name="mobileId" required /></td>
            </tr>
            <tr>
                <td>Mobile Name:</td>
                <td><input type="text" name="mobileName" required /></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><textarea name="description" rows="4" cols="50" required></textarea></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><input type="number" name="price" step="0.01" min="0" required /></td>
            </tr>
            <tr>
                <td>Year of Production:</td>
                <td><input type="number" name="yearOfProduction" min="1900" max="2100" required /></td>
            </tr>
            <tr>
                <td>Quantity:</td>
                <td><input type="number" name="quantity" min="0" required /></td>
            </tr>
            <tr>
                <td>Not Sale:</td>
                <td>
                    <select name="notSale" required>
                        <option value="false">Sale (Available)</option>
                        <option value="true">Not Sale</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Add Mobile" />
                    <input type="reset" value="Reset" />
                    <input type="button" value="Back to List" 
                           onclick="window.location='LoadMobileController';" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>