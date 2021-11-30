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

    public static void main(String[] args) throws Exception {
        new CropEnvirServiceImple().sendCropEnvirInfo(new CropEnvirInfo());
    }

    //송신
    @Override
    public void sendCropEnvirInfo(CropEnvirInfo cropEnvirInfo) throws Exception {
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
