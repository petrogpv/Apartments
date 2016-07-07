<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Apartments</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


    </head>
    <body>
        <div class="container">
            <h3>Apartments menu</h3>

            <div align="center">
                <h3>
                    ${message}
                </h3>
            </div>

            <input type="submit" class="btn btn-primary" value="Add apartment" onclick="window.location='/add_apartment';" />
            <br>
                <ul id="groupList" class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Districts <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/apartments_page">All</a></li>
                            <c:forEach items="${districts}" var="district">
                                <li><a href="/apartments_page/${district.id}">${district.name}</a></li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
        </div>



            <table class="table table-striped">
                <thead>
                <tr>
                    <td><b>Street</b></td>
                    <td><b>Building</b></td>
                    <td><b>Apt_number</b></td>
                    <td><b>District</b></td>
                    <td></td>
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
                            <c:forEach items="${apartment.images}" var="image">
                                <a target="_blank" href=/photo/${apartment.id}/${image.filename}><img hight = "50" width="50" src="/photo/${apartment.id}/${image.filename}"></a>
                            </c:forEach>
                        </td>
                        <td>
                            <form action="/apartment_edit" id="form_${apartment.id}" method="post">
                                <input type="hidden" name="apartmentId" value=${apartment.id}>
                                <input type="submit" name="form_${apartment.id}" id="edit_${apartment.id}" class="btn btn-primary"
                                       value="Edit">
                            </form>
                        </td>
                        <td>
                            <form action="/apartment_delete" id="delete_form" method="post">
                                <input type="hidden" name="apartmentId" value=${apartment.id}>
                                <input type="submit" onclick="return confirm('Are you sure?')" name="delete_form"
                                       class="btn btn-primary" id="delete" value="Delete">
                            </form>
                        </td>

                    </tr>
                </c:forEach>
            </table>
        <%--<script>--%>
            <%--$('.selectpicker').selectpicker();--%>
        <%--</script>--%>
    </body>
</html>