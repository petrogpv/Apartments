<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Districts</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
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
            <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/district/add" method="post">
                <div class="form-group"><h5>New District</h5></div>
                <div class="form-group" style="float: left;"><input type="text" required class="form-control" id="name" name="name" placeholder="name"></div>
                <div class="form-group" style="margin-left: 200px;"><input type="submit" class="btn btn-primary" value="Add"></div>
            </form>
            <input type="submit" class="btn btn-primary" value="Goto main page" onclick="window.location='/';" />

            <div align="center">
                <h3>
                ${message}
                </h3>
            </div>

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
                            <form action="/district_edit" id="form_${district.id}" method="post">
                                <input type="text" required id="edit_input_${district.id}" name="districtName">
                                <input type="hidden" name="districtId" value=${district.id}>
                                <input type="submit" onclick="return confirm('Are you sure?')" name="form_${district.id}" id="edit_${district.id}" class="btn btn-primary"
                                       value="Edit">
                            </form>
                        </td>
                        <td>
                            <form action="/district_delete" id="delete_form" method="post">
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