<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Apartment edit</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <%--<link rel="stylesheet" type="text/css" href="http://rvera.github.io/image-picker/image-picker/image-picker.css">--%>
    <script src="http://rvera.github.io/image-picker/image-picker/image-picker.js" type="text/javascript"></script>

    <style type="text/css">
        ul.thumbnails.image_picker_selector {
            overflow: auto;
            list-style-image: none;
            list-style-position: outside;
            list-style-type: none;
            padding: 0px;
            margin: 0px; }
        ul.thumbnails.image_picker_selector ul {
            overflow: auto;
            list-style-image: none;
            list-style-position: outside;
            list-style-type: none;
            padding: 0px;
            margin: 0px; }
        ul.thumbnails.image_picker_selector li.group_title {
            float: none; }
        ul.thumbnails.image_picker_selector li {
            margin: 0px 12px 12px 0px;
            float: left; }
        ul.thumbnails.image_picker_selector li .thumbnail {
            padding: 6px;
            border: 1px solid #dddddd;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none; }
        ul.thumbnails.image_picker_selector li .thumbnail img {
            width: 200px;
            -webkit-user-drag: none; }
        ul.thumbnails.image_picker_selector li .thumbnail.selected {
            background: #ff1010; }

        /*.thumbnails li img{*/
            /*width: 200px;*/
        /*}*/
    </style>
</head>
<body>

<div class="container">
    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/apartment/post_edit" method="post">
        <h3>Apartment edit</h3>
        <h5 >District </h5>
        <select style="width: 200px" required name="districtId">
            <c:forEach items="${districts}" var="district">
                <option  <c:out value="${district.id eq apartment.district.id ? 'selected': ''}"/> value="${district.id}">${district.name}</option>
            </c:forEach>
        </select>
        <h5>Street </h5><input style="width: 200px" class="form-control form-group" type="text" id="street"  name="street" required value="${apartment.street}">
        <h5>Building </h5><input style="width: 200px" class="form-control form-group" type="text" id="building" name="building" required value="${apartment.building}" >
        <h5>AptNumber </h5><input style="width: 200px" class="form-control form-group" type="text" id="aptNumber" name="aptNumber" value="${apartment.aptNumber}" >

        <c:if test ="${fn:length(apartment.images) ne 0}">
            <h5 >Choose photos to delete: </h5>
        <div class="picker">

        <select style="display: none"  multiple="multiple" class="image-picker" name="toDelete" >
        <c:forEach items="${apartment.images}" var="image">
            <option data-img-src="/photo/${apartment.id}/${image.filename}"  value="${image.id}"></option>

        </c:forEach>
        </select>
        </div>
        </c:if>

        <input type="hidden" name="apartmentId" value=${apartment.id}>

        Upload photos: <input type="file" name="photos" multiple><br>
        <input type="submit" class="btn btn-primary" id="sub" value="Save changes">

    </form>

</div>

<script>
    $("select").imagepicker({
        hide_select : false,
        show_label  : false
    })
</script>
</body>
</html>