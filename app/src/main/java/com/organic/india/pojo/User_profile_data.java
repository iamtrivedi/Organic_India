package com.organic.india.pojo;

public class User_profile_data {

    String title,info;
    boolean is_pic;

    public User_profile_data(String title, String info, boolean is_pic) {
        this.title = title;
        this.info = info;
        this.is_pic = is_pic;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isIs_pic() {
        return is_pic;
    }

    public void setIs_pic(boolean is_pic) {
        this.is_pic = is_pic;
    }
}
