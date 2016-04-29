<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>New Apartment</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#sub').click(function() {

                    if($('#street').val().length == 0){
                        alert('Please fill out "Street".');
                        return false;
                    }
                    if($('#building').val().length == 0){
                        alert('Please fill out "Street".');
                        return false;
                    }
                    if($('#aptNumber').val().length == 0){
                        alert('Please fill out "aptNumber".');
                        return false;
                    }

                });
            });


        </script>
    </head>
    <body>
        <div class="container">
            <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/apartment/add" method="post">
                        <h3>New apartment</h3>
                        <select class="selectpicker form-control form-group" name="districtId">
                            <option value="" disabled selected hidden>select district</option>
                            <c:forEach items="${districts}" var="district">
                                <option value="${district.id}">${district.name}</option>
                            </c:forEach>
                        </select>
                        <input class="form-control form-group" type="text" id="street"  name="street" placeholder="street">
                        <input class="form-control form-group" type="text" id="building" name="building" placeholder="building">
                        <input class="form-control form-group" type="text" id="aptNumber" name="aptNumber" placeholder="aptNumber">
                    <input type="submit" class="btn btn-primary" id="sub" value="Add">
            </form>
        </div>

        <script>
            $('.selectpicker').selectpicker();
        </script>
    </body>
</html>