package com.holy.yangsheng.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Hailin on 2017/2/10.
 */

public class DiscoverFragment extends BaseFragment {
    @Override
    public View initView(Bundle savedInstanceState) {
        TextView textView = new TextView(mActivity);
        textView.setText("动态");
        return textView;
    }
}
