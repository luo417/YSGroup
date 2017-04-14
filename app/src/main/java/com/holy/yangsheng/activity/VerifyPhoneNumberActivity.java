package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.holy.yangsheng.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.holy.yangsheng.utils.UIUtils.toast;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    @InjectView(R.id.tv_psd_verify)
    TextView tv_time;

    @InjectView(R.id.et_sms_verify_code)
    EditText et_code;

    private String phone_number;
    private MyCountTimer timer;
    private String user_psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("验证手机号码");

        //拿到手机号码
        Intent intent = getIntent();
        phone_number = intent.getStringExtra("phone_number");
        user_psd = intent.getStringExtra("user_password");

        //发送验证码
        requestSMSCode();
    }

    class MyCountTimer extends CountDownTimer {

        public MyCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_time.setText((millisUntilFinished / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            tv_time.setText("重新发送验证码");
        }
    }

    @OnClick(R.id.tv_psd_verify)
    public void send() {
        requestSMSCode();
    }

    @OnClick(R.id.btn_verify_finish)
    public void nextPage(){
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            toast(VerifyPhoneNumberActivity.this, "验证码不能为空");
            return;
        }
        BmobSMS.verifySmsCode(phone_number, code, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){
                    toast(VerifyPhoneNumberActivity.this, "手机号码已验证");
                    Intent intent = new Intent(VerifyPhoneNumberActivity.this, FillinInformationActivity.class);
                    intent.putExtra("phone_number", phone_number);
                    intent.putExtra("user_password", user_psd);
                    startActivity(intent);
                }else{
                    toast(VerifyPhoneNumberActivity.this, "验证失败：code="+ex.getErrorCode()+"，错误描述："+ex.getLocalizedMessage());
                }
            }
        });
    }

    private void requestSMSCode() {
        timer = new MyCountTimer(60000, 1000);
        timer.start();
        BmobSMS.requestSMSCode(phone_number, "验证码", new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                // TODO Auto-generated method stub
                if (ex == null) {// 验证码发送成功
                    toast(VerifyPhoneNumberActivity.this, "验证码发送成功");// 用于查询本次短信发送详情
                } else {//如果验证码发送错误，可停止计时
                    timer.cancel();
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
