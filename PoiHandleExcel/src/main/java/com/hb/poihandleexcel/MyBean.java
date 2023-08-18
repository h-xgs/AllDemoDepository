package com.hb.poihandleexcel;

import androidx.annotation.NonNull;

public class MyBean {

    private String name; // 名称
    private String value; // 数值
    private String time; // 时间

    public MyBean() {
    }

    public MyBean(String name, String value, String time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    public MyBean(int name, int value, int time) {
        this.name = String.valueOf(name);
        this.value = String.valueOf(value);
        this.time = String.valueOf(time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "MyBean:{ " +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", time='" + time + '\'' +
                " }";
    }
}
