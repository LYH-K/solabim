<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en" class="fontawesome-i2svg-active fontawesome-i2svg-complete">
<head>
    <meta charset="utf-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400;0,500;0,600;0,700;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet">

    <title>농작물 정보</title>
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

    .hay{
        text-decoration:none;
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

    <script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>
    <script type="text/javascript"
            src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.themes.fusion.js"></script>
    <script type="text/javascript">
        let chartData;
        chartData = [
            <c:forEach var="i" begin="1" end="${top.size()}" step="1">
            {
                "label": "${top.get(top.size()-i).date}",
                "value": "${top.get(top.size()-i).growthAvg}",
                "tooltext": "날짜 : ${top.get(top.size()-i).date} {br} 생장률(%) : ${top.get(top.size()-i).growthAvg} {br} 조도(Lux) : ${top.get(top.size()-i).illuminanceAvg}"
            },
            </c:forEach>
        ]

        chartConfig = {
            type: 'splinearea',
            renderAt: 'chart-container',
            width: '100%',
            height: '400',
            dataFormat: 'json',
            dataSource: {
                data: chartData,
                "chart": {
                    "caption": "오늘의 농작물 정보", //차트 제목
                    "captionFont" : "Lora",       //차트 폰트
                    "captionFontSize" : 20,       //차트 폰트 크기
                    "xAxisName": "날짜",
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
                    "canvasBorderColor" : "#ffffff",
                    "showAlternateHGridColor" : false,
                    "theme": "fusion",
                }
            }, legend: {
                display: false
            }
        }

        FusionCharts.ready(function () {
            var fusioncharts = new FusionCharts(chartConfig);
            fusioncharts.render();
        });

        var pageNo = 0;

        function initPage() {
            pageNo = 0;
        }

        function changePage(pageButtonId) {
            pageNo = parseInt(pageButtonId);

            search();
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/dates",
            type: "POST",
            data: JSON.stringify(searchKeyword),
            headers: {"Content-Type": "application/json;charset=UTF-8"},
            success: function (rows) {
                drawTable(rows);
            }
        })
    </script>
</head>
<body class="sb-nav-fixed" style="font-family: Lora; font-weight: 700">

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

<div class="container-fluid px-5" style="margin-top: 50px;">
    <div class="card mb-4">

        <div class="card-body">
            <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
                <div class="dataTable-top">
                    <div class="dataTable-dropdown"></div>
                </div>
                <div class="dataTable-container">
                    <table  class="dataTable-table">
                        <thead>
                        <tr>
                            <th data-sortable="" align="center" colspan="4" class="dataTable-table">오늘의 농작물 분석 정보</th>
                        </tr>
                        </thead>
                        <thead>
                        <tr>
                            <th data-sortable="" style="width: 25%;">날짜</th>
                            <th data-sortable="" style="width: 25%;">생장률 평균(%)</th>
                            <th data-sortable="" style="width: 25%;">조도 평균(Lux)</th>
                            <th data-sortable="" style="width: 25%;">출하 예측 일자</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td width="120" align="center"><a href="/chd/view/?date=${list.get(0).date}" class="hy" >
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
                <svg class="svg-inline--fa fa-chart-area fa-w-16 me-1" aria-hidden="true" focusable="false"
                     data-prefix="fas" data-icon="chart-area" role="img" xmlns="http://www.w3.org/2000/svg"
                     viewBox="0 0 512 512" data-fa-i2svg="">
                    <path fill="currentColor"
                          d="M500 384c6.6 0 12 5.4 12 12v40c0 6.6-5.4 12-12 12H12c-6.6 0-12-5.4-12-12V76c0-6.6 5.4-12 12-12h40c6.6 0 12 5.4 12 12v308h436zM372.7 159.5L288 216l-85.3-113.7c-5.1-6.8-15.5-6.3-19.9 1L96 248v104h384l-89.9-187.8c-3.2-6.5-11.4-8.7-17.4-4.7z"></path>
                </svg><!-- <i class="fas fa-chart-area me-1"></i> Font Awesome fontawesome.com -->날짜 별 농작물 분석 정보
            </div>
            <div id="chart-container">FusionCharts XT will load here!</div>
            <div class="card-footer small text-muted">최신 날짜 : ${list.get(0).date}</div>
        </div>
    </div>
    <div class="card mb-4">
        <div class="card-header">
            <svg class="svg-inline--fa fa-table fa-w-16 me-1" aria-hidden="true" focusable="false" data-prefix="fas"
                 data-icon="table" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" data-fa-i2svg="">
                <path fill="currentColor"
                      d="M464 32H48C21.49 32 0 53.49 0 80v352c0 26.51 21.49 48 48 48h416c26.51 0 48-21.49 48-48V80c0-26.51-21.49-48-48-48zM224 416H64v-96h160v96zm0-160H64v-96h160v96zm224 160H288v-96h160v96zm0-160H288v-96h160v96z"></path>
            </svg><!-- <i class="fas fa-table me-1"></i> Font Awesome fontawesome.com -->날짜 별 농작물 분석 정보
        </div>
        <div class="card-body">
            <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
                <div class="dataTable-top">
                    <div class="dataTable-dropdown"/>
                    <div class="dataTable-search">
                        <input class="dataTable-input" placeholder="ex) ${list.get(0).date}" type="text" id="search">
                        <button class="btn btn-primary" id="btnNavbarSearch" type="button" onclick="sendData()"
                                style=";height: 38px;margin-bottom: 2px;margin-left: 30px;">
                            <svg class="svg-inline--fa fa-search fa-w-16" aria-hidden="true" focusable="false"
                                 data-prefix="fas" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg"
                                 viewBox="0 0 512 512" data-fa-i2svg="">
                                <path fill="currentColor"
                                      d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"/>
                            </svg><!-- <i class="fas fa-search"></i> Font Awesome fontawesome.com -->
                        </button>
                    </div>
                </div>
            </div>
            <div class="dataTable-container">
                <table id="datatablesSimple" class="dataTable-table">
                    <thead>
                    <tr>
                        <th data-sortable="" style="width: 10%;">번호</th>
                        <th data-sortable="" style="width: 30%;">날짜</th>
                        <th data-sortable="" style="width: 30%;">생장률 평균(%)</th>
                        <th data-sortable="" style="width: 30%;">조도(Lux)</th>
                    </tr>
                    </thead>
                    <tbody id="table" class="hay">
                    <c:forEach items="${list}" var="list" varStatus="no">
                        <tr>
                            <td>
                                    ${no.count}
                            </td>
                            <td>
                                <a class="hy" href="/chd/view/?date=${list.date}">${list.date}</a>
                            </td>
                            <td>
                                    ${list.growthAvg}
                            </td>
                            <td>
                                    ${list.illuminanceAvg}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div style="display: block; text-align: center;">
                <div class="dataTable-info">Showing 1 to 10 of ${total} entries</div>
                <!-- -->
            </div>
            <div class="input-group">
            </div>

            <nav class="dataTable-pagination">
                <ul id="navigator" class="dataTable-pagination-list">
                </ul>
            </nav>
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

<script>
    var pageNo = 0;

    var xmlRequest;

    var menu;

    sendData();

    function initPage() {
        pageNo = 0;
    }

    function changePage(pageButtonId) {
        pageNo = parseInt(pageButtonId);

        sendData();
    }

    function sendData() {
        xmlRequest = new XMLHttpRequest();

        var request = document.getElementById('search').value;


        if (request == null) {
            request = "";
        }

        let requestJson = {"date": request, "pageNo": pageNo.toString()};

        console.log("-------->" + JSON.stringify(requestJson));

        xmlRequest.open("POST", "/chd/list", true);
        xmlRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        xmlRequest.send(JSON.stringify(requestJson));

        xmlRequest.onreadystatechange = getData;

    }

    function getData() {

        var text = xmlRequest.responseText;
        var json = JSON.parse(text);//문자열을 제이슨으로 변형
        var cropAverages = json.cropAverages;

        console.log("응답 json : " + text);

        var tag = "<table border='2'>"
        for (var i = 0; i < cropAverages.length; i++) {
            tag +=
                "<tr>" + "<td align = center>" + (pageNo * 10 + i + 1) + "</td>"
                + "<td align = center><a class='hy' href='/chd/view/?date=" + cropAverages[i].date + "'>"
                + cropAverages[i].date + "</a></td><td align = center>"
                + cropAverages[i].growthAvg + "</td><td align = center>"
                + cropAverages[i].illuminanceAvg + "</td></tr >";
        }

        tag += "</table>";
        document.getElementById("table").innerHTML = tag;

        document.getElementById("navigator").innerHTML = json.navigator;
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
</body>
</html>