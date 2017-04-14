package com.holy.yangsheng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.view.VerifyCodeView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

public class RegisterByAccountActivity extends AppCompatActivity {

    @InjectView(R.id.et_user_nickname_p)
    EditText user_name;

    @InjectView(R.id.et_user_psd_p)
    EditText user_psd;

    @InjectView(R.id.et_user_psd_confirm_p)
    EditText psd_confirm;

    @InjectView(R.id.rg__p_user_gender)
    RadioGroup user_gender;

    @InjectView(R.id.p_verify_code)
    VerifyCodeView verify_code_view;

    @InjectView(R.id.et_p_verify_code)
    EditText verify_code;

    @InjectViews({R.id.iv_caution_1, R.id.iv_caution_2, R.id.iv_caution_3})
    ImageView[] iv_cautions;
    private PopupWindow mPopupWindow;

    @OnClick(R.id.tv_register_by_phone)
    public void toAccountRegister() {
        startActivity(new Intent(RegisterByAccountActivity.this, RegisterByPhoneActivity.class));
        finish();
    }

    @OnClick(R.id.btn_register_finish)
    public void register(){
        verifyRegister();
    }

    @OnClick(R.id.p_verify_code)
    public void initVerifyCodeView() {
        verify_code_view.invaliChenkCode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("账号注册");

        verifyInput();
    }

    private void verifyInput() {
//        user_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                String name = user_name.getText().toString();
//                if (TextUtils.isEmpty(name)) {
//                    if (b) { //获取焦点
//                        boolean isShowed = iv_cautions[0].getVisibility()==View.VISIBLE?true:false;
//                        if (isShowed) {
//                            iv_cautions[0].setVisibility(View.INVISIBLE);
//                            dismissCautionPopup();
//                        }
//                    } else { //失去焦点
//                        //显示警示弹框
//                        if (!mPopupWindow.isShowing()) {
//                            showCautionPopup(iv_cautions[0], "用户名不能为空");
//                        }
//                    }
//                } else {
//                    if (name.matches("\\w{2,10}")) {
//                        //什么都不做
//                    } else {
//                        if (b) { //获取焦点
//                            boolean isShowed = iv_cautions[0].getVisibility()==View.VISIBLE?true:false;
//                            if (isShowed) {
//                                user_name.setText("");
//                                iv_cautions[0].setVisibility(View.INVISIBLE);
//                                dismissCautionPopup();
//                            }
//                        } else { //失去焦点
//                            //显示警示弹框
//                            if (!mPopupWindow.isShowing()) {
//                                showCautionPopup(iv_cautions[0], "用户名不合法");
//                            }
//                        }
//                    }
//                }
//            }
//        });
        user_psd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Pattern p = Pattern.compile("[^\\da-zA-Z]");
                Matcher m = p.matcher(charSequence.toString());
                if (m.find()) {  //包含除数字字母以外的符号
                    //显示警示弹框
                    if (!mPopupWindow.isShowing()) {
                        showCautionPopup(iv_cautions[1], "密码不能包含空格、符号等特殊字符");
                    }
                } else {
                    boolean isShowed = iv_cautions[1].getVisibility()==View.VISIBLE?true:false;
                    if (isShowed) {
                        iv_cautions[1].setVisibility(View.INVISIBLE);
                        dismissCautionPopup();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        user_psd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String psd = user_psd.getText().toString();
                if (TextUtils.isEmpty(psd)) {
                    if (b) { //获取焦点
                        boolean isShowed = iv_cautions[1].getVisibility()==View.VISIBLE?true:false;
                        if (isShowed) {
                            iv_cautions[1].setVisibility(View.INVISIBLE);
                            dismissCautionPopup();
                        }
                    } else { //失去焦点
                        //显示警示弹框
                        if (!mPopupWindow.isShowing()) {
                            showCautionPopup(iv_cautions[1], "密码不能为空");
                        }
                    }
                } else {
                    if (psd.matches("[0-9a-zA-Z]{6,16}")) {
                        //什么都不做
                    } else {
                        if (b) { //获取焦点
                            boolean isShowed = iv_cautions[1].getVisibility()==View.VISIBLE?true:false;
                            if (isShowed) {
                                user_psd.setText("");
                                iv_cautions[1].setVisibility(View.INVISIBLE);
                                dismissCautionPopup();
                            }
                        } else { //失去焦点
                            //显示警示弹框
                            if (!mPopupWindow.isShowing()) {
                                showCautionPopup(iv_cautions[1], "请输入6-16位字母/数字");
                            }
                        }
                    }
                }
            }
        });
        psd_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
    }

    private void verifyRegister(){

    }

    /**
     * 弹出警示框
     *
     * @param view 警示框位于此view下面
     * @param text 需要显示的文本内容
     */
    private void showCautionPopup(View view, String text) {
        view.setVisibility(View.VISIBLE);

        View cautionView = View.inflate(RegisterByAccountActivity.this, R.layout.view_caution_popup_window, null);
        TextView popup_text = (TextView) cautionView.findViewById(R.id.tv_popup_text);
        popup_text.setText(text);

        cautionView.measure(0, 0);
        int width = cautionView.getMeasuredWidth() - 85;

        mPopupWindow = new PopupWindow(cautionView, -2, -2);//-2表示包裹内容

        //使用PopupWindow，必须设置背景，不然没有动画
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //设置PopupWindow的显示位置
        mPopupWindow.showAsDropDown(view, -width, 0);

        ScaleAnimation sa = new ScaleAnimation(0, 1.0f, 0, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(500);

        cautionView.startAnimation(sa);
    }

    /**
     * 取消警示弹框
     */
    private void dismissCautionPopup(){
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
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
