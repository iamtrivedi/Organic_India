package com.organic.india.pojo.employee_attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceReport {

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("holiday")
    @Expose
    private String holiday;
    @SerializedName("weekly_off")
    @Expose
    private String weeklyOff;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("attendance_id")
    @Expose
    private Integer attendanceId;
    @SerializedName("log")
    @Expose
    private String log;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getWeeklyOff() {
        return weeklyOff;
    }

    public void setWeeklyOff(String weeklyOff) {
        this.weeklyOff = weeklyOff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
