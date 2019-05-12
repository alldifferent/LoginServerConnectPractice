package com.alldi.loginserverconnectpractice;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alldi.loginserverconnectpractice.databinding.ActivityLoginBinding;
import com.alldi.loginserverconnectpractice.utils.ConnectServer;
import com.alldi.loginserverconnectpractice.utils.ContextUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        act.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                1. 아이디와 비번을 뭐라고 입력했는지? 받아오기

                String inputId = act.loginIdEdt.getText().toString();
                String inputPw = act.loginPwEdt.getText().toString();

//                1.2 - 입력받은 ID를 SharedPreferences에 저장.
                ContextUtil.setUserInputId(mContext, inputId);

//                2. 받아온 아이디와 비번이 정말로 올바른 회원인지? 검사
//                  아이디/비번이 모두 동일한 사람이 회원 명부에 있는지 검사

                ConnectServer.postRequestSignIn(mContext, inputId, inputPw, new ConnectServer.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    int code = json.getInt("code");
                                    if (code == 200){
//                                        로그인 성공!



                                    }else {
//                                        로그인 실패. 왜실패했는지 AlertDialog
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                        alertDialog.setTitle("로그인 실패 알림");
                                        alertDialog.setMessage(json.getString("message"));
                                        alertDialog.setPositiveButton("확인", null);
                                        alertDialog.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }
                });

//                test123 / test123
//                iu0001 / Test!123
//                testorder1 / testorder1
//                gggg1111 / gggg1111




            }
        });
    }

    @Override
    public void setValues() {
        String savedUserId = ContextUtil.getUserInputId(mContext);
        act.loginIdEdt.setText(savedUserId);
    }

    @Override
    public void bindView() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
