package com.skyreach.yinliu.Main.Ob;

public class ObPlate {

    private String title,content;
    private String see,support;
    public ObPlate(String title, String content, String see, String support){
        this.title=title;
        this.content=content;
        this.see=see;
        this.support=support;
    }
    public String getSee(){
        return see;
    }
    public String getSupport(){
        return support;
    }
    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
