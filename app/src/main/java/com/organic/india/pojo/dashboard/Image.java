package com.organic.india.pojo.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    public Image(String img) {
        this.img = img;
    }

    @SerializedName("img")
    @Expose
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
