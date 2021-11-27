package kr.co.envir.chd.envirmanagement;

import okhttp3.*;

import java.io.IOException;

public class facilitySend {
	public void get() {
		String url = "http://localhost/chd/analysis/control";

		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(url)
					.build();

			//비동기 처리 (enqueue 사용)
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

		} catch (Exception e){
			System.err.println(e.toString());
		}
	}
}