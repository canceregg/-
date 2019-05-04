package com.skyreach.yinliu.Main.Ob;

public class ObComment {

    private String userName,content;
    public ObComment(String userName, String content){
        this.userName=userName;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }
}
