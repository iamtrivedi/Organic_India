package com.organic.india.pojo.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pdf {

    @SerializedName("Caption")
    @Expose
    private String caption;
    @SerializedName("Added by")
    @Expose
    private String addedBy;
    @SerializedName("Last Updated at")
    @Expose
    private String lastUpdatedAt;
    @SerializedName("pdf")
    @Expose
    private String pdf;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
