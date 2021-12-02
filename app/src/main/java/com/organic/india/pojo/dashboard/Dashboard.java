package com.organic.india.pojo.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Dashboard {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("organization_chart")
    @Expose
    private List<OrganizationChart> organizationChart = null;
    @SerializedName("pdf")
    @Expose
    private List<Pdf> pdf = null;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("app_version")
    @Expose
    private List<AppVersion> appVersion = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrganizationChart> getOrganizationChart() {
        return organizationChart;
    }

    public void setOrganizationChart(List<OrganizationChart> organizationChart) {
        this.organizationChart = organizationChart;
    }

    public List<Pdf> getPdf() {
        return pdf;
    }

    public void setPdf(List<Pdf> pdf) {
        this.pdf = pdf;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<AppVersion> getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(List<AppVersion> appVersion) {
        this.appVersion = appVersion;
    }
}
