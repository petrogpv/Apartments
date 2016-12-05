<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Order page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">

    <h3>Order page</h3>
    <jsp:include page="/WEB-INF/pages/navbar.jsp" />

    <table class="table table-striped">
        <thead>
        <tr>
            <td><b>order ID</b></td>
            <td><b>order date</b></td>
            <td><b>client</b></td>
            <td><b>apartments-bookings</b></td>
            <td><b>price</b></td>
            <td></td>

        </tr>
        </thead>
            <tr>
                <td style="vertical-align: middle">${order.id}</td>
                <td style="vertical-align: middle">${order.order_date}</td>
                <td style="vertical-align: middle">${order.client.firstName}  ${order.client.lastName}</td>
                <td>

                    <table class="table table-striped">
                        <thead>
                        <tr>

                            <td><b>Str</b></td>
                            <td><b>Bld</b></td>
                            <td><b>Apt</b></td>
                            <td><b>Dst</b></td>
                            <td><b>Bkgs</b></td>

                        </tr>
                        </thead>
                        <c:forEach items="${order.apartments}" var="apartment">

                            <tr>
                                <td style="vertical-align: middle">${apartment.street}</td>
                                <td style="vertical-align: middle">${apartment.building}</td>
                                <td style="vertical-align: middle">${apartment.aptNumber}</td>
                                <td style="vertical-align: middle">${apartment.district.name}</td>
                                <td>
                                    <c:forEach items="${order.bookings}" var="booking">
                                        <c:if test="${booking.apartment.id eq apartment.id}">
                                                <p><fmt:formatDate pattern="MM-dd-yyyy EEE" value="${booking.day.id}"/></p>
                                        </c:if>
                                    </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                <td style="vertical-align: middle"><b>${order.finalPrice} $</b></td>
                <td style="vertical-align: middle">
                    <form action="/order_delete" id="delete_form" method="post">
                        <input type="hidden" name="orderId" value=${order.id}>
                        <input type="submit" onclick="return confirm('Are you sure?')" name="delete_form"
                               class="btn btn-primary" id="delete" value="Delete">
                    </form>
                </td>

            </tr>
    </table>
</div>
<script>
    $('.selectpicker').selectpicker();
</script>
</body>
</html>