package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.holy.yangsheng.R;
import com.holy.yangsheng.domain.User;
import com.holy.yangsheng.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.holy.yangsheng.utils.UIUtils.toast;

public class FillinInformationActivity extends AppCompatActivity {

    @InjectView(R.id.rg_user_gender)
    RadioGroup rg_gender;

    @InjectView(R.id.et_user_nickname)
    EditText user_nickname;

    @InjectView(R.id.et_user_email)
    EditText user_mail;

    private String gender = "未知";
    private String phone_number;
    private String user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillin_information);

        ButterKnife.inject(this); //初始化

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("填写资料");

        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");
        user_password = intent.getStringExtra("user_password");

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //获取变更后的选中项的ID
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                //RadioButton rb = (RadioButton)FillinInformationActivity.this.findViewById(radioButtonId);
                if (radioButtonId == R.id.rb_male) {
                    gender = "男";
                } else if (radioButtonId == R.id.rb_female) {
                    gender = "女";
                } else {
                    gender = "未知";
                }
            }
        });
    }

    @OnClick(R.id.btn_register_finish)
    public void finish() {
        User user = new User();
        String nickname = user_nickname.getText().toString();
        if (!TextUtils.isEmpty(nickname)) {
            user.setUsername(nickname);
        } else {
            user.setUsername(StringUtils.getRandomString(6));
        }

        String email = user_mail.getText().toString();
        if (!TextUtils.isEmpty(email)) {
            user.setEmail(email);
            user.setEmailVerified(false);
        }
        user.setMobilePhoneNumber(phone_number);
        user.setPassword(user_password);
        user.setMobilePhoneNumberVerified(true);
        user.setGender(gender);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User s, BmobException e) {
                if (e == null) {
                    toast(FillinInformationActivity.this, "注册成功:" + s.toString());
                } else {
                    toast(FillinInformationActivity.this, "注册失败:" + e.toString());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
