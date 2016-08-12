<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Chart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/css/msg.css" />" rel="stylesheet">

</head>
<body>
<div class="container">
    <h3>Chart</h3>
<c:if test="${not empty message}">
    <div class="msg">${message}</div>
</c:if>

<div style="width: 100%">
    <jsp:include page="/WEB-INF/pages/navbar.jsp" />
</div>

<a href="/chart_clear" class="btn btn-sm btn-default" style="margin-right: 10px;" role = button >Clear chart</a>

<table class="table table-striped">
    <thead>
    <tr>

        <td><b>Street</b></td>
        <td><b>Building</b></td>
        <td><b>Apt_number</b></td>
        <td><b>District</b></td>
        <td>Bookings</td>
        <td></td>
        <td></td>
    </tr>
    </thead>
    <c:forEach items="${apartments}" var="apartment">
        <tr>

            <td>${apartment.street}</td>
            <td>${apartment.building}</td>
            <td>${apartment.aptNumber}</td>
            <td>${apartment.district.name}</td>
            <td>
        <c:forEach items="${apartment.bookingDates}" var="booking">
            <p><fmt:formatDate pattern="MM-dd-yyyy EEE" value="${booking}"/></p>
            </c:forEach>
            </td>
            <td>
                <form action="/apartment" id="form_${apartment.id}" method="post">
                    <input type="hidden" name="apartmentId" value=${apartment.id}>
                    <input type="submit"  name="form_${apartment.id}" id="edit_${apartment.id}" class="btn btn-primary"
                           value="Edit">
                </form>
            </td>
            <td>
                <form action="/chart_delete" id="delete_form" method="post">
                    <input type="hidden" name="apartmentId" value=${apartment.id}>
                    <input type="submit" onclick="return confirm('Are you sure?')" name="delete_form"
                           class="btn btn-primary" id="delete" value="Delete">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
<a href="/order" class="btn btn-sm btn-default" style="margin-top: 10px;" role = button >Create order</a>
</div>
</body>
</html>
