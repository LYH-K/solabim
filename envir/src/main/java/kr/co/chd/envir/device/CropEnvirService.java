package kr.co.chd.envir.device;

import kr.co.chd.envir.weatherinfo.SunTimeInfo;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CropEnvirService {
    public static SunTimeInfo sunTimeInfo;

    public static void main(String[] args) {
        CropEnvirService cropEnvirService = new CropEnvirService();
        CropEnvirInfo cropEnvirInfo = new CropEnvirInfo();
        cropEnvirInfo.setHorizontalAngle(180);
        cropEnvirInfo.setVerticalAngle(60);
        cropEnvirInfo.setResetSignal(false);
        try {
            cropEnvirService.sendCropEnvirInfo(cropEnvirInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //농작물 환경 정보 측정
    public void measureCropEnvir(boolean resetSignal) throws Exception{
        CropEnvirInfo cropEnvirInfo = new MeasureCropEnvirUtil().measure();
        System.out.println("setResetSignal");
        System.out.println("resetSignal");
        cropEnvirInfo.setResetSignal(resetSignal);
        System.out.println("sendCropEnvirInfo");
        sendCropEnvirInfo(cropEnvirInfo);
    }

    //송신
    public void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception {
        System.out.println("send...");
        try {
            OkHttpClient client = new OkHttpClient();
            String strURL = "http://192.168.0.127/chd/envir";

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
                        System.out.println("성공 ");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("cut");
    }
}
