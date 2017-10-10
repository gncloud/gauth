<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gauth Admin Login</title>
    <link rel="stylesheet" href="<%=pageContext.getServletContext().getContextPath()%>/css/style.css">
    <script src='<%=pageContext.getServletContext().getContextPath()%>/js/jquery-3.2.1.js'></script>
    <script src="<%=pageContext.getServletContext().getContextPath()%>/admin/login.js"></script>
</head>
<body>
<div class="login-page">
    <div class="form">
        <div class="login-form">
            <input type="text" placeholder="id" autofocus="autofocus" id="userId"/>
            <input type="password" placeholder="password"  id="password"/>
            <button id="submit">login</button>
        </div>
    </div>
</div>
</body>
</html>