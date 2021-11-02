package com.organic.india.pojo.leave_category;

public class Leave_category {

    String leave_id,leave_type;

    public Leave_category(String leave_id, String leave_type) {
        this.leave_id = leave_id;
        this.leave_type = leave_type;
    }

    public String getLeave_id() {
        return leave_id;
    }

    public void setLeave_id(String leave_id) {
        this.leave_id = leave_id;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }
}
