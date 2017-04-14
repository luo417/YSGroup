package com.holy.yangsheng.domain;

/**
 * Created by Hailin on 2017/2/12.
 */

public class HomeTab {
    private String key;
    private String name;
    private String flag;

    public HomeTab() {
    }

    public HomeTab(String key, String name, String flag) {

        this.key = key;
        this.name = name;
        this.flag = flag;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getKey() {

        return key;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }
}
