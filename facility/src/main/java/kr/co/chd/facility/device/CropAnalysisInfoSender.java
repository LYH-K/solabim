package kr.co.chd.facility.device;

import kr.co.chd.facility.facility_control.CropAnalysis;
import okhttp3.*;

import java.io.File;

public class CropAnalysisInfoSender {

    public void sendInfo(CropAnalysis cropAnalysis){
        try {
            final MediaType MULTIPART = MediaType.parse("multipart/form-data");
            final String requestUrl = "http://192.168.0.127:80/chd/analysis";

            //파일의 위치, 생장률은 추후에 cropAnalysis에서 추출
            File cropSideImage = new File("/home/pi/Desktop/pictures/cropSideImage.jpg");
            File cropVerticalImage = new File("/home/pi/Desktop/pictures/cropVerticalImage.jpg");
            String growth = "50";

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder().
                    setType(MultipartBody.FORM).
                    addFormDataPart("growth", String.valueOf(growth)).
                    addFormDataPart("cropSideImage", cropSideImage.getName(), RequestBody.create(MULTIPART, cropSideImage)).
                    addFormDataPart("cropVerticalImage", cropVerticalImage.getName(), RequestBody.create(MULTIPART, cropVerticalImage))
                    .build();

            Request request = new Request.Builder().
                    url(requestUrl).
                    post(requestBody).
                    build();

            Response response = client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
