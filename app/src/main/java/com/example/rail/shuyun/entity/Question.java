package com.example.rail.shuyun.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by qq on 2016/7/12.
 */
public class Question extends BmobObject{

    private String Content;
    private Person Author;
    private BookMessageDetail book;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Person getAuthor() {
        return Author;
    }

    public void setAuthor(Person author) {
        Author = author;
    }


    public BookMessageDetail getBook() {
        return book;
    }

    public void setBook(BookMessageDetail book) {
        this.book = book;
    }

}
