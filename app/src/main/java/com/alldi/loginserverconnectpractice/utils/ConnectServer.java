package com.alldi.loginserverconnectpractice.utils;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectServer {

    private final static String BASE_URL = "http://delivery-dev-389146667.ap-northeast-2.elb.amazonaws.com";

    public interface JsonResponseHandler{
        void onResponse(JSONObject json);
    }

    public static void postRequestSignIn(Context context, String user_id, String password, final JsonResponseHandler handler){

        OkHttpClient client = new OkHttpClient();

//        POST 메쏘드는 urlBuilder가 아니라, RequestBody를 Build.
        RequestBody requestBody = new FormBody.Builder()
                .add("user_id", user_id)
                .add("password", password)
                .build();

//        실제 Request를 생성, 서버로 떠날 준비.
        Request request = new Request.Builder()
                .url(BASE_URL+"/auth")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String responseContent = response.body().string();

                Log.d("서버 응답 내용", responseContent);

                try {
//                    받아온 응답을 JSON 객체로 변환
                    JSONObject json = new JSONObject(responseContent);

                    if (handler != null){
//                        화면에서 처리하는 코드가 있으면 실행시켜줌.
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public static void getRequestInfo(Context context, String token, final JsonResponseHandler handler){

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL+"/v2/me_info").newBuilder();
        String requestURL = urlBuilder.build().toString();
//        실제 Request를 생성, 서버로 떠날 준비.
        Request request = new Request.Builder()
                .header("X-Http-Token", token)
                .url(requestURL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String responseContent = response.body().string();

                Log.d("서버 응답 내용", responseContent);

                try {
//                    받아온 응답을 JSON 객체로 변환
                    JSONObject json = new JSONObject(responseContent);

                    if (handler != null){
//                        화면에서 처리하는 코드가 있으면 실행시켜줌.
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
