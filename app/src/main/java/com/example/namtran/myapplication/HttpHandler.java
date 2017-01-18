package com.example.namtran.myapplication;

/**
 * Created by nam.tran on 17-Jan-17.
 */

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public HttpHandler() {
    }

    public String sendRequest(String reqUrl, String params) {
        try {

            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(JSON, params);
            Request request = new Request.Builder()
                    .url(reqUrl)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
