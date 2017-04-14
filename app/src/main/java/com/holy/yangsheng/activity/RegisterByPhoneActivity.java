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
import android.widget.TextView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.view.VerifyCodeView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.holy.yangsheng.utils.UIUtils.toast;

public class RegisterByPhoneActivity extends AppCompatActivity {

    private PopupWindow mPopupWindow;
    private boolean isShowedPopup = false;
    private boolean isShowedErrorIcon = false;
    private boolean isShowedComfirmPopup = false;
    private boolean isShowedComfirmErrorIcon = false;

    @InjectView(R.id.et_register_phone_number)
    EditText phone_number;

    @InjectView(R.id.et_register_password)
    EditText psd;

    @InjectView(R.id.et_register_psd_confirm)
    EditText psd_confirm;

    @InjectView(R.id.iv_input_error_caution)
    ImageView error_caution;

    @InjectView(R.id.iv_confirm_error_caution)
    ImageView confirm_error_caution;

    @InjectView(R.id.verify_code)
    VerifyCodeView verify_code_view;

    @InjectView(R.id.et_a_verify_code)
    EditText verify_code;

    @OnClick(R.id.tv_register_by_account)
    public void toAccountRegister() {
        startActivity(new Intent(RegisterByPhoneActivity.this, RegisterByAccountActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("手机号注册");

        initVerifyPassword();
    }

    @OnClick(R.id.verify_code)
    public void initVerifyCodeView() {
        verify_code_view.invaliChenkCode();
    }

    private void initVerifyPassword() {
        //psd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);  //设置密码可见
        psd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Pattern p = Pattern.compile("[^\\da-zA-Z]");
                Matcher m = p.matcher(charSequence.toString());
                if (m.find()) {  //包含除数字字母以外的符号
                    if (!isShowedPopup) {
                        //弹出警示框
                        showCautionPopup(error_caution, "密码不能包含空格、符号等特殊字符");
                        isShowedPopup = true;
                        isShowedErrorIcon = true;
                    }
                } else {
                    if (isShowedPopup) {
                        if (mPopupWindow != null) {
                            error_caution.setVisibility(View.INVISIBLE);
                            mPopupWindow.dismiss();
                            isShowedPopup = false;
                            isShowedErrorIcon = false;
                        }
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        psd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) { //失去焦点，判断输入字符是否>=6
                    if (((EditText) view).getText().toString().length() < 6 && ((EditText) view).getText().toString().length() != 0) {
                        if (isShowedPopup) {
                            if (mPopupWindow != null) {
                                mPopupWindow.dismiss();
                                isShowedPopup = false;
                            }
                        }
                        //弹出警示框
                        showCautionPopup(error_caution, "请输入6-16位字母/数字");
                        isShowedPopup = true;
                    }
                } else {  //获取焦点，判断是否满足密码输入条件
                    if (error_caution.getVisibility() == View.VISIBLE) {
                        psd.setText("");
                        if (isShowedPopup) {
                            mPopupWindow.dismiss();
                            isShowedPopup = false;
                        }

                        if (isShowedErrorIcon) {
                            error_caution.setVisibility(View.INVISIBLE);
                            isShowedErrorIcon = false;
                        }
                    }

                }
            }
        });

        psd_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!((EditText) view).getText().toString().equals(psd.getText().toString()) && ((EditText) view).getText().toString().length() != 0) {
                    if (b) { //获取焦点

                        psd_confirm.setText("");
                        if (isShowedComfirmPopup) {
                            mPopupWindow.dismiss();
                            isShowedComfirmPopup = false;
                        }

                        if (isShowedComfirmErrorIcon) {
                            confirm_error_caution.setVisibility(View.INVISIBLE);
                            isShowedComfirmErrorIcon = false;
                        }


                    } else { //失去焦点
                        if (isShowedPopup) {
                            mPopupWindow.dismiss();
                            isShowedPopup = false;
                        }

                        if (!isShowedComfirmPopup) {
                            //弹出警示框
                            showCautionPopup(confirm_error_caution, "两次密码输入不一致");
                            isShowedPopup = true;
                            isShowedComfirmErrorIcon = true;
                        }
                    }
                }

            }
        });
    }

    //显示警示框
    private void showCautionPopup(View view, String text) {
        view.setVisibility(View.VISIBLE);

        View cautionView = View.inflate(RegisterByPhoneActivity.this, R.layout.view_caution_popup_window, null);
        TextView popup_text = (TextView) cautionView.findViewById(R.id.tv_popup_text);
        popup_text.setText(text);

        cautionView.measure(0, 0);
        int width = cautionView.getMeasuredWidth() - 85;
        System.out.println(width);

        //-2表示包裹内容
        mPopupWindow = new PopupWindow(cautionView, -2, -2);

        //使用PopupWindow，必须设置背景，不然没有动画
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //获取将要显示到窗口上的位置
        int[] location = new int[2];
        error_caution.getLocationInWindow(location);

        //设置PopupWindow的显示位置
        mPopupWindow.showAsDropDown(view, -width, 0);

        ScaleAnimation sa = new ScaleAnimation(0, 1.0f, 0, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(500);

        cautionView.startAnimation(sa);
    }

    /**
     * 如果所有输入均正确，跳转到下一页
     *
     * @param view
     */
    public void nextPage(View view) {
        if (isShowedErrorIcon || !(psd_confirm.getText().toString().equals(psd.getText().toString()))
                || TextUtils.isEmpty(psd_confirm.getText().toString())) {
            toast(RegisterByPhoneActivity.this, "密码输入错误！");
        } else {
            if (verify_code.getText().toString().equals(verify_code_view.getCheckCode())) {
                Intent intent = new Intent(RegisterByPhoneActivity.this, VerifyPhoneNumberActivity.class);
                intent.putExtra("phone_number", phone_number.getText().toString());
                intent.putExtra("user_password", psd_confirm.getText().toString());
                startActivity(intent);
            } else {
                toast(RegisterByPhoneActivity.this, "验证码有误");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
