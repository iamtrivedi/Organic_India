package com.organic.india.pojo.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingRequest {

    @SerializedName("leave_request")
    @Expose
    private int leaveRequest;
    @SerializedName("attendance_request")
    @Expose
    private int attendanceRequest;

    public int getLeaveRequest() {
        return leaveRequest;
    }

    public void setLeaveRequest(int leaveRequest) {
        this.leaveRequest = leaveRequest;
    }

    public int getAttendanceRequest() {
        return attendanceRequest;
    }

    public void setAttendanceRequest(Integer attendanceRequest) {
        this.attendanceRequest = attendanceRequest;
    }
}
