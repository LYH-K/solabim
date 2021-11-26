package kr.co.envir.chd.publicdata;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class EnvirService {
    private void sendEnvirInfo(){
        String url = "http://192.168.137.1/chd/envir";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-type", "application/json")
                .build();

        StringBuffer buffer = new StringBuffer();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code());
            buffer.append(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
