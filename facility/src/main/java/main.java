import okhttp3.*;
import org.apache.tomcat.util.json.JSONParser;
import org.codehaus.jackson.map.util.JSONPObject;

import java.io.File;
import java.time.LocalDate;

public class main {
    public static void main(String args[]){

        try {
            final MediaType MULTIPART = MediaType.parse("multipart/form-data");
            final String requestUrl = "http://localhost:80/chd/analysis";

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

            ResponseBody responseBody = response.body();
            JSONParser jsonParser = new JSONParser(responseBody.toString());

            System.out.println(jsonParser);

            System.out.println("송신");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
