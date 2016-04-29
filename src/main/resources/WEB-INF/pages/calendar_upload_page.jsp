<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Calendar Upload</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

</head>

<body>
<div class="container">
    <form action="/calendar_template_upload_page" method="post" >
        <h3>New month</h3>
        <select  name="month">
            <option value="" disabled selected hidden>select month</option>
            <c:forEach items="${months}" var="month">
                <option value="${month.key}">${month.value}</option>
            </c:forEach>
        </select>
        <select  name="year">
            <c:forEach items="${years}" var="year">
                <option value="${year}">${year}</option>
            </c:forEach>
        </select>

        <input type="submit" class="btn btn-primary" value="Add">
    </form>
    <input type="submit" class="btn btn-primary" value="Goto main page" onclick="window.location='/';" />
</div>
</body>
</html>