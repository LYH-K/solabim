package kr.co.chd.envir.weatherinfo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimerTask;

public class SunTimeUtil extends TimerTask{
    private static final String WEATHER_SERVICE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo";
    private static final String SERVICE_KEY = "=7BI%2FKhhpzf6YXg813%2BtypHOlSOfZjAUxeLOcw%2BU2eBXoHbeHKwtKcLCz%2BKNrpC8sYPh5VcYDwYXMsdiH%2BRxjpA%3D%3D";
    private static final String LOCAL = "서울";
    public static SunTimeInfo sunTimeInfo;

    //태양광 측정명령
    public static SunTimeInfo searchSunTime(LocalDate localDate)
            throws Exception {
        localDate = LocalDate.now();

        String currentDate = localDate.getYear()+ "" + localDate.getMonthValue() + "" + localDate.getDayOfMonth();

//        StringBuffer urlBuffer = new StringBuffer(WEATHER_SERVICE_URL);
//        urlBuffer.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
//        urlBuffer.append("&" + URLEncoder.encode("locdate", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(currentDate, StandardCharsets.UTF_8));
//        urlBuffer.append("&" + URLEncoder.encode("location", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(LOCAL, StandardCharsets.UTF_8));

        String url = WEATHER_SERVICE_URL + "?serviceKey=7BI%2FKhhpzf6YXg813%2BtypHOlSOfZjAUxeLOcw%2BU2eBXoHbeHKwtKcLCz%2BKNrpC8sYPh5VcYDwYXMsdiH%2BRxjpA%3D%3D" +
                "&locdate=" + URLEncoder.encode(currentDate) + "&location=" + URLEncoder.encode(LOCAL);

        // Used by OkHttp API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .addHeader("Content-type", "application/json")
                .build();

        SunTimeInfo sun = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                String xml = response.body().string();
                sun = sunRiseAndSet(xml);
            } else {
                return searchSunTime(localDate);
            }
        }

        sunTimeInfo = sun;

        return sunTimeInfo;
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

    //위치 변경 신호
    public static boolean resetSignal(LocalTime localTime) throws Exception {
        if(localTime.isAfter(sunTimeInfo.getSunRise()) && localTime.isBefore(sunTimeInfo.getSunSet())){
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        SunTimeUtil sunTimeUtil = new SunTimeUtil();
        try {
            sunTimeInfo = sunTimeUtil.searchSunTime(LocalDate.now());
            System.out.println(sunTimeInfo.getSunRise());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
