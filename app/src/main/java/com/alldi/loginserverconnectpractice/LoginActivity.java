package com.alldi.loginserverconnectpractice;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.alldi.loginserverconnectpractice.databinding.ActivityLoginBinding;

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
//                2. 받아온 아이디와 비번이 정말로 올바른 회원인지? 검사
//                  아이디/비번이 모두 동일한 사람이 회원 명부에 있는지 검사

//                test123 / test123
//                iu0001 / Test!123
//                testorder1 / testorder1
//                gggg1111 / gggg1111




            }
        });
    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindView() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
