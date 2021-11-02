package com.organic.india.pojo.attendance_log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Log {

    @SerializedName("log_id")
    @Expose
    private Integer logId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("attendance_date")
    @Expose
    private String attendanceDate;
    @SerializedName("in_time")
    @Expose
    private String inTime;
    @SerializedName("out_time")
    @Expose
    private String outTime;
    @SerializedName("in_time_ip")
    @Expose
    private String inTimeIp;
    @SerializedName("out_time_ip")
    @Expose
    private String outTimeIp;
    @SerializedName("in_time_lat")
    @Expose
    private String inTimeLat;
    @SerializedName("out_time_lat")
    @Expose
    private String outTimeLat;
    @SerializedName("in_time_long")
    @Expose
    private String inTimeLong;
    @SerializedName("out_time_log")
    @Expose
    private String outTimeLog;
    @SerializedName("DeviceLogID")
    @Expose
    private String deviceLogID;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getInTimeIp() {
        return inTimeIp;
    }

    public void setInTimeIp(String inTimeIp) {
        this.inTimeIp = inTimeIp;
    }

    public String getOutTimeIp() {
        return outTimeIp;
    }

    public void setOutTimeIp(String outTimeIp) {
        this.outTimeIp = outTimeIp;
    }

    public String getInTimeLat() {
        return inTimeLat;
    }

    public void setInTimeLat(String inTimeLat) {
        this.inTimeLat = inTimeLat;
    }

    public String getOutTimeLat() {
        return outTimeLat;
    }

    public void setOutTimeLat(String outTimeLat) {
        this.outTimeLat = outTimeLat;
    }

    public String getInTimeLong() {
        return inTimeLong;
    }

    public void setInTimeLong(String inTimeLong) {
        this.inTimeLong = inTimeLong;
    }

    public String getOutTimeLog() {
        return outTimeLog;
    }

    public void setOutTimeLog(String outTimeLog) {
        this.outTimeLog = outTimeLog;
    }

    public String getDeviceLogID() {
        return deviceLogID;
    }

    public void setDeviceLogID(String deviceLogID) {
        this.deviceLogID = deviceLogID;
    }
}
