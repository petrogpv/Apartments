<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Cart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/css/msg.css" />" rel="stylesheet">

</head>
<body>
<div class="container">
    <h3>Cart</h3>
<c:if test="${not empty message}">
    <div class="msg">${message}</div>
</c:if>

<div style="width: 100%">
    <jsp:include page="/WEB-INF/pages/navbar.jsp" />
</div>

<a href="/cart_clear" class="btn btn-sm btn-default" style="margin-right: 10px;" role = button >Clear cart</a>

    <c:set var="total" scope="page" />

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
    <c:forEach items="${cartApartmentsSet}" var="cartApartment">

        <c:set var="total" value="${total + cartApartment.totalPrice}" scope="page" />

        <tr>
            <td>${cartApartment.street}</td>
            <td>${cartApartment.building}</td>
            <td>${cartApartment.aptNumber}</td>
            <td>${cartApartment.district.name}</td>
            <td>
        <c:forEach items="${cartApartment.monthDaysMap}" var="entry">
            <c:forEach items="${entry.value}" var="set" varStatus="loop">
                    <p><fmt:formatDate pattern="MM-dd-yyyy EEE" value="${set.day}"/>  -  ${set.price.price} $</p>
            </c:forEach>
        </c:forEach>
            </td>
            <td>
                <form action="/apartment" id="form_${cartApartment.id}" method="post">
                    <input type="hidden" name="apartmentId" value=${cartApartment.id}>
                    <input type="submit"  name="form_${cartApartment.id}" id="edit_${cartApartment.id}" class="btn btn-primary"
                           value="Edit">
                </form>
            </td>
            <td>
                <form action="/cart_delete" id="delete_form" method="post">
                    <input type="hidden" name="apartmentId" value=${cartApartment.id}>
                    <input type="submit" onclick="return confirm('Are you sure?')" name="delete_form"
                           class="btn btn-primary" id="delete" value="Delete">
                </form>
            </td>
        </tr>
        <tr>
            <td colspan="4"></td>
            <td align="right"><b>${cartApartment.totalPrice} </b>$</td>
            <td colspan="2"></td>
        </tr>

    </c:forEach>
</table>
    <div align="right" style="margin-right: 10%">
    <h4>Total: <b> ${total} $</b></h4>
    </div>

    <div align="center"><h3><b> New order</b></h3></div>

    <h4><b> Search client in database</b></h4>
    <form class="navbar-form navbar-left" role="search" action="/search" method="post">
        <div class="form-group">
            <input type="text" class="form-control" name="pattern" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Search client</button>
    </form>

    <br><br><br><h4><b>New client</b></h4>

    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/new_order" method="post">

        <input class="form-control form-group width30" type="text" id="firstName"  name="firstName" required placeholder="first name">
        <input class="form-control form-group width30" type="text" id="lastName" name="lastName" required placeholder="last name">
        <input class="form-control form-group width30" type="text" id="address" name="address" required placeholder="address">
        <input class="form-control form-group width30" type="text" id="eMail" name="eMail" placeholder="e-mail">
        <input class="form-control form-group width30" type="text" id="phoneNumber" name="phoneNumber" required placeholder="phone number">
        <input class="form-control form-group width30" type="number" id="discount" name="discount" placeholder="discount">

        <input type="submit" class="btn btn-primary" id="sub" value="Create order">

    </form>

<%--<a href="/order" class="btn btn-sm btn-default" style="margin-top: 10px;" role = button ></a>--%>
</div>
</body>
</html>
