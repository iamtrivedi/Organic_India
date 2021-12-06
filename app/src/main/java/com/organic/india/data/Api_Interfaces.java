package com.organic.india.data;

import com.google.gson.JsonObject;
import com.organic.india.pojo.attendance.Attendance;
import com.organic.india.pojo.attendance_log.Attendance_log;
import com.organic.india.pojo.attendance_report.Attendance_report_res;
import com.organic.india.pojo.attendance_request.Attendance_request;
import com.organic.india.pojo.dashboard.Dashboard;
import com.organic.india.pojo.employee_attendance.Employee_attendance;
import com.organic.india.pojo.gallery_folder.Gallery_folder;
import com.organic.india.pojo.gallery_image_pojo.Gallery_image;
import com.organic.india.pojo.leave_applications.Leave_application;
import com.organic.india.pojo.logged_in_user.Logged_in_user;
import com.organic.india.pojo.pending_leave.Pending_leave;
import com.organic.india.pojo.policy.Get_policy;
import com.organic.india.pojo.team_leave_request.Team_leave_request;
import com.organic.india.pojo.team_listing.Team_listing;
import com.organic.india.pojo.update_attendance.Update_attendance_res;
import com.organic.india.pojo.whoswho.GetWhoswho;
import com.organic.india.pojo.whoswhoemployee.WhoWhos_employee;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Api_Interfaces {

    @POST("employee_login")
    Call<Logged_in_user> employee_login(@Body JsonObject jsonObject);

    @POST("employee_profile")
    Call<Logged_in_user> employee_profile(@Body JsonObject jsonObject);


    @POST("change_password")
    Call<ResponseBody> change_password(@Body JsonObject jsonObject);


    @POST("add_attandance")
    Call<Attendance> add_attandance(@Body JsonObject jsonObject);


    @POST("update_attandance")
    Call<ResponseBody> update_attandance(@Body JsonObject jsonObject);


    @POST("attendance_report")
    Call<Attendance_report_res> attendance_report(@Body JsonObject jsonObject);


    @POST("attandance_request")
    Call<Update_attendance_res> attandance_request(@Body JsonObject jsonObject);


    @POST("attendance_report")
    Call<Employee_attendance> employee_attendance_report(@Body JsonObject jsonObject);


    @POST("get_attandance_request")
    Call<Attendance_request> get_attandance_request(@Body JsonObject jsonObject);


    @POST("approve_reject_attendance_request")
    Call<ResponseBody> approve_reject_attendance_request(@Body JsonObject jsonObject);


    @POST("attandance_response")
    Call<ResponseBody> attandance_response(@Body JsonObject jsonObject);


    @POST("employee_dashboard")
    Call<Dashboard> employee_dashboard(@Body JsonObject jsonObject);


    @POST("attendance_logs")
    Call<Attendance_log> attendance_logs(@Body JsonObject jsonObject);


    @POST("team_listing")
    Call<Team_listing> team_listing(@Body JsonObject jsonObject);


    @Multipart
    @POST("create_leave")
    Call<ResponseBody> create_leave(@PartMap Map<String, RequestBody> map,
                                    @Part MultipartBody.Part profile_picture);

    @Multipart
    @POST("create_leave")
    Call<ResponseBody> create_leave(@PartMap Map<String, RequestBody> map);


    @POST("pending_leave")
    Call<Pending_leave> pending_leave(@Body JsonObject jsonObject);


    @POST("leave_applications")
    Call<Leave_application> leave_applications(@Body JsonObject jsonObject);


    @POST("team_leave_request")
    Call<Team_leave_request> team_leave_request(@Body JsonObject jsonObject);


    @POST("leave_accept_reject")
    Call<ResponseBody> leave_accept_reject(@Body JsonObject jsonObject);

    @POST("gallery_folders")
    Call<Gallery_folder> gallery_folders(@Body JsonObject jsonObject);


    @POST("gallery_images")
    Call<Gallery_image> gallery_images(@Body JsonObject jsonObject);


    @GET("whos_who_title")
    Call<GetWhoswho> whos_who_title();


    @POST("whos_who_title_employees")
    Call<WhoWhos_employee> whos_who_title_employees(@Body JsonObject jsonObject);


    @GET("policies")
    Call<Get_policy> policies();

}
