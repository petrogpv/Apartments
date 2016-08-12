<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Districts</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="/css/msg.css" >
        <%--<script>--%>
            <%--$(document).ready(function () {--%>
                <%--$('#sub').click(function() {--%>

                    <%--if($('#name').val().length == 0){--%>
                        <%--alert('Please fill out "Name".');--%>
                        <%--return false;--%>
                    <%--}--%>
                <%--});--%>
            <%--});--%>


        <%--</script>--%>
    </head>
    <body>
        <div class="container">

            <h3>Districts menu</h3>

            <c:if test="${not empty message}">
                <div class="msg">${message}</div>
            </c:if>

            <jsp:include page="/WEB-INF/pages/navbar.jsp" />

            <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/admin/district/add" method="post">

                <ul class="list-inline">
                    <li><h5>New District : </h5></li>
                    <li><input type="text" required id="name" name="name" placeholder="name"></li>
                    <li><input type="submit" class="btn btn-primary" value="Add"></li>
                </ul>
            </form>


            <table class="table table-striped">
                <thead>
                <tr>
                    <td><b>District</b></td>
                    <td></td>
                    <td></td>
                </tr>
                </thead>
                <c:forEach items="${districts}" var="district">
                    <tr>
                        <td>${district.name}</td>
                        <td>
                            <form action="/admin/district_edit" id="form_${district.id}" method="post">
                                <input type="text" required id="edit_input_${district.id}" name="districtName">
                                <input type="hidden" name="districtId" value=${district.id}>
                                <input type="submit" onclick="return confirm('Are you sure?')" name="form_${district.id}" id="edit_${district.id}" class="btn btn-primary"
                                       value="Edit">
                            </form>
                        </td>
                        <td>
                            <form action="/admin/district_delete" id="delete_form" method="post">
                                <input type="hidden" name="districtId" value=${district.id}>
                                <input type="submit" onclick="return confirm('Are you sure?')" name="delete_form"
                                       class="btn btn-primary" id="delete" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
    </div>

    </body>
</html>