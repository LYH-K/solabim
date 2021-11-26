package kr.co.chd.system.analysis_management;

import jakarta.annotation.Resource;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EnvirServiceImple implements EnvirService{
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void receiveEnvirInfo(EnvirInfo envirInfo) {
        addEnvirInfo(envirInfo);
        sendEnvirInfo (envirInfo);
    }

    @Override
    public void addEnvirInfo(EnvirInfo envirInfo) {
        sqlSessionTemplate.insert("kr.co.chd.system.analysis_management.EnvirMapper.insert", envirInfo);
    }
    
    //농작물 환경 정보 송신
    public void sendEnvirInfo (EnvirInfo envirInfo) {
        try {
            OkHttpClient client = new OkHttpClient();
            String strURL = "http://localhost/chd/analysis/control";

            StringBuffer json = new StringBuffer();
            json.append("{");
            json.append("\"envirNo\" : " + envirInfo.getEnvirNo());
            json.append("\"lux\" : " + envirInfo.getLux());
            json.append("\"verticalAngle\" : " + envirInfo.getVerticalAngle());
            json.append("\"verticalAngle\" : " + envirInfo.getVerticalAngle());
            json.append("\"resetSignal\" : " + envirInfo.isResetSignal());
            json.append("}");

            String strBody = json.toString();

            RequestBody requestBody =
                    RequestBody.create(
                            MediaType.parse(
                                    "application/json; charset=utf-8"), strBody);

            Request.Builder builder = new Request.Builder()
                    .url(strURL)
                    .post(requestBody);

            builder.addHeader("Content-type", "application/json");

            Request request = builder.build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("error");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println(response.body());
                    System.out.println("not");

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
