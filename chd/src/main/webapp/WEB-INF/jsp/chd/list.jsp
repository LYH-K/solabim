<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<html>
    <head>
        <title>농작물 분석 정보 목록</title>
    </head>
    <body>
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

        <div id="table" align="center"></div>



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
