package com.example.skyreach.Ob;

import java.io.Serializable;

public class ObInterest implements Serializable {

    private String title,content;
    public ObInterest(String title,String content){
        this.title=title;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
