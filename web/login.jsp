<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login - MobileShop</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
            width: 100%;
            max-width: 400px;
        }
        
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .login-header h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 10px;
        }
        
        .login-header p {
            color: #666;
            font-size: 14px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn-login {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s;
        }
        
        .btn-login:hover {
            transform: translateY(-2px);
        }
        
        .error-message {
            background: #fee;
            color: #c33;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #c33;
        }
        
        .success-message {
            background: #efe;
            color: #3c3;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #3c3;
        }
        
        .test-accounts {
            margin-top: 30px;
            padding: 15px;
            background: #f5f5f5;
            border-radius: 5px;
            font-size: 12px;
        }
        
        .test-accounts h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 14px;
        }
        
        .test-accounts div {
            margin: 5px 0;
            color: #666;
        }
        
        .test-accounts strong {
            color: #667eea;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>📱 MobileShop</h1>
            <p>Please login to continue</p>
        </div>
        
        <!-- Error Message -->
        <c:if test="${not empty ERROR_MESSAGE}">
            <div class="error-message">
                ❌ ${ERROR_MESSAGE}
            </div>
        </c:if>
        
        <!-- Success Message (after logout) -->
        <c:if test="${not empty SUCCESS_MESSAGE}">
            <div class="success-message">
                ✅ ${SUCCESS_MESSAGE}
            </div>
        </c:if>
        
        <!-- Login Form -->
        <form action="LoginController" method="POST">
            <div class="form-group">
                <label for="userId">User ID</label>
                <input type="text" 
                       id="userId" 
                       name="userId" 
                       placeholder="Enter your user ID"
                       value="${param.userId}"
                       required 
                       autofocus>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" 
                       id="password" 
                       name="password" 
                       placeholder="Enter your password"
                       required>
            </div>
            
            <button type="submit" class="btn-login">
                Login
            </button>
        </form>
        
        <!-- Test Accounts Info -->
        <div class="test-accounts">
            <h3>🔑 Test Accounts:</h3>
            <div>
                <strong>Staff:</strong> admin / 1
            </div>
            <div>
                <strong>User:</strong> user01 / 1
            </div>
        </div>
    </div>
</body>
</html>