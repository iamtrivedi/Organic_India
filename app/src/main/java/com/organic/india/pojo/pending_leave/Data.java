package com.organic.india.pojo.pending_leave;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("leave_category")
    @Expose
    private String leaveCategory;
    @SerializedName("pending_leave")
    @Expose
    private String pendingLeave;
    @SerializedName("short_name")
    @Expose
    private String shortName;

    public String getLeaveCategory() {
        return leaveCategory;
    }

    public void setLeaveCategory(String leaveCategory) {
        this.leaveCategory = leaveCategory;
    }

    public String getPendingLeave() {
        return pendingLeave;
    }

    public void setPendingLeave(String pendingLeave) {
        this.pendingLeave = pendingLeave;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
