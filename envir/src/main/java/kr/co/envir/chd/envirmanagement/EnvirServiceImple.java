package kr.co.envir.chd.envirmanagement;

import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EnvirServiceImple implements EnvirService{
    private final static String ENVIR_SERVICE_URL = "http://192.168.137.1/chd/envir";

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

    @Override
    public void sendEnvirInfo() throws Exception {
//        EnvirInfo envirInfo = measureEnvir();
//        sendEnvirInfo(envirInfo);
        sendEnvirInfo(new EnvirInfo());
    }
    
    //송신
    public void sendEnvirInfo(EnvirInfo envirInfo){
        StringBuffer urlBuffer = new StringBuffer(ENVIR_SERVICE_URL);
        urlBuffer.append("?" + URLEncoder.encode("lux", StandardCharsets.UTF_8) + envirInfo.getLux());
        urlBuffer.append("&" + URLEncoder.encode("horizontalAngle", StandardCharsets.UTF_8) + "=" + envirInfo.getHorizontalAngle());
        urlBuffer.append("&" + URLEncoder.encode("verticalAngle", StandardCharsets.UTF_8) + "=" + envirInfo.getVerticalAngle());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlBuffer.toString())
                .addHeader("Content-type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            //비동기 처리를 위해 Callback 구현
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error + Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("Response Body is " + response.body().string());
            }
        });
    }
}
