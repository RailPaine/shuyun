package com.example.rail.shuyun.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by qq on 2016/7/12.
 */
public class Comment extends BmobObject {

    private String content;
    private Post post;
    private Person author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }
}
