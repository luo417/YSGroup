package com.holy.yangsheng.domain;

import cn.bmob.v3.BmobUser;

/**
 * Created by Hailin on 2017/2/18.
 */

public class User extends BmobUser {
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
