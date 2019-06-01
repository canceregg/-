package com.example.skyreach.Ob;

import androidx.annotation.NonNull;

public class ObMessage {

    private String title;
    private String content;

    public ObMessage(String title,String content) {
        this.title=title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
