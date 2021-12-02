
package com.organic.india.pojo.attendance_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceReport {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("holiday")
    @Expose
    private String holiday;
    @SerializedName("holiday_title")
    @Expose
    private String holidayTitle;
    @SerializedName("attendance_id")
    @Expose
    private String attendanceId;
    @SerializedName("actual_in_time")
    @Expose
    private String actualInTime;
    @SerializedName("actual_out_time")
    @Expose
    private String actualOutTime;
    @SerializedName("present")
    @Expose
    private String present;
    @SerializedName("log")
    @Expose
    private String log;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("weekly_off")
    @Expose
    private String weeklyOff;
    @SerializedName("leave")
    @Expose
    private String leave;
    @SerializedName("leave_title")
    @Expose
    private String leaveTitle;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
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

    public String getHolidayTitle() {
        return holidayTitle;
    }

    public void setHolidayTitle(String holidayTitle) {
        this.holidayTitle = holidayTitle;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getActualInTime() {
        return actualInTime;
    }

    public void setActualInTime(String actualInTime) {
        this.actualInTime = actualInTime;
    }

    public String getActualOutTime() {
        return actualOutTime;
    }

    public void setActualOutTime(String actualOutTime) {
        this.actualOutTime = actualOutTime;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeeklyOff() {
        return weeklyOff;
    }

    public void setWeeklyOff(String weeklyOff) {
        this.weeklyOff = weeklyOff;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getLeaveTitle() {
        return leaveTitle;
    }

    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

}
