<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        
        .header {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .header-left h1 {
            color: #333;
            font-size: 24px;
            margin-bottom: 5px;
        }
        
        .header-left p {
            color: #666;
            font-size: 14px;
        }
        
        .header-right {
            display: flex;
            gap: 10px;
        }
        
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background: #667eea;
            color: white;
        }
        
        .btn-primary:hover {
            background: #5568d3;
        }
        
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background: #c82333;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background: #218838;
        }
        
        .btn-warning {
            background: #ffc107;
            color: #333;
        }
        
        .btn-warning:hover {
            background: #e0a800;
        }
        
        .search-section {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .search-form {
            display: flex;
            gap: 10px;
        }
        
        .search-input {
            flex: 1;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
        }
        
        .search-input:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .message {
            padding: 12px 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .message-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .message-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .message-info {
            background: #d1ecf1;
            color: #0c5460;
            border: 1px solid #bee5eb;
        }
        
        .table-container {
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        thead {
            background: #667eea;
            color: white;
        }
        
        th {
            padding: 15px;
            text-align: left;
            font-weight: 600;
            font-size: 14px;
        }
        
        td {
            padding: 12px 15px;
            border-bottom: 1px solid #f0f0f0;
            font-size: 14px;
        }
        
        tbody tr:hover {
            background: #f8f9fa;
        }
        
        .status-badge {
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .status-available {
            background: #d4edda;
            color: #155724;
        }
        
        .status-not-sale {
            background: #f8d7da;
            color: #721c24;
        }
        
        .status-low-stock {
            background: #fff3cd;
            color: #856404;
        }
        
        .action-buttons {
            display: flex;
            gap: 5px;
        }
        
        .btn-small {
            padding: 5px 10px;
            font-size: 12px;
        }
        
        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
        }
        
        .stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-bottom: 20px;
        }
        
        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .stat-card h3 {
            color: #666;
            font-size: 14px;
            margin-bottom: 10px;
        }
        
        .stat-card .number {
            color: #333;
            font-size: 32px;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <div class="header">
        <div class="header-left">
            <h1>📱 Mobile Management</h1>
            <p>Welcome, <strong>${LOGIN_USER.fullName}</strong> (Staff)</p>
        </div>
        <div class="header-right">
            <a href="add_mobile.jsp" class="btn btn-success">+ Add New Mobile</a>
            <a href="LogoutController" class="btn btn-danger">Logout</a>
        </div>
    </div>
    
    <!-- Search Section -->
    <div class="search-section">
        <h3 style="margin-bottom: 15px; color: #333;">🔍 Search Mobiles</h3>
        <form action="SearchMobileController" method="POST" class="search-form">
            <input type="text" 
                   name="searchValue" 
                   class="search-input"
                   placeholder="Search by Mobile ID or Name..."
                   value="${SEARCH_VALUE}"
                   required>
            <button type="submit" class="btn btn-primary">Search</button>
            <a href="LoadMobileController" class="btn btn-warning">Show All</a>
        </form>
    </div>
    
    <!-- Messages -->
    <c:if test="${not empty SUCCESS_MESSAGE}">
        <div class="message message-success">
            ✅ ${SUCCESS_MESSAGE}
        </div>
    </c:if>
    
    <c:if test="${not empty ERROR_MESSAGE}">
        <div class="message message-error">
            ❌ ${ERROR_MESSAGE}
        </div>
    </c:if>
    
    <c:if test="${not empty INFO_MESSAGE}">
        <div class="message message-info">
            ℹ️ ${INFO_MESSAGE}
        </div>
    </c:if>
    
    <!-- Statistics -->
    <c:if test="${not empty LIST_MOBILE}">
        <div class="stats">
            <div class="stat-card">
                <h3>Total Mobiles</h3>
                <div class="number">${LIST_MOBILE.size()}</div>
            </div>
            <div class="stat-card">
                <h3>Available for Sale</h3>
                <div class="number">
                    <c:set var="availableCount" value="0"/>
                    <c:forEach var="mobile" items="${LIST_MOBILE}">
                        <c:if test="${!mobile.notSale}">
                            <c:set var="availableCount" value="${availableCount + 1}"/>
                        </c:if>
                    </c:forEach>
                    ${availableCount}
                </div>
            </div>
            <div class="stat-card">
                <h3>Total Stock</h3>
                <div class="number">
                    <c:set var="totalStock" value="0"/>
                    <c:forEach var="mobile" items="${LIST_MOBILE}">
                        <c:set var="totalStock" value="${totalStock + mobile.quantity}"/>
                    </c:forEach>
                    ${totalStock}
                </div>
            </div>
        </div>
    </c:if>
    
    <!-- Mobile List Table -->
    <div class="table-container">
        <c:choose>
            <c:when test="${not empty LIST_MOBILE}">
                <table>
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Mobile ID</th>
                            <th>Mobile Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Year</th>
                            <th>Quantity</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="mobile" items="${LIST_MOBILE}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td><strong>${mobile.mobileId}</strong></td>
                                <td>${mobile.mobileName}</td>
                                <td style="max-width: 300px;">
                                    ${mobile.description.length() > 80 
                                      ? mobile.description.substring(0, 80).concat('...') 
                                      : mobile.description}
                                </td>
                                <td>
                                    <strong>$<fmt:formatNumber value="${mobile.price}" pattern="#,##0.00"/></strong>
                                </td>
                                <td>${mobile.yearOfProduction}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mobile.quantity <= 0}">
                                            <span style="color: red; font-weight: bold;">0</span>
                                        </c:when>
                                        <c:when test="${mobile.quantity < 10}">
                                            <span style="color: orange; font-weight: bold;">${mobile.quantity}</span>
                                        </c:when>
                                        <c:otherwise>
                                            ${mobile.quantity}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${mobile.notSale}">
                                            <span class="status-badge status-not-sale">Not for Sale</span>
                                        </c:when>
                                        <c:when test="${mobile.quantity <= 0}">
                                            <span class="status-badge status-not-sale">Out of Stock</span>
                                        </c:when>
                                        <c:when test="${mobile.quantity < 10}">
                                            <span class="status-badge status-low-stock">Low Stock</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-badge status-available">Available</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="action-buttons">
                                        <form action="MainController" method="POST" style="display: inline;">
                                            <input type="hidden" name="mobileId" value="${mobile.mobileId}">
                                            <button type="submit" name="action" value="UpdateMobile" 
                                                    class="btn btn-primary btn-small">
                                                Edit
                                            </button>
                                        </form>
                                        
                                        <form action="MainController" method="POST" style="display: inline;">
                                            <input type="hidden" name="mobileId" value="${mobile.mobileId}">
                                            <button type="submit" name="action" value="DeleteMobile" 
                                                    class="btn btn-danger btn-small"
                                                    onclick="return confirm('Are you sure you want to delete ${mobile.mobileName}?');">
                                                Delete
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="no-data">
                    <h3>📭 No mobiles found</h3>
                    <p style="margin-top: 10px;">
                        <c:choose>
                            <c:when test="${not empty SEARCH_VALUE}">
                                Try a different search keyword or <a href="LoadMobileController">show all mobiles</a>
                            </c:when>
                            <c:otherwise>
                                <a href="add_mobile.jsp">Add your first mobile</a>
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>