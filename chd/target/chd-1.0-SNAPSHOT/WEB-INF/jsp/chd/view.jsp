<%--
  Created by IntelliJ IDEA.
  User: sdm05
  Date: 2021-11-27
  Time: 오후 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
            <th width="120">시간</th>
            <th width="120">생장률</th>
            <th width="120">조도</th>
            <th width="120">세로축 각도</th>
            <th width="120">가로축 각도</th>
        </tr>

        <c:forEach items="${list}" var="list">
            <tr>
                <td width="120">${list.time}</td>
                <td width="120">${list.growth}</td>
                <td width="120">${list.illuminance}</td>
                <td width="120">${list.horizontalAngle}</td>
                <td width="120">${list.verticalAngle}</td>
            </tr>
        </c:forEach>
    </table>

    </body>
</html>
