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
    @SerializedName("in_time_ip")
    @Expose
    private String inTimeIp;
    @SerializedName("in_time_lat")
    @Expose
    private String inTimeLat;
    @SerializedName("in_time_long")
    @Expose
    private String inTimeLong;
    @SerializedName("out_time")
    @Expose
    private String outTime;
    @SerializedName("out_time_ip")
    @Expose
    private String outTimeIp;
    @SerializedName("out_time_lat")
    @Expose
    private String outTimeLat;
    @SerializedName("out_time_long")
    @Expose
    private String outTimeLong;

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

    public String getInTimeIp() {
        return inTimeIp;
    }

    public void setInTimeIp(String inTimeIp) {
        this.inTimeIp = inTimeIp;
    }

    public String getInTimeLat() {
        return inTimeLat;
    }

    public void setInTimeLat(String inTimeLat) {
        this.inTimeLat = inTimeLat;
    }

    public String getInTimeLong() {
        return inTimeLong;
    }

    public void setInTimeLong(String inTimeLong) {
        this.inTimeLong = inTimeLong;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getOutTimeIp() {
        return outTimeIp;
    }

    public void setOutTimeIp(String outTimeIp) {
        this.outTimeIp = outTimeIp;
    }

    public String getOutTimeLat() {
        return outTimeLat;
    }

    public void setOutTimeLat(String outTimeLat) {
        this.outTimeLat = outTimeLat;
    }

    public String getOutTimeLong() {
        return outTimeLong;
    }

    public void setOutTimeLong(String outTimeLong) {
        this.outTimeLong = outTimeLong;
    }
}
