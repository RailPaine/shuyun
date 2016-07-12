package com.example.rail.shuyun.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by qq on 2016/7/12.
 */
public class BookMessageDetail extends BmobObject{

    private String bookName;
    private String bookAuthor;
    private String bookPublish;
    private String bookContent;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublish() {
        return bookPublish;
    }

    public void setBookPublish(String bookPublish) {
        this.bookPublish = bookPublish;
    }

    public String getBookContent() {
        return bookContent;
    }

    public void setBookContent(String bookContent) {
        this.bookContent = bookContent;
    }
}
