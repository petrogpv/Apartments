<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Cart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
    <link href="<c:url value="/css/msg.css" />" rel="stylesheet">

    <style>
        .span-size  {
            font-size: medium;
            display: inline-block;
            text-align: center;
        }
        .font-red{
            color: red;
        }
    </style>
<script>
        function validateForm() {
            var radios = document.getElementsByName("clientId");
            var formValid = false;

            var i = 0;
            while (!formValid && i < radios.length) {
                if (radios[i].checked) formValid = true;
                i++;
            }

            if (!formValid) alert("Must check some option!");
            return formValid;
        }
</script>
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
    <c:choose>
        <c:when test="${fn:length(cartApartmentsSet) eq 0}">
            <h3 align="center">Cart is empty</h3>
                </c:when>
        <c:otherwise>


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
                                    <p><fmt:formatDate pattern="MM-dd-yyyy EEE" value="${set.day.id}"/>  -  ${set.price.price} $</p>
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

            <h4><b> Search client in database</b></h4>

            <form class="navbar-form " role="search" action="/client_search" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="pattern" required placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">Search client</button>
            </form>

            <c:if test="${empty client}">

                <c:if test="${not empty patternToFind}">
                    <div style="margin-top: 20px; margin-left: 20px; margin-bottom: 20px">Searching results for: <span style="font-size: 20px; color: red"> ${patternToFind}</span></div>
                </c:if>
                <c:choose>
                    <c:when test="${found gt 0}">
                        <form action="/client_select"  onsubmit="return validateForm()" method="post">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <td></td>
                                    <td><b>f.name</b></td>
                                    <td><b>l.name</b></td>
                                    <td><b>address</b></td>
                                    <td><b>email</b></td>
                                    <td><b>phone number</b></td>
                                    <td><b>discount</b></td>
                                </tr>
                                </thead>

                                <c:forEach items="${clients}" var="client">

                                <tr>
                                    <td>
                                        <input type="radio" name="clientId" id = "radio" value=${client.id}>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:containsIgnoreCase(client.firstName, patternToFind)}">
                                                <div style="font-size: 0px">
                                                    <c:set var="patternToFindLC" value="${fn:toLowerCase(patternToFind)}" />
                                                    <c:set var="firstNameLC" value="${fn:toLowerCase(client.firstName)}" />
                                                    <c:set var="nameParts" value="${fn:split(firstNameLC, patternToFindLC )}"/>

                                                    <c:if test="${fn:startsWith(firstNameLC,patternToFindLC )}">
                                                        <span class="span-size font-red">${patternToFindLC}</span>
                                                    </c:if>
                                                    <c:set var="check" value="0"></c:set>
                                                    <c:forEach items="${nameParts}" var="namePart">
                                                        <c:choose>
                                                            <c:when test="${check eq 1}">
                                                                <span class="span-size font-red">${patternToFindLC}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="check" value="1"></c:set>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <span class="span-size">${namePart}</span>
                                                    </c:forEach>
                                                    <c:if test="${fn:length(firstNameLC) ne fn:length(patternToFindLC)}">
                                                        <c:if test="${fn:endsWith(firstNameLC,patternToFindLC )}">
                                                            <span class="span-size font-red">${patternToFindLC}</span>
                                                        </c:if>
                                                    </c:if>

                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                ${client.firstName}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${fn:containsIgnoreCase(client.lastName, patternToFind)}">
                                                <div style="font-size: 0px">
                                                    <c:set var="patternToFindLC" value="${fn:toLowerCase(patternToFind)}" />
                                                    <c:set var="lastNameLC" value="${fn:toLowerCase(client.lastName)}" />
                                                    <c:set var="nameParts" value="${fn:split(lastNameLC, patternToFindLC )}"/>

                                                    <c:if test="${fn:startsWith(lastNameLC,patternToFindLC )}">
                                                        <span class="span-size font-red">${patternToFindLC}</span>
                                                    </c:if>
                                                    <c:set var="check" value="0"></c:set>
                                                    <c:forEach items="${nameParts}" var="namePart">
                                                        <c:choose>
                                                            <c:when test="${check eq 1}">
                                                                <span class="span-size font-red">${patternToFindLC}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="check" value="1"></c:set>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <span class="span-size">${namePart}</span>
                                                    </c:forEach>
                                                    <c:if test="${fn:length(lastNameLC) ne fn:length(patternToFindLC)}">
                                                        <c:if test="${fn:endsWith(lastNameLC,patternToFindLC )}">
                                                            <span class="span-size font-red">${patternToFindLC}</span>
                                                        </c:if>
                                                    </c:if>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                ${client.lastName}
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                    <td>${client.address}</td>
                                    <td>${client.eMail}</td>
                                    <td>${client.phoneNumber}</td>
                                    <td>${client.discount}</td>
                                <tr>

                                    </c:forEach>

                            </table>
                            <input type="submit"  name="form" id="select" class="btn btn-primary" value="Select">
                        </form>
                        <input type="submit" class="btn btn-primary" value="New client" onclick="window.location.href='/cart'"/>

                    </c:when>
                    <c:when test="${found eq 0}">
                        <p>Not found</p>
                        <div style="width: 100%; margin-top: 0px">
                            <jsp:include page="/WEB-INF/pages/new_client_form.jsp" />
                        </div>
                    </c:when>
                </c:choose>

                <c:if test="${empty found}">
                    <div style="width: 100%; margin-top: 0px">
                        <jsp:include page="/WEB-INF/pages/new_client_form.jsp" />
                    </div>
                </c:if>
            </c:if>

            <c:if test="${not empty client}">
                <div align="center"><h3><b> New order</b></h3></div>
                <table class="table table-striped">
                    <thead>
                    <tr>

                        <td><b>f.name</b></td>
                        <td><b>l.name</b></td>
                        <td><b>address</b></td>
                        <td><b>email</b></td>
                        <td><b>phone number</b></td>
                        <td><b>discount</b></td>
                    </tr>
                    </thead>
                    <tr>
                        <td>${client.firstName}</td>
                        <td>${client.lastName}</td>
                        <td>${client.address}</td>
                        <td>${client.eMail}</td>
                        <td>${client.phoneNumber}</td>
                        <td>${client.discount}</td>
                    <tr>
                </table>
                <form action="/new_order" id="form_${client.id}" method="post">
                    <input type="hidden" name="clientId" value=${client.id}>
                    <input type="submit"  name="form_${client.id}" id="select_${client.id}" class="btn btn-primary" value="Save order">
                </form>
            </c:if>
        </c:otherwise>
    </c:choose>

<%--<a href="/order" class="btn btn-sm btn-default" style="margin-top: 10px;" role = button ></a>--%>
</div>
</body>
</html>
