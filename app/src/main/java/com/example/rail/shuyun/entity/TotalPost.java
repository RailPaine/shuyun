package com.example.rail.shuyun.entity;


import java.util.List;

/**
 * Created by qq on 2016/7/12.
 */
public class TotalPost {

    private Post post;
    private List<Comment> list;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Comment> getList() {
        return list;
    }

    public void setList(List<Comment> list) {
        this.list = list;
    }
}
