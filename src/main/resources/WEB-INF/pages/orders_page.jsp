<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="<c:url value="/css/msg.css" />" rel="stylesheet">
<html>
<head>
    <title>Orders</title>
    <%--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">
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

    <h3>Orders</h3>

    <c:if test="${not empty error}">
    <div class="error">${error}</div>
</c:if>
    <c:if test="${not empty message}">
        <div class="msg">${message}</div>
    </c:if>

    <jsp:include page="/WEB-INF/pages/navbar.jsp" />

    <h4><b> Search order by client</b></h4>

    <form class="navbar-form " role="search" action="/client_orders_search" method="post">
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
                <form action="/client_orders_select"  onsubmit="return validateForm()" method="post">
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

            </c:when>
            <c:when test="${found eq 0}">
                <p>Not found</p>
                <div style="width: 100%; margin-top: 0px">
                    <jsp:include page="/WEB-INF/pages/new_client_form.jsp" />
                </div>
            </c:when>
        </c:choose>
    </c:if>

    <c:if test="${not empty orders}">
    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>order ID</b></td>
            <td><b>order date </b></td>
            <td><b>client</b></td>
            <td><b>price</b></td>
            <td><b>registrator</b></td>
        </tr>
        </thead>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>
                    <form action="/order_edit" id="form_${order.id}"  method="post">
                        <input type="hidden" name="orderId" value=${order.id}>
                        <input type="submit" name="form_${order.id}" id = "${order.id}" class="btn btn-primary" value=">">
                    </form>
                </td>
                <td>${order.id}</td>
                <td>${order.order_date}</td>
                <td>${order.client.firstName}  ${order.client.lastName}</td>
                <td>${order.finalPrice} $</td>
                <td>${order.registrator}</td>

            </tr>
        </c:forEach>
    </table>
        </c:if>
</div>

</body>
</html>
