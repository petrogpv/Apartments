<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page session="true" %>--%>
<%--<html>--%>
<%--<head>--%>
    <%--<title>Title</title>--%>
    <%--&lt;%&ndash;<link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">&ndash;%&gt;--%>

<%--</head>--%>
<%--<body>--%>

<div style="margin-bottom: 10px; ">
    <a href="/" class="btn btn-sm btn-default" style="margin-right: 10px;" role = button >Home</a>
    <a href="/" style="margin-right: 10px; ">Home</a>
    <div  style=" float: right" >
        <a href="<c:url value="/chart" />" style="margin-right: 20px">Chart(${sessionScope.chartQuantity})</a>
        <strong style="font-size: 14px"><sec:authentication property="principal.username" /></strong> |
        <a href="<c:url value="/logout" />" >Logout</a>
    </div>
</div>

