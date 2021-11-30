package kr.co.chd.envir.envir_management;

import kr.co.chd.envir.weather_info.SunTimeInfo;
import kr.co.chd.envir.weather_info.SunTimeUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;

public class CropEnvirServiceImple implements CropEnvirService {
    public static SunTimeInfo sunTimeInfo;
    public static boolean resetSignal;

    @Autowired
    private MeasureCropEnvirUtil measureCropEnvirUtile;

    static {
        try {
            sunTimeInfo = SunTimeUtil.searchSunTime(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //농작물 환경 정보 측정
    @Override
    public CropEnvirInfo measureCropEnvir() throws Exception{
        CropEnvirInfo cropEnvirInfo = new MeasureCropEnvirUtil().measure();
        cropEnvirInfo.setResetSignal(resetSignal);
        return cropEnvirInfo;
    }

    //송신 및 측정
//    @Override
//    @Scheduled(fixedDelay = 324000000)
//    public void mesureSend() throws Exception {
//        if(resetSignal){
//            CropEnvirInfo envirInfo = measureEnvir();
//            envirInfo.setResetSignal(resetSignal);
//            sendEnvirInfo(envirInfo);
//        } else {
//            CropEnvirInfo envirInfo = new CropEnvirInfo();
//            envirInfo.setResetSignal(resetSignal);
//            sendEnvirInfo(envirInfo);
//        }
//    }
//
//    //현재시간 측정에 따른 업무
//    @Override
//    @Scheduled(fixedDelay = 3600000)
//    public void now() throws Exception {
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                try {
//                    LocalDate localDate = LocalDate.now();
//                    LocalTime localTime = LocalTime.now();
//                    LocalTime midnight = LocalTime.of(0,1,0);
//                    System.out.println(localDate);
//                    if(localTime.equals(midnight)){
//                        sunTimeInfo = SunTimeUtil.searchSunTime(localDate);
//                    } else if(localTime.equals(sunTimeInfo.getSunRise())){
//                        resetSignal = true;
//                    } else if(localTime.equals(sunTimeInfo.getSunRise())){
//                        resetSignal = false;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread.setDaemon(true);
//        thread.start();
//    }

    public static void main(String[] args) throws Exception {
        new CropEnvirServiceImple().sendEnvirInfo(new CropEnvirInfo());
    }

    //송신
    @Override
    public void sendEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception {
        try {
            OkHttpClient client = new OkHttpClient();
            String strURL = "http://localhost/chd/envir";

            StringBuffer json = new StringBuffer();
            json.append("{");
            json.append("\"illuminance\" : " + cropEnvirInfo.getIlluminance() + ",");
            json.append("\"verticalAngle\" : " + cropEnvirInfo.getVerticalAngle() + ",");
            json.append("\"horizontalAngle\" : " + cropEnvirInfo.getHorizontalAngle() + ",");
            json.append("\"resetSignal\" : " + cropEnvirInfo.isResetSignal());
            json.append("}");

            String strBody = json.toString();

            RequestBody requestBody =
                    RequestBody.create(
                            MediaType.parse(
                                    "application/json; charset=utf-8"), strBody);

            Request.Builder builder = new Request.Builder()
                    .addHeader("Content-type", "application/json")
                    .url(strURL)
                    .post(requestBody);

            Request request = builder.build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println(response.body());
                    if (response.body() != null) {
                        System.out.println(response.body().string());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}