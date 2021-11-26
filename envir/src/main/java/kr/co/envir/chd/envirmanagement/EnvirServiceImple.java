package kr.co.envir.chd.envirmanagement;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;

public class EnvirServiceImple implements EnvirService {
    private final static String ENVIR_SERVICE_URL = "http://192.168.137.1/chd/envir";
    private static LocalTime localTime;

//    static{
//        int start = 0;
//
//
//
//        int end = 1;
//    }

    @Override
    public EnvirInfo measureEnvir() throws Exception{
        EnvirInfo envirInfo = new MeasureEnvirUtil().measureEnvirUtile();
        return envirInfo;
    }

    @Override
    public void sendEnvirInfo() throws Exception {
        EnvirInfo envirInfo = measureEnvir();

        StringBuffer urlBuffer = new StringBuffer(ENVIR_SERVICE_URL);
        urlBuffer.append("?" + URLEncoder.encode("lux", StandardCharsets.UTF_8) + envirInfo.getLux());
        urlBuffer.append("&" + URLEncoder.encode("horizontalAngle", StandardCharsets.UTF_8) + "=" + envirInfo.getHorizontalAngle());
        urlBuffer.append("&" + URLEncoder.encode("verticalAngle", StandardCharsets.UTF_8) + "=" + envirInfo.getVerticalAngle());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlBuffer.toString())
                .addHeader("Content-type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 200) {
                sendEnvirInfo();
            }
        }
    }

    //실행 명령
//    public static boolean RunCommand(){
//        LocalDateTime localDateTime = LocalDateTime.now();
//        boolean result = false;
//        LocalTime[] localTimes;
//        try {
//            result = new SunTimeUtile().resetSignal(localDateTime);
//
//            if(result){
//                localTimes = new SunTimeUtile().isRunMeasuremen(localDateTime);
//                localTime = LocalTime.of(localTimes[0].getHour(), localTimes[0].getMinute(), 00);
//                if(localTimes[0].equals(localTime)){
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
}
