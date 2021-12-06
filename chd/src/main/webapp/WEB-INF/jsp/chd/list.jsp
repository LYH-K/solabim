<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en" class="fontawesome-i2svg-active fontawesome-i2svg-complete">
<head>
    <meta charset="utf-8">

    <title>Dashboard - SB Admin</title>
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

    .svg-inline--fa.fa-lg {
        vertical-align: -.225em
    }

    .svg-inline--fa.fa-w-1 {
        width: .0625em
    }

    .svg-inline--fa.fa-w-2 {
        width: .125em
    }

    .svg-inline--fa.fa-w-3 {
        width: .1875em
    }

    .svg-inline--fa.fa-w-4 {
        width: .25em
    }

    .svg-inline--fa.fa-w-5 {
        width: .3125em
    }

    .svg-inline--fa.fa-w-6 {
        width: .375em
    }

    .svg-inline--fa.fa-w-7 {
        width: .4375em
    }

    .svg-inline--fa.fa-w-8 {
        width: .5em
    }

    .svg-inline--fa.fa-w-9 {
        width: .5625em
    }

    .svg-inline--fa.fa-w-10 {
        width: .625em
    }

    .svg-inline--fa.fa-w-11 {
        width: .6875em
    }

    .svg-inline--fa.fa-w-12 {
        width: .75em
    }

    .svg-inline--fa.fa-w-13 {
        width: .8125em
    }

    .svg-inline--fa.fa-w-14 {
        width: .875em
    }

    .svg-inline--fa.fa-w-15 {
        width: .9375em
    }

    .svg-inline--fa.fa-w-16 {
        width: 1em
    }

    .svg-inline--fa.fa-w-17 {
        width: 1.0625em
    }

    .svg-inline--fa.fa-w-18 {
        width: 1.125em
    }

    .svg-inline--fa.fa-w-19 {
        width: 1.1875em
    }

    .svg-inline--fa.fa-w-20 {
        width: 1.25em
    }

    .svg-inline--fa.fa-pull-left {
        margin-right: .3em;
        width: auto
    }

    .svg-inline--fa.fa-pull-right {
        margin-left: .3em;
        width: auto
    }

    .svg-inline--fa.fa-border {
        height: 1.5em
    }

    .svg-inline--fa.fa-li {
        width: 2em
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

    .fa-layers {
        display: inline-block;
        height: 1em;
        position: relative;
        text-align: center;
        vertical-align: -.125em;
        width: 1em
    }

    .fa-layers svg.svg-inline--fa {
        -webkit-transform-origin: center center;
        transform-origin: center center
    }

    .fa-layers-counter, .fa-layers-text {
        display: inline-block;
        position: absolute;
        text-align: center
    }

    .fa-layers-text {
        left: 50%;
        top: 50%;
        -webkit-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
        -webkit-transform-origin: center center;
        transform-origin: center center
    }

    .fa-layers-counter {
        background-color: #ff253a;
        border-radius: 1em;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        color: #fff;
        height: 1.5em;
        line-height: 1;
        max-width: 5em;
        min-width: 1.5em;
        overflow: hidden;
        padding: .25em;
        right: 0;
        text-overflow: ellipsis;
        top: 0;
        -webkit-transform: scale(.25);
        transform: scale(.25);
        -webkit-transform-origin: top right;
        transform-origin: top right
    }

    .fa-layers-bottom-right {
        bottom: 0;
        right: 0;
        top: auto;
        -webkit-transform: scale(.25);
        transform: scale(.25);
        -webkit-transform-origin: bottom right;
        transform-origin: bottom right
    }

    .fa-layers-bottom-left {
        bottom: 0;
        left: 0;
        right: auto;
        top: auto;
        -webkit-transform: scale(.25);
        transform: scale(.25);
        -webkit-transform-origin: bottom left;
        transform-origin: bottom left
    }

    .fa-layers-top-right {
        right: 0;
        top: 0;
        -webkit-transform: scale(.25);
        transform: scale(.25);
        -webkit-transform-origin: top right;
        transform-origin: top right
    }

    .fa-layers-top-left {
        left: 0;
        right: auto;
        top: 0;
        -webkit-transform: scale(.25);
        transform: scale(.25);
        -webkit-transform-origin: top left;
        transform-origin: top left
    }

    .fa-lg {
        font-size: 1.3333333333em;
        line-height: .75em;
        vertical-align: -.0667em
    }

    .fa-xs {
        font-size: .75em
    }

    .fa-sm {
        font-size: .875em
    }

    .fa-1x {
        font-size: 1em
    }

    .fa-2x {
        font-size: 2em
    }

    .fa-3x {
        font-size: 3em
    }

    .fa-4x {
        font-size: 4em
    }

    .fa-5x {
        font-size: 5em
    }

    .fa-6x {
        font-size: 6em
    }

    .fa-7x {
        font-size: 7em
    }

    .fa-8x {
        font-size: 8em
    }

    .fa-9x {
        font-size: 9em
    }

    .fa-10x {
        font-size: 10em
    }

    .fa-fw {
        text-align: center;
        width: 1.25em
    }

    .fa-ul {
        list-style-type: none;
        margin-left: 2.5em;
        padding-left: 0
    }

    .fa-ul > li {
        position: relative
    }

    .fa-li {
        left: -2em;
        position: absolute;
        text-align: center;
        width: 2em;
        line-height: inherit
    }

    .fa-border {
        border: solid .08em #eee;
        border-radius: .1em;
        padding: .2em .25em .15em
    }

    .fa-pull-left {
        float: left
    }

    .fa-pull-right {
        float: right
    }

    .fa.fa-pull-left, .fab.fa-pull-left, .fal.fa-pull-left, .far.fa-pull-left, .fas.fa-pull-left {
        margin-right: .3em
    }

    .fa.fa-pull-right, .fab.fa-pull-right, .fal.fa-pull-right, .far.fa-pull-right, .fas.fa-pull-right {
        margin-left: .3em
    }

    .fa-spin {
        -webkit-animation: fa-spin 2s infinite linear;
        animation: fa-spin 2s infinite linear
    }

    .fa-pulse {
        -webkit-animation: fa-spin 1s infinite steps(8);
        animation: fa-spin 1s infinite steps(8)
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

    .fa-rotate-90 {
        -webkit-transform: rotate(90deg);
        transform: rotate(90deg)
    }

    .fa-rotate-180 {
        -webkit-transform: rotate(180deg);
        transform: rotate(180deg)
    }

    .fa-rotate-270 {
        -webkit-transform: rotate(270deg);
        transform: rotate(270deg)
    }

    .fa-flip-horizontal {
        -webkit-transform: scale(-1, 1);
        transform: scale(-1, 1)
    }

    .fa-flip-vertical {
        -webkit-transform: scale(1, -1);
        transform: scale(1, -1)
    }

    .fa-flip-both, .fa-flip-horizontal.fa-flip-vertical {
        -webkit-transform: scale(-1, -1);
        transform: scale(-1, -1)
    }

    :root .fa-flip-both, :root .fa-flip-horizontal, :root .fa-flip-vertical, :root .fa-rotate-180, :root .fa-rotate-270, :root .fa-rotate-90 {
        -webkit-filter: none;
        filter: none
    }

    .fa-stack {
        display: inline-block;
        height: 2em;
        position: relative;
        width: 2.5em
    }

    .fa-stack-1x, .fa-stack-2x {
        bottom: 0;
        left: 0;
        margin: auto;
        position: absolute;
        right: 0;
        top: 0
    }

    .svg-inline--fa.fa-stack-1x {
        height: 1em;
        width: 1.25em
    }

    .svg-inline--fa.fa-stack-2x {
        height: 2em;
        width: 2.5em
    }

    .fa-inverse {
        color: #fff
    }

    .sr-only {
        border: 0;
        clip: rect(0, 0, 0, 0);
        height: 1px;
        margin: -1px;
        overflow: hidden;
        padding: 0;
        position: absolute;
        width: 1px
    }

    .sr-only-focusable:active, .sr-only-focusable:focus {
        clip: auto;
        height: auto;
        margin: 0;
        overflow: visible;
        position: static;
        width: auto
    }

    .svg-inline--fa .fa-primary {
        fill: var(--fa-primary-color, currentColor);
        opacity: 1;
        opacity: var(--fa-primary-opacity, 1)
    }

    .svg-inline--fa .fa-secondary {
        fill: var(--fa-secondary-color, currentColor);
        opacity: .4;
        opacity: var(--fa-secondary-opacity, .4)
    }

    .svg-inline--fa.fa-swap-opacity .fa-primary {
        opacity: .4;
        opacity: var(--fa-secondary-opacity, .4)
    }

    .svg-inline--fa.fa-swap-opacity .fa-secondary {
        opacity: 1;
        opacity: var(--fa-primary-opacity, 1)
    }

    .svg-inline--fa mask .fa-primary, .svg-inline--fa mask .fa-secondary {
        fill: #000
    }

    .fad.fa-inverse {
        color: #fff
    }</style>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">


    <script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>
    <script type="text/javascript"
            src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.themes.fusion.js"></script>
    <script type="text/javascript">
        var chartData;
        for (let i = 0; i < ${list.size()}; i++) {
            chartData = [
                <c:forEach items="${list}" var="list">
                {
                    "label": "${list.date}",
                    "value": "${list.growthAvg}",
                    "tooltip": "시간 : ${list.date} {br} 생장률(%) : ${list.growthAvg} {br} 조도(Lux) : ${list.illuminanceAvg}"
                },
                </c:forEach>
            ]
        }

        console.log(chartData)

        chartConfig = {
            type: 'splinearea',
            renderAt: 'chart-container',
            width: '100%',
            height: '400',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "오늘의 농작물 정보",
                    "xAxisName": "시간",
                    "yAxisName": "생장률",
                    "theme": "fusion"
                },

                data: chartData
            }, options: {
                tooltips: {
                    displayColors: false
                }
            },
            scales: {
                xAxes: [{
                    time: {
                        unit: 'date'
                    },
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        maxTicksLimit: 10
                    }
                }],
                yAxes: [{
                    ticks: {
                        text: "생장률(%)",
                        min: 0,
                        max: 100,
                        maxTicksLimit: 11
                    },
                    gridLines: {
                        color: "rgb(178, 195, 152)",
                    }
                }],
            },
            legend: {
                display: false
            }
        }

        FusionCharts.ready(function () {
            var fusioncharts = new FusionCharts(chartConfig);
            fusioncharts.render();
        });
    </script>
</head>


<body class="sb-nav-fixed">
<div>

    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
        <!-- Navbar Brand-->
        <a class="navbar-brand ps-3">Crop</a>
        <!-- Sidebar Toggle-->

        <!-- Navbar Search-->
        <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">

        </form>
        <!-- Navbar-->
        <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown"
                   aria-expanded="false">
                    <svg class="svg-inline--fa fa-user fa-w-14 fa-fw" aria-hidden="true" focusable="false"
                         data-prefix="fas" data-icon="user" role="img" xmlns="http://www.w3.org/2000/svg"
                         viewBox="0 0 448 512" data-fa-i2svg="">
                        <path fill="currentColor"
                              d="M224 256c70.7 0 128-57.3 128-128S294.7 0 224 0 96 57.3 96 128s57.3 128 128 128zm89.6 32h-16.7c-22.2 10.2-46.9 16-72.9 16s-50.6-5.8-72.9-16h-16.7C60.2 288 0 348.2 0 422.4V464c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48v-41.6c0-74.2-60.2-134.4-134.4-134.4z"></path>
                    </svg><!-- <i class="fas fa-user fa-fw"></i> Font Awesome fontawesome.com --></a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="/chd/logout">Logout</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</div>
<div class="container-fluid px-5" style="margin-top: 75px;">
    <div class="card mb-4">

        <div class="card-body">
            <div class="dataTable-wrapper dataTable-loading no-footer sortable searchable fixed-columns">
                <div class="dataTable-top">
                    <div class="dataTable-dropdown"></div>
                </div>
                <div class="dataTable-container">
                    <table id="datatablesSimple2" class="dataTable-table">
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
                            <td width="120" align="center"><a href="/chd/view/?date=2021.12.05">
                                2021.12.05</a></td>
                            <td width="120" align="center">30</td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">2021-12-29</td>
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
            <div class="card-footer small text-muted">Updated</div>
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
                    <div class="dataTable-dropdown"></div>
                    <div class="dataTable-search"><input class="dataTable-input" placeholder="ex) 2021.12.02"
                                                         type="text" id="search">
                        <button class="btn btn-primary" id="btnNavbarSearch" type="button" onclick="sendData()"
                                style="heigt=38;height: 38px;margin-bottom: 2px;margin-left: 30px;">
                            <svg class="svg-inline--fa fa-search fa-w-16" aria-hidden="true" focusable="false"
                                 data-prefix="fas" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg"
                                 viewBox="0 0 512 512" data-fa-i2svg="">
                                <path fill="currentColor"
                                      d="M505 442.7L405.3 343c-4.5-4.5-10.6-7-17-7H372c27.6-35.3 44-79.7 44-128C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c48.3 0 92.7-16.4 128-44v16.3c0 6.4 2.5 12.5 7 17l99.7 99.7c9.4 9.4 24.6 9.4 33.9 0l28.3-28.3c9.4-9.4 9.4-24.6.1-34zM208 336c-70.7 0-128-57.2-128-128 0-70.7 57.2-128 128-128 70.7 0 128 57.2 128 128 0 70.7-57.2 128-128 128z"></path>
                            </svg><!-- <i class="fas fa-search"></i> Font Awesome fontawesome.com --></button>
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

                        <tbody id="table">

                        <tr>
                            <td width="120" align="center">1</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.12.05">
                                2021.12.05</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">2</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.12.04">
                                2021.12.04</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">3</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.12.03">
                                2021.12.03</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">4</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.12.02">
                                2021.12.02</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">5</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.12.01">
                                2021.12.01</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">6</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.11.30">
                                2021.11.30</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">7</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.11.29">
                                2021.11.29</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">8</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.11.28">
                                2021.11.28</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">9</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.11.27">
                                2021.11.27</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        <tr>
                            <td width="120" align="center">10</td>
                            <td width="120" align="center"><a href="/chd/view/?date=2021.11.26">
                                2021.11.26</a></td>
                            <td width="120" align="center">357</td>
                            <td width="120" align="center">30</td>
                        </tr>

                        </tbody>

                    </table>
                </div>
                <div class="dataTable-bottom">
                    <div class="dataTable-info">Showing 1 to 10 of 57 entries</div>
                    <nav class="dataTable-pagination">
                        <ul class="dataTable-pagination-list">
                            <li class="active"><a href="#" data-page="1">1</a></li>
                            <li class=""><a href="#" data-page="2">2</a></li>
                            <li class=""><a href="#" data-page="3">3</a></li>
                            <li class=""><a href="#" data-page="4">4</a></li>
                            <li class=""><a href="#" data-page="5">5</a></li>
                            <li class=""><a href="#" data-page="6">6</a></li>
                            <li class="pager"><a href="#" data-page="2">›</a></li>
                        </ul>
                    </nav>
                </div>
                <div class="input-group">
                </div>
            </div>
        </div>
    </div>
</div>
<div class="d-flex align-items-center justify-content-between small">
    <div class="text-muted">Copyright © Your Website 2021</div>
    <div>
        <a href="#">Privacy Policy</a>
        <a href="#">Terms &amp; Conditions</a>
    </div>
</div>

<script>


    function sendData() {
        xmlRequest = new XMLHttpRequest();

        request = document.getElementById('search').value;

        xmlRequest.open("GET", "/chd/list/?date=" + request, true);
        xmlRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        xmlRequest.send();

        xmlRequest.onreadystatechange = getData;

    }

    function getData() {
        if (xmlRequest.status == 200) {
            var text = xmlRequest.responseText;
            var json = JSON.parse(text);//문자열을 제이슨으로 변형
        }

        var tag = "<table border='2'>"
        for (var i = 0; i < json.length; i++) {
            tag +=
                "<tr>" + "<td align = center>" + (i + 1) + "</td>"
                + "<td align = center><a href='/chd/view/?date=" + json[i].date + "'>"
                + json[i].date + "</a></td><td align = center>"
                + json[i].illuminanceAvg + "</td><td align = center>"
                + json[i].growthAvg + "</td></tr >";
        }

        tag += "</table>";
        document.getElementById("table").innerHTML = tag;
    }
</script>

</body>
</html>