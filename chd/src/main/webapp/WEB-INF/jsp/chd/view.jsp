<%--
  Created by IntelliJ IDEA.
  User: sdm05
  Date: 2021-11-27
  Time: 오후 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>


<html>
    <head>
        <title>Title</title>
    </head>
    <body>

    <table border="2" align="center">
        <tr>
            <th colspan="5">
                <h1>시간별 농작물 환경 및 분석정보 </h1>
            </th>
        </tr>
        <tr>
            <th colspan="5">
                <h1>${list.get(0).date}</h1>
            </th>
        </tr>
        <tr>
            <th width="120" align="center">시간</th>
            <th width="120" align="center">생장률</th>
            <th width="120" align="center">조도</th>
            <th width="120" align="center">세로축 각도</th>
            <th width="120" align="center">가로축 각도</th>
        </tr>

        <c:forEach items="${list}" var="list">
            <tr>
                <td width="120" align="center"><a href="/chd/view/${list.no}" data-title="${list.time} 농작물 수직 이미지" data-lightbox="example-set">${list.time}</a>
                    <a style="display:none;"/></td>
                <td width="120" align="center">${list.growth}</td>
                <td width="120" align="center">${list.illuminance}</td>
                <td width="120" align="center">${list.verticalAngle}</td>
                <td width="120" align="center">${list.horizontalAngle}</td>
            </tr>
        </c:forEach>
    </table>

    </body>
</html>
