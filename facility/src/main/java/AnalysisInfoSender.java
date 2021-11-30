import kr.co.chd.facility.CropAnalysis;
import okhttp3.*;

import java.io.File;

public class AnalysisInfoSender {

    public void sendInfo(CropAnalysis cropAnalysis){
        try {
            final MediaType MULTIPART = MediaType.parse("multipart/form-data");
            final String requestUrl = "http://localhost:80/chd/analysis";

            //파일의 위치는 수정이 필요함함
            File cropSideImage = new File("C:\\Users\\sdm05\\Desktop\\cropInfo\\cropSideImage.jpg");
            File cropVerticalImage = new File("C:\\Users\\sdm05\\Desktop\\cropInfo\\cropVerticalImage.jpg");
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
