
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Apartment</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/slimbox2.js"></script>
    <link rel="stylesheet" href="css/slimbox2.css" type="text/css" media="screen" />
</head>
<body>
<table class="table table-striped">
    <thead>
    <tr>
        <td><b>Street</b></td>
        <td><b>Building</b></td>
        <td><b>Apt_number</b></td>
        <td><b>District</b></td>
    </tr>
    </thead>
    <tr>
        <td>${apartment.street}</td>
        <td>${apartment.building}</td>
        <td>${apartment.aptNumber}</td>
        <td>${apartment.district.name}</td>
    </tr>


</table>
<c:forEach items="${apartment.images}" var="image">
    <a target="_blank" style="padding: 5px" href=/photo/${apartment.id}/${image.filename}><img hight = "100" width="100" src="/photo/${apartment.id}/${image.filename}"></a>
</c:forEach>
</body>
</html>
