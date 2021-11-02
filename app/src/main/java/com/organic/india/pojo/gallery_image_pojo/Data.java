package com.organic.india.pojo.gallery_image_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("sl.no")
    @Expose
    private Integer slNo;
    @SerializedName("file_id")
    @Expose
    private Integer fileId;
    @SerializedName("folder_id")
    @Expose
    private Integer folderId;
    @SerializedName("file_name")
    @Expose
    private String fileName;

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
