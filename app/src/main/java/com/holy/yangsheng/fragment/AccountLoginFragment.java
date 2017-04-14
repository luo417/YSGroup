package com.holy.yangsheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.holy.yangsheng.R;
import com.holy.yangsheng.utils.UIUtils;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import static cn.jpush.im.android.tasks.GetUserInfoListTask.IDType.username;

/**
 * Created by Hailin on 2017/2/19.
 */

public class AccountLoginFragment extends Fragment {

    private View mView;
    private EditText userAccount;
    private EditText userPsd;
    private Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_account_login, null);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //实例化组件
        userAccount = (EditText) mView.findViewById(R.id.et_login_account);
        userPsd = (EditText) mView.findViewById(R.id.et_login_account_psd);
        btnLogin = (Button) mView.findViewById(R.id.btn_account_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JMessageClient.login(userAccount.getText().toString(),
                        userPsd.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            UIUtils.toast(getContext(), username + "-->登录成功!");
                        }
                    }
                });
            }
        });
    }
}
