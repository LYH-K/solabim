package kr.co.envir.chd.publicdata;

import kr.co.envir.chd.envirmanagement.SunTimeInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SunTimeUtile {
    private static final String WEATHER_SERVICE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo";
    private static final String SERVICE_KEY = "=7BI%2FKhhpzf6YXg813%2BtypHOlSOfZjAUxeLOcw%2BU2eBXoHbeHKwtKcLCz%2BKNrpC8sYPh5VcYDwYXMsdiH%2BRxjpA%3D%3D";
    private static final String LOCAL = "서울";
    private static boolean result = true;

    public static void main(String[] args) throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        boolean resetSignal = SunTimeUtile.resetSignal(localDateTime);
        System.out.println(resetSignal);
        SunTimeInfo sunTimeInfo = SunTimeUtile.isRunMeasuremen(localDateTime);
        System.out.println(sunTimeInfo.getSunSet());
        System.out.println(sunTimeInfo.getSunRise());
    }

    //위치 변경 신호
    public static boolean resetSignal(LocalDateTime localDateTime) throws Exception {
        LocalTime time = LocalTime.of(localDateTime.getHour(), localDateTime.getMinute());

        SunTimeInfo measuremen = isRunMeasuremen(localDateTime);

        result = dayTime(time,measuremen);

        return result;
    }

    //태양광 측정명령
    public static SunTimeInfo isRunMeasuremen(LocalDateTime localDateTime)
            throws Exception {
        localDateTime = LocalDateTime.now();

        String currentDate = String.valueOf(localDateTime.getYear())
                + localDateTime.getMonthValue()
                + localDateTime.getDayOfMonth();

        StringBuffer urlBuffer = new StringBuffer(WEATHER_SERVICE_URL);
        urlBuffer.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuffer.append("&" + URLEncoder.encode("locdate", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(currentDate, StandardCharsets.UTF_8));
        urlBuffer.append("&" + URLEncoder.encode("location", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(LOCAL, StandardCharsets.UTF_8));

        // Used by OkHttp API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlBuffer.toString())
                .addHeader("Content-type", "application/json")
                .build();

        SunTimeInfo sun = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                String xml = response.body().string();
                sun = sunRiseAndSet(xml);
            }else {
                return isRunMeasuremen(localDateTime);
            }
        }

        return sun;
    }

    //공공 데이터 xml문자열 파싱
    private static SunTimeInfo sunRiseAndSet(String xml){
        LocalTime sunriseTime = null;
        LocalTime sunsetTime = null;
        String[] sbs = xml.split("<");

        for(String sun : sbs) {
            if(sun.indexOf("sunrise") == 0) {
                String[] sunriseTimes = sun.split(">");
                String sunrise = sunriseTimes[1];
                int sunriseHours = Integer.parseInt(sunrise.substring(0,2));
                int sunriseMinute = Integer.parseInt(sunrise.substring(2,4));
                sunriseTime = LocalTime.of(sunriseHours, sunriseMinute, 00);
            }

            if(sun.indexOf("sunset") == 0) {
                String[] sunsetTimes = sun.split(">");
                String sunset = sunsetTimes[1];
                int sunsetHour = Integer.parseInt(sunset.substring(0,2));
                int sunsetMinute = Integer.parseInt(sunset.substring(2,4));
                sunsetTime = LocalTime.of(sunsetHour, sunsetMinute, 00);
            }
        }

        SunTimeInfo sun = new SunTimeInfo();
        sun.setSunRise(sunriseTime);
        sun.setSunSet(sunsetTime);

        return sun;
    }

    //낮시간 판단
    private static boolean dayTime(LocalTime time, SunTimeInfo sun) {
        //현재시간이 일출시간이후인가? 그리고 현재시간이  일몰시간 이후 인가?
        if(time.isAfter(sun.getSunRise()) && time.isBefore(sun.getSunSet())){
            return true;
        }
        return false;
    }
}
