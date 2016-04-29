<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>New District</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#sub').click(function() {

                    if($('#name').val().length == 0){
                        alert('Please fill out "Name".');
                        return false;
                    }
                });
            });


        </script>
    </head>
    <body>
        <div class="container">
            <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/district/add" method="post">
                <div class="form-group"><h3>New District</h3></div>
                <div class="form-group"><input type="text" class="form-control" id="name" name="name" placeholder="name"></div>
                <div class="form-group"><input type="submit" class="btn btn-primary" value="Add"></div>
            </form>
            <input type="submit" class="btn btn-primary" id="sub" value="Goto main page" onclick="window.location='/';" />
    </div>
    </body>
</html>