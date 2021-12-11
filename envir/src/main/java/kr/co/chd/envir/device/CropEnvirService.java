package kr.co.chd.envir.device;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CropEnvirService {
    private Logger logger = LogManager.getLogger(CropEnvirService.class);
    //농작물 환경 정보 측정
    public void measureCropEnvir(boolean resetSignal) throws Exception{
        CropEnvirInfo cropEnvirInfo = new MeasureCropEnvirUtil().measure();

        int verticalAngle = cropEnvirInfo.getVerticalAngle();
        int horizontalAngle = cropEnvirInfo.getHorizontalAngle() + 180;

        cropEnvirInfo.setResetSignal(resetSignal);

        logger.debug("horizontal : " + cropEnvirInfo.getVerticalAngle() + " , "
                + "vertical : " + cropEnvirInfo.getHorizontalAngle() + " , "
                + "illuminance : " + cropEnvirInfo.getIlluminance() + " , "
                + "resetSignal : " + cropEnvirInfo.isResetSignal()
        );

        sendCropEnvirInfo(cropEnvirInfo);
    }

    //송신
    public void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception {
        if(cropEnvirInfo.isResetSignal() == true){
            logger.debug("resetSignal : true");
        }
        logger.debug("send...");
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
                    logger.debug("error");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    logger.debug(response.body());
                    if (response.body() != null) {
                        logger.debug(response.body().string());
                        logger.debug("성공 ");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.debug("end");
    }
}
