
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Apartment</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value="/css/msg.css" />" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>


    <style>
        td, th {
            -webkit-touch-callout: none; /* iOS Safari */
            -webkit-user-select: none;   /* Chrome/Safari/Opera */
            -khtml-user-select: none;    /* Konqueror */
            -moz-user-select: none;      /* Firefox */
            -ms-user-select: none;       /* IE/Edge */
            user-select: none;
            cursor: default/* non-prefixed version, currently
                                 not supported by any browser */
        }
        td {
            height: 40px;
            width: 50px;
            text-align: center;
            vertical-align: top;
            padding-top: 10px;
        }
        .price {
            font-size: 10px;
            color: blue;
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
            var inputs = document.getElementsByTagName("input");
            for(var i = 0; i < inputs.length; i++) {
                inputs[i].onclick =
                        function(input){
                            return function() {
                                inputOnclick(input);
                            };
                        }(inputs[i]);
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
        function inputOnclick(input) {
            input.checked = !input.checked;
            return false;
        }


        function calendar() {
            var monthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
            var date = new Date(${date});
            var availableMonth =  ${availableMonth};
            var forward = ${forward};
            var backward = ${backward};


            thisDay = ${today};
            year =  date.getYear();
            if (year < 2000)
                year = year + 1900;
            nDays = date.getDate();
            firstDay = date;
            firstDay.setDate(1);
//        testMe = firstDay.getDate();
//        if (testMe == 2)
//            firstDay.setDate(0);
            startDay = firstDay.getDay();
            startDay --;


            if (startDay < 0) startDay = 6;
            document.writeln("<div align = 'center'>");

            document.write("<form action='/apartment' id='form'  method='post'> ");
            document.write("<input type='hidden' name='apartmentId' value=${apartment.id}>");
            document.write("<input type='hidden' name='date' value=${date}>");
            document.write("<table border = '3'>" );
            document.write("<tr style = 'outline: thin solid black;'><th style ='border: transparent'>");

            if(backward!=-1){
                   document.write("<input type='submit' style='width: 100%' name='sub' value='<'>");

        }

            document.write("<th colspan='5' style ='border: transparent'>");
            document.write(monthNames.substring( date.getMonth() * 3, ( date.getMonth() + 1) * 3));
            document.write("  ");
            document.write(year);
            document.write("<th style ='border: transparent'>");

            if(forward!=-1){
                    document.write("<input type='submit' style='width: 100%' name='sub' value='>'>");

                }

            if(availableMonth == -1){
                document.write("<tr><td colspan='7'>");
                document.write("<c:if test="${not empty message}">");
                document.write("<div class='msg'>${message}</div>");
                document.write("</c:if>");
            }
                    else {
                var prices = JSON.parse("${prices}");
                var bookings = JSON.parse("${bookings}");

                document.write("<tr> <th>Mon <th>Tue <th>Wed " +
                        "<th>Thu <th>Fri <th><span style='color: red'> Sat</span> " +
                        "<th><span style='color: red'> Sun</span>");
                document.write("<tr>");
                column = 0;
                for (i = 0; i < startDay; i++) {
                    document.write("<td>");
                    column++;
                }
                for (i = 1; i <= nDays; i++) {
                    if (i < thisDay && thisDay != 0) {
                        document.write("<td style='background-color:#e6e6db'>");
                        document.write(i + "<br>");
//                    document.write("<span class='price'>"+prices[i]+"</span>");
                    } else {
                        if (prices[i] == -1)
                            document.write("<td style='background-color:#fd7672' >");
                        else {
                            if (bookings[i] != -1) {
                                if (i != thisDay)
                                    document.write("<td>");
                                else
                                    document.write("<td style = 'border: 2px solid red;'>");
                                if (prices[i] != 0)
                                    document.write("<input type='checkbox' style='opacity:0; position:absolute;' name='bookings' value='" + i + "'>");
                            }
                            else {
                                if (i != thisDay)
                                    document.write("<td style='background-color:#a2ff41'>");
                                else
                                    document.write("<td style='border: 2px solid red; background-color:#a2ff41'>");
                                if (prices[i] != 0)
                                    document.write("<input type='checkbox' checked style='opacity:0; position:absolute;' name='bookings' value='" + i + "'>");
                            }
                        }
                        document.write(i + "<br>");
                        document.write("<span class='price'>" + prices[i] + "</span>");
                    }
                    column++;
                    if (column == 7 && i != nDays) {
                        document.write("<tr>");
                        column = 0;
                    }
                }
                if (column != 7) {
                    while (column < 7) {
                        document.write("<td class = 'standart_size'>");
                        column++;
                    }
                }
            }
            document.write("</table>");
            document.write("<input type='submit' style='width: 220px; margin-top: 20px' name='sub' value='Discard changes'>");
            document.write("<br><input type='submit' style='width: 220px; margin-top: 20px' name='sub' value='Save and go to other apartment'>");
            document.write("<br><input type='submit'  style='width: 220px; margin-top: 20px' name='sub' value='Save and go to chart'>");
            document.write("</form>");

            document.writeln("</div>");
        }

//        window.onbeforeunload = confirmExit;
//        function confirmExit()
//        {
//            return "You have attempted to leave this page.  If you have made any changes to the fields without clicking the Save button, your changes will be lost.  Are you sure you want to exit this page?";
//        }
    </script>
</head>
<body>
<div class="container">

    <h3>Apartment page</h3>
    <jsp:include page="/WEB-INF/pages/navbar.jsp" />

<table class="table table-striped">
    <thead >
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

    <script>
        calendar();

        $(function()
        {
            onload();
        });
    </script>
</div>

</body>
</html>



<%--//            <form action="/test" method="post" name="myform">--%>
    <%--//--%>
    <%--//<input type="hidden" id="bool" name="bool" value="" />--%>
    <%--//--%>
    <%--//<input type="button" value="Yes" onclick="test()" />--%>
    <%--//<input type="button" value="No" onclick="test1()" />--%>
    <%--//--%>
    <%--//</form>--%>
<%--//--%>
<%--//<script type="text/javascript">--%>
<%--//        function test() {--%>
<%--//        document.getElementById('bool').value = "true";--%>
<%--//        document.myform.submit();--%>
<%--//        }--%>
<%--//        function test1() {--%>
<%--//        document.getElementById('bool').value = "false";--%>
<%--//        document.myform.submit();--%>
<%--//        }--%>
<%--//</script>--%>
