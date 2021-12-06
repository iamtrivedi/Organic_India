package com.organic.india.pojo.attendance_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("employee_code")
    @Expose
    private Integer employeeCode;
    @SerializedName("attendance_date")
    @Expose
    private String attendanceDate;
    @SerializedName("attendance_status")
    @Expose
    private Integer attendanceStatus;
    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private Object checkOut;
    @SerializedName("in_time_request")
    @Expose
    private String inTimeRequest;
    @SerializedName("out_time_request")
    @Expose
    private String outTimeRequest;
    @SerializedName("reason")
    @Expose
    private Integer reason;
    @SerializedName("request_status")
    @Expose
    private Object requestStatus;
    @SerializedName("reporting_m_1")
    @Expose
    private Integer reportingM1;
    @SerializedName("reporting_m_2")
    @Expose
    private Integer reportingM2;
    @SerializedName("request_time")
    @Expose
    private String requestTime;
    @SerializedName("leave_category_id")
    @Expose
    private Object leaveCategoryId;
    @SerializedName("is_holiday")
    @Expose
    private Integer isHoliday;
    @SerializedName("is_used")
    @Expose
    private Integer isUsed;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("reason_name")
    @Expose
    private String reasonName;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(Integer employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Integer getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(Integer attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public Object getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Object checkOut) {
        this.checkOut = checkOut;
    }

    public String getInTimeRequest() {
        return inTimeRequest;
    }

    public void setInTimeRequest(String inTimeRequest) {
        this.inTimeRequest = inTimeRequest;
    }

    public String getOutTimeRequest() {
        return outTimeRequest;
    }

    public void setOutTimeRequest(String outTimeRequest) {
        this.outTimeRequest = outTimeRequest;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public Object getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Object requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getReportingM1() {
        return reportingM1;
    }

    public void setReportingM1(Integer reportingM1) {
        this.reportingM1 = reportingM1;
    }

    public Integer getReportingM2() {
        return reportingM2;
    }

    public void setReportingM2(Integer reportingM2) {
        this.reportingM2 = reportingM2;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Object getLeaveCategoryId() {
        return leaveCategoryId;
    }

    public void setLeaveCategoryId(Object leaveCategoryId) {
        this.leaveCategoryId = leaveCategoryId;
    }

    public Integer getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(Integer isHoliday) {
        this.isHoliday = isHoliday;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }
}
