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
    <title>농작물 상세 보기</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400;0,500;0,600;0,700;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet">

    <title>Table</title>
    <style type="text/css">svg:not(:root).svg-inline--fa {
        overflow: visible
    }

    .svg-inline--fa {
        display: inline-block;
        font-size: inherit;
        height: 1em;
        overflow: visible;
        vertical-align: -.125em
    }

    .svg-inline--fa.fa-w-16 {
        width: 1em
    }

    .svg-inline--fa.fa-fw {
        width: 1.25em
    }

    .fa-layers svg.svg-inline--fa {
        bottom: 0;
        left: 0;
        margin: auto;
        position: absolute;
        right: 0;
        top: 0
    }

    .fa-layers svg.svg-inline--fa {
        -webkit-transform-origin: center center;
        transform-origin: center center
    }

    .fa-fw {
        text-align: center;
        width: 1.25em
    }

    .fa-ul > li {
        position: relative
    }

    @-webkit-keyframes fa-spin {
        0% {
            -webkit-transform: rotate(0);
            transform: rotate(0)
        }
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg)
        }
    }

    @keyframes fa-spin {
        0% {
            -webkit-transform: rotate(0);
            transform: rotate(0)
        }
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg)
        }
    }

    .hy {
        text-decoration:none;
        font-weight: bold;
        color: #20c997;
    }

    .hy:hover {
        color: #ffcd39;
        font-weight: 1200;
        -webkit-transform: scale(1.2,1.2);
        -moz-transform: scale(1.2,1.2);
        -o-transform: scale(1.2,1.2);
        -ms-transform: scale(1.2,1.2);
        transform: scale(1.2,1.2);
    }

    </style>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet">
    <link href="/chd/css/styles.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>
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
                "tooltext" : "시간 : ${list.get(list.size()-i).time} {br} 생장률(%) : ${list.get(list.size()-i).growth} {br} 조도(Lux) : ${list.get(list.size()-i).illuminance} {br} 세로축 각도 : ${list.get(list.size()-i).verticalAngle} {br} 가로축 각도 : ${list.get(list.size() - i).horizontalAngle}"
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
                    "caption": "${list.get(0).date} 농작물 정보", //차트 제목
                    "captionFont" : "Lora",       //차트 폰트
                    "captionFontSize" : 20,       //차트 폰트 크기
                    "xAxisName": "시간",
                    "xAxisNameFont" : "Lora",
                    "xAxisNameFontSize" : 15,

                    "yAxisName": "생장률",         //Y축의 이름
                    "yAxisNameFont" : "Lora",     //생장률 폰트
                    "yAxisNameFontSize" : 15,     // 생장률 폰트 크기
                    "yAxisValueFontSize" :  13,   // Y축
                    "labelFont" : "Lora",
                    "labelFontSize" : 13,
                    "yAxisMaxValue" : 100,        //생장률 값의 최대값
                    "yAxisMinValue" : 0,          //생장률 값의 최대값

                    "vDivLineThickness" : 13,
                    "anchorHoverAnimation" : true,
                    "anchorHoverRadius" : 10,
                    "valueBorderRadius" : 13,
                    "valueFontSize" : 13,

                    "plotFillColor" : "#147814",
                    "toolTipBorderColor" : "#78EFAD",  //툴팁 테두리
                    "canvasBorderThickness" : .1,
                    "anchorBorderColor" : "#147814",
                    "anchorBgColor" : "#147814",
                    "showAlternateHGridColor" : false,
                    "theme": "fusion",
                },
                legend: {
                    display: false
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
<body style="font-family: Lora; font-weight: 700">
<div>
    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-success">
        <a class="navbar-brand ps-3" href="/chd/list">Crop</a>
        <!-- Navbar Search-->
        <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
        </form>
        <!-- Navbar-->
        <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><svg class="svg-inline--fa fa-user fa-w-14 fa-fw" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="user" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512" data-fa-i2svg=""><path fill="currentColor" d="M224 256c70.7 0 128-57.3 128-128S294.7 0 224 0 96 57.3 96 128s57.3 128 128 128zm89.6 32h-16.7c-22.2 10.2-46.9 16-72.9 16s-50.6-5.8-72.9-16h-16.7C60.2 288 0 348.2 0 422.4V464c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48v-41.6c0-74.2-60.2-134.4-134.4-134.4z"></path></svg><!-- <i class="fas fa-user fa-fw"></i> Font Awesome fontawesome.com --></a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="/chd/logout">Logout</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</div>
<div class="card-body">
    <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
        <div class="dataTable-top">
            <div class="dataTable-dropdown"></div>
        </div>
        <div class="dataTable-container">
            <table id="datatablesSimple2" class="dataTable-table">
                <thead>
                    <tr>
                        <th data-sortable="" align="center" colspan="4" class="dataTable-table">${list.get(0).date} 농작물 분석 정보</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th data-sortable="" style="width: 25%;">날짜</th>
                        <th data-sortable="" style="width: 25%;">생장률 평균(%)</th>
                        <th data-sortable="" style="width: 25%;">조도 평균(Lux)</th>
                    </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="120" align="center">${list.get(0).date}</td>
                    <td width="120" align="center">${dateAverage.growthAvg}</td>
                    <td width="120" align="center">${dateAverage.illuminanceAvg}</td>
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
            <svg class="svg-inline--fa fa-chart-area fa-w-16 me-1" aria-hidden="true" focusable="false" data-prefix="fas" data-icon="chart-area" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" data-fa-i2svg=""><path fill="currentColor" d="M500 384c6.6 0 12 5.4 12 12v40c0 6.6-5.4 12-12 12H12c-6.6 0-12-5.4-12-12V76c0-6.6 5.4-12 12-12h40c6.6 0 12 5.4 12 12v308h436zM372.7 159.5L288 216l-85.3-113.7c-5.1-6.8-15.5-6.3-19.9 1L96 248v104h384l-89.9-187.8c-3.2-6.5-11.4-8.7-17.4-4.7z"></path></svg><!-- <i class="fas fa-chart-area me-1"></i> Font Awesome fontawesome.com -->시간 별 농작물 분석 정보</div>
        <div id="chart-container">FusionCharts XT will load here!</div>
    </div>
</div>

<div class="card mb-4">
    <div class="card-body">
        <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
            <div class="dataTable-top">
                <div class="dataTable-dropdown">
                </div>
            </div>
            <div class="dataTable-container">
                <table id="datatablesSimple" class="dataTable-table"  align="center">
                    <thead>
                        <tr>
                            <th colspan="5">
                                <p style="font-size:20px;">${list.get(0).date}</p>
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
                            <td width="120" align="center"><a href="/chd/view/${list.no}" class="hy" data-title="${list.time} 농작물 수직 이미지" data-lightbox="example-set">${list.time}</a>
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
    <div class="d-flex align-items-center justify-content-between small">
        <div class="text-muted">Copyright © Your Website 2021</div>
        <div>
            <a href="#" class="hy">Privacy Policy</a>
            <a href="#" class="hy">Terms &amp; Conditions</a>
        </div>
    </div>
</body>
</html>
