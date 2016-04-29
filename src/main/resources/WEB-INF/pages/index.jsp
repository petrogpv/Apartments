<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Prog.kiev.ua</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h3><a href="/">Apartments main</a></h3>

            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul id="groupList" class="nav navbar-nav">
                            <li><button type="button" id="pricing_upload" class="btn btn-default navbar-btn">Pricing upload</button></li>
                            <li><button type="button" id="calendar_upload" class="btn btn-default navbar-btn">Calendar upload</button></li>
                            <li><button type="button" id="add_apartment" class="btn btn-default navbar-btn">Add Apartment</button></li>
                            <li><button type="button" id="add_district" class="btn btn-default navbar-btn">Add District</button></li>

                            <li><button type="button" id="delete_group" class="btn btn-default navbar-btn">Delete Group</button></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Districts <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <%--<li><a href="/">Default</a></li>--%>
                                    <c:forEach items="${districts}" var="district">
                                        <li><a href="/">${district.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                        <form class="navbar-form navbar-left" role="search" action="/search" method="post">
                            <div class="form-group">
                                <input type="text" class="form-control" name="pattern" placeholder="Search">
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>

            <table class="table table-striped">
                <thead>
                <tr>
                    <td></td>
                    <td><b>Street</b></td>
                    <td><b>Building</b></td>
                    <td><b>Apt_number</b></td>
                    <td><b>District</b></td>
                </tr>
                </thead>
                <c:forEach items="${apartments}" var="apartment">
                    <tr>
                        <td><input type="checkbox" name="toDelete[]" value="${apartment.id}" id="checkbox_${apartment.id}"/></td>
                        <td>${apartment.street}</td>
                        <td>${apartment.building}</td>
                        <td>${apartment.aptNumber}</td>
                        <td>${apartment.district.name}</td>

                    </tr>
                </c:forEach>
            </table>
        </div>

        <script>
            $('.dropdown-toggle').dropdown();

            $('#add_apartment').click(function(){
                window.location.href='/apartment_add_page';
            })

            $('#add_district').click(function(){
                window.location.href='/district_add_page';
            })

            $('#calendar_upload').click(function(){
                window.location.href='/calendar_upload_page';
            })
            $('#pricing_upload').click(function(){
                window.location.href='/pricing_upload_page';
            })

            $('#delete_contact').click(function(){
                var data = { 'toDelete[]' : []};
                $(":checked").each(function() {
                    data['toDelete[]'].push($(this).val());
                });
                $.post("/contact/delete", data);
            })

            $( "li .searchterm" ).click(function() {
                console.log('testing');
            });
        </script>
    </body>
</html>