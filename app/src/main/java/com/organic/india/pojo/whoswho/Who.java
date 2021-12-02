package com.organic.india.pojo.whoswho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Who {

    @SerializedName("department_id")
    @Expose
    private String departmentId;
    @SerializedName("title")
    @Expose
    private String title;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
