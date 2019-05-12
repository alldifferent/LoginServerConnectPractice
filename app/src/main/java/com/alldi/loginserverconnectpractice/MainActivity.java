package com.alldi.loginserverconnectpractice;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alldi.loginserverconnectpractice.databinding.ActivityMainBinding;
import com.alldi.loginserverconnectpractice.utils.ConnectServer;
import com.alldi.loginserverconnectpractice.utils.ContextUtil;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setupEvents();
        setValues();

    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

//        로그인에 성공한 사람의 토큰을 받아오기.
        String token = getIntent().getStringExtra("userToken");

        Log.d("사용자 토큰값", token);
//        받아온 토큰을 가지고 /v2/me_info API 호출, 사용자 데이터 표시

        ConnectServer.getRequestInfo(mContext, token, new ConnectServer.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int code = json.getInt("code");
                            if (code == 200){

                                JSONObject data = json.getJSONObject("data");

                                JSONObject user = data.getJSONObject("user");

                                String name = user.getString("name");
                                act.userNameTxt.setText(name);

                                JSONObject bank_code = user.getJSONObject("bank_code");
                                String bankName = bank_code.getString("name");
                                act.bankNameTxt.setText(bankName);

                                int balance = user.getInt("balance");
                                act.userBalanceTxt.setText(String.format("%d",balance));

                                String billing_account = user.getString("billing_account");
                                act.bankAccountTxt.setText(billing_account);

                                String logo = bank_code.getString("logo");
                                Glide.with(mContext).load(logo).into(act.bankLogoIme);

                                String profile_image = user.getString("profile_image");
                                Glide.with(mContext).load(profile_image).into(act.profileImg);


                            }else {
                                String message = json.getString("message");
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });

    }

    @Override
    public void bindView() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
