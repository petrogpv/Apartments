<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>

    <link href="<c:url value="/css/signin.css" />" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/css/msg.css" />" rel="stylesheet">
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>

    <title>Apartments</title>

</head>

<body>

<div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl" />
    <form action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="msg">${message}</div>
        </c:if>

        <input type="text" class="form-control" name="j_username" placeholder="Email address" required autofocus value="admin">
        <input type="password" class="form-control" name="j_password" placeholder="Password" required value="admin">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>
</div>

</body>
</html>
