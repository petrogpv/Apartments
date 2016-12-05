<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%@ page session="true" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--<title>Title</title>--%>
<%--&lt;%&ndash;<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">&ndash;%&gt;--%>

<%--</head>--%>
<%--<body>--%>



<div style="margin-bottom: 10px; ">
    <br><br><br><h4><b>New client</b></h4>

    <form role="form" enctype="multipart/form-data" class="form-horizontal" action="/new_client" method="post">

        <input class="form-control form-group width30" type="text" id="firstName"  name="firstName" required placeholder="first name">
        <input class="form-control form-group width30" type="text" id="lastName" name="lastName" required placeholder="last name">
        <input class="form-control form-group width30" type="text" id="address" name="address" required placeholder="address">
        <input class="form-control form-group width30" type="text" id="eMail" name="eMail" placeholder="e-mail">
        <input class="form-control form-group width30" type="text" id="phoneNumber" name="phoneNumber" required placeholder="phone number">
        <input class="form-control form-group width30" type="number" id="discount" name="discount" placeholder="discount">

        <input type="submit" class="btn btn-primary" id="sub" value="Save client">

    </form>
</div>

