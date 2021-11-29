package kr.co.chd.system.analysis_management;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CropFacilityServiceImple implements CropFacilityService {


    //농작물 환경 정보 송신
    @Override
    public void sendEnvirInfo (EnvirInfo envirInfo) {
        try {
            OkHttpClient client = new OkHttpClient();
            String strURL = "http://localhost/chd/analysis/control";

            StringBuffer json = new StringBuffer();
            json.append("{");
            json.append("\"illuminace\" : " + envirInfo.getIlluminance());
            json.append("\"verticalAngle\" : " + envirInfo.getVerticalAngle());
            json.append("\"horizontalAngle\" : " + envirInfo.getHorizontalAngle());
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
