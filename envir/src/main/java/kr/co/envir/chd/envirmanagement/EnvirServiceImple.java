package kr.co.envir.chd.envirmanagement;

import kr.co.envir.chd.publicdata.SunTimeUtile;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class EnvirServiceImple implements EnvirService{
    private static SunTimeInfo sunTimeInfo;

    public static void main(String[] args) throws Exception {
        EnvirServiceImple envirServiceImple = new EnvirServiceImple();
        envirServiceImple.sendEnvirInfo();
    }

    @Override
    public EnvirInfo measureEnvir() throws Exception{
//        EnvirInfo envirInfo = new MeasureEnvirUtil().measureEnvirUtile();
//        return envirInfo;
        return new EnvirInfo();
    }

    //송신 및 조도 및 각도 측정
    @Override
    public void sendEnvirInfo() throws Exception {
//        EnvirInfo envirInfo = measureEnvir();
//        sendEnvirInfo(envirInfo);
        sendEnvirInfo(new EnvirInfo());
    }

//    //1시간 30분마다 송신 및 조도 및 각도 측정
//    @Scheduled(fixedDelay = 5400000)
//    public void sch() throws Exception {
//        sendEnvirInfo();
//    }
//
//    //일출시에 시작
////    @Scheduled(cron = "")
//    public void start() throws Exception {
//        sch();
//    }
//
//    //일몰에 일시정지
//    public void end() throws Exception {
//
//    }

    //자정마다 일출 및 일몰시간 조회
    @Scheduled(cron = "0 1 0 * *")
    public void midnight() throws Exception {
        SunTimeUtile sunTimeUtile = new SunTimeUtile();
        LocalDate localDate = LocalDate.now();
        sunTimeInfo = sunTimeUtile.isRunMeasuremen(localDate);
    }

    //송신
    public void sendEnvirInfo(EnvirInfo envirInfo){
        try {

            OkHttpClient client = new OkHttpClient();
            String strURL = "http://localhost/chd/envir";

            StringBuffer json = new StringBuffer();
            json.append("{");
            json.append("\"illuminance\" : " + envirInfo.getIlluminance());
            json.append("\"verticalAngle\" : " + envirInfo.getVerticalAngle());
            json.append("\"horizontalAngle\" : " + envirInfo.getVerticalAngle());
            json.append("\"resetSignal\" : " + envirInfo.isResetSignal());
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
