<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Prog.kiev.ua</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script>
            $( document ).ready(function() {
            $('input:radio').change(function() {
                if ($(this).is(':checked')){ //radio is now checked
                    $('input:checkbox').prop('checked', false); //unchecks all checkboxes
                }
            });

            $('input:checkbox').change(function() {
                if ($(this).is(':checked')){
                    $('input:radio').prop('checked', false);
                }
            });
            });
        </script>
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
                            <li><button type="button" id="apartments" class="btn btn-default navbar-btn">Apartments</button></li>
                            <li><button type="button" id="districts" class="btn btn-default navbar-btn">Districts</button></li>

                            <li><button type="button" id="delete_group" class="btn btn-default navbar-btn">Delete Group</button></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Districts <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/">All</a></li>
                                    <c:forEach items="${districts}" var="district">
                                        <li><a href="/${district.id}">${district.name}</a></li>
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
                        <td>
                            <form action="/apartment" id="form_${apartment.id}"  method="post">
                                <input type="hidden" name="aptartmentId" value=${apartment.id}>
                                <input type="submit" name="form_${apartment.id}" id = "${apartment.id}" class="btn btn-primary" value=">">
                             </form>
                        </td>
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

            $('#apartments').click(function(){
                window.location.href='/apartments_page';
            })

            $('#districts').click(function(){
                window.location.href='/districts_page';
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