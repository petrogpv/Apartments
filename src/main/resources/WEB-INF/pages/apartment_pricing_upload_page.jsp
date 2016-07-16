<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Apartment pricing upload</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <style>
        input[type=number] {
            -moz-appearance: textfield;
        }
    </style>
    <script>
        $(document).ready(function () {
            $('#sub').click(function () {
//                        if($('#datepicker1').val().length == 0){
//                            alert('Please fill out "Date from".');
//                            return false;
//                        }
                if ($('#datepicker2').val().length != 0 && $('#datepicker2').val() < $('#datepicker1').val()) {
                    alert('Please fill out correct dates: "Date to" can\'t be earlier than "Date from".');
                    return false;
                }
                if ($('#type1').val().length == 0 && $('#type2').val().length == 0 && $('#type3').val().length == 0) {
                    alert('Please fill out at least one type.');
                    return false;
                }
                if (($('#type1').val().length > 0 && $('#type1').val() <= 0)
                        || ($('#type2').val().length > 0 && $('#type2').val() <= 0)
                        || ($('#type3').val().length > 0 && $('#type3').val() <= 0)) {
                    alert('Price can\'t be 0 or less!');
                    return false;
                }

            })
        })
    </script>

    <script>
        $(function () {
            $("#datepicker1").datepicker();
            $("#datepicker2").datepicker();
        })
    </script>
    <%--<script>--%>
    <%--$(document).ready(function () {--%>
    <%--$('#delete').click(function () {--%>

    <%--checked = $("input[type=checkbox]:checked").length;--%>

    <%--if (!checked) {--%>
    <%--alert("You must select at least one price to delete.");--%>
    <%--return false;--%>
    <%--}else{--%>
    <%--return true;--%>
    <%--}--%>
    <%--});--%>
    <%--});--%>
    <%--</script>--%>

</head>
<body>
<div class="container">

<table class="table table-striped">
    <thead>
    <tr>
        <td><b>Street</b></td>
        <td><b>Building</b></td>
        <td><b>Apt_number</b></td>
        <td><b>District</b></td>
        <%--<td><b>type 1</b></td>--%>
        <%--<td><b>type 2</b></td>--%>
        <%--<td><b>type 3</b></td>--%>
    </tr>
    </thead>
    <tr>
        <td>${apartment.street}</td>
        <td>${apartment.building}</td>
        <td>${apartment.aptNumber}</td>
        <td>${apartment.district.name}</td>
        <%--<c:choose>--%>
        <%--<c:when test="${fn:length(apartment.prices) eq 0}">--%>
        <%--<td>-</td>--%>
        <%--<td>-</td>--%>
        <%--<td>-</td>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
        <%--<td>--%>
        <%--<c:forEach items="${apartment.prices}" var="price">--%>
        <%--<c:if test="${price.type eq 1}">--%>
        <%--${price.price}--%>
        <%--</c:if>--%>
        <%--</c:forEach>--%>
        <%--</td>--%>
        <%--<td>--%>
        <%--<c:forEach items="${apartment.prices}" var="price">--%>
        <%--<c:if test="${price.type eq 2}">--%>
        <%--${price.price}--%>
        <%--</c:if>--%>
        <%--</c:forEach>--%>
        <%--</td>--%>

        <%--<td>--%>
        <%--<c:forEach items="${apartment.prices}" var="price">--%>
        <%--<c:if test="${price.type eq 3}">--%>
        <%--${price.price}--%>
        <%--</c:if>--%>
        <%--</c:forEach>--%>
        <%--</td>--%>
        <%--</c:otherwise>--%>
        <%--</c:choose>--%>
    </tr>


</table>
<div class="container">
    <h3>Apartment pricing upload</h3>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <form action="/apartment_pricing_upload" method="post">
                    <ul id="groupList" class="nav navbar-nav">

                        <li style="margin: 10px"><p>Date from: <input type="date"  size="6" name="dateFrom" required id="datepicker1"></p></li>
                        <li style="margin: 10px"><p>Date to: <input type="date"  size="6" name="dateTo" id="datepicker2"></p></li>
                        <li style="margin: 10px"><input type="number"  step="any" id="type1" name="types" placeholder="type1"></li>
                        <li style="margin: 10px"><input type="number" step="any" id="type2" name="types" placeholder="type2"></li>
                        <li style="margin: 10px"><input type="number"   step="any" id="type3" name="types" placeholder="type3"></li>
                        <li style="margin: 10px"><input type="submit" id="sub" class="btn btn-default navbar-btn" value="Save"></li>
                        <input type="hidden" name="aptId" value=${apartment.id}>
                    </ul>
                </form>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div>
<div align="center"><h3>
    ${message}
</h3>
</div>
<h4>Actual prices</h4>
<table class="table table-striped">
    <thead>
    <tr>
        <td><b>Type</b></td>
        <td><b>Price</b></td>
        <td><b>DateFrom</b></td>
        <td><b>DateTo</b></td>
        <td><b>DateReg</b></td>
        <td><b>Registrator</b></td>
        <td></td>
        <td></td>
    </tr>
    </thead>

    <c:forEach items="${pricesActual}" var="price">
        <tr>
            <td>${price.type}</td>
            <td>${price.price}</td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_from}"/>
                <br></td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_to}"/></td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_reg}"/>
                <p style="font-size: 10px"><fmt:formatDate type="time" timeStyle="short" value="${price.date_reg}"/></p>
            </td>
            <td>${price.registratorReg}</td>
            <td>
                <form action="/price_edit" id="form_${price.id}" method="post">
                    <input type="number" step="any" required id="edit_input_${price.id}" name="price">
                    <input type="hidden" name="priceId" value=${price.id}>
                    <input type="hidden" name="aptId" value=${apartment.id}>
                    <input type="submit" name="form_${price.id}" id="edit_${price.id}" class="btn btn-primary"
                           value="Edit">
                </form>
            </td>
            <td>
                <form action="/price_delete" id="delete_form" method="post">
                        <%--<input type="checkbox" name="toDelete[]" value="${price.id}" />--%>
                    <input type="hidden" name="aptId" value=${apartment.id}>
                    <input type="hidden" name="priceId" value=${price.id}>
                    <input type="submit" onclick="return confirm('Are you sure?')" name="delete_form"
                           class="btn btn-primary" id="delete" value="Delete">
                </form>
            </td>
        </tr>
        <script>
            $(document).ready(function () {
                $('#edit_${price.id}').click(function () {

                    if (($('#edit_input_${price.id}').val().length > 0 && $('#edit_input_${price.id}').val() <= 0)) {
                        alert('Price can\'t be 0 or less!');
                        return false;
                    }
                    if (($('#edit_input_${price.id}').val().length > 0 && $('#edit_input_${price.id}').val() == ${price.price})) {
                        alert('There is no edit! What are you trying to do?');
                        return false;
                    }
                });
            });
        </script>

    </c:forEach>
</table>
<h4>Archive prices</h4>
<table class="table table-striped">
    <thead>
    <tr>
        <td><b>Type</b></td>
        <td><b>Price</b></td>
        <td><b>DateFrom</b></td>
        <td><b>DateTo</b></td>
        <td><b>DateReg</b></td>
        <td><b>Registrator</b></td>
        <td><b>DateExp</b></td>
        <td><b>RegistratorExp</b></td>
        <td></td>
    </tr>
    </thead>
    <c:forEach items="${pricesArchive}" var="price">
        <tr>
            <td>${price.type}</td>
            <td>${price.price}</td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_from}"/>
                <br></td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_to}"/></td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_reg}"/>
                <p style="font-size: 10px"><fmt:formatDate type="time" timeStyle="short" value="${price.date_reg}"/></p>
            </td>
            <td>${price.registratorReg}</td>
            <td><fmt:formatDate pattern="MM-dd-yyyy" value="${price.date_exp}"/>
                <p style="font-size: 10px"><fmt:formatDate type="time" timeStyle="short" value="${price.date_exp}"/></p>
            </td>
            <td>${price.registratorExp}</td>
        </tr>
    </c:forEach>
</table>
</div>
</body>
</html>