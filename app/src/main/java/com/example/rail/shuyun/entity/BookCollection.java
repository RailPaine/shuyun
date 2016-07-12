package com.example.rail.shuyun.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import recyclerview.Item;

/**
 * Created by qq on 2016/7/12.
 */
public class BookCollection extends BmobObject{
    private String School;
    private String Colleage;
    private String Major;
    private String Grade;
    private List<Item> bookMessage;

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getColleage() {
        return Colleage;
    }

    public void setColleage(String colleage) {
        Colleage = colleage;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public List<Item> getBookMessage() {
        return bookMessage;
    }

    public void setBookMessage(List<Item> bookMessage) {
        this.bookMessage = bookMessage;
    }
}
