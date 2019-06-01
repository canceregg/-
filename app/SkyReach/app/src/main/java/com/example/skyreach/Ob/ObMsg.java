package com.example.skyreach.Ob;

public class ObMsg {

    private String content,time,uid,uname;

    public ObMsg(String content,String time,String uid){
        this.content=content;
        this.time=time;
        this.uid=uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
