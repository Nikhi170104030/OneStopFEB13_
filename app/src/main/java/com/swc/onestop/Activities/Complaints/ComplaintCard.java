package com.swc.onestop.Activities.Complaints;

import android.graphics.drawable.Drawable;


import java.util.ArrayList;
import java.util.List;

public class ComplaintCard {
    private String name,date;
    private Drawable image;
    private String subject,body,id;
    private String likes,comments;
    private Drawable supported;
    private List<Comment> commentList;


    public void addComment(Comment comment){
        commentList.add(comment);
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }
    public String getId() {
        return id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Drawable isSupported() {
        return supported;
    }

    public void setSupported(Drawable supported) {
        this.supported = supported;
    }

    public ComplaintCard(String name, String date, Drawable image, String subject, String body,String id,
                         String likes, String comments, Drawable supported) {

        this.name = name;
        this.date = date;
        this.image = image;
        this.subject = subject;
        this.body = body;
        this.id = id;
        this.likes = likes;
        this.comments = comments;
        this.supported=supported;
        this.commentList = new ArrayList<>();
    }

    public ComplaintCard(String s[],Drawable d,Drawable c) {
        name=s[0];
        date=s[1];
        subject=s[2];
        body=s[3];
        likes=s[4];
        comments=s[5];
        image = d;
        supported = c;
        commentList = new ArrayList<>();
    }

    public String[] getAllStringData(){
        String s[] = new String[6];
        s[0]=name;
        s[1]=date;
        s[2]=subject;
        s[3]=body;
        s[4]=likes;
        s[5]=comments;
        return s;
    }

}
