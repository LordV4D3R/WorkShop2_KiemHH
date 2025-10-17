<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login - MobileShop</title>
</head>
<body>
    <h1>MobileShop - Login</h1>
    <hr/>
    
    <!-- Error Message -->
    <c:if test="${not empty ERROR_MESSAGE}">
        <p style="color: red;"><strong>${ERROR_MESSAGE}</strong></p>
    </c:if>
    
    <!-- Success Message -->
    <c:if test="${not empty SUCCESS_MESSAGE}">
        <p style="color: green;"><strong>${SUCCESS_MESSAGE}</strong></p>
    </c:if>
    
    <!-- Login Form -->
    <form action="LoginController" method="POST">
        <table>
            <tr>
                <td>User ID:</td>
                <td>
                    <input type="text" name="userId" value="${param.userId}" required />
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <input type="password" name="password" required />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Login" />
                </td>
            </tr>
        </table>
    </form>
    
    <hr/>
    <h3>Test Accounts:</h3>
    <ul>
        <li><strong>Staff:</strong> admin / 1</li>
        <li><strong>User:</strong> user01 / 1</li>
    </ul>
</body>
</html>