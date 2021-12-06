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

<html>
<head>
    <title>Title</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Table</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="css/styles.css" rel="stylesheet" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>
    <script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.themes.fusion.js"></script>

    <script type="text/javascript">
        var chartData;
        chartData = [
            <c:forEach var="i" begin="1" end="${list.size()}" step="1">
            {
                "label" : "${list.get(list.size()-i).time}",
                "value" : "${list.get(list.size()-i).growth}",
                "tooltip" : "시간 : ${list.get(list.size()-i).date} {br} 생장률(%) : ${list.get(list.size()-i).growth} {br} 조도(Lux) : ${list.get(list.size()-i).illuminance} {br} 세로축 각도 : ${list.get(list.size()-i).verticalAngle} {br} 가로축 각도 : ${list.get(list.size() - i).horizontalAngle}"
            },
            </c:forEach>
        ]

        console.log(chartData)

        chartConfig = {
            type : 'splinearea',
            renderAt : 'chart-container',
            width : '100%',
            height : '400',
            dataFormat : 'json',
            dataSource :{
                "chart" : {
                    "caption" : "오늘의 농작물 정보",
                    "xAxisName" : "시간",
                    "yAxisName" : "생장률",
                    "theme" : "fusion"
                },

                data : chartData
            }
        }

        FusionCharts.ready(function(){
            var fusioncharts = new FusionCharts(chartConfig);
            fusioncharts.render();
        });
    </script>
</head>
<body>
    <div class="card mb-4">
        <div class="card-body">
            <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
                <div class="dataTable-top">
                    <div class="dataTable-dropdown">
                    </div>
                </div>
                <div class="dataTable-container">
                    <table id="datatablesSimple2" class="dataTable-table">
                        <thead>
                        <tr><th data-sortable="" align="center" colspan="4" class = "dataTable-table">오늘의 농작물 분석 정보</th></tr>
                        </thead>
                        <thead>
                        <tr><th data-sortable="" style="width: 25%;">날짜</th><th data-sortable="" style="width: 25%;">생장률 평균(%)</th><th data-sortable="" style="width: 25%;">조도 평균(Lux)</th><th data-sortable="" style="width: 25%;">출하 예측 일자</th></tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td width="120" align="center"><a href="/chd/view/?date=${list.get(0).date}">
                                ${list.get(0).date}</a></td>
                            <td width="120" align="center">${list.get(0).growthAvg}</td>
                            <td width="120" align="center">${list.get(0).illuminanceAvg}</td>
                            <td width="120" align="center">${predictHarvest}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

<div class="col-xl-6">
    <div class="card mb-4">
        <div class="card-header">
            <svg class="svg-inline--fa fa-chart-area fa-w-16 me-1" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="chart-area" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" data-fa-i2svg=""><path fill="currentColor" d="M500 384c6.6 0 12 5.4 12 12v40c0 6.6-5.4 12-12 12H12c-6.6 0-12-5.4-12-12V76c0-6.6 5.4-12 12-12h40c6.6 0 12 5.4 12 12v308h436zM372.7 159.5L288 216l-85.3-113.7c-5.1-6.8-15.5-6.3-19.9 1L96 248v104h384l-89.9-187.8c-3.2-6.5-11.4-8.7-17.4-4.7z"></path></svg><!-- <i class="fas fa-chart-area me-1"></i> Font Awesome fontawesome.com -->날짜 별 농작물 분석 정보</div>
        <div id="chart-container">FusionCharts XT will load here!</div>
        <div class="card-footer small text-muted">Updated </div>
    </div>
</div>
<div class="container-fluid px-5" style="margin-top: 75px;">
    <div class="card mb-4">
        <div class="card-body">
            <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns"><div class="dataTable-top"><div class="dataTable-dropdown"></div></div><div class="dataTable-container">
                    <table id="datatablesSimple2" class="dataTable-table">
                        <tr>
                            <th colspan="5">
                                <h1>${list.get(0).date}</h1>
                            </th>
                        </tr>
                            <th width="120" align="center">시간</th>
                            <th width="120" align="center">생장률</th>
                            <th width="120" align="center">조도</th>
                            <th width="120" align="center">세로축 각도</th>
                            <th width="120" align="center">가로축 각도</th>
                        </tr>
                        </thead>
                    <tbody>
                    <c:forEach items="${list}" var="list">
                        <tr>
                            <td width="120" align="center"><a href="/chd/view/${list.no}" data-title="${list.time} 농작물 수직 이미지" data-lightbox="example-set">${list.time}</a>
                                <a style="display:none;" /></td>
                            <td width="120" align="center">${list.growth}</td>
                            <td width="120" align="center">${list.illuminance}</td>
                            <td width="120" align="center">${list.verticalAngle}</td>
                            <td width="120" align="center">${list.horizontalAngle}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
