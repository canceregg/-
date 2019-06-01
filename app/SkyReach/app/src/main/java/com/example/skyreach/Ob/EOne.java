package com.example.skyreach.Ob;


import java.util.List;

public class EOne {
    private String fight;
    private int index;
    private String value;
    private Object object;
    public EOne(){

    }
    public EOne(String fight,Object object){
        this.fight=fight;
        this.object=object;
    }

    public Object getObject() {
        return object;
    }


    public EOne(String fight){
        this.fight=fight;
    }
    public EOne(String fight,int index){
        this.fight=fight;
        this.index=index;
    }
    public EOne(String fight,String value){
        this.fight=fight;
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public String getFight() {
        return fight;
    }
}
