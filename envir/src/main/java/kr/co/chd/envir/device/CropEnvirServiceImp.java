package kr.co.chd.envir.device;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CropEnvirServiceImp implements CropEnvirService {
    //농작물 환경 정보 측정
    @Override
    public CropEnvirInfo measureCropEnvir(boolean resetSignal) throws Exception{
        CropEnvirInfo cropEnvirInfo = new MeasureCropEnvirUtil().measure();

        int verticalAngle = cropEnvirInfo.getVerticalAngle();
        int horizontalAngle = cropEnvirInfo.getHorizontalAngle() + 180;

        cropEnvirInfo.setResetSignal(resetSignal);

        System.out.println("horizontal : " + cropEnvirInfo.getVerticalAngle() + " , "
                + "vertical : " + cropEnvirInfo.getHorizontalAngle() + " , "
                + "illuminance : " + cropEnvirInfo.getIlluminance() + " , "
                + "resetSignal : " + cropEnvirInfo.isResetSignal()
        );

        return cropEnvirInfo;
    }

    //송신
    @Override
    public void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception {
        if(cropEnvirInfo.isResetSignal() == true){
            System.out.println("resetSignal : true");
        }
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
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }
}
