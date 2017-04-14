package com.holy.yangsheng.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by Hailin on 2017/2/13.
 */

public class NewsItem extends BmobObject{
    private String id;
    private String title;
    private String kind;
    private String author;
    private String contentUrl;
    private String picOne;
    private String picTwo;
    private String picThree;

    public NewsItem() {
    }

    public NewsItem(String id, String title, String kind, String author, String contentUrl, String picOne, String picTwo, String picThree) {
        this.id = id;
        this.title = title;
        this.kind = kind;
        this.author = author;
        this.contentUrl = contentUrl;
        this.picOne = picOne;
        this.picTwo = picTwo;
        this.picThree = picThree;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public void setPicOne(String picOne) {
        this.picOne = picOne;
    }

    public void setPicTwo(String picTwo) {
        this.picTwo = picTwo;
    }

    public void setPicThree(String picThree) {
        this.picThree = picThree;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getKind() {
        return kind;
    }

    public String getAuthor() {
        return author;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getPicOne() {
        return picOne;
    }

    public String getPicTwo() {
        return picTwo;
    }

    public String getPicThree() {
        return picThree;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", kind='" + kind + '\'' +
                ", author='" + author + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", picOne='" + picOne + '\'' +
                ", picTwo='" + picTwo + '\'' +
                ", picThree='" + picThree + '\'' +
                '}';
    }
}
