<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Calendar Upload</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

<style>
    td, th {
        -webkit-touch-callout: none;
        -webkit-user-select: none;
        -html-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        cursor: default

    }
    td {
        height: 40px;
        width: 50px;
        text-align: center;
        vertical-align: top;
        padding-top: 10px;
    }
</style>

<script>
    function onload() {
        var tds = document.getElementsByTagName("td");
        for(var i = 0; i < tds.length; i++) {
            tds[i].onclick =
                    function(td) {
                        return function() {
                            tdOnclick(td);
                        };
                    }(tds[i]);
        }
    }
    function tdOnclick(td) {
        for(var i = 0; i < td.childNodes.length; i++) {
            if(td.childNodes[i].nodeType == 1) {
                if(td.childNodes[i].nodeName == "INPUT") {
                    if(td.childNodes[i].checked) {
                        td.childNodes[i].checked = false;
                        td.style.backgroundColor = "transparent";
                    } else {
                        td.childNodes[i].checked = true;
                        td.style.backgroundColor = "#a2ff41";
                    }
                } else {
                    tdOnclick(td.childNodes[i]);
                }
            }
        }
    }

    function calendar() {
        var monthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
        var date = new Date(${date});

        year =  date.getYear();
        if (year < 2000)
            year = year + 1900;
        nDays = date.getDate();
        firstDay = date;
        firstDay.setDate(1);
        startDay = firstDay.getDay();
//        startDay --;
//        if (startDay < 0) startDay = 6;
        document.writeln("<div>");
        document.write("<table border = '3'>" );
        document.write("<tr><th><span style='color: red'> Sun</span> <th>Mon <th>Tue <th>Wed " +
                "<th>Thu <th>Fri <th><span style='color: red'> Sat</span> ");
        document.write("<tr>");

        column = 0;

        for (i = 0; i < startDay; i++) {
            document.write("<td>");
            column++;
        }
        for (i = 1; i <= nDays; i++) {
            document.write("<td>");
            document.write(i+"<br>");
            document.write("<input type='checkbox' style='opacity:0; position:absolute;' name='holidays' value='" + i + "'>");
//            style='opacity:0; position:absolute;'
            column++;
            if (column == 7) {
                document.write("<tr>");
                column = 0;
            }
        }
        if (column !=7&& column!=0){
            while (column < 7) {
                document.write("<td>");
                column++;
            }
        }
        document.write("</table>");
        document.writeln("</div>");
    }
</script>
</head>
<body onload="onload()">
<div class="container">

    <h3>Calendar upload</h3>
    <jsp:include page="/WEB-INF/pages/navbar.jsp" />

<c:choose>
        <c:when test="${check ne 0}">
            <h3>
                <span>The month pricing for ${monthString} ${year} is alredy exist. Choose other month...</span>
            </h3>
            <input type="submit" class="btn btn-primary" value="Back" onclick="window.location='/calendar_upload_page';" />
        </c:when>
        <c:otherwise>
            <h3>Pricing template upload for ${monthString} ${year}</h3>

            <form action="/admin/calendar_upload" method="post">
                <h5>Default pricing model - all days in month will be priced by Type 1 prices. Choose options below to edit pricing model.</h5>
                <input type="checkbox" name="weekends" value="1">Weekends pricing by Type 2 prices<Br>
                <p>Choose holidays pricing by Type 3 or leave blank:</p>

                <input type="hidden" name="year" value=${year}>
                <input type="hidden" name="month" value=${month}>

                    <script>
                    calendar();
                </script>
                <br>
                <p><input type="submit" class="btn btn-primary" value="Confirm"></p>
            </form>
            <input type="submit" class="btn btn-primary" value="Back" onclick="window.location='/calendar_upload_page';" />
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>