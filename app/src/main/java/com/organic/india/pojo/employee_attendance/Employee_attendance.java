package com.organic.india.pojo.employee_attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employee_attendance {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("employees")
    @Expose
    private List<Employee> employees = null;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<AttendanceReport> getAttendanceReport() {
        return attendanceReport;
    }

    public void setAttendanceReport(List<AttendanceReport> attendanceReport) {
        this.attendanceReport = attendanceReport;
    }
}
