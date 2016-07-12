package com.example.rail.shuyun.entity;

/**
 * Created by qq on 2016/5/6.
 */
public class SonComment {

    private String id;
    private String sosnCommentUserPicture;
    private String sonCommentUserName;
    private String createDate;
    private String commentContent;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getSosnCommentUserPicture() {
        return sosnCommentUserPicture;
    }

    public void setSosnCommentUserPicture(String sosnCommentUserPicture) {
        this.sosnCommentUserPicture = sosnCommentUserPicture;
    }

    public String getSonCommentUserName() {
        return sonCommentUserName;
    }

    public void setSonCommentUserName(String sonCommentUserName) {
        this.sonCommentUserName = sonCommentUserName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
