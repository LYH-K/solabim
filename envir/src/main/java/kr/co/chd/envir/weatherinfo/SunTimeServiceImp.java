package kr.co.chd.envir.weatherinfo;

import okhttp3.*;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;


public class SunTimeServiceImp implements SunTimeService {
    private static final String WEATHER_SERVICE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo";
    private static final String SERVICE_KEY = "7BI%2FKhhpzf6YXg813%2BtypHOlSOfZjAUxeLOcw%2BU2eBXoHbeHKwtKcLCz%2BKNrpC8sYPh5VcYDwYXMsdiH%2BRxjpA%3D%3D";
    private static final String LOCAL = "서울";

    //일출 및 일몰 조회
    @Override
    public SunTimeInfo getSunTime() throws Exception {
        LocalDate localDate = LocalDate.now();
        String currentDate = localDate.getYear() + "" + localDate.getMonthValue() + "" + localDate.getDayOfMonth();
        String url = WEATHER_SERVICE_URL + "?serviceKey=" + SERVICE_KEY + "&locdate=" + URLEncoder.encode(currentDate, "UTF-8") + "&location=" + URLEncoder.encode(LOCAL, "UTF-8");

        // Used by OkHttp API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .addHeader("Content-type", "application/json")
                .build();

        SunTimeInfo sunTimeInfo = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                String xml = response.body().string();
                sunTimeInfo = sunRiseAndSet(xml);
            }
        } catch (Exception e) {
            throw e;
        }

        return sunTimeInfo;
    }

    //공공 데이터 xml문자열 파싱
    private static SunTimeInfo sunRiseAndSet(String xml) {
        LocalTime sunriseTime = null;
        LocalTime sunsetTime = null;
        String[] sbs = xml.split("<");

        for (String sun : sbs) {
            if (sun.indexOf("sunrise") == 0) {
                String[] sunriseTimes = sun.split(">");
                String sunrise = sunriseTimes[1];
                int sunriseHours = Integer.parseInt(sunrise.substring(0, 2));
                int sunriseMinute = Integer.parseInt(sunrise.substring(2, 4));
                sunriseTime = LocalTime.of(sunriseHours, sunriseMinute, 00);
            }

            if (sun.indexOf("sunset") == 0) {
                String[] sunsetTimes = sun.split(">");
                String sunset = sunsetTimes[1];
                int sunsetHour = Integer.parseInt(sunset.substring(0, 2));
                int sunsetMinute = Integer.parseInt(sunset.substring(2, 4));
                sunsetTime = LocalTime.of(sunsetHour, sunsetMinute, 00);
            }
        }

        SunTimeInfo sun = new SunTimeInfo();
        sun.setSunRise(sunriseTime);
        sun.setSunSet(sunsetTime);

        return sun;
    }
    
    //위치 초기화 신호
    @Override
    public boolean resetSignal(SunTimeInfo sunTimeInfo) throws Exception {
        LocalTime localTime = LocalTime.now();
        if (localTime.isBefore(sunTimeInfo.getSunRise()) && localTime.isAfter(sunTimeInfo.getSunSet())) {
            return true;
        }
        return false;
    }
}
