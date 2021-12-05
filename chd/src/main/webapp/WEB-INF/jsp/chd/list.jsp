<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>농작물 분석 정보 목록</title>
        <script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/fusioncharts.js"></script>
        <script type="text/javascript" src="https://cdn.fusioncharts.com/fusioncharts/latest/themes/fusioncharts.themes.fusion.js"></script>
        <script type="text/javascript">
            const chartData =[
                {
                    "label" : "${list.get(9).date}",
                    "value" : "${list.get(9).growthAvg}",
                    "tooltext" : "시간 : ${list.get(9).date} {br} 생장률(%) : ${list.get(9).growthAvg} {br} 조도(Lux) : ${list.get(9).illuminanceAvg}"},

                { "label" : "${list.get(8).date}",
                    "value" : "${list.get(8).growthAvg}",
                    "tooltext" : "시간 : ${list.get(8).date} {br} 생장률(%) : ${list.get(8).growthAvg} {br} 조도(Lux) : ${list.get(8).illuminanceAvg}"},

                { "label" : "${list.get(7).date}",
                    "value" : "${list.get(7).growthAvg}",
                    "tooltext" : "시간 : ${list.get(7).date} {br} 생장률(%) : ${list.get(7).growthAvg} {br} 조도(Lux) : ${list.get(7).illuminanceAvg}"},

                { "label" : "${list.get(6).date}",
                    "value" : "${list.get(6).growthAvg}",
                    "tooltext" : "시간 : ${list.get(6).date} {br} 생장률(%) : ${list.get(6).growthAvg} {br} 조도(Lux) : ${list.get(6).illuminanceAvg}"},

                { "label" : "${list.get(5).date}",
                    "value" : "${list.get(5).growthAvg}",
                    "tooltext" : "시간 : ${list.get(5).date} {br} 생장률(%) : ${list.get(5).growthAvg} {br} 조도(Lux) : ${list.get(5).illuminanceAvg}"},

                { "label" : "${list.get(4).date}",
                    "value" : "${list.get(4).growthAvg}",
                    "tooltext" : "시간 : ${list.get(4).date} {br} 생장률(%) : ${list.get(4).growthAvg} {br} 조도(Lux) : ${list.get(4).illuminanceAvg}"},

                { "label" : "${list.get(3).date}",
                    "value" : "${list.get(3).growthAvg}",
                    "tooltext" : "시간 : ${list.get(3).date} {br} 생장률(%) : ${list.get(3).growthAvg} {br} 조도(Lux) : ${list.get(3).illuminanceAvg}"},

                { "label" : "${list.get(2).date}",
                    "value" : "${list.get(2).growthAvg}",
                    "tooltext" : "시간 : ${list.get(2).date} {br} 생장률(%) : ${list.get(2).growthAvg} {br} 조도(Lux) : ${list.get(2).illuminanceAvg}"},

                { "label" : "${list.get(1).date}",
                    "value" : "${list.get(1).growthAvg}",
                    "tooltext" : "시간 : ${list.get(1).date} {br} 생장률(%) : ${list.get(1).growthAvg} {br} 조도(Lux) : ${list.get(1).illuminanceAvg}"},

                { "label" : "${list.get(0).date}",
                "value" : "${list.get(0).growthAvg}",
                "tooltext" : "시간 : ${list.get(0).date} {br} 생장률(%) : ${list.get(0).growthAvg} {br} 조도(Lux) : ${list.get(0).illuminanceAvg}"}
            ],

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
                }, options: {
                    tooltips : {
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
                            text:"생장률(%)",
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
            FusionCharts.ready(function(){
                var fusioncharts = new FusionCharts(chartConfig);
                fusioncharts.render();
            });
        </script>
    </head>
    <body>
    <div id="chart-container">FusionCharts XT will load here!</div>
        <table border="2" align="center">
            <tr>
                <th colspan="4">
                    <h1>오늘의 농작물 분석 정보</h1>
                </th>
            </tr>
            <tr>
                <th width="120" align="center">날짜</th>
                <th width="120" align="center">조도 평균(LUX)</th>
                <th width="120" align="center">생장률 분석(%)</th>
                <th width="120" align="center">출하 예측 일자</th>
            </tr>
            <tr>
                <td width="120" align="center"><a href='/chd/view/?date=${list.get(0).date}'>
                ${list.get(0).date}</a></td>
                <td width="120" align="center">${list.get(list.size()-1).illuminanceAvg}</td>
                <td width="120" align="center">${list.get(list.size()-1).growthAvg}</td>
                <td width="120" align="center">${predictHarvest}</td>
            </tr>
        </table>

        <table border="2" id="old" align="center">
            <tr>
                <td>
                    <input id="search" value="YYYY.MM.DD" />
                </td>
                <td>
                    <button type="button"  onclick="sendData()" >검색</button>
                </td>
            </tr>
        </table>

        <div id="table" align="center">

            <table border="2" align="center">
                <tr>
                    <th width="120" align="center">NO</th>
                    <th width="120" align="center">날짜</th>
                    <th width="120" align="center">조도 평균(LUX)</th>
                    <th width="120" align="center">생장률 분석(%)</th>
                </tr>

                <c:forEach items="${list}" var="list" varStatus="no" >
                    <tr>
                        <td width="120" align="center">${no.count}</td>
                        <td width="120" align="center"><a href='/chd/view/?date=${list.date}'>
                        ${list.date}</a></td>
                        <td width="120" align="center">${list.illuminanceAvg}</td>
                        <td width="120" align="center">${list.growthAvg}</td>
                    </tr>
                </c:forEach>

            </table>
        </div>

        <script>
            <%--var list = '${list}';--%>

            function sendData() {
                xmlRequest = new XMLHttpRequest();

                request = document.getElementById('search').value;

                console.log(request);

                xmlRequest.open("GET","/chd/list/?date="+request,true);
                xmlRequest.setRequestHeader("Content-Type","application/json;charset=UTF-8");

                xmlRequest.send();

                xmlRequest.onreadystatechange = getData;
            }

            function getData(){
                if(xmlRequest.status == 200){
                    var text = xmlRequest.responseText;
                    var json = JSON.parse(text);//문자열을 제이슨으로 변형
                }

                var tag = "<table border='2'>"
                    + "<tr><th>NO</th><th>날짜</th><th>조도 평균(Lux)</th><th>생장률 평균(%)</th></tr>"
                for (var i = 0; i < json.length; i++) {
                    tag +=
                        "<tr>" +"<td align = center>"+(i+1)+"</td>"
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
