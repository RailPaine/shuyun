package com.example.rail.shuyun.entity;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by qq on 2016/7/11.
 */
public class Person extends BmobUser {
    private BmobFile ImageUrl;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BmobFile getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(BmobFile imageUrl) {
        ImageUrl = imageUrl;
    }
}
