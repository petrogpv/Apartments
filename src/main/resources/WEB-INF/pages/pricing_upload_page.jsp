
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Pricing upload</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script>
        $(function() {
            $( "#datepicker1" ).datepicker();
            $( "#datepicker2" ).datepicker();
        });
    </script>
    <style>
        /*input[type=number]::-webkit-inner-spin-button,*/
        /*input[type=number]::-webkit-outer-spin-button {*/
            /*-webkit-appearance: none;*/
            /*-moz-appearance: none;*/
            /*appearance: none;*/
            /*margin: 0;*/
        /*}*/
        input[type=number] {
            -moz-appearance:textfield;
        }
    </style>
    <script>
                $(document).ready(function () {
                    $('#sub').click(function() {

                        checked = $("input[type=checkbox]:checked").length;

                        if(!checked) {
                            alert("You must select at least one apartment.");
                            return false;
                        }
//                        if($('#datepicker1').val().length == 0){
//                            alert('Please fill out "Date from".');
//                            return false;
//                        }
                        if($('#datepicker2').val().length != 0 && $('#datepicker2').val() < $('#datepicker1').val() ){
                            alert('Please fill out correct dates: "Date to" can\'t be earlier than "Date from".');
                            return false;
                        }
                        if($('#type1').val().length == 0 && $('#type2').val().length == 0 && $('#type3').val().length == 0){
                            alert('Please fill out at least one type.');
                            return false;
                        }
                    });
                });


    </script>
</head>
<body>


        <h3>
           ${message}
        </h3>


<form action="/pricing_upload" id="form" method="post">
<div class="container">
    <h3>Pricing upload</h3>
    <%--<nav class="navbar navbar-default">--%>
        <%--<div class="container-fluid">--%>
            <%--<!-- Collect the nav links, forms, and other content for toggling -->--%>
            <%--<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">--%>
                <%--<ul id="groupList" class="nav navbar-nav">--%>
                    <%--<li class="dropdown">--%>
                        <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Districts <span class="caret"></span></a>--%>
                        <%--<ul class="dropdown-menu">--%>
                            <%--<li><a href="/pricing_upload_page">All</a></li>--%>
                            <%--<c:forEach items="${districts}" var="district">--%>
                                <%--<li><a href="/pricing_upload_page/${district.id}">${district.name}</a></li>--%>
                            <%--</c:forEach>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                    <%--<li><input type="submit" id = "sub" class="btn btn-default navbar-btn" value="Save"></li>--%>
                    <%--<li><p >Date from: <input type="date" name="dateFrom" required id="datepicker1"></p></li>--%>
                    <%--<li><p >Date from: <input type="date" name="dateTo" id="datepicker2"></p></li>--%>
                    <%--<li><input type="number" size="4" step="any" id = "type1" name="types" placeholder="type1"></li>--%>
                    <%--<li><input type="number" size="4" step="any" id = "type2" name="types" placeholder="type2"></li>--%>
                    <%--<li><input type="number" size="4" step="any" id = "type3" name="types" placeholder="type3"></li>--%>
                <%--</ul>--%>
            <%--</div><!-- /.navbar-collapse -->--%>
        <%--</div><!-- /.container-fluid -->--%>
    <%--</nav>--%>
    <ul id="groupList" class="nav navbar-nav">
    <li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Districts <span class="caret"></span></a>
    <ul class="dropdown-menu">
    <li><a href="/pricing_upload_page">All</a></li>
    <c:forEach items="${districts}" var="district">
    <li><a href="/pricing_upload_page/${district.id}">${district.name}</a></li>
    </c:forEach>
    </ul>
    </li>
        </ul>
</div>
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
                <form action="/pricing_upload" id="form_${apartment.id}"  method="post">
                    <input type="hidden" name="aptId" value=${apartment.id}>
                    <input type="submit" name="form_${apartment.id}" id = "change_${price.id}" class="btn btn-primary" value=">">
                </form>

            <td>${apartment.street}</td>
            <td>${apartment.building}</td>
            <td>${apartment.aptNumber}</td>
            <td>${apartment.district.name}</td>

        </tr>
    </c:forEach>

</table>
</form>
</body>
</html>
