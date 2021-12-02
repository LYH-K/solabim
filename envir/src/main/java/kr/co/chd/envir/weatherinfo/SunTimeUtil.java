package kr.co.chd.envir.weatherinfo;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class SunTimeUtil {
    private static final String WEATHER_SERVICE_URL = "http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo";
    private static final String SERVICE_KEY = "=7BI%2FKhhpzf6YXg813%2BtypHOlSOfZjAUxeLOcw%2BU2eBXoHbeHKwtKcLCz%2BKNrpC8sYPh5VcYDwYXMsdiH%2BRxjpA%3D%3D";
    private static final String LOCAL = "서울";
    @Autowired
    public static SunTimeInfo sunTimeInfo;

    public static void main(String[] args) throws Exception {
        searchSunTime(LocalDate.now());
        System.out.println(sunTimeInfo.getSunRise());
        System.out.println(sunTimeInfo.getSunSet());
    }

    //일출 및 일몰 조회
    public static SunTimeInfo searchSunTime(LocalDate localDate)
            throws Exception {
        localDate = LocalDate.now();

        String currentDate = localDate.getYear()+ "" + localDate.getMonthValue() + "" + localDate.getDayOfMonth();

        String url = WEATHER_SERVICE_URL + "?serviceKey=7BI%2FKhhpzf6YXg813%2BtypHOlSOfZjAUxeLOcw%2BU2eBXoHbeHKwtKcLCz%2BKNrpC8sYPh5VcYDwYXMsdiH%2BRxjpA%3D%3D" +
                "&locdate=" + URLEncoder.encode(currentDate) + "&location=" + URLEncoder.encode(LOCAL);

        // Used by OkHttp API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url.toString())
                .addHeader("Content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 200) {
                String xml = response.body().string();
                sunTimeInfo = sunRiseAndSet(xml);
            } else {
                return searchSunTime(localDate);
            }
        }

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                System.out.println("error");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.body() != null) {
//                    String xml = response.body().string();
//                    System.out.println(xml);
//                    sunTimeInfo = sunRiseAndSet(xml);
//                }
//            }
//        });

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
    public static boolean resetSignal(SunTimeInfo sunTimeInfo) throws Exception {
        LocalTime localTime = LocalTime.now();
        if(localTime.isAfter(sunTimeInfo.getSunRise()) && localTime.isBefore(sunTimeInfo.getSunSet())){
            return true;
        }
        return false;
    }
}
