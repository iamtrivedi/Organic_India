package com.organic.india.pojo.attendance_report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Attendance_report_res {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("attendance_report")
    @Expose
    private List<AttendanceReport> attendanceReport = null;

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

    public List<AttendanceReport> getAttendanceReport() {
        return attendanceReport;
    }

    public void setAttendanceReport(List<AttendanceReport> attendanceReport) {
        this.attendanceReport = attendanceReport;
    }

}
