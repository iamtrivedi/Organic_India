package com.organic.india.pojo.gallery_folder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("sl.no")
    @Expose
    private Integer slNo;
    @SerializedName("folder_id")
    @Expose
    private Integer folderId;
    @SerializedName("folder_name")
    @Expose
    private String folderName;
    @SerializedName("folder_description")
    @Expose
    private String folderDescription;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderDescription() {
        return folderDescription;
    }

    public void setFolderDescription(String folderDescription) {
        this.folderDescription = folderDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
