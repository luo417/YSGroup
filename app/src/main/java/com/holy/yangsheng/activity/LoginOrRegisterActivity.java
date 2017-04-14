package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.holy.yangsheng.R;

public class LoginOrRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);


    }

    public void login(View view) {
        startActivity(new Intent(LoginOrRegisterActivity.this, LoginActivity.class));
    }

    public void register(View view) {
        startActivity(new Intent(LoginOrRegisterActivity.this, RegisterByPhoneActivity.class));

    }
}
