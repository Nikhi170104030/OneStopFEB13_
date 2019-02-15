package com.swc.onestop.Activities.Complaints;

import java.io.Serializable;

public class Comment implements Serializable {
    private String name,body;

    public Comment(String name,String body) {
        this.name = name;
        //this.date = date;
        this.body = body;
    }


    public String getName() {

        return name;
    }
    public String getBody() {

        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Comment() {

    }
}
